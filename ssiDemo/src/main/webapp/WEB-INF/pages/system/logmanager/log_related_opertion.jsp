<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
</style>
<script language="javascript">
	
</script>
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
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="operationLog.relate" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<button class="small-btn btn-grey" onclick="buttonClick();" type="button">
				<span class="icon-export"></span> <span><s:text
						name="common.title.back" /></span>
			</button>
		</div>
	</div>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/javascript">
	$("#flexigrid")
			.flexigrid(
					{
						url : 'relatedOperationData.action?sessionId=<s:property value="sessionId"/>&userId=<s:property value="userId"/>&logId=<s:property value="logId"/>', // ajax 请求的url
						dataType : 'json', //返回数据类型
						//preProcess:formatCustomerResults,
						colModel : [
								{
									display : '<s:text name="table.thead.time" />',
									name : 'DATE_FORMAT',
									//pk : true,
									width : 340,
									sortable : false
								},
								{
									display : '<s:text name="table.thead.model" />', //第二列表头显示名称….
									name : 'ACTION',
									width : 342,
									sortable : false,
									align : 'left'
								},
								{
									display : '<s:text name="table.thead.description" />', //第三列表头显示名称….
									name : 'OPERATION_INFO',
									width : 400,
									sortable : false,
									align : 'left'
								} ],
						singleSelect : true,
						sortname : 'DATETIME',
						sortorder : "desc",
						usepager : true,
						useRp : true,
						rp : 10,
						showTableToggleBtn : true,
						width : 'auto',
						height : 250,
						pagestat : '<s:text name="common.flexigrid.pagestat.from"/>{from}<s:text name="common.flexigrid.pagestat.to"/>{to}<s:text name="common.flexigrid.pagestat.total"/> {total} <s:text name="common.flexigrid.pagestat.unit"/>', //显示中文
						procmsg : '<s:text name="common.flexigrid.procmsg"/>' //处理数据时显示内容
					});
	$(function() {
		//list自动高度
		$(".bDiv").height(
				$(window).height() - $(".rightToolbar").height()
						- $(".hDiv").height() - $(".pDiv").height() - 18);
	});
	/*返回按钮*/
	function buttonClick() {
		var url = "logBrowse.action";
		location.href = url;
	}

	
</script>