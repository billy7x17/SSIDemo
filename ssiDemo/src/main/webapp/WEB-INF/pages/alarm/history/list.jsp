<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="common.alarm.tab.name" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="function.title" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
			<button id="exportBtn" class="small-btn btn-grey" onclick="buttonClick();" type="button">
				<span class="icon-export"></span><span><s:text
						name="button.export" /></span>
			</button>
			<button class="small-btn btn-grey" id="search" type="button">
				<span class="icon-search"></span> <span><s:text
						name="common.title.search" /></span>
			</button>
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
	$("#flexigrid")
			.flexigrid(
					{
						url : 'alarmHistoryList.action?domain.sortColum=Alarm_Time',
						dataType : 'json',
						colModel : [
								{
									display : "<s:text name='web.table.columnName.agentName'/>",
									name : 'agentName',
									width : 140,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmGrade'/>",
									name : 'alarmGrade',
									width : 60,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmTitle'/>",
									name : 'alarmTitle',
									width : 125,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmType'/>",
									name : 'alarmType',
									width : 70,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.LocalTableIDRef'/>",
									name : 'LocalTableIDRef',
									width : 115,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.siteName'/>",
									name : 'siteName',
									width : 90,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmStatus'/>",
									name : 'confirmStatus',
									width : 60,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.clearStatus'/>",
									name : 'clearStatus',
									width : 60,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.deviceTypeName'/>",
									name : 'deviceTypeName',
									width : 100,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.count'/>",
									name : 'count',
									width : 60,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmTime'/>",
									name : 'alarmTime',
									width : 115,
									sortable : true,
									align : 'left'
								} ],
						customSearch : true,
						singleSelect : true,
						sortname : 'alarm.eventTime',
						sortorder : "desc",
						usepager : true,
						useRp : true,
						rp : 10,
						showTableToggleBtn : true,
						width : 'auto',
						height : 250,
						pagestat : "<s:text name='web.table.page.hint1'/>{from}<s:text name='web.table.page.hint2'/>{to}<s:text name='web.table.page.hint3'/>{total}<s:text name='web.table.page.hint4'/>",
						procmsg : "<s:text name='web.table.page.waiting'/>",
						onRowSelect : openview
					//flexigrid.pack.js中增加方法
					});
	$(function() {
		//list自动高度
		$(".bDiv").height($(window).height()-$(".rightToolbar").height()-$(".hDiv").height()-$(".pDiv").height() - 18);
		//显示提示消息
		var msg = "<s:property value='msg'/>";
		var errorMsg = "<s:property value='errorMsg'/>";
		if (msg != null && msg != "") {
			$("#msgTip").html(msg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		} else if (errorMsg != null && errorMsg != "") {
			$("#msgTip").attr("class", "msgErrors");
			$("#msgTip").html(errorMsg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		}
	});
	
	function openview(pid) {
		var agentName = get_cell_text(pid, 0);
		viewDetail('alarmHistoryDetail.action?domain.alarmID=' + pid + "&domain.agentName=" + encodeURIComponent(agentName));
	}

	function buttonClick(com, grid) {
		var url = "historyAlarmDownLoad.action";
		/*	window.open(url);  */
		location.href = url;
	}
	
	/*当没有能导出的数据时，导出按钮点击无反应，添加title提示*/
	function expertDisable(table){
		if (table.children().children('tr').length === 0) {
			$('#exportBtn').attr('title',"<s:text name='oplog.cannotexport'/>");
			$('#exportBtn').attr('onclick','return false;');
			$('#exportBtn').css('background-color','#bbbbbb');
		}
		else {
			$('#exportBtn').attr('title','');
			$('#exportBtn').css('background-color','#666666');
		}
	}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">

		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.agentName' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text" id="agentName"
				name="domain.agentName" maxlength="32" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.alarmTitle' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="alarmTitle" name="domain.alarmTitle" maxlength="32"
				class="input" /></td>
		</tr>

		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.startTime" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="startTime"
				class="input" name="domain.startTime" maxlength="128"
				onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
			</td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.endTime" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input id="endTime"
				class="input" name="domain.endTime" maxlength="128"
				onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',readOnly:true})" />
			</td>
		</tr>

		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.alarmGrade' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="levelList"
					listKey="alarmGrade" listValue="alarmGradeName"
					name="domain.alarmGrade" id="alarmGrade" cssClass="newSelect"
					headerKey="" headerValue="%{getText('common.lable.select')}">
				</s:select></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.alarmType" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="typeList"
					listKey="alarmType" listValue="alarmType" name="domain.alarmType"
					id="alarmType" cssClass="newSelect" headerKey=""
					headerValue="%{getText('common.lable.select')}">
				</s:select></td>
			
		</tr>

		<tr>
			<s:if test="#session.userInfo.roleType == 1">
				<td class="tdLabel" align="right" valign="top"><strong><s:text
							name="field.label.siteName" />
						<s:text name="common.lable.point" /></strong></td>
				<td class="tdInput" valign="top"><s:select list="siteList"
						listKey="siteId" listValue="siteName"
						name="domain.siteId" id="siteId" cssClass="newSelect"
						headerKey="" headerValue="%{getText('common.lable.select')}">
					</s:select></td>
			</s:if>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name="field.label.deviceTypeName" />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="deviceList"
					listKey="deviceType" listValue="deviceTypeName"
					name="domain.deviceTypeName" id="deviceType" cssClass="newSelect"
					headerKey="" headerValue="%{getText('common.lable.select')}">
				</s:select></td>
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
						href="#" onclick='javascript:expertDisable($("#flexigrid").submitSearch());'><s:text
								name="common.button.submit" /></a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>