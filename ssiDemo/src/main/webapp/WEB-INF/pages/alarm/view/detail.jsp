<script>
var winHeight = $(window).height() - 17; //17px为workarea的margin-top
$(".baseTab").height(winHeight - ($(".BasicInformation").height()+11) - (18 + 12*2));
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/pages/alarm/addPerson.jsp" %>
<div class="BasicInformation">
	<h2><s:property value="domain.agentName"/></h2>
    <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
    <%-- 最新的为3-警告，2-一般，1-重要，0-紧急 --%>
    <s:if test="domain.alarmGrade==3">
         <img class="warn" src="<%=request.getContextPath()%>/themes/blue/images/warn-lemon-40.png"/>
	</s:if>
	<s:elseif test="domain.alarmGrade==2">
	    <img class="warn"  src="<%=request.getContextPath()%>/themes/blue/images/warn-yellow-40.png"/>
	</s:elseif>
	<s:elseif test="domain.alarmGrade==1">
        <img class="warn"  src="<%=request.getContextPath()%>/themes/blue/images/warn-org-40.png"/>
	</s:elseif>
	<s:elseif test="domain.alarmGrade==0">
        <img class="warn"  src="<%=request.getContextPath()%>/themes/blue/images/warn-red-40.png"/>
	</s:elseif>
</div>

<%-- 详细信息 --%>
<div onclick="changeDiv(1,this)" class="baseTitl click"><s:text name="web.title.detail" /></div>
<s:if test="#session.authenticater.authIdList.contains('05_01_06_00')">
<div onclick="changeDiv(2,this)" class="baseTitl"><s:text name="web.mail.title"/></div>
</s:if>
<div id="infoDiv" class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
	
	<!-- 页面显示顺序为：
                       告警对象(IP) > 告警级别            > 告警标题 >
      	  告警内容                            > 首次发生时间  > 告警分类 >
                       告警状态(确认状态) > 设备类型            > 重复次数 > 
                       最后发生时间                  > 告警标识ID
      -->
	
     <!-- 不显示流水号 -->
     <!-- 
     <tr>
        <td width="60">流水号<s:text name="common.lable.point" /></td>
         <th width="175"><s:property value="domain.alarmID"/></th>
     </tr>
      -->
     <tr>
        <td><s:text name="field.label.alarmGrade" /><s:text name="common.lable.point" /></td>
         <%-- <th><s:property value="domain.alarmGradeName"/></th> --%>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.alarmGradeName"/>" /></th>
     </tr>
     <tr>
        <td><s:text name="field.label.originalAlarmGrade" /><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.originalAlarmGrade"/>" /></th>
     </tr>
     <tr>
        <td><s:text name="field.label.alarmTitle" /><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.alarmTitle"/>" /></th>
     </tr>
     <tr>
        <td><s:text name="field.label.alarmContent" /><s:text name="common.lable.point" /></td>
         <th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="domain.alarmContent"/></textarea></th>
     </tr>
     <!-- 
     <tr>
        <td>对象标识<s:text name="common.lable.point" /></td>
         <th><s:property value="domain.alarmIP"/></th>
     </tr>
      -->
     <tr>
		  <td><s:text name="web.table.columnName.firstAlarmTime"></s:text><s:text name="common.lable.point" /></td>
	      <th><input type="text" readonly="readonly" value="<s:property value="domain.firstAlarmTime"/>" /></th>
	 </tr> 
	 
     <tr>
        <td><s:text name='web.table.columnName.alarmType'/><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.alarmType"/>" /></th>
     </tr>
     <tr>
			<td><s:text name="field.label.LocalTableIDRef" /><s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.LocalTableIDRef" />" /></th>
		</tr>
		<tr>
			<td><s:text name="field.label.siteName" /><s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="domain.siteName" />" /></th>
		</tr>
     
     <tr>
        <td><s:text name='web.table.columnName.alarmStatus'/><s:text name="common.lable.point" /></td>
         <th><input type="text" id="confirmStat" readonly="readonly" value="<s:property value="domain.confirmStatus"/>" /></th>
     </tr>
     <tr>
        <td><s:text name="web.table.columnName.clearStatus"></s:text><s:text name="common.lable.point" /></td>
         <th><input type="text" id="clearStatus" readonly="readonly" value="<s:property value="domain.clearStatus"/>" /></th>
     </tr>
     
     <tr>
        <td><s:text name='web.table.columnName.deviceTypeName'/><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.deviceTypeName"/>" /></th>
     </tr>
     <tr>
        <td><s:text name='web.table.columnName.count'/><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.count"/>" /></th>
      </tr>
     <tr>
        <td><s:text name='web.table.columnName.alarmTime'/><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.alarmTime"/>" /></th>
     </tr>
     <tr>
        <td><s:text name='web.table.columnName.alarmIdentify'/><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.alarmIdentify"/>" /></th> 
     </tr>
     
     <!-- 
     <s:if test="#session.environmentType==3">
     <tr>
        <td>业务类型<s:text name="common.lable.point" /></td>
        <th>公众服务云</th>
     </tr>
     </s:if>
      -->
      
      <tr class="confirmStatus">
        <td><s:text name="web.table.columnName.confirmTime"></s:text><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.confirmTime"/>" /></th>
      </tr>
      <tr class="confirmStatus">
        <td><s:text name="web.table.columnName.confirmPerson"></s:text><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.confirmPerson"/>" /></th>
      </tr>
      <tr class="confirmStatus">
        <td><s:text name="web.table.columnName.confirmDesc"></s:text><s:text name="common.lable.point" /></td>
         <th><input type="text" readonly="readonly" value="<s:property value="domain.confirmDesc"/>" /></th>
      </tr>
  <!-- </table>
  	<table width="250" border="0" cellspacing="0" cellpadding="0"> -->
     <tr>
        <td width="60"><s:text name="web.table.columnName.operationDesc" /><s:text name="common.lable.point" /></td>
        <th><s:textarea id="operatorDesc" name="domain.operatorDesc" onkeydown="validateOperatorDesc();" onkeyup="validateOperatorDesc();"/></th>
     </tr>
     </table>
	<ul style="padding:2px;border: 0;">
		<!--<s:if test="domain.confirmStatus=='未确认'">-->
		<s:if test="#session.authenticater.authIdList.contains('05_01_03_00')">
			<li id="confirmLi" class="pageButon1"><a href="#" onclick="confirmAlarm();return false;"><font color='#FFFFFF'><s:text name="button.conform" /></font></a></li>
		</s:if> 
		<!-- </s:if> --> 
		<s:if test="#session.authenticater.authIdList.contains('05_01_02_00')">
			<li class="pageButonRed"><a href="#" onclick="clearAlarm();return false;"><font color='#FFFFFF'><s:text name="button.clear" /></font></a></li>
		</s:if> 
	</ul>
	<br/>
