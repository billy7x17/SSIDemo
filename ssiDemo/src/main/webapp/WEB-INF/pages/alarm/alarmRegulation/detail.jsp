<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/javascript/JQuery-zTree/css/zTreeStyle/zTreeStyle.css"
    type="text/css">
<script
    src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.core-3.0.js"></script>
<script
    src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.excheck-3.0.js"></script>
<script
    src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.exedit-3.0.js"></script>
    
<style type="text/css">
.baseTab li{height:auto;}
li a{text-decoration:none;}
.baseTab ul{border-top:0px solid #CCCCCC}
.BasicInformation li{width:40px;margin-top:3px;}
</style>
    
<script type="text/javascript">
$(function() {

    //展示规则树信息
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    var nodes = '<s:property value="domain.ruleInfo" escape="false" />';
   $.fn.zTree.init($("#treeDemo"), setting, eval(nodes));
   
   var  eventContent = " ";
   var num='<s:property value="actionDomain.ruleAction"/>'; 
   var notityStatus = false;//通知状态
   var redefineStatus = false;//重定义状态
   if(num.indexOf('1') >-1){
	   eventContent+='<s:text name="field.label.ruleAction.clear" /> ';
   }
   if(num.indexOf('2') >-1){
	   eventContent+='<s:text name="field.label.ruleAction.confirm" /> ';
   }
   if(num.indexOf('4') >-1){
	   eventContent+='<s:text name="field.label.ruleAction.filter" /> ';
   }
   if(num.indexOf('3') >-1){
	   eventContent+='<s:text name="field.label.ruleAction.notufy" /> ';
	   notityStatus = true;
   }
   if(num.indexOf('5')>-1){
	   eventContent+='<s:text name="field.label.ruleAction.redefine" /> ';
	   redefineStatus = true;
   }
  $("#ruleAction").val(eventContent);
  
  if(notityStatus && redefineStatus){
	  $("#basetitleDiv").css("width","107px");
	  $("#notitytitleDiv").css("width","107px");
	  $("#redifinetitleDiv").css({"width":"107px","border-left":"1px solid #555"});
	  
	  $("#basetitleDiv").attr("onclick","showBase(this)");
	  $("#notitytitleDiv").attr("onclick","showBase(this)");
	  $("#redifinetitleDiv").attr("onclick","showBase(this)");
  }else if(notityStatus){
	  $("#basetitleDiv").css("width","50%");
	  $("#notitytitleDiv").css("width","50%");
	  $("#redifinetitleDiv").css("display","none");
	  
	  $("#basetitleDiv").attr("onclick","showBase(this)");
	  $("#notitytitleDiv").attr("onclick","showBase(this)");
  }else if(redefineStatus){
	  $("#basetitleDiv").css("width","50%");
	  $("#redifinetitleDiv").css("width","50%");
	  $("#notitytitleDiv").css("display","none");
	  
	  $("#basetitleDiv").attr("onclick","showBase(this)");
	  $("#redifinetitleDiv").attr("onclick","showBase(this)");
  }else{
	  $("#basetitleDiv").css({"width":"322","cursor": "default","text-decoration": "none"});
	  $("#redifinetitleDiv").css("display","none");
	  $("#notitytitleDiv").css("display","none");
  }
  
  
});
function showBase(obj){
	$("#"+obj.id).removeClass().addClass("baseTitl click");
	$("#"+obj.id+"Info").css("display","block");
	//同级节点隐藏内容，样式变灰
	$("#"+obj.id).siblings().each(function(){
		if($(this).css("display")!="none"){
		  $(this).removeClass().addClass("baseTitl");
		  $("#"+this.id+"Info").css("display","none");
		}
	});
	
}

</script>

<div class="BasicInformation">
	<h2><s:property value="domain.ruleName"/></h2>
    <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
</div>

<%-- 详细信息 --%>
<div>
<div class="baseTitl click" id="basetitleDiv" ><s:text name="common.lable.baseInfo"/> </div>
<div class="baseTitl" id="notitytitleDiv" ><s:text name="detail.notity.info"/> </div>
<div class="baseTitl" id="redifinetitleDiv" ><s:text name="detail.redifine.info"/> </div>
</div>
<div id="basetitleDivInfo" class="baseTab scroll-pane">
    <form action="" method="post" name="detailForm" id="detailForm">
	  <s:hidden id="ID" name="domain.ID" />
      <s:hidden id="ruleName" name="domain.ruleName" />
      <s:hidden id="ruleStateDetail" name="domain.ruleState" />
	</form>
    <table width="250" border="0" cellspacing="0" cellpadding="0">
     <tr>
        <td ><s:text name="field.label.ruleName"/><s:text name="common.lable.point" /></td>
        <th><input type="text" readonly="readonly" value="<s:property value="domain.ruleName"/>"/></th>
     </tr>
     <tr>
        <td><s:text name="field.label.ruleAction"/><s:text name="common.lable.point" /></td>
     	<th>
         	<input type="text" readonly="readonly" id="ruleAction" />
        </th>
     </tr>   
     <tr>
        <td><s:text name="field.label.ruleState"/><s:text name="common.lable.point" /></td>
     	<th><input type="text" readonly="readonly" value="<s:property value="domain.ruleState"/>"/></th>
     </tr>
     <tr>
        <td><s:text name="field.label.ruleDesc"/><s:text name="common.lable.point" /></td>
     	<th><input type="text" readonly="readonly" value="<s:property value="domain.ruleDesc"/>"/> </th>
     </tr>
     <tr>
        <td><s:text name="field.label.rultInfo"/><s:text name="common.lable.point" /></td>
     </tr>
     <tr>
      	 <td></td>
         <th>
              <div style="overflow-x:auto; height:auto; min-height:150px; width: 282px;">
                   <ul id="treeDemo" class="ztree"></ul>
              </div>
         </th>
     </tr>
  </table>
</div>


<%-- 通知 --%>
<div id="notitytitleDivInfo" class="baseTab scroll-pane" style="display:none;">
    <table width="250" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
               	<s:text name="field.label.clear.notity"/><s:text name="common.lable.point" />
            </td>
            <th>
                <s:if test="actionDomain.clearNotify==0"><input type="text" readonly="readonly" value="<s:text name="field.label.no"/>"/></s:if> 
                <s:if test="actionDomain.clearNotify==1"><input type="text" readonly="readonly" value="<s:text name="field.label.yes"/>"/></s:if>
            </th>
         </tr>
         <tr>
            <td>
                <s:text name="field.label.notifyType"/><s:text name="common.lable.point" />
            </td>
             <th> <s:if test="actionDomain.notifyType==1"><input  type="text" readonly="readonly" value="<s:text name="field.label.notifyType.mail"/>"/></s:if>
             </th>
         </tr>
          <tr>
            <td><s:text name="field.label.mailAdd"/><s:text name="common.lable.point" /></td>
             <th><textarea rows="3" cols="20" readonly="readonly" ><s:property value="actionDomain.notifyPersonMail"/></textarea> </th>
         </tr>
          <tr>
            <td><s:text name="field.label.notifyTitle"/><s:text name="common.lable.point" /></td>
             <th><textarea rows="3" cols="20" readonly="readonly"  ><s:property value="actionDomain.notifyTitle" /></textarea> </th>
             
         </tr>
          <tr>
            <td><s:text name="field.label.notifyContent"/><s:text name="common.lable.point" /></td>
             <th><textarea rows="3" cols="20" readonly="readonly"  ><s:property value="actionDomain.notifyContent" /></textarea> </th>
         </tr>
    </table>
</div>


<%-- 重定义 --%>
<div id="redifinetitleDivInfo" class="baseTab scroll-pane" style="display:none;">
    <table width="250" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <s:text name="field.label.redefineLevel"/><s:text name="common.lable.point" />
            </td>
            <th>
              <input type="text"  readonly="readonly" value="<s:property value="domain.levelName"/>"/>
            </th>
         </tr>
        <%--  <s:iterator value="redefineContentList">
           <tr style="height:2px;">
             <td colspan="2"><hr style="height:2px;" /></td>
           </tr>
           <tr>
            <td>原始告警内容<s:text name="common.lable.point" /></td>
            <th>
               <s:property value="originalContent" /> 
            </th>
           </tr>
           <tr>
            <td>重定义告警内容<s:text name="common.lable.point" /> 
            </td>
            <th>
              <s:property value="redefineContent" /> 
            </th>
           </tr>
        </s:iterator> --%>
    </table>
</div>

