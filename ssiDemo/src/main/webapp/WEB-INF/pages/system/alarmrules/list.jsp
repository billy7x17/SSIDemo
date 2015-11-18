<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form action="" method="post" id="mainForm" name="mainForm">
	<input type="hidden" name="alarmrules.rowID" id="rowID"
		value="<s:property value='alarmrules.rowID'/>" />
</form>
<!-- 排序列样式 -->
<style type="text/css">
.flexigrid tr td.sorted {
	background: #e3e3e3;
	border-right: 1px solid #ddd;
	border-bottom: 1px solid #f3f3f3
}
</style>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="menu.title" /> -><s:text name="menu.sub.title" /> -><s:text
					name="function.title" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<s:if
				test="#session.authenticater.authIdList.contains('05_10_07_01')">
				<button class="small-btn btn-grey" onclick="add();" type="button">
					<span class="icon-add"></span><span><s:text
							name="common.title.add" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('05_10_07_04')">
				<button class="small-btn btn-grey" id="search" type="button">
					<span class="icon-search"></span><span><s:text
							name="common.title.search" /></span>
				</button>
			</s:if>
		</div>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess" style="display: none;left:37%;width:45%;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
	<div class="InsidePageButton"></div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">
$("#flexigrid").flexigrid({
	url : 'alarmrulesBaseList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="alarmrules.typeID"/>', //第一列表头显示名称
		name : 't.AGENT_NAME', // 字段名称
		width : 150,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="alarmrules.alarmTitle"/>', //第二列表头显示名称….
		name : 'title.tc_title',
		width : 220,
		sortable : true,
		align : 'left',
	}, {
		display : '<s:text name="alarmrules.alarmOID"/>', //第三列……
		width : 220,
		name:'alarmOID',
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="alarmrules.alarmLevel"/>',
		name : 'level.NAME',
		width : 100,
		sortable : true,
		align : 'left',
	}, {
        display : '<s:text name="alarmrules.alarmIdentityID"/>',
        name : 'r.AlarmIdentityID',
        width : 200,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="common.title.operation"/>',
        name : 'operation',
        width : 160,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
		
    }],
	customSearch : true,
    singleSelect:true,
	sortname : 'rowID', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
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

function rewriteDelLink(tdDiv, id){
	var divText = "";
 	 <s:if test="#session.authenticater.authIdList.contains('05_10_07_02')">
	 	divText += '<a href="javascript:del(\''+id+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span> </a>';
    </s:if> 
    <s:if test="#session.authenticater.authIdList.contains('05_10_07_03')">
 		divText += '<a href="javascript:edit(\''+id+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
    </s:if> 
    tdDiv.innerHTML = divText;
}

function openview(rowID){
	var typeId = get_cell_text(rowID, 0);
	viewDetail("alarmrulesDetail.action?alarmrules.rowID=" + rowID + "&alarmrules.typeID=" + typeId);
}

//添加按钮
function add(){
	window.location="alarmrulesBeforeAdd.action";
}

// 修改按钮
function edit(rowID){
	$("#rowID").val(rowID);
	$("#mainForm").attr("action","alarmrulesBeforeEdit.action");
	$("#mainForm").submit();
}

//删除按钮
function del(rowID){
	var typeId = get_cell_text(rowID, 0);
	if(confirm('<s:text name="common.message.delConfig"/>')){
		$("#rowID").val(rowID);
		$("#mainForm").attr("action","alarmrulesDelete.action?alarmrules.typeID=" + typeId);
		$("#mainForm").submit();
	}else{
		$("#mainForm").attr("action","alarmrulesList.action");
		$("#mainForm").submit();
	}
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="alarmrules.typeID" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select
					list="alarmRulesTypeList" id="typeID" listKey="typeID"
					listValue="agentName" name="domain.typeID" cssClass="newSelect"
					headerKey="" headerValue="%{getText('common.lable.select')}">
				</s:select></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="alarmrules.alarmOID" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="alarmOID"
				class="input" name="domain.alarmOID" maxlength="64"
				value="<s:property value="alarmrules.alarmTitle"/>" /></td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="alarmrules.alarmTitle" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="alarmTitle"
				class="input" name="domain.alarmTitle" maxlength="64"
				value="<s:property value="alarmrules.alarmTitle"/>" /></td>

			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="alarmrules.alarmLevel" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="LevelList"
					id="alarmLevel" listKey="alarmLevel" listValue="alarmLevelName"
					name="domain.alarmLevel" cssClass="newSelect" headerKey=""
					headerValue="%{getText('common.lable.select')}">
				</s:select></td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<ul>
					<li class="pageButon1 pageButtonSearch"
						onclick='javascript:$("#flexigrid").resetSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text
								name="common.button.reset" /></a></li>
					<li class="pageButonRed pageButtonSearch"
						onclick='javascript:$("#flexigrid").submitSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text
								name="common.button.submit" /></a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>