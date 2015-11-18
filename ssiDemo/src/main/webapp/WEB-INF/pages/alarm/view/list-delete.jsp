<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <script type="text/javascript">

 $(function(){

	 /**
	//将菜单div,span属性初始化
	 var menuDiv = $(window.parent.document).find(".leftAcd > div");
	 $(menuDiv).each(function (index, domEle) { 
	   $(domEle).attr("class","leftAcdTit");
	   var span = $(domEle).find("span");
	   $(span).attr("class","leftAcdArr");
	   $(span).attr("style","background: url('themes/default/images/acd_expand.png') no-repeat scroll left top transparent;");
	 });

	 
	 //设置选中状态
	 var  alarmViewDiv = $(window.parent.document).find("#alarmViewDiv");
	 var  alarmViewSpan = $(window.parent.document).find("#alarmViewSpan");
	 $(alarmViewSpan).removeAttr("class");
	 $(alarmViewSpan).removeAttr("style");
	 $(alarmViewDiv).attr("class","cur_menu");
	 **/
	 
 });

 
$(function(){
	//window.setTimeout("datagride()",1000);
	datagride();
	window.setInterval("reloadData()",180000); 
	//解决ie7下表头不滚动问题
	$('.datagrid-header').attr("style","position:relative;");
});


function datagride(){

	var grideHeight = $(window.parent.document).height()-208;
	var gridWidth = $(window.parent.document).width()-204;

	  $('#tt').datagrid({
	    	url: 'alarmList.action',
	 		title: '<s:text name="function.title" />',
	 		//width: gridWidth,
	 		height: grideHeight,
	 		nowrap: true,  //是否在一行显示数据,出列宽时将会自动截取
	 		striped: true, //是否显示斑马线
	 		border:false, //是否显示面板的边界
	 		singleSelect:true, //是否允许只选择一行
	 		sortName: 'alarmTime',
	 	//	fit:true,   //是否允许表格自动缩放，以适应父容器
	 	//  fitColumns:true, //自动使列适应表格宽度以防止出现水平滚动
	 		sortOrder: 'desc',
	 		remoteSort: false,
	 		pageSize:10,
	 		loadMsg:'<s:text name="message.fresh" />',
	 		frozenColumns:[[
                {field:'alarmIP',title:'<s:text name="field.label.alarmIP" />',width:100,align:'center',resizable:false,
                	 formatter:function(value,rowData,rowIndex){
                	//var row = $('#tt').datagrid('getSelected');
                	var alarmId = rowData.alarmId;
                    return "<a href='#' onclick='openDetail(" + alarmId + ");'><font color=‘#0000FF’>" + value + "</font></a>";  
                       }}
	 					]],
			columns:[[
                {field:'alarmTime',title:'最新告警时间',width:150,align:'center', resizable:false},
                {field:'alarmStatus',title:'告警状态',width:100,align:'center',resizable:false,
				    formatter:function(value,rec){
					   if(value =="1"){
						   return '未确认未清除';
					   }else if(value =="2"){
						   return '已确认未清除';
					   }
				    }},
				{field:'eventID',title:'关联事件标识',width:100,align:'center',resizable:false},
				{field:'alarmTitle',title:'<s:text name="field.label.alarmTitle" />',width:350,align:'center', resizable:false,
				    formatter:function(value,rec){
					   if(null != value && value.length>25){
						   var subValue = value.substring(0, 25) + '...';
						   return subValue;
					   }else{
						   return value;
					   }
				    }},
				    {field:'alarmGrade',title:'<s:text name="field.label.alarmGrade" />',width:80,align:'center', resizable:false,
					    formatter:function(value,rec){
						//0-通知，1-警告，2-次要，3-重大，4-严重
						   if(value =="0"){
							   return '<img src="themes/default/images/alert01.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + '<s:text name="field.label.alarmtype0" />';
						   }
						   if(value =="1"){
							   return '<img src="themes/default/images/alert02.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + '<s:text name="field.label.alarmtype1" />';
						   }
						   if(value =="2"){
							   return '<img src="themes/default/images/alert03.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + '<s:text name="field.label.alarmtype2" />';
						   }
						   if(value =="3"){
							   return '<img src="themes/default/images/alert04.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + '<s:text name="field.label.alarmtype3" />';
						   }
						   if(value =="4"){
							   return '<img src="themes/default/images/alert05.png" style="width:20px; margin-bottom:-6px; align:absbottom; height:20px;" />' + '<s:text name="field.label.alarmtype4" />';
						   }
						   return  '<s:text name="field.label.unkown" />';
					    }},
			    {field:'alarmType',title:'<s:text name="field.label.alarmType" />',width:100,align:'center', resizable:false},
			    {field:'deviceTypeName',title:'<s:text name="field.label.deviceTypeName" />',width:90,align:'center',resizable:false},
			    {field:'alarmContent',title:'告警内容',width:350,align:'center',resizable:false,
				    formatter:function(value,rec){
					   if(null != value && value.length>25){
						   var subValue = value.substring(0, 25) + '...';
						   return subValue;
					   }else{
						   return value;
					   }
				    }},
                {field:'count',title:'<s:text name="field.label.count" />',width:80,align:'center',resizable:false},
                {field:'firstAlarmTime',title:'首次告警时间',width:150,align:'center',resizable:false},
				{field:'eventName',title:'关联事件名称',width:350,align:'center',resizable:false,
				    formatter:function(value,rec){
					   if(null != value && value.length>25){
						   var subValue = value.substring(0, 25) + '...';
						   return subValue;
					   }else{
						   return value;
					   }
				    }},
	            {field:'alarmId',title:'<s:text name="field.label.alarmID" />',hidden:true, resizable:false},
	            {field:'alarmSourceType',title:'<s:text name="field.label.alarmID" />',hidden:true, resizable:false}
				]],
	        onRowContextMenu: function(e, rowIndex, rowData){
			    e.preventDefault();
			    //	alert( rowIndex+ '$$$$$$$' + rowData.itemid);
				$('#tt').datagrid('clearSelections');
				$('#tt').datagrid('selectRow',rowIndex);
				if (!$('#rowMenu').length){
				    createRowMenu();
				}
				$('#rowMenu').menu('show', {
				    left:e.pageX,
					top:e.pageY
				});
			},
	/**		onHeaderContextMenu: function(e, field){
				e.preventDefault();
				if (!$('#tmenu').length){
					createColumnMenu();
				}
				$('#tmenu').menu('show', {
					left:e.pageX,
					top:e.pageY
				});
			}, */
			pagination:true
		});

		var p = $('#tt').datagrid('getPager');
		if (p){
		    $(p).pagination({
		    	pageSize: 20,//每页显示的记录条数，默认为10  
		        pageList: [20,40,60],//可以设置每页记录条数的列表  
			    beforePageText:'第',
				afterPageText:'页  共{pages}页',
				displayMsg:'当前显示 {from} - {to}条记录  共{total}条记录',
				onBeforeRefresh:function(){
				    //alert('before refresh');
				}
			});
		}
}
/**
function getWidth(percent){ 
  var gridWidth = $(".iframeWorkarea").width();
  var width = gridWidth*percent;
 // alert('width' + width);
  return width; 
} 
*/

