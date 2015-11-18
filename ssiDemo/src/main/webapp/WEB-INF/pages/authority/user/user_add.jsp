<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
	/*角色与所属站点联动*/
	function siteChange() {
		var roleId = '';

		$("#roleList option:selected").each(function() {
			roleId = $(this).val();
		});

		$.ajax({
			async : true,
			type : "POST",
			url : "getRoleType.action",
			data : {
				'roleId' : roleId
			},
			dataType : "json",
			success : function(c) {
				/*将'控制中心'从下拉框中拿出来放入input中*/
				var v = $("#site option[value=1]").html();
				if (v) {
					$("#site2").val(v);
					$("#siteHidden").val(1);
					$("#site option[value=1]").remove();
				}

				/*中心管理员    站点只显示控制中心*/
				if (1 == c) {
					$("#site").css("display", "none");
					$("#site2").css("display", "block");
					$("#siteTip").css("display", "none");
				}
				/*站点管理员    站点显示除了控制中心*/
				else {
					$("#site").css("display", "block");
					$("#site2").css("display", "none");
					$("#siteTip").css("display", "block");
				}
			}
		});
	}

	$(document)
			.ready(
					function() {
						$.formValidator.initConfig({
							formid : "mainForm",
							wideword : false
						});
						$("#userId1")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.userId'/>",
											onfocus : "<s:text name='web.validate.userId'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.inputValidator(
										{
											min : 1,
											max : 64,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.userId'/>",
											onerrormax : "<s:text name='web.validate.userId.max'/>"
										})
								.regexValidator(
										{
											regexp : "^[a-zA-Z]+[a-zA-Z0-9_]*$",
											onerror : "<s:text name='web.validate.userId.regex'/>"
										});
						$("#password")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.password'/>",
											onfocus : "<s:text name='web.validate.password'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.inputValidator(
										{
											min : 1,
											max : 16,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.password.null'/>"
										})
								.regexValidator(
										{
											regexp : "^[a-zA-Z0-9]+$",
											onerror : "<s:text name='web.validate.password.regex'/>"
										});
						$("#password2")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.pwdConfirm'/>",
											onfocus : "<s:text name='web.validate.pwdConfirm'/>",
											oncorrect : "<s:text name='web.validate.pwdConfirm.ok'/>"
										})
								.inputValidator(
										{
											min : 1,
											max : 16,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.password'/>"
										})
								.compareValidator(
										{
											desid : "password",
											operateor : "=",
											onerror : "<s:text name='web.validate.pwdConfirm.fail'/>"
										});
						$("#userName")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.userName'/>",
											onfocus : "<s:text name='web.validate.userName'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.inputValidator(
										{
											min : 1,
											max : 5,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.userName.null'/>",
											onerrormax : "<s:text name='web.validate.userName.max'/>"
										});
						$("#personnelId")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.personnelId'/>",
											onfocus : "<s:text name='web.validate.personnelId'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.regexValidator(
										{
											regexp : "^([a-zA-Z0-9]+)*$",
											onerror : "<s:text name='web.validate.personnelId.regex'/>"
										})
								.inputValidator(
										{
											min : 0,
											max : 8,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.personnelId.null'/>",
											onerrormax : "<s:text name='web.validate.personnelId.max'/>"
										});
						$("#email")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.mail'/>",
											onfocus : "<s:text name='web.validate.mail'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.regexValidator(
										{
											regexp : "^([a-zA-Z0-9_.-]+[@]{1}[a-zA-Z0-9_.-]+[.]{1}[a-zA-Z0-9_-]+)*$",
											onerror : "<s:text name='web.validate.mail.regex'/>"
										})
								.inputValidator(
										{
											min : 3,
											max : 64,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.mail.null'/>",
											onerrormax : "<s:text name='web.validate.mail.max'/>"
										})
								.ajaxValidator({
										type: "post",
										url:  "EmailVerify.action",
										dataType: "json",
										data: document.getElementById("email").value,
										success: function(count){
											if (count == '0') {
												return true;
											}else{
												return false;
											}
										},
										onerror: "<s:text name='web.validate.mail.hasBeenUsed' />",
										onwait: "<s:text name='web.validate.mail.validating' />"
										});
						$("#mobile")
								.formValidator(
										{
											onshow : "<s:text name='web.validate.mobile'/>",
											onfocus : "<s:text name='web.validate.mobile'/>",
											oncorrect : "<s:text name='web.validate.ok'/>"
										})
								.inputValidator(
										{
											min : 1,
											max : 11,
											empty : {
												leftempty : false,
												rightempty : false,
												emptyerror : "<s:text name='web.validate.trim'/>"
											},
											onerror : "<s:text name='web.validate.mobile.null'/>",
											onerrormax : "<s:text name='web.validate.mobile.max'/>"
										})
								.regexValidator(
										{
											regexp : "^1[3|5|7|8|][0-9]{9}$",
											onerror : "<s:text name='web.validate.mobile.regex'/>"
										});

						$("#description").formValidator({
							onshow : "<s:text name='web.validate.desc'/>",
							onfocus : "<s:text name='web.validate.desc'/>",
							oncorrect : "<s:text name='web.validate.ok'/>"
						}).inputValidator({
							max : 256,
							onerror : "<s:text name='web.validate.desc.max'/>"
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
						});

						/*将readonly的标签赋值*/
						var v = $("#site option[value=1]").html();
						$("#site2").val(v);
						$("#siteHidden").val(1);

						/*站点标签下拉框校验       控件模拟*/
						$("#site")
								.focus(
										function() {
											$("#siteTip").attr("class",
													"onShow");
											$("#siteTip")
													.html(
															"<s:text name='web.validate.site'/>");
										});
						$("#site")
								.blur(
										function() {
											if ($(this).val()) {
												$("#siteTip").attr("class",
														"onCorrect");
												$("#siteTip")
														.html(
																"<s:text name='web.validate.ok'/>");
											} else {
												$("#siteTip").attr("class",
														"onError");
												$("#siteTip")
														.html(
																"<s:text name='web.validate.site.null'/>");
											}
										});
						$("#site")
								.change(
										function() {
											if ($(this).val()) {
												$("#siteTip").attr("class",
														"onCorrect");
												$("#siteTip")
														.html(
																"<s:text name='web.validate.ok'/>");
											} else {
												$("#siteTip").attr("class",
														"onError");
												$("#siteTip")
														.html(
																"<s:text name='web.validate.site.null'/>");
											}
										});
						
						var sessionRoleType = <s:property value="#session.userInfo.roleType"/>;

						/*当前角色为站点管理员*/
						if (2 === sessionRoleType) {
							/*角色*/
							$('#role2').val('<s:property value="sessionRole.roleName" escape="false"/>');
							$('#role2').attr('title','<s:text name="web.title.roleOnly"/>');
							$('#roleList').css('display', 'none');
							$('#role2').css('display', 'block');
							$('#roleHidden').val('<s:property value="sessionRole.roleId" escape="false"/>');
							
							/*站点*/
							$('#site2').val('<s:property value="siteName" escape="false"/>');
							$('#site2').attr('title','<s:text name="web.title.siteOnly"/>');
							$('#site').css('display', 'none');
							$('#site2').css('display', 'block');
							$('#siteHidden').val('<s:property value="#session.userInfo.siteId" escape="false"/>');
						}else {
							$('#roleList')
							.formValidator(
									{
										onshow : "<s:text name='web.validate.role'/>",
										onfocus : "<s:text name='web.validate.role'/>",
										onerror : "<s:text name='web.validate.role.null'/>"
									})
							.inputValidator(
									{
										min : 1,
										max : 64,
										empty : {
											leftempty : false,
											rightempty : false,
											emptyerror : "<s:text name='web.validate.role.null'/>"
										},
										onerrormin : "<s:text name='web.validate.role.null'/>",
										onerrormax : "<s:text name='web.validate.role.null'/>"
									});
						}

					});

	function validate() {
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck && siteValidate()) {
			$("#mainForm").submit();
		} else {
			return false;
		}
	}

	/*表单提交时另外校验所属站点*/
	function siteValidate() {
		/*如果下拉框没显示，则无需交验*/
		if ($('#site').css('display') != "none") {
			if ($("#site option:selected").val()) {

			} else {
				/*显示错误信息*/
				$("#siteTip").attr("class", "onError");
				$("#siteTip").html("<s:text name='web.validate.site'/>");
				return false;
			}
		}
		return true;
	}

	function validateTextarea() {
		if ($("#description").val().length > 256) {
			$("#description").val($("#description").val().substring(0, 256));
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
			<li><s:text name="common.title.add"></s:text>
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
		<form id="mainForm" method="post" action="./userAddSave.action">
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.userId" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="userId1"
						name="userInfo.userId" maxlength="64" type="text" class="input"
						value='<s:property value="userInfo.userId"/>' /></td>
					<td class="tdVad" valign="top"><div id="userId1Tip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.userName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="userName"
						name="userInfo.userName" maxlength="5" type="text" class="input"
						value='<s:property value="userInfo.userName"/>' /></td>
					<td class="tdVad" valign="top"><div id="userNameTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.password" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="password"
						name="userInfo.password" maxlength="16" type="password"
						class="input" value='<s:property value="userInfo.password"/>' /></td>
					<td class="tdVad" valign="top"><div id="passwordTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.pwdConfirm" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="password2"
						name="password2" maxlength="16" type="password" class="input"
						value='<s:property value="userInfo.password"/>' /></td>
					<td class="tdVad" valign="top"><div id="password2Tip"></div></td>
				</tr>

				<!-- 角色单选 -->
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.role" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><s:select id="roleList"
							list="roleList" listKey="roleId" listValue="roleName"
							cssClass="newSelect" headerKey=""
							headerValue="%{getText('common.lable.select')}"
							onchange="siteChange();$('#roleHidden').val($(this).val());"></s:select><input
						id="role2" class="input" type="text" readonly="readonly"
						style="display: none;"><input id="roleHidden"
						type="hidden" name="userInfo.selectvalue" /></td>
					<td class="tdVad" valign="top">
						<div id="roleListTip"></div>
					</td>
				</tr>

				<!-- 所属站点 -->
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.siteName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><s:select id="site"
							list="sites" listKey="siteId" listValue="siteName"
							cssClass="newSelect" headerKey=""
							headerValue="%{getText('common.lable.select')}"
							onchange="$('#siteHidden').val($(this).val());"></s:select> <input
						id="site2" class="input" type="text" readonly="readonly"
						 style="display: none;">
						<input id="siteHidden" type="hidden" name="userInfo.siteId" /></td>
					<td class="tdVad" valign="top"><div id="siteTip"
							style="left: 2px; height: 22px"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="web.table.columnName.sex" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top" colspan="2">
						<div style="float: left; color: #666">
							<input type="radio" class="input" name="userInfo.sex" value="1"
								<s:if test="%{userInfo.sex==1}">checked</s:if>
								<s:elseif test="%{userInfo.sex==null}">checked</s:elseif> />
							<s:text name="web.sex.male" />
						</div>
						<div style="float: left; margin-left: 20px; color: #666">
							<input type="radio" class="input" name="userInfo.sex" value="2"
								<s:if test="%{userInfo.sex==2}">checked</s:if> />
							<s:text name="web.sex.female" />
						</div>
						<div style="float: left; margin-left: 20px; color: #666">
							<input type="radio" class="input" name="userInfo.sex" value="3"
								<s:if test="%{userInfo.sex==3}">checked</s:if> />
							<s:text name="web.sex.unknown" />
						</div>
						<div id="sexTip" style="float: left; margin-left: 70px;"></div>
					</td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.email" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="email"
						name="userInfo.email" type="text" class="input" maxlength="64"
						value='<s:property value="userInfo.email"/>' /></td>
					<td class="tdVad" valign="top"><div id="emailTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="web.table.columnName.mobile" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="mobile"
						name="userInfo.mobile" type="text" maxlength="11" class="input"
						value='<s:property value="userInfo.mobile"/>' /></td>
					<td class="tdVad" valign="top"><div id="mobileTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="web.table.columnName.personnelId" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input id="personnelId"
						name="userInfo.personnelId" maxlength="8" type="text"
						class="input" value='<s:property value="userInfo.personnelId"/>' /></td>
					<td class="tdVad" valign="top"><div id="personnelIdTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="web.table.columnName.description" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><textarea id="description"
							name="userInfo.description" cols="30" rows="3"
							class="newTextarea" onkeydown="validateTextarea();"
							onkeyup="validateTextarea();"></textarea></td>
					<td class="tdVad" valign="top"><div id="descriptionTip"></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="messages succcess" style="top: 25px; left: 37%;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<%-- 按钮栏 --%>
	<div class="serverEventButton">
		<ul class="rightToolButtonjhjFwSj">
			<li><a href="#" class="buttonFwSj"
				onclick="validate();return false;"><s:property
						value="getText('common.button.add')" /></a></li>
			<li><a href="userBrowser.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>
</div>