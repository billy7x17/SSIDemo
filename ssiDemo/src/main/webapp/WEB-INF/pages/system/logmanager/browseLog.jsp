<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript">
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

function opType(tdDiv, id){
    var opType = tdDiv.innerHTML;
    if(opType=='Add')tdDiv.innerHTML = '<s:text name="optype.add" />';
    if(opType=='Update')tdDiv.innerHTML = '<s:text name="optype.edit" />';
    if(opType=='Del')tdDiv.innerHTML = '<s:text name="optype.delete" />';
    if(opType=='View')tdDiv.innerHTML = '<s:text name="optype.show" />';
    if(opType=='AddPrivilege')tdDiv.innerHTML = '<s:text name="optype.add" />';
    if(opType=='DelPrivilege')tdDiv.innerHTML = '<s:text name="optype.delete" />';
    if(opType=='UpdatePrivilege')tdDiv.innerHTML = '<s:text name="optype.edit" />';
    if(opType=='Login')tdDiv.innerHTML = '<s:text name="optype.login" />';
    if(opType=='Logout')tdDiv.innerHTML = '<s:text name="optype.logout" />';
    if(opType=='Other')tdDiv.innerHTML = '<s:text name="optype.other" />';
}

function openview(id){
    var url = "logSearchDetail.action?logId=" + id;
    viewDetail(url);
}

</script>
<form action="" method="post" name="mainForm">
	<s:hidden id="logId" name="logId" />
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="common.system.tab.name" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="function.title" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<s:if
				test="#session.authenticater.authIdList.contains('08_08_06_00')">
				<button id="exportBtn" class="small-btn btn-grey" onclick="buttonClick();" type="button">
					<span class="icon-export"></span> <span><s:text
							name="common.button.export" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('08_08_05_00')">
				<button class="small-btn btn-grey" id="search" type="button">
					<span class="icon-search"></span> <span><s:text
							name="common.title.search" /></span>
				</button>
			</s:if>
		</div>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/javascript">
$("#flexigrid").flexigrid({
    url : 'logBrowseData.action', // ajax 请求的url
    dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
    colModel : [{
        display : '<s:text name="table.thead.logname" />',
        name : 'USER_ID',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="table.thead.username" />',
        name : 'USERNAME',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="table.thead.ip" />',
        name : 'IP',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="table.thead.time" />', 
        name : 'DATETIME',
        width : 140,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="table.thead.model" />', 
        name : 'ACTION',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="table.thead.type" />', 
        name : 'OpType',
        width : 120,
        sortable : true,
        align : 'left',
        process : opType
    } , {
        display : '<s:text name="table.thead.description" />', 
        name : 'OPERATION_INFO',
        width : 299,
        sortable : true,
        align : 'left'
    } ],
    customSearch : true,
    singleSelect:true,
    sortname : 'DATETIME', //默认排序字段名
    sortorder : "desc", //排序方式 asc/desc
    usepager : true, //使用分页
    //title : '虚拟机', //表格标题
    useRp : true, // 是否可以动态设置每页显示的结果数
    rp : 10, //默认每页结果数
    showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
    width : 'auto', //自动适应列宽
    height : 250, //设置高度
    pagestat: '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
	procmsg: '<s:text name="common.flexigrid.procmsg"/>', //处理数据时显示内容
    onRowSelect: openview
});
$(function(){
    //list自动高度
    $(".bDiv").height($(window).height()-$(".rightToolbar").height()-$(".hDiv").height()-$(".pDiv").height() - 18);
    /* console.log($('#exportBtn')); */
    /* expertDisable(); */
});

/*导出按钮*/
function buttonClick() {
	var url = "OperationLogDownLoad.action";
	/* window.open(url); */
	location.href=url;
}

/*当没有能导出的数据时，导出按钮点击无反应，添加title提示*/
function expertDisable(table){
	if (table.children().children('tr').length === 0) {
		$('#exportBtn').attr('title',"<s:text name='oplog.cannotexport'/>");
		$('#exportBtn').attr('onclick','return false;');
		$('#exportBtn').css('background-color','#bbbbbb');
	}
	else {
		$('#exportBtn').attr('title','');
		$('#exportBtn').css('background-color','#666666');
	}
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong>
					<s:text name="table.thead.logname" /> <s:text
						name="common.lable.point" />
			</strong></td>
			<td class="tdInput" valign="top"><input name="domain.userId"
				type="text" class="input" size="10" /></td>
			<td class="tdLabel" align="right" valign="top"><strong>
					<s:text name="table.thead.username" /> <s:text
						name="common.lable.point" />
			</strong></td>
			<td class="tdInput" valign="top"><input name="domain.userName"
				type="text" class="input" /></td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong>
					<s:text name="table.thead.starttime" /> <s:text
						name="common.lable.point" />
			</strong></td>
			<td class="tdInput" valign="top"><input id="startTime"
				class="input" name="domain.startTime" maxlength="128"
				onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
			</td>
			<td class="tdLabel" align="right" valign="top"><strong>
					<s:text name="table.thead.endtime" /> <s:text
						name="common.lable.point" />
			</strong></td>
			<td class="tdInput" valign="top"><input id="endTime"
				class="input" name="domain.endTime" maxlength="128"
				onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',readOnly:true})" />
			</td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong>
					<s:text name="table.thead.ip" /> <s:text name="common.lable.point" />
			</strong></td>
			<td class="tdInput" valign="top"><input id="ip4"
				name="domain.ip" type="text" class="input" /></td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<ul style="padding-right: 10px;">
					<li class="pageButon1 pageButtonSearch"
						onclick='javascript:$("#flexigrid").resetSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text
								name="common.button.reset" /> </a></li>
					<li class="pageButonRed pageButtonSearch"
						onclick='javascript:$("#flexigrid").submitSearch();'><a
						href="#" onclick='javascript:expertDisable($("#flexigrid").submitSearch());'><s:text
								name="common.button.submit" /> </a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>