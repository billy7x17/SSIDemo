<%@page contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ERROR</title>
<style>
#errorPage{
	position: absolute;
	margin-left:300px;
	top: 260px;
	color: #158AE4;
}
#errorPage a{
	font-size: 14px;
	color: #158AE4;
	text-decoration: none;
}
#errorPage a:HOVER{
	text-decoration: underline;
	cursor: pointer;
}
</style>
</head>

<body>

<div id="main">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top">
		  <div>
			<div id="list">
			     <div id="title"><img src="<%=basePath%>/themes/blue/images/noPermission.gif" title="error"></img></div>
			     <div id="errorPage">
			     <%if(session != null){%>
			     	<<a href="<%=basePath%>main.shtml">&nbsp;返回首页</a>&nbsp;&nbsp;
			     <% }else{%>
			     	<<a href="<%=basePath%>">&nbsp;返回首页</a>&nbsp;&nbsp;
			     <%} %>
			     	<<a onclick="history.back()">&nbsp;返回上一页</a>
			     </div>
		    </div>
		  </div>		
		</td>
      </tr>
	</table>
</div>

</body>
</html>

