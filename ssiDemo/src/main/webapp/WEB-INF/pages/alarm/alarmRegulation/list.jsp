<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<style>
</style>
 <form action="" method="post" name="mainForm" id="mainForm">
  <input id="ID" name="domain.ID" type="hidden" />
  <input id="ruleName" name="domain.ruleName" type="hidden" />
  <s:hidden id="ruleState" name="domain.ruleState" />
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
	 <h2 class="sec-label"><s:text name="function.title"/></h2>
	 <ul class="bread-cut">
		  <li><s:text name="menu.title"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="alarmConfig.title"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="function.title"/> </li>
	 </ul>
	  <div class="btn-group fn-right">
	  	<s:if test="#session.authenticater.authIdList.contains('05_10_05_01')">
	 		<button class="small-btn btn-grey" onclick="addAlarmRegulation();"  type="button">
				<span class="icon-add"></span>
				<span><s:text name="common.title.add" /></span>
		    </button>
	  	</s:if>
	  	<s:if test="#session.authenticater.authIdList.contains('05_10_05_13')">
	  		<button class="small-btn btn-grey" id="search"  type="button">
				<span class="icon-search"></span>
				<span><s:text name="common.title.search" /></span>
			</button>
	  	</s:if>
  	  </div>
	 
  </div>
  <%-- 提示信息 --%>
  <div class="messages succcess"  style="left:37%;width:45%;">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
	url : 'alarmRegulationList.action', // ajax 请求的url
	dataType : 'json', //返回数据类型
    preProcess:formatCustomerResults,
	colModel : [{
		display : '<s:text name="field.label.ruleName"/>', //第一列表头显示名称
		name : 'ruleName', // 字段名称
		width : 220,//列宽度
		sortable : true,// 是否可排序
		align : 'left'
	}, {
		display : '<s:text name="field.label.ruleAction"/>', //第三列表头显示名称….
		name : 'ruleAction',
		width : 200,
		sortable : true,
		align : 'left',
		process : formatRuleAction
	}, {
        display : '<s:text name="field.label.ruleState"/>', //第三列表头显示名称….
        name : 'ruleState',
        width : 200,
        sortable : true,
        align : 'left',
        process : formatRuleState
    },
	{
		display : '<s:text name="field.label.ruleDesc"/>',
		name : 'ruleDesc',
		width : 200,
		sortable : true,
		align : 'left'
	},
	{
        display : '<s:text name="common.title.operation"/>',
        name : 'operation',
        width : 240,
        sortable : false,
        align : 'left',
		process : reWriteLink
    }
    ],	
    customSearch : true, 
    singleSelect:true,
	sortname : 'ID', //默认排序字段名
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

//绑定事件列格式化
function formatRuleAction(tdDiv, id){
    var ruleAction = tdDiv.innerHTML;
    tdDiv.innerHTML = "";
    if(ruleAction.indexOf('1') > -1) {
        tdDiv.innerHTML = '<s:text name="field.label.ruleAction.clear" />' + "&nbsp;&nbsp;";
    } 
    if(ruleAction.indexOf('2') > -1) {
        tdDiv.innerHTML = tdDiv.innerHTML + '<s:text name="field.label.ruleAction.confirm" />' + "&nbsp;&nbsp;";
    } 
    if(ruleAction.indexOf('3') > -1) {
        tdDiv.innerHTML = tdDiv.innerHTML + '<s:text name="field.label.ruleAction.notufy" />' + "&nbsp;&nbsp;";
    } 
    if(ruleAction.indexOf('4') > -1) {
        tdDiv.innerHTML = tdDiv.innerHTML + '<s:text name="field.label.ruleAction.filter" />' + "&nbsp;&nbsp;";
    } 
    if(ruleAction.indexOf('5') > -1) {
        tdDiv.innerHTML = tdDiv.innerHTML + '<s:text name="field.label.ruleAction.redefine" />';
    } 
}
//格式化策略状态
function formatRuleState(tdDiv, id){
    var ruleState = tdDiv.innerHTML;
    tdDiv.innerHTML = "";
    if(ruleState=="0") {
        tdDiv.innerHTML = '<s:text name="field.label.ruleState.0" />' ;
    } else{
    	tdDiv.innerHTML = '<s:text name="field.label.ruleState.2" />' ;
    }
    
}

