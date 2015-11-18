<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<script type="text/JavaScript">
$(function(){
    <s:if test="#session.authenticater.authIdList.contains('06_04_00_00')">
    $('#cronJobAcdTit').click();
    </s:if>
});

function cronExpressionList(){
	var url = "<%=request.getContextPath()%>/cronExpressionList.action";
	$("#framezc").attr("src",url);
}
//-->
</script>
<style type="text/css">
.jstree LI {clear:both;}
</style>
<!--左边内容 left_side start-->
<div class="left_side">
    <!--手风琴 leftAcd start-->
    <div class="leftAcd">
        <iamp-identify:IAMPIdentify authid="06_04_00_00">
            <div class="leftAcdTit" id="cronJobAcdTit" onclick="cronExpressionList();return false;"><a href="#">任务管理</a><span id="cronJobSpan"  class="leftAcdArr"></span></div>
	        <span class="leftTre"></span>
        </iamp-identify:IAMPIdentify>
    <!--手风琴 leftAcd end-->
    </div>
</div>
<!--左边内容 left_side end-->
<div id="mainTest">
	<iframe id="framezc" frameborder="0" class="workarea">
	</iframe>
</div>
<!--左边内容 left_side end-->