<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="BasicInformation">
	<h2>
		<s:property value="site.siteName" />
	</h2>
	<span class="close"><a href="javascript:closeDetail();"><img
			src="<%=request.getContextPath()%>/themes/blue/images/close.png" /></a></span>
</div>
<div class="baseTitl click"
	style="width: 100%; cursor: default; text-decoration: none;">
	<s:text name="common.title.newDetil" />
</div>
<div class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><s:text name="site.table.siteName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="site.siteName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="site.table.lineName" /> <s:text
					name="common.lable.point" /></td>
			<th><s:if test="site.lineNum==1">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.lineName1" />" />
				</s:if> <s:elseif test="site.lineNum==2">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.lineName2" />" />
				</s:elseif></th>
		</tr>
		<tr>
			<td><s:text name="site.table.siteType" /> <s:text
					name="common.lable.point" /></td>
			<th><s:if test="site.siteType==1">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.typeName1" />" />
				</s:if> <s:elseif test="site.siteType==2">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.typeName2" />" />
				</s:elseif> <s:elseif test="site.siteType==3">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.typeName3" />" />
				</s:elseif> <s:elseif test="site.siteType==4">
					<input type="text" readonly="readonly"
						value="<s:text name="resource.site.typeName4" />" />
				</s:elseif></th>
		</tr>
		<tr>
			<td><s:text name="site.table.description" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly"
					name="deviceDomain.agentDesc" contenteditable="true"><s:property value="site.description" />
				</textarea></th>
		</tr>
		<tr>
			<td><s:text name="resource.site.moreInfo" /> <s:text
					name="common.lable.point" /></td>
			<th><a style="color: #428bca;cursor: pointer;" onclick="openNew('<s:property value='site.siteId'/>')"><s:text
						name="resource.site.moreInfo" /></a></th>
		</tr>
	</table>
</div>
<script>
function openNew(siteId){
	window.open('./moreInfo.action?siteId='+siteId,'newwindow','height=675,width=1500,top=100,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no'); 
}
</script>