<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.2.js"></script>

<!--日期-->
<link href="<%=request.getContextPath()%>/stylesheets/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/date.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
<%-- <script src="<%=request.getContextPath()%>/javascript/select_date.js"></script> --%>

<script src="<%=request.getContextPath()%>/javascript/jq.layout.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery.jscrollpane.min.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery.mousewheel.js"></script>
<script src="<%=request.getContextPath()%>/javascript/scroll_size.js"></script>
 
<script src="<%=request.getContextPath()%>/javascript/nav.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tree.js"></script>
<script src="<%=request.getContextPath()%>/javascript/list.js"></script>
<script src="<%=request.getContextPath()%>/javascript/form.js"></script>
<script src="<%=request.getContextPath()%>/javascript/dialog.js"></script>
<script src="<%=request.getContextPath()%>/javascript/ui.checkbox.js"></script>
<script src="<%=request.getContextPath()%>/javascript/wzdBar.js"></script>
<script src="<%=request.getContextPath()%>/javascript/mask.js"></script>
<script src="<%=request.getContextPath()%>/javascript/checkbox.js"></script>

<!-- page -->
<link href="<%=request.getContextPath()%>/themes/default/styles/page.css" rel="stylesheet" type="text/css">

<!--  时间控件 时分秒 -->
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-timepicker-addon.js"></script>


<!-- 图形组件charts -->
<script src="<%=request.getContextPath()%>/javascript/charts/charts.js"></script>
<script src="<%=request.getContextPath()%>/javascript/charts/grid.js"></script>

<link href="<%=request.getContextPath()%>/javascript/charts/jquery.ui.all.css" rel="stylesheet" type="text/css">

<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.progressbar.js"></script>
<%-- 性能单独的css文件--%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/blue/styles/right_side.css" />
<link href="<%=request.getContextPath()%>/themes/blue/styles/content.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/reset.css" rel="stylesheet" type="text/css">
<script type="text/JavaScript">

var freshTime = "";
var type_Id='<s:property value="#parameters.typeId"/>';

var fanStatusOid='<s:text name="ipan.fan.status.oid"/>';
var fanStatus0='<s:text name="ipsan.fan.status0"/>';
var fanStatus1='<s:text name="ipsan.fan.status1"/>';

var raidStatusOid='<s:text name="nvr.raid.status.oid"/>';
var raidStatus1='<s:text name="nvr.raid.status1"/>';
var raidStatus2='<s:text name="nvr.raid.status2"/>';
var raidStatus3='<s:text name="nvr.raid.status3"/>';
var raidStatus4='<s:text name="nvr.raid.status4"/>';
var raidStatus5='<s:text name="nvr.raid.status5"/>';
var raidStatus6='<s:text name="nvr.raid.status6"/>';

window.onload=function (){
	$("#performanceProperty").hide();
	freshTime = '<s:property value="freshTime" />';
	//查询Tab页信息
	getTab();
};

//tab数组
var result = [];
var indexArray =[];
function getTab() {
	$.ajax({
        url: "getEncoderAlreadyConfTab.action",
        type: "POST",
        data:{typeId:""+'<s:property value="#parameters.typeId"/>',deviceIp:""+'<s:property value="#parameters.deviceIp"/>'},
        dataType: "text",
        success:function(tab) {//请求Tab页信息
            result = tab.split(';');
			for(var i = 0 ; i < result.length ; i++) {
				//拼所有Tab
				if (i == 0) {
					//默认选中第一个Tab
	        		$("#task1 .btn-group").append('<button id="'+result[i]+'ReportButton" style="padding-left:20px;"'
	        				+'class="small-btn btn-grey" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;">'
	        				+'<span>'+result[i]+'</span></button>');
				}else {
					//其它Tab不选中
	        		$("#task1 .btn-group").append('<button id="'+result[i]+'ReportButton" style="padding-left:20px;"'
	        			+'class="small-btn btn-eee" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;">'
	        			+'<span>'+result[i]+'</span></button>');
				}
				//拼所有折线图和表格的外框
 		       	$("#task1").append('<div id="'+result[i]+'Report" class="report" style="height: 50%">'
 		       			+'<div id="pmres'+result[i]+'" class="reportdiv"></div>'
 		       			+'</div>');
				//默认展示第一个Tab下的div
				if (i==0) {
					$("#"+result[0]+"Report").show();
					lastClick = result[0]+"Report";
				}
				//定义折线图的html用于替换
				var propertyViewChart = '<tr><td style="width: 100%;height: 200px;"><div id="replaceThisChartId" class="reportdiv1"></div></td></tr>';
				$("#performancePropertyTable").append(propertyViewChart.replace("replaceThisChartId",result[i]+'view'));
 			}
			//tab页信息请求返回成功，并且拼好html后，请求tab下具体指标信息
			getTabIndex();
        }
    })
}
//所有指标的JSONArray
var tabIndexArray;
function getTabIndex(){
	$.ajax({
        url: "getAlreadyConfTabIndexEncoder.action",
        type: "POST",
        data:{typeId:""+'<s:property value="#parameters.typeId"/>',deviceIp:""+'<s:property value="#parameters.deviceIp"/>'},
        dataType: "json",
        success:function(tabIndex) {//请求Tab页信息
        	tabIndexArray = tabIndex;
        	
        	getAllData();
        }
    })
}
//所有数据的JSONArray
//承载普通tab页数据的数组
var pmListJSONArray = [];


