<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Properties prop = new Properties();

	String realpath = request.getRealPath("/WEB-INF/classes/conf/other");  
	try{  
		//读取配置文件
		FileInputStream in = new FileInputStream(realpath+"/System.properties");  
		prop.load(in);  
	}  
	catch(FileNotFoundException e){  
	    out.println(e);  
	}  
	catch(IOException e){
		out.println(e);
	} 
%>
<form action="" method="post" name="mainForm" id="mainForm">
	<input type="hidden" id="agentId" name="agentId"
		value="<s:property value='agentId'/>" />
</form>
<!-- 排序列样式 -->
<style type="text/css">
.flexigrid tr td.sorted {
	background: #e3e3e3;
	border-right: 1px solid #ddd;
	border-bottom: 1px solid #f3f3f3
}
</style>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="menu.title" /> -><s:text
					name="function.title" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<s:if
				test="#session.authenticater.authIdList.contains('02_08_01_00')">
				<button class="small-btn btn-grey" onclick="add();" type="button">
					<span class="icon-add"></span><span><s:text
							name="common.title.add" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('02_08_05_00')">
				<button class="small-btn btn-grey" id="search" type="button">
					<span class="icon-search"></span><span><s:text
							name="common.title.search" /></span>
				</button>
			</s:if>
		</div>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess" style="display: none;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<div class="InsidePageButton"></div>
</div>
<!-- 导入 -->
<s:if test="#session.authenticater.authIdList.contains('02_08_01_00')">
	<table id="importTable">
		<tr>
			<th style="padding-top: 10px"><s:text name="import.title" /></th>
		</tr>
		<tr>
			<td valign="middle">
				<form id="batchinto_form" method="post"
					enctype="multipart/form-data">
					<div style="display: inline; float: left;">
						<input type="file" id="batchfile" name="deviceDomain.batchfile"
							contenteditable="false" onchange="checkExcel()">
					</div>
					<div style="float: left;">
						<ul class="singTable">
							<li><a href="#" class="pageButon1 pageButtonSearch"
								onclick="exportFile();"><s:text name="export.template" /></a></li>
							<li><a href="#" class="pageButon1 pageButtonSearch"
								onclick="importFile();"><s:text name="import.data" /></a></li>
						</ul>
					</div>
				</form>
			</td>
		</tr>
	</table>
</s:if>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
	url : 'deviceBaseList.action?domain.importDeviceFlag='+"<s:property value='domain.importDeviceFlag'/>", // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="device.localTableIDRef"/>', //第一列表头显示名称
		name : 't6.LocalTable_ID_Ref', // 字段名称
		width : 110,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="device.deviceName"/>', //第一列表头显示名称
		name : 't1.AGENT_NAME', // 字段名称
		width : 140,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="device.status"/>', //第二列表头显示名称….
		name : 't1.status',
		width : 60,
		sortable : false,
		align : 'left',
		process : getStatus
	}, {
		display : '<s:text name="device.agentGroup"/>', //第三列表头显示名称….
		name : 't2.AGENT_GROUP',
		width : 100,
		sortable : true,
		align : 'left',
		process : getAgentGroup
	}, {
		display : '<s:text name="device.typeName"/>', //第四列表头显示名称….
		name : 't2.AGENT_NAME',
		width : 120,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="device.siteName"/>', //第五列表头显示名称….
		name : 't3.site_name',
		width : 100,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="device.deviceIp"/>', //第六列表头显示名称….
		name : 't1.AGENT_IP',
		width : 100,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="device.deviceType"/>', //第七列表头显示名称……
		width : 100,
		name : 't1.device_type',
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="device.agentComp"/>', //第八列表头显示名称……
		name : 't1.AGENT_COMP',
		width : 100,
		sortable : true,
		align : 'left'
	}, {
        display : '<s:text name="common.title.operation"/>', //第九列表头显示名称……
        name : 'operation',
        width : 73,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
		
    }],
	customSearch : true,
    singleSelect:true,
	sortname : 't1.agent_Id', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
	procmsg: '<s:text name="common.flexigrid.procmsg"/>', //处理数据时显示内容
	onRowSelect: openview
	});