$(window).resize(function() {	
    var grideHeight = $(window.parent.document).height()-192;
    var aa =  $(window.parent.document).height() - $(window).height();
  //  alert(aa);
	if(aa == '140'){
	  grideHeight = $(window.parent.document).height()-192;
	}else{
	  grideHeight = $(window.parent.document).height()-212;
	}
	
	$('#tt').datagrid('resize', {
	    width:'100%',
	    height:grideHeight
	});
});


function createRowMenu(){
    //alert(indexTest + '@@@@' + idTest);
	var rowMenu = $('<div id="rowMenu" style="width:100px;"></div>').appendTo('body');
	<s:if test="#session.authenticater.authIdList.contains('05_01_03_00')">
 	    $('<div/>').html('<s:text name="button.conform" />').appendTo(rowMenu);
 	</s:if>    
 	<s:if test="#session.authenticater.authIdList.contains('05_01_02_00')">
 	    $('<div/>').html('<s:text name="button.clear" />').appendTo(rowMenu);
 	</s:if>
	$('<div class="menu-sep"></div>').appendTo(rowMenu);
	<s:if test="#session.authenticater.authIdList.contains('05_01_01_00')">
	$('<div/>').html('<s:text name="button.detail" />').appendTo(rowMenu);
	</s:if>
	//<s:if test="#session.authenticater.authIdList.contains('05_01_04_00')">
	//$('<div/>').html('<s:text name="button.topology" />').appendTo(rowMenu);
	//</s:if>
	<s:if test="#session.authenticater.authIdList.contains('05_01_05_00')">
	$('<div/>').html('<s:text name="button.smsNotify" />').appendTo(rowMenu);
	</s:if>
	<s:if test="#session.authenticater.authIdList.contains('05_01_06_00')">
	$('<div/>').html('<s:text name="button.mailNotify" />').appendTo(rowMenu);
	</s:if>
	<s:if test="#session.authenticater.authIdList.contains('05_01_07_00')">
	$('<div/>').html('关联设备').appendTo(rowMenu);
	</s:if>
	<s:if test="#session.authenticater.authIdList.contains('05_01_07_00')">
	$('<div/>').html('生成事件').appendTo(rowMenu);
	</s:if>

	
	rowMenu.menu({
	    onClick: function(item){
		    var row = $('#tt').datagrid('getSelected');
			if (item.text=='<s:text name="button.clear" />'){
			   // alert('点击清除' + row.alarmId);
				clearAlarm(row.alarmId,row.alarmStatus);
			} else if (item.text=='<s:text name="button.conform" />'){
				if(row.alarmStatus == '2'){
					 alert('<s:text name="message.status.isconfirm" />');
					   return;
				   }
				confirmAlarm(row.alarmId,row.alarmStatus);
			} else if (item.text=='<s:text name="button.detail" />'){
				//alert('点击详细' + row.alarmId);
				openDetail(row.alarmId);
			} else if (item.text=='<s:text name="button.topology" />'){
				//alert('拓扑' + row.alarmId);
				openTopology(row.alarmId);
			}else if (item.text=='<s:text name="button.mailNotify" />'){
				//alert('mailNotify' + row.alarmId);
				mailNotify(row.alarmId);
			}else if (item.text=='<s:text name="button.smsNotify" />'){
				//alert('smsNotify' + row.alarmId);
				smsNotify(row.alarmId);
			}else if (item.text=='关联设备'){
				//alert('smsNotify' + row.alarmId);
				deviceInfo(row.alarmIP,row.alarmSourceType);
			}
			else if (item.text=='生成事件'){
				//alert('smsNotify' + row.alarmId);
				createEvent(row.alarmId);
			}
		}
	});
}

