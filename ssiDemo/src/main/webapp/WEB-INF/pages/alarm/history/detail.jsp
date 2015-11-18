<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<div class="BasicInformation">
	<h2>
		<s:property value="domain.agentName" />
	</h2>
	<span class="close"><a href="javascript:closeDetail();"><img
			src="<%=request.getContextPath()%>/themes/blue/images/close.png" /></a></span>
</div>
<div class="baseTitl click" style="width: 100%;cursor: default; text-decoration: none;">
	<s:text name="web.title.detail" />
</div>
<div class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><s:text name="field.label.alarmIdentify" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmIdentify" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmGrade" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmGrade" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.originalAlarmGrade" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.originalAlarmGrade" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmTitle" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmTitle" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmContent" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="domain.alarmContent"/></textarea></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmType" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmType" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.LocalTableIDRef" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.LocalTableIDRef" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.siteName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.siteName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmIP" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmIP" />" /></th>
		</tr> 
		<tr>
			<td><s:text name="web.table.columnName.alarmStatus" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.confirmStatus" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.confirmTime" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.confirmTime" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.confirmPerson" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.confirmPerson" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.confirmDesc" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="domain.confirmDesc"/></textarea></th>
		</tr>
		<tr>
			<td><s:text name='web.table.columnName.clearStatus' /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.clearStatus" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.clearTime" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.clearTime" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.clearPerson" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.clearPerson" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.clearDesc" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="domain.clearDesc"/></textarea></th>
		</tr>
		<tr>
			<td><s:text name="field.label.deviceTypeName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.deviceTypeName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.count" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.count" />" /></th>
		</tr>

		<tr>
			<td><s:text name="field.label.firstAlarmTime" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.firstAlarmTime" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.alarmTime" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.alarmTime" />" /></th>
		</tr>
	</table>
</div>
