<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<form action="" method="post" name="mainForm">
	<input type="hidden" id="roleId" name="roleId" />
	<input type="hidden" id="roleName" name="roleName" />
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
				test="#session.authenticater.authIdList.contains('08_02_01_00')">
				<button class="small-btn btn-grey" onclick="add();" type="button">
					<span class="icon-add"></span> <span><s:text
							name="common.title.add" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('08_02_05_00')">
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
    url : 'roleBrowserDate.action', // ajax 请求的url
    dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
    colModel : [{
        display : '<s:text name="function.lable.roleName" />', //第一列表头显示名称….
        name : 'ROLE_NAME',
        width : 180,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="function.lable.roleType" />', //第二列表头显示名称….
        name : 'ROLE_TYPE',
        width : 180,
        sortable : true,
        align : 'left',
        process : roleType
    }, {
        display : '<s:text name="function.lable.status" />', //第三列表头显示名称….
        name : 'STATUS',
        width : 180,
        sortable : true,
        align : 'left',
        process : status
    }, {
        display : '<s:text name="function.lable.description" />',//第四列……
        name : 'DESCRIPTION',
        width : 180,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="function.lable.createTime"/>', 
        name : 'CREATE_TIME',
        width : 200,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="function.lable.operation"/>',
        name : 'operation',
        width : 130,
        sortable : false,
        align : 'left',
        process : rewriteDelLink
    } ],
    singleSelect:true,
    customSearch : true,
    sortname : 'ROLE_ID', //默认排序字段名
    sortorder : "asc", //排序方式 asc/desc
    usepager : true, //使用分页
    //title : '虚拟机', //表格标题
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
    if(msg != null && msg != ""){
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

function status(tdDiv, id){
    var status = tdDiv.innerHTML;
    if(status==0)tdDiv.innerHTML = '<s:text name="function.lable.status.0"/>';
    if(status==1)tdDiv.innerHTML = '<s:text name="function.lable.status.1"/>';
}

function roleType(tdDiv, id){
	var type = tdDiv.innerHTML;
    if(type=='1')tdDiv.innerHTML = '<s:text name="function.lable.roleType.1" />';
    if(type=='2')tdDiv.innerHTML = '<s:text name="function.lable.roleType.2" />';
}

function rewriteDelLink(tdDiv, id){
	var divText = "";
	 <s:if test="#session.authenticater.authIdList.contains('08_02_02_00')">
	 	divText += '<a href="javascript:del(\''+id+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('08_02_03_00')">
	 	divText += '<a href="javascript:edit(\''+id+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 </s:if>
	 tdDiv.innerHTML = divText;
}

//角色添加
function add(){
	window.location = "roleAdd.action";
}

//角色修改
function edit(roleId){
    document.mainForm.action = "roleEdit.action";
    document.mainForm.roleId.value = roleId;
    document.mainForm.submit();
}
//角色删除
function del(roleId){
    if(confirm('<s:text name="web.confirm.delete"/>')){
        document.mainForm.action = "roleDel.action";
        document.mainForm.roleId.value = roleId;
        document.mainForm.roleName.value = get_cell_text(roleId, 0);
        document.mainForm.submit();
    }else{
    	document.mainForm.action = "roleBrowser.action";
    	document.mainForm.submit();
    }
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="function.lable.roleName" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="roleName" maxlength="32" class="input" name="domain.roleName" />
			</td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="function.lable.status" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><select name="domain   .status"
				id="status" class="newSelect">
					<option value="">
						<s:text name="common.lable.select" />
					</option>
					<option value="0">
						<s:text name="function.lable.status.0" />
					</option>
					<option value="1">
						<s:text name="function.lable.status.1" />
					</option>
			</select></td>
		</tr>
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="function.lable.roleType" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><select name="domain.roleType"
				id="roleType" class="newSelect">
					<option value="">
						<s:text name="common.lable.select" />
					</option>
					<option value="1">
						<s:text name="function.lable.roleType.1" />
					</option>
					<option value="2">
						<s:text name="function.lable.roleType.2" />
					</option>
			</select></td>
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
						href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text
								name="common.button.submit" /> </a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>
