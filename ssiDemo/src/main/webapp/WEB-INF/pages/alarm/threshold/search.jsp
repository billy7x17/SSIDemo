<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<script type="text/JavaScript">
//$(document).ready(function(){
$(function(){
	$.formValidator.initConfig({formid:"mainForm",wideword:false});
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

function validate(){
	var formCheck = $.formValidator.pageIsValid("1");
	if(formCheck){
		$("#mainForm").submit();
	}else{
		$("#mainForm").submit();
	}
} 
</script>

 <!--内容部分 main star-->
    <div class="main">
	 <!--工作区 workarea start-->
        <div class="iframeWorkarea"> 	
            	<!--选项卡 tab start-->
                <div class="tabBar">
                    <a href="#" class="tabCurrent" style="text-decoration:none"><s:text name="common.title.search"/></a>
                </div>
                <!--选项卡 tab end-->    
                <!--滚动条 scroll start-->
                <div class="workareaMain scroll-pane">
                	<form id="mainForm" method="post" action="thresholdSearch.action" > 
            		 <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
					 
                     <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.name"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
			              <input type="text" id="eventName" name="eventName" value="" class="input"/>
			            </td>
			            <td class="tdVad" valign="top"><div id="eventNameTip"></div></td>
			         </tr>
			         
			         
			         <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.level"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
			              <s:select list="levelList" listKey="levelId" listValue="levelName" name="level" id="level" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
	                    </s:select>
			            </td>
			            <td class="tdVad" valign="top"><div id="levelTip"></div></td>
			         </tr>
			          
					 
					  <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.sign"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
			             <select id="perCondition" name="perCondition" class="newSelect">
                        	<option value=""><s:text name="common.lable.select"/></option>
                        	<option value="1"><s:text name="threshold.sign2"/></option>
                        	<option value="2"><s:text name="threshold.sign3"/></option>
                        	<option value="3"><s:text name="threshold.sign4"/></option>
                        	<option value="4"><s:text name="threshold.sign5"/></option>
                        </select>
			            </td>
			            <td class="tdVad" valign="top"><div id="perConditionTip"></div></td>
			         </tr>
					 
					  
					 <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.threshold"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
							<input type="text" id="value" name="value" value="" class="input"/>
			            </td>
			            <td class="tdVad" valign="top"><div id="valueTip"></div></td>
			         </tr>
					 
					 
					 <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.thresholdDeviceType"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
							<s:select list="deviceTypeList" listKey="rowId" listValue="agentName" name="deviceType" id="deviceType" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
	                    </s:select>
			            </td>
			            <td class="tdVad" valign="top"><div id="deviceTypeTip"></div></td>
			         </tr>
					 
					 <tr>
					 	<td>&nbsp</td>
					 	<td>&nbsp</td>
					 	<td>&nbsp</td>
					 </tr>
					 <tr>
					 	<td>&nbsp</td>
					 	<td>&nbsp</td>
					 	<td>&nbsp</td>
					 </tr>
					 
                    </table>  
					<input type="hidden" name="pageName" id="pageName" value="searchpage"/>
                   </form>               
                </div>  
              	<!--滚动条 scroll end-->
              	
              	<!-- 提示信息start -->
              	<div class="messages succcess">
            		<div id="msgTip" class="msgSuccess" style="top:25px"></div>
        		</div>
                <!-- 提示信息end-->	
                
                <div class="buttonbar">
                <!--按钮条 buttonbar start-->
                	<div class="buttonbarLef">
                        <a href="#" class="btnImp btn_disable testMessage3" onclick="validate();return false;">&#30830;&#23450;</a>   
                        <a href="thresholdList.action" class="button btn_disable" >&#21462;&#28040;</a>                
                    </div>
                <!--按钮条 buttonbar end-->
          		</div>       		  
        </div>        
        <!--工作区 workarea end-->     
    </div>
 	<!--内容部分 main end-->    
	
<!--最外层 end
</div>
-->
