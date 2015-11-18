<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<script type="text/JavaScript">
window.onload = function(){
    $.formValidator.initConfig({formid:"mainForm",wideword:false});
         
       $("#tcId").formValidator({
    	  onshow:'<s:text name="validator.tcId.onshow" />',
          onfocus:'<s:text name="validator.tcId.onfocus" />'})
          .functionValidator({
              fun:function(val,elem){
              if(val == null || val == ""){
                 return '<s:text name="validator.tcId.onerrormin" />';
              }else{
                 var exist = false;
             	$.ajax({  
                      async: false,
                      url: "alarmTitleBeforeAddValidateTcId.action", 
                      type: "post",   
                      dataType:"text",
                      data : {'tcId': val},
                      success:function(data) {//这里的data是由请求页面返回的数据   
                          var json = eval("("+data+")");//转换为json对象
                          if(json.result=="exist") {//系统异常
                         	 exist = true;
                          }
                      },   
                      error: function(XMLHttpRequest, textStatus, errorThrown) {
                     	alert('error');
                         return true; 
                      }   
                  }); 
                 if(exist){
                 	return '<s:text name="message.add.dataExsit" />';
                 }else{
                     return true;
                 }
              }
           }
         });
        	
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

	//去掉鼠标移入移出效果
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		})
	})


};
function validateTextarea(id ,length){
	if($("#" + id).val().length > length)
	  {
		$("#" + id).val($("#" + id).val().substring(0,length));
	  }
}

function validate(){
	var formCheck = $.formValidator.pageIsValid("1");
	if(formCheck){
		$("#mainForm").attr("action","alarmTitleAdd.action?domain.type=1");
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
	  <li><s:text name="alarmTitle.function.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.add"></s:text>
  </ul>
  </div>
</div>
	 <!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
	 	 <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
	     <form id="mainForm" method="post" action="alarmTitleAdd.action" > 
     		<table class="addTable" align="center" cellpadding="0" cellspacing="0" >
                   <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.tcId"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
	              <input type="text" id="tcId" name="domain.tcId" class="input" maxlength="64"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcIdTip"></div></td>
	         </tr>
	          <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.tcTitle"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="tcTitle" name="domain.tcTitle" class="input" maxlength="64"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcTitleTip"></div></td>
	         </tr>
	         <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.tcType"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="tcType" name="domain.tcType" class="input" maxlength="16"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="tcTypeTip"></div></td>
	         </tr>
	         <tr>
	            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.alarmIdentityID"/><s:text name="common.lable.point"/></td>
	            <td class="tdInput" valign="top">
                   	<input type="text" id="alarmIdentityID" name="domain.alarmIdentityID" class="input" maxlength="64"/>
	            </td>
	            <td class="tdVad" valign="top"><div id="alarmIdentityIDTip"></div></td>
	         </tr>

	      </table>  
	  </form>
	</div> 
	<!-- 提示信息start -->
   	<div class="messages succcess">
 		<div id="msgTip" class="msgSuccess" style="top:25px;left: 37%;"></div>
	</div>
	<!-- 提示信息end-->
	<div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="alarmTitleList.action"  class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div>  
</div>
  