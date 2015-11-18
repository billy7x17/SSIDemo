<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
//$(document).ready(function(){
$(function(){
	  $.formValidator.initConfig({validatorgroup:"1",wideword:false});
	//厂家标识验证
	$("#manufactureID").formValidator({
		validatorgroup:"1",
		onshow:'<s:text name="validator.manufactureID.onshow" />',
		onfocus:'<s:text name="validator.manufactureID.onfocus" />',
		oncorrect:'<s:text name="validator.manufactureID.oncorrect"/>'
		})
    .inputValidator({
        min:1,
        max:64,
        onerrormin:'<s:text name="validator.manufactureID.onerrormin" />',
        onerrormax:'<s:text name="validator.manufactureID.onerrormax" />'
    });
	//类型
	$("#type").formValidator(
			   {validatorgroup:"1",
				onshow:'<s:text name="validator.type.onshow" />',
			   onfocus:'<s:text name="validator.type.onfocus" />',
			    oncorrect:'<s:text name="validator.type.oncorrect" />'})
			 .inputValidator({min:1,
			      onerrormin:'<s:text name="validator.type.onerrormin" />',
			       onerror:'<s:text name="validator.type.onerror" />'});
		
	//阀值
	$("#thresholdID").formValidator(
			   {validatorgroup:"1",
				onshow:'<s:text name="validator.thresholdID.onshow" />',
			   onfocus:'<s:text name="validator.thresholdID.onfocus" />',
			    oncorrect:'<s:text name="validator.thresholdID.oncorrect" />'})
			 .inputValidator({min:1,
			      onerrormin:'<s:text name="validator.thresholdID.onerrormin" />',
			       onerror:'<s:text name="validator.thresholdID.onerror" />'});

    // 映射告警级别
	$("#alarmGrade")
        .formValidator({
            validatorgroup:"1",
            onshow:'<s:text name="validator.alarmGrade.onshow" />',
            onfocus:'<s:text name="validator.alarmGrade.onfocus" />',
            oncorrect:'<s:text name="validator.alarmGrade.oncorrect" />'
        })
        .inputValidator({
            min:1,
            onerrormin:'<s:text name="validator.alarmGrade.onerrormin" />',
            onerror:'<s:text name="validator.alarmGrade.onerror" />'
        });
	
	//描述验证
	$("#description").formValidator({
		validatorgroup:"1",
		onshow:'<s:text name="validator.description.onshow" />',
		onfocus:'<s:text name="validator.description.onfocus" />',
		oncorrect:'<s:text name="validator.description.oncorrect"/>'}).
		inputValidator({min:0,max:512,onerrormax:'<s:text name="validator.description.onerrormax" />'});

	  $.formValidator.initConfig({validatorgroup:"2",wideword:false});
		//厂家标识验证
		$("#manufactureID").formValidator({
			validatorgroup:"2",
			onshow:'<s:text name="validator.manufactureID.onshow" />',
			onfocus:'<s:text name="validator.manufactureID.onfocus" />',
			oncorrect:'<s:text name="validator.manufactureID.oncorrect"/>'
			})
        .inputValidator({
            min:1,
            max:64,
            onerrormin:'<s:text name="validator.manufactureID.onerrormin" />',
            onerrormax:'<s:text name="validator.manufactureID.onerrormax" />'});
		//类型
		$("#type").formValidator(
				   {validatorgroup:"2",
					onshow:'<s:text name="validator.type.onshow" />',
				   onfocus:'<s:text name="validator.type.onfocus" />',
				    oncorrect:'<s:text name="validator.type.oncorrect" />'})
				 .inputValidator({min:1,
				      onerrormin:'<s:text name="validator.type.onerrormin" />',
				       onerror:'<s:text name="validator.type.onerror" />'});

		//规则
		$("#rowID").formValidator(
				   {validatorgroup:"2",
					onshow:'<s:text name="validator.rowID.onshow" />',
				   onfocus:'<s:text name="validator.rowID.onfocus" />',
				    oncorrect:'<s:text name="validator.rowID.oncorrect" />'})
				 .inputValidator({min:1,
				      onerrormin:'<s:text name="validator.rowID.onerrormin" />',
				       onerror:'<s:text name="validator.rowID.onerror" />'});

		// 映射告警级别
		$("#alarmGrade")
        .formValidator({
            validatorgroup:"2",
            onshow:'<s:text name="validator.alarmGrade.onshow" />',
            onfocus:'<s:text name="validator.alarmGrade.onfocus" />',
            oncorrect:'<s:text name="validator.alarmGrade.oncorrect" />'
        })
        .inputValidator({
            min:1,
            onerrormin:'<s:text name="validator.alarmGrade.onerrormin" />',
            onerror:'<s:text name="validator.alarmGrade.onerror" />'
        });
		
		//描述验证
		$("#description").formValidator({
			validatorgroup:"2",
			onshow:'<s:text name="validator.description.onshow" />',
			onfocus:'<s:text name="validator.description.onfocus" />',
			oncorrect:'<s:text name="validator.description.oncorrect"/>'}).
			inputValidator({min:0,max:512,onerrormax:'<s:text name="validator.description.onerrormax" />'});

	var errorMsg = "<s:property value='errorMsg'/>";
	if(errorMsg != null && errorMsg != ""){
		$("#msgTip").attr("class","msgErrors");
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}

		$("tr").each(function(){
			var tr_bgColor = $(this).css("background-color");
		//	alert(tr_bgColor);	
			$(this).hover(function(){
			    $(this).css("background-color","transparent");
			},function(){
				$(this).css("background-color","transparent");
			})
		})

		//隐藏阀值名称或者规则名称
		var type = "<s:property value='alarmroster.type'/>";
		if(type==""){
			var typeTr = $("tr[class='type']"); 
			for(var i=0;i<typeTr.length;i++){
				typeTr[i].style.display = "none";
			}
		}
		else if(type=="0"){
			var typeTr = $("tr[class='type']"); 
			typeTr[1].style.display = "none";
		}
		else if(type=="1"){
			var typeTr = $("tr[class='type']"); 
			typeTr[0].style.display = "none";	
		}
});
function typeClick(){
	var selectValue=$("#type").val(); 
	if(selectValue== ""){
		var typeTr = $("tr[class='type']"); 
		typeTr[0].style.display = "none";
		typeTr[1].style.display = "none";
		} 
	else if(selectValue == 0){
		var typeTr = $("tr[class='type']"); 
		typeTr[0].style.display = "";
		typeTr[1].style.display = "none";

	}
	else if(selectValue == 1){
		var typeTr = $("tr[class='type']"); 
		typeTr[0].style.display = "none";
		typeTr[1].style.display = "";
	}
} 
function validate(){
	var selectValue=$("#type").val();   
	if(selectValue==0){
		formCheck = $.formValidator.pageIsValid("1");
	}else{
		formCheck = $.formValidator.pageIsValid("2");
	}
	if(formCheck){
		$("#mainForm").attr("action","alarmrosterAdd.action");
		$("#mainForm").submit();
	}else{
		return false;
	}
} 

