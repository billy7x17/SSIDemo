<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style type="text/css">
.tdInput{width:350px;}
.newSelect{width:350px;}
.input{width:350px;}
.confWrap{width:340px;}
.newTextarea{width:338px;}
</style>

<script type="text/JavaScript">
//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
$(function(){
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

	 //隐藏业务实例
    var trs = $("tr[class='system']");    
    for(i = 0; i < trs.length; i++){      
      trs[i].style.display = "none";
    }  
    
});

$(function(){
	$.formValidator.initConfig({formid:"add_form",wideword:false});
	//名称
	$("#thresholdName")
	    .formValidator
	    (
	    	{onshow:'<s:text name="threshold.js.onshow.thresholdName"/>'
		    ,onfocus:'<s:text name="threshold.js.onshow.thresholdName"/>'
			,oncorrect:'<s:text name="threshold.js.oncorrect"/>'}
	    )
	    .inputValidator
	    ({
	   		min:1,max:32, 
	   		empty:
	   			{	
	   				leftempty:false,
	   				rightempty:false,
	           	 	emptyerror:'<s:text name="threshold.js.emptyerror"/>'
	           	},
	         onerror:'<s:text name="threshold.js.not.null"/>',
	         onerrormax:'<s:text name="threshold.js.onshow.32char"/>'
	     });

	 	//描述验证
		$("#description")
		.formValidator({onshow:'请输入告警内容，最多输入200个字符 <br/>支持通配符,告警时间：${create_time},设备IP：${device_ip},设备名称：${device_name},告警值：${alarMessage} , 性能真实值：${value}&nbsp;',
            onfocus:'请输入告警内容，最多输入200个字符 <br/>支持通配符,告警时间：${create_time},设备IP：${device_ip},设备名称：${device_name},告警值：${alarMessage} , 性能真实值：${value}&nbsp;',
            oncorrect:'正确 <br/>支持通配符,告警时间：${create_time},设备IP：${device_ip},设备名称：${device_name},告警值：${alarMessage} , 性能真实值：${value} &nbsp;'})
		.inputValidator({min:0,max:200,onerrormax:'最多输入200个字符 '+'<br/>支持通配符,告警时间：${create_time},设备IP：${device_ip},设备名称：${device_name},告警值：${alarMessage} , 性能真实值：${value}&nbsp;'});

		//设备
		$("#agentId")
        .formValidator({onshow:'<s:text name="threshold.headerValue" />',
            onfocus:'<s:text name="threshold.headerValue" />',
            oncorrect:'<s:text name="threshold.js.oncorrect" />'})
        .inputValidator({min:1,max:64,
            empty:{leftempty:false,rightempty:false,
                emptyerror:'<s:text name="threshold.js.emptyerror" />'},
                onerror:'<s:text name="threshold.headerValue" />',
                onerrormax:'<s:text name="threshold.headerValue" />'});
		//OID
		$("#oid")
        .formValidator({onshow:'<s:text name="threshold.headerValue" />',
            onfocus:'<s:text name="threshold.headerValue" />',
            oncorrect:'<s:text name="threshold.js.oncorrect" />'})
        .inputValidator({min:1,max:4096,
            empty:{leftempty:false,rightempty:false,
                emptyerror:'<s:text name="threshold.js.emptyerror" />'},
                onerror:'<s:text name="threshold.headerValue" />',
                onerrormax:'<s:text name="threshold.headerValue" />'});

        
		//告警等级
        $("#level")
        .formValidator({onshow:'<s:text name="threshold.headerValue" />',
            onfocus:'<s:text name="threshold.headerValue" />',
            oncorrect:'<s:text name="threshold.js.oncorrect" />'})
        .inputValidator({min:1,max:64,
            empty:{leftempty:false,rightempty:false,
                emptyerror:'<s:text name="threshold.js.emptyerror" />'},
                onerror:'<s:text name="threshold.headerValue" />',
                onerrormax:'<s:text name="threshold.headerValue" />'});


        //阀值类型
          $("#dealType")
          .formValidator({onshow:'<s:text name="validator.dealType.onshow" />',
              onfocus:'<s:text name="validator.dealType.onfocus" />',
              oncorrect:'<s:text name="threshold.js.oncorrect" />'})
          .inputValidator({min:1,max:64,
              empty:{leftempty:false,rightempty:false,
                  emptyerror:'&nbsp;'},
                  onerrormin:'<s:text name="validator.dealType.onerrormin" />',
                  onerrormax:'<s:text name="validator.dealType.onerrormax" />'});
          

        //阀值标题
          $("#tcTitle")
          .formValidator({onshow:'<s:text name="validator.tcType.onshow" />',
              onfocus:'<s:text name="validator.tcType.onfocus" />',
              oncorrect:'<s:text name="threshold.js.oncorrect" />'})
          .inputValidator({min:1,max:4096,
              empty:{leftempty:false,rightempty:false,
                  emptyerror:'&nbsp;'},
                  onerrormin:'<s:text name="validator.tcType.onerrormin" />',
                  onerrormax:'<s:text name="validator.tcType.onerrormax" />'});
            
          $("#valueTip").html('<s:text name="threshold.js.onshow.thresholdValue" />'); //请输入阈值，必须为数字，最多15个字符
          $("#valueTip").addClass("onShow");


		  //告警唯一标识
          $("#alarmIdentityID").formValidator(
        	        {onshow:'<s:text name="validator.ruleName.onshow" />',
        	         onfocus:'<s:text name="validator.ruleName.onfocus" />',
        	         oncorrect:'<s:text name="common.validator.oncorrect" />'})
        	         .inputValidator({min:0,max:256,
        	                 empty:{
        	                 leftempty:false,
        	                 rightempty:false,
        	                 emptyerror:'<s:text name="common.validator.emptyerror" />'},
        	                 onerrormin:'<s:text name="validator.ruleName.onerrormin" />',
        	                 onerrormax:'<s:text name="validator.ruleName.onerrormax" />'});
       /**
        	         .functionValidator({
        	                 fun:function(val,elem){
        	                 if(val == null || val == ""){
        	                    return '<s:text name="common.validator.emptyerror" />';
        	                 }else{
        	                    var exist = false;
        	                	$.ajax({  
        	                         async: false,
        	                         url: "thresholdBeforeAddValidateAlarmIdentityID.action", 
        	                         type: "post",   
        	                         dataType:"text",
        	                         data : {'alarmIdentityID': val},
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
          **/

   
				//告警的影响描述
        	    $("#alarmImpact").formValidator({onshow:'<s:text name="threshold.js.onshow.alarmImpact" />',
          			onfocus:'<s:text name="threshold.js.onshow.alarmImpact" />',
          			oncorrect:'<s:text name="threshold.js.oncorrect" />'})
          		.inputValidator({min:0,max:512,onerrormax:'<s:text name="threshold.js.512char" />'});
        	
        	  //业务实例
        	    $("#systemId")
        	    .formValidator({onshow:'<s:text name="threshold.headerValue" />',
        	        onfocus:'<s:text name="threshold.headerValue" />',
        	        oncorrect:'<s:text name="common.validator.oncorrect" />'})
        	    .inputValidator({min:0,max:64,
        	        empty:{leftempty:false,rightempty:false,
        	            emptyerror:'<s:text name="validator.validator.emptyerror" />'},
        	            onerror:'<s:text name="threshold.headerValue" />',
        	            onerrormax:'<s:text name="threshold.headerValue" />'});

        	   //阀值条件
        	    $("#perCondition")
                .formValidator({onshow:'<s:text name="validator.perCondition.onshow" />',
                    onfocus:'<s:text name="validator.perCondition.onfocus" />',
                    oncorrect:'<s:text name="common.validator.oncorrect" />'})
                .inputValidator({min:1,max:64,
                    empty:{leftempty:false,rightempty:false,
                        emptyerror:'<s:text name="validator.validator.emptyerror" />'},
                        onerror:'<s:text name="validator.perCondition.onerror" />',
                        onerrormin:'<s:text name="validator.perCondition.onerrormin" />'});    

});

