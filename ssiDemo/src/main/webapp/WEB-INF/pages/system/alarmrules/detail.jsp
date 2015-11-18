<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="BasicInformation">
	<h2>
		<s:property value="alarmrules.agentName" />
	</h2>
	<span class="close"><a href="javascript:closeDetail();"><img
			src="<%=request.getContextPath()%>/themes/blue/images/close.png" /></a></span>
</div>
<div class="baseTitl click" style="width: 100%;cursor: default;text-decoration: none;">
	<s:text name="common.title.newDetil" />
</div>
<div class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><s:text name="alarmrules.typeID" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.agentName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmTitle" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmTitleContent" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmOID" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmOID" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmLevel" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmLevelName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmIdentityID" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmIdentityID" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmIndex" />
					<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmIndex" />" /></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmContent" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="alarmrules.alarmContent"/></textarea></th>
		</tr>
		<tr>
			<td><s:text name="alarmrules.alarmImpact" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="alarmrules.alarmImpact" />" /></th>
		</tr>
	</table>
</div>