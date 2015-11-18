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

var freshTime = "";//定时刷新
var typeId = "";//类型值
var fanStatusOid='<s:text name="ipsan.fan.status.oid"/>';
var diskStatusOid='<s:text name="ipsan.disk.status.oid"/>';
var ipsanRaidStatusOid='<s:text name="ipsan.raid.status.oid"/>';
var ipsanPDiskStatusOid='<s:text name="ipsan.pdisk.status.oid"/>';
var raidStatusOid='<s:text name="nvr.raid.status.oid"/>';

var fanstatus0='<s:text name="ipsan.fan.status0"/>';
var fanstatus1='<s:text name="ipsan.fan.status1"/>';
var fanstatus2='<s:text name="ipsan.fan.status2"/>';
var fanstatus3='<s:text name="ipsan.fan.status3"/>';
var fanstatus4='<s:text name="ipsan.fan.status4"/>';
var fanstatus5='<s:text name="ipsan.fan.status5"/>';
var fanstatus6='<s:text name="ipsan.fan.status6"/>';
var fanstatus7='<s:text name="ipsan.fan.status7"/>';

var diskStatus0='<s:text name="ipsan.disk.status0"/>';
var diskStatu1='<s:text name="ipsan.disk.status1"/>';


var ipsanRaidStatusOid0='<s:text name="ipsan.raid.status0"/>';
var ipsanRaidStatusOid1='<s:text name="ipsan.raid.status1"/>';

var ipsanPDiskStatusOid0='<s:text name="ipsan.pdisk.status0"/>';
var ipsanPDiskStatusOid1='<s:text name="ipsan.pdisk.status1"/>';
var ipsanPDiskStatusOid2='<s:text name="ipsan.pdisk.status2"/>';
var ipsanPDiskStatusOid3='<s:text name="ipsan.pdisk.status3"/>';
var ipsanPDiskStatusOid4='<s:text name="ipsan.pdisk.status4"/>';
var ipsanPDiskStatusOid5='<s:text name="ipsan.pdisk.status5"/>';

var raidStatus1='<s:text name="nvr.raid.status1"/>';
var raidStatus2='<s:text name="nvr.raid.status2"/>';
var raidStatus3='<s:text name="nvr.raid.status3"/>';
var raidStatus4='<s:text name="nvr.raid.status4"/>';
var raidStatus5='<s:text name="nvr.raid.status5"/>';
var raidStatus6='<s:text name="nvr.raid.status6"/>';
window.onload=function (){
	//查询Tab页信息
	freshTime ='<s:property value="freshTime" />';
	typeId ='<s:property value="#parameters.typeId"/>';
	getTab();
};