function validateTextarea(){
	if($("#description").val().length > 512)
	  {
		$("#description").val($("#description").val().substring(0,512));
	  }
}


function getText(){
	var checkText=$("#alarmIdentify").find("option:selected").text();
	//alert(checkText);
	$("#alarmIdentifyName").val(checkText);

}
</script>

<!-- 修改table列宽样式 -->      
<style type="text/css">
.tdInput{width:350px;}
.input{width:350px;}
.newSelect{width:350px;}
.confWrap{width:340px;;}
.newTextarea{width:338px;}
</style>

		<%-- 当前位置 --%>
		<div class="rightToolbar">
		  <div class="rightToolbarCrumbs">
		    <table cellpadding="0" cellspacing="0" height="40">
		      <tr>
		        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
		        <td>
		          <h3>添加规则匹配</h3>
		          告警管理->告警配置->规则匹配管理->添加
	          </td>
		      </tr>
		    </table>
		  </div>
		</div>
		 <!--内容部分 main star-->
		     <div class="rightDisplayFwSj">
                	<form id="mainForm" method="post" action="alarmrosterAdd.action" >       	  
            		<table class="addTable" align="center" cellpadding="0" cellspacing="0" border="0">
                    <!-- 厂家标识 -->
                     <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.manufactureID"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
			              <input type="text" id="manufactureID" class="input" name="alarmroster.manufactureID" maxlength="64" value="<s:property value="alarmroster.manufactureID"/>"/>
			            </td>
			            <td class="tdVad" valign="top"><div id="manufactureIDTip"></div></td>
			         </tr>
          			 <!--类型-->
			         
			        <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.type"/><s:text name="common.lable.point" /></strong></td>
			            <td  class="tdInput" valign="top">
			              <select id="type" name="alarmroster.type" class="newSelect" onblur="typeClick();return false;">
					               		<option value="">------------------------------请选择---------------------------------------</option>
					                    <option value="0" <s:if test="alarmroster.type==0">selected</s:if>><s:text name="alarmroster.type.0"/></option>
					                 	<option value="1" <s:if test="alarmroster.type==1">selected</s:if>><s:text name="alarmroster.type.1"/></option>
					              	 	</select>
			            </td>
			            <td class="tdVad" valign="top"><div id="typeTip"></div></td>
			        </tr>
				
					   <!-- 阀值名称 -->
			          <tr class="type">
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.thresholdID"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
			               <s:select list="idList" listKey="thresholdID" listValue="eventName" name="alarmroster.thresholdID" id="thresholdID" cssClass="newSelect" headerKey="" headerValue="------------------------------请选择---------------------------------------">          
	         			  </s:select>
			            </td>
			            <td class="tdVad" valign="top"><div id="thresholdIDTip"></div></td>
			         </tr>
			         
			          <!-- 规则名称 -->
			          <tr class="type">
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.rowID"/><s:text name="common.lable.point" /></strong></td>
			            <td class="tdInput" valign="top">
			               <s:select list="rowIDList" listKey="rowID" listValue="alarmTitle" name="alarmroster.rowID" id="rowID" cssClass="newSelect" headerKey="" headerValue="------------------------------请选择---------------------------------------">          
	         			  </s:select>
			            </td>
			            <td class="tdVad" valign="top"><div id="rowIDTip"></div></td>
			         </tr>
			         <!-- 告警级别
					 <tr>
			           <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.alarmGrade"/><s:text name="common.lable.point" /></strong></td>
			            <td  class="tdInput" valign="top" colspan="2">
			              <div style="float:left;">
			               <input <s:if test="%{alarmGrade==null}">checked</s:if>   type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="2" <s:if test="alarmroster.alarmGrade==2">checked</s:if><s:elseif test="alarmroster.alarmGrade==null">checked</s:elseif> /><s:text name="alarmroster.alarmGrade.2"/>
			              </div>
			              <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="3" <s:if test="alarmroster.alarmGrade==3">checked</s:if>/><s:text name="alarmroster.alarmGrade.3"/>
			              </div>
			              <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="4" <s:if test="alarmroster.alarmGrade==4">checked</s:if>/><s:text name="alarmroster.alarmGrade.4"/>
			              </div>
			               <div style="float:left; margin-left:10px;">
			                <input  type="radio" id="alarmGrade" name="alarmroster.alarmGrade" value="5" <s:if test="alarmroster.alarmGrade==5">checked</s:if>/><s:text name="alarmroster.alarmGrade.5"/>
			              </div>
			              <div id="alarmGradeTip" style="float:left; margin-left:70px;"></div></td>
			        </tr> 
			         -->
			         <tr>
                        <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point" /><s:text name="alarmroster.alarmGrade"/><s:text name="common.lable.point" /></strong></td>
                        <td class="tdInput" valign="top">
                           <s:select list="levelList" listKey="alarmGrade" listValue="alarmGradeName" name="alarmroster.alarmGrade" id="alarmGrade" cssClass="newSelect" headerKey="" headerValue="------------------------------请选择---------------------------------------">          
                        </s:select>
                        </td>
                        <td class="tdVad" valign="top"><div id="alarmGradeTip"></div></td>
                     </tr>
                     
 					<!-- 描述 -->
                    <tr>
				            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.description"/><s:text name="common.lable.point" /></strong></td>
				            <td  class="tdInput" valign="top">        
				              <div class="confWrap">
				                        <label for="textarea"></label>
				                        <textarea name="alarmroster.description" id="description" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"><s:property value="alarmroster.description"/></textarea>
				                      </div>
				            </td>
				            <td class="tdVad" valign="top"><div id="descriptionTip"></div></td>
				   </tr> 
                    </table>  
                   </form>               
              	</div>
              	<!-- 提示信息start -->
              	<div class="messages succcess" style="top:25px">
            		<div id="msgTip" class="msgSuccess" ></div>
        		</div>
                <!-- 提示信息end-->	
 	
 		<%-- 按钮栏 --%>
		<div class="serverEventButton">
		    <ul class="rightToolButtonjhjFwSj">
		        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
		        <li><a href="alarmrosterList.action" class="buttonFwSj" ><s:property value="getText('common.button.cancel')" /></a></li>
		    </ul>
		</div> 