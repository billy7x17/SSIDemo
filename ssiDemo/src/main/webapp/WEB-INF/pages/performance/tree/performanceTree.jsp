<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cloudmaster.cmp.web.authority.auth.*"%>
 <style>
 #leftTree li{float:none};
 #deviceViewTree li{float:none};
 <%-- golbal.css中的内容如下 ：缺少一个overflow:hidden--%>
div{ overflow:visible; height:auto;}
 </style>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/JQuery-zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/themes/default/styles/nodeCss.css"
    type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/Flexigrid/demo/style.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/javascript/Flexigrid/css/flexigrid.css"
	type="text/css">
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.core-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.excheck-3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.exedit-3.0.js"></script>
<script type="text/javascript" language="JavaScript1.2">

$(document).ready(function(){
    $("#dtree_a").click();
});

//业务视图tree
function showDeviceViewTree(){
	$("#deviceViewTree").jstree({
		"xml_data" : {
            "ajax" : {
                "url" : "serviceTreeData.action"
            },
            "xsl" : "flat"
         },
         "plugins" : [ "themes", "xml_data","ui"]
	})
    .bind("select_node.jstree", function (event, data){
        //点击，展开节点  
    	$('#deviceViewTree').jstree("toggle_node", "#"+data.rslt.obj.attr("id"));
    	
    	var id = data.rslt.obj.attr("id");
    	var typeId = data.rslt.obj.attr("typeId");
    	var deviceIp = data.rslt.obj.attr("deviceIp");
    	var resName = data.rslt.obj.attr("resName");
    	var state = data.rslt.obj.attr("status");
    	var parentId = data.rslt.obj.parent().parent().attr("id");
    	
    	var url = "";
    	if(state!="1"){
    		if(typeId=='43'||typeId=='44'||typeId=='45' || typeId=='48'){//D4和VMS
        		url = encodeURI("decoderResource.action?deviceIp="+deviceIp+"&deviceType=D4&typeId="+typeId+"&resourceName="+resName);
        	}else if(typeId=='33'||typeId=='42' ||typeId=='41'){//IPSAN 和 NVR(HUS-NVR、HUS-XACT和H3C)
        		url = encodeURI("searchIPSANResource.action?deviceIp="+deviceIp+"&deviceType=IPSAN&typeId="+typeId+"&resourceName="+resName);
        	}else if(typeId=='36'||typeId=='37'){//部分IPC 
        		url = encodeURI("ipcResource.action?deviceIp="+deviceIp+"&deviceType=IPC&typeId="+typeId+"&resourceName="+resName);
        	}else if(typeId=='32' ||typeId=='35'||typeId=='46'||typeId=='31' ||typeId=='34'){
        		url =encodeURI("encoderResouce.action?deviceIp="+deviceIp+"&deviceType=Encoder&typeId="+typeId+"&resourceName="+resName);
        	}
              $("#mainIFrame").attr("src",url);
    	}else{
    		data.rslt.obj.children("a").removeClass();
    	}
     })
    .bind("loaded.jstree", function (e, data){
          $("#deviceViewTree").css("background-color","#f4f6f8");
           $("#deviceViewTree").find("a").find("ins").each(function(){
        	  	if(typeof($(this).attr("style"))=="undefined"){
        	  		$(this).css("margin-left","-18px");
        	  	}
          });
        //默认展开第一设备节点.
        $("#deviceViewTree ul li ul li ").each(function(){
        	var obj = $(this).find("ul li:eq(0)[status!='1'] a");
        	if(obj.html()!=null){
        		obj.click();
        		 return false;
        	}
        });
     });
}
 </script>
 
 <!--左边内容 left_side start-->
 <div class="left_side" >
     <!--手风琴 leftAcd start-->
     <div class="leftAcd" >
		<iamp-identify:IAMPIdentify authid="04_01_00_00">
         	<div id="deviceView" class="leftAcdTit" onclick="showDeviceViewTree();" >
         		<a id="dtree_a" href="#"><s:text name="performance.operation.view"/> </a>
         		<span id="deviceViewSpan" class="leftAcdArr"></span>
         	</div> 
	            <!--树 leftTre start-->
	           <div class="leftTre">
	            	<div class="scroll-pane">
	            		<!--新增查询按钮开始-->
						
						<!--新增查询按钮结束-->
	            		<div id="deviceViewTree">
	            			<!-- <ul id="dtree" class="ztree"></ul> -->
	            		</div>
	            	</div>
	        	</div> 
	    </iamp-identify:IAMPIdentify>
	    
	    <!-- 采集指标配置管理 start -->
	   <iamp-identify:IAMPIdentify authid="04_12_00_00">
                <div class="leftAcdTit" id="mibDiv" onclick="mibList();return false;">
                <a href="#"><s:text name="performance.mibinfo.managament"/> </a>
                <span id="mibSpan" class="leftAcdArr"></span></div>
                <span class="leftTre"></span>
       </iamp-identify:IAMPIdentify>
       <!-- 采集指标配置管理 end -->
     </div>
     <!--手风琴 leftAcd end-->
 </div>

 <!--左边内容 left_side end-->
 <div id="mainTest" >
     <iframe id="mainIFrame" src="" frameborder="0" class="workarea" >
     </iframe>
 </div>

<script>
function mibList(){
    var url = "<%=request.getContextPath()%>/mibList.action";
    $("#mainIFrame").attr("src",url);
}
</script>