</div>

<%-- 邮件提醒 --%>
<s:if test="#session.authenticater.authIdList.contains('05_01_06_00')">
<%-- <div class="baseTitl"><s:text name="web.mail.title"/></div> --%>
<div id="mailDiv" class="softTab" style="display:none;">
	<ul class="new_ul email">
	    <li>
		    <label>
		    	<s:text name="common.red.point"/><s:text name="web.mail.address"/><s:text name="common.lable.point" />
		    </label>
		    <input type="text" class="input-lg" name="domain.mailMember" id="notifyPersonMail"/>
		    <img id="mm" src="themes/blue/images/icon-address.png" onclick="choosePerson('notifyPersonMail');" style="cursor: pointer;">
		</li>
	    <li>
	    	<label>
	    		<s:text name="common.red.point"/><s:text name="web.mail.mailTitle"/><s:text name="common.lable.point" />
    		</label>
    		<input type="text" class="input-lg" name="domain.alarmTitle" id="mailTitle"/>
   		</li>
	    <li>
	    	<label>
	    		<s:text name="web.mail.mailContent"/><s:text name="common.lable.point" />
    		</label>
    		<textarea id="mailContent" class="textarea-control texarea-lg" rows="3" name="domain.alarmContent" onkeydown="validateMailContent();" onkeyup="validateMailContent();"></textarea>
   		</li>
	    <li class="btn-detail">
	    	<a href="javascript:sendMail();" class="submit-btn"><s:text name="button.sendEmail"/></a>
    	</li>
    </ul>
	<%-- <table width="280" border="0" cellspacing="0" cellpadding="0">
		<tr>
	        <td>
	            <strong  class="redStar">*</strong><s:text name="web.mail.address"/><s:text name="common.lable.point" />
	        </td>
	        <th>
	            <s:textfield name="domain.mailMember" id="mailMember" maxlength="1024" cssClass="tdInput"></s:textfield>
	            <img id="mm" src="<%=request.getContextPath()%>/images/icon-address.png" onclick="selectMail();" style="cursor: pointer;">
	        </th>
	     </tr>
	     <tr>
	        <td>
	            <strong  class="redStar">*</strong><s:text name="web.mail.mailTitle"/><s:text name="common.lable.point" />
	        </td>
	         <th><s:textfield name="domain.alarmTitle" id="mailTitle" maxlength="128" cssClass="tdInput"></s:textfield></th>
	     </tr>
	     <tr>
	        <td><s:text name="web.mail.mailContent"/><s:text name="common.lable.point" /></td>
	         <th><s:textarea id="mailContent" name="domain.alarmContent" cssClass="tdTextarea" onkeydown="validateMailContent();" onkeyup="validateMailContent();"/></th>
	     </tr>
	</table>
    <ul>
   		<li class="pageButon1" style="margin-left: 80px;"><a href="javascript:sendMail();" ><s:text name="button.sendEmail"/></a></li>
   </ul> --%>
   
   <!-- 选择人员邮件信息  -->
   <div id="personEmail" style="display: none;">
	<div class="rightToolbar" style="height:35px;">
	 <div class="rightToolbarCrumbs">
		<div class="btn-group fn-right" style="margin-top: 10px;font-size:12px;">
	  		 <button class="small-btn btn-grey" id="search" onclick="showSeachDiv()" type="button">
				<span class="icon-search"></span>
				<span><s:text name="common.title.search" /></span>
			 </button>
	  	  </div>
  	  </div>
  	 </div>
  	 <div id="searchUserDiv" style="display: none;float: left;background: #f0f0f0;">
  	 	<table border="0" class="searchUserTable" style="font-size: 12px;">
			 <tr>
	            <td class="tdLabel1" align="right" valign="top"><strong><s:text name="field.user.username"/><s:text name="common.lable.point" /></strong></td>
	            <td class="tdInput1" valign="top">
	              <s:textfield  id="userName" name="user.userName"  cssClass="input" ></s:textfield>
	            </td>
	            <td class="tdLabel1" align="right" valign="top"><strong><s:text name="field.user.sitename"/><s:text name="common.lable.point" /></strong></td>
	            <td class="tdInput1" valign="top">
	              <select name="user.siteId" id="siteId" class="newSelect"></select>
	            </td>
	          </tr> 
	     	  <tr>
	            <td align="right" colspan="4">
	            	<a class="pageButonRed pageButtonSearch" href="#" onclick="searchUser();" style="color:#fff;padding: 4px 20px 4px 20px;cursor: pointer;"><s:text name="common.button.submit"/></a>
	            	<a class="pageButon1 pageButtonSearch" href="#" onclick="resetUser();" style="color:#fff;padding: 4px 20px 4px 20px;margin-right: 24px;cursor: pointer;"><s:text name="common.button.reset"/></a>
	            </td>
	         </tr>
        </table>
  	 </div>
	<table id="personTable" class="grid grid-bordered" style="width: 100%;margin-top:0px;"></table>
  </div>	
