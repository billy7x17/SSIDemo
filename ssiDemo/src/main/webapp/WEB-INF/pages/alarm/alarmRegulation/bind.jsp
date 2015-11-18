<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<style type="text/css">
.tdLabel{width:220px;}
.tdInput{width:290px;}
.searchTable{
    font-family:'Microsoft Yahei','微软雅黑',Arial,Verdana;
    font-size:12px;
    margin-top: 6px;
}
.searchTable td{height:30px;}
.searchTable li{ color:#FFF; float:right; height:22px; line-height:22px;margin-left:10px; text-align:center; width:82px; }
.searchTable li a{display:block;color:#ffffff;}
}
</style>
<%@ include file="/WEB-INF/pages/alarm/addPerson.jsp"%>
<script type="text/JavaScript">
var num = 0;
window.onload = function(){	
	 $.formValidator.initConfig({formid:"mainForm123",wideword:false});
	 
	    $("#notifyType").formValidator(
                {onshow:'<s:text name="validator.notifyType.onshow" />',
                 onfocus:'<s:text name="validator.notifyType.onfocus" />',
                 oncorrect:'<s:text name="common.validator.oncorrect" />'})
                 .inputValidator({min:1,max:512,
                         empty:{
                         leftempty:false,
                         rightempty:false,
                         emptyerror:'<s:text name="common.validator.emptyerror" />'},
                         onerrormin:'<s:text name="validator.notifyType.onerrormin" />',
                         onerrormax:'<s:text name="validator.notifyType.onerrormax" />'});
     
	    $("#levelId").formValidator(
                {onshow:'<s:text name="validator.redefineLevelId.onshow" />',
                 onfocus:'<s:text name="validator.redefineLevelId.onfocus" />',
                 oncorrect:'<s:text name="common.validator.oncorrect" />'})
                 .inputValidator({min:1,max:512,
                         empty:{
                         leftempty:false,
                         rightempty:false,
                         emptyerror:'<s:text name="common.validator.emptyerror" />'},
                         onerrormin:'<s:text name="validator.redefineLevelId.onerrormin" />',
                         onerrormax:'<s:text name="validator.redefineLevelId.onerrormax" />'});
	    		//通知人员
				$("#notifyPersonMail")
				.formValidator(
				{
					onshow : '<s:text name="validator.notifyPersonMail.onshow" />',
					onfocus : '<s:text name="validator.notifyPersonMail.onfocus" />',
					oncorrect : '<s:text name="common.validator.oncorrect" />'
				})
				.inputValidator(
				{
					min : 1,
					max : 1000,
					empty : {
						leftempty : false,
						rightempty : false,
						emptyerror : '<s:text name="common.validator.emptyerror" />'
					},
					onerrormin : '<s:text name="validator.notifyPersonMail.onerrormin" />',
					onerrormax : '<s:text name="validator.notifyPersonMail.onerrormax" />'
				});

	    		//邮件标题 
	    		$("#notifyTitle")
				.formValidator(
				{
					onshow : '<s:text name="validator.notifyTitle.onshow" />',
					onfocus : '<s:text name="validator.notifyTitle.onfocus" />',
					oncorrect : '<s:text name="common.validator.oncorrect" />'
				})
				.inputValidator(
				{
					min : 1,
					max : 512,
					empty : {
						leftempty : false,
						rightempty : false,
						emptyerror : '<s:text name="common.validator.emptyerror" />'
					},
					onerrormin : '<s:text name="validator.notifyTitle.onerrormin" />',
					onerrormax : '<s:text name="validator.notifyTitle.onerrormax" />'
				});
	    		//邮件内容 notifyContent
	    		$("#notifyContent")
				.formValidator(
				{
					onshow : '<s:text name="validator.notifyContent.onshow" />',
					onfocus : '<s:text name="validator.notifyContent.onfocus" />',
					oncorrect : '<s:text name="common.validator.oncorrect" />'
				})
				.inputValidator(
				{
					min : 1,
					max : 512,
					empty : {
						leftempty : false,
						rightempty : false,
						emptyerror : '<s:text name="common.validator.emptyerror" />'
					},
					onerrormin : '<s:text name="validator.notifyContent.onerrormin" />',
					onerrormax : '<s:text name="validator.notifyContent.onerrormax" />'
				});
	    		
		//提示消息
		var msg = "<s:property value='msg'/>";
		var errorMsg = "<s:property value='errorMsg'/>";
		if (msg != null && msg != "") {
			$("#msgTip").html(msg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		} else if (errorMsg != null && errorMsg != "") {
			$("#msgTip").attr("class", "msgErrors");
			$("#msgTip").html(errorMsg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		}

		$("tr").each(function() {
			var tr_bgColor = $(this).css("background-color");
			$(this).hover(function() {
				$(this).css("background-color", "transparent");
			}, function() {
				$(this).css("background-color", "transparent");
			})
		})

		//展示重定义内容
		//<s:iterator value="redefineContentList">
		//addRedefineContent();
		//var originalContent = '<s:property value="originalContent" escape="false"/>';
		//var redefineContent = '<s:property value="redefineContent" escape="false"/>';
		//alert(originalContent + redefineContent);
		//document.getElementById('originalContent' + (num - 2)).value = originalContent;
		//document.getElementById('redefineContent' + (num - 1)).value = redefineContent;
		//</s:iterator>

		//控制页面展示
		var ruleAction = '<s:property value="domain.ruleAction" />';
		if (ruleAction.indexOf('5') < 0 && ruleAction.indexOf('3') < 0) {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			$(".configTab").hide();
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='mail']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "block";
			}
			/* var trs = $("tr[class='sms']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			} */
		} else if (ruleAction.indexOf('5') > -1 && ruleAction.indexOf('3') < 0) {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();

			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "none";
			}
			var redefineTab = $("#redefineTab");
			for (i = 0; i < redefineTab.length; i++) {
				redefineTab[i].style.display = "";
				
			}
			$("#redefineTab").removeAttr("class");
			$("#notifyTab").attr("class", "tabCurrent");

			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='mail']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='sms']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
		} else if (ruleAction.indexOf('5') > -1 && ruleAction.indexOf('3') > -1) {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();
			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
			}
			var redefineTab = $("#redefineTab");
			for (i = 0; i < redefineTab.length; i++) {
				redefineTab[i].style.display = "";
				;
			}
			$("#notifyTab").removeAttr("class");
			$("#redefineTab").attr("class", "tabCurrent");

			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			checkNotifyType('<s:property value="domain.notifyType" />');

		} else {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();
			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
			}
			var redefineTab = $("#redefineTab");
			for (i = 0; i < redefineTab.length; i++) {
				redefineTab[i].style.display = "none";
				;
			}
			$("#notifyTab").removeAttr("class");
			$("#redefineTab").attr("class", "tabCurrent");

			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			checkNotifyType('<s:property value="domain.notifyType" />');
		}

		$('tr').unbind('mouseenter mouseleave');

	};

	//提交表单
	function validate() {

		var formCheck = $.formValidator.pageIsValid("1");

		validatorAction();

		var ruleAction = $("#ruleAction").val();
		if (ruleAction == "") {
			alert('<s:text name="field.label.ruleAction"/>,<s:text name="field.title.notNull"/>');
			return false;
		}
		if (ruleAction.indexOf('3') > -1) {
			var notifyPerson = $("#notifyPerson").val();
			var notifyType = "1";
			//var notifySMS = $("#notifySMS").val();
			var notifyTitle = $("#notifyTitle").val();
			var notifyContent = $("#notifyContent").val();
			
			 /* if(notifyPerson ==""){
			 alert("通知TAB页中，请选择通知人员，通知人员不能为空");
			 return false;
			 } */
			/* if (notifyType == "") {
				alert("通知TAB页中，请选择通知方式，通知方式不能为空");
				return false;
			} */

			if (notifyType == "1") {
				if (!isMail()) {
					alert('<s:text name="notity.tab.email"/>');
					return false;
				}
				if (notifyTitle == "" || notifyTitle == "") {
					alert('<s:text name="notity.tab.emailTitle"/>');
					return false;
				}
				if (notifyContent == "" || notifyContent == "") {
					alert('<s:text name="notity.tab.emailContent"/>');
					return false;
				}
				
			}/*  else if (notifyType == "2") {
				if (notifySMS == "" || notifySMS == "") {
					alert("通知TAB页中，请输入短信内容，短信内容不能为空");
					return false;
				}
				if (!isMobile()) {
					alert("通知TAB页中，手机号码不合法，手机号码不能为空，不能重复，多个手机号码使用分号(;)分隔");
					return false;
				}
			} else {
				if (notifyTitle == "" || notifyTitle == "") {
					alert("通知TAB页中，请输入邮件标题，邮件标题不能为空");
					return false;
				}
				if (notifyContent == "" || notifyContent == "") {
					alert("通知TAB页中，请输入邮件内容，邮件内容不能为空");
					return false;
				}
				if (notifySMS == "" || notifySMS == "") {
					alert("通知TAB页中，请输入短信内容，短信内容不能为空");
					return false;
				}
				if (!isMail()) {
					alert("通知TAB页中，邮件地址不合法，邮件地址不能为空，不能重复，多个邮件地址使用分号(;)分隔");
					return false;
				}
				if (!isMobile()) {
					alert("通知TAB页中，手机号码不合法，手机号码不能为空，不能重复，多个手机号码使用分号(;)分隔");
					return false;
				}
			} */
		}

		if (ruleAction.indexOf('5') > -1) {
			var redefineLevel = $("#levelId").val();
			if (redefineLevel == "") {
				alert('<s:text name="define.tab.alarmLevel"/>');
				return false;
			}
		}

		/* var originalContent = $("textarea[id^='originalContent']");
		var result = 'true';
		originalContent.each(function(index, jdom) {
			var value = $(jdom).val(); //$(jdom).html()); $(jdom).text());
			if ($.trim(value) == "") {
				result = 'false';
				return false;
			}
		});
		if (result == 'false') {
			alert("重定义TAB页中，原始告警内容不能为空，请填写原始告警内容");
			return false;
		}

		var redefineContent = $("textarea[id^='redefineContent']");
		var result = 'true';
		redefineContent.each(function(index, jdom) {
			var value = $(jdom).val();
			if ($.trim(value) == "") {
				result = 'false';
				return false;
			}
		});
		if (result == 'false') {
			alert("重定义TAB也中，重定义告警内容不能为空，请填写重定义告警内容");
			return false;
		} */

		$("#mainForm").attr("action",
				"alarmRegulationBind.action?domain.notifyType=1");
		$("#mainForm").submit();
	}

	//提交表单时或者选择的事件
	function validatorAction() {
		var checkbox = document.getElementsByName("actionCheckbox");
		var ruleAction = "";
		for ( var i = 0; i < checkbox.length; i++) {
			if (checkbox[i].checked) {
				ruleAction = ruleAction + checkbox[i].value + ',';
			}
		}

		if (ruleAction != "") {
			ruleAction = ruleAction.substring(0, ruleAction.length - 1);
			$("#ruleAction").val(ruleAction);
		} else {
			$("#ruleAction").val("");
		}
	}
	
	//选择动态内容
	var contentSelected;
	function selectContent(fileid) {
		$("#emailContent").dialog({
			title:'<s:text name="button.select"/>',
			autoOpen : false,
		    resizable: false,
		    height: 420,
		    width: 650,
		    modal: true,
		    buttons:{
		    	'<s:text name="common.button.add" />': function() {
		    		var content="";
		    		 $("input[name='content']").each(function(){
		    			if($(this).attr("checked")){
		    				content = $(this).parent().siblings().find("div").html();
		    			};
		    		}); 
		    		 insertAtCursor(fileid, content);
		    		 if (typeof (returnValue) != "undefined") {
		    				if (fileid == "notifyContent") {
		    					validateTextarea(fileid, 256);
		    				} else {
		    					validateTextarea(fileid, 64);
		    				}
		    		} 
		    		 $("#emailContent").dialog("close");
		    	},
		    	'<s:text name="common.button.cancel" />': function() {
		        	$("#emailContent").dialog("close");
		        }
		    }
		});
		$("#emailContent").show();
		$("#emailContent").dialog("open"); 
		/* var url = "selectAlarmContent.action";
		var returnValue = window
				.showModalDialog(url, contentSelected,
						"dialogWidth:900px;dialogHeight:460px;status:no;help:no;resizable:no;");
		if (typeof (returnValue) != "undefined") {
			insertAtCursor(fileid, returnValue);
			if (fileid == "notifyContent") {
				validateTextarea(fileid, 256)
			} else {
				validateTextarea(fileid, 64)
			}
		} */
		
	}

	//控制事件类型关联项是否展示
	function checkAction(type) {

		var notifyChecked = $("#ruleAction3").attr("checked");
		var redefineChecked = $("#ruleAction5").attr("checked");
		//alert(notifyChecked);
		if (notifyChecked == "checked" && redefineChecked == "checked") {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();
			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
				//$("#notifyTab").removeAttr("class");
			}
			var notifyTab = $("#redefineTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
				//$("#notifyTab").removeAttr("class");
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			checkNotifyType();
			$("#notifyTab").removeAttr("class");
			$("#redefineTab").attr("class", "tabCurrent");
		} else if (notifyChecked == "checked" && redefineChecked != "checked") {
			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
				$("#notifyTab").removeAttr("class");
			}
			var notifyTab = $("#redefineTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "none";
				//$("#notifyTab").removeAttr("class");
			}
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			checkNotifyType();
			$("#notifyTab").removeAttr("class");
			$("#redefineTab").attr("class", "tabCurrent");
		} else if (notifyChecked != "checked" && redefineChecked == "checked") {
			var notifyTab = $("#notifyTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "none";
				//$("#notifyTab").removeAttr("class");
			}
			var notifyTab = $("#redefineTab");
			for (i = 0; i < notifyTab.length; i++) {
				notifyTab[i].style.display = "";
				$("#notifyTab").removeAttr("class");
			}
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$(".configTab").show();
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='mail']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='sms']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			$("#redefineTab").removeAttr("class");
			$("#notifyTab").attr("class", "tabCurrent");
		} else {
			var trs = $("tr[class='configTab']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			$(".configTab").hide();
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='mail']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='sms']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
		}
	}

	//控制通知类型关联项是否展示
	function checkNotifyType() {
		//邮件
		var trs = $("tr[class='mail']");
		for (i = 0; i < trs.length; i++) {
			trs[i].style.display = "";
		}
		var trs = $("tr[class='sms']");
		for (i = 0; i < trs.length; i++) {
			trs[i].style.display = "none";
		}
	}

	//控制tab关联项是否展示
	function selectTab(tab) {
		if (tab == "notify") {
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			checkNotifyType();

			$("#notifyTab").removeAttr("class");
			$("#redefineTab").attr("class", "tabCurrent");
		} else {
			var trs = $("tr[class='notify']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='mail']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='sms']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "none";
			}
			var trs = $("tr[class='redefine']");
			for (i = 0; i < trs.length; i++) {
				trs[i].style.display = "";
			}
			$("#redefineTab").removeAttr("class");
			$("#notifyTab").attr("class", "tabCurrent");
		}
	}

	function addRedefineContent() {

		var tr1Num = num;
		num = num + 1;
		tr2Num = num;
		num = num + 1;

		var tr = '<tr class="redefine" id="redefineTr' + tr1Num + '">'
				+ '<td class="tdLabel" align="center" valign="top">'
				+ '<textarea  name="domain.originalContent" id="originalContent'
				+ tr1Num
				+ '"   onkeydown="validateTextarea(\'originalContent'
				+ tr1Num
				+ '\',256);" onkeyup="validateTextarea(\'originalContent'
				+ tr1Num
				+ '\',256);"><s:property value="domain.originalContent"/></textarea>'
				+ '</td>'
				+ '<td class="tdInput" align="center" valign="top">'
				+ '<textarea  name="domain.redefineContent" id="redefineContent'
				+ tr2Num
				+ '" class="newTextarea" onkeydown="validateTextarea(\'redefineContent'
				+ tr2Num
				+ '\',256);" onkeyup="validateTextarea(\'redefineContent'
				+ tr2Num
				+ '\',256);"><s:property value="domain.redefineContent"/></textarea>'
				+ '</td>'
				+ '<td class="tdVad" align="center" valign="top">'
				+ '<ul class="rightToolButtonjhjFwSj">'
				+ '<li><a href="javascript:void(0);" class="buttonGrey" style="color:#222222;" onclick="delRedefineContent('
				+ tr1Num
				+ ','
				+ tr2Num
				+ ');">移除</a></li>'
				+ '</ul>'
				+ '<div id="notifyTitleTip"></div>' + '</td>' + '</tr>';

		// $("#actionTable").prepend(tr);//append(tr);
		//$(tr).appendTo($('#redefineContentTitle'));
		$('#redefineContentTitle').after(tr);
		//$(this).parent().parent().parent().parent().parent().remove();
	}

	function delRedefineContent(tr1Num, tr2Num) {
		jQuery('#redefineTr' + tr1Num).remove();
		jQuery('#redefineTr' + tr2Num).remove();
	}

	function isMobile() {

		var mobile = $('#notifyPersonMobile').val();
		if (mobile == '' || $.trim(mobile) == '') {
			return false;
		}

		var mobileArr = mobile.split(';');
		//手机号码不能重复
		var sortArr = mobileArr.sort();
		for ( var i = 0; i < sortArr.length; i++) {
			if (sortArr[i] == sortArr[i + 1]) {
				return false;
			}
		}
		//手机号码要合法
		var partten = /^(13|15|18)[0-9]{9}$/;
		for (i = 0; i < mobileArr.length; i++) {
			if (!partten.test(mobileArr[i])) {
				return false;
			}
		}
		return true;
	}

	function isMail() {

		var mail = $('#notifyPersonMail').val();
		if (mail == '' || $.trim(mail) == '') {
			return false;
		}

		var mailArr = mail.split(';');
		//邮件地址不能重复
		var sortArr = mailArr.sort();
		for ( var i = 0; i < sortArr.length; i++) {
			if (sortArr[i] == sortArr[i + 1]) {
				return false;
			}
		}
		//邮件地址要合法
		var partten = /^([a-zA-Z0-9_.-]+[@]{1}[a-zA-Z0-9_.-]+[.]{1}[a-zA-Z0-9_-]+)*$/;
		for (i = 0; i < mailArr.length; i++) {
			if (!partten.test(mailArr[i])) {
				return false;
			}
		}
		return true;
	}