function createColumnMenu(){
	var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
	var fields =  $('#tt').datagrid('getColumnFields');
	for(var i=1; i<fields.length; i++){ //从1开始，不显示alarmId
	  var opts = $('#tt').datagrid('getColumnOption', fields[i]);
	  var muit = $('<div iconCls="icon-ok"/>');
	  muit.attr('id', fields[i]);
	  muit.html(opts.title).appendTo(tmenu);
	}
	tmenu.menu({
	  onClick: function(item){
	    if (item.iconCls=='icon-ok'){
	      $('#tt').datagrid('hideColumn', item.id);
	      tmenu.menu('setIcon', {
	        target: item.target,
	        iconCls: 'icon-empty'
	      });
	    } else {
	      $('#tt').datagrid('showColumn', item.id);
	      tmenu.menu('setIcon', {
	        target: item.target,
	        iconCls: 'icon-ok'
	      });
	    }
	  }
	});
}

function clearAlarm(AlarmId, alarmStatus){
  if(confirm('是否清除该告警记录')){ 
    $.ajax({   
	    url: "alarmClear.action", 
		type: "post",   
		data : {'domain.alarmID': AlarmId, 'domain.alarmStatus': AlarmId},
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="fail") {//系统异常
				 alert('<s:text name="log.clear.error" />');
			}else{//成功,不提示
				// alert('<s:text name="log.clear.success" />');
				reloadData();	
			}
		},   
        error: function(XMLHttpRequest, textStatus, errorThrown) {   
			 alert('<s:text name="log.clear.error" />'); 
		}   
	});  
  }else{
	    return false;
  }         
}

