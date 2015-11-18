<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- 新样式start -->
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs" >
		<h2 class="sec-label"><s:text name="resource.cluster.title"/></h2>
		<ul class="bread-cut">
		  <li><s:text name="resource.title.show.ci"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="resource.cluster.title"/> </li>
	   </ul>
		<div class="btn-group fn-right" id="change">
		<s:if test="#session.authenticater.authIdList.contains('02_11_01_00')">
			<button class="small-btn btn-grey" onclick="addCluster();" type="button">
				<span class="icon-add"></span>
				<span><s:text name="common.title.add" /></span>
			</button>
		</s:if>
		<s:if test="#session.authenticater.authIdList.contains('02_11_05_00')">
			<button class="small-btn btn-grey" id="search" type="button">
				<span class="icon-search"></span>
				<span><s:text name="common.title.search" /></span>
			</button>
		</s:if>
	    </div>
	</div>
	  <%-- 提示信息 --%>
    <div class="messages succcess">
   		<div id="msgTip" class="msgSuccess" ></div>
  	</div>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>  
<script type="text/javascript">
$("#flexigrid").flexigrid({
	url : 'clusterResource.action',
	dataType : 'json',
	preProcess:formatCustomerResults,
	colModel : [
	{
		display:'<s:text name="resource.cluster.clusterName"/>',
		name:'cluster_name',
		width:180,
		sortable:true
	},
	{
		display:'<s:text name="resource.cluster.zoneName"/>',
		name:'zoneName',
		width:180,
		sortable:true
	},
	{
		display:'<s:text name="resource.cluster.siteName"/>',
		name:'siteName',
		width:180,
		sortable:true
	},
	{
		display:'<s:text name="resource.cluster.brand"/>',
		name:'brand',
		width:150,
		sortable:true
	},
	{
		display:'<s:text name="resource.cluster.description"/>',
		name:'description',
		width:180,
		sortable:true
	},
	{
		display : '<s:text name="common.title.operation"/>',
		name : 'opera',
		width : 180,
		sortable : false,
		process:reWriteLink
	}],
	customSearch : true,
    singleSelect:true,
    sortname : 'cluster_id', //默认排序字段名
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

//转换链接
function openview(pid){
	var clusterName = get_cell_text(pid, 0);
	viewDetail('clusterDetail.action?clusterId=' + pid + "&cluster.clusterName=" + encodeURIComponent(clusterName));
}
function reWriteLink(tdDiv,pid){
	var divText = "";
	 <s:if test="#session.authenticater.authIdList.contains('02_11_02_00')">
	 	divText += '<a href="javascript:deleteCluster(\''+pid+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('02_11_03_00')">
	 	divText += '<a href="javascript:editCluster(\''+pid+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 </s:if>
	 tdDiv.innerHTML = divText;
}
//删除机柜
function deleteCluster(id){
	if(confirm('<s:text name="common.message.delConfig"/>?')){
		var clusterName = get_cell_text(id, 0);
		window.location = "clusterDelete.action?clusterId="+id + "&cluster.clusterName=" + encodeURIComponent(clusterName);
	}
}
//修改机柜
function editCluster(id){
	window.location = "clusterBeforeEdit.action?clusterId="+id;
}
function addCluster(){
	window.location = "clusterBeforeAdd.action";
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
     	<tr>
          <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.cluster.clusterName"/><s:text name="common.lable.point"/></strong></td>
          <td class="tdInput" valign="top">
            <input type="text" id="clusterName" name="cluster.clusterName" class="input"/>
          </td>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.cluster.zoneName"/><s:text name="common.lable.point"/></strong></td>
           <td class="tdInput" valign="top">
           	 <input type="text" id="zoneName" name="cluster.zoneName" class="input"/>
           </td>
        </tr>
        <tr>
         <s:if test="roleType!=2">
          <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.cluster.siteName"/><s:text name="common.lable.point"/></strong></td>
          <td class="tdInput" valign="top">
             <s:select list="siteList" listKey="siteId" listValue="siteName" id="siteId" name="cluster.siteId" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}"></s:select>
          </td>
         </s:if>
          <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.cluster.brand"/><s:text name="common.lable.point"/></strong></td>
          <td class="tdInput" valign="top">
             <input type="text" id="brand" name="cluster.brand" class="input"/>
           </td>
         </tr>
		 <tr>
            <td align="right" colspan="4">
            <ul >
                <li class="pageButon1 pageButtonSearch" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text name="common.button.reset"/> </a></li>
                <li class="pageButonRed pageButtonSearch" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text name="common.button.submit"/> </a></li>
            </ul>
            </td>
         </tr>
    </table>
</div>