</div>
</s:if>

<script>
$(function() {
	$('#mm').css("position","relative");
	$('#mm').css("z-index",90);
	
	var t = $("#notifyPersonMail");
	t.css("position","relative"); 
	var flag = ('<s:property value="agentId"/>' == '');
	if (flag) {
		$('#mm').offset({left: t.width() - 15 - 30 - 30, top: 28});
	}
	else{
		$('#mm').offset({left: t.width() - 15 - 30 - 30 - 97, top: 28});
	}
	$(".baseTitl").hover(function(){
		$(this).css	("text-decoration","underline");			
	},function(){
		$(this).css	("text-decoration","none");
	});
	
	$(".baseTitl").each(function(){
		$(this).width($("#detail").width() / $(".baseTitl").length);
	});
	
	/*判断清除状态的去掉汉字方法*/
	var confirmStatus = $("#confirmStat").val();
	if(confirmStatus == "<s:text name='web.translate.confirmStatus1' />")
	{
		$(".confirmStatus").css("display","none");
	}
	else
	{
		$("#confirmLi").css("display","none");
	}
});

function changeDiv(t,obj){
	if (t) {
		if('1' == t){
			$("#infoDiv").css("display","block");
			$("#mailDiv").css("display","none");
		}
		else if('2' == t){
			$("#infoDiv").css("display","none");
			$("#mailDiv").css("display","block");
		}
		
		$(obj).removeClass("click").addClass("click");
		$(obj).siblings().removeClass("click");
	}
}

