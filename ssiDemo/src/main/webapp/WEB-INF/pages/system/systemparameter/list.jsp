<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<form action="" method="post" name="mainForm" id="mainForm">
	<s:hidden name="domain.organize" id="organize"
		value="<s:property value='domain.organize'/>" />
	<s:hidden name="domain.paramKey" id="paramKey"
		value="<s:property value='domain.paramKey'/>" />
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
				test="#session.authenticater.authIdList.contains('08_10_01_00')">
				<button class="small-btn btn-grey" onclick="add();" type="button">
					<span class="icon-add"></span> <span><s:text
							name="common.title.add" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('08_10_04_00')">
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
<table id="flexigrid"></table>
<script type="text/JavaScript">
$("#flexigrid").flexigrid({
	url : 'systemparameterBaseList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="field.label.organize"/>', //第一列表头显示名称
		name : 'Organize', // 字段名称
		width : 170,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	},{
		display : '<s:text name="field.label.paramKey"/>', //第二列表头显示名称….
		name : 'Para_Key ',
		width : 180,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="field.label.paramValue"/>', //第三列表头显示名称….
		name : 'Para_Value',
		width : 220,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="field.label.description"/>',//第五列……
		name : 'Para_Describe',
		width : 180,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="field.label.updateTime"/>', //第四列……
		width : 170,
		name:'Update_Time',
		sortable : true,
		align : 'left'
	}, {
        display : '<s:text name="common.title.operation"/>',
        name : 'operation',
        width : 130,
        sortable : false,
        align : 'left',
        process: reWriteLink
    }], 
	customSearch : true,
    singleSelect:true,
	sortname : 'Update_Time', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
	procmsg: '<s:text name="common.flexigrid.procmsg"/>' //处理数据时显示内容
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
	//拿id相当于tr
    var arr = data.rows;
    for(var i in arr){
    	//拿cell相当于td
        var rowArr = arr[i].cell;
        for(var j in rowArr){
            if(rowArr[j] == null || rowArr[j] == 'null') rowArr[j] = '';
        }
    }
    return data;
}

function reWriteLink(tdDiv,id){
	var divText = "";
	var aaa = get_cell_text(id,0);
	var paramKey = get_cell_text(id,1);
	 <s:if test="#session.authenticater.authIdList.contains('08_10_02_00')">
	 	if((aaa!="<s:text name="common.performance.tab.name"/>"
	 			|| (paramKey!='intervalTime' && paramKey!='collectEndTime' && paramKey!='collectStartTime'))
	 			&& (aaa!="<s:text name="log.functionName"/>" && paramKey!='logRetentionTime')){
		 	divText += '<a href="javascript:del(\'' + aaa + '\',\'' + paramKey + '\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span> </a>';
	 	}
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('08_10_03_00')">
	 	if((aaa!="<s:text name="common.performance.tab.name"/>"
	 			|| (paramKey!='intervalTime' && paramKey!='collectEndTime' && paramKey!='collectStartTime'))
	 			&& (aaa!="<s:text name="log.functionName"/>" && paramKey!='logRetentionTime')){
		 	divText += '<a href="javascript:edit(\'' + aaa + '\',\'' + paramKey + '\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 	}else{
		 	divText += '<a href="javascript:edit(\'' + aaa + '\',\'' + paramKey + '\')"><span class=\"icon-edit-only\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 	}
	 </s:if>
	 tdDiv.innerHTML = divText;
}

//添加
function add(){
	window.location="systemparameterBeforeAdd.action";
}

//修改
function edit(organize,paramKey){
  	$("#paramKey").val(paramKey);           
	$("#organize").val(organize);  
	$('#mainForm').attr('action',"systemParamBeforeEdit.action");
    $('#mainForm').submit();
}

//删除
function del(organize,paramKey){
	if(confirm('<s:text name="common.message.delConfig"/>')){ 
	    $("#paramKey").val(paramKey);           
		$("#organize").val(organize);  
		$('#mainForm').attr('action',"systemParamDelete.action");
	    $('#mainForm').submit();     
		return false;   
	}else{
		$('#mainForm').attr('action',"systemparameterList.action");
	    $('#mainForm').submit();  
	}
}

</script>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.organize" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="organize" name="domain.organize" maxlength="32"
				value="<s:property value="domain.organize"/>" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.paramKey" /> <s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="paramKey" name="domain.paramKey" maxlength="64"
				value="<s:property value="domain.paramKey"/>" class="input" /></td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.paramValue" /> <s:text
						name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="paramValue" name="domain.paramValue" maxlength="128"
				value="<s:property value="domain.paramValue"/>" class="input" /></td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<ul style="padding-right: 10px;">
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