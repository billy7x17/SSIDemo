<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-base-1.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-cornExpression-1.0.js"></script>

<script type="text/JavaScript">
function formatCustomerResults(data){
    var arr = data.rows;
    for(var i in arr){
        var rowArr = arr[i].cell;
        for(var j in rowArr){
            if(rowArr[j] == null || rowArr[j] == 'null') rowArr[j] = '';
        }
    }
    return data;
}

//添加
function add(){
	var subUrl = "cronExpressionBeforeAdd.action";
	$('#mainForm').attr('action',subUrl);
	$('#mainForm').submit();
}

function tasklog(){
    var flag = $("input:radio[name^='TASK_ID']:checked").size();
    if(flag == 0){
        alert('<s:text name="common.message.select"/>');
        return false;
    }else{
        var id = $("input:radio[name^='TASK_ID']:checked").attr('id');
        var subUrl = "cronExpressionTasklog.action?domain.TASK_ID=" + id;
        $('#mainForm').attr('action',subUrl);
        $('#mainForm').submit();
    }
}

//修改
function edit(id){
	var url = "cronExpressionBeforeEdit.action?domain.taskID="+id;
	$('#mainForm').attr('action',url);
	$('#mainForm').submit();
}


//删除
function del(id){
	if(confirm('<s:text name="common.message.delConfig"/>')){
		var subUrl = "cronExpressionDelete.action?domain.taskID=" + id;
		$('#mainForm').attr('action',subUrl);
		$('#mainForm').submit();
	}//end if
}

//执行记录
function taskLog(id){
	var subUrl = "cronExpressionTasklog.action?domain.taskID=" + id;
	$('#mainForm').attr('action',subUrl);
	$('#mainForm').submit();
}

//详细
function openview(id){
	url = "cronExpressionDetail.action?domain.taskID=" + id;
	viewDetail(url);
}

function rewriteDelLink(tdDiv, id){
    var delimg = "<img src='<%=request.getContextPath()%>/themes/default/images/tol_delete.png' title='删除'>";
    var editimg = "<img src='<%=request.getContextPath()%>/themes/default/images/tol_edit.png' title='修改'>";
    var logimg = "<img src='<%=request.getContextPath()%>/themes/default/images/tol_exportReport.png' title='执行记录'>";
    tdDiv.innerHTML = '';
    <s:if test="#session.authenticater.authIdList.contains('06_04_03_00')">
    tdDiv.innerHTML = '<a href="javascript:del(\'' + id + '\')">' + delimg +'</a>';
    </s:if>
    <s:if test="#session.authenticater.authIdList.contains('06_04_02_00')">
    tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:edit(\'' + id + '\')">' + editimg +'</a>';
    </s:if>
    tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:taskLog(\'' + id + '\')">' + logimg +'</a>';
}

function translateDesc(tdDiv, id){
	var expressiom = get_cell_text(id, 3);
	tdDiv.innerHTML = translateCronExpression(expressiom);
}

function rewriteTaskStatus(tdDiv, id){
	var text = get_cell_text(id, 5);
	if (text == 1) {
		tdDiv.innerHTML = '<s:text name="cronExpression.taskstatus.invalid" />';
	} else if (text == 0) {
		tdDiv.innerHTML = '<s:text name="cronExpression.taskstatus.effective" />';
	} else {
		tdDiv.innerHTML = '未知';
	}
}
</script>
<form action="" method="post" name="mainForm" id="mainForm"></form>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<table cellpadding="0" cellspacing="0" height="40">
			<tr>
				<td width="45"><img src="themes/blue/images/toolbarIcozy.png" /></td>
				<td><h3>任务管理</h3>
					<a>服务流程</a>-><a>任务管理</a></td>
			</tr>
		</table>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<s:if test="#session.authenticater.authIdList.contains('06_04_01_00')">
		<div class="InsidePageButton"
			onclick="javascript:location.href='cronExpressionBeforeAdd.action'">
			<a href="#" class="button"><s:text name='common.title.add' /></a>
		</div>
	</s:if>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/javascript">
$("#flexigrid").flexigrid({
    url : 'cronExpressionListData.action',
    dataType : 'json',
    preProcess:formatCustomerResults,
    colModel : [{
        display : '<s:text name="cronExpression.manager.taskID"/>',
        name : 'taskID',
        width : 120,
        sortable : true,
        hide : true
    },{
        display : '<s:text name="cronExpression.manager.taskname"/>',
        name : 'taskname',
        width : 150,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="cronExpression.manager.addtime"/>',
        name : 'addtime',
        width : 150,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="cronExpression.manager.excutetime"/>',
        name : 'cronExpression',
        width : 180,
        sortable : false,
        align : 'left'
    },{
        display : '<s:text name="cronExpression.manager.excutetimeDesc"/>',
        name : 'cronExpressionDesc',
        width : 420,
        sortable : false,
        align : 'left',
        process : translateDesc
    }, {
        display : '<s:text name="cronExpression.manager.taskstatus"/>',
        name : 'taskstatus',
        width : 60,
        sortable : true,
        align : 'left',
        process : rewriteTaskStatus
    }, {
        display : '操作',
        name : 'operation',
        width : 80,
        sortable : false,
        align : 'left',
        process : rewriteDelLink
    } ],
    customSearch : true,
    singleSelect:true,
    sortname : 'addtime',
    sortorder : "desc",
    usepager : true,
    useRp : true,
    rp : 10,
    showTableToggleBtn : true,
    width : 'auto',
    height : 250,
    pagestat: '显示记录从{from}到{to}，总数 {total} 条',
    procmsg: '正在处理数据，请稍候 ...',
    onRowSelect: openview
});
$(function(){
    $(".bDiv").height($(window).height()-156);
    $(window).resize(function() {
        var winHeight = $(window).height();
        var winWidth = $(window).width();
        //$(".bDiv").height(winHeight -158);
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
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="cronExpression.manager.taskID" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text" id="taskID"
				class="input" name="domain.taskID" maxlength="9" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="cronExpression.manager.taskname" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="taskName" class="input" name="domain.taskName" maxlength="64" />
			</td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="cronExpression.manager.taskstatus" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top">
	           <s:select id="TASK_STATUS" name="domain.taskStatus"
	               headerKey="" headerValue="%{getText('common.lable.select')}"
	               list="#{'0':getText('cronExpression.taskstatus.effective'),
	               '1':getText('cronExpression.taskstatus.invalid')}" cssClass="newSelect"/>
	        </td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<ul>
					<li class="pageButon1"
						onclick='javascript:$("#flexigrid").resetSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").resetSearch();'>重置条件</a></li>
					<li class="pageButon1"
						onclick='javascript:$("#flexigrid").submitSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").submitSearch();'>提交查询</a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>