//第一次请求页面获取全量数据
var onlyTableName="";
function getAllData(){
	$.ajax({
	           url: "searchEncoderResource.action",
	           type: "POST",
	           data:{typeId:""+'<s:property value="#parameters.typeId"/>',deviceIp:""+'<s:property value="#parameters.deviceIp"/>'},
	           dataType: "text",
	           success:function(pmList) {
	        	 	//请求回来的json数据格式是什么样的？答：物理机对象集合(jsonArray格式)
	        	 	if(pmList!=null && pmList!=""){
	        	 		pmListJSONArray = eval("("+pmList+")");
		        	 	//第一轮全量数据获取完成后，绘制表格
		        	 	drawTable();
		        	 	//第一轮全量数据获取完成后，调用方法绘制曲线图
		        	 	drawLine();
		        	 	//计算只有表格的页面高度
		        	 	setOnlyTableHeight();
		        	 	//所有数据处理结束后准备获取增加数据，为折线图和表格刷新做准备，5分钟获取一次
		        	 	setInterval(getIncreaseData,freshTime);
	        	 	}
	        	 	
	           }
       });
}


<%-- 报表 --%>
Charts.setOptions({
	global: {
		useUTC: false
	}
});

function drawLine() {
	//首先遍历tab
	var lineFlag = false;
	for(var t = 0 ; t < result.length ; t++) {
		//var chartzu = new Array();
		
		//是否只有图.
		if($("#pmres"+result[t]).siblings().html()==null){
			setReportHeight(result[t]);
		}else{
			//重新计算表格的高度.
			redefineTable(result[t]);
		}
		var seriesData = [];
		//定义一个临时数组
		var temp = [];
		temp = pmListJSONArray;
		//遍历tab下的指标
		for (var one in tabIndexArray)
		{
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[one]["indexGroup"] && tabIndexArray[one]["ifShowLine"] == "1"){
				lineFlag = true;
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[one]["indexUnit"];
				var indexName = tabIndexArray[one]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii in temp)
				{
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
					if(tabIndexArray[one]["indexGroup"]==temp[ii]["mibGroup"]|| temp[ii]["mibGroup"]==''){
						//if(temp[ii]["oid"]!='1.3.6.1.4.1.42347.1.1.4.3.1.2' && temp[ii]["oid"]!='1.3.6.1.4.1.42347.1.1.4.3.1.3'
						//		&& temp[ii]["oid"]!='1.3.6.1.4.1.42347.1.1.4.3.1.4'){
						  if(temp[ii]["ifShowLine"]=="1"){
							  var yValue = temp[ii]["tagetValue"];
								if (isNaN(parseFloat(yValue))){
									indexData.push({
										x: temp[ii]["showTime"].time,
										y: null
									});
								} else {
									indexData.push({
										x: temp[ii]["showTime"].time,
										y: Math.round(parseFloat(yValue)*100)/100
									});
								} 
						  }	
							
						//}
					}
				}
				//将指标单位和指标名称一起拼到名称中，在折线图中备用
				seriesData.push({
						name: indexName+'('+indexUnit+')',
						data: indexData
				});
				indexArray.push(t);
			}else if(result[t]==tabIndexArray[one]["indexGroup"] && tabIndexArray[one]["ifShowLine"] !="1"){
				onlyTableName +=result[t]+";";
				
			}
		}
		if(lineFlag){
			lineFlag = false;
			//根据tab个数新建chart个数
			this['chart'+t] = new Charts.Chart({
				chart: {
					renderTo: 'pmres'+result[t],
					defaultSeriesType: 'spline',
					backgroundColor: '#ffffff'
				},
				title: {
					text: ''
				},
				xAxis: {
					type: 'datetime',
					dateTimeLabelFormats: { // don't display the dummy year
						minute: '%H:%M'
					}
				},
				yAxis: {
					title: {
						text: ''
					},
					labels: {
						formatter: function(){
							return (Math.abs(this.value));
						}
					},
					min: 0,
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+
						Charts.dateFormat('%H:%M', this.x) +' '+ this.y;
					}
				},
				plotOptions: {
					spline: {
						marker: {
							enabled: false,
							symbol: 'circle',
							radius: 0,
							states: {
								hover: {
									enabled: true
								}
							}
						}
					}
				},
				legend:{
					margin:20
				},
				series: seriesData
			});		
		}
		if ($("#pmres"+result[t]).siblings().html()!=null&&!$("#pmres"+result[t]).html()) {
			setTableOnlyHeight(result[t]);
		}
	}
}