function openview(Id){
	var ruleName = get_cell_text(Id,0);
    viewDetail('alarmRegulationDetail.action?domain.ID=' + Id + "&domain.ruleName=" + ruleName);
}


function reWriteLink(tdDiv,id){
	var html ='';
	 var state = get_cell_text(id,2);
	 if(state=='<s:text name="field.label.ruleState.2" />'){
	 	  tdDiv.innerHTML = "";
	 	  //删除
	 	 <s:if test="#session.authenticater.authIdList.contains('05_10_05_02')">
	  		tdDiv.innerHTML = '<a  href="javascript:doDel(\'' + id + '\')"><span class=\"icon-del\" title=\'<s:text name="common.title.delete" />\'></span></a>';
	 	 </s:if>
	 	<s:if test="#session.authenticater.authIdList.contains('05_10_05_07')">
	 		tdDiv.innerHTML = tdDiv.innerHTML+'<a  href="javascript:doActivation(\'' + id + '\')"><span class=\"icon-actived\" title=\'<s:text name="field.label.ruleState.3" />\'></span></a>';
	 	</s:if>
	 }else{
		 html = html + '<div><s:text name="common.title.more.operation"/><span id='+id+' class=\"operSpan\" onmouseover=\"showOperButton(this);\" ></span></div><div class="selDiv" onmouseover=\"showButton(this);\"  onmouseout=\"hideButton(this)\" style="display:none;">';
		 //删除
		 <s:if test="#session.authenticater.authIdList.contains('05_10_05_02')">
		 	html = html+'<a  class="buttonA" style="margin-top:-5px;"  href="javascript:doDel(\'' + id + '\')"><span class=\"icon-del\"></span><span class="buttonSpan"><s:text name="common.title.delete" /></span> </a>';
		 </s:if>
		 //修改
		 <s:if test="#session.authenticater.authIdList.contains('05_10_05_03')">
		 	html = html+'<a class="buttonA" href="javascript:doUpdate(\'' + id + '\')"><span class=\"icon-edit\" style="margin-left:6px;margin-right: 20px;"></span><span class="buttonSpan"><s:text name="common.title.edit" /></span></a>';
		 </s:if>
		 //中止
		 <s:if test="#session.authenticater.authIdList.contains('05_10_05_06')">
		 	html = html+'<a class="buttonA" href="javascript:doSuspend(\'' + id + '\')"><span class=\"icon-suspend\" style="margin-left:6px;"></span> <span class="buttonSpan"><s:text name="field.label.ruleState.2" /></span></a>';
		 </s:if>
		 //绑定
		 <s:if test="#session.authenticater.authIdList.contains('05_10_05_04')">
		 	html = html+'<a  class="buttonA" href="javascript:doBind(\'' + id + '\')"><span class=\"icon-bind\" style="margin-left:6px;"></span><span class="buttonSpan"><s:text name="field.label.bind" /></span></a>';
		 </s:if>
		 
		 var isBind = get_cell_text(id,1);
	      if(isBind.length>1){
	    	//解绑
	    	  <s:if test="#session.authenticater.authIdList.contains('05_10_05_07')">
	    		html = html+'<a class="buttonA" href="javascript:doUnbind(\'' + id + '\')"><span class=\"icon-unBind\" style="margin-left:6px;"></span><span class="buttonSpan"><s:text name="field.label.unBind" /></span></a>';
	    	  </s:if>
	      }
	      tdDiv.innerHTML = html+"</div>" ;
	 }
}


function showOperButton(obj){
	$(".selDiv").css("display","none");
	$("#"+obj.id).parent().siblings().css("display","block").addClass("operationShowDiv").focus();
}

function hideButton(obj){
	$(obj).mouseout(function(e){
		if( !e ) e = window.event;  
	    var reltg = e.relatedTarget ? e.relatedTarget : e.fromElement;  
	    while( reltg && reltg != this ) reltg = reltg.parentNode;  
	    if( reltg != this ){  
	    	$(obj).css("display","none");
	    }  
	});
} 
 function showButton(obj){
	 $(obj).mouseover(function(e){
		if( !e ) e = window.event;  
	    var reltg = e.relatedTarget ? e.relatedTarget : e.fromElement;  
	    while( reltg && reltg != this ) reltg = reltg.parentNode;  
	    if( reltg != this ){  
	    	$(obj).css("display","block");
	    }  
	 });
}
/**
 * 添加按钮调用函数
 */
