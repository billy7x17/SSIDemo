<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<title><s:text name='common.head.title'/></title>
<script type="text/javascript" language="JavaScript1.2">   
	if(top != window)   
		top.location.href = window.location.href;   
</script>
</head>
<body>
<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/blue/styles/login.css" />
<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.2.js"></script>
<form method="post" action="./Login.action" name="loginForm" id='loginForm'>
<div class="login-wrapper">
	<div class="login-top">
    	<span class="brand"></span>
    </div>
    <div class="login-body">
    	<div class="lg-bg"></div>
        <div class="lg-input">
        	<input id="userId" name="userId" type="text" class="input-username" title='<s:text name="login.title.username" />'/>
            <input id="password" name="password" type="password" class="input-pwd" title='<s:text name="login.title.password"/>' />
            <button class="lg-btn" onclick="return check();"><s:text name="login.button.login"/></button>
            
            <div id="message">
	        	<ul>
					<li>
						<font color="scarlet">
		       				<s:property value="invalid" />
	       			 	</font>
		        	</li>
				</ul>
	        </div>
	        
        </div>
        
        <div class="lg-line"></div>
    </div> 
</div>
</form>
<script type="text/javascript">
$(document).ready(function(){
	$('#userId').focus();
	
	$('a').focus($(this).blur());
});
function check(){
    var username = document.getElementById("userId");
    var password = document.getElementById("password");
    var msg = document.getElementById("message");
    
    var isOk = true;
    
    if (password.value == ""){ 
        msg.innerHTML="<ul><li><font color='scarlet'><s:text name='login.validate.password'/></font></li></ul>";
        $(password).focus();
        isOk = false;
    } 
    
    if (username.value == ""){ 
        msg.innerHTML="<ul><li><font color='scarlet'><s:text name='login.validate.username'/></font></li></ul>";
        $(username).focus();
        isOk = false;
    } 
    
    if (!isOk) {
		return false;
	}
    
    document.getElementById("loginForm").submit();
} 
</script>
</body>
</html>
