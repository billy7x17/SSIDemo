 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<script type="text/JavaScript">
<!--
$(function(){
	 //设置上方导航菜单选中状态
   // var menu = $(".menu [rel]");
   // var rel = menu.attr("rel");
   // $(menu).attr("class",rel).removeAttr("rel");
   // $("#alarm").attr("class","alarm-focus").attr("rel","alarm");
    <s:if test="specifiedAction!=null">
    	$('#<s:property value="specifiedAction"/>').click();
    </s:if>
    <s:elseif test="#session.authenticater.authIdList.contains('05_01_00_00')">
         $('#alarmViewDiv').click();
    </s:elseif>
    <s:elseif test="#session.authenticater.authIdList.contains('05_05_00_00')">
        $('#alarmHistoryDiv').click();
    </s:elseif>
    <s:elseif test="#session.authenticater.authIdList.contains('05_10_00_00')">
　　　　$('#alarmConfigLeftAcdTit').click();
　　</s:elseif>
　　<s:elseif test="#session.authenticater.authIdList.contains('05_11_00_00')">
　　　　$('#historyPerformenceLeftAcdTit').click();
　　</s:elseif>
	<s:elseif test="#session.authenticater.authIdList.contains('05_12_00_00')">
		$('#breakPMDiv').click();
	</s:elseif>
    <s:else>
    </s:else> 

    /**
    <s:if test="#session.authenticater.authIdList.contains('05_10_00_00')">
　　　　$('#alarmConfigLeftAcdTit').click();
　　</s:if>
　　<s:elseif test="#session.authenticater.authIdList.contains('05_11_00_00')">
　　　　$('#historyPerformenceLeftAcdTit').click();
　　</s:elseif>
    <s:elseif test="#session.authenticater.authIdList.contains('05_01_00_00')">
        $('#alarmViewDiv').click();
    </s:elseif>
    <s:elseif test="#session.authenticater.authIdList.contains('05_05_00_00')">
        $('#alarmHistoryDiv').click();
    </s:elseif>
    <s:else>
    </s:else> 
    **/
    
});

function alarmConfig(){

	$("#alarmConfigTree").jstree({
		 "xml_data" : {  
		           "data" : "" + "<s:property value='alarmConfTree' escape='false' />"
		           // "data" : "" + "<root><item id='node_1' action='url1'><content><name>Root node 1</name></content></item><item id='node_2' action='url2'><content><name>Root node 2</name></content></item></root>" 
		         },  
	     "ui" : {
	         //默认选中虚拟机模板
	         "initially_select" : [ '<s:property value="alrmConfInitId" escape="false" />' ]
	     },
	     "plugins" : [ "themes", "xml_data", "ui" ],
	     "themes":{
	    	 theme:"classic",
	    	 url:false,
	    	 dots:true,
	    	 icons:true
	     }
	})
	
	$("#alarmConfigTree").bind("select_node.jstree", function (e, data){
		var id = data.rslt.obj.attr("id");
		var url = data.rslt.obj.attr("action");
		var data = $(data.rslt.obj);
		var $parentUL = $("ul",data);
		var $content = $("li",$parentUL);
		if($content.html() == null ){
	    	if(id!=''){
	            var url = "<%=request.getContextPath()%>/" + url;
	            $("#framezc").attr("src",url);
	    	}
	    }
	 });
	$("#alarmConfigTree").bind("loaded.jstree", function (e, data){
	    $("#alarmConfigTree").css("background-color","#f4f6f8");
	   
	    var url = "<%=request.getContextPath()%>/" + '<s:property value="alarmConfInitUrl" escape="false" />';
	    $("#framezc").attr("src",url);
	 });

}