//短信内容 长度校验
function validateSMContent(){
	if($("#smsContent").val().length > 60)
	  {
		$("#smsContent").val($("#smsContent").val().substring(0,60));
	  }
}
//邮件内容 长度校验
function validateMailContent(){
	if($("#mailContent").val().length > 512)
	  {
		$("#mailContent").val($("#mailContent").val().substring(0,512));
	  }
}

//操作说明内容 长度校验
function validateOperatorDesc(){
    if($("#operatorDesc").val().length > 512)
      {
        $("#operatorDesc").val($("#operatorDesc").val().substring(0,512));
      }
}

//事件描述 长度校验
function validateEventDesc(){
	if($("#description").val().length > 512)
	  {
		$("#description").val($("#description").val().substring(0,512));
	  }
}

//清除告警
function clearAlarm(){
    var AlarmId = '<s:property value="domain.alarmID"/>';
    var agentName = '<s:property value="domain.agentName"/>';
    var operatorDesc = $("#operatorDesc").val(); 
    if(confirm('<s:text name="web.confirm.clearAlarm"/>')){ 
    $.ajax({   
	    url: "alarmClear.action", 
		type: "post",   
		data : {'domain.alarmID': AlarmId,
		    'domain.agentName' : agentName,
		    'domain.confirmDesc' : operatorDesc},
		dataType : "text",
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="fail") {//系统异常
			setTimeout("clearAlarmReload('" + '<s:text name="log.clear.error" />' + "')", 2*1000);
			}else{//成功,不提示
			setTimeout("clearAlarmReload('" + '<s:text name="log.clear.success" />' + "')", 2*1000);
			}
		},   
        error: function(XMLHttpRequest, textStatus, errorThrown) {   
			 setTimeout("clearAlarmReload('" + '<s:text name="log.clear.error" />' + "')", 2*1000);
		}   
	});  
  }else{
	return false;
  }         
}

function clearAlarmReload(message){
	alert(message);
	setTimeout("reloadGrid()", 2*1000);
}
function reloadGrid() {
	if ('<s:property value="isTopo" escape="true" />') {
		
	}else {
		$("#flexigrid").flexReload();
		$(".messages").css("display", "none");
	}
	closeDetail();
}
//确认告警
function confirmAlarm(){
    var AlarmId = '<s:property value="domain.alarmID"/>';
    var agentName = '<s:property value="domain.agentName"/>';
    var operatorDesc = $("#operatorDesc").val(); 
    if(confirm('<s:text name="web.confirm.confirmAlarm"/>')){ 
    $.ajax({   
	    url: "alarmConfirm.action", 
		type: "post",   
		data : {'domain.alarmID': AlarmId,
			    'domain.agentName' : agentName,
			    'domain.confirmDesc' : operatorDesc},
		dataType : "text",
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="fail") {//系统异常
			    alert('<s:text name="log.confirm.error" />');
			}else{//成功,不提示
				alert('<s:text name="log.confirm.success" />');
			    reloadGrid();	
			}
		 },   
         error: function(XMLHttpRequest, textStatus, errorThrown) {   
			alert('<s:text name="log.confirm.error" />');
			 reloadGrid();
		 }   
	 });  
   }else{
    return false;
   }   
}


//选择短信发送人员
var smSelected="";
function selectSM(){
	  //var url="selectNotifyMember.action";
	   var url="selectPersonBase.action";
	   var returnValue = window.showModalDialog(url, smSelected, "dialogWidth:900px;dialogHeight:460px;status:no;help:no;resizable:no;");
	   if( typeof(returnValue)!="undefined" && $.trim(returnValue)!=""){
		   $('#smsMember').val(returnValue.split("@#neu&*")[3]);
	       smSelected = returnValue.split("@#neu&*")[0];
	   }   
}

