<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 新样式start -->
<div class="rightToolbar">
		<%-- 当前位置 --%>
	<div class="rightToolbarCrumbs" >
		<h2 class="sec-label"><s:text name="function.title"/></h2>
		<ul class="bread-cut">
		  <li><s:text name="resource.title.show.ci"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="function.title"/> </li>
		</ul>
		<div class="btn-group fn-right" id="change">
		   <s:if test="#session.authenticater.authIdList.contains('02_10_01_00')">
	   		<button class="small-btn btn-grey" onclick="addZone();" type="button">
				<span class="icon-add"></span>
				<span><s:text name="common.title.add" /></span>
			</button>
		   </s:if>
		   <s:if test="#session.authenticater.authIdList.contains('02_10_05_00')">
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
	url : 'zoneResource.action',
	dataType : 'json',
	preProcess:formatCustomerResults,
	colModel : [
	        {
		        display : '<s:text name="resource.zone.roomName" />',
		        name : 'zone_name',
		        width : 180,
		        sortable : true
			},
			{
				display : '<s:text name="resource.zone.siteName" />',
				name : 'z.Site_id',
				width : 180,
				sortable : true
			},
			{
				display: '<s:text name="resource.zone.principal"/>',
				name : 'principal',
				width:130,
				sortable:true
			},
			{
				display : '<s:text name="resource.zone.address"/>',
				name : 'Address',
				width :180,
				sortable:true
			},
			{
				display :'<s:text name="resource.zone.description"/>',
				name :'description',
				width :200,
				sortable:true
			},
			{
				display : '<s:text name="resource.zone.operation"/>',
				name : 'zone_id',
				width : 160,
				sortable : false,
				process:reWriteLink
			}
			],
		customSearch : true,
	    singleSelect:true,
	    sortname : 'zone_id', //默认排序字段名
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
	 <s:if test="#session.authenticater.authIdList.contains('02_10_02_00')">
	 	divText += '<a href="javascript:zoneDelete(\''+pid+'\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
	 </s:if>
	 <s:if test="#session.authenticater.authIdList.contains('02_10_03_00')">
	 	divText += '<a href="javascript:edit(\''+pid+'\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
	 </s:if>
	 tdDiv.innerHTML = divText;
}

function zoneDelete(id){
	if(confirm('<s:text name="common.message.delConfig"/>?')){
		var zoneName = get_cell_text(id, 0);
		window.location = "zoneDelete.action?zoneId="+id + "&room.zoneName=" + encodeURIComponent(zoneName);
	}
}
function edit(id){
	window.location ="zoneBeforeEdit.action?zoneId="+id;
}
function addZone(){
	window.location = "zoneBeforeAdd.action";
}
</script>
	<div id="detail" class="view-detail"></div>
	<div id="searchDiv" style="display: none;">
	    <table border="0" class="searchTable">
	     	<tr>
	          <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.zone.roomName"/><s:text name="common.lable.point"/></strong></td>
	          <td class="tdInput" valign="top">
	            <input type="text" id="zoneName" name="room.zoneName" class="input"/>
	          </td>
	          <s:if test="roleType!=2">
	           <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.zone.siteName"/><s:text name="common.lable.point"/></strong></td>
	           <td class="tdInput" valign="top">
	             <s:select list="siteList" listKey="siteId" listValue="siteName" id="siteId" name="room.siteId" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}"></s:select>
	           </td>
	          </s:if>
	          <s:else>
	          	 <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.zone.principal"/><s:text name="common.lable.point"/></strong></td>
		         <td class="tdInput" valign="top">
		          	 <input type="text" id="principal" name="room.principal" class="input"/>
		         </td>
	          </s:else>
	        </tr>
	        <tr>
	         <s:if test="roleType!=2">
	         	<td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.zone.principal"/><s:text name="common.lable.point"/></strong></td>
		        <td class="tdInput" valign="top">
		          	 <input type="text" id="principal" name="room.principal" class="input"/>
		        </td>
	         </s:if>
	         <td class="tdLabel" align="right" valign="top"><strong><s:text name="resource.zone.address"/><s:text name="common.lable.point"/></strong></td>
	         <td class="tdInput" valign="top">
	            <input type="text" id="address" name="room.address" class="input"/>
	         </td>
	         </tr>
			 <tr>
	            <td align="right" colspan="4">
	            <ul style="padding-right: 10px;">
	                <li class="pageButon1 pageButtonSearch" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text name="common.button.reset"/> </a></li>
	                <li class="pageButonRed pageButtonSearch" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text name="common.button.submit"/> </a></li>
	            </ul>
	            </td>
	         </tr>
	    </table>
	</div>