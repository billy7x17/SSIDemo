<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>

<script type="text/JavaScript">
window.onload = function(){	
	$.formValidator.initConfig({formid:"update_form",wideword:false});

	
	    $("#rosterName").formValidator(
	            {onshow:'<s:text name="validator.rosterName.onshow" />',
	             onfocus:'<s:text name="validator.rosterName.onfocus" />',
	             oncorrect:'<s:text name="common.validator.oncorrect" />'})
	             .inputValidator({min:1,max:256,
	                     empty:{
	                     leftempty:false,
	                     rightempty:false,
	                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
	                     onerrormin:'<s:text name="validator.rosterName.onerrormin" />',
	                     onerrormax:'<s:text name="validator.rosterName.onerrormax" />'});

    
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
		$("#mainForm").attr("action","alarmFilterEdit.action");
		$("#mainForm").submit();
	}else{
		return false;
	}
} 


function back(){
	var url = "alarmFilterBack.action";
	$("#mainForm").attr("action", url);
	$("#mainForm").submit();
}

function selectContent(){
	  var url="alarmOIDSelect.action";
	  window.open(url,'mib','height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
}

function setContent(content){
	var id = content.split('@_@')[0];
    var name = content.split('@_@')[1];
    $('#rosterId').val(id);
    $('#rosterName').val(name);
    $('#rosterName').blur();
}
</script>
	<%-- 当前位置 --%>
	<div class="rightToolbar">
	  <div class="rightToolbarCrumbs">
	    <table cellpadding="0" cellspacing="0" height="40">
	      <tr>
	        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
	        <td>
	          <h3>修改筛选器</h3>
		          告警管理->告警配置->筛选器管理->修改</td>
	      </tr>
	    </table>
	  </div>
	</div>
	 <!--内容部分 main star-->
	     <div class="rightDisplayFwSj">
      <form id="mainForm" method="post" action="alarmrosterAdd.action" > 
        <s:hidden id="filterId" name="domain.filterId" />
        <s:hidden id="filterName" name="domain.filterName" />
        <s:hidden id="rosterId" name="domain.rosterId" />
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
          <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="field.label.filterName"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <s:property value="domain.filterName"/>
            </td>
              <td class="tdVad" valign="top"></td>
          </tr>
          <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="field.label.rosterName"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
               <input  name="domain.rosterName" id="rosterName" maxlength="256" class="input"  style="width:100px" value="<s:property value='domain.rosterName'/>" readonly="readonly"/>
              <a href="#" onclick="selectContent();return false;"><img align="absmiddle" src="<%=request.getContextPath()%>/themes/default/images/btn_view.gif" width="86" height="24"></a>
            </td>
            <td class="tdVad" valign="top"><div id="rosterNameTip"></div></td>
          </tr>
          
           <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="field.label.filterStatus"/><s:text name="common.lable.point" /></strong></td>
        	<td  class="tdInput" valign="top" colspan="2">
              <div style="float:left;">
                <input  type="radio" id="filterStatus" name="domain.filterStatus" value="0" <s:if test="domain.filterStatus == 0">checked</s:if>/><s:text name="field.label.status0"/>
              </div>
              <div style="float:left; margin-left:10px;">
               <input  type="radio" id="filterStatus" name="domain.filterStatus" value="1" <s:if test="domain.filterStatus == 1">checked</s:if>/><s:text name="field.label.status1"/>
              </div>
              <div id="filterStatusTip" style="float:left; margin-left:70px;"></div></td>
          </tr> 
         
        </table>    
      </form>
    </div>  
    <!--滚动条 scroll end-->
	<div class="messages succcess"  style="top:25px">
      <div id="msgTip" class="msgSuccess"></div>
    </div>
	<div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="#" onclick="back();return false;" class="buttonFwSj" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div>  
 	