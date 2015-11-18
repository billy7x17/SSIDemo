<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
	$(function() {
		$.formValidator.initConfig({
			formid : "mainForm",
			wideword : false
		});

		$("#organize").formValidator({
			onshow : '<s:text name="validator.organize.onshow" />',
			onfocus : '<s:text name="validator.organize.onfocus" />',
			oncorrect : '<s:text name="validator.organize.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 32,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="validator.organize.emptyerror" />'
			},
			onerrormin : '<s:text name="validator.organize.onerrormin" />',
			onerrormax : '<s:text name="validator.organize.onerrormax"/>'
		});

		$("#paramKey").formValidator({
			onshow : '<s:text name="validator.paramKey.onshow" />',
			onfocus : '<s:text name="validator.paramKey.onfocus" />',
			oncorrect : '<s:text name="validator.paramKey.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="validator.paramKey.emptyerror" />'
			},
			onerrormin : '<s:text name="validator.paramKey.onerrormin" />',
			onerrormax : '<s:text name="validator.paramKey.onerrormax"/>'
		});

		$("#paramValue")
				.formValidator(
						{
							onshow : '<s:text name="validator.paramValue.onshow" />',
							onfocus : '<s:text name="validator.paramValue.onfocus" />',
							oncorrect : '<s:text name="validator.paramValue.oncorrect" />'
						})
				.inputValidator(
						{
							min : 1,
							max : 512,
							empty : {
								leftempty : false,
								rightempty : false,
								emptyerror : '<s:text name="validator.paramValue.emptyerror" />'
							},
							onerrormin : '<s:text name="validator.paramValue.onerrormin" />',
							onerrormax : '<s:text name="validator.paramValue.onerrormax"/>'
						});

		$("#description").formValidator({
			onshow : '<s:text name="validator.description.onshow" />',
			onfocus : '<s:text name="validator.description.onfocus" />',
			oncorrect : '<s:text name="validator.description.oncorrect"/>'
		}).inputValidator({
			min : 0,
			max : 150,
			onerrormax : '<s:text name="validator.description.onerrormax" />'
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

	function validateTextarea(id, length) {
		if ($("#" + id).val().length > length) {
			$("#" + id).val($("#" + id).val().substring(0, length));
		}
	}
</script>
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
		<form id="mainForm" method="post" action="systemParamAdd.action">
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="field.label.organize" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="organize" name="domain.organize" maxlength="32"
						value="<s:property value="domain.organize"/>" class="input" /></td>
					<td class="tdVad" valign="top"><div id="organizeTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="field.label.paramKey" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="paramKey" name="domain.paramKey" maxlength="64"
						value="<s:property value="domain.paramKey"/>" class="input" /></td>
					<td class="tdVad" valign="top"><div id="paramKeyTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="field.label.paramValue" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><textarea id="paramValue"
							name="domain.paramValue" cols="45" rows="5" class="newTextarea"
							onkeydown="validateTextarea('paramValue','512');"
							onkeyup="validateTextarea('paramValue','512');" style="margin-bottom: 15px"></textarea></td>
					<td class="tdVad" valign="top"><div id="paramValueTip"></div></td>
				</tr>

				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="field.label.description" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><textarea
							name="domain.description" id="description" class="newTextarea"
							onkeydown="validateTextarea('description','150');"
							onkeyup="validateTextarea('description','150');"></textarea>
						</div></td>
					<td class="tdVad" valign="top"><div id="descriptionTip"></div></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 提示信息start -->
	<div class="messages succcess" style="top: 25px; left: 37%;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<!-- 提示信息end-->
	<%-- 按钮栏 --%>
	<div class="serverEventButton">
		<ul class="rightToolButtonjhjFwSj">
			<li><a href="#" class="buttonFwSj"
				onclick="validate();return false;"><s:property
						value="getText('common.button.add')" /></a></li>
			<li><a href="systemparameterList.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>

</div>