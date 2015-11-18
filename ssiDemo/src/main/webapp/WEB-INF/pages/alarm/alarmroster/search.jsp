<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<script type="text/JavaScript">
//$(document).ready(function(){
$(function(){
	$.formValidator.initConfig({formid:"mainForm",wideword:false});
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
	//	alert(tr_bgColor);	
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
               <form id="mainForm" method="post" action="alarmrosterSearch.action" > 
                   <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
                     <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.manufactureID"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
			              <input type="text" id="manufactureID" class="input" name="alarmroster.manufactureID" maxlength="64" value="<s:property value="alarmroster.manufactureID"/>"/>
			            </td>
			            <td class="tdVad" valign="top"><div id="manufactureIDTip">&nbsp;</div></td>
			         </tr>
			         
					 <tr>
			           <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.alarmGrade"/><s:text name="common.lable.point" /></strong></td>
			            <td  class="tdInput" valign="top" colspan="2">
			              <div style="float:left;">
			               		<input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="0" /><s:text name="alarmroster.alarmGrade.0"/>
			              </div>
			              <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="1"/><s:text name="alarmroster.alarmGrade.1"/>
			              </div>
			              <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="2" /><s:text name="alarmroster.alarmGrade.2"/>
			              </div>
			              <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="3" />  <s:text name="alarmroster.alarmGrade.3"/>
			              </div>
			               <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="4" /> <s:text name="alarmroster.alarmGrade.4"/>
			              </div>
			              <div id="alarmGradeTip" style="float:left; margin-left:70px;"></div></td>
			        </tr> 
					<tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.type"/><s:text name="common.lable.point" /></strong></td>
			            <td  class="tdInput" valign="top">
			             <select name="alarmroster.type" id="type" class="newSelect">	
						         <option value="" <s:if test="%{alarmroster.type==null}">selected</s:if>><s:text name="common.lable.select"/></option>   
						         <option value="0" <s:if test="%{alarmroster.type==0}">selected</s:if>><s:text name="alarmroster.type.0"/></option>     		
							     <option value="1" <s:if test="%{alarmroster.type==1}">selected</s:if>><s:text name="alarmroster.type.1"/></option>
						</select>
			            </td>
			            <td class="tdVad" valign="top"><div id="eventSourceTip">&nbsp;</div></td>
		        	</tr>  
					<tr>
	
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.startTime"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
			               <div class="select_date1 input_mt" style="_margin-top:20px; *margin-left:0px;">	
			               	<input type="text" id="datepicker" name="alarmroster.startTime"  class="select_date_input" onFocus="this.blur(); "/>
							</div>
			            </td>  
			         </tr>
			         
			        <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.endTime"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
					            <div class="select_date1 input_mt" style="_margin-top:20px; *margin-left:0px;">
		              		  	<input type="text" id="datepicker1" name="alarmroster.endTime" class="select_date_input" onFocus="this.blur(); "/>
		              			</div>
   					</td>  
			         </tr>     
                    </table> 
                   </form>                         
                </div>
              	<!--滚动条 scroll end-->
                <div class="buttonbar">
                <!--按钮条 buttonbar start-->
                	<div class="buttonbarLef">
                       <a href="#" class="btnImp btn_disable testMessage3" onclick="validate();return false;"><s:property value="getText('common.button.search')"></s:property></a>
                           <a href="alarmrosterList.action" class="button btn_disable" ><s:property value="getText('common.button.cancel')"></s:property></a>                
                    </div>
                <!--按钮条 buttonbar end-->
          		</div>
        </div>        
        <!--工作区 workarea end-->     
        
        
    </div>
 	<!--内容部分 main end-->    