function confirmAlarm(AlarmId, alarmStatus){
  if(confirm('是否确认该告警记录')){ 
    $.ajax({   
	    url: "alarmConfirm.action", 
		type: "post",   
		data : {'domain.alarmID': AlarmId, 'domain.alarmStatus': AlarmId},
		success:function(data) {//这里的data是由请求页面返回的数据  
		    var json = eval("("+data+")");//转换为json对象
		    //alert(json.result);
			if(json.result=="fail") {//系统异常
			    alert('<s:text name="log.confirm.error" />');
			}else{//成功,不提示
				//alert('<s:text name="log.confirm.success" />');
				reloadData();	
			}
		},   
        error: function(XMLHttpRequest, textStatus, errorThrown) {   
			alert('<s:text name="log.confirm.error" />');
		}   
	});  
  }else{
    return false;
  }   
}
	
function openDetail(AlarmId){
    var random = Math.random();
	//alert(AlarmId + '====' + random);

    $("#detailDialog").attr('title','<s:text name="function.title" />' + '<s:text name="button.detail" />');
    $("#detailDialog").dialog({
	    autoOpen: false,
		height: 450,
		width: 450,
		modal: true,
		bgiframe: true,
		position: "center",
		zIndex:5000,
		resizable:false,
		close: function() {
			//alert('close');
		}
    });
   
    $.ajax({   
        url: "alarmDetail.action", 
        type: "post",   
        data : {'alarmID': AlarmId},
        dataType : "text",
        success:function(data) {//这里的data是由请求页面返回的数据   
           // alert('detail success');
            var begin = data.indexOf("<!-- begin -->");
            var end = data.indexOf("<!-- end -->");
            var src = data.substring(begin, end); 
          //  alert(src);
            $("#detailDialog").html("");
            $("#detailDialog").html(src);  
            //$("#detailDialog").html("你好，这是一个测试的例子");
            $("#detailDialog").dialog("open");
        },   
        error: function(XMLHttpRequest, textStatus, errorThrown) {   
            alert('<s:text name="message.detail.error" />');   
        }   
    });   
    
}

function openTopology(AlarmId){
//	 var url="PreTopologyAction.action?alarmId=" + AlarmId;
//	  window.open(url,'topology','top=10,left=10,height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
 //    window.open(url);
var subUrl = "PreTopologyAction.action";
$('#mainForm').attr('action',subUrl);
$('#mainForm').submit();  
}

function mailNotify(AlarmId){
	var url="beforeMailNotify.action?alarmID=" + AlarmId;
    window.open(url,"mailnotify","top=10,left=10,height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes");	
    //window.open(url);
}

