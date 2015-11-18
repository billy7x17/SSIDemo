<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <form action="" method="post" name="mainForm" id="mainForm">
 	<input id="id" type="hidden" name="thresholdDomain.id"  value='<s:property value="thresholdDomain.id"/>'/>
	<input id="deviceId" type="hidden" name="deviceId"/>
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
    <table cellpadding="0" cellspacing="0" height="40">
      <tr>
        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
        <td><h3><s:text name="function.title"/></h3>
          <s:text name="function.title2"/>-><s:text name="function.title3"/>-><s:text name="function.title4"/></td>
      </tr>
    </table>
  </div>
    <%-- 提示信息 --%>
  <div class="messages succcess">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
  <div class="InsidePageButton"><a href="#" onclick="javascript:location.href='thresholdBeforeAdd.action'" class="button"><s:text name="threshold.list.titlebar.add"/></a ></div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
	url : 'thresholdBaseList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="threshold.name"/>', //标题
		name : 'threshold.EVENT_NAME', // 字段名称
		width : 200,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	},{
		display : '<s:text name="threshold.level"/>', //告警级别
		name : 'level.NAME',
		width : 180, 
		sortable : true,
		align : 'left'
	}, {
		display : '<s:text name="threshold.thresholdDeviceName"/>', //设备类型
		name : 't4.agentType',
		width : 130,
		sortable : true,
		align : 'left'
	},{
		display : '<s:text name="threshold.device.typeId"/>', //设备类型id
		name : 	't4.typeId',
		width : 115,
		sortable : false,
		hide:true
	},
    {
        display : '<s:text name="threshold.sign"/>',//告警判断条件
        name : 'threshold.PER_CONDITION',
        width : 200,
        sortable : true,
        align : 'left'
    },
	{
		display : '<s:text name="threshold.threshold" />',//告警区间
		name : 't4.agentInterval',
		width : 200,
		sortable : false,
		align : 'left'
	},
	{
		display : '<s:text name="threshold.createTime"/>', //创建时间
		name : 'threshold.CREATE_TIME',
		width : 125,
		sortable : true,
		align : 'left'
	},
	{
        display : '<s:text name="threshold.operate"/>',//操作
        name : 'operation',
        width : 120,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
		
    }],
    customSearch : true, 
    singleSelect:true,
	sortname : 'threshold.CREATE_TIME', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '显示记录从{from}到{to}，总数 {total} 条', //显示中文
	procmsg: '正在处理数据，请稍候 ...',//处理数据时显示内容
	onRowSelect: openview 
	});
$(function(){
	//list自动高度
	$(".bDiv").height($(window).height()-158);
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
function edit(thresholdId,typeID){
	$("#deviceId").val(typeID);
	$("#id").val(thresholdId);
	$('#mainForm').attr("action","thresholdBeforeEdit.action");
    $('#mainForm').submit();
}

function detail(){
	var f = $("input[name=funRadio]");
	var rosterID = "";
	for(var i = 0; i < f.length;i++){
		if(f[i].checked){
			rosterID = f[i].value;
		}
	}
	if(rosterID==""){
		alert('<s:text name="threshold.list.js.detail.alert"/>');
		return;
	}
	document.mainForm.action = "thresholdDetail.action";
	document.mainForm.thresholdId.value = rosterID;
	document.mainForm.submit();
}

//删除按钮
function del(thresholdId){
	if(confirm('<s:text name="threshold.list.js.del.alert.2"/>')){
		$("#id").val(thresholdId);
		$('#mainForm').attr("action","thresholdDelete.action");
        $('#mainForm').submit();
	}
	else{
			$("#id").val(thresholdId);
			$('#mainForm').attr("action","thresholdList.action");
	        $('#mainForm').submit();
		}
}

function formatCustomerResults(data){
    var arr = data.rows;
    for(var i in arr){
        var rowArr = arr[i].cell;
        for(var j in rowArr){
            if(rowArr[j] == null || rowArr[j] == 'null') rowArr[j] = '';
        }
        //rowArr[0] = "<a href=\"#\" onclick=\"operator('" + arr[i].id + "')\">" + rowArr[0] + "</a>";
    }
    return data;
}
function openview(id){
	viewDetail("thresholdDetail.action?thresholdDomain.id=" + id);
}
function rewriteDelLink(tdDiv, id){
 	tdDiv.innerHTML = '';
	var typeID = get_cell_text(id,3);
	var delimg = "<img title='<s:text name="common.title.delete"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_delete.png'>";
	<s:if test="#session.authenticater.authIdList.contains('05_10_02_03')">
   	 tdDiv.innerHTML = '<a href="javascript:del(\'' + id + '\')">' + delimg +'</a>&nbsp;';
    </s:if> 
	var editimg = "<img title='<s:text name="common.title.edit"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_edit.png'>";
	<s:if test="#session.authenticater.authIdList.contains('05_10_02_02')">
   	 tdDiv.innerHTML = tdDiv.innerHTML + '<a title="修改" href="javascript:edit(\'' + id  + '\',\'' + typeID + '\')">' + editimg +'</a>';
    </s:if> 
}
function operator(id){
    var subUrl = "thresholdDetail.action?thresholdDomain.id=" + id;
	 $('#mainForm').attr('action',subUrl);
	 $('#mainForm').submit(); 
} 
</script>
  <div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
     <tr>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.name"/><s:text name="common.lable.point"/></strong></td>
           <td class="tdInput" valign="top">
             <input type="text" id="eventName" name="domain.eventName" value="" class="input"/>
           </td>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.level"/><s:text name="common.lable.point"/></strong></td>
           <td class="tdInput" valign="top">
             <s:select list="levelList" listKey="levelId" listValue="levelName" name="domain.level" id="level" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
             </s:select>
           </td>
       </tr>
					 
	 	<tr>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="threshold.thresholdDeviceType"/><s:text name="common.lable.point"/></strong></td>
           <td class="tdInput" valign="top">
			<s:select list="deviceTypeList" listKey="rowId" listValue="agentName" name="domain.agentType" id="agentType" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
            </s:select>
           </td>
           <td class="tdVad" valign="top"><div id="deviceTypeTip"></div></td>
        </tr>
       	<tr>
          <td align="right" colspan="4">
          <ul>
              <li class="pageButon1" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'>重置条件</a></li>
              <li class="pageButon1" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'>提交查询</a></li>
              
          </ul>
          </td>
      </tr>
    </table>
</div>