<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<form action="" method="post" name="mainForm" id="mainForm">
</form>
<table id="flexigrid"></table>
<script type="text/JavaScript">
$("#flexigrid").flexigrid({
	url : 'alarmOIDBaseSelect.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '规则匹配名称', //第二列表头显示名称….
		name : 'roster.Manufacture_ID',
		width : 115,
		sortable : true,
		align : 'left'
	},
	<s:if test="domain.rosterType==0">
	{
		display : '<s:text name="规则匹配阀值（规则）名称"/>',
		name : 'threshold.EVENT_NAME',
		width : 115,
		sortable : false,
		align : 'left'
	}
	</s:if>
	<s:else>
	{
		display : '<s:text name="规则匹配阀值（规则）名称"/>',
		name : 'access.ALARM_TITLE',
		width : 115,
		sortable : false,
		align : 'left'
	}
 	 </s:else>
	,{
		display : '映射告警级别', //第列……
		width : 115,
		name:'roster.Alarm_Grade',
		sortable : true,
		align : 'left'
		}, 
	{
	display : '规则匹配类型',
	name : 'roster.type',
	width : 115,
	sortable : true,
	align : 'left',
	process:rosterType
	},{
	display : '修改时间',
	name : 'roster.Modify_Time',
	width : 115,
	sortable : true,
	align : 'left'
	},{
	display : '操作',
	name : 'operation',
	width : 115,
	sortable : false,
	align : 'left',
	process : rewriteDelLink
}],
	sortname : 'Roster_ID', //默认排序字段名
	sortorder : "desc", //排序方式 asc/desc
	usepager : true, //使用分页
	title : '浏览', //表格标题
	useRp : true, // 是否可以动态设置每页显示的结果数
	rp : 10, //默认每页结果数
	showTableToggleBtn : true,// //是否显示【显示隐藏Grid】的按钮
	width : 'auto', //自动适应列宽
	height : 250, //设置高度
	pagestat: '显示记录从{from}到{to}，总数 {total} 条', //显示中文
	procmsg: '正在处理数据，请稍候 ...' //处理数据时显示内容
	});


$(function(){
	//list自动高度
	$(".bDiv").height($(window).height()-90);
	$(window).resize(function() {	
		var winHeight = $(window).height();
		var winWidth = $(window).width();
		//$(".bDiv").height(winHeight -90);
		//详细信息自动适应
		if($("#detail").width()>0){
			$(".flexigrid").width(winWidth -300);
			$(".rightToolbar").width(winWidth -334);
			var bars = $(".baseTitl").length;
			$(".baseTab").height(winHeight - bars * 26 - 76);
		}
	});
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
function reWriteLink(tdDiv,id){
	tdDiv.innerHTML = '<a href="javascript:operator(\'' + id + '\')">' + tdDiv.innerHTML+'</a>';
}
function rewriteDelLink(tdDiv, id){
	var name = get_cell_text(id, 0);
	var editimg = "<img title='选择' src='<%=request.getContextPath()%>/themes/default/images/tol_add.png'>";
   	 tdDiv.innerHTML = '<a href="javascript:edit(\'' + id + '@_@' + name  + '\')">' + editimg +'</a>';
}
function rosterType(tdDiv,id){
	var rosterType = tdDiv.innerHTML;
	if(rosterType==0){
		tdDiv.innerHTML = '阀值类型';
	}else{
		tdDiv.innerHTML = '规则类型';
		}
}
/**
 * 选择按钮函数
 */
function edit(rosterId){
		window.opener.setContent(rosterId);	
		window.close();
	}//end if     
//-->
</script>