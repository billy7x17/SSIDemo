<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="BasicInformation">
	<h2>
		<s:property value="operationLogInfo.userId" />
		-
		<s:property value="operationLogInfo.action" />
	</h2>
	<ul>
	</ul>
	<span class="close"><a href="javascript:closeDetail();"><img
			src="<%=request.getContextPath()%>/themes/blue/images/close.png" /></a></span>
</div>
<div class="baseTitl click" style="width: 100%;cursor: default; text-decoration: none;">
	<s:text name="common.title.newDetil" />
</div>
<div class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><s:text name="table.thead.logname" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="operationLogInfo.userId" />" /></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.username" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="operationLogInfo.userName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.ip" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="operationLogInfo.ip" />" /></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.time" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="operationLogInfo.dateTime" />" /></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.model" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="operationLogInfo.action" />" /></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.type" /> <s:text
					name="common.lable.point" /></td>
			<th><s:if test="operationLogInfo.opType=='Add'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.add" />" />
				</s:if> <s:if test="operationLogInfo.opType=='Update'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.edit" />" />
				</s:if> <s:if test="operationLogInfo.opType=='Del'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.delete" />" />
				</s:if> <s:if test="operationLogInfo.opType=='View'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.show" />" />
				</s:if> <s:if test="operationLogInfo.opType=='AddPrivilege'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.add" />" />
				</s:if> <s:if test="operationLogInfo.opType=='DelPrivilege'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.delete" />" />
				</s:if> <s:if test="operationLogInfo.opType=='UpdatePrivilege'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.edit" />" />
				</s:if> <s:if test="operationLogInfo.opType=='Login'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.login" />" />
				</s:if> <s:if test="operationLogInfo.opType=='Logout'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.logout" />" />
				</s:if> <s:if test="operationLogInfo.opType=='Other'">
					<input type="text" readonly="readonly"
						value="<s:text name="optype.other" />" />
				</s:if></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.description" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true"> <s:property value="operationLogInfo.operationInfo" /></textarea></th>
		</tr>
		<tr>
			<td><s:text name="table.thead.operation" /> <s:text
					name="common.lable.point" /></td>
			<th><a style="color: #428bca;"
				href="./relatedOperation.action?sessionId=<s:property value='operationLogInfo.sessionId'/>&userId=<s:property value='operationLogInfo.userId'/>&logId=<s:property value='logId'/>"><s:text
						name="table.thead.operation" /></a></th>
		</tr>
	</table>
</div>
<!-- 判断页面 是否当前Menu ,value="10"为[系统管理]-->
<input type="hidden" value="10" id="MenuIndexValue" />