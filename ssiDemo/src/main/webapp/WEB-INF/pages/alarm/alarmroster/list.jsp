<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <form action="" method="post" name="mainForm" id="mainForm">
 <input id="rosterID" name="alarmroster.rosterID" type="hidden" value='<s:property value="alarmroster.rosterID"/>'/>
  <input id="type" name="alarmroster.type" type="hidden" value='<s:property value="alarmroster.type"/>'/>
</form>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
    <table cellpadding="0" cellspacing="0" height="40">
      <tr>
        <td width="45"><img src="themes/blue/images/toolbarIcozy.png"/></td>
        <td><h3>规则匹配</h3>
          告警管理->告警配置->规则匹配管理</td>
      </tr>
    </table>
  </div>
   <%-- 提示信息 --%>
  <div class="messages succcess">
	 <div id="msgTip" class="msgSuccess" ></div>
  </div>
  <div class="InsidePageButton"><a href="#" onclick="javascript:location.href='alarmrosterBeforeAdd.action'" class="button">添加</a ></div>
</div>
<table id="flexigrid"></table>
<script type="text/JavaScript">

$("#flexigrid").flexigrid({
		url : 'alarmrosterBaseList.action?flag=<s:property value="flag"/>', // ajax 请求的url
		dataType : 'json', //返回数据类型
	    preProcess:formatCustomerResults,
		colModel : [
		   {
            display : '<s:text name="alarmroster.manufactureID"/>', //第3列……
            width : 155,
            name:'Manufacture_ID',
            sortable : true,
            align : 'left'
        },{
			display : '<s:text name="alarmroster.type"/>', //第1列……
			width : 135,
			name:'type',
			sortable : true,
			align : 'left',
			process:type
		},{
			display : '阀值（规则）名称', //第2列……
			name : 'type',
			width : 150,
			sortable : true,
			align : 'left'
		},
		{
			display : '<s:text name="alarmroster.alarmGrade"/>', //第4列
			name : 'gradeLevel.name',
			width : 135,
			sortable : true,
			align : 'left'
		},{
			display : '<s:text name="alarmroster.description"/>',//第5列
			name : 'Description',
			width : 225,
			sortable : true,
			align : 'left'
		}, {
			display : '<s:text name="alarmroster.modifyTime"/>', //第三列表头显示名称….
			name : 'Modify_Time',
			width : 120,
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
		customSearch : true,
	    singleSelect:true,
		sortname : 'Roster_ID', //默认排序字段名
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
        //rowArr[0] = "<a href=\"#\" onclick=\"operator('" + arr[i].id + "','"+rowArr[3]+"')\">" + rowArr[0] + "</a>";
    }
    return data;
}
function openview(id){
	var type = get_cell_text(id,1);
	if(type=='<s:text name="alarmroster.type.0"/>'){
		 type='0';
		}
	else if(type=='<s:text name="alarmroster.type.1"/>'){
			 type='1';
		}
	viewDetail("alarmrosterDetail.action?alarmroster.rosterID=" + id+"&alarmroster.type="+type);
}
function rewriteDelLink(tdDiv, id){
	 tdDiv.innerHTML = '';
	var type = get_cell_text(id,1);
	var delimg = "<img title='<s:text name="common.title.delete"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_delete.png'>";
	 <s:if test="#session.authenticater.authIdList.contains('05_10_03_04')">
    tdDiv.innerHTML = '<a href="javascript:del(\'' + id + '\')">' + delimg +'</a>&nbsp;';
    </s:if> 
	var editimg = "<img title='<s:text name="common.title.edit"/>' src='<%=request.getContextPath()%>/themes/default/images/tol_edit.png'>";
	<s:if test="#session.authenticater.authIdList.contains('05_10_03_03')">
    tdDiv.innerHTML = tdDiv.innerHTML + '<a href="javascript:edit(\'' + id  + '\',\'' + type + '\')">' + editimg +'</a>';
    </s:if> 
}
function alarmGrade(tdDiv, id){
	var alarmGrade = tdDiv.innerHTML;
	if(alarmGrade==0)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.0"/>';
	if(alarmGrade==1)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.1"/>';
	if(alarmGrade==2)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.2"/>';
	if(alarmGrade==3)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.3"/>';
	if(alarmGrade==4)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.4"/>';
	if(alarmGrade==5)tdDiv.innerHTML = '<s:text name="alarmroster.alarmGrade.5"/>';
}

function type(tdDiv, id){
	var type = tdDiv.innerHTML;
	if(type==0)tdDiv.innerHTML = '<s:text name="alarmroster.type.0"/>';
	if(type==1)tdDiv.innerHTML = '<s:text name="alarmroster.type.1"/>';
}
function operator(rosterID,type){
    var subUrl = "alarmrosterDetail.action?alarmroster.rosterID=" + rosterID+"&alarmroster.type="+type;
	 $('#mainForm').attr('action',subUrl);
	 $('#mainForm').submit(); 
} 
//修改
function edit(rosterID,type){
	if(type=='<s:text name="alarmroster.type.0"/>'){
		var type= 0;
		}
	else if(type=='<s:text name="alarmroster.type.1"/>'){
		var type= 1;
			}
	$("#rosterID").val(rosterID);
	$("#type").val(type);
	$('#mainForm').attr("action","alarmrosterBeforeEdit.action");
    $('#mainForm').submit();
}

//详细
 function detail(){
	var flag = $("input:radio[name^='fun']:checked").size();
	if(flag == 0){
		alert("<s:text name='common.message.select'/>");
	  return false;
	}else{    
	   var type = $("input:radio[name^='funRadio']:checked").attr('id');//类型          
       var rosterID = $("input:radio[name^='funRadio']:checked").attr('value');
       var subUrl = "alarmrosterDetail.action?alarmroster.rosterID="+rosterID+"&alarmroster.type="+type;
       $('#mainForm').attr('action',subUrl);
	   $('#mainForm').submit();   
	   return false;   
	}//end if     
  }
 //删除
function  del(rosterID){
		if(confirm('<s:text name="common.message.delConfig"/>')){              
			$("#rosterID").val(rosterID);
			$('#mainForm').attr("action","alarmrosterDelete.action");
	        $('#mainForm').submit();
	        return false;
		} 
	else{
		$("#rosterID").val(rosterID);
		$('#mainForm').attr("action","alarmrosterList.action");
        $('#mainForm').submit();
        return false;
		}  
}
</script>
<div id="detail" class="view-detail"></div>
<div id="searchDiv" style="display: none;">
    <table border="0" class="searchTable">
		<tr>  
            <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.type"/><s:text name="common.lable.point" /></strong></td>
            <td  class="tdInput" valign="top">
             <select name="alarmroster.type" id="type" class="newSelect">	
			         <option value="" <s:if test="%{alarmroster.type==null}">selected</s:if>><s:text name="common.lable.select"/></option>   
			         <option value="0" <s:if test="%{alarmroster.type==0}">selected</s:if>><s:text name="alarmroster.type.0"/></option>     		
				     <option value="1" <s:if test="%{alarmroster.type==1}">selected</s:if>><s:text name="alarmroster.type.1"/></option>
			</select>
            </td>
        </tr>
        <tr>
           <td class="tdLabel" align="right" valign="top"><strong><s:text name="alarmroster.alarmGrade"/><s:text name="common.lable.point" /></strong></td>
           <td class="tdInput" valign="top">
              <s:select list="levelList" listKey="alarmGrade" listValue="alarmGradeName" name="alarmroster.alarmGrade" id="alarmGrade" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
           </s:select>
           </td>
        </tr>
     	<tr>
            <td align="right" colspan="2">
            <ul>
                <li class="pageButon1" onclick='javascript:$("#flexigrid").resetSearch();'><a href="#" onclick='javascript:$("#flexigrid").resetSearch();'>重置条件</a></li>
                <li class="pageButon1" onclick='javascript:$("#flexigrid").submitSearch();'><a href="#" onclick='javascript:$("#flexigrid").submitSearch();'>提交查询</a></li>
                
            </ul>
            </td>
        </tr>
    </table>
</div>