function smsNotify(AlarmId){
	var url="beforeSmsNotify.action?alarmID=" + AlarmId;
	window.open(url,'smsnotify','top=10,left=10,height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
    //window.open(url);
}

function deviceInfo(AlarmIP,alarmSourceType){
	var url="beforeDeviceInfo.action?domain.alarmIP=" + AlarmIP + "&domain.alarmSourceType=" + alarmSourceType;
	window.open(url,'DeviceInfo','top=10,left=10,height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
    //window.open(url);
}

function createEvent(AlarmId){
	var url="alarmEventBeforeAdd.action?alarmId=" + AlarmId ;
	window.open(url,'createEvent','top=10,left=10,height=600,width=800,resizable=yes,location=no, status=yes,scrollbars=yes');	
    //window.open(url);
}

function reloadData(){
   var ids = document.getElementsByName("typeCheck");
    //var ids = $("#typeCheck1");
	var alarmType = "";
 	for (var i = 0; i < ids.length; i++)       
    {    
        if(ids[i].checked) {
           // alert(ids[i].value); 
            alarmType = alarmType + ids[i].value + ',';
    	}        
    }      
	if(alarmType != ""){
		alarmType = alarmType.substring(0, alarmType.length-1);
	}else{
	//	alert('<s:text name="message.alrmtype.noselect" />');
		return;
	}
  //  alert('reload,type:' + alarmType);
    $("#tt").datagrid('reload',{
   	    'typeCheck':alarmType
    });
}

function loadData(){
	   var ids = document.getElementsByName("typeCheck");
	    //var ids = $("#typeCheck1");
		var alarmType = "";
	 	for (var i = 0; i < ids.length; i++)       
	    {    
	        if(ids[i].checked) {
	           // alert(ids[i].value); 
	            alarmType = alarmType + ids[i].value + ',';
	    	}        
	    }      
		if(alarmType != ""){
			alarmType = alarmType.substring(0, alarmType.length-1);
		}else{
		//	alert('<s:text name="message.alrmtype.noselect" />');
		//	return;
		alarmType = " ";
		}
	//    alert('load,type:' + alarmType);
	    $("#tt").datagrid('load',{
	   	    'typeCheck':alarmType
	    });
	}

function checkClick(){
	loadData();
}


</script>

<form action="" method="post" name="mainForm" id="mainForm">
</form>
<!--内容部分 main star-->
<div class="main1">
     
    <!--工作区 workarea start-->
    <div class="iframeWorkarea1">
        <!--选项卡 tab start-->
        <div class="tabBar"> <a href="#" class="tabCurrent" style="text-decoration:none"><s:text name="function.title" /></a></div>

        <!-- 复选框 -->
        <div style="height:5px;"></div>
        <div>
        <div style="float:left; margin-left:50px;"><input name="typeCheck" id="typeCheck1" type="checkbox" value="0"  checked="checked" onclick="checkClick()"/><s:text name="field.label.alarmtype0" /></div>
        <div style="float:left; margin-left:20px;"><input name="typeCheck" id="typeCheck1" type="checkbox" value="1"  checked="checked" onclick="checkClick()"/><s:text name="field.label.alarmtype1" /></div>
        <div style="float:left; margin-left:20px;"><input name="typeCheck" id="typeCheck1" type="checkbox" value="2"  checked="checked" onclick="checkClick()"/><s:text name="field.label.alarmtype2" /></div>
        <div style="float:left; margin-left:20px;"><input name="typeCheck" id="typeCheck1" type="checkbox" value="3"  checked="checked" onclick="checkClick()"/><s:text name="field.label.alarmtype3" /></div>
        <div  style="float:left; margin-left:20px;"><input name="typeCheck" id="typeCheck1" type="checkbox" value="4"  checked="checked" onclick="checkClick()"/><s:text name="field.label.alarmtype4" /></div>
		</div>
		<div style="height:5px;"></div>

        <!-- datagride -->
		<table id="tt"></table>
	   
	    <!-- detail dialog  -->
	    <div id="detailDialog" style="height:0px;"></div>
        
    </div>
     <!--工作区 workarea end-->
</div>
  <!--内容部分 main end-->


