<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.1.min.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery.mooko.tabs.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tab-core.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tab.js"></script>
<link href="<%=request.getContextPath()%>/themes/blue/styles/home-pc.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/content.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/reset.css" rel="stylesheet" type="text/css">
<title>站点详情</title>
<!-- 图形组件charts -->
<script src="<%=request.getContextPath()%>/javascript/charts/charts.js"></script>
<%-- 需要表格时再加载此项！ <script src="<%=request.getContextPath()%>/javascript/charts/grid.js"></script> --%>

<link href="<%=request.getContextPath()%>/javascript/charts/jquery.ui.all.css" rel="stylesheet" type="text/css">

<style>
    /* .main{
    position:relative;
    width:99%;
    margin:auto;       
    }  */
    .wrap{
        position:relative;
        margin:auto;
        width:300px;
        height:200px;
    } 

    .image{
        position:absolute;
        left:30px; 
        top:30px;    
        height:40px;
        width:50px;
        border:0px;
    }
    .p{
        position:absolute; 
        left:130px;
        top:10px;
        display:block;
        margin-top:15px;  
    }
    strong{
        font-size:18px;
        color:#CCCC66; 
    }
    .div{
        position:absolute;
        left:120px;
        top:60px; 
        width:180px;
        height:120px;  
        word-wrap:break-word;
        font-size:13px;
    }
    a{text-decoration:none}
    
