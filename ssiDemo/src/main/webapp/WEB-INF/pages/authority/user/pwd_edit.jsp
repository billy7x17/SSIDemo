<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="<s:url value="/scripts/lib/tree/checktree.js"/>"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript" language="JavaScript1.2">
	function cc() {
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck) {
			document.getElementById('psw_edit_Form').submit();
		}
	}
	$(document).ready(function() {
		$.formValidator.initConfig({
			formid : "mainForm",
			wideword : false
		});
		$("#oldPassword").formValidator({
			onshow : "<s:text name='web.validate.pwd'/>",
			onfocus : "<s:text name='web.validate.pwd'/>",
			oncorrect : "<s:text name='web.validate.ok'/>"
		}).regexValidator({
			regexp : "^[a-zA-Z0-9]+$",
			onerror : "<s:text name='web.validate.password.regex'/>"
		}).inputValidator({
			min : 1,
			onerror : "<s:text name='web.validate.pwd'/>"
		});
		$("#password").formValidator({
			onshow : "<s:text name='web.validate.pwd'/>",
			onfocus : "<s:text name='web.validate.pwd'/>",
			oncorrect : "<s:text name='web.validate.ok'/>"
		}).regexValidator({
			regexp : "^[a-zA-Z0-9]+$",
			onerror : "<s:text name='web.validate.password.regex'/>"
		}).inputValidator({
			min : 1,
			max : 16,
			onerror : "<s:text name='web.validate.pwd'/>"
		});
		$("#password2").formValidator({
			onshow : "<s:text name='web.validate.pwd'/>",
			onfocus : "<s:text name='web.validate.pwd'/>",
			oncorrect : "<s:text name='web.validate.ok'/>"
		}).inputValidator({
			min : 1,
			max : 16,
			onerror : "<s:text name='web.validate.pwd'/>"
		}).compareValidator({
			desid : "password",
			operateor : "=",
			onerror : "<s:text name='web.validate.pwdConfirm.fail'/>"
		});

		var errorMsg = "<s:property value='errorMsg'/>";
		if (errorMsg != null && errorMsg != "") {
			$("#msgTip").attr("class", "msgErrors");
			$("#msgTip").html(errorMsg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		}

		$("tr").each(function() {
			var tr_bgColor = $(this).css("background-color");
			$(this).hover(function() {
				$(this).css("background-color", "transparent");
			}, function() {
				$(this).css("background-color", "transparent");
			})
		})
	});
	function validate() {
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck) {
			$("#mainForm").submit();
		} else {
			return false;
		}
	}
</script>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="common.system.tab.name" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="function.title" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="button.pwdchange"></s:text>
		</ul>
	</div>
</div>
<!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
		<div class="formLabel">
			<s:text name="common.lable.baseInfo" />
			<s:text name="common.lable.line" />
		</div>
		<form id="mainForm" method="post"
			action="./pswEditSave.action?userId=<s:property value="userInfo.userId"/>&userName=<s:property value="userInfo.userName"/>">
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="web.table.columnName.userId" /><s:text
								name="common.lable.point" /></td>
					<td class="tdInput" valign="top" style="color: #666"><s:property
							value="userInfo.userId" /></td>
					<td class="tdVad" valign="top"><div id="userIdTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="web.table.columnName.userName" /><s:text
								name="common.lable.point" /></td>
					<td class="tdInput" valign="top" style="color: #666"><s:property
							value="userInfo.userName" /></td>
					<td class="tdVad" valign="top"><div id="userNameTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
								name="common.red.point" /><s:text
							name="web.table.columnName.password.old" /><s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="password"
						autocomplete="off" id="oldPassword" name="password" size="34"
						maxlength="64" class="input"
						value="<s:property value="password"/>" /></td>
					<td class="tdVad" valign="top"><div id="oldPasswordTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
								name="common.red.point" /><s:text
							name="web.table.columnName.password.new" /><s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="password"
						name="newPassword" type="password" class="input" maxlength="16"
						value='<s:property value="newPassword"/>' /></td>
					<td class="tdVad" valign="top"><div id="passwordTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
								name="common.red.point" /><s:text
							name="web.table.columnName.password.new.confirm" /><s:text
								name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="password2"
						name="confPasswd" class="input" type="password" maxlength="16"
						value='<s:property value="confPasswd"/>' /></td>
					<td class="tdVad" valign="top"><div id="password2Tip"></div></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 提示信息start -->
	<div class="messages succcess" style="top: 25px; left: 37%;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<!-- 提示信息end-->
	<div class="serverEventButton">
		<ul class="rightToolButtonjhjFwSj">
			<li><a href="#" class="buttonFwSj"
				onclick="validate();return false;"><s:property
						value="getText('common.button.edit')" /></a></li>
			<li><a href="userBrowser.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>
</div>