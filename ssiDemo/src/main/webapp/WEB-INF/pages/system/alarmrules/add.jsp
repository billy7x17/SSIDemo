<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/JavaScript">
$(function(){
    $.formValidator.initConfig({formid:"mainForm",wideword:false});

    //设备类型验证
    $("#typeID")
    .formValidator({
        onshow:'<s:text name="validator.typeID.onshow" />',
        onfocus:'<s:text name="validator.typeID.onfocus" />',
        oncorrect:'<s:text name="validator.typeID.oncorrect" />'})
    .inputValidator({min:1,max:64,
        empty:{leftempty:false,rightempty:false,
            emptyerror:'<s:text name="validator.typeID.emptyerror" />'},
            onerrormin:'<s:text name="validator.typeID.onerrormin" />',
            onerrormax:'<s:text name="validator.typeID.onerrormax"/>'});        
        
    //告警OID验证
    $("#alarmOID").formValidator({
        onshow:'<s:text name="validator.alarmOID.onshow" />',
        onfocus:'<s:text name="validator.alarmOID.onfocus" />',
        oncorrect:'<s:text name="validator.alarmOID.oncorrect" />'}).
        inputValidator({min:1,max:200,empty:{leftempty:false,rightempty:false,emptyerror:'<s:text name="validator.alarmOID.emptyerror" />'},
        onerrormin:'<s:text name="validator.alarmOID.onerrormin" />',
        onerrormax:'<s:text name="validator.alarmOID.onerrormax" />'});
    

    //告警标题验证
    $("#alarmTitle").formValidator({
        onshow:'<s:text name="validator.alarmTitle.onshow" />',
        onfocus:'<s:text name="validator.alarmTitle.onfocus" />',
        oncorrect:'<s:text name="validator.alarmTitle.oncorrect" />'}).
        inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:'<s:text name="validator.alarmTitle.emptyerror" />'},
        onerrormin:'<s:text name="validator.alarmTitle.onerrormin" />',
        onerrormax:'<s:text name="validator.alarmTitle.onerrormax" />'});

  	//告警索引验证
    $("#alarmIndex").formValidator({
        onshow:'<s:text name="validator.alarmIndex.onshow" />',
        onfocus:'<s:text name="validator.alarmIndex.onfocus" />',
        oncorrect:'<s:text name="validator.alarmIndex.oncorrect" />'}).
        inputValidator({min:0,max:200,empty:{leftempty:false,rightempty:false,emptyerror:'<s:text name="validator.alarmIndex.emptyerror" />'},
        onerrormax:'<s:text name="validator.alarmIndex.onerrormax" />'});
    
    //告警级别
    $("#alarmLevel")
    .formValidator({
        onshow:'<s:text name="validator.alarmLevel.onshow" />',
        onfocus:'<s:text name="validator.alarmLevel.onfocus" />',
        oncorrect:'<s:text name="validator.alarmLevel.oncorrect" />'})
    .inputValidator({min:1,max:5,
        empty:{leftempty:false,rightempty:false,
            emptyerror:'<s:text name="validator.alarmLevel.emptyerror" />'},
            onerrormin:'<s:text name="validator.alarmLevel.onerrormin" />',
            onerrormax:'<s:text name="validator.alarmLevel.onerrormax"/>'});    

    //描述验证
    $("#alarmContent").formValidator({
        onshow:'<s:text name="validator.alarmContent.onshow" />',
        onfocus:'<s:text name="validator.alarmContent.onfocus" />',
        oncorrect:'<s:text name="common.validator.oncorrect" /> '}).
        inputValidator({min:0,max:500,onerrormax:'<s:text name="validator.alarmContent.onerrormax" />'});
    
    //告警的影响描述
    $("#alarmImpact").formValidator({
        onshow:'<s:text name="threshold.js.onshow.alarmImpact" />',
        onfocus:'<s:text name="threshold.js.onshow.alarmImpact" />',
        oncorrect:'<s:text name="common.validator.oncorrect" />'})
        .inputValidator({min:0,max:512,onerrormax:'<s:text name="threshold.js.512char" />'});

    var msg = "<s:property value='msg'/>";
	var errorMsg = "<s:property value='errorMsg'/>";
	if (msg != null && msg != "") {
		$("#msgTip").html(msg);
		$(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	} else if (errorMsg != null && errorMsg != "") {
		$("#msgTip").attr("class", "msgErrors");
		$("#msgTip").html(errorMsg);
		$(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}

  	//去掉鼠标移入移出效果
    $("tr").each(function(){
        var tr_bgColor = $(this).css("background-color");
        $(this).hover(function(){
            $(this).css("background-color","transparent");
        },function(){
            $(this).css("background-color","transparent");
        })
    })
            
});