</style>
<script type="text/javascript">

 	var fristPageClickFalg="event";
   
   $(function(){
		realTimeFresh();
		
		var nvrJson;
		
		var chart;
		var VMSCPUchart;
		var VMSMEMchart;
		var NVRcharts = new Array();
		$(document).ready(function() {
			
			$.ajax({
				type: "POST",
				url: 'firstPageNVRReportAction.action?siteId=' + '<s:property value="siteId"/>',
				success:function(jsonStr){
					var json = JSON.parse(jsonStr);
					
					nvrJson = json.data;
					
					var nvrNames = json.NvrNames;
					
					/* console.log(nvrJson);  */
					
					switch (nvrJson.length) {
						case 0:
							$('#NVRparent').html('');
							break;
						case 1:
							$('#NVRparent').html('<div id="NVR0"></div>');
							break;
						case 2:
							$('#NVRparent').html('<div id="NVR0" style="float:left;"></div><div id="NVR1" style="float:right;"></div>');
							break;
						default:
							alert('异常：该站点包含超过2个NVR!');
							break;
					}
					
					for ( var i = 0; i < nvrJson.length; i++) {
						if (i > 1)
							break;
						
						NVRcharts[i] = new Charts.Chart({
							chart: {
								renderTo: 'NVR'+i,
								defaultSeriesType: 'pie',
								backgroundColor: '#F4F4F4',
								height: 215,
								width: 220,
								events: {                                                           
			                		load: function() {                                              
					                    // set up the updating of the chart each second             
					                    var series = this.series[0];                                 
					                    setInterval(
			                    			function() {                                    
			                    				$.ajax({
			                    					 type: "POST",
			                    					 url: "firstPageNVRReportAction.action?siteId="+'<s:property value="siteId"/>',
			                    					 dataType:"json",
			                    					 success: function(json){
			                    						  /* console.log(json);  */
			                    						 series.setData(json.cpu);
			                    						 /* console.log(series); */
			                    						 /* alert(chart.series); */
			                    					 }
			                    				});
			                    					 
						                        /* series.addPoint([x, y], true, true); */                    
					                    	}, 10000);                                                   
			                		}                                                               
			            		}      
							},
							title: {
								text: nvrNames[i]
							},
							plotOptions: {
								pie: {
									allowPointSelect: true,
									cursor: 'pointer',
									dataLabels: {
										enabled: false
									},
									showInLegend: true
								}
							},
							tooltip: {
								formatter: function() {
									return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
								}
							},
							 series: [{
								type: 'pie',
								name: 'Browser share',
								data: nvrJson[i]
							 }]
						});
					}					
					
				}
			});
			
			
			chart = new Charts.Chart({
				chart: {
					renderTo: 'realAlarmColumn',
					defaultSeriesType: 'column',
					backgroundColor: '#F4F4F4',
					height: 215,
					width: 514,
					events: {                                                           
                		load: function() {                                              
		                    // set up the updating of the chart each second             
		                    var series = this.series[0];                                 
		                    setInterval(
                    			function() {                                    
                    				$.ajax({
                    					 type: "POST",
                    					 url: "firstPageGradeAlarmAction.action?siteId="+'<s:property value="siteId"/>',
                    					 dataType:"json",
                    					 success: function(json){
                    						 /* console.log(json); */
                    						 series.setData(json);
                    						 /* console.log(series); */
                    						 /* alert(chart.series); */
                    					 }
                    				});
                    					 
			                        /* series.addPoint([x, y], true, true); */                    
		                    	}, 10000);                                                   
                		}                                                               
            		}      
				},
				title: {
					text:""	
				},
				plotOptions: {
					column: {
						pointPadding: 0.5,
						borderWidth: 0,
						pointWidth: 20,
						shadow: false
					}
				},
				xAxis: {
					categories: [
						"<s:text name='graph.alarmGrade.critical'/>", 
						"<s:text name='graph.alarmGrade.major'/>", 
						"<s:text name='graph.alarmGrade.minor'/>", 
						"<s:text name='graph.alarmGrade.normal'/>" 
					],
					labels: {
						y : 18
					}
				},
				yAxis: {
					title:{
						text:""
					}
				},
				legend: {
					enabled: false
					/* layout: 'vertical',
					backgroundColor: '#FFFFFF',
					align: 'left',
					verticalAlign: 'top',
					x: 100,
					y: 70,
					floating: true,
					shadow: true */
				},
				tooltip: {
					formatter: function() {
						return ''+
							this.x +': '+ this.y;
					}
				},
				series: [{
					name: '<s:text name="graph.series.alarmCount"/>',
					data: [{y:0},{y:0},{y:0},{y:0}],
					dataLabels: {
			          enabled: true,
			          backgroundColor:'#124632',
			          borderColor:'#000000',
			          color: '#000000',
			          align: 'center',
			          x: 0,
			          y: -8,
			          style: {
			              fontSize: '13px',
			              fontFamily: 'Verdana, sans-serif' ,
			              cursor: 'default'
			              /*textShadow: '0 0 3px black' */
       				  }
            		}
				}]
			});
			
			/*VMS CPU*/
			VMSCPUchart = new Charts.Chart({
				chart: {
					renderTo: 'VMSCPU',
					defaultSeriesType: 'pie',
					backgroundColor: '#F4F4F4',
					height: 215,
					width: 220,
					events: {                                                           
                		load: function() {                                              
		                    // set up the updating of the chart each second             
		                    var series = this.series[0];                                 
		                    setInterval(
                    			function() {                                    
                    				$.ajax({
                    					 type: "POST",
                    					 url: "firstPageVMSReportAction.action?siteId="+'<s:property value="siteId"/>',
                    					 dataType:"json",
                    					 success: function(json){
                    						 /* console.log(json); */
                    						 series.setData(json.cpu);
                    						 if (json.cpu) {
                   								VMSCPUchart.setTitle({text:"CPU"});
                							 }
                    						 /* console.log(series); */
                    						 /* alert(chart.series); */
                    					 }
                    				});
                    					 
			                        /* series.addPoint([x, y], true, true); */                    
		                    	}, 10000);                                                   
                		}                                                               
            		}      
				},
				title: {
					text:""	
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: false
						},
						showInLegend: true
					}
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
					}
				},
				 series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
							
							/* {name : 'CPU占用', y:50},
							{
								name: 'CPU空闲',    
								y: 50
							} */
						]
				 }]
			});
			
			/*VMS 内存*/
			VMSMEMchart = new Charts.Chart({
				chart: {
					renderTo: 'VMSMEM',
					defaultSeriesType: 'pie',
					backgroundColor: '#F4F4F4',
					height: 215,
					width: 220,
					events: {                                                           
	            		load: function() {                                              
		                    // set up the updating of the chart each second             
		                    var series = this.series[0];                                 
		                    setInterval(
	                			function() {                                    
	                				$.ajax({
	                					 type: "POST",
	                					 url: "firstPageVMSReportAction.action?siteId="+'<s:property value="siteId"/>',
	                					 dataType:"json",
	                					 success: function(json){
	                						 /* console.log(json); */
	                						 series.setData(json.mem);
                							 if (json.mem) {
                								 VMSMEMchart.setTitle({text:"内存"});
                							 }
	                						 /* console.log(series); */
	                						 /* alert(chart.series); */
	                					 }
	                				});
	                					 
			                        /* series.addPoint([x, y], true, true); */                    
		                    	}, 10000);                                                   
	            		}                                                               
	        		}      
				},
				title: {
					text:""	
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: false
						},
						showInLegend: true
					}
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
					}
				},
				 series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
							
							/* {name : '内存占用', y:50},
							{
								name: '内存空闲',    
								y: 50
							} */
						]
				 }]
			});
		
	});
		
		/*实时告警级别柱状图初始化数据*/
		$.ajax({
			 type: "POST",
			 url: "firstPageGradeAlarmAction.action?siteId="+'<s:property value="siteId"/>',
			 dataType:"json",
			 success: function(json){
				 chart.series[0].setData(json);
			 }
		});
			
		/*设备离在线统计初始化数据*/
		$.ajax({
			 type: "POST",
			 url: "resourceReport.action?siteId="+'<s:property value="siteId"/>',
			 dataType:"json",
			 success: function(list){
				 var str = '';
				 for(var i = 0 ; i < list.length ; i++){
					 str += '<ul><li class="ul-top ';
					 if(list[i].used != 0 && list[i].used == list[i].total)
						 str += 'top-blue';
				 	 else
				 		 str += 'top-grey';
					 str += '"><span class="font-30">'+list[i].used+'</span>/<span class="font-14">'+list[i].total+'</span></li>';
					 str += '<li class="ul-info">'+ list[i].type +'</li></ul>';
				 }
				 $("#resourceUsed").html(str);
			 }
		});
		
		/*VMS CPU和内存利用率初始化数据*/
		  $.ajax({
			type: "POST",
			url: 'firstPageVMSReportAction.action?siteId=' + '<s:property value="siteId"/>',
			success:function(test){
				var json = JSON.parse(test);
				VMSCPUchart.series[0].setData(json.cpu);
				VMSMEMchart.series[0].setData(json.mem);
				if (json.cpu) {
					VMSCPUchart.setTitle({text:"CPU"});
				}
				if (json.mem) {
					VMSMEMchart.setTitle({text:"内存"});
				}
			}
		});  
	});
   
   function realTimeFresh(){
	 //实时告警列表
		$.ajax({
			 type: "POST",
			 url: "firstPageAlarmListAction.action?siteId="+'<s:property value="siteId"/>',
			 dataType:"json",
			 success: function(list){
				 var htmlstr = '';
				for(var i = 0 ; i < list.length ; i++){
					var str = '<li> <span class="icon-warn warn-'+list[i].alarmGrade+'"></span>';
					str += '<div class="nav-inner"><h4>'+ list[i].alarmTitle +'</h4><h4><small>'+ list[i].alarmTime +'</small></h4></div>';
					str += '<div class="pop" id="detail" style="display: none;"><table cellpadding="0" cellspacing="0">';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmObject"/>\uff1a</td><td class="pop-list-content"><strong>'+ list[i].deviceName +'</strong></td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmGrade"/>\uff1a</td><td class="pop-list-content"><strong>'+ list[i].alarmGradeName +'</strong></td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmTitle"/>\uff1a</td><td class="pop-list-content">'+ list[i].alarmTitle +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmContent"/>\uff1a</td><td class="pop-list-content">'+ list[i].alarmContent +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.firstAlarmTime"/>\uff1a</td><td class="pop-list-content">'+ list[i].firstAlarmTime +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmType"/>\uff1a</td><td class="pop-list-content">'+ list[i].alarmType +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.deviceTypeName"/>\uff1a</td><td class="pop-list-content">'+ list[i].deviceTypeName +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.count"/>\uff1a</td><td class="pop-list-content">'+ list[i].count +'</td></tr>';
					str += '<tr><td class="pop-list-label"><s:text name="detail.columnName.alarmTime"/>\uff1a</td><td class="pop-list-content">'+ list[i].alarmTime +'</td></tr></table>';
					<s:if test="#session.authenticater.authIdList.contains('05_01_03_00')">
					str += '<div class="btn-group-firstPage"><button onclick="confirmAlarm('+ list[i].alarmID +')" class="btn btn-blue"><s:text name="button.conform"/></button>';
					</s:if>
					<s:if test="#session.authenticater.authIdList.contains('05_01_02_00')">
					str += '<button onclick="clearAlarm('+ list[i].alarmID +')" class="btn btn-red"><s:text name="button.clear"/></button></div></div></li>';
					</s:if>
					htmlstr += str;
				}
				
				$("#realList").html(htmlstr);
				/*样式调整*/
				$("#realList").height($(document).height()-85);
				
				$("#realList").children().each(function(){
					$(this).mouseover(function(){
						$(this).children(":last").css("display","block");
					});
					$(this).mouseout(function(){
						$(this).children(":last").css("display","none");
					});
					$(this).children(":last").mouseout(function(){
						$(this).css("display","none");
					});
					
					var third = $(this).children(":last");
					
					var second = $(this).children(":odd:first");
					
					if($(this).index()>=5){
						third.offset({top:(second.offset().top + second.height() - third.height()),left:($(this).offset().left+$(this).width() + 10)});
					}
					else{
						third.offset({top:($(this).offset().top - 20),left:($(this).offset().left+$(this).width() + 10)});
					}
				});
			 		
			 },
			 error:function(e){
				$("notice ul").html('<s:text name="oplog.getDataFail"/>');}
		});
   }
   
   /*告警确认*/
   function confirmAlarm(alarmId){
	   $.ajax({
			 type: "POST",
			 url: "alarmConfirm.action",
			 dataType:"json",
			 data: {"domain.alarmID":alarmId+''},
			 success: function(result){
				 alert(result.result);
				 realTimeFresh();
			 }
	   });
   }

   /*告警清除*/
   function clearAlarm(alarmId){
	   $.ajax({
			 type: "POST",
			 url: "alarmClear.action",
			 dataType:"json",
			 data: {"domain.alarmID":alarmId+''},
			 success: function(result){
				 alert(result.result);
				 realTimeFresh();
			 }
	   });
   }
   </script>
</head>
<body>
   <div class="main">
    <div class="section">
      <div class="co-2">
        <div class="panel-title"> <s:text name='module.title.detail'/> </div>
        <ul class="nav-left" id="realList">
        </ul>
      </div>
    </div>
    <div class="section">
      <div class="co-5">
        <div class="panel-title"><s:text name="module.title.gradeGraph"/></div>
        <div class="panel" id="realAlarmColumn">
        </div>
      </div>
      <div class="co-5">
        <div class="panel-title"><s:text name="module.title.equipment"/></div>
        <div class="panel">
          <div class="ul-group" id="resourceUsed">
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="co-4">
        <div class="panel-title"><s:text name="module.title.NVRmemory"/></div>
        <div class="panel" id="NVRparent">
        	
        </div>
      </div>
      <div class="co-4">
        <div class="panel-title"><s:text name="module.title.VMSCpu"/></div>
        <div class="panel">
       		<div id="VMSCPU" style="float: left"></div>
        	<div id="VMSMEM" style="float: right"></div>
        </div>
      </div>
    </div>
  </div>
  
</body>
</html>