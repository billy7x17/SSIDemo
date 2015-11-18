<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/JQuery-zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/styles/nodeCss.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/Flexigrid/demo/style.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/Flexigrid/css/flexigrid.css"
	type="text/css">
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.core-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.excheck-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.exedit-3.0.js"></script>

<script type="text/JavaScript">

$(function(){
	<s:if test="#session.authenticater.authIdList.contains('08_02_00_00')">
	    $("#roleDiv").click();
	</s:if>
	
	<s:elseif test="#session.authenticater.authIdList.contains('08_03_00_00')">
	    $("#userDiv").click();
	</s:elseif>
	
	<s:elseif test="#session.authenticater.authIdList.contains('08_08_00_00')">
	 $("#logDiv").click();
	</s:elseif>
	
	<s:elseif test="#session.authenticater.authIdList.contains('08_10_00_00')">
	    $("#systemParameterDiv").click();
	</s:elseif>
});

function roleBrowser(){
     var url = "<%=request.getContextPath()%>/roleBrowser.action";
   $("#homePage").attr("src",url);
}

function userBrowser(){
     var url = "<%=request.getContextPath()%>/userBrowser.action";
    $("#homePage").attr("src",url);
}

function logBrowse(){
	var url = "<%=request.getContextPath()%>/logBrowse.action";
    $("#homePage").attr("src",url);
}

function systemparameterList(){
     var url = "<%=request.getContextPath()%>/systemparameterList.action";
    $("#homePage").attr("src",url);
}

</script>
<!--左边内容 left_side start-->
<div class="left_side">
	<div class="leftAcd">
		<iamp-identify:IAMPIdentify authid="08_02_00_00">
			<div class="leftAcdTit" id="roleDiv"
				onclick="roleBrowser();return false;">
				<a href="roleBrowser.action" target="framePage">角色管理</a><span
					class="leftAcdArr" id="roleSpan"></span>
			</div>
			<span class="leftTre"></span>
		</iamp-identify:IAMPIdentify>
		<iamp-identify:IAMPIdentify authid="08_03_00_00">
			<div class="leftAcdTit" id="userDiv"
				onclick="userBrowser();return false;">
				<a href="userBrowser.action" target="framePage">用户管理</a><span
					class="leftAcdArr" id="userSpan"></span>
			</div>
			<span class="leftTre"></span>
		</iamp-identify:IAMPIdentify>
		<iamp-identify:IAMPIdentify authid="08_08_00_00">
			<div class="leftAcdTit" id="logDiv"
				onclick="logBrowse();return false;">
				<a href="logBrowse.action" target="framePage">日志管理</a><span
					class="leftAcdArr" id="logSpan"></span>
			</div>
			<span class="leftTre"></span>
		</iamp-identify:IAMPIdentify>
		<iamp-identify:IAMPIdentify authid="08_10_00_00">
			<div class="leftAcdTit" id="systemParameterDiv"
				onclick="systemparameterList();return false;">
				<a href=".action" target="framePage">系统参数管理</a><span
					id="systemParameterSpan" class="leftAcdArr"></span>
			</div>
			<span class="leftTre"></span>
		</iamp-identify:IAMPIdentify>
	</div>
	<!--手风琴 leftAcd end-->
</div>
<!--左边内容 left_side end-->

<div id="mainTest" class="mainTest">
	<iframe id="homePage" name="framePage" src="" class="workarea"
		frameborder="0"> </iframe>
</div>