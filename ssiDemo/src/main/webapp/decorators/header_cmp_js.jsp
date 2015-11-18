<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link href="<%=request.getContextPath()%>/themes/default/styles/tab.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/toolbar.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/searchBar.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/list.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/scroll_bar.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/form.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/buttonbar.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/dialog.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/enhanced.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/checkbox.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/wzdBar.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/tips.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/mask.css" rel="stylesheet" type="text/css">
<!-- 当前位置 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/blue/styles/right_side.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/blue/styles/global.css" /> --%>
<link href="<%=request.getContextPath()%>/themes/blue/styles/content.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/reset.css" rel="stylesheet" type="text/css">

<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.2.js"></script>

<!-- add by wengcz 2012-7-26,My97日期控件 -->
<script src="<%=request.getContextPath()%>/javascript/My97DatePicker/WdatePicker.js" ></script>

<!--日期-->
<link href="<%=request.getContextPath()%>/stylesheets/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/default/styles/date.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
<script src="<%=request.getContextPath()%>/javascript/select_date.js"></script>

<script src="<%=request.getContextPath()%>/javascript/jq.layout.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery.jscrollpane.min.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery.mousewheel.js"></script>
<script src="<%=request.getContextPath()%>/javascript/scroll_size.js"></script>
<script src="<%=request.getContextPath()%>/javascript/nav.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tree.js"></script>
<script src="<%=request.getContextPath()%>/javascript/list.js"></script>
<script src="<%=request.getContextPath()%>/javascript/form.js"></script>
<script src="<%=request.getContextPath()%>/javascript/dialog.js"></script>
<script src="<%=request.getContextPath()%>/javascript/ui.checkbox.js"></script>
<script src="<%=request.getContextPath()%>/javascript/wzdBar.js"></script>
<script src="<%=request.getContextPath()%>/javascript/mask.js"></script>
<script src="<%=request.getContextPath()%>/javascript/checkbox.js"></script>
<!-- list页面排序，使用后添加页面出现js错误，date控件不可用 
<script src="<%=request.getContextPath()%>/javascript/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath()%>/javascript/table_sort.js"></script>
-->
<!-- 该js文件在模板中不存在
<script src="<%=request.getContextPath()%>/javascript/jquery.ui.widget.js"></script> -->
<script src="<%=request.getContextPath()%>/javascript/checkbox.js"></script>

<!-- JSTREE -->
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/jquery.jstree.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/_lib/jquery.cookie.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jsTree1.0/_lib/jquery.hotkeys.js"></script>        

<!-- page -->
<link href="<%=request.getContextPath()%>/themes/default/styles/page.css" rel="stylesheet" type="text/css">

<!-- validate -->
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/validate/formValidator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/validate/formValidatorRegex.js"></script>
<link href="<%=request.getContextPath()%>/javascript/validate/style/validator.css" rel="stylesheet" type="text/css" />

<!--  时间控件 时分秒 -->
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-timepicker-addon.js"></script>

<!-- 页面元素生成html -->
<script src="<%=request.getContextPath()%>/javascript/Jquery-Snapshot-V1.0.js"></script>
<!-- ztree -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/javascript/JQuery-zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.core-3.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.excheck-3.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/JQuery-zTree/js/jquery.ztree.exedit-3.0.js"></script>

<script src="<%=request.getContextPath()%>/javascript/json2.js"></script>
<script src="<%=request.getContextPath()%>/javascript/inserttext.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/waiting.js" ></script>
</head>
<body>
	<decorator:body />
</body>
</html>