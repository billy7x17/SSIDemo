<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="BasicInformation">
	<h2>
		<s:property value="userInfo.userName" />
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
			<td><s:text name="web.table.columnName.userId" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.userId" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.userName" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.userName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.role" />
				<s:text name="common.lable.point" /></td>
			<th><s:iterator value="myRoleList" status="listIndex">
					<s:if test="#listIndex.index != myRoleList.size()-1">
						<input type="text" readonly="readonly"
							value="<s:property value="roleName" />" />
						<br />
					</s:if>
					<s:else>
						<input type="text" readonly="readonly"
							value="<s:property value="roleName" />" />
					</s:else>
				</s:iterator></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.siteName" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.siteName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.sex" />
				<s:text name="common.lable.point" /></td>
			<th><s:if test="userInfo.sex == 1">
					<input type="text" readonly="readonly"
						value="<s:text name="web.sex.male" />" />
				</s:if> <s:if test="userInfo.sex == 2">
					<input type="text" readonly="readonly"
						value="<s:text name="web.sex.female" />" />
				</s:if> <s:if test="userInfo.sex == 3">
					<input type="text" readonly="readonly"
						value="<s:text name="web.sex.unknown" />" />
				</s:if></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.email" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.email" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.mobile" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.mobile" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.personnelId" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.personnelId" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.createTime" />
				<s:text name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="userInfo.creatTime" />" /></th>
		</tr>
		<tr>
			<td><s:text name="web.table.columnName.description" />
				<s:text name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true"><s:property value="userInfo.description" /></textarea></th>
		</tr>
	</table>
</div>

<!-- 判断页面 是否当前Menu ,value="10"为[系统管理]-->
<input type="hidden" value="10" id="MenuIndexValue" />
