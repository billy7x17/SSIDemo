<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type='text/javascript'
	src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript'
	src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/dwr/interface/ReverseNewAlarm.js'></script>

<!-- 排序列样式 -->
<style type="text/css">
.flexigrid tr td.sorted {
	background: #e3e3e3;
	border-right: 1px solid #ddd;
	border-bottom: 1px solid #f3f3f3
}
</style>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="web.title.name" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="web.title.direction" /></li>
		</ul>
		<div class="btn-group fn-right" id="change">
		
			<button id="batchConfirm" class="small-btn btn-grey" onclick="batchClick(this);" type="button">
				<span class="icon-confirm"></span><span><s:text
						name="button.batchConfirm" /></span>
			</button>
			<button id="batchClear" class="small-btn btn-grey" onclick="batchClick(this);" type="button">
				<span class="icon-clear"></span><span><s:text
						name="button.batchClear" /></span>
			</button>
			
			<button id="exportBtn" class="small-btn btn-grey" onclick="buttonClick();" type="button">
				<span class="icon-export"></span><span><s:text
						name="button.export" /></span>
			</button>
			<button class="small-btn btn-grey" id="search" type="button">
				<span class="icon-search"></span><span><s:text
						name="common.title.search" /></span>
			</button>
		</div>
	</div>
	<%-- 提示信息 --%>
	<div class="messages succcess" style="display: none;">
		<div id="msgTip" class="msgSuccess"></div>
	</div>