//验证告警区间
function validateObject(){
	  
	  var value1 = $("#value").val();
	  var value2 = $("#value2").val();
	  var perCondition = $("#perCondition").val();
	  var reg = new RegExp("^([+-]?)\\d*\\.?\\d+$");
    
     if(value1 == "" || value1 == null || !reg.test(value1)) { // value1.search("^-?\\d+$")!=0){
		  $("#valueTip").html('<s:text name="threshold.js.onshow.thresholdValue" />'); //请输入阈值，必须为数字，最多15个字符
          $("#valueTip").removeClass();
          $("#valueTip").addClass("onError");
          return false;
	  }

	  if(perCondition==3 &&(value2 == "" || value2 == null || !reg.test(value2))){
		  $("#valueTip").html('<s:text name="threshold.js.onshow.thresholdMaxValue" />'); //请输入阀值区间最大值，必须为整数，最多15个字符
          $("#valueTip").removeClass();
          $("#valueTip").addClass("onError");
          return false;
	  }

	  // alert(parseFloat(value2)<=parseFloat(value1));
	  if(perCondition ==3 && parseFloat(value2)<=parseFloat(value1)){
		  $("#valueTip").html('<s:text name="threshold.js.alarm.value2" />' //阀值区间最大值不能小于等于阈值A的值为2
                  +'<s:text name="threshold.js.alarm.doNot.lss" />'
                  +'<s:text name="threshold.js.alarm.value1" />'+value1);
          $("#valueTip").removeClass();
          $("#valueTip").addClass("onError");
          return false;
      }

	  $("#valueTip").html('<s:text name="threshold.js.oncorrect" />'); //正确
      $("#valueTip").removeClass();
      $("#valueTip").addClass("onCorrect");
      return true;
}

