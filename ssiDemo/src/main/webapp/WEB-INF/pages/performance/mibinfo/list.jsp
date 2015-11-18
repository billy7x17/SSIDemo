<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <form action="" method="post" name="mainForm" id="mainForm">
  	<s:hidden name="mibId" id="mibId"/>
	<s:hidden name="mibName" id="mibName"/>
	<s:hidden name="oid" id="oid"/>
 </form> 
 
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
  	<h2 class="sec-label"><s:text name="function.title"/></h2>
  	<ul class="bread-cut">
	  <li><s:text name="menu.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="function.title"/> </li>
	</ul>
	<div class="btn-group fn-right" id="change">
	  <s:if test="#session.authenticater.authIdList.contains('04_12_01_00')">
	  	<button class="small-btn btn-grey" onclick="addMib();" type="button">
			<span class="icon-add"></span>
			<span><s:text name="common.title.add" /></span>
	    </button>
	  </s:if>
	  <s:if test="#session.authenticater.authIdList.contains('04_12_04_00')">
	  	<button class="small-btn btn-grey" id="search" type="button">
			<span class="icon-search"></span>
			<span><s:text name="common.title.search" /></span>
		</button>
	  </s:if>
	</div>
  </div>
    <%-- 提示信息 --%>
  <div class="messages succcess" style="left: 37%;">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">
$("#flexigrid").flexigrid({
	url : 'mibBaseList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="mib.mibName"/>', //第一列表头显示名称
		name : 't1.MIB_NAME', // 字段名称
		width : 155,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="mib.mibDeviceType"/>', //第三列表头显示名称….
		name : 't3.AGENT_NAME',
		width : 145,
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="mib.oid"/>', 
		width : 210,
		name:'t1.oid',
		sortable : true,
		align : 'left'
	},
	{
		display:'<s:text name="mib.indexGroup"/>',
		width:120,
		name:'mg.mib_group_name ',
		sortable:true,
		align:'left'
	}
	, 
	{
		display:'<s:text name="mib.indexUnit"/>',
		width:120,
		name:'t1.MIB_UNIT',
		sortable:true,
		align:'left',
		process:reMibUnit
	},
	{
		display : '<s:text name="mib.createTime"/>',
		name : 't1.CREATE_TIME',
		width : 150,
		sortable : true,
		align : 'left'
	},
	{
        display : '<s:text name="common.title.operation"/>',
        name : 'operation',
        width : 138,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
    }],
	customSearch : true,
    singleSelect:true,
	sortname : 't1.create_time', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	showToggleBtn: false,
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
	procmsg: '<s:text name="common.flexigrid.procmsg"/>', //处理数据时显示内容
	onRowSelect: openview
	});
$(function(){
	//list自动高度
	$(".bDiv").height($(window).height()-$(".rightToolbar").height()-$(".hDiv").height()-$(".pDiv").height() - 18);
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

function openview(mibId){
	var mibName = get_cell_text(mibId, 0);
	viewDetail("mibInfoDetail.action?mibId=" + mibId + "&mibinfoDomain.mibName=" + encodeURIComponent(mibName));
}
function rewriteDelLink(tdDiv, id){
	tdDiv.innerHTML = '';
	<s:if test="#session.authenticater.authIdList.contains('04_12_02_00')">
   		 tdDiv.innerHTML = '<a href="javascript:del(\'' + id + '\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
	</s:if>
	<s:if test="#session.authenticater.authIdList.contains('04_12_03_00')">
    	tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:edit(\'' + id + '\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	</s:if>
}
function reMibUnit(tdDiv,id){
	var unit = tdDiv.innerHTML;
	if(unit=='0')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.0"/>';
	if(unit=='1')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.1"/>';
	if(unit=='2')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.2"/>';
	if(unit=='3')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.3"/>';
	if(unit=='4')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.4"/>';
	if(unit=='5')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.5"/>';
	if(unit=='6')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.6"/>';
	if(unit=='7')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.7"/>';
	if(unit=='8')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.8"/>';
	if(unit=='9')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.9"/>';
	if(unit=='10')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.10"/>';
	if(unit=='11')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.11"/>';
	if(unit=='12')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.12"/>';
	if(unit=='13')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.13"/>';
	if(unit=='14')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.14"/>';
	if(unit=='15')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.15"/>';
	if(unit=='16')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.16"/>';
	if(unit=='17')tdDiv.innerHTML='<s:text name="mibinfo.mibUnit.17"/>';
}

//添加
function addMib(){
	window.location="mibInfoBeforeAdd.action";
}

//修改
function edit(mibId){
	$("#mibId").val(mibId);
	$('#mainForm').attr("action","mibInfoBeforeUpdate.action");
    $('#mainForm').submit();
}

//删除
function del(mibId){
	if(confirm('<s:text name="common.message.delConfig"/>')){
		$("#mibId").val(mibId);
		var mibName = get_cell_text(mibId, 0);
		$('#mainForm').attr("action","mibInfoDelete.action?mibinfoDomain.mibName=" + encodeURIComponent(mibName));
        $('#mainForm').submit();
	}else{
		$('#mainForm').attr("action","mibList.action");
        $('#mainForm').submit();
	}
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
    			 
                     <tr>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="mib.mibName"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
			              <input type="text" id="mibName" name="domain.mibName" value="" class="input"/>
			            </td>
			            <td class="tdLabel" align="right" valign="top"><strong><s:text name="mib.mibDeviceType"/><s:text name="common.lable.point"/></strong></td>
			            <td class="tdInput" valign="top">
			            <s:select list="deviceTypeList" listKey="typeId" listValue="typeName" name="domain.typeId" id="typeId" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
	                    </s:select>
			            </td>
			         </tr>
			         
			         
			          <tr>
			             <td class="tdLabel" align="right" valign="top"><strong><s:text name="mib.is.collection1"/><strong><s:text name="common.lable.point"/></strong></strong></td>
			            <td  class="tdInput" valign="top">
			              <select id="isCollection" name="domain.isCollection" Class="newSelect">
			              	<option value=""><s:text name="common.lable.select"/></option>
			              	<option value="1"><s:text name="mib.is.collection2"/></option>
			              	<option value="0"><s:text name="mib.is.collection3"/></option>
			              </select>
			             </td>
			         </tr>
			         
         	<tr>
            <td align="right" colspan="4">
            <ul style="margin-right: 10px;">
                <li class="pageButon1 pageButtonSearch" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'>重置条件</a></li>
                <li class="pageButonRed pageButtonSearch" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'>提交查询</a></li>
                
            </ul>
            </td>
        </tr>
    </table>
</div>