</script>
	<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
   <h2 class="sec-label"><s:text name="function.title"/></h2>
   <ul class="bread-cut">
	  <li><s:text name="menu.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="alarmConfig.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="function.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="alarmRegulation.bindEvent.title"/></li>
	</ul>
  </div>
</div>
	 <!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
      <form id="mainForm" method="post" action="alarmRegulationBind.action" > 
      <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
        <s:hidden id="ID" name="domain.ID" />
        <s:hidden id="ruleAction" name="domain.ruleAction" />
        <s:hidden id="notifyPersonId" name="domain.notifyPersonId" />
        <s:hidden id="ruleName" name="domain.ruleName" />
        <s:hidden id="ruleState" name="domain.ruleState" />
	<div>
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" id="actionTable">
          <tr>
            <td class="tdLabel" align="right" valign="top" style="width:200px;"><strong><s:text name="common.red.point" /><s:text name="field.label.ruleAction"/><s:text name="common.lable.point" /></strong></td>
              <td class="tdInput" valign="top" colspan="2" style="width:300px;">
              <%-- 
                <s:if test="#session.authenticater.authIdList.contains('05_10_05_08')">
                    <div style="float:left;"><input name="actionCheckbox" id="ruleAction1" type="checkbox" value="1" <s:if test="domain.ruleAction.indexOf('1') > -1">checked</s:if>/><s:text name="field.label.ruleAction.clear" /> </div>
                </s:if>
              --%>
                <s:if test="#session.authenticater.authIdList.contains('05_10_05_09')">
                    <div style="float:left; margin-left:10px;"><input name="actionCheckbox" id="ruleAction2" type="checkbox" value="2" <s:if test="domain.ruleAction.indexOf('2') > -1">checked</s:if>/><s:text name="field.label.ruleAction.confirm" /> </div>
                 </s:if>
                <s:if test="#session.authenticater.authIdList.contains('05_10_05_10')">
                    <div style="float:left; margin-left:10px;"><input name="actionCheckbox" id="ruleAction4" type="checkbox" value="4" <s:if test="domain.ruleAction.indexOf('4') > -1">checked</s:if>/><s:text name="field.label.ruleAction.filter" /> </div>
                 </s:if>
                <s:if test="#session.authenticater.authIdList.contains('05_10_05_11')">
                    <div style="float:left; margin-left:10px;"><input name="actionCheckbox" id="ruleAction3" type="checkbox" value="3" onclick="checkAction(3)" <s:if test="domain.ruleAction.indexOf('3') > -1">checked</s:if>/><s:text name="field.label.ruleAction.notufy" /> </div>
                </s:if>
                <s:if test="#session.authenticater.authIdList.contains('05_10_05_12')">
                    <div style="float:left; margin-left:10px;"><input name="actionCheckbox" id="ruleAction5" type="checkbox" value="5" onclick="checkAction(5)" <s:if test="domain.ruleAction.indexOf('5') > -1">checked</s:if>/><s:text name="field.label.ruleAction.redefine" /> </div>
                </s:if>
            </td>
          </tr>

         
          </table>
          <table class="addTable" align="center" cellpadding="0" cellspacing="0">
          		 <%--tab页 --%>
           <tr class="configTab">
	          <td class="tdInput" align="left" colspan = "3">
	            <div class="tabBar" style="margin:0 0 2px 133px;"> 
		            <a href="#" id="notifyTab" onclick="selectTab('notify');"><s:text name="bindEvent.notityConfig"/> </a> 
		            <a href="#" id="redefineTab" class="tabCurrent" onclick="selectTab('redefine');"><s:text name="bindEvent.redefine.config"/> </a>
	             </div>
	          </td>
          </tr>
          </table>
       </div>
          <table></table>
        <div  class="configTab"  >
        	<table class="addTable" style="width: 750px;margin-left: 83px;" align="center" cellpadding="0" cellspacing="0" id="configInfoTab" >
	          <tr class="notify">
	             <td class="tdLabel" align="right" valign="top"><s:text name="field.label.clearNotify"/><s:text name="common.lable.point" /></td>
	             <td  class="tdInput" valign="top">
	              <div style="float:left;">
	                <input  type="radio" id="clearNotify" name="domain.clearNotify" value="0" <s:if test="domain.clearNotify==0">checked</s:if> /><s:text name="field.label.no"/>
	              </div>
	              <div style="float:left; margin-left:10px;">
	                <input  type="radio" id="clearNotify" name="domain.clearNotify" value="1" <s:if test="domain.clearNotify==1">checked</s:if> /><s:text name="field.label.yes"/>
	              </div>
	              <!-- <div id="filterStatusTip" style="float:left; margin-left:70px;"></div> -->
	             </td>
	             <td class="tdVad" valign="top"><div id="clearNotifyTip"></div></td>
	          </tr> 
         
	          <tr class="notify">
	             <td class="tdLabel" align="right" valign="top"><s:text name="field.label.notifyType"/><s:text name="common.lable.point" /></td>
	             <td  class="tdInput" valign="top">      
	                 <s:text name="field.label.notifyType.mail"/>
		            <!--  <select id="notifyType" name="domain.notifyType" class="newSelect" onchange="checkNotifyType()">
		               <option value="" ><s:text name="common.lable.select"/></option>   
		               <option value="1" <s:if test="domain.notifyType==1">selected</s:if>><s:text name="field.label.notifyType.mail"/></option>   
		               <option value="2" <s:if test="domain.notifyType==2">selected</s:if>><s:text name="field.label.notifyType.sms"/></option> 
		               <option value="3" <s:if test="domain.notifyType==3">selected</s:if>><s:text name="field.label.notifyType.smsMail"/></option>  
		             </select>-->
		           </td>
	             <td class="tdVad" valign="top"><div id="notifyTypeTip"></div></td>
	          </tr> 
          
	           <tr class="mail">
	             <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.mailAdd"/><s:text name="common.lable.point" /></td>
	             <td class="tdInput" valign="top">
                     <input name="domain.notifyPersonMail" id="notifyPersonMail" value="<s:property value="domain.notifyPersonMail"/>" onkeydown="validateTextarea('notifyPersonMail',1024);" onkeyup="validateTextarea('notifyPersonMail',1024);" />
                     <span class="addEmailPerson" title="<s:text name="common.button.select"/>" onclick="choosePerson('notifyPersonMail');" ></span>
                     
	             </td>
	              <td class="tdVad" valign="top">
	                 <div id="notifyPersonMailTip"></div>
	             </td>
	          </tr> 
          
           <%-- <tr  class="sms">
             <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.notifyPersonMobile"/> <s:text name="common.lable.point" /></td>
             <td class="tdInput" valign="top">
                     <textarea name="domain.notifyPersonMobile" id="notifyPersonMobile" class="newTextarea" onkeydown="validateTextarea('notifyPersonMobile',1024);" onkeyup="validateTextarea('notifyPersonMobile',1024);" ><s:property value="domain.notifyPersonMobile"/></textarea>
                 
               <s:textfield name="domain.notifyPerson" id="notifyPerson" maxlength="1024" cssClass="input"  style="width:107px" ></s:textfield>
               <a href="#" onclick="choosePerson();return false;"><img align="absmiddle" src="<%=request.getContextPath()%>/themes/default/images/btn_view.gif" width="86" height="24"></a>
               
             </td>
             <td class="tdVad" valign="top">
                <ul class="rightToolButtonjhjFwSj">
                    <li><a href="javascript:void(0);" class="buttonGrey" style="color:#222222;" onclick="choosePerson('notifyPersonMobile');">选择</a>
                </ul>
                <div id="notifyPersonTip">(';'分号分隔多个号码)</div>
             </td>
          </tr> 
 --%>
         <%--  <tr class="sms">
             <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.notifySMS"/>(最多64个字符)<s:text name="common.lable.point" /></td>
             <td class="tdInput" valign="top">
                  <textarea name="domain.notifySMS" id="notifySMS" class="newTextarea" onkeydown="validateTextarea('notifySMS',64);" onkeyup="validateTextarea('notifySMS',64);" ><s:property value="domain.notifySMS"/></textarea>
             </td>
             <td class="tdVad" valign="top">
                <ul class="rightToolButtonjhjFwSj">
                    <li><a href="javascript:void(0);" class="buttonGrey" style="color:#222222;" onclick="selectContent('notifySMS');">插入动态内容</a></li>
                </ul>
                <div id="notifyTitleTip"></div>
             </td>
          </tr>  --%>
           <tr class="mail" style="height: 56px;">
             <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.notifyTitle"/><s:text name="common.lable.point" /></td>
             <td class="tdInput" valign="top">
                  <textarea name="domain.notifyTitle" id="notifyTitle"  style="height:43px;"  onkeydown="validateTextarea('notifyTitle',64);" onkeyup="validateTextarea('notifyTitle',64);"><s:property value="domain.notifyTitle"/></textarea>
            	  <span class="emailContent" onclick="selectContent('notifyTitle');" title="<s:text name="common.button.select"/>"></span>
             </td>
             <td class="tdVad" valign="top">
                <div id="notifyTitleTip"></div>
             </td>
          </tr> 
          <tr class="mail" >
             <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.notifyContent"/><s:text name="common.lable.point" /></td>
             <td  class="tdInput" valign="top">        
                 <textarea name="domain.notifyContent" id="notifyContent" style="height:54px;" onkeydown="validateTextarea('notifyContent',256);" onkeyup="validateTextarea('notifyContent',256);"><s:property value="domain.notifyContent"/></textarea>
             	 <span class="emailContent" onclick="selectContent('notifyContent');" title="<s:text name="common.button.select"/>"></span>
             </td>
             <td class="tdVad" valign="top">
                  <div id="notifyContentTip"></div>
             </td>
          </tr> 
          
          <%-- 重定义配置 --%>
          <tr class="redefine">
            <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="field.label.redefineLevel"/><s:text name="common.lable.point"/></td>
            <td class="tdInput" valign="top">
              <s:select list="levelList" listKey="levelId" listValue="levelName" name="domain.levelId" id="levelId" cssClass="newSelect" value="%{domain.levelId}" headerKey="" headerValue="%{getText('common.lable.select')}">          
            </s:select>
            </td>
            <td class="tdVad" valign="top"><div id="levelIdTip"></div></td>
          </tr>
          
       <%--    
          <tr class="redefine">
             <td class="tdLabel" align="right" valign="top"></td>
             <td class="tdInput" valign="top">
                 <ul class="rightToolButtonjhjFwSj">
                   <li><a href="javascript:void(0);" class="buttonGrey" style="color:#222222;" onclick="addRedefineContent();"><s:text name="field.label.redefine"/> </a></li>
                </ul>
             </td>
             <td class="tdVad" valign="top"><div id="Tip"></div></td>
          </tr>  --%>
          
          <%-- <tr class="redefine" bgcolor="#CCCCCC" style="background-color:#CCCCCC;">
           <td class="tdLabel" align="center" valign="top" style="height:20px;"> <s:text name="common.red.point"/>原始告警内容(最多256个字符)</td>
           <td class="tdInput" align="center" valign="top"><s:text name="common.red.point"/>重定义告警内容(最多256个字符) </td>
           <td class="tdVad" align="center" valign="top">操作</td>
          </tr>
          <tr class="redefine" id="redefineContentTitle" >
            <td class="tdLabel" align="right" valign="top" style="height:5px;"></td>
            <td class="tdInput" valign="top">
            </td>
            <td class="tdVad" valign="top"><div id="levelIdTip"></div></td>
          </tr> --%>
        </table> 
        </div>
      </form>
	</div>  
	<div class="messages succcess"  style="top:25px;left: 37%；">
      <div id="msgTip" class="msgSuccess"></div>
    </div>  
    <div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj" style="margin-left: 245px;">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="alarmRegulationBase.action" class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div> 
