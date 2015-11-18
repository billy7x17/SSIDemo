<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>

<script type="text/JavaScript">
window.onload = function(){	
	$.formValidator.initConfig({formid:"update_form",wideword:false});
	
	    $("#tcTitle").formValidator(
	            {onshow:'<s:text name="validator.tcTitle.onshow" />',
	             onfocus:'<s:text name="validator.tcTitle.onfocus" />',
	             oncorrect:'<s:text name="common.validator.oncorrect" />'})
	             .inputValidator({min:1,max:64,
	                     empty:{
	                     leftempty:false,
	                     rightempty:false,
	                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
	                     onerrormin:'<s:text name="validator.tcTitle.onerrormin" />',
	                     onerrormax:'<s:text name="validator.tcTitle.onerrormax" />'});
		
	    $("#tcType").formValidator(
	            {onshow:'<s:text name="validator.tcType.onshow" />',
	             onfocus:'<s:text name="validator.tcType.onfocus" />',
	             oncorrect:'<s:text name="common.validator.oncorrect" />'})
	             .inputValidator({min:1,max:16,
	                     empty:{
	                     leftempty:false,
	                     rightempty:false,
	                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
	                     onerrormin:'<s:text name="validator.tcType.onerrormin" />',
	                     onerrormax:'<s:text name="validator.tcType.onerrormax" />'});
	

	    $("#alarmIdentityID").formValidator(
	            {onshow:'<s:text name="validator.alarmIdentityID.onshow" />',
	             onfocus:'<s:text name="validator.alarmIdentityID.onfocus" />',
	             oncorrect:'<s:text name="common.validator.oncorrect" />'})
	             .inputValidator({min:1,max:64,
	                     empty:{
	                     leftempty:false,
	                     rightempty:false,
	                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
	                     onerrormin:'<s:text name="validator.alarmIdentityID.onerrormin" />',
	                     onerrormax:'<s:text name="validator.alarmIdentityID.onerrormax" />'});

	    $("#systemId").formValidator(
	            {onshow:'<s:text name="validator.systemId.onshow" />',
	             onfocus:'<s:text name="validator.systemId.onfocus" />',
	             oncorrect:'<s:text name="common.validator.oncorrect" />'})
	             .inputValidator({min:0,max:64,
	                     empty:{
	                     leftempty:false,
	                     rightempty:false,
	                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
	                     onerrormin:'<s:text name="validator.systemId.onerrormin" />',
	                     onerrormax:'<s:text name="validator.systemId.onerrormax" />'});

    //提示消息
    var msg = "<s:property value='msg'/>";
	var errorMsg = "<s:property value='errorMsg'/>";
	if(msg != null && msg != ""){
		$("#msgTip").html(msg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}else if(errorMsg != null && errorMsg != ""){
		$("#msgTip").attr("class","msgErrors");
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}

	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		})
	})	
};

function validate(){
	var formCheck = $.formValidator.pageIsValid("1");
	if(formCheck){
		$("#mainForm").attr("action","alarmTitleEdit.action?domain.type=1");
		$("#mainForm").submit();
	}else{
		return false;
	}
} 

</script>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
  <h2 class="sec-label"><s:text name="alarmTitle.function.title"/></h2>
  <ul class="bread-cut">
	  <li><s:text name="alarmTitle.menu.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="alarmTitle.alarmConfig.title"/> </li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="alarmTitle.function.title"/></li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.edit"></s:text>
  </ul>
  </div>
</div>
	 <!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
	  <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
      <form id="mainForm" method="post" action="alarmTitleEdit.action" > 
      	<s:hidden id="tcId" name="domain.tcId" />
         <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
         	 <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="field.label.tcId"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top" style="color: #666;">
	              <s:property value="domain.tcId"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcIdTip"></div></td>
	         </tr>
	         
	          <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.tcTitle"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="tcTitle" name="domain.tcTitle" value="<s:property value="domain.tcTitle"/>"  class="input" maxlength="64"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcTitleTip"></div></td>
	         </tr>
	         
	         <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.tcType"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="tcType" name="domain.tcType"  value="<s:property value="domain.tcType"/>" class="input" maxlength="16"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcTypeTip"></div></td>
	         </tr>
	        
	         <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.alarmIdentityID"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="alarmIdentityID" name="domain.alarmIdentityID" value="<s:property value="domain.alarmIdentityID"/>"  class="input" maxlength="64"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="alarmIdentityIDTip"></div></td>
	         </tr>

        </table>    
      </form>
    </div>
    <!--滚动条 scroll end-->
	<div class="messages succcess"  style="top:25px;left:37%;">
      <div id="msgTip" class="msgSuccess"></div>
    </div>
    <div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="alarmTitleList.action" class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div> 
</div>  
    
    
	 
 	