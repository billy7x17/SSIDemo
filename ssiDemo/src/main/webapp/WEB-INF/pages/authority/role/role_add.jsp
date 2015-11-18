<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cloudmaster.cmp.web.authority.auth.*"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/JQuery-zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.core-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.excheck-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.exedit-3.0.js"></script>
<script type="text/javascript" language="JavaScript1.2">
	var setting = {
		view : {
			showLine : false
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		}
	};
	$(function() {
		var obj = $(window.parent.document).find(".acdSel");
		$(obj).attr("class", "leftAcdArr");
		var obj12 = $(window.parent.document).find("#alarmview");
		$(obj12).attr("class", "leftAcdArr acdSel");

		$.formValidator.initConfig({
			formid : "mainForm",
			wideword : false
		});
		$("#roleName").formValidator({
			onshow : "<s:text name='web.validate.roleName'/>",
			onfocus : "<s:text name='web.validate.roleName'/>",
			oncorrect : "<s:text name='web.validate.ok'/>"
		}).inputValidator({
			min : 1,
			max : 32,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : "<s:text name='web.validate.trim'/>"
			},
			onerror : "<s:text name='web.validate.roleName.null'/>",
			onerrormax : "<s:text name='web.validate.roleName.max'/>"
		});
		$("#description").formValidator({
			onshow : "<s:text name='web.validate.description'/>",
			onfocus : "<s:text name='web.validate.description'/>",
			oncorrect : "<s:text name='web.validate.ok'/>"
		}).inputValidator({
			min : 0,
			max : 256,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : "<s:text name='web.validate.trim'/>"
			},
			onerrormax : "<s:text name='web.validate.description.max'/>"
		});

		$("tr").each(function() {
			var tr_bgColor = $(this).css("background-color");
			$(this).hover(function() {
				$(this).css("background-color", "transparent");
			}, function() {
				$(this).css("background-color", "transparent");
			})
		})

		var errorMsg = "<s:property value='errorMsg'/>";
		if (errorMsg != null && errorMsg != "") {
			$("#msgTip").attr("class", "msgErrors");
			$("#msgTip").html(errorMsg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		}

		var nodes = [];
		<s:iterator value="authList" status="node">
		nodes.push({
			id : '<s:property escape="false" value="authId" />',
			pId : '<s:property escape="false" value="pid" />',
			name : '<s:property escape="false" value="authName" />'
		});
		</s:iterator>
		$.fn.zTree.init($("#tree"), setting, nodes);

		//选中树节点
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var node = "";
		<s:iterator value="roleAuthList" status="node">
		node = treeObj.getNodesByParam('id',
				'<s:property escape="false" value="authId" />', null);
		treeObj.checkNode(node[0], true, true);
		</s:iterator>

	});

	function validate() {
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck) {
			var ids = getNodeIds();
			if (ids.length == 0 && ids == "") {
				alert("<s:text name='web.alert.chooseAuth'/>");
				return false;
			} else {
				$('#authIds').val(ids);
				$("#mainForm").submit();
			}
		} else {
			return false;
		}
	}
	function validateTextarea() {
		if ($("#description").val().length > 256) {
			$("#description").val($("#description").val().substring(0, 256));
		}
	}
	function getNodeIds() {
		var rv = "";
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		for ( var i = 0; i < nodes.length; i++) {
			rv += nodes[i].id;
			if (i != nodes.length - 1) {
				rv += ",";
			}
		}//end for i;
		return rv;
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
		<form id="mainForm" method="post" action="roleAddSave.action">
			<input type="hidden" id="authIds" name="authIds" value="" />
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="function.lable.roleName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						class="input" id="roleName" maxlength="32" name="roleName"
						value="<s:property value="roleName"/>" /></td>
					<td class="tdVad" valign="top"><div id="roleNameTip"></div></td>
				</tr>
				<!-- 角色类型 -->
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="function.lable.roleType" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top" colspan="2">
						<div style="float: left; color: #666;">
							<input type="radio" name="roleType" value="1" checked />
							<s:text name="function.lable.roleType.1" />
						</div>
						<div style="float: left; margin-left: 30px; color: #666;">
							<input type="radio" name="roleType" class="input" value="2" />
							<s:text name="function.lable.roleType.2" />
						</div>
						<div id="danxuanTip" style="float: left; margin-left: 70px;"></div>
					</td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text name="function.lable.status" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top" colspan="2">
						<div style="float: left; color: #666;">
							<input type="radio" name="status" value="0"
								<s:if test="%{status==null}">checked</s:if>
								<s:elseif test="%{status==0}">checked</s:elseif> />
							<s:text name="function.lable.status.0" />
						</div>
						<div style="float: left; color: #666; margin-left: 66px;">
							<input type="radio" name="status" class="input" value="1"
								<s:if test="%{status==1}">checked</s:if> />
							<s:text name="function.lable.status.1"/>
						</div>
						<div id="danxuanTip" style="float: left; margin-left: 70px;"></div>
					</td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text
							name="function.lable.authList" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top" style="width: 265px">
						<div style="overflow: auto;">
							<ul id="tree" class="ztree"></ul>
						</div>
					</td>
					<td class="tdVad" valign="top"><div id="roleNameTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"></td>
					<td class="tdInput" valign="top"></td>
					<td class="tdVad" valign="top"></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="function.lable.description" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top">
						<textarea id="description" name="description" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"></textarea>
					</td>
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
			<li><a href="roleBrowser.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>
</div>
