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

var freshTime = "<s:text name="resource.jsp.freshTime" />";
var freshTime1 = 10000;
window.onload=function (){
	$("#performanceProperty").hide();

	//查询Tab页信息
	getTab();
};

//tab数组
var result = [];
function getTab() {
	$.ajax({
        url: "getAlreadyConfTab.action",
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
 		       			+'<div class="task1Table" style="overflow:auto;margin-top:20px;">'
 		       			+'<table  cellpadding="0"  cellspacing="0" class="grid grid-bordered">'
 		       			+'<thead><tr>'
 		       			+'<th  width="30%">'
 		       			+'<s:text name="performance.resource.tableName" /></th>'
 		       			+'<th width="30%">'
 		       			+'<s:text name="performance.resource.tableValue" /></th>'
 		       			+'<th width="40%">'
 		       			+'<s:text name="performance.resource.discribe"></s:text></th></tr></thead>'
 		       			+'<tbody class="stripe_tb"></tbody></table>'
 		       			+'</div></div>');
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
        url: "getAlreadyConfTabIndex.action",
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
function getAllData(){
	$.ajax({
	           url: "searchPMResource.action",
	           type: "POST",
	           data:{typeId:""+'<s:property value="#parameters.typeId"/>',deviceIp:""+'<s:property value="#parameters.deviceIp"/>'},
	           dataType: "text",
	           success:function(pmList) {
	        	 	//请求回来的json数据格式是什么样的？答：物理机对象集合(jsonArray格式)
	        	 	pmListJSONArray = eval("("+pmList+")");
	        	 	//第一轮全量数据获取完成后，绘制表格
	        	 	drawTable();
	        	 	//第一轮全量数据获取完成后，调用方法绘制曲线图
	        	 	drawLine();
	        	 	//图标绘制完成后，设置奇偶行样式
	        	 	jiouRowStyle();
	        	 	//当奇偶行样式设置完成后，设置表格自动高度
	        	 	setTableHeight();
	        	 	//所有数据处理结束后准备获取增加数据，为折线图和表格刷新做准备，5分钟获取一次
	        	 	setInterval(getIncreaseData,freshTime);
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
	for(var t = 0 ; t < result.length ; t++) {
		var chartzu = new Array();
		
		var seriesData = [];
		//定义一个临时数组
		var temp = [];
		temp = pmListJSONArray;
		//遍历tab下的指标
		for (var one in tabIndexArray)
		{
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[one]["indexGroup"] && tabIndexArray[one]["ifShowLine"] == "1")
			{
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[one]["indexUnit"];
				var indexName = tabIndexArray[one]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii in temp)
				{
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
					if (isNaN(parseFloat(temp[ii][tabIndexArray[one]["oid"]]))){
						indexData.push({
							x: temp[ii]["per_time"].time,
							y: null
						});
					} else {
						indexData.push({
							x: temp[ii]["per_time"].time,
							y: Math.round(parseFloat(temp[ii][tabIndexArray[one]["oid"]])*100)/100
						});
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
			series: seriesData
		});
	}
}


//5分钟自动刷新折线图和表格
function freshLineAndTable(){
	//遍历每一个chart中的series将折线图更新
	for (var bb = 0 ; bb < result.length; bb++) {
		for (var aa = 0 ; aa < eval('chart'+bb).series.length; aa++) {
			//获取当前折线图上第一个折线(带单位的折线图名称)
			var seriesNameUnit = eval('chart'+bb).series[aa].name;
			//用seriesNameUnit遍历tabIndexArray集合中匹配tab以及列名
			for (var one in tabIndexArray)
			{
				var nameandunit = tabIndexArray[one]["mibName"]+'('+tabIndexArray[one]["indexUnit"]+')';
				//在tabIndexArray中找到对应的series
				if (seriesNameUnit==nameandunit && tabIndexArray[one]["ifShowLine"] == "1")
				{
					//通过tab找到需要遍历的集合
					var indexGroupIncrease = tabIndexArray[one]["indexGroup"];
					//定义一个临时数组，用来承载增量数据
					var temp = [];
					temp = pmListJSONArrayIncrease;
					//通过colomeName遍历增量数据集合，将数据插入series的data中
					for (var ii in temp)
					{
						if (isNaN(parseFloat(temp[ii][tabIndexArray[one]["oid"]]))){
							//数据为空
							eval('chart'+bb).series[aa].addPoint([temp[ii]["per_time"].time, null], true, false);
						} else {
							eval('chart'+bb).series[aa].addPoint([temp[ii]["per_time"].time, Math.round(parseFloat(temp[ii][tabIndexArray[one]["oid"]])*100)/100], true, false);
						}
					}
					//if条件成立，在tabIndexArray中找到了 与 series中相符合的指标，结束tabIndexArray循环，查找series中的下一个指标
					break;
				}
			}
		}
	}
	//获取tbody中的所有tr行
	refreshTable();
};


function refreshTable(){
	$("#task1 table tbody tr").each(
		function(){
			var td = $(this).children();
			var td0_text = td.eq(0).text();
			var colomeName;
			var indexGroup;
			var indexUnit;
			//用td_text作为oid列(页面输入的OID字段，用来显示表格中的第一列)，用该列获取列名
			for (var one in tabIndexArray)
			{
				var oid = tabIndexArray[one]["oid"];
				if (td0_text==oid) {
					colomeName = tabIndexArray[one]["oid"];
					indexGroup = tabIndexArray[one]["indexGroup"];
					indexUnit = tabIndexArray[one]["indexUnit"];
					break;
				}
			}
			//用指标分组确定增量数据集合，用列名获取数据集合中的最后一条记录的字段值
			var tempTable = [];
			tempTable = pmListJSONArrayIncrease;
			//如果有增量数据则更新表格
			if (tempTable.length>0 && tempTable[tempTable.length-1][colomeName]!='') {
				var refrashIndexData = tempTable[tempTable.length-1][colomeName];
				//如果请求的表格数据是一个数，则对数据进行处理，保留小数点后两位小数
				if (!isNaN(refrashIndexData)) {
					refrashIndexData = Math.round(parseFloat(refrashIndexData)*100)/100;
				}
				var td1_text =  refrashIndexData + indexUnit;
				$("#"+colomeName).text(td1_text);
				//判断单位，是否需要更新进度条
				if (indexUnit=='%' && !isNaN(refrashIndexData)) {
					$("#"+colomeName+"Bar").progressbar('destroy');
					$("#"+colomeName+"Bar").progressbar({
			   			value: parseFloat(refrashIndexData)
			   		});
				}
			}
		}
	)
};

//绘制表格
function drawTable(){
	//首先遍历tab数组
	for(var t = 0 ; t < result.length ; t++) {
		//遍历每一个tab下的指标
		for (var one in tabIndexArray)
		{
			//定义一个临时数组
			var temp = [];
			temp = pmListJSONArray;
			//将该tab下的所有tr行拼到表格中
			if (result[t]==tabIndexArray[one]["indexGroup"])
			{
				//列名是定义好的一些有规律的字符串，不应显示到表格第一项，但是需要该字段取性能数据
				var colomeName = tabIndexArray[one]["oid"];
				var oid = tabIndexArray[one]["oid"];
				var indexData;
				var indexUnit = tabIndexArray[one]["indexUnit"];
				//采集到指标的集合可能不为空
				if (temp.length>3) {
					indexData = temp[temp.length-2][tabIndexArray[one]["oid"]];
					//若物理机的性能信息中该字段没有采集到数据
					if ('' == indexData || null == indexData || -1==indexData) {
						indexData = 'N/A';
						indexUnit = '';
					} else if (!isNaN(indexData)) {
						//indexData有数据，并且是一个数，则对数据进行处理，保留小数点后两位小数
						indexData = Math.round(parseFloat(indexData)*100)/100;
					}
				} else {
					//未采集到配置指标的数据
					indexData = 'N/A';
					indexUnit = '';
				}
				var mibName = tabIndexArray[one]["mibName"];

				//拼tr行
				$("#" +result[t]+ "Report .task1Table table tbody").append('<tr ><td style="padding-left:15px; border-right:1px solid #eee;"><strong>'+oid+'</strong></td><td style="padding-left:15px; border-right:1px solid #eee;"><div class="progressbarNum"><span id="'+colomeName+'">'+indexData+indexUnit+'</span></div><div id="'+colomeName+'Bar" class="progressbar"></div></td><td style="padding-left:15px; border-right:1px solid #eee;">'+mibName+'</td></tr>');
				//若该指标单位为百分比,则增加进度条
				if ('%' == indexUnit) {
					$("#"+colomeName+"Bar").progressbar({
			   			value: parseFloat(indexData)
			   		});
				}
			}
		}
	}
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
	
	$('tr').unbind('mouseenter mouseleave');
	$(".report").each(function(){
		$(this).find(" .stripe_tb tr:even").addClass("ou");
	});

}


function setTableHeight(){
	//list自动高度
	$(".task1Table").height($(window).height()-$(".reportdiv").height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-44);
	$(window).resize(function() {
		var winHeight = $(window).height();
		$(".task1Table").height(winHeight -$(".reportdiv").height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-44-10);
	});
}

//承载普通tab页增量数据的数组
var pmListJSONArrayIncrease = [];

//根据采集时间间隔在action中直接处理增量数据(此处获取最后时间带到后台失败)
function getIncreaseData(){
	$.ajax({
        url: "searchPMResourceIncrease.action",
        type: "POST",
        data:{ip:""+'<s:property value="#parameters.deviceIp"/>'},
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
				<button id="tab-title-1" style="padding-left:20px;color:#fff;" class="small-btn btn-blue" onclick="resource('<s:property value='#parameters.id'/>');" type="button">
					<span style="font-weight:600;"><s:text name="performance.tab.property" /></span>
	   			 </button>
			</s:if>
			<s:if test="#session.authenticater.authIdList.contains('04_01_03_00')">
				<button id="tab-title-2" style="padding-left:20px;color:#fff;" class="small-btn btn-grey" onclick="property('<s:property value='#parameters.id'/>');" type="button">
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
	getTimeDate(optionTime,'<s:property value="#parameters.deviceIp"/>','<s:property value="#parameters.deviceType"/>');
}
function getTimeDate(optionTime,ip,deviceType) {
	$.ajax({
		url: "searchPMProperty.action?optionTime="+optionTime+"&deviceIp="+ip,
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
	for(var t = 0 ; t < result.length ; t++) {
		var chartzu = new Array();
		var seriesData = [];
		//定义一个临时数组
		var temp = [];
		temp = propertyViewData;
		//遍历tab下的指标
		for (var one in tabIndexArray)
		{
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[one]["indexGroup"] && tabIndexArray[one]["ifShowLine"] == "1")
			{
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[one]["indexUnit"];
				var indexName = tabIndexArray[one]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii in temp)
				{
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
					if (isNaN(parseFloat(temp[ii][tabIndexArray[one]["oid"]]))){
						indexData.push({
							x: temp[ii]["per_time"].time,
							y: null
						});
					} else {
						indexData.push({
							x: temp[ii]["per_time"].time,
							y: Math.round(parseFloat(temp[ii][tabIndexArray[one]["oid"]])*100)/100
						});
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
		//根据tab个数新建chart个数
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