function validate(){
	var formCheck = $.formValidator.pageIsValid("1");
	var throshlodValue = $("#value").val();
	var oidSelect = $("#oid").val();
	var agentIdSelect = $("#agentId").val();


    //校验设备类型为业务实例时，业务实例必填
    if(!checkSystem()) {
        return false;
    }
    
	if(!$("#valueTip").hasClass("onCorrect")){
		$("#valueTip").removeClass();
		$("#valueTip").addClass("onError");
    }else if(formCheck){
		$("#mainForm").submit();
	}else{
		return false;
	}
} 

//获取设备性能指标
function getDeviceOid(){
	//alert('getDeviceOid');
	var selectValue = $('#agentId').val();
    if (selectValue=='20') {  
        $(".system").show();
    }else{
    	$(".system").hide();
    }

	 var url = 'getDeviceOid.action';
     var params = {
             deviceId:$("#agentId").val()
     };
    jQuery.post(url, params, callbackFun, 'text');
}

function callbackFun(data)
{	
	var jsonObj=eval("("+data+")");
	var oidLi = jsonObj.mib;
	var titleLi = jsonObj.title;
    $("#oid").empty();
    $("#oid").append("<option value='null'>"+'<s:text name="lable.select" />'+"</option>");
	for(var key in oidLi){
       if(typeof(oidLi[key].name)!="undefined"){
       	$("#oid").append("<option value='"+oidLi[key].id+"'>"+oidLi[key].name+"</option>");
       }
    } 

	$("#tcTitle").empty();
    $("#tcTitle").append("<option value='null'>"+'<s:text name="lable.select" />'+"</option>");
    for(var key in titleLi){
       if(typeof(titleLi[key].tcId)!="undefined"){
        $("#tcTitle").append("<option value='"+titleLi[key].tcId + "' title='" + titleLi[key].tcTitle +"'>"+titleLi[key].tcTitle+"</option>");
       }
    } 
}
function validateTextarea(id ,length){
	if($("#" + id).val().length > length)
	  {
		$("#" + id).val($("#" + id).val().substring(0,length));
	  }
}

