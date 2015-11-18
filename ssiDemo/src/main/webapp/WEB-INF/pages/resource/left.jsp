<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/JavaScript">
    $(function(){
    	var src = "<s:property value='#parameters.actionName'/>" + ".action";
        if(src==".action"){
            <s:if test="#session.authenticater.authIdList.contains('02_08_00_00')">
                $("#deviceDiv").click();
            </s:if>
         } else {
         	if(src=='deviceList.action'){
            	<s:if test="#session.authenticater.authIdList.contains('02_08_00_00')">
                    $("#deviceDiv").click();
                </s:if>
             }
         }
    });
    
    //采集设备管理
    function deviceList(){
        var url = "<%=request.getContextPath()%>/deviceList.action";
       $("#framezc").attr("src",url);
   }
   //机房管理
   function zoneList(){
	   var url = "<%=request.getContextPath()%>/zoneResourceInit.action";
	   $("#framezc").attr("src",url);
   }
   //机柜管理
   function clusterList(){
	   var url = "<%=request.getContextPath()%>/clusterResourceInit.action";
	   $("#framezc").attr("src",url);
   }
   //站点管理
   function siteList(){
	   var url = "<%=request.getContextPath()%>/siteList.action";
	   $("#framezc").attr("src",url);
   }
</script>
<!--左边内容 left_side start-->
<div class="left_side">
	<!--手风琴 leftAcd start-->
	<div class="leftAcd" style="background-color: #f4f6f8;">
		<s:if test="#session.authenticater.authIdList.contains('02_08_00_00')">
			<div class="leftAcdTit" id="deviceDiv"
				onclick="deviceList();return false;">
				<a href="#"><s:text name="resource.collectDevice.title"/> </a> <span id="deviceSpan" class="leftAcdArr"></span>
			</div>
			<span class="leftTre"></span>
		</s:if>
		<s:if test="#session.authenticater.authIdList.contains('02_09_00_00')">
			<div class="leftAcdTit" id="siteDiv" onclick="siteList();return false;">
				<a href="#"><s:text name="resource.site.title"/> </a>
				<span id="siteSpan" class="leftAcdArr"></span>
			</div>
		</s:if>
		<!-- 机房管理-->
		<s:if test="#session.authenticater.authIdList.contains('02_10_00_00')">
			<div class="leftAcdTit" id="zoneDiv" onclick="zoneList();return false;">
				<a href="#"><s:text name="resource.zone.title"/></a> <span id="zoneSpan" class="leftAcdArr"></span>
			</div>
			<span class="leftTre"></span>
		</s:if>
		
		<!-- 机柜管理 -->
		<s:if test="#session.authenticater.authIdList.contains('02_11_00_00')">
			<div class="leftAcdTit" id="clusterDiv" onClick="clusterList();return false;">
				<a href="#"><s:text name="resource.cluster.title"/></a><span id="clusterSpan" class="leftAcdArr"></span>
			</div>
			<span class="leftTre"></span>
		</s:if>
	</div>
	<!--手风琴 leftAcd end-->
</div>
<div id="mainTest">
	<iframe id="framezc" src="" frameborder="0" class="workarea">
	</iframe>
</div>
<!--左边内容 left_side end-->