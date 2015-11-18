<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XMLSpy v2009 (http://www.altova.com) by dreams (LG) -->
<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = request.getParameter("name");
	System.out.println("name: " + name);
    if(name.startsWith("rootLayer")){
    	pageContext.forward("./topCanvas"+name.substring(9)+".jsp");
    }else{
    	pageContext.forward("./subCanvas"+name+".jsp");
    }
%>

