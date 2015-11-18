<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
 <form action="" method="post" name="mainForm" id="mainForm">
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
	  <h2 class="sec-label"><s:text name="alarmTitle.function.title"/></h2>
	  <ul class="bread-cut">
		  <li><s:text name="alarmTitle.menu.title"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="alarmTitle.alarmConfig.title"/> </li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="alarmTitle.function.title"/> </li>
	  </ul>
	  <div class="btn-group fn-right">
	  	<s:if test="#session.authenticater.authIdList.contains('05_10_06_02')">
	 		<button class="small-btn btn-grey" onclick="addAlarmTitle();"  type="button">
				<span class="icon-add"></span>
				<span><s:text name="common.title.add" /></span>
		    </button>
	  	</s:if>
	  	<s:if test="#session.authenticater.authIdList.contains('05_10_06_07')">
	  		<button class="small-btn btn-grey" onclick="allExportByCondition();"  type="button">
				<span class="icon-export"></span>
				<span><s:text name="common.button.export" /></span>
			</button>
	  	</s:if>
	  	<s:if test="#session.authenticater.authIdList.contains('05_10_06_06')">
	  		<button class="small-btn btn-grey" id="search" type="button">
				<span class="icon-search"></span>
				<span><s:text name="common.title.search" /></span>
			</button>
	  	</s:if>
  	  </div>
  </div>
  
   <%-- 提示信息 --%>
  <div class="messages succcess" style="left:37%;width:45%;">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
</div>

<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
	url : 'alarmBaseTitleList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="field.label.tcId"/>', //第一列表头显示名称
		name : 'tc_id', // 字段名称
		width : 160,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="field.label.tcTitle"/>', 
		name : 'tc_title',
		width : 200,
		sortable : true,
		align : 'left'
	},
	{
		display : '<s:text name="field.label.tcType"/>',
		name : 'tc_type',
		width : 150,
		sortable : true,
		align : 'left'
	},
    {
		display : '<s:text name="field.label.alarmIdentityID"/>',
		name : 'AlarmIdentityID',
		width : 200,
		sortable : true,
		align : 'left'
	},
	{
        display : '<s:text name="field.label.createTime"/>', 
        name : 'create_time',
        width : 180,
        sortable : true,
        align : 'left'
        //hide: true
    },
	{
        display : '<s:text name="common.title.operation"/>',
        name : 'operation',
        width : 156,
        sortable : false,
        align : 'left',
		process : rewriteDelLink
    }],
    customSearch : true, 
    singleSelect:true,
	sortname : 'create_time', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	showToggleBtn: false,
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
	procmsg: '<s:text name="common.flexigrid.procmsg"/>' //处理数据时显示内容
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
	 tdDiv.innerHTML = '';
	<s:if test="#session.authenticater.authIdList.contains('05_10_06_03')">
    tdDiv.innerHTML = '<a  href="javascript:doDel(\'' + id + '\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
    </s:if>  
 	<s:if test="#session.authenticater.authIdList.contains('05_10_06_04')">
    tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:doUpdate(\'' + id + '\')"><span class=\"icon-edit\" title=\'<s:text name="common.title.edit" />\'></span></a>';
    </s:if> 
}


/**
 * 添加按钮调用函数
 */
function add(){
	var subUrl = "alarmTitleBeforeAdd.action";
	$('#mainForm').attr('action',subUrl);
	$('#mainForm').submit();   
}
/**
 * 修改按钮函数
 */
function doUpdate(tcId){
	$('#mainForm').attr('action',"alarmTitleBeforeEdit.action?domain.tcId="+tcId);
	$('#mainForm').submit();
}

/**
 * 删除按钮调用函数
 */
function doDel(tcId){
	var tcTitle = get_cell_text(tcId, 1);
    if(confirm('<s:text name="common.message.delConfig"/>')){ 
    	var url = "alarmTitleDelete.action?domain.tcId="+tcId+"&domain.tcTitle="+tcTitle;
  		$('#mainForm').attr('action',url);
		$('#mainForm').submit(); 
    }else{
    	var url = "alarmTitleList.action";
  		$('#mainForm').attr('action',url);
		$('#mainForm').submit();
    }
}

//点击全部导出按钮，获取查询框的值按条件全部导出
function allExportByCondition() {
	//按条件导出，需要查询条件的值，去后台查询
	var params="";
	var url ="";
	for(var i=0;i<exportParam.length;i++){
		if(i!=0 && exportParam[i].value!=""){
			params +=exportParam[i].name+"="+exportParam[i].value+"&"; 
		}
	}
	if(params!=""){
		params = params.substring(0,params.length-1);
		url = "alarmTitleExport.action?"+params;
	}else{
		url = "alarmTitleExport.action";
	}
    window.location = url;
}
//添加告警标题
function addAlarmTitle(){
	window.location="alarmTitleBeforeAdd.action";
}
</script>

 <div id="detail" class="view-detail"></div>
 
<!-- 以下是查询页，不需要再写searchAction -->
<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
		 <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.tcId"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <input type="text" name="domain.tcId" id="tcId" maxlength="16" cssClass="input" />
            </td>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.tcTitle"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
              <s:textfield name="domain.tcTitle" id="tcTitle" maxlength="256" cssClass="input"></s:textfield>
            </td>
          </tr> 
		  <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.tcType"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <s:textfield name="domain.tcType" id="tcType" maxlength="16" cssClass="input" ></s:textfield>
            </td>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.alarmIdentityID"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
              <s:textfield name="domain.alarmIdentityID" id="alarmIdentityID" maxlength="256" cssClass="input"></s:textfield>
            </td>
          </tr> 
      
		 <tr>
            <td align="right" colspan="4">
            <ul style="margin-right: 10px;">
                <li class="pageButon1 pageButtonSearch" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text name="common.button.reset"/></a></li>
                <li class="pageButonRed pageButtonSearch" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text name="common.button.submit"/></a></li>
            </ul>
            </td>
        </tr>
    </table>
</div>