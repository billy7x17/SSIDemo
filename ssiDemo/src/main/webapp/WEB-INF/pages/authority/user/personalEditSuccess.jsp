<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="<s:url value="/source/lib/jquery-1.3.2.js"/>"></script>
<script type="text/javascript" src="<s:url value="/source/dialog/kmDialog/artDialog.js"/>"></script>
<script type="text/javascript" src="<s:url value="/source/dialog/kmDialog/plugs.js"/>"></script>
<script type="text/javascript">
	/*查找父框架中form的id,提交Action*/
	$(function(){
		$.unfunkyUI();
	});
</script>
<div id="tmpMsg" style="display:none"><s:property value="msg"/><s:property value="errorMsg"/><s:fielderror cssClass="error"/></div>