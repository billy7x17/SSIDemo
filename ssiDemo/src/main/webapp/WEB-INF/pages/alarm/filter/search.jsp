<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<script type="text/JavaScript">
window.onload = function(){
	
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
	$("#mainForm").attr("action","alarmFilterSearch.action");
	$("#mainForm").submit();
} 

function back(){
    var url = "alarmFilterBack.action";
	$("#button_form").attr("action", url);
	$("#button_form").submit();
}

function selectContent(){
	  var url="alarmOIDSelect.action";
	  window.open(url,'mib','height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
}

function setContent(content){
	  $('#rosterId').val(content);
}



</script>

<!--内容部分 main star-->
<div class="main">
  <!--工作区 workarea start-->
  <div class="iframeWorkarea">
    <!--选项卡 tab start-->
	<div class="tabBar">
	  <a href="#" class="tabCurrent" style="text-decoration:none">
	    <s:text name="common.title.search" />
	  </a>
	</div>
	<!--选项卡 tab end-->
	<!--滚动条 scroll start-->
    <div class="workareaMain scroll-pane">
    
      <form id="mainForm" method="post" action="alarmrosterAdd.action" > 
        <input type="hidden" name="domain.alarmGrade" id="alarmGrade">
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
          <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.filterName"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <s:textfield name="domain.filterName" id="filterName" maxlength="16" cssClass="input" ></s:textfield>
            </td>
            <td class="tdVad" valign="top"><div id="filterNameTip"></div></td>
          </tr>
          <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.rosterId"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
              <s:textfield name="domain.rosterId" id="rosterId" maxlength="256" cssClass="input" style="width:235px"></s:textfield>
              <a href="#" onclick="selectContent();return false;"><img align="absmiddle" src="<%=request.getContextPath()%>/themes/default/images/btn_view.gif" width="96" height="24"></a>
            </td>
            <td class="tdVad" valign="top"><div id="rosterIdTip"></div></td>
          </tr>
         
          <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.filterStatus"/><s:text name="common.lable.point" /></strong></td>
        	<td  class="tdInput" valign="top" colspan="2">
              <div style="float:left;">
                <input  type="radio" id="filterStatus" name="domain.filterStatus" value="0" /><s:text name="field.label.status0"/>
              </div>
              <div style="float:left; margin-left:10px;">
                <input  type="radio" id="filterStatus" name="domain.filterStatus" value="1" /><s:text name="field.label.status1"/>
              </div>
              <div id="filterStatusTip" style="float:left; margin-left:70px;"></div></td>
          </tr> 
         
        </table>    
      </form>
            
    </div>
    <!--搜索条纵向 searchBar end-->
	<div class="messages succcess">
      <div id="msgTip" class="msgSuccess"></div>
    </div>
	<div class="buttonbar">
	<!--按钮条 buttonbar start-->
	  <div class="buttonbarLef">
	    <form id="button_form" action="">
		  <a href="#" class="btnImp btn_disable testMessage3"  onclick="validate();return false;"><s:text name="common.button.search" /></a>   
		  <a href="#" class="button btn_disable" onclick="back();return false;"><s:text name="common.button.cancel" /></a>                
		</form>
	  </div>
	  <!--按钮条 buttonbar end-->
	</div>
  </div>        
  <!--工作区 workarea end-->
</div>
<!--内容部分 main end-->    