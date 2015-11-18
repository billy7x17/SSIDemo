<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ page import="com.cloudmaster.cmp.service.serviceTemplate.dao.ServiceTemplateDyDomain"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script type="text/JavaScript">
<!--
function rewriteExecuteResult(tdDiv, id){
	var flag = tdDiv.innerHTML;
	if (flag == 0) {
		tdDiv.innerHTML = '<s:text name="cronExpression.tasklog.success" />';
	} else {
		tdDiv.innerHTML = '<s:text name="cronExpression.tasklog.failed" />';
	}
}

function rewriteExecuteLog(tdDiv, id){
	var log = tdDiv.innerHTML;
	if (log == "null") {
		tdDiv.innerHTML = "";
	}
}
//-->
</script>
<form action="" method="post" name="mainForm" id="mainForm">
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<table cellpadding="0" cellspacing="0" height="40">
			<tr>
				<td width="45"><img src="themes/blue/images/toolbarIcozy.png" /></td>
				<td><h3>执行记录</h3><a>服务流程</a>-><a>任务管理</a>-><a>执行记录</a></td>
			</tr>
		</table>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<div class="InsidePageButton"
		onclick="javascript:location.href='cronExpressionList.action'">
		<a href="#" class="button"><s:text name='common.button.return' /></a>
	</div>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/javascript">
$("#flexigrid").flexigrid({
    url : 'cronExpressionTasklogData.action?domain.taskID=<s:property value="domain.taskID" />',
    dataType : 'json',
    colModel : [{
        display : '<s:text name="cronExpression.manager.taskname"/>',
        name : 'taskname',
        width : 150,
        sortable : false,
        align : 'left'
    },{
        display : '<s:text name="cronExpression.manager.executetime"/>',
        name : 'executeTime',
        width : 150,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="cronExpression.manager.executeresult"/>',
        name : 'executeResult',
        width : 150,
        sortable : false,
        align : 'left',
        process : rewriteExecuteResult
    }, {
        display : '<s:text name="cronExpression.manager.executelog"/>',
        name : 'tasktype',
        width : 150,
        sortable : false,
        align : 'left',
        process : rewriteExecuteLog
    }],
    customSearch : false,
    singleSelect:true,
    sortname : 'executeTime',
    sortorder : "desc",
    usepager : true,
    useRp : true,
    rp : 10,
    showTableToggleBtn : true,
    width : 'auto',
    height : 250,
    pagestat: '显示记录从{from}到{to}，总数 {total} 条',
    procmsg: '正在处理数据，请稍候 ...'
});
$(function(){
    $(".bDiv").height($(window).height()-124);
    $(window).resize(function() {
        var winHeight = $(window).height();
        var winWidth = $(window).width();
        $(".bDiv").height(winHeight-124);
        //详细信息自动适应
        if($("#detail").width()>0){
            $(".flexigrid").width(winWidth -300);
            $(".rightToolbar").width(winWidth -334);
            var bars = $(".baseTitl").length;
            $(".baseTab").height(winHeight - bars * 26 - 76);
        }
    });
  //显示提示消息
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
});
</script>
