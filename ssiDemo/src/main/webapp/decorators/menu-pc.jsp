<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/javascript/notify/default/css/mk.icons.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/javascript/notify/default/css/mk.notify.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/notify/mk.notify.min.js"></script>
  
<script type="text/JavaScript">

$(function() {
   // setInterval("open_notify();", 30*1000); 
});

//右下角告警提醒
/* function open_notify(type) {
    $.ajax({
        type: "POST",
        url: "getalarmNotify.action",
        data : {'domain.queryType': 'allPage'},
        dataType:"json",
        success: function(c){
            var msg = '';
            var type = c.notifyType; //1-无新告警，2-出现新告警，3-出现新的重复告警
            var time = c.notifyTime;
            var count = c.notifyCount;
            if(type ==2){msg = '新告警';}
            if(type ==3){msg = '重复告警';}
            if(type != 1){
                var content = "系统接收到" + msg + "，最新告警时间：" + time + "，最新记录条数：" + count;
                notify = $.mk_notify({
                    title: "系统接收到" + msg,
                    type: 'message',
                    delay: 60000,
                    body: content
                 });
            }
       }
   });
} */


var date=new Date();
date.setTime(date.getTime()-1000*10);//10秒前
var loadingTab = "";
//控制点击菜单的时间间隔
function clickMenu(tabtitle,tabid,taburl,obj){
    //已经打开tab
    if ( $("#page_" + tabid + "_tab").length > 0 ) { 
        return false;   
    }else{
    	addTab(tabtitle,tabid,taburl);
        closeOtherIframe("page_"+tabid+"_tab");
    }

    /*去掉其他的已选标签样式*/
	$(".menu-pc li a").each(function(){
		$(this).css("background","#3B3B3B");
		$(this).css("color","#D8D8D8");
	});
    
    /*将此标签选用已选标签样式*/
    obj.style.background = "#D8D8D8";
    obj.style.color = "#3B3B3B";
    
    //第一次打开tab,判断不能连续点击
    /* if(loadingTab=="" || typeof(loadingTab)=="undefined"){
        loadingTab=tabtitle;
    }
	
    var currenDate = new Date();
    var dateDiff=currenDate.getTime()-date.getTime(); 
    if(dateDiff<5000){
        alert("正在获取 " + loadingTab +" 数据，请稍后访问其他功能!");
        return false;
    }else{
        date = currenDate;
        loadingTab = tabtitle;
        
    } */

/** 
    var iframe = document.getElementById("page_" + tabid + "_tab");
    if (iframe.attachEvent){
        iframe.attachEvent("onload", function(){
            loaded = "true";
          //var iframe = document.getElementById("framezc");
        });
    } else {
        iframe.onload = function(){
            loaded = "true";
        };
    }
**/

}
//关闭其他ifram
 function closeOtherIframe(iframeId){
	var id= $("#"+iframeId).siblings().attr("id");
	$("#"+id).remove();
	$("#"+id.substring(4,id.length)).remove();
	
} 
</script>


      <ul class="menu-pc">
      	
      	
			<!-- 首页 -->
		 <li><a href="javascript:void(0);" style="background:#D8D8D8;color: #3B3B3B;" onclick="clickMenu('<s:text name="common.home.tab.name"/>','firstPage','FirstPage.action',this);return false;"><s:text name="common.home.tab.name"/></a> </li>      
         <iamp-identify:IAMPIdentify authid="02_00_00_00">
        	<li><a href="javascript:void(0);" onclick="clickMenu('<s:text name="common.resource.tab.name"/>','resource','unifyinitleft.action',this); return false;" ><s:text name="common.resource.tab.name"/></a></li>
         </iamp-identify:IAMPIdentify>
      	 <iamp-identify:IAMPIdentify authid="04_00_00_00">
         	<li><a href="javascript:void(0);" onclick="clickMenu('<s:text name="common.performance.tab.name"/>','performance','performanceList.action',this); return false;"><s:text name="common.performance.tab.name"/></a></li>
         </iamp-identify:IAMPIdentify>
      	 <iamp-identify:IAMPIdentify authid="05_00_00_00">
        	<li><a href="javascript:void(0);" onclick="clickMenu('<s:text name="common.alarm.tab.name"/>','alarm','alarmManager.action',this); return false;"><s:text name="common.alarm.tab.name"/></a></li>
         </iamp-identify:IAMPIdentify>
      	 <iamp-identify:IAMPIdentify authid="08_00_00_00">
       		<li><a href="javascript:void(0);" onclick="clickMenu('<s:text name="common.system.tab.name"/>','role','frame.action?',this); return false;"><s:text name="common.system.tab.name"/></a></li>
         </iamp-identify:IAMPIdentify>
      </ul>