/* var mailSelected="";
function selectMail(){
	 // var url="selectNotifyMember.action";
	  var url="selectPersonBase.action";
	  var returnValue = window.showModalDialog(url, mailSelected, "dialogWidth:900px;dialogHeight:460px;status:no;help:no;resizable:no;");
	  if( typeof(returnValue)!="undefined" && $.trim(returnValue)!=""){ 
	      $('#mailMember').val(returnValue.split("@#neu&*")[2]);
	      mailSelected = returnValue.split("@#neu&*")[0];
	  } 
} */


//邮件通知
function sendMail(){
	var mailMember=$("#notifyPersonMail").val(); 
	var mailTitle=$("#mailTitle").val(); 
	var mailContent=$("#mailContent").val(); 
	var agentName = '<s:property value="domain.agentName"/>';
    if(mailMember=="" || mailTitle==""){
        alert('<s:text name="web.mail.nullValidate"/>');
        return;
    }
    $.ajax({   
	    url: "alarmMailNotify.action", 
		type: "post",   
		data : {'domain.mailMember': mailMember, 
			    'domain.mailTitle': mailTitle, 
			    'domain.mailContent': mailContent,
			    'domain.agentName' : agentName},
		dataType : "text",
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="0") {//
				/*发送邮件后，所有选中的人都去掉*/
			 	$('input:checkbox[name="userCheckValue"]').each(function(){
			 		$(this).removeAttr('checked');
			 	});
			    alert('<s:text name="web.mail.sending"/>');
			}else if(json.result=="2") {//
			    alert('<s:text name="web.mail.fomateValidate"/>');
			}else{//成功,不提示
				alert('<s:text name="web.mail.sendingException"/>');
			}
			 reloadGrid();
		 },   
         error: function(XMLHttpRequest, textStatus, errorThrown) {   
			alert('<s:text name="web.mail.sendingException"/>');
			 reloadGrid();
		 }   
	 });  
}

//短信通知
/* function sendSM(){
	var smsMember=$("#smsMember").val(); 
	var smsContent=$("#smsContent").val(); 

    if(smsMember=="" || smsContent==""){
        alert('手机号、内容不能为空，请输入手机号、内容信息');
        return;
    }
    $.ajax({   
	    url: "alarmSMNotify.action", 
		type: "post",   
		data : {'domain.smsMember': smsMember, 'domain.smsContent': smsContent},
		dataType : "text",
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="0") {//
			    alert('短信正在下发中');
		        reloadGrid();
			}else if(json.result=="2") {//
			    alert('手机号格式错误，请正确输入手机号，多个手机号间使用";"分隔');
			}else{//成功,不提示
			    alert('短信通知出现异常，请联系系统管理员');
	            reloadGrid();
			}
		 },   
         error: function(XMLHttpRequest, textStatus, errorThrown) {   
			alert('短信通知出现异常，请联系系统管理员');
			 reloadGrid();
		 }   
	 });  
} */

//生成事件
/* function createEvent(){

	var eventId = '<s:property value="domain.eventID"/>';
	var eventName = '<s:property value="domain.eventName" escape="false"/>';
	var alarmId = '<s:property value="domain.alarmID"/>';

	if(null != eventId && eventId != ""){
		var result = confirm('该告警记录已关联"' + eventName + '(' + eventId + ')"，是否需要再次关联事件');
		if(!result){
			return;
		}
	}
	var eventTitle=$("#eventTitle").val(); 
	var eventOccurTime=$("#eventOccurTime").val(); 
	var eventPlace=$("#eventPlace").val(); 
	var eventPriority=$("#eventPriority").val(); 
	var eventCompleteTime=$("#eventCompleteTime").val(); 
	var receiveTime=$("#receiveTime").val(); 
	var solveTime=$("#solveTime").val(); 
	var description=$("#description").val();

	var eventType = "1";  //故障
	var eventProperty = "1"; //故障
	var eventSource = "2"; //监控系统
	
    if(eventTitle=="" || eventOccurTime=="" ||eventPriority=="" || eventCompleteTime=="" 
        || receiveTime=="" || solveTime=="" || description==""){
        alert('事件标题、事件时间、优先级、完成期限、接单期限、处理期限、描述不能为空，请输入完整信息');
        return;
    }

    $('#eventSubmit').hide();
    
    $.ajax({   
	    url: "alarmCreateEvent.action", 
		type: "post",   
		data : {'eventDomain.eventTitle': eventTitle, 'eventDomain.eventOccurTime': eventOccurTime,'eventDomain.eventPlace': eventPlace,
    	        'eventDomain.eventPriority': eventPriority,'eventDomain.eventCompleteTime': eventCompleteTime,'eventDomain.receiveTime': receiveTime,
    	        'eventDomain.solveTime': solveTime,'eventDomain.description': description,'eventDomain.eventType': eventType,
    	        'eventDomain.eventProperty': eventProperty,'eventDomain.eventSource': eventSource,'eventDomain.alarmId': alarmId
		},
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="0") {//
			    alert('创建事件成功');
			}else{//成功,不提示
				alert('创建事件出现异常，请联系系统管理员');
			}
			 reloadGrid();
		 },   
         error: function(XMLHttpRequest, textStatus, errorThrown) {   
			alert('创建事件出现异常，请联系系统管理员');
			 reloadGrid();
		 }   
	 });  
} */