function alarmView(){
	// var url = "<%=request.getContextPath()%>/alarmView.action";
  //  $("#framezc").attr("src",url);

  	$("#alarmViewTree").jstree({ "xml_data" : {
  			    "ajax" : {
  			    	 "url" : "alarmViewTree.action"
  			    }
  		 },
  		 "ui" : {
  			     //默认选中虚拟机模板
  			     "initially_select" : [ 'CIDC-RT-VM' ]
  			 },
  			 "plugins" : [ "themes", "xml_data","checkbox", "ui" ],
  			 "themes" :	{ 
  				theme : "classic", 
  				url : false,
  				dots : true,
  				icons : true
  			}
  		})
  		.bind("check_node.jstree", function (e, data){
  			 var grade = "('";
 	  		var type = "('";
  	  	    var checked = $("#alarmViewTree").jstree("get_checked",null,true);
  	        $(checked).each(function (i,node) {
  	            var id = $(node).attr("id");
  	            if(id.indexOf('TYPE-')==0){
	            	type = type + id.replace('TYPE-','') + "','";  
	            }else if(id.indexOf('GRADE-')==0){
	            	grade = grade + id.replace('GRADE-','') + "','";   
	            }
  	        });
  	        grade = grade + "')";
	        type = type + "')";
  	        var url = "<%=request.getContextPath()%>/alarmView.action?domain.alarmGrade=" + grade + "&domain.deviceType=" + type;
  	       // alert(url);
		    $("#framezc").attr("src",url);	
  	  	})
  	  	.bind("uncheck_node.jstree", function (e, data){
  	  	    var grade = "('";
	  		var type = "('";
  	  	    var checked = $("#alarmViewTree").jstree("get_checked",null,true);
  	        $(checked).each(function (i,node) {
  	            var id = $(node).attr("id");
  	            if(id.indexOf('TYPE-')==0){
	            	type = type + id.replace('TYPE-','') + "','";  
	            }else if(id.indexOf('GRADE-')==0){
	            	grade = grade + id.replace('GRADE-','') + "','";   
	            }
  	        });
  	        grade = grade + "')";
  	        type = type + "')";
  	        var url = "<%=request.getContextPath()%>/alarmView.action?domain.alarmGrade=" + grade + "&domain.deviceType=" + type ;
  	      //  alert(url);
		    $("#framezc").attr("src",url);
  	  	})
  		.bind("loaded.jstree", function (e, data){
  			 var grade = "('";
             var type = "('";
             var checked = $("#alarmViewTree").jstree("get_checked",null,true);
             $(checked).each(function (i,node) {
                 var id = $(node).attr("id");
                 if(id.indexOf('TYPE-')==0){
                     type = type + id.replace('TYPE-','') + "','";  
                 }else if(id.indexOf('GRADE-')==0){
                     grade = grade + id.replace('GRADE-','') + "','";   
                 }
             });
             grade = grade + "')";
             type = type + "')";
  			$("#alarmViewTree").css("background-color","#f4f6f8");
  			
  			 $("#alarmViewTree").find("a").find("ins[class!='jstree-checkbox']").each(function(){
  		    	if(typeof($(this).attr("style"))=="undefined"){
  		    		$(this).remove();
  		    	}
  		    });	 
  			
  			//var type = "(1,2,4,5,6,7,8,9)";
	  		//var grade = "(2,3,4,5)";
  			 var url = "<%=request.getContextPath()%>/alarmView.action?domain.alarmGrade=" + grade + "&domain.deviceType=" + type;
  			 $("#framezc").attr("src",url);
  		});
    
}

function alarmHistoryList(){
	 var url = "<%=request.getContextPath()%>/alarmHistoryListBase.action";
	 
 	$("#framezc").attr("src",url);
}
function breakPMList(){
	 var url = '<%=request.getContextPath()%>/PMServiceList.action?operationType=break'; 
	$("#framezc").attr("src",url);
}

//南基
function alarmTopology(){
	 var url = "<%=request.getContextPath()%>/PreTopologyAction1.action";
 	$("#framezc").attr("src",url);
}

//业务拓扑图
function serviceTopology(){
	 var url = "<%=request.getContextPath()%>/ServiceTopologyAction.action";
	$("#framezc").attr("src",url);
}
//公有云(为了2级菜单树)
function alarmTopologyPC(){
	$("#topologyTree").jstree({
		 "xml_data" : {  
		           "data" : "" + "<s:property value='topologyTree' escape='false' />"
		           // "data" : "" + "<root><item id='node_1' action='url1'><content><name>Root node 1</name></content></item><item id='node_2' action='url2'><content><name>Root node 2</name></content></item></root>" 
		         },  
	     "ui" : {
	         //默认选中虚拟机模板
	         "initially_select" : [ '<s:property value="topologyTreeInitId" escape="false" />' ]
	     },
	     "plugins" : [ "themes", "xml_data", "ui" ]
	})
	
	$("#topologyTree").bind("select_node.jstree", function (e, data){
		var id = data.rslt.obj.attr("id");
		var url = data.rslt.obj.attr("action");
		var data = $(data.rslt.obj);
		var $parentUL = $("ul",data);
		var $content = $("li",$parentUL);
		if($content.html() == null ){
	    	if(id!=''){
	            var url = "<%=request.getContextPath()%>/" + url;
	            $("#framezc").attr("src",url);
	    	}
	    }
	 });
	$("#topologyTree").bind("loaded.jstree", function (e, data){
	    $("#topologyTree").css("background-color","#f4f6f8");
	    var url = "<%=request.getContextPath()%>/" + '<s:property value="topologyTreeInitUrl" escape="false" />';
	    $("#framezc").attr("src",url);
	 });
}

