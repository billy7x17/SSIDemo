<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<script type="text/javascript">
<!--

window.onload = function(){
    $.formValidator.initConfig({formid:"search_form",wideword:false});

    //任务名称
    $("#TASK_NAME").formValidator({
     onshow :'<s:text name="cronExpression.manager.validator.taskname.onfocus" />'
         , onfocus : '<s:text name="cronExpression.manager.validator.taskname.onfocus" />'
         , oncorrect : '<s:text name="common.validator.oncorrect"/>'
    }).inputValidator({
               min : 0
                 , max : 64
                 , empty : {leftempty:false, rightempty:false ,emptyerror : '<s:text name="cronExpression.manager.validator.common.lrnotempty" />'}
                 , onerror: '<s:text name="cronExpression.manager.validator.taskname.onerror" />'
    });//end validator
};

    //提交按钮
    function validate(){
        var formCheck = $.formValidator.pageIsValid("1");
        if(formCheck){
            $("#search_form").attr("action","cronExpressionSearch.action");
            $("#search_form").submit();
        }else{
            return false;
        }
    }

    function back(){
        var url = "<%=request.getContextPath()%>/cronExpressionList.action";
        $("#button_form").attr("action", url);
        $("#button_form").submit();
    }

//-->
</script>

<!--内容部分 main star-->
<div class="main">
    <!-- 工作区 workarea start -->
    <div class="iframeWorkarea">
        <!-- 选项卡 tab start -->
        <div class="tabBar">
            <a href="#" class="tabCurrent" style="text-decoration:none">
            <s:text name="common.title.search" /></a>
        </div>
        <!-- 选项卡 tab end -->
        <!-- 滚动条 scroll start -->
        <div class="workareaMain scroll-pane">
            <form id="search_form" method="post" action="#" >
                <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
                    
                    <tr>
                        <td class="tdLabel" align="right" valign="top">
                        <strong>
                            <s:text name="cronExpression.manager.taskname" />
                            <s:text name="common.lable.point" />
                        </strong>
                        </td>
                        <td class="tdInput" valign="top">
                            <input type="text" id="TASK_NAME" name="domain.TASK_NAME" value="" maxlength="64" class="input"/>
                        </td>
                        <td class="tdVad" valign="top">
                            <div id="TASK_NAMETip"></div>
                        </td>
                    </tr>
                    

                    <!--
                    <tr>
                      <td width="40%" height="28" align="right">
                        <s:text name="cronExpression.manager.tip.excute.time"/><s:text name="common.lable.point" />&nbsp;
                      </td>
                      <td><s:textfield id="EXCUTE_TIME" name="domain.EXCUTE_TIME" cssClass="input180" /></td>
                      <td><div id="EXCUTE_TIMETip" class="tipWidth"></div></td>
                    </tr>
                     -->
                     
                     <tr>
                        <td class="tdLabel" align="right" valign="top">
                        <strong>
                            <s:text name="cronExpression.manager.taskpara" />
                            <s:text name="common.lable.point" />
                        </strong>
                        </td>
                        <td class="tdInput" valign="top">
                            <s:select id="TASK_TYPE" name="domain.TASK_TYPE" list="taskType"
                                headerKey="" headerValue="%{getText('common.lable.select')}" cssClass="newSelect"/>
                        </td>
                        <td class="tdVad" valign="top">
                            <div id="TASK_TYPETip"></div>
                        </td>
                    </tr>
                    
                    <!--
                    <tr>
                      <td width="40%" height="28" align="right">
                        <s:text name="cronExpression.manager.tip.task.para"/><s:text name="common.lable.point" />&nbsp;
                      </td>
                      <td><s:textarea id="TASK_PARA" name="domain.TASK_PARA" cssClass="textareaCSS" /></td>
                      <td><div id="TASK_PARATip" class="tipWidth"></div></td>
                    </tr>
                     -->
                     
                     <tr>
                        <td class="tdLabel" align="right" valign="top">
                        <strong>
                            <s:text name="cronExpression.manager.taskpara" />
                            <s:text name="common.lable.point" />
                        </strong>
                        </td>
                        <td class="tdInput" valign="top">
                            <s:select id="TASK_STATUS" name="domain.TASK_STATUS" list="taskType"
                                headerKey="" headerValue="%{getText('common.lable.select')}"
                                list="#{'0':getText('cronExpression.taskstatus.effective'),
                                '1':getText('cronExpression.taskstatus.invalid')}" cssClass="newSelect"/>
                        </td>
                        <td class="tdVad" valign="top">
                            <div id="TASK_STATUSTip"></div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--滚动条 scroll end-->
        <div class="messages succcess"  style="top:25px">
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
