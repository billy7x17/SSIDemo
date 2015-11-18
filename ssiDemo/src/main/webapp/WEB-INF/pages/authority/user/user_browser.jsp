<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<form action="" method="post" name="mainForm">
	<input type="hidden" id="roleId" name="userId" />
	<input type="hidden" id="userName" name="userName" />
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
				test="#session.authenticater.authIdList.contains('08_03_01_00')">
				<button class="small-btn btn-grey" onclick="add();" type="button">
					<span class="icon-add"></span> <span><s:text
							name="common.title.add" /></span>
				</button>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('08_03_04_00')">
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
    url : 'userBrowserData.action', // ajax 请求的url
    dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
    colModel : [{
        display : '<s:text name="web.table.columnName.userId"/>', //第一列表头显示名称
        name : 'u.USER_ID', // 字段名称
        //pk : true, //是否主键
        width : 100,//列宽度
        sortable : true,// 是否可排序
        hide : false //是否隐藏
    },{
        display : '<s:text name="web.table.columnName.userName"/>', //第二列表头显示名称….
        name : 'USER_NAME',
        width : 100,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="web.table.columnName.role"/>', //第二列表头显示名称….
        name : 'role_NAME',
        width : 100,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="web.table.columnName.siteName"/>', //第二列表头显示名称….
        name : 'SITE_NAME',
        width : 100,
        sortable : true,
        align : 'left'
    },{
        display : '<s:text name="web.table.columnName.sex"/>', //第三列表头显示名称….
        name : 'SEX',
        width : 50,
        sortable : true,
        align : 'left',
        process : sex
    },{
        display : '<s:text name="web.table.columnName.email"/>', //第四列……
        name : 'EMAIL',
        width : 132,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="web.table.columnName.mobile"/>', 
        name : 'MOBILE',
        width : 110,
        sortable : true,
        align : 'left'
    },  
       {
        display : '<s:text name="web.table.columnName.personnelId"/>', //第三列表头显示名称….
        name : 'PERSONNEL_ID',
        width : 110,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="web.table.columnName.createTime"/>', 
        name : 'u.CREATE_TIME',
        width : 89,
        sortable : true,
        align : 'left'
    }, {
        display : '<s:text name="web.table.columnName.operation"/>',
        name : 'operation',
        width : 115,
        sortable : false,
        align : 'left',
        process : rewriteDelLink
    } ],
    customSearch : true,
    singleSelect:true,
    sortname : 'u.CREATE_TIME', //默认排序字段名
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

//关闭tab 选项卡
try {
	if(typeof(window.parent.closeTab)){
		window.parent.closeTab('baseinfo');
	}
}catch(e) {
}

var userInfoName = '<s:text name="#session.userInfo.userName"/>';
var userInfoId = '<s:text name="#session.userInfo.userId"/>';

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

function sex(tdDiv, id){
    var sex = tdDiv.innerHTML;
    if(sex==1)tdDiv.innerHTML = '<s:text name="web.sex.male"/>';
    if(sex==2)tdDiv.innerHTML = '<s:text name="web.sex.female"/>';
    if(sex==3)tdDiv.innerHTML = '<s:text name="web.sex.unknown"/>';
}

function rewriteDelLink(tdDiv, id){
	var divText = "";
	var userId = get_cell_text(id, 0);
	if(userId != userInfoId && userId != 'admin'){
		<s:if test="#session.authenticater.authIdList.contains('08_03_02_00')">
			divText += '<a href="javascript:del(\''+id+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
		</s:if>
		<s:if test="#session.authenticater.authIdList.contains('08_03_03_00')">
		 	divText += '<a href="javascript:edit(\''+id+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
		</s:if>
		<s:if test="#session.authenticater.authIdList.contains('08_03_05_00')">
			/* divText += '<a href="javascript:pwd(\'' + id + '\')"><span class=\"icon-editPwd\" title=\'<s:text name="button.reset" />\'></span></a>'; */
		</s:if>
	}
    if(userId == userInfoId){
    	<s:if test="#session.authenticater.authIdList.contains('08_03_06_00')">
    		divText += '<a href=\"javascript:location.href=\'pswEdit.action\'\"><span class=\"icon-resetPwd\" title=\'<s:text name="button.pwdchange" />\'></span></a>';
        </s:if>
        /*可以修改自己的信息*/
	 	divText += '<a href="javascript:edit(\''+id+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 	/* divText += '<a href="javascript:pwd(\'' + id + '\')"><span class=\"icon-editPwd\" title=\'<s:text name="button.reset" />\'></span></a>'; */
    }
    tdDiv.innerHTML = divText;
}

//用户添加
function add(){
	window.location = "userAdd.action";
}

//用户修改
function edit(userId){
    document.mainForm.action = "userEdit.action";
    if(userId == userInfoId){
    	document.mainForm.action = document.mainForm.action + "?firstPage=firstPage"
    }
    document.mainForm.userId.value = userId;
    document.mainForm.submit();
}


//用户删除
function del(userId){
    if(confirm('<s:text name="common.message.delConfig"/>')){
        document.mainForm.action = "userDel.action";
        document.mainForm.userId.value = userId;
        document.mainForm.userName.value = get_cell_text(userId, 1);
        document.mainForm.submit();
    }else{
    	document.mainForm.action = "userBrowser.action";
    	document.mainForm.submit();
    }
}

//重置密码
function pwd(userId){
    if(confirm('<s:text name="common.message.reConfirm"/>')){
        document.mainForm.action = "clearPsw.action";
        document.mainForm.userId.value = userId;
        document.mainForm.userName.value = get_cell_text(userId, 1);
        document.mainForm.submit();
    }else{
    	document.mainForm.action = "userBrowser.action";
    	document.mainForm.submit();
    }
}

//用户详细信息
function openview(userId){
    url = "userMoreInfo.action?userId=" + userId + "&userInfo.userName=" + encodeURIComponent(get_cell_text(userId, 1));
    viewDetail(url);
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="web.table.columnName.userId" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="userId1"
				name="userInfo.userId" type="text" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="web.table.columnName.userName" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="userName"
				name="userInfo.userName" type="text" class="input" /></td>
			<td class="tdVad" valign="top"><div id="userNameTip"></div></td>
		</tr>
		<s:if test='#session.userInfo.roleType == "1"'>
			<tr>
				<td class="tdLabel" align="right" valign="top"><strong><s:text
							name="web.table.columnName.siteName" />
						<s:text name="common.lable.point" /></strong></td>
				<td class="tdInput" valign="top"><s:select id="sites"
						name="userInfo.siteId" list="sites" listValue="siteName"
						listKey="siteId" cssClass="newSelect" headerKey=""
						headerValue="%{getText('common.lable.select')}"></s:select></td>
			</tr>
		</s:if>
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