//tab数组
var result = [];
function getTab() {
	$.ajax({
        url: "getAlreadyConfTabIPSAN.action",
        type: "POST",
        data:{deviceIp:""+'<s:property value="#parameters.deviceIp"/>',typeId:'<s:property value="#parameters.typeId"/>'},
        dataType: "text",
        success:function(tab) {//请求Tab页信息
            result = tab.split(';');
			for(var i = 0 ; i < result.length ; i++) {
				//拼所有Tab
				if (i == 0) {
					//默认选中第一个Tab
	        		$("#task1 .btn-group").append('<button id="'+result[i]+'ReportButton" style="padding-left:20px;" '
	        			+'class="small-btn btn-grey" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;"><span>'+result[i]+'</span></button>');
				}else {
					//其它Tab不选中
	        		$("#task1 .btn-group").append('<button id="'+result[i]+'ReportButton" style="padding-left:20px;" '
	        		+'class="small-btn btn-eee" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;"><span>'+result[i]+'</span></button>');
				}
				//拼所有折线图和表格的外框
 		       	$("#task1").append('<div id="'+result[i]+'Report" class="report">'
 		       			+'<div class="task1Table"  style="width:100%;float:left;">'
 		       			+'<table  cellpadding="0"  cellspacing="0" class="grid grid-bordered"  > '
 		       			+'<thead><tr>'
 		       			+'<th width="25%"><s:text name="ipsan.resource.indexnumber" /></th>'
 		       			+'<th width="30%"><s:text name="ipsan.resource.tablename" /></th>'
 		       			+'<th width="40%"><s:text name="ipsan.resource.tableValue" ></s:text></th>'
 		       			+'</tr></thead></table></div> '
 		       			+'<div class="task1TableDiv" style="width:100%;overflow:auto;float:left;">'
 		       			+'<table  id="tableScoll" class="grid grid-bordered" style="margin-top:0px;border-top:0px;"><tbody class="stripe_tb"></tbody> </table></div>'
 		       			+'</div>');
				//默认展示第一个Tab下的div
				if (i==0) {
					$("#"+result[0]+"Report").show();
					lastClick = result[0]+"Report";
				}
 			}
			//tab页信息请求返回成功，并且拼好html后，请求tab下具体指标信息
			getTabIndex();
        }
    });
}
//所有指标的JSONArray
var tabIndexArray;
function getTabIndex(){
	$.ajax({
        url: "getAlreadyConfTabIndexIPSAN.action",
        type: "POST",
        data:{deviceIp:""+'<s:property value="#parameters.deviceIp"/>',typeId:'<s:property value="#parameters.typeId"/>'},
        dataType: "json",
        success:function(tabIndex) {//请求Tab页信息
       		tabIndexArray =eval(tabIndex);
           	drawTable();
           	//图标绘制完成后，设置奇偶行样式
       	 	jiouRowStyle();
       	 	//当奇偶行样式设置完成后，设置表格自动高度
       	 	setTableHeight();
       	 	
       	 	setInterval(getIncreaseData,freshTime);
        
        }
    });
}



//绘制表格
function drawTable(){
	//首先遍历tab数组
	for(var t = 0 ; t < result.length ; t++) {
		//遍历每一个tab下的指标
		for (var one in tabIndexArray)
		{
			
			//将该tab下的所有tr行拼到表格中
			if (result[t]==tabIndexArray[one]["mibGroup"])
			{
				//列名是定义好的一些有规律的字符串，不应显示到表格第一项，但是需要该字段取性能数据
				var indexNumber = tabIndexArray[one]["indexNumber"];
				var mibName = tabIndexArray[one]["mibName"];
				var tagetValue = tabIndexArray[one]["tagetValue"];
				var unit = tabIndexArray[one]["mibUnit"];
				var oid = tabIndexArray[one]["oid"];
				
				//设备类型值是33风扇和逻辑盘状态值标识方法
				if(typeId=='33'){
					if(oid==fanStatusOid){
						if(tagetValue=='0'){
							tagetValue = fanstatus0;
						}else if(tagetValue=='1'){
							tagetValue = fanstatus1;
						}else if(tagetValue=='2'){
							tagetValue = fanstatus2;
						}else if(tagetValue=='3'){
							tagetValue = fanstatus3;
						}else if(tagetValue=='4'){
							tagetValue = fanstatus4;
						}else if(tagetValue=='5'){
							tagetValue = fanstatus5;
						}else if(tagetValue=='6'){
							tagetValue = fanstatus6;
						}else if(tagetValue=='7'){
							tagetValue = fanstatus7;
						}
					}else if(oid==diskStatusOid){
						if(tagetValue=='0'){
							tagetValue = diskStatus0;
						}else if(tagetValue=='1'){
							tagetValue = diskStatu1;
						}
					}else if(oid==ipsanRaidStatusOid){
						if(tagetValue=='0'){
							tagetValue = ipsanRaidStatusOid0;
						}else if(tagetValue=='1'){
							tagetValue = ipsanRaidStatusOid1;
						}
					}else if(oid==ipsanPDiskStatusOid){
						if(tagetValue=='0'){
							tagetValue = ipsanPDiskStatusOid0;
						}else if(tagetValue=='1'){
							tagetValue = ipsanPDiskStatusOid1;
						}else if(tagetValue=='2'){
							tagetValue = ipsanPDiskStatusOid2;
						}else if(tagetValue=='3'){
							tagetValue = ipsanPDiskStatusOid3;
						}else if(tagetValue=='4'){
							tagetValue = ipsanPDiskStatusOid4;
						}else if(tagetValue=='5'){
							tagetValue = ipsanPDiskStatusOid5;
						}
					}else{
						//拼tr行
						if(unit=="Byte" || unit=='byte'){
							tagetValue = Math.round((tagetValue/1024/1024))+" MB";
						}else{
							tagetValue = tagetValue +" "+unit;
						}
					}
				}else if(typeId=='42' && oid==raidStatusOid){//设备类型是42的raid状态
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
					//拼tr行
					if(unit=="Byte" || unit=='byte'){
						tagetValue = Math.round((tagetValue/1024/1024))+" MB";
					}else{
						tagetValue = tagetValue +" "+unit;
					}
				}
				
	$("#" + result[t] + "Report .task1TableDiv table ")
							.append(
									'<tr ><td style="padding-left:15px; border-right:1px solid #eee;width:25%;">'
											+ indexNumber
											+ '</td><td style="padding-left:15px; border-right:1px solid #eee;width:30%;">'
											+ ''+mibName+'</td><td style="padding-left:15px; border-right:1px solid #eee;width:40%;">'
											+ tagetValue +'</td></tr>');
				}
			}
		}
	}