function addAlarmRegulation(){
	window.location = "alarmRegulationBeforeAdd.action";
}
/**
 * 修改按钮函数
 */
function doUpdate(Id){
	$('#ID').val(Id);
	$('#mainForm').attr('action',"alarmRegulationBeforeEdit.action");
	$('#mainForm').submit();
}

/**
 * 删除按钮调用函数
 */
function doDel(Id){
    if(confirm('<s:text name="common.message.delConfig"/>')){ 
    	var ruleName = get_cell_text(Id,0);
        $('#ID').val(Id);
        $('#ruleName').val(ruleName);
		$('#mainForm').attr('action',"alarmRegulationDelete.action");
		$('#mainForm').submit();    
           return false;                             
    }else{
    	$('#mainForm').attr('action',"alarmRegulationBase.action");
		$('#mainForm').submit();    
    }
}
/**
 * 详细按钮函数
 */
function doDetail(Id){
    $('#ID').val(Id);
    $('#mainForm').attr('action',"alarmRegulationDetail.action");
    $('#mainForm').submit();
}
	
/**
 * 解绑按钮函数
 */
function doUnbind(Id){
	if(confirm('<s:text name="common.message.unBindConfing" />')){ 
		var ruleName = get_cell_text(Id,0);
	    $('#ID').val(Id);
	    $('#ruleName').val(ruleName);
	    $('#mainForm').attr('action',"alarmRegulationUnbind.action");
	    $('#mainForm').submit();
	}
}

/**
 * 绑定按钮函数
 */
function doBind(Id){   
	var ruleName = get_cell_text(Id,0);
	var state = get_cell_text(Id,2);
	if(state=='<s:text name="field.label.ruleState.0" />'){
		state = '0';
	}else{
		state = '1';
	}
		    
    $('#ID').val(Id);
    $('#ruleName').val(ruleName);
    $('#ruleState').val(state);

    $('#mainForm').attr('action',"alarmRegulationBeforeBind.action");
    $('#mainForm').submit();
}


/**
* 中止按钮函数
*/
function doSuspend(Id){
   if(confirm('<s:text name="common.message.stopConfig" />')){ 
	   var ruleName = get_cell_text(Id,0);
       $('#ID').val(Id);
       $('#ruleName').val(ruleName);
       $('#mainForm').attr('action',"alarmRegulationSuspend.action");
       $('#mainForm').submit();
   }
}

/**
* 激活按钮函数
*/
function doActivation(Id){
   if(confirm('<s:text name="common.message.activedConfig" />')){ 
	   var ruleName = get_cell_text(Id,0);
       $('#ID').val(Id);
       $('#ruleName').val(ruleName);
	   $('#mainForm').attr('action',"alarmRegulationActivation.action");
	   $('#mainForm').submit();
   }
}

function enter_down() { 
	if (event.keyCode==13) {
		$('#ruleNameSearch').val("");
	    return false;
	} 
}

</script>
 <div id="detail" class="view-detail"></div>

<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
		 <tr>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.ruleName"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <s:textfield name="domain.ruleName" id="ruleNameSearch" maxlength="64" cssClass="input"  onKeydown="enter_down();" ></s:textfield>
            </td>
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="field.label.ruleAction"/><s:text name="common.lable.point" /></strong></td>
            <td class="tdInput" valign="top">
              <select name="domain.ruleAction" id="ruleActionSearch" class="newSelect">  
                     <option value="" ><s:text name="common.lable.select"/></option>   
                     <option value="2" ><s:text name="field.label.ruleAction.confirm" /></option>    
                     <option value="3" ><s:text name="field.label.ruleAction.notufy" /></option> 
                     <option value="4" ><s:text name="field.label.ruleAction.filter" /></option> 
                     <option value="5" ><s:text name="field.label.ruleAction.redefine" /></option> 
            </select>
            </td>
          </tr> 
          
     	<tr>
            <td align="right" colspan="4">
            <ul style="margin-right:10px;">
                <li class="pageButon1 pageButtonSearch" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'><s:text name="common.button.reset"/></a></li>
                <li class="pageButonRed pageButtonSearch" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'><s:text name="common.button.submit"/></a></li>
            </ul>
            </td>
        </tr>
    </table>
</div>
