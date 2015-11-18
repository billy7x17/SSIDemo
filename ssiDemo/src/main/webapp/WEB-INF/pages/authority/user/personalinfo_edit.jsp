<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
	<link href="./styles/style.css" rel="stylesheet" type="text/css" />
	<link href="./scripts/validate/style/validator.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<s:url value="/source/lib/jquery-1.3.2.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/scripts/validate/formValidator.js"/>"></script>

<script type="text/javascript" language="JavaScript1.2">
$(document).ready(function(){
	$.formValidator.initConfig({formid:"personal_edit_form",wideword:false});
	$("#userName").formValidator({onshow:"必填项",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空,请确认"});

	//打开时验证
	$.formValidator.pageIsValid("1");
});

function submitForm() {
	var formCheck = $.formValidator.pageIsValid("1");
	if (formCheck){
	document.getElementById('personal_edit_form').submit();
	}
};
</script>
</head>
<body id="secondBody">
	<!-- 消息开始 -->
	<s:if test="ActionMessages.size != 0 || ActionErrors.size != 0 || FieldErrors.size != 0 ">
		<div id="msgbox">
			<s:actionerror cssClass="error"/><s:fielderror cssClass="error"/><s:actionmessage cssClass="succeed"/>
			<span class="close">
			<img src="./images/ico_close.gif" style="cursor:hand" onClick="javaScript:document.getElementById('msgbox').style.display='none'"/>
			</span>
		</div>
	</s:if>
	<!-- 消息结束 -->
	<!--主体-->
	<form id='personal_edit_form' method="post"
		action="./personalEditSave.action?userId=<s:property value="userInfo.userId"/>">
		<div id="content">
			<table width="96%" border="0" align="center" cellpadding="0"
												cellspacing="4">
				<tr>
					<td colspan="3"><font color="red">*修改信息将在下次登录生效</font></td>
				</tr>
				<tr>
					<td width="20%">用户登录名</td>
	           		<td width="210"><s:property value="userInfo.userId"/></td>
					<td></td>
				</tr>
				<tr>
					<td><span class="red">*</span>用户姓名</td>
		            <td><input id="userName" name="userName" type="text" class="input200" value='<s:property value="userInfo.userName"/>'/></td>
		            <td><div id="userNameTip"></div></td>
				</tr>
				<tr>
					<td>性别</td>
		            <td>
			            <input type="radio" name="sex" value="1" <s:if test="userInfo.sex == 1">checked="checked"</s:if>/>男
			            <input type="radio" name="sex" value="2" <s:if test="userInfo.sex == 2">checked="checked"</s:if>/>女
			            <input type="radio" name="sex" value="3" <s:if test="userInfo.sex == 3">checked="checked"</s:if>/>未知
		            </td>
		            <td></td>
				</tr>
				<tr>
					<td>电子邮件</td>
		            <td><input name="email" type="text" class="input200" value='<s:property value="userInfo.email"/>'/></td>
		            <td></td>
				</tr>
				<tr>
		            <td>手机号码</td>
		            <td><input name="mobile" type="text" class="input200" value='<s:property value="userInfo.mobile"/>'/></td>
		            <td></td>
	          	</tr>
	          	<tr>
		            <td>办公电话</td>
		            <td><input name="phone" type="text" class="input200" value='<s:property value="userInfo.phone"/>'/></td>
		            <td></td>
		        </tr>
		        <tr>
		            <td>邮编</td>
		            <td><input name="zipcode" type="text" class="input200" value='<s:property value="userInfo.zipcode"/>'/></td>
		            <td></td>
		        </tr>
		        <tr>
		            <td>地址</td>
		            <td colspan="2"><textarea name="address" rows="3" class="input3"><s:property value="userInfo.address"/></textarea></td>
		        </tr>
		        <tr>
		            <td>用户描述</td>
		            <td colspan="2">
		            <textarea name="description" rows="3" class="input3"><s:property value="userInfo.description"/></textarea>
		            </td>
		        </tr>
			</table>
		</div>
	</form>
</body>
<!-- 
<div id="main" onkeypress="return CheckEnterKey(event);">
	<div id="body">
		<div id="content">
			<div id="c_middle">
				<form id='personal_edit_form' method="post" action="./personalEditSave.action?userId=<s:property value="userInfo.userId"/>">
					<table cellspacing="1">
							<tr>
							<td class="tHeader" width="13%">用户信息</td>
							<td class="tHeader">(<span class="noticeMsg">*</span>号为必填项)</td>
						</tr>
						<tr class="trBg0">
							<td>用户ID:</td>
							<td><s:property value="userInfo.userId"/></td>
						</tr>

						<tr class="trBg1">
							<td>用户名称:</td>
							<td><input class="ta" type="text" id="username" name="userName" size="34" maxlength="32" 
							    <s:if test="%{userName!=null}">value="<s:property value="userName"/>"</s:if><s:else>value="<s:property value="userInfo.userName"/>"</s:else>/></td>
						</tr>

						<tr class="trBg0">
							<td>手机:</td>
							<td><input class="ta" type="text" id="mobile" name="mobile" size="34" maxlength="14" 
							<s:if test="%{mobile!=null}">value="<s:property value="mobile"/>"</s:if><s:else>value="<s:property value="userInfo.mobile"/>"</s:else>/></td>
						</tr>
						<tr class="trBg1">
							<td>邮箱:</td>
							<td><input class="ta" type="text" id="email" name="email" size="34" maxlength="32" 
							<s:if test="%{email!=null}">value="<s:property value="email"/>"</s:if><s:else>value="<s:property value="userInfo.email"/>"</s:else>/></td>
						</tr>
						<tr class="trBg0">
							<td>地址:</td>
							<td><input class="ta" type="text" id="address" name="address" size="34" maxlength="32" 
							<s:if test="%{address!=null}">value="<s:property value="address"/>"</s:if><s:else>value="<s:property value="userInfo.address"/>"</s:else>/></td>
						</tr>
						<tr class="trBg1">
							<td>用户描述:</td>
							<td><textarea rows="4" cols="32" class="ta" id="userDesc" name="description"><s:if test="%{description!=null}"><s:property value="description"/></s:if><s:else><s:property value="userInfo.description" /></s:else></textarea></td>
						</tr>
                   </table>
				</form>
			</div>
		</div>
	</div>
 -->
</div>