function jiouRowStyle() {
	//加载数据
	$("tr").each(function() {
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function() {
			$(this).css("background-color", "transparent");
		}, function() {
			$(this).css("background-color", "transparent");
		})
	});
	$("table").width(pageWidth);
	//设置奇偶行样式
	$(".report").each(function(){
		$(this).find(".stripe_tb  tr:even").addClass("ou");
	});
	
	$('tr').unbind('mouseenter mouseleave');
}

function setTableHeight() {
	//list自动高度

	$(".task1TableDiv").height($(window).height()-$(".rightToolbar").height()-($(".btn-group").height()+21)-57-14);
	$(window).resize(function() {
		var winHeight = $(window).height();
		$(".task1TableDiv").height(winHeight-$(".rightToolbar").height()-($(".btn-group").height()+21)-14 -57- 10);
	});
}

	//根据采集时间间隔在action中直接处理增量数据(此处获取最后时间带到后台失败)
function getIncreaseData() {
	refreshTable();
	getTabIndex();
}	
function refreshTable(){
	$(".task1TableDiv table tbody").empty();
};
	
</script>

<style type="text/css">
<%-- golbal.css中的内容如下 ：缺少一个overflow:hidden--%>
div{ 
	overflow:visible;
    height:auto;
}
<%-- 表格中	第二列数据	的div--%>
.progressbarNum {
 float: left;
 width: 100px;
}
<%-- 表格中	第二列进度条		的div--%>
.progressbar{ 
	margin-left: 2px; 
	margin-top: 4px;
	display: true ; 
	width:80px; 
	height:10px;
	float: left;
}
<%-- 包含所有报表   	的div--%>
.report {
	display: none;
	width: 100%;
}
.reportdiv {
	margin-left: 10px ;
    margin-top: 2px;
    width: 98%; 
}


.stripe_tb tr.ou {
	background:#f8f8f8;
} /*这行将给所有偶数行加上背景色*/

</style>

<script type="text/JavaScript">

var lastClick;
<%--控制 div 显示与隐藏属性的 切换--%>
function change(r) {
	$(".report").hide();
	$("#"+r).show();
	$('#'+lastClick+'Button').removeClass().addClass('small-btn btn-eee');
	$('#'+r+'Button').removeClass().addClass("small-btn btn-grey");
	lastClick = r;
}


</script>

<style type="text/css">
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
    </div>
</div>
        
<div id="task1" style="margin-top:-2px;float:left;width: 100%;background: #fff;border-top: 2px solid #d8d8d8;">
	<div class="btn-group" style="margin-top: 21px;">
	</div>   		
</div>
        

<div class="messages succcess" style="top:25px">
    <div id="msgTip" class="msgSuccess"></div>
</div>

