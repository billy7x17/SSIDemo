<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    Thread.sleep(1 * 1000);
    System.out.println("MVC:" +action);
    if("construction".equals(action)){
%>
    <jsp:forward page="./CanvasMVC.jsp" /> 
<%  
    }else if("config".equals(action)){
%>
    <jsp:forward page="./config.jsp" /> 
<%
    }else{
%>
    <jsp:forward page="./IncrementMVC.jsp" /> 
<%    
    }
%>

