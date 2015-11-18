<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name='common.head.title' /></title>
<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.2.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jq.layout.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tab-core.js"></script>
<script src="<%=request.getContextPath()%>/javascript/tab.js"></script>
<link
	href="<%=request.getContextPath()%>/themes/blue/styles/blue-pc.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/themes/blue/styles/tab.css"
	rel="stylesheet" type="text/css">
<body>
	<div class="wrapper">
		<div class="info-bar">
			<div class="user">
				<ul class="fn-right">
					<li><s:if test="#session.userInfo.roleType == 2">
							<a href="#"
								onclick="javascript:location.href='<%=request.getContextPath()%>/excel/help_site.doc'"><s:text
									name="common.head.help" /></a></li>
					</s:if>
					<s:else>
						<a href="#"
							onclick="javascript:location.href='<%=request.getContextPath()%>/excel/help.doc'"><s:text
								name="common.head.help" /></a>
						</li>
					</s:else>
					<li><a href="#" style="cursor: default;"> <s:property
								value="#session.userInfo.userName" />
					</a></li>
					<li><a href="#" onclick="window.location = 'logout.action'">
							<s:text name="common.head.logout" />
					</a></li>
				</ul>
			</div>
		</div>
		<%--头部内容 header start--%>
		<div class="header">
			<div class="logo">
				<img
					src="<%=request.getContextPath()%>/themes/default/images/logo-pc.png"
					complete="complete" />
			</div>
			<%@ include file="../../decorators/menu-pc.jsp"%>
		</div>
	</div>
	<div id="page"></div>
	<%--头部内容 header end--%>
	<div class="footer">
		<span><s:text name="common.bottom.copy" /></span>
	</div>
</body>
</html>