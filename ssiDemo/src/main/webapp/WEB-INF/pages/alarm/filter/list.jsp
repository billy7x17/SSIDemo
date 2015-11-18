<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
 <form action="" method="post" name="mainForm" id="mainForm">
 <input id="filterId" name="domain.filterId" type="hidden" value='<s:property value="domain.filterId"/>'/>
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
    <table cellpadding="0" cellspacing="0" height="40">
      <tr>
        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
        <td><h3>筛选器管理</h3>
          告警管理->告警配置->筛选器管理</td>
      </tr>
    </table>
  </div>
  <%-- 提示信息 --%>
  <div class="messages succcess">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
   <div class="InsidePageButton"><a href="#" onclick="javascript:location.href='alarmFilterBeforeAdd.action'" class="button">添加</a ></div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
	url : 'alarmBaseFilterList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="field.label.filterName"/>', //第一列表头显示名称
		name : 'filter.Filter_Name', // 字段名称
		width : 185,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="field.label.rosterName"/>', //第三列表头显示名称….
		name : 'roster.Manufacture_ID',
		width : 135,
		sortable : true,
		align : 'left'
	},
	{
		display : '<s:text name="field.label.filterStatus"/>',
		name : 'filter.Filter_Status',
		width : 120,
		sortable : true,
		align : 'left'
	},
	{
        display : '<s:text name="field.label.modifyTime"/>', //第二列表头显示名称….
        name : 'filter.Modify_Time',
        width : 135,
        sortable : true,
        align : 'left'
    },
	{
        display : '操作',
        name : 'operation',
        width : 120,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
    }],	
    /**
    <s:if test="#session.authenticater.authIdList.contains('05_10_04_06')">
    searchitems : [ {
    	display : '<s:text name="field.label.filterName"/>', //下拉菜单显示名称
    	name : 'filter.Filter_Name', //字段名
   		isdefault : true //默认选中
    	}],
    </s:if> 
*/	customSearch : true, 
    singleSelect:true,
	sortname : 'filter.Modify_Time', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '显示记录从{from}到{to}，总数 {total} 条', //显示中文
	procmsg: '正在处理数据，请稍候 ...', //处理数据时显示内容
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
function formatCustomerResults(data){
    var arr = data.rows;
    for(var i in arr){
        var rowArr = arr[i].cell;
        for(var j in rowArr){
            if(rowArr[j] == null || rowArr[j] == 'null') rowArr[j] = '';
        }
        //rowArr[0] = "<a href=\"#\" onclick=\"operation('" + arr[i].id + "')\">" + rowArr[0] + "</a>";
    }
    return data;
}
function openview(filterId){
	viewDetail("alarmFilterDetail.action?domain.filterId=" + filterId);
}
function reWriteLink(tdDiv,id){
	tdDiv.innerHTML = '<a href="javascript:operator(\'' + id + '\')">' + tdDiv.innerHTML+'</a>';
}
function rewriteDelLink(tdDiv, id){
	 tdDiv.innerHTML = '';
	var delimg = "<img title='<s:text name="common.title.delete"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_delete.png'>";
	 <s:if test="#session.authenticater.authIdList.contains('05_10_04_04')">
    tdDiv.innerHTML = '<a  href="javascript:doDel(\'' + id + '\')">' + delimg +'</a>&nbsp;';
    </s:if>  
	var editimg = "<img title='<s:text name="common.title.edit"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_edit.png'>";
 	<s:if test="#session.authenticater.authIdList.contains('05_10_04_03')">
    tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:doUpdate(\'' + id + '\')">' + editimg +'</a>';
    </s:if> 
}
function rosterType(tdDiv,id){
	var rosterType = tdDiv.innerHTML;
	if(rosterType==0){
		tdDiv.innerHTML = '阀值类型';
	}
	else if(rosterType==1){
		tdDiv.innerHTML = '规则类型';
		}
	else{
			tdDiv.innerHTML='';
		}
}
function operator(filterId){
	var subUrl = "alarmFilterDetail.action?domain.filterId=" + filterId;
	 $('#mainForm').attr('action',subUrl);
	 $('#mainForm').submit(); 
} 
/**
 * 添加按钮调用函数
 */
function add(){
	var subUrl = "alarmFilterBeforeAdd.action";
	$('#mainForm').attr('action',subUrl);
	$('#mainForm').submit();   
}
/**
 * 修改按钮函数
 */
function doUpdate(filterId){
	$('#filterId').val(filterId);
	$('#mainForm').attr('action',"alarmFilterBeforeEdit.action");
	$('#mainForm').submit();
}
/**
* 详细页面函数
*/
function doDetail(){
	var flag = $("input:radio[name^='fun']:checked").size();
	if(flag == 0){
		alert('<s:text name="common.message.select"/>');
		return false;
	}else{
		var id = $("input:radio[name^='fun']:checked").attr('value');
		var subUrl = "alarmFilterDetail.action?domain.filterId=" + id;
		$('#mainForm').attr('action',subUrl);
		$('#mainForm').submit();   
	}

}
/**
 * 删除按钮调用函数
 */
function doDel(filterId){
	    if(confirm('<s:text name="common.message.delConfig"/>')){ 
	    	$('#filterId').val(filterId);
			$('#mainForm').attr('action',"alarmFilterDelete.action");
			$('#mainForm').submit();    
            return false;                             
	    }else{
	    	$('#filterId').val(filterId);
			$('#mainForm').attr('action',"alarmFilterList.action");
			$('#mainForm').submit(); 
			 return false; 
		}//end if
	}//end if

/**
 * 查询按钮调用函数
 */
function doSearch(){
	var subUrl = "alarmFilterBeforeSearch.action";
	$('#mainForm').attr('action', subUrl);
	$('#mainForm').submit(); 
}


function selectContent(){
		  var url="alarmOIDSelect.action";
		  window.open(url,'mib','height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
	}

function setContent(content){
		  $('#rosterId').val(content);
	}
//-->
</script>
 <div id="detail" class="view-detail"></div>

<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
		 <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.filterName"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <s:textfield name="domain.filterName" id="filterName" maxlength="16" cssClass="input" ></s:textfield>
            </td>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.rosterName"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
              <s:textfield name="domain.manufactureId" id="manufactureId" maxlength="256" cssClass="input"></s:textfield>
             <%--   <a href="#" onclick="selectContent();return false;"><img align="absmiddle" src="<%=request.getContextPath()%>/themes/default/images/btn_view.gif" width="96" height="24"></a> --%>
            </td>
          </tr> 
          <tr>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.filterStatus"/><s:text name="common.lable.point" /></strong></td>
        	<td  class="tdInput" valign="top" colspan="2">
              <div style="float:left;">
                <input  type="radio" id="filterStatus" name="domain.filterStatus" value="0" /><s:text name="field.label.status0"/>
              </div>
              <div style="float:left; margin-left:10px;">
                <input  type="radio" id="filterStatus" name="domain.filterStatus" value="1" /><s:text name="field.label.status1"/>
              </div>
              <div id="filterStatusTip" style="float:left; margin-left:70px;"></div>
             </td>
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