function validate() {
	var formCheck = $.formValidator.pageIsValid("1");
    if(formCheck) {
        $("#mainForm").submit();
    } else {
        return false;
    }
} 

function validateTextarea(){
    if($("#alarmContent").val().length > 500){
        $("#alarmContent").val($("#alarmContent").val().substring(0,500));
    }
}

function validateTextareaImpact(){
    if($("#alarmImpact").val().length > 512){
        $("#alarmImpact").val($("#alarmImpact").val().substring(0,512));
    }
}

function checkType(obj){
    var id = $(obj).attr('id');
    var selectValue = $('#' + id).val();
   
    var url = 'getDeviceTitle.action';
    var params = {
            deviceId:selectValue
    };
    jQuery.post(url, params, callbackFun, 'text');
   
    $('#typeID').val(selectValue);
}

function callbackFun(data){   
   var jsonObj=eval("("+data+")");
   var titleLi = jsonObj.title;

   $("#alarmTitle").empty();
   $("#alarmTitle").append("<option value='null'>"+'<s:text name="lable.select" />'+"</option>");
   for(var key in titleLi){
      if(typeof(titleLi[key].tcId)!="undefined"){
       $("#alarmTitle").append("<option value='"+titleLi[key].tcId + "' title='" + titleLi[key].tcTitle +"'>"+titleLi[key].tcTitle+"</option>");
      }
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
			<li><s:text name="menu.title" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="menu.sub.title" /></li>
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
		<form id="mainForm" method="post" action="alarmrulesAdd.action">
			<div class="formLabel">
				<s:text name="common.lable.baseInfo" />
				<s:text name="common.lable.line" />
			</div>
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="alarmrules.typeID" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top" id="sd1"><s:select
							list="alarmRulesList" id="typeID" listKey="typeID"
							listValue="agentName" name="alarmrules.typeID"
							cssClass="newSelect" headerKey=""
							headerValue="%{getText('lable.select')}" onchange="checkType(this)">
						</s:select></td>
					<td class="tdVad" valign="top"><div id="typeIDTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="alarmrules.alarmTitle" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="alarmrules.alarmTitle" id="alarmTitle" class="newSelect">
							<option value="null">
								<s:text name="lable.select" />
							</option>
					</select></td>
					<td class="tdVad" valign="top"><div id="alarmTitleTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="alarmrules.alarmOID" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="alarmOID" name="alarmrules.alarmOID" maxlength="200"
						class="input" /></td>
					<td class="tdVad" valign="top"><div id="alarmOIDTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="alarmrules.alarmLevel" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><s:select list="LevelList"
							id="alarmLevel" listKey="alarmLevel" listValue="alarmLevelName"
							name="alarmrules.alarmLevel" cssClass="newSelect" headerKey=""
							headerValue="%{getText('lable.select')}">
						</s:select></td>
					<td class="tdVad" valign="top"><div id="alarmLevelTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
								name="alarmrules.alarmIndex" />
							<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="alarmIndex" name="alarmrules.alarmIndex" maxlength="200"
						value="<s:property value="alarmrules.alarmIndex"/>" class="input" />
					</td>
					<td class="tdVad" valign="top"><div id="alarmIndexTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="alarmrules.alarmContent" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top">
						<div>
							<label for="textarea"></label>
							<textarea name="alarmrules.alarmContent" id="alarmContent"
								class="newTextarea" onkeydown="validateTextarea();"
								onkeyup="validateTextarea();" style="margin-bottom: 15px"></textarea>
						</div>
					</td>
					<td class="tdVad" valign="top"><div id="alarmContentTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="alarmrules.alarmImpact" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top">
						<div>
							<label for="textarea"></label>
							<textarea name="alarmrules.alarmImpact" id="alarmImpact"
								class="newTextarea" onkeydown="validateTextareaImpact();"
								onkeyup="validateTextareaImpact();"></textarea>
						</div>
					</td>
					<td class="tdVad" valign="top"><div id="alarmImpactTip"></div></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 提示信息start -->
	<div class="messages" style="top: 25px; left: 37%">
		<div id="msgTip" class="msgErrors"></div>
	</div>
	<!-- 提示信息end-->
	<%-- 按钮栏 --%>
	<div class="serverEventButton">
		<ul class="rightToolButtonjhjFwSj">
			<li><a href="#" class="buttonFwSj"
				onclick="validate();return false;"><s:property
						value="getText('common.button.add')" /></a></li>
			<li><a href="alarmrulesList.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>
</div>