$(function(){
	//定时刷新列表
	setInterval('$("#flexigrid").flexReload()',<%=prop.getProperty("agentFrequency")%>*60*1000);
	
	//list自动高度,有添加权限的用户可以看到批量导入栏，需要重新算高度
	<s:if test="#session.authenticater.authIdList.contains('02_08_01_00')">
		$(".bDiv").height($(window).height()-$(".rightToolbar").height()-$(".hDiv").height()-$(".pDiv").height() - 18 - $("#importTable").height());
	</s:if>
	<s:else>
		$(".bDiv").height($(window).height()-$(".rightToolbar").height()-$(".hDiv").height()-$(".pDiv").height() - 18);
	</s:else>
	//显示提示消息
	var msg = "<s:property value='msg'/>";
	var errorMsg = "<s:property value='errorMsg'/>";
	if(msg != null && msg != ""){
		$("#msgTip").html(msg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}else if(errorMsg != null && errorMsg != ""){
		$("#msgTip").attr("class","msgErrors");
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}
	// 批量导入文件初始化方法
	<s:if test="#session.authenticater.authIdList.contains('02_08_01_00')">
		initFile();
	</s:if>
});

//添加
function add(){
	window.location="deviceBeforeAdd.action";
}

//导出模板
function exportFile(){
	$('#batchinto_form').attr("action","deviceExportTemplate.action");
    $('#batchinto_form').submit(); 
}

//导入数据
function importFile(){
	if (checkExcel()) {
		$('#batchinto_form').attr("action","deviceBatchinto.action");
	    $('#batchinto_form').submit();
	}
}

function checkExcel() {
    var elm = document.getElementById("batchfile").value;
    var elma = document.getElementById("batchfile");
    if(null != elm){
		var postfix = elm.substr(elm.lastIndexOf(".")+1).toLowerCase();
		if (postfix != "xls") {
			alert("<s:text name='file.format.error'/>");
			return false;
		}else{
			return true;
		}
    }else{
    	return true;
    }
}

function initFile(){
 	var file = document.getElementById("batchfile");
    file.onkeydown = function(){
        if(event.keyCode == 9){}
        else{
        	alert("<s:text name="filePath.input.error" />");
            file.focus();
            window.event.returnValue = false;
            return;
        }
    }

    file.oncontextmenu = function(){                   
        event.returnValue = false;
        return false;              
    }
    
    file.onpaste = function(){
    	alert("<s:text name="filePath.stick.error" />");
        file.focus();
        window.event.returnValue = false;
        return;
    }
    
    file.ondrag = function(){
        event.returnValue = false;
        return false;
    }
    
    file.ondragenter = function(){
        event.returnValue = false;
        return false;
    }       
}

//修改
function edit(agentId){     
	$("#agentId").val(agentId);
    $('#mainForm').attr("action","deviceBeforeUpdate.action");
    $('#mainForm').submit(); 
}

//删除
function del(agentId){
	var agentName = get_cell_text(agentId, 1);
    if(confirm('<s:text name="common.message.delConfig"/>')){
        $("#agentId").val(agentId);
        $('#mainForm').attr("action","deviceDelete.action?deviceDomain.agentName=" + encodeURIComponent(agentName));
        $('#mainForm').submit();
    }else{
        $('#mainForm').attr("action","deviceList.action");
        $('#mainForm').submit();
    }
}

function formatCustomerResults(data){
    var arr = data.rows;
    for(var i in arr){
        var rowArr = arr[i].cell;
        for(var j in rowArr){
            if(rowArr[j] == null || rowArr[j] == 'null') rowArr[j] = '';
        }
    }
    return data;
}

function openview(agentId){
	var agentName = get_cell_text(agentId, 1);
	viewDetail("deviceDetail.action?deviceDomain.agentId=" + agentId + "&deviceDomain.agentName=" + encodeURIComponent(agentName));
}

function rewriteDelLink(tdDiv, id){
	var divText = "";
	 <s:if test="#session.authenticater.authIdList.contains('02_08_02_00')">
	 	divText += '<a href="javascript:del(\''+id+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span> </a>';
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('02_08_03_00')">
	 	divText += '<a href="javascript:edit(\''+id+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 </s:if>
	 tdDiv.innerHTML = divText;
}

//读取设备状态
function getStatus(tdDiv, id){
    var status = tdDiv.innerHTML;
    if(status=='0')tdDiv.innerHTML = '<img src="themes/blue/images/icon_online.png" style="align:absbottom;margin-right: 5px; " /><s:text name="device.status.0" />';
    else if(status=='1')tdDiv.innerHTML = '<img src="themes/blue/images/icon_offline.png" style="align:absbottom;margin-right: 5px; " /><s:text name="device.status.1" />';
}

// 读取设备分组
function getAgentGroup(tdDiv, id){
    var agentGroup = tdDiv.innerHTML;
    if(agentGroup=='NVR')tdDiv.innerHTML = '<s:text name="device.group.NVR" />';
    else if(agentGroup=='IP-SAN')tdDiv.innerHTML = '<s:text name="device.group.IP-SAN" />';
    else if(agentGroup=='Encoder')tdDiv.innerHTML = '<s:text name="device.group.Encoder" />';
    else if(agentGroup=='IPC')tdDiv.innerHTML = '<s:text name="device.group.IPC" />';
    else if(agentGroup=='Switch')tdDiv.innerHTML = '<s:text name="device.group.Switch" />';
    else if(agentGroup=='VMS')tdDiv.innerHTML = '<s:text name="device.group.VMS" />';
    else if(agentGroup=='D4')tdDiv.innerHTML = '<s:text name="device.group.D4" />';
    else if(agentGroup=='Keyboard')tdDiv.innerHTML = '<s:text name="device.group.Keyboard" />';
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="device.deviceName" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="deviceName" name="domain.agentName" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="device.deviceIp" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="deviceIp" name="domain.agentIp" class="input" /></td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="device.agentGroup" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select
					list="deviceGroupList" listKey="agentGroup" listValue="groupName"
					name="domain.agentGroup" id="agentGroup" cssClass="newSelect"
					headerKey="" headerValue="%{getText('common.lable.select')}"></s:select></td>
			<s:if test="#session.userInfo.roleType == 1">
				<td class="tdLabel" align="right" valign="top"><strong><s:text
							name="device.siteName" /> <s:text name="common.lable.point" /></strong></td>
				<td class="tdInput" valign="top"><s:select list="siteList"
						listKey="siteId" listValue="siteName" name="domain.siteId"
						id="siteId" cssClass="newSelect" headerKey=""
						headerValue="%{getText('common.lable.select')}"></s:select></td>
			</s:if>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<ul>
					<li class="pageButon1 pageButtonSearch"
						onclick='javascript:$("#flexigrid").resetSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text
								name="common.button.reset" /></a></li>
					<li class="pageButonRed pageButtonSearch"
						onclick='javascript:$("#flexigrid").submitSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text
								name="common.button.submit" /></a></li>

				</ul>
			</td>
		</tr>
	</table>
</div>