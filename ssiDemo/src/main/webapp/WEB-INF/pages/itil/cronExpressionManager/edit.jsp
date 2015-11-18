<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-base-1.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-cornExpression-1.0.js"></script>

<script type="text/JavaScript">
window.onload = function(){
    $.formValidator.initConfig({formid:"edit_form",wideword:false});

    $("#TASK_NAME")
    .formValidator({
     onshow :'<s:text name="cronExpression.manager.validator.taskname.onfocus" />'
         , onfocus : '<s:text name="cronExpression.manager.validator.taskname.onfocus" />'
         , oncorrect : '<s:text name="common.validator.oncorrect"/>'
    })
    .inputValidator({
               min : 1
                 , max : 64
                 , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                 , onerror: '<s:text name="cronExpression.manager.validator.taskname.onerror" />'
    });//end validator

    //执行时间
    $("#EXCUTE_TIME").formValidator({
     onshow : '<s:text name="cronExpression.manager.validator.excutetime.onfocus" />'
         , onfocus : '<s:text name="cronExpression.manager.validator.excutetime.onfocus" />'
         , oncorrect : '<s:text name="common.validator.oncorrect"/>'
    }).inputValidator({
                min:1
               ,max : 32
                 , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                 , onerror: '<s:text name="cronExpression.manager.validator.excutetime.onerror" />'
    });//end validator

    //任务参数
    $("#TASK_PARA").formValidator({
     onshow : '<s:text name="cronExpression.manager.validator.taskpara.onfocus" />'
         , onfocus : '<s:text name="cronExpression.manager.validator.taskpara.onfocus" />'
         , oncorrect : '<s:text name="common.validator.oncorrect"/>'
    }).inputValidator({
                min:1,
                max : 1024
                 , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                 , onerror: '<s:text name="cronExpression.manager.validator.taskpara.onerror" />'
    }).regexValidator({
            regexp : "^{(\"[a-zA-Z0-9]+\":\"[^\"]+\",)*(\"[a-zA-Z0-9]+\":\"[^\"]+\")}$"
          , onerror : '<s:text name="cronExpression.manager.validator.taskpara.onfocus" />'
         });//end regexValidator

     $("#TASK_STATUS").formValidator({
         onshow : '<s:text name="cronExpression.manager.validator.taskstatus.onfocus" />'
             , onfocus : '<s:text name="cronExpression.manager.validator.taskstatus.onfocus" />'
             , oncorrect : '<s:text name="common.validator.oncorrect"/>'
        }).inputValidator({
                    min:1
                   ,max : 32
                     , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                     , onerror: '<s:text name="cronExpression.manager.validator.taskstatus.onerror" />'
        });//end validator

    $("#TASK_TYPE").formValidator({
        onshow : '<s:text name="cronExpression.manager.validator.tasktype.onfocus" />'
            , onfocus : '<s:text name="cronExpression.manager.validator.tasktype.onfocus" />'
            , oncorrect : '<s:text name="common.validator.oncorrect"/>'
       }).inputValidator({
                   min:1
                  ,max : 32
                    , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                    , onerror: '<s:text name="cronExpression.manager.validator.tasktype.onerror" />'
       });//end validator

    var msg ="<s:property value='msg'/>";
    if(null != msg && msg != ""){
        $("#msgTip").html(msg);
        $(".messages").show("slow");
        $(".messages").delay(5000).hide("slow");
    }
    var errorMsg = "<s:property value='errorMsg'/>";
    if(errorMsg != null && errorMsg != ""){
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

    $('#EXCUTE_TIME').val("<s:property value='domain.cronExpression'/>");

    $('#EXCUTE_TIME').bind('focus',function(){
    	var src = $("#Dialog_Content").attr("src",url);
    	if(src == ""){
    		var url = "<%=request.getContextPath()%>/cronExpressionDate.action?domain.cronExpression=" + $('#EXCUTE_TIME').val();
            $("#Dialog_Content").attr("src",url);
    	}
        $("#dialog").dialog("open");
    })


    $("#cronExpressionDesc").html(translateCronExpression("<s:property value='domain.cronExpression'/>") + "<br/><br/>");

    //dialog初始化
    $("#dialog").dialog({
        height: 330,
        width : 572,
        resizable: false,
        modal: true,
        autoOpen : false
    });
};

function setExecuteTime(expression){
	$('#EXCUTE_TIME').val(expression);
	$("#cronExpressionDesc").html(translateCronExpression(expression));
}

function dialog_close(){
    $("#dialog").dialog("close");
}

function validate(){
    var formCheck = $.formValidator.pageIsValid("1");
    if(formCheck){
        $("#edit_form").attr("action","cronExpressionEdit.action");
        $("#edit_form").submit();
    }else{
        return false;
    }
}

function back(){
    var url = "<%=request.getContextPath()%>/cronExpressionList.action";
    $("#button_form").attr("action", url);
    $("#button_form").submit();
}

function validateTextarea(){
    if($("#TASK_PARA").val().length >256)
      {
        $("#TASK_PARA").val($("#TASK_PARA").val().substring(0,256));
      }
}
</script>

<div id="dialog" style="padding:0;" title="<s:text name="cronExpression.dialog.title" />">
    <iframe id="Dialog_Content" frameborder="0" src="" width="572px" height="292px"></iframe>
</div>

<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
    <table cellpadding="0" cellspacing="0" height="40">
      <tr>
        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
        <td>
          <h3>修改任务</h3>
          <a>服务流程</a>-><a>任务管理</a>-><a>修改</a></td>
      </tr>
    </table>
  </div>
</div>
<%-- 表单 --%>
<div class="rightDisplayFwSj">
    <form id="edit_form" method="post" action="#" >
        <s:hidden name="domain.taskID" id="TASK_ID" />
        <s:hidden id="TASK_TYPE" name="domain.taskType" value="SPRING" />
        <s:hidden id="SS_ID" name="domain.ssID" />
        <s:hidden id="BEAN_ID" name="domain.beanID" />
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
            <tr>
                <td class="tdLabel" align="right" valign="top">
                <strong>
                    <s:text name="common.red.point" />
                    <s:text name="cronExpression.manager.taskname" />
                    <s:text name="common.lable.point" />
                </strong>
                </td>
                <td class="tdInput" valign="top">
                    <input type="text" id="TASK_NAME" name="domain.taskName" value="<s:property value='domain.taskName'/>"  maxlength="64" class="input"/>
                </td>
                <td class="tdVad" valign="top">
                    <div id="TASK_NAMETip"></div>
                </td>
            </tr>

            <tr>
                <td class="tdLabel" align="right" valign="top">
                <strong>
                    <s:text name="common.red.point" />
                    <s:text name="cronExpression.manager.excutetime" />
                    <s:text name="common.lable.point" />
                </strong>
                </td>
                <td class="tdInput" valign="top">
                    <input type="text" id="EXCUTE_TIME" name="domain.cronExpression" maxlength="32" class="input" readonly="readonly"/>
                </td>
                <td class="tdVad" valign="top">
                    <div id="EXCUTE_TIMETip"></div>
                </td>
            </tr>

            <tr id="desc_tr">
	          <td class="tdLabel" align="right" valign="top">
	          <strong><s:text name="cronExpression.manager.excutetimeDesc" /><s:text name="common.lable.point" /></strong>
	          </td>
	          <td class="tdInput" valign="top" id="cronExpressionDesc"></td>
	          <td class="tdVad" valign="top"></td>
	        </tr>

		<!--
            <tr>
                <td class="tdLabel" align="right" valign="top">
                <strong>
                    <s:text name="common.red.point" />
                    <s:text name="cronExpression.manager.tasktype" />
                    <s:text name="common.lable.point" />
                </strong>
                </td>
                <td class="tdInput" valign="top">
                    <s:select id="TASK_TYPE" name="domain.taskType" list="taskType"
                        headerKey="" headerValue="%{getText('common.lable.select')}"
                        cssClass="newSelect"/>
                </td>
                <td class="tdVad" valign="top">
                    <div id="TASK_TYPETip"></div>
                </td>
            </tr>
			 -->

			<tr>
	        <td class="tdLabel" align="right" valign="top">
	        <strong>
	            <s:text name="common.red.point" />
	            <s:text name="cronExpression.manager.ssID" />
	            <s:text name="common.lable.point" />
	        </strong>
	        </td>
	        <td class="tdInput" valign="top">
				<s:property value="subsystemName" />
	        </td>
	        <td class="tdVad" valign="top">
	            <div id="SS_IDTip"></div>
	        </td>
	    </tr>

			<tr>
				<td class="tdLabel" align="right" valign="top">
				<strong><s:text name="common.red.point" />
						<s:text	name="cronExpression.manager.beanID" />
						<s:text	name="common.lable.point" />
				</strong>
				</td>
				<td class="tdInput" valign="top">
					<s:property value="jobName" />
				</td>
				<td class="tdVad" valign="top">
					<div id="BEAN_IDTip"></div>
				</td>
			</tr>

            <tr>
                <td class="tdLabel" align="right" valign="top">
                <strong>
                    <s:text name="common.red.point" />
                    <s:text name="cronExpression.manager.taskstatus" />
                    <s:text name="common.lable.point" />
                </strong>
                </td>
                <td class="tdInput" valign="top">
                    <s:select id="TASK_STATUS" name="domain.taskStatus"
                        headerKey="" headerValue="%{getText('common.lable.select')}"
                        list="#{'0':getText('cronExpression.taskstatus.effective'),
                        '1':getText('cronExpression.taskstatus.invalid')}" cssClass="newSelect"/>
                </td>
                <td class="tdVad" valign="top">
                    <div id="TASK_STATUSTip"></div>
                </td>
            </tr>

		<!--
            <tr>
                <td class="tdLabel" align="right" valign="top">
                <strong>
                    <s:text name="common.red.point" />
                    <s:text name="cronExpression.manager.taskpara" />
                    <s:text name="common.lable.point" />
                </strong>
                </td>
                <td class="tdInput" valign="top">

                    <div class="confWrap">
                        <label for="textarea"></label>
                        <textarea id="TASK_PARA" cols="45" rows="5" name="domain.taskParam" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"><s:property value="domain.taskParam"/></textarea>
                     </div>
                </td>
                <td class="tdVad" valign="top">
                    <div id="TASK_PARATip"></div>
                </td>
            </tr>
             -->

        </table>
    </form>
</div>
<div class="messages succcess" style="top:25px">
    <div id="msgTip" class="msgSuccess" ></div>
</div>
<%-- 按钮栏 --%>
<div class="serverEventButton">
    <ul class="rightToolButtonjhjFwSj">
        <li><a href="#" class="buttonFwSj" onclick="validate()"><s:property value="getText('common.button.add')"/></a></li>
        <li><a href="cronExpressionList.action" class="buttonFwSj" ><s:property value="getText('common.button.cancel')" /></a></li>
    </ul>
</div>