//校验业务实例
function checkSystem(){
    var agentId = $("#agentId").val();
    var systemId = $("#systemId").val();
   // alert(agentId + '=====' + systemId);
    if (agentId==20 && systemId=='') {
        $("#systemIdTip").html('<s:text name="threshold.headerValue" />'); 
        $("#systemIdTip").removeClass();
        $("#systemIdTip").addClass("onError");
        return false;
    }
    return true;   
}


//修改阀值条件
function changeCondition(){
    var perCondition = $("#perCondition").val();
    //alert(typeId + '=====' + systemId);
    if (perCondition==1 || perCondition==2 || perCondition=='') {
        $("#value2Div").hide(); 
    }else{
    	$("#value2Div").show(); 
    }
    //修改后校验阀值
    validateObject();
}

</script>

<%-- 当前位置 --%>
	<div class="rightToolbar">
	  <div class="rightToolbarCrumbs">
	    <table cellpadding="0" cellspacing="0" height="40">
	      <tr>
	        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
	        <td>
	          <h3>添加性能阀值</h3>
		          告警管理->告警配置->性能阀值管理->添加
          </td>
	      </tr>
	    </table>
	  </div>
	</div>
	 <!--内容部分 main star-->
	     <div class="rightDisplayFwSj">
                   	<form id="mainForm" method="post" action="thresholdAdd.action" >
                   	
      					<div>
      					
		                   <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
		                     <%--
		                     <tr>
					            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.alarmIdentityID"/><s:text name="common.lable.point"/></strong></td>
					            <td class="tdInput" valign="top" style="width:300px;">
					              <input type="text" id="alarmIdentityID" name="alarmIdentityID" class="input" maxlength="256"/>
					            </td>
					            <td class="tdVad" valign="top"><div id="alarmIdentityIDTip"></div></td>
					         </tr>
					          --%>
		                     <tr>
					            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.name"/><s:text name="common.lable.point"/></strong></td>
					            <td class="tdInput" valign="top">
					              <input type="text" id="thresholdName" name="thresholdName" class="input" maxlength="32"/>
					            </td>
					            <td class="tdVad" valign="top"><div id="thresholdNameTip"></div></td>
					         </tr>
					         <tr>
					            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.thresholdDeviceName"/><s:text name="common.lable.point"/></strong></td>
					            <td class="tdInput" valign="top">
			                    <s:select list="deviceListType" listKey="rowId" listValue="agentName" name="agentId" id="agentId" cssClass="newSelect" headerKey="" headerValue="%{getText('lable.select')}" onchange="getDeviceOid(); return false;">          
			                    </s:select>
					            </td>
					            <td class="tdVad" valign="top"><div id="agentIdTip"></div></td>
					         </tr>
					         
					          <tr class="system">
		                        <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.system"/><s:text name="common.lable.point"/></strong></td>
		                        <td class="tdInput" valign="top">
		                         <s:select list="systemList" listKey="systemId" listValue="systemName" name="systemId" id="systemId" cssClass="newSelect" headerKey="" headerValue="%{getText('lable.select')}" >          
		                        </s:select>
		                        </td>
		                        <td class="tdVad" valign="top"><div id="systemIdTip"></div></td>
		                     </tr>

                             <tr>
                                <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.OID"/><s:text name="common.lable.point"/></strong></td>
                                <td class="tdInput" valign="top">
                                  <select id="oid" name="oid" class="newSelect" >
                                    <option value="null"><s:text name="lable.select"/></option>
                                  </select>
                                </td>
                                <td class="tdVad" valign="top"><div id="oidTip"></div></td>
                             </tr>
                             
                             <tr>
                                <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.thresholdTitle"/><s:text name="common.lable.point"/></strong></td>
                                <td class="tdInput" valign="top">
                                 <select name="tcTitle" id="tcTitle" class="newSelect" >
                                    <option value="null"><s:text name="lable.select"/></option>
                                  </select>
                                </td>
                                <td class="tdVad" valign="top"><div id="tcTitleTip"></div></td>
                             </tr>
							 
							 <tr>
					            <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.level"/><s:text name="common.lable.point"/></strong></td>
					            <td class="tdInput" valign="top">
					               <s:select list="levelList" listKey="levelId" listValue="levelName" name="level" id="level" cssClass="newSelect" headerKey="" headerValue="%{getText('lable.select')}">          
			                    </s:select>
					            </td>
					            <td class="tdVad" valign="top"><div id="levelTip"></div></td>
					         </tr>

                             
                              <tr>
                                <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.sign" /><s:text name="common.lable.point"/></strong></td>
                                <td class="tdInput" valign="top">
				                     <s:select list="#{1:'等于',2:'不等于',3:'区间'}" listKey="key" listValue="value" id="perCondition" name="perCondition" cssClass="newSelect" headerKey="" headerValue="%{getText('lable.select')}" onchange="changeCondition(this);">
                                     </s:select>
                                </td>
                                <td class="tdVad" valign="top"><div id="perConditionTip"></div></td>
                             </tr>
                             
                             <tr>
                                <td class="tdLabel" align="right" valign="top"><strong><s:text name="common.red.point"/><s:text name="threshold.threshold" /><s:text name="common.lable.point"/></strong></td>
                                <td class="tdInput" valign="top">
                                  <div style="float:left; margin-left:2px;">
                                  <input type="text" id="value" name="value" class="alarmInput" maxlength="15" onblur="validateObject()" style="width:80px;"/>
                                   </div>
                                   <div id="value2Div" style="float:left; margin-left:10px; display:none;">                                                     
                                                                                         至&nbsp;<input type="text" id="value2" name="value2" class="alarmInput" maxlength="15" onblur="validateObject()" style="width:80px;"/>
                                    </div>
                                </td>
                                <td class="tdVad" valign="top"><div id="valueTip"></div></td>
                             </tr>
					         
					         
                           <tr>
                              <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.agentDesc"/><s:text name="common.lable.point" /></strong></td>
                              <td  class="tdInput" valign="top" >        
                                <div class="confWrap">
                                  <label for="textarea"></label>
                                  <textarea name="description" id="description" class="newTextarea" onkeydown="validateTextarea('description',200);" onkeyup="validateTextarea('description',200);"></textarea>
                                </div>
                              </td>
                              <td class="tdVad" valign="top"><div id="descriptionTip"></div></td>
                           </tr> 
                             <tr>
                              <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.alarmImpact"/><s:text name="common.lable.point" /></strong></td>
                              <td  class="tdInput" valign="top" >        
                                <div class="confWrap">
                                  <label for="textarea"></label>
                                  <textarea name="alarmImpact" id="alarmImpact" class="newTextarea" onkeydown="validateTextarea('alarmImpact',512);" onkeyup="validateTextarea('alarmImpact',512);"></textarea>
                                </div>
                              </td>
                              <td class="tdVad" valign="top"><div id="alarmImpactTip"></div></td>
                           </tr> 
							 
		                    </table>  
            		 </div>
  
                   </form>               
              	</div>
              	<!-- 提示信息start -->
              	<div class="messages succcess">
            		<div id="msgTip" class="msgSuccess" style="top:25px"></div>
        		</div>
                <!-- 提示信息end-->	
		<div class="serverEventButton">
		    <ul class="rightToolButtonjhjFwSj">
		        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
		        <li><a href="thresholdList.action"  class="buttonFwSj" ><s:property value="getText('common.button.cancel')" /></a></li>
		    </ul>
	</div>  