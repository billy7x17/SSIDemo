<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 当前位置 -->
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="resource.site.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="resource.title.show.ci" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="resource.site.title" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<s:if
				test="#session.authenticater.authIdList.contains('02_09_01_00')">
				<s:if test="roleType!=2">
					<button class="small-btn btn-grey" onclick="addSite();" type="button">
						<span class="icon-add"></span> <span><s:text
								name="common.title.add" /></span>
					</button>
				</s:if>
			</s:if>
			<s:if
				test="#session.authenticater.authIdList.contains('02_09_05_00')">
				<s:if test="roleType!=2">
					<button class="small-btn btn-grey" id="search" type="button">
						<span class="icon-search"></span> <span><s:text
								name="common.title.search" /></span>
					</button>
				</s:if>
			</s:if>
		</div>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess" style="display: none;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
</div>
<table id="flexigrid"></table>
<script type="text/javascript">
$("#flexigrid").flexigrid({
	url : 'list.action',
	dataType : 'json',
	preProcess:formatCustomerResults,
	colModel : [
	{
		display:'<s:text name="site.table.siteName"/>',
		name:'site_name',
		width:200,
		sortable:true
	},
	{
		display:'<s:text name="site.table.lineName"/>',
		name:'line_num',
		width:200,
		sortable:true,
		process:reWriteLine
	},
	{
		display:'<s:text name="site.table.siteType"/>',
		name:'site_type',
		width:200,
		sortable:true,
		process:reWriteType
	},
	{
		display:'<s:text name="site.table.description"/>',
		name:'description',
		width:260,
		sortable:true
	}
	<s:if test="roleType!=2">
	,{
		display : '<s:text name="common.title.operation"/>',
		name : 'opera',
		width : 200,
		sortable : false,
		process:reWriteLink
	}
	</s:if>
	],
	customSearch : true,
    singleSelect:true,
    sortname : 'site_id', //默认排序字段名
    sortorder : "asc", //排序方式 asc/desc
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
function reWriteLink(tdDiv,pid){
	var divText = "";
	 <s:if test="#session.authenticater.authIdList.contains('02_09_02_00')">
	 	divText += '<a href="javascript:deleteSite(\''+pid+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span> </a>';
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('02_09_03_00')">
	 	divText += '<a href="javascript:editSite(\''+pid+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 </s:if>
	 tdDiv.innerHTML = divText;
}
//线路名称
function reWriteLine(tdDiv,pid){
	var line = tdDiv.innerHTML;
	if(line=="1")tdDiv.innerHTML='<s:text name="resource.site.lineName1"/>';
	if(line=="2")tdDiv.innerHTML='<s:text name="resource.site.lineName2"/>';
}
function reWriteType(tdDiv,pid){
	var type = tdDiv.innerHTML;
	if(type=="1")tdDiv.innerHTML='<s:text name="resource.site.typeName1"/>';
	if(type=="2")tdDiv.innerHTML='<s:text name="resource.site.typeName2"/>';
	if(type=="3")tdDiv.innerHTML='<s:text name="resource.site.typeName3"/>';
	if(type=="4")tdDiv.innerHTML='<s:text name="resource.site.typeName4"/>';
}

//添加站点
function addSite(){
	window.location="addSiteBefore.action";
}
//修改站点
function editSite(id){
	window.location = "editSiteBefore.action?siteId="+id;
}
//删除站点
function deleteSite(id){
	if(confirm('<s:text name="common.message.delConfig"/>?')){
		var siteName = get_cell_text(id, 0);
		window.location = "delSite.action?siteId="+id + "&site.siteName=" + encodeURIComponent(siteName);
	}
}

//详情
function openview(id){
	var siteName = get_cell_text(id, 0);
	viewDetail("siteDetail.action?siteId=" + id + "&site.siteName=" + encodeURIComponent(siteName));
}
</script>
<%-- 详细 --%>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">
		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='site.table.siteName' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="siteName" name="site.siteName" maxlength="32" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='site.table.siteType' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="siteTypeMap"
					key="key" value="value" name="site.siteType" id="site.siteType"
					cssClass="newSelect" headerKey=""
					headerValue="%{getText('common.lable.select')}" /></td>
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