//ajax请求设备ci项信息
/* function alarmDtialCI(){
	var alarmIP = '<s:property value="domain.alarmIP"/>';
	 $.ajax({
	        type:"post",
	        url:"alarmDtialCI.action",
	        dataType:"text",
	        async: false,
	        data:'domain.alarmIP='+alarmIP,
	        complete:function(json){
	            $("#alarmDtialCI").empty();
	            json = json.responseText; 

	            //alert('json:' + json);
	            var dataObj = eval("("+json+")");//转换为json对象
	            if(dataObj == null || dataObj==""){
	            	$("<tr><td width='160'>"+"没有设备CI项数据!"+"</td></tr>").appendTo("#alarmDtialCI");
                }else{
                	$.each(dataObj,function(index,item){
                        if(null != item ){
                            $("<tr><td width='60'>"+item.ciName+"</td><th width='175'>"+item.ciValue+"</th></tr>").appendTo("#alarmDtialCI");
                        }
                    })
                }
	        }
	    });
} */

//ajax请求关联ci项信息
/* function alarmRelationCI(){
    var alarmIP = '<s:property value="domain.alarmIP"/>';
     $.ajax({
            type:"post",
            url:"alarmRelationCI.action",
            dataType:"text",
            async: false,
            data:'domain.alarmIP='+alarmIP,
            complete:function(json){
                $("#alarmRelationCI").empty();
                json = json.responseText; 
             // alert('json:' + json);
                var dataObj = eval("("+json+")");//转换为json对象
                if(dataObj == null || dataObj==""){
                    $("<p>"+"没有关联CI项数据!"+"</p>").appendTo("#alarmRelationCI");
                }else{
                	 $.each(dataObj,function(index,item){
                         if(null != item ){
                             $("<p>"+item.ciName+":</br>"+item.ciValue+"</p>").appendTo("#alarmRelationCI");
                         }
                     })
                }
            }
        });
} */

//ajax请求关联业务信息
/* function alarmAnalysisCorrelation(){
    var alarmIP = '<s:property value="domain.alarmIP"/>';
    var alarmContent = '<s:property value="domain.alarmContent"/>';
    var deviceType = '<s:property value="domain.deviceType"/>';
    
     $.ajax({
            type:"post",
            url:"alarmBusiness.action",
            dataType:"text",
            async: false,
            data:{'domain.alarmIP':""+alarmIP,'domain.alarmContent':""+alarmContent,'domain.alarmIdentify':""+alarmIdentify,'domain.deviceType':""+deviceType},
            complete:function(json){
                $("#alarmBusiness").empty();
                json = json.responseText; 
             // alert('json:' + json);
                var dataObj = eval("("+json+")");//转换为json对象
                if(dataObj == null || dataObj==""){
                    $("<p>"+"无相关性信息!"+"</p>").appendTo("#alarmBusiness");
                }else{
                	 $.each(dataObj,function(index,item){
                         if(null != item ){
                             $("<p>"+item.business+"&nbsp;</p>").appendTo("#alarmBusiness");
                         }
                     })
                }
            }
        });
        
} */

</script>