</div>  
	
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
  	 	<table  class="searchUserTable" style="font-size: 12px;">
			 <tr>
	            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.user.username"/><s:text name="common.lable.point" /></strong></td>
	            <td class="tdInput" valign="top">
	              <s:textfield  id="userName" name="user.userName"  cssClass="input" ></s:textfield>
	            </td>
	            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.user.sitename"/><s:text name="common.lable.point" /></strong></td>
	            <td class="tdInput" valign="top">
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
	
<div id="emailContent" style="display: none;">
	<table id="contentTable" class="grid grid-bordered" style="width: 97%;margin-left:10px;">
		<thead style="border-left: 1px solid #ececec;">
			<tr>
				<td width="5%" style="text-align: center;"></td>
				<td width="40%"><s:text name="emailContent.dynamic.title"/></td>
				<td width="55%"><s:text name="emailContent.dynamic.desc"/></td>
			</tr>
		</thead>
		<tbody style="border-left: 1px solid #ececec;">
			<tr style="background:#f8f8f8;">
				<td align="center" style="text-align: center;margin-left:20px;"><input type="radio" name="content" checked="checked" /> </td>
				<td><div>${alarm_title}</div> </td>
				<td><s:text name="emailContent.alarmTitle.desc"/></td>
			</tr>
			<tr>
				<td><input type="radio" name="content"/></td>
				<td><div>${alarm_content}</div> </td>
				<td><s:text name="emailContent.alarmContent.desc"/> </td>
			</tr>
			<tr style="background: #f8f8f8;">
				<td><input type="radio" name="content" /> </td>
				<td><div>${alarm_time}</div> </td>
				<td><s:text name="emailContent.alarmTime.desc"/> </td>
			</tr>
			<tr>
				<td><input type="radio" name="content" /></td>
				<td><div>${alarm_type}</div> </td>
				<td><s:text name="emailContent.alarmType.desc"/></td>
			</tr>
			<tr style="background:#f8f8f8;">
				<td><input type="radio" name="content" /></td>
				<td><div>${device_type}</div> </td>
				<td><s:text name="emailContent.deviceType.desc"/></td>
			</tr>
			<tr>
				<td><input type="radio" name="content" /></td>
				<td><div>${alarm_IP}</div> </td>
				<td><s:text name="emailContent.alarmIp.desc"/></td>
			</tr>
		</tbody>
	</table>
</div>	
	
	