function historyPerformence(){
    $("#historyPerformenceTree").jstree({
    	"xml_data" : {
    	    "data" : "" + "<s:property value='historyPerformTree' escape='false' />"  
          //  "data" : "" + "<root><item id='node_1' action='url1'><content><name>Root node 1</name></content></item><item id='node_2' action='url2'><content><name>Root node 2</name></content></item></root>" 
         },  
         "ui" : {
	         //默认选中虚拟机模板
	         "initially_select" : [ '<s:property value="historyPerformInitId" escape="false" />' ]
	     },
         "plugins" : [ "themes", "xml_data", "ui" ]
    })
    .bind("select_node.jstree", function (e, data){
    	var id = data.rslt.obj.attr("id");
    	var url = data.rslt.obj.attr("action");
    	var data = $(data.rslt.obj);
    	var $parentUL = $("ul",data);
    	var $content = $("li",$parentUL);
    	if($content.html() == null ){
        	if(id!=''){
        		var url = "<%=request.getContextPath()%>/" + url;
            //	alert('url:' + url);
                $("#framezc").attr("src",url);
        	}
        }
     })
    .bind("loaded.jstree", function (e, data){
        $("#historyPerformenceTree").css("background-color","#f4f6f8");
        var url = "<%=request.getContextPath()%>/" + '<s:property value="historyPerformInitUrl" escape="false" />';
        $("#framezc").attr("src",url);
     });
}

//-->
</script>
 <!--左边内容 left_side start-->
<div class="left_side">
    <!--手风琴 leftAcd start-->
    <div class="leftAcd">
        <iamp-identify:IAMPIdentify authid="05_01_00_00"> 
	       	<div class="leftAcdTit" id="alarmViewDiv" onclick="alarmView();"><a href="#"  onclick="alarmView();return false;" >告警实时监控</a><span  class="icon-right fn-right"></span></div> 
		  <%--  <span class="leftTre"></span>  --%> 
		    <div class="leftTre">
              <div class="scroll-pane">
            		<div id="alarmViewTree"></div>
            		<div class="h_20"></div>
              </div>
            </div>
        </iamp-identify:IAMPIdentify>        
			
        <iamp-identify:IAMPIdentify authid="05_05_00_00"> 
	       	<div class="leftAcdTit" id="alarmHistoryDiv" onclick="alarmHistoryList();"><a href="#"  onclick="alarmHistoryList();return false;" >告警历史浏览</a><span id="autodetectPMSpan"  class="icon-right fn-right"></span></div> 
	        <span class="leftTre"></span>
        </iamp-identify:IAMPIdentify>        
   			 
    	<iamp-identify:IAMPIdentify authid="05_10_00_00">
	        <div id="alarmConfigLeftAcdTit" class="leftAcdTit" onclick="alarmConfig();return false;"><a href="#" onclick="alarmConfig();return false;">告警配置</a><span class="icon-right fn-right"></span></div> 
            <div class="leftTre">
              <div class="scroll-pane">
            		<div id="alarmConfigTree"></div>
            		<div class="h_20"></div>
              </div>
            </div>
        </iamp-identify:IAMPIdentify>
        
        <iamp-identify:IAMPIdentify authid="05_11_00_00">
        <div id="historyPerformenceLeftAcdTit" class="leftAcdTit" onclick="historyPerformence();return false;"><a href="#" onclick="historyPerformence();return false;">历史性能管理</a><span class="icon-right fn-right"></span></div> 
	            <!--树 leftTre start-->
	             <div class="leftTre">
	              <div class="scroll-pane">
	            		<div id="historyPerformenceTree"></div>
	            		<div class="h_20"></div>
	              </div>
	            </div>
        </iamp-identify:IAMPIdentify>

		<s:if test="#session.authenticater.authIdList.contains('05_15_00_00')">
	       	<div class="leftAcdTit" id="alarmTopologyPCDiv" onclick="alarmTopologyPC();return false;"><a href="#"  onclick="alarmTopologyPC();return false;" >网络拓扑</a><span class="icon-right fn-right"></span></div> 
	        	<div class="leftTre">
	              <div class="scroll-pane">
	            		<div id="topologyTree"></div>
            			<div class="h_20"></div>
	              </div>
	            </div>
		</s:if>

    </div>
</div>
<!--左边内容 left_side end-->
<div id="mainTest">
	<iframe id="framezc" frameborder="0" class="workarea">
	</iframe>
</div>