function setReportHeight(id){	
	$("#"+id+"Report").height($(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-23);
	$("#pmres"+id).css("height",$("#"+id+"Report").height() + "px");
	$(window).resize(function(){
		$("#task1").height($(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-23-10);
	});
};

function setTableOnlyHeight(id){
	$('#' + id + 'Report .task1TableDiv').css('height','auto');
}

function redefineTable(divId){
	var tableHeader ="";
	if($("#pmres"+divId).html()!=null){
		   tableHeader = $("#"+divId+"Report").find(".task1Table").height()+20+26+32;
		var tableHeight = $(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-$("#pmres"+divId).height()-tableHeader-23;
		$("#"+divId+"Report").find(".task1TableDiv").height(tableHeight+"px");
	}
	$(window).resize(function(){
		var tableHeight = $(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-$("#pmres"+divId).height()-tableHeader-23-10;
		$("#"+divId+"Report").find(".task1TableDiv").height(tableHeight+"px");
	}); 
}

function setOnlyTableHeight(){
	if(onlyTableName.length>0){
		var tableName = onlyTableName.split(";");
		for(var i=0;i<tableName.length;i++){
			if(tableName[i]!="" ||tableName[i]!=null){
				if($("#pmres"+tableName[i]).html()==""){
					$("#pmres"+tableName[i]).removeClass();
					$("#"+tableName[i]+"Report").height($(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-13);
					$("#"+tableName[i]+"Report").find(".task1Table").css("margin-top","0px");
				}
			}
		}
	}
}

//5分钟自动刷新折线图和表格
function freshLineAndTable(){
	//遍历每一个chart中的series将折线图更新
	 for (var bb = 0 ; bb < indexArray.length; bb++) {
		for (var aa = 0 ; aa < eval('chart'+indexArray[bb]).series.length; aa++) {
			//获取当前折线图上第一个折线(带单位的折线图名称)
			var seriesNameUnit = eval('chart'+indexArray[bb]).series[aa].name;
			//alert(seriesNameUnit);
			//用seriesNameUnit遍历tabIndexArray集合中匹配tab以及列名
			var temp = pmListJSONArrayIncrease;
			for(var ii in temp){
				var nameandunit = temp[ii]["mibName"]+'('+temp[ii]["mibUnit"]+')';	
				if (seriesNameUnit==nameandunit && temp[ii]["ifShowLine"] == "1"){
					if(isNaN(parseFloat(temp[ii]["tagetValue"]))){
						eval('chart'+indexArray[bb]).series[aa].addPoint([temp[ii]["showTime"].time,null],true,false);
					}else{
						eval('chart'+indexArray[bb]).series[aa].addPoint([temp[ii]["showTime"].time,Math.round(parseFloat(temp[ii]["tagetValue"])*100)/100],true,false);
					}
				}
			}
		}
	} 
	//获取tbody中的所有tr行
	refreshTable();
};


function refreshTable(){
	var tempTable = pmListJSONArrayIncrease;
	$("#task1 .task1TableDiv table tbody tr").each(
			function(){
				var td = $(this).children();
				var td0_text = td.eq(0).text();
				var td1 =td.eq(1).text();
				var td2 = td.eq(2);
				//用td_text作为oid列(页面输入的OID字段，用来显示表格中的第一列)，用该列获取列名
				td2.text("");
				for(var one in tempTable){
					var mibName = tempTable[one]["mibName"];
					var indexNumber = tempTable[one]["indexNumber"];
					if(tempTable[one]["ifShowLine"]!='1' && td0_text==indexNumber && td1==mibName){
						var tagetValue = tempTable[one]["tagetValue"];
						var mibUnit = tempTable[one]["mibUnit"];
						var oid = tempTable
						if(type_Id=='34'){
							if(oid==fanStatusOid){
								if(tagetValue=='0'){
									tagetValue==fanStatus0;
								}else if(tagetValue=='1'){
									tagetValue = fanStatus1;
								}
							}
						}else{
							if(!isNaN(parseFloat(tagetValue))){
								if(mibUnit=="Byte" || mibUnit=='byte'){
									tagetValue = Math.round(tagetValue/1024/1024) +" MB";
								}else if(mibUnit=='KB'|| mibUnit=='kb' ||mibUnit=='Kb'){
									tagetValue = Math.round((parseFloat(tagetValue)/1024/1024)*100)/100 +" GB";
								}else{
									tagetValue = Math.round(tagetValue) +" "+mibUnit;
								}
							}else if(tagetValue ==null || tagetValue==""){
								tagetValue = "N/A";
							}
						}
						td2.text(tagetValue);
						break;
					}
				}
		}
	);
};


function drawTable(){
	//首先遍历tab数组
	for(var t = 0 ; t < result.length ; t++) {
		var tableId = "";
		//遍历每一个tab下的指标
		for(var ii in tabIndexArray){
			if(result[t]==tabIndexArray[ii]["indexGroup"] && tabIndexArray[ii]["ifShowLine"]!="1"){
				if($("#"+result[t]+"Report").find("table").html()==null){
					$("#"+result[t]+"Report").append('<div class="task1Table" style="width:100%;float:left;margin-top:20px">'
		      			+'<table  cellpadding="0"  cellspacing="0" class="grid grid-bordered">'
		      			+'<thead><tr>'
		      			+'<th width="15%"><s:text name="performance.resource.index" /></th>'
		     			+'<th  width="30%">'
		     			+'<s:text name="performance.resource.discribe" /></th>'
		     			+'<th width="20%">'
		     			+'<s:text name="performance.resource.tableValue" /></th>'
		     			+'<th width="35%">'
		     			+'<s:text name="performance.resource.tableName" /></th></tr></thead>'
		     			+'</table></div>'
		     			+'<div class="task1TableDiv" style="width:100%;overflow:auto;float:left;">'
		     			+'<table  id="tableScoll" class="grid grid-bordered" style="margin-top:0px;border-top:0px;"><tbody class="stripe_tb"></tbody></table></div>');
				}
			}	
		}
		
		var temp = pmListJSONArray;
		for (var one in temp){
			//定义一个临时数组
			//将该tab下的所有tr行拼到表格中
			//alert(result[t]+":"+tabIndexArray[one]["indexGroup"]+":"+(result[t]==tabIndexArray[one]["indexGroup"])+"::"+tabIndexArray[one]["ifShowLine"]+":"+(tabIndexArray[one]["ifShowLine"]!="1"));
			if (result[t]==temp[one]["mibGroup"] && temp[one]["ifShowLine"] != "1")
			{
				tableId = result[t];
				
				//列名是定义好的一些有规律的字符串，不应显示到表格第一项，但是需要该字段取性能数据
				var index = temp[one]["indexNumber"];	
				var oid = temp[one]["oid"];
				var tagetValue =temp[one]["tagetValue"];
				var unit = temp[one]["mibUnit"];
				var colomeName = temp[one]["mibName"];
				
				
				//类型是34风扇状态值.
				if(type_Id=='34' && oid==fanStatusOid){
					if(tagetValue=='0'){
						tagetValue = fanStatus0;
					}else if(tagetValue=='1'){
						tagetValue = fanStatus1;
					}
				}else if(type_Id=='31' && oid==raidStatusOid){
					if(tagetValue=='1'){
						tagetValue = raidStatus1;
					}else if(tagetValue=='2'){
						tagetValue = raidStatus2;
					}else if(tagetValue=='3'){
						tagetValue = raidStatus3;
					}else if(tagetValue=='4'){
						tagetValue = raidStatus4;
					}else if(tagetValue=='5'){
						tagetValue = raidStatus5;
					}else if(tagetValue=='6'){
						tagetValue = raidStatus6;
					}
				}else{
					if('' == tagetValue || null == tagetValue || -1==tagetValue){
						tagetValue = 'N/A';
						unit = '';
					}else if(!isNaN(tagetValue)){
						if(unit=="Byte" || unit=='byte'){
							tagetValue = Math.round((tagetValue/1024/1024))+" MB";
						}else{
							tagetValue = tagetValue +" "+unit;
						}
					}
				}
				//采集到指标的集合可能不为空
				
				//拼tr行
				$("#" +result[t]+ "Report .task1TableDiv table ").append('<tr >'
						+'<td style="padding-left:15px;border-right:1px solid #eee;width:15%">'+index+'</td>'
						+'<td style="padding-left:15px; border-right:1px solid #eee;width:30%">'+colomeName+'</td>'
						+'<td style="padding-left:15px; border-right:1px solid #eee;width:20%">'+tagetValue+'</td>'
						+'<td style="padding-left:15px; border-right:1px solid #eee;width:35%">'+oid+'</td></tr>');
				
			}
			
		}
	}
	//图标绘制完成后，设置奇偶行样式
 	jiouRowStyle();
 	//当奇偶行样式设置完成后，设置表格自动高度
 	setTableHeight("pmres"+tableId);
} 

function jiouRowStyle(){
	//加载数据
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		})
	});
	//设置奇偶行样式
	$("table").width(pageWidth);
	$('tr').unbind('mouseenter mouseleave');
	$(".report").each(function(){
		$(this).find(" .stripe_tb tr:odd").addClass("ou");
	});

}


function setTableHeight(tableId){
	//list自动高度
	$(".task1TableDiv").height($(window).height()-$("#"+tableId).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-57-14);
	$(window).resize(function() {
		var winHeight = $(window).height();
		$(".task1TableDiv").height(winHeight -$(".reportdiv").height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-57-14-10);
	});
}

//承载普通tab页增量数据的数组
var pmListJSONArrayIncrease = [];

//根据采集时间间隔在action中直接处理增量数据(此处获取最后时间带到后台失败)
function getIncreaseData(){
	$.ajax({
        url: "searchEncoderResourceIncrease.action",
        type: "POST",
        data:{deviceIp:""+'<s:property value="#parameters.deviceIp"/>',typeId:'<s:property value="#parameters.typeId" />'},
        dataType: "text",
        success:function(pmList) {
        	pmListJSONArrayIncrease = eval("("+pmList+")");
        	//增量数据请求后,刷新折线图和表格
        	freshLineAndTable();
        }
	});
}

</script>

<style type="text/css">
<%-- golbal.css中的内容如下 ：缺少一个overflow:hidden--%>
div{ overflow:visible; height:auto;}
<%-- 表格中	第二列数据	的div--%>
.progressbarNum { float: left; width: 100px;}
<%-- 表格中	第二列进度条		的div--%>
.progressbar{ margin-left: 2px; margin-top: 4px; display: true ; width:80px; height:10px; float: left;}
<%-- 包含所有报表   	的div--%>
.report {display: none;width: 100%;}
.reportdiv {margin-left: 10px ; margin-top: 12px; width: 97%; height: 200px;}


.stripe_tb tr.ou {background:#f8f8f8;} /*这行将给所有偶数行加上背景色*/

</style>

<script type="text/JavaScript">

var lastClick;
<%--控制 div 显示与隐藏属性的 切换--%>
function change(r) {
	$(".report").hide();
	$("#"+r).show();
	$('#'+lastClick+'Button').removeClass();
	$('#'+lastClick+'Button').addClass('small-btn btn-eee');
	$('#'+r+'Button').removeClass();
	$('#'+r+'Button').addClass("small-btn btn-grey");
	lastClick = r;
}


function resource(id){
	//首先隐藏性能视图的div
	$("#performanceProperty").hide();
	//其次隐藏第二个tab页和第三个tab页的div

	//显示性能指标的div
	$("#task1").show();
	$('#tab-title-1').removeClass().addClass("small-btn btn-blue");
	$('#tab-title-2').removeClass().addClass("small-btn btn-grey");
}

//增加判断，当用户第一次点击性能视图的tab时，请求数据，否则只有性能视图的下拉列表变化时请求数据
var propertyViewClickIsFirst = true;
function property(id){
	$('#tab-title-1').removeClass().addClass("small-btn btn-grey");
	$('#tab-title-2').removeClass().addClass("small-btn btn-blue");
	
	//请求性能视图数据，显示性能视图div
	$("#performanceProperty").show();
	//隐藏被load的div
	$("#task1").hide();
	if (propertyViewClickIsFirst) {
		timeChange(document.getElementById("oneWeek"));
		propertyViewClickIsFirst = false;
	}
}
</script>

<style type="text/css" id="page-css">
	.scroll-pane
	{
		width: 100%;
		height: 455px;
		overflow: auto;
	}
</style>
  
<div class="rightToolbar">
    <div class="rightToolbarCrumbs">
      	<h2 class="sec-label"><s:text name="function.title"/></h2>
      	<ul class="bread-cut">
		  <li><s:text name="menu.title"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="function.title"/> </li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:property value="#parameters.resourceName"/> </li>
		</ul>
		<div class="btn-group fn-right" >
			<s:if test="#session.authenticater.authIdList.contains('04_01_01_00')">
				<button id="tab-title-1" style="padding-left:20px;color:#fff;" class="small-btn btn-blue" onclick="resource('<s:property value='#parameters.id'/>');">
					<span style="font-weight:600;"><s:text name="performance.tab.property" /></span>
	   			 </button>
			</s:if>
			<s:if test="#session.authenticater.authIdList.contains('04_01_03_00')">
				<button id="tab-title-2" style="padding-left:20px;color:#fff;" class="small-btn btn-grey" onclick="property('<s:property value='#parameters.id'/>');">
					<span style="font-weight:600;"><s:text name="performance.tab.view"/></span>
				</button>
			</s:if>
		</div>
    </div>
</div>
        
<div id="task1" style="margin-top:-2px;float:left;width: 100%;background: #fff;border-top: 2px solid #d8d8d8;">
 <!-- tab开始  -->
 <div class="btn-group" style="margin-top: 21px;">
     
 </div>
<!-- tab结束 -->
<!-- 折线图+表格开始 -->
<!-- 折线图+表格结束-->

</div>
<!-- task1结束 -->        
<!-- 性能视图 -->
<!--内容部分 main star-->
<div id="performanceProperty" style="overflow:auto;background-color: white;">
  <!--工作区 workarea start-->
    <!--选项卡 tab start-->
    <div class="btn-group">
    	<button id="oneWeek" class="small-btn btn-grey" style="padding-left:20px;" value="0" onclick="timeChange(this);"><s:text name="performance.period.oneWeek"/></button>
    	<button class="small-btn btn-eee" style="padding-left:20px;" value="1" onclick="timeChange(this);"><s:text name="performance.period.oneMonth"/></button>
    	<button class="small-btn btn-eee" style="padding-left:20px;" value="2" onclick="timeChange(this);"><s:text name="performance.period.threeMonth"/></button>
    </div>
 <!--报表开始-->
 	<table id="performancePropertyTable" style="width: 98%;height: 80%;margin: 5px;">
 	</table>
 <!--报表结束-->
 </div>
		 
<!--工作区 workarea end-->
<!--内容部分 main end-->
 <div class="messages succcess" style="top:25px">
     <div id="msgTip" class="msgSuccess"></div>
 </div>


<style>
<%-- 包含所有报表   	的div--%>
.reportdiv1 {width: 98%; height: 98%; margin-left: 5px;margin-top: 0px;}
</style>

<script type="text/JavaScript">
/**
 * 获取物理机状态
 */
function getState(){
	getObjState('<s:property value="#parameters.id"/>','<s:property value="#parameters.ip"/>','<s:property value="#parameters.clusterID"/>');
}
var unknown = "<s:text name="performance.message.stateValue" />";
var starting = "<s:text name="performance.state.starting" />";
var running = "<s:text name="performance.state.running" />";
var shutting = "<s:text name="performance.state.shutting" />";
var shutdown = "<s:text name="performance.state.shutdown" />";
var restarting = "<s:text name="performance.state.restarting" />";
var restartcomplete = "<s:text name="performance.state.restartcomplete" />";

function getObjState(id,ip,clusterID){
	$("#pmState").text('');
	$.ajax({
	           url: "searchPMState.action",
	           type: "POST",
	           data:{id:""+id,ip:""+ip,clusterID:""+clusterID},
	           success:function(pmState) {//这里的data是由请求页面返回的数据
				$("#pmState").text(pmState);
	           },
	           error: function(XMLHttpRequest, textStatus, errorThrown) { }
          });
	}
</script>

<script type="text/JavaScript">

function timeChange(obj) {
	var optionTime = obj.value;
	$(obj).siblings().removeClass().addClass("small-btn btn-eee");
	$(obj).removeClass().addClass("small-btn btn-grey");
	getTimeDate(optionTime,'<s:property value="#parameters.deviceIp"/>','<s:property value="#parameters.deviceType"/>','<s:property value="#parameters.typeId"/>');
}
function getTimeDate(optionTime,ip,deviceType,typeId) {
	$.ajax({
		url: "searchEncoderProperty.action?optionTime="+optionTime+"&deviceIp="+ip+"&typeId="+typeId,
        type: "POST",
        dataType: "text",
        success:function(propertyView) {//这里的data是由请求页面返回的数据
	    	var propertyViewData = eval("("+propertyView+")");
	    	//画线
	    	drawPropertyViewLine(propertyViewData);
        }
	});
}

function drawPropertyViewLine(propertyViewData){
	//首先遍历tab
	var lineFlag = false;
	for(var t = 0 ; t < result.length ; t++) {
		var seriesData = [];
		//定义一个临时数组
		var temp = propertyViewData;
		
		//遍历tab下的指标
		for (var one in tabIndexArray)
		{
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[one]["indexGroup"] && tabIndexArray[one]["ifShowLine"] == "1")
			{
				lineFlag = true;
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[one]["indexUnit"];
				var indexName = tabIndexArray[one]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii in temp)
				{
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
					if(tabIndexArray[one]["indexGroup"]==temp[ii]["mibGroup"]||temp[ii]["mibGroup"]==''){
						var yValue = temp[ii]["tagetValue"];
						if (isNaN(parseFloat(yValue))){
							indexData.push({
								x: temp[ii]["showTime"].time,
								y: null
							});
						} else {
							indexData.push({
								x: temp[ii]["showTime"].time,
								y: Math.round(parseFloat(yValue)*100)/100
							});
						}
					}
					
				}
				//将指标单位和指标名称一起拼到名称中，在折线图中备用
				seriesData.push({
						name: indexName+'('+indexUnit+')',
						data: indexData
				});
			}
		}
		//alert(JSON.stringify(seriesData));
		if(lineFlag){
			lineFlag = false;
			this['viewChart'+t] = new Charts.Chart({
				chart: {
					renderTo: result[t]+'view',
					defaultSeriesType: 'spline',
					backgroundColor: '#ffffff',
				},
				title: {
					text: ''
				},
				xAxis: {
					type: 'datetime',
					dateTimeLabelFormats: { // don't display the dummy year
						minute: '%m-%d %H:%M'
					}
				},
				yAxis: {
					title: {
						text: ''
					},
					labels: {
						formatter: function(){
							return (Math.abs(this.value));
						}
					},
					min: 0,
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+Charts.dateFormat('%m-%d %H:%M', this.x) +' '+ this.y;
					}
				},
				plotOptions: {
					spline: {
						marker: {
							enabled: false,
							symbol: 'circle',
							radius: 2,
							states: {
								hover: {
									enabled: true
								}
							}
						}
					}
				},
				series: seriesData
			});
			
		}
		//根据tab个数新建chart个数
	}
	$("#performancePropertyTable").find(".reportdiv1").each(function(){
		if($(this).html()=="" ||$(this).html()==null){
			$(this).parent().parent().remove();
		}
	});
	
}



$(function(){
	$("#performanceProperty").height($(window).height()-$(".rightToolbar").height()-14);
	$(window).resize(function() {
		var winHeight = $(window).height();
		$("#performanceProperty").height(winHeight -$(".rightToolbar").height()-14-10);
	});
	$("#performanceProperty").hide();
});

</script>

