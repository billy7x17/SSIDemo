<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/stylesheets/left_side.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/content.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/reset.css" rel="stylesheet" type="text/css">

<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.2.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jq.layout.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tree.js"></script>

<%-- JSTREE 改为ztree后请清除--%>
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/jquery.jstree.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/_lib/jquery.cookie.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/_lib/jquery.hotkeys.js"></script>

</head>
<body>
	<decorator:body />
</body>
</html>