</div>
<%-- 列表 --%>
<table id="flexigrid"></table>
<script type="text/javascript">
	var _alert = window.alert;
	window.alert = function(s) {
		if (s != "The specified call count is not a number") {
			_alert(s);
		}
	}
	function checkMap(cIName, id) {
		var returnValue = window
				.showModalDialog("commonModelOther.action?cIName=" + cIName, $(
						id).val(),
						"dialogWidth:800px;dialogHeight:460px;status:no;help:no;resizable:no;");
		$(id).val(returnValue);
	}

	function clickButton() {
		$("#flexigrid tr:eq(1)").css("top", 100);
		alert($("#flexigrid tr:eq(1)").css("top"));
	}

	var gData;
	function getData(data) {
		gData = data;
		return data;
	}
	$(function() {
		//setInterval("initDWR()", 20*1000);
	});
	//设置是否使用DWR轮询推送
	//window.onload = initDWR;
	function initDWR() {
		//dwr.engine.setActiveReverseAjax(true); // 激活反转 重要
		//dwr.engine.setNotifyServerOnPageUnload(true);
		//ReverseNewAlarm.getNewAlarmInfo3();
		ReverseNewAlarm.getNewAlarmInfo();

	}
	// 回调函数
	function refreshAlarm(data) {
		//dwr.util.setValue($("count"), data);
		var obj = eval("(" + data + ")");
		//gData.page = obj.page;
		gData.total = obj.total;
		var olength = obj.rows.length;
		for ( var i = 0; i < olength; i++) {

			for ( var j = 0; j < gData.rows.length; j++) {
				if (obj.rows[i].id == gData.rows[j].id) {
					gData.rows.splice(j, 1);
				}
			}

			gData.rows.unshift(obj.rows[i]);
		}

		while (gData.rows.length > 10) {
			gData.rows.pop();
		}
		$("#flexigrid").flexAddData(gData);
		// 添加滚动样式
		var h = $("#flexigrid tr:first-child").height();
		$("#flexigrid").css('marginTop', -h * olength + 'px');
		$("#flexigrid").animate({
			marginTop : "0px"
		}, 1000 * olength /* ,function(){
					for(var i = 0; i < olength;i++){
						$("#flexigrid tr").each(function(index) {
							if(index > olength - 1) {
								if(obj.rows[i].id == $(this).attr("id").substr(3)){
									$("#flexigrid tr").eq(index).remove();
									
									for(var j = 0; j < gData.rows.length;j++){
										if(j > olength - 1 && obj.rows[i].id == gData.rows[j].id){
											gData.rows.splice(j, 1);
										}
									}
								}
							}
						});
					}
			  }*/
		);

		//alert($("#flexigrid tr").length);

	}
	$("#flexigrid")
			.flexigrid(
					{
						url : 'alarmList.action?domain.sortName=Alarm_Time',
						dataType : 'json',
						preProcess : getData,
						colModel : [
								/*页面显示顺序为：
								 *      告警对象(IP) --> 告警级别            --> 告警标题 -->
								 *      告警内容                            --> 首次发生时间  --> 告警分类 -->
								 *      告警状态(确认状态) --> 设备类型            --> 重复次数 --> 
								 *		   最后发生时间                  --> 告警标识ID
								 * 
								 * */
								/* {
									display : '告警流水号',
									name : 'alarmID',
									pk : true,
									width : 80,
									sortable : true,
									align : 'left'
								}, */
								{ 
									display: '<input type="checkbox" name="checkboxTitle" onclick="checkclick(this)">',
									name: 'id',
									width: 40,
									align: 'center',
									checkbox:true
								},
								{
									display : "<s:text name='web.table.columnName.agentName'/>",
									name : 'agentName',
									width : 100,
									sortable : true,
									align : 'left'
								//process : detailLink
								},
								{
									display : "<s:text name='web.table.columnName.alarmGrade'/>",
									name : 'alarmGrade',
									width : 60,
									sortable : true,
									align : 'left',
									process : formatGrade
								},
								{
									display : "<s:text name='web.table.columnName.alarmTitle'/>",
									name : 'alarmTitle',
									width : 110,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.firstAlarmTime'/>",
									name : 'firstAlarmTime',
									width : 120,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmType'/>",
									name : 'alarmType',
									width : 60,
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
									align : 'left',
									process : formatStatus
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
									width : 120,
									sortable : true,
									align : 'left'
								},
								{
									display : "<s:text name='web.table.columnName.alarmIdentify'/>",
									name : 'alarmIdentify',
									width : 150,
									sortable : true,
									align : 'left'
								}],
						customSearch : true,
						singleSelect : true,
						striped : true,
						sortname : 'alarmTime',
						sortorder : "desc",
						usepager : true,
						//title : '告警实时浏览',
						useRp : true,
						rp : 10,
						showTableToggleBtn : true,
						showToggleBtn : true,
						//singleToggleBtn : true,
						//width : 'auto',
						//height : 250,
						pagestat : "<s:text name='web.table.page.hint1'/>{from}<s:text name='web.table.page.hint2'/>{to}<s:text name='web.table.page.hint3'/>{total}<s:text name='web.table.page.hint4'/>",
						procmsg : "<s:text name='web.table.page.waiting'/>",
						onRowSelect : openview
					//flexigrid.pack.js中增加方法
					});

	$(function() {
		//list自动高度
		$(".bDiv").height(
				$(window).height() - $(".rightToolbar").height()
						- $(".hDiv").height() - $(".pDiv").height() - 18);
		/* console.log($(window).height());
		console.log($(".rightToolbar").height());
		console.log($(".hDiv").height());
		console.log($(".pDiv").height());
		console.log($(".bDiv").height()); */
		/* $(window).resize(function() {	
		var winHeight = $(window).height();
		var winWidth = $(window).width(); */
		//$(".bDiv").height(winHeight -160);
		//详细信息自动适应
		/* if($("#detail").width()>0){
			$(".flexigrid").width(winWidth -300);
			$(".rightToolbar").width(winWidth -334);
			var bars = $(".baseTitl").length;
			$(".baseTab").height(winHeight - bars * 26 - 129);
		}
		});  */
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
		//定时刷新
		setInterval("alarmNotify()", 30 * 1000);

	});

	function alarmNotify() {
		$
				.ajax({
					type : "POST",
					url : "getalarmNotify.action",
					data : {
						'domain.queryType' : 'realTimeAlarmPage'
					},
					dataType : "json",
					success : function(c) {
						var msg = '';
						var type = c.notifyType; //1-无新告警，2-出现新告警，3-出现新的重复告警
						var time = c.notifyTime;
						var count = c.notifyCount;
						var clearCount = c.clearCount;
						if (type == 2) {
							var currentCount = $('#flexigrid').children().children().length;
							
							msg =  (count - currentCount + clearCount)+"<s:text name='web.realTimeAlarmHint.type2'/>";
						}
						if (type == 3) {
							msg = "<s:text name='web.realTimeAlarmHint.type3'/>";
						}
						if (type != 1) {
							var content = "<s:text name='web.realTimeAlarmHint.content1'/>"
									+ msg
									+ "<s:text name='web.realTimeAlarmHint.content2'/>"
									+ time
									+ "<s:text name='web.realTimeAlarmHint.content3'/>"
									+ count
									+ "<a href='javascript:reloadGrid();'><"
									+ "<s:text name='web.realTimeAlarmHint.content4'/>"
									+ "></a>";
							$("#msgTip").attr("class", "msgWarnings").html(
									content);
							$(".messages").show("slow");
						}
					}
				});
	}

	function reloadGrid() {
		$("#flexigrid").flexReload();
		$(".messages").css("display", "none");
		closeDetail();
	}
	function formatStatus(celDiv, pid) {
		var value = $(celDiv).html();
		if (value == '1') {
			var text = /* '<img src="themes/default/images/ico_info.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + */'<s:text name="web.translate.confirmStatus1"/>';
			$(celDiv).html(text);
		} else if (value == "2") {
			var text = /*  '<img src="themes/default/images/ico_successful.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + */'<s:text name="web.translate.confirmStatus2"/>';
			$(celDiv).html(text);
			//alert(pid);
			/* jQuery('#row' + pid).removeClass(); */
			//jQuery('#row' + pid).attr("bgcolor","#CCCCCC");
			/* jQuery('#row' + pid).addClass("erow"); */

			//var td = jQuery('#row' + pid).children('td');
			//$(td).each(function (index, domEle) { 
			// alert( $(domEle).attr("abbr") + '----' + $(domEle).attr("class"));
			//$(domEle).removeClass();
			//$(domEle).addClass("neu");
			// });
			//var sorttd = jQuery('#row' + pid);   //.find(".sorted");
			//alert($(sorttd).html());
		} else if (value == "3") {
			var text = /* '<img src="themes/default/images/ico_successful.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + */'<s:text name="web.translate.confirmStatus3"/>';
			$(celDiv).html(text);
			/*  jQuery('#row' + pid).removeClass();
			 jQuery('#row' + pid).addClass("erow"); */
		} else {
			//$(celDiv).html('未知');
			var text = /* '<img src="themes/default/images/ico_info.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + */'<s:text name="web.translate.confirmStatus1"/>';
			$(celDiv).html(text);
		}
	}

	function formatGrade(celDiv, pid) {
		var value = $(celDiv).html();
		//2-警告，2-一般，3-重要，4-紧急
		//最新的为3-警告，2-一般，1-重要，0-紧急
		if (value == "3") {
			celDiv.innerHTML = '<img src="themes/blue/images/warn-lemon-16.png" style="align:absbottom;margin-right: 5px; " /><s:text name="web.alarmGrade.warn" />';
		} else if (value == "2") {
			celDiv.innerHTML = '<img src="themes/blue/images/warn-yellow-16.png" style="align:absbottom;margin-right: 5px; " /><s:text name="web.alarmGrade.minor" />';
		} else if (value == "1") {
			celDiv.innerHTML = '<img src="themes/blue/images/warn-org-16.png" style="align:absbottom;margin-right: 5px; " /><s:text name="web.alarmGrade.major" />';
		} else if (value == "0") {
			celDiv.innerHTML = '<img src="themes/blue/images/warn-red-16.png" style="align:absbottom;margin-right: 5px; " /><s:text name="web.alarmGrade.critical" />';
		} else {
			celDiv.innerHTML = '<s:text name="field.label.unkown" />';
		}
	}

	function openview(pid) {
		var agentName = get_cell_text(pid,0);
		viewDetail('alarmDetail.action?alarmID=' + pid + "&domain.agentName=" + encodeURIComponent(agentName));
	}
	//转换链接
	function detailLink(tdDiv, pid) {
		tdDiv.innerHTML = '<a href="javascript:viewDetail(\'alarmDetail.action?alarmID='
				+ get_cell_text(pid, 0)
				+ '\')"><font color="#0000FF">'
				+ tdDiv.innerHTML + '</font></a>';
	}

	function buttonClick(com, grid) {
		var url = "realTimeAlarmDownLoad.action";
		//$('#mainForm').attr('action',url);
		//$('#mainForm').submit();
		location.href = url; 
		/* window.open(url);*/
	}
	
	/*批量确认和批量清除按钮*/
	function batchClick(obj){
		var cbObj = $("#flexigrid").getCheckValue();

		if (cbObj.length === 0) {
			alert('<s:text name="message.status.chooseAtLeastOne"/>');
			return false;
		}
		
		var ids = '';
		
		for ( var e in cbObj) {
			ids += cbObj[e] + ',';
		}
		
		ids = ids.substring(0, ids.length - 1);
		
		var url = '' ,errorMsg ='' , successMsg = '' , confirmMsg = '';
		
		if (obj.id === 'batchConfirm') {
			url = 'alarmBatchConfirm.action';
			errorMsg = '<s:text name="log.confirm.error" />';
			successMsg = '<s:text name="log.confirm.success" />';
			confirmMsg = '<s:text name="web.confirm.confirmBatchAlarm"/>';
		} else if(obj.id === 'batchClear') {
			url = 'alarmBatchClear.action';
			errorMsg = '<s:text name="log.clear.error" />';
			successMsg = '<s:text name="log.clear.success" />';
			confirmMsg = '<s:text name="web.confirm.confirmBatchAlarm"/>';
		}
		
		if(confirm(confirmMsg))
			$.ajax({
				type : "POST",
				url : url,
				data : {
					'domain.alarmID' : ids
				},
				dataType : "json",
				success : function(data) {
					if(data=="fail")
					    alert(errorMsg);
					else
						alert(successMsg);
					reloadGrid();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {   
					alert(errorMsg);
					reloadGrid();
				}   
			});
		else
	    	return false;
	    
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


<%-- 详细 --%>
<div id="detail" class="view-detail"></div>

<div id="searchDiv" style="display: none;">
	<table border="0" class="searchTable">

		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.alarmObject' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text" id="agentName"
				name="domain.agentName" maxlength="32" class="input" /></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='field.label.alarmTitle' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text" id="alarmTitle"
				name="domain.alarmTitle" maxlength="32" class="input" /></td>
		</tr>

		<tr>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.alarmType' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><s:select list="typeList"
					listKey="alarmType" listValue="alarmType" name="domain.alarmType"
					id="alarmType" cssClass="newSelect" headerKey=""
					headerValue="%{getText('common.lable.select')}">
				</s:select></td>
			<td class="tdLabel" align="right" valign="top"><strong><s:text
						name='web.table.columnName.alarmStatus' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><select
				name="domain.confirmStatus" id="confirmStatus" class="newSelect">
					<option value=""
						<s:if test="%{domain.confirmStatus==null}">selected</s:if>>
						<s:text name="common.lable.select" />
					</option>
					<option value="1"
						<s:if test="%{domain.confirmStatus==1}">selected</s:if>>
						<s:text name="web.translate.confirmStatus1" />
					</option>
					<option value="2"
						<s:if test="%{domain.confirmStatus==2}">selected</s:if>>
						<s:text name="web.translate.confirmStatus2" />
					</option>
					<option value="3"
						<s:if test="%{domain.confirmStatus==3}">selected</s:if>>
						<s:text name="web.translate.confirmStatus3" />
					</option>
			</select></td>
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
						name='web.table.columnName.alarmIdentify' />
					<s:text name="common.lable.point" /></strong></td>
			<td class="tdInput" valign="top"><input type="text"
				id="alarmIdentify" name="domain.alarmIdentify" maxlength="64"
				class="input" /></td>
		</tr>

		<tr>
			<td align="right" colspan="4">
				<ul>
					<li class="pageButon1 pageButtonSearch"
						onclick='javascript:$("#flexigrid").resetSearch();'><a
						href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text
								name="button.reset" /></a></li>
					<li class="pageButonRed pageButtonSearch"
						onclick='javascript:$("#flexigrid").submitSearch();'><a
						href="#" onclick='javascript:expertDisable($("#flexigrid").submitSearch());'><s:text
								name="button.commit" /></a></li>
				</ul>
			</td>
		</tr>
	</table>
</div>
