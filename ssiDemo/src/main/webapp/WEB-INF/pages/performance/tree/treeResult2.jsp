 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags"%>
 [
 <s:iterator value="list">
 {
     "data":{
         "title" : "<s:text name='leafName' />",
         "attr" : {
	         "id" : "<s:text name='localTableIdRef' />",
	         "pid" : "<s:text name='zoneClusterId' />",
	         "type" : "<s:text name='resourceType' />",
	         "ip" : "<s:text name='leafName' />",
	         "status":"<s:text name='status'/>",
         },
         "icon" : "././././themes/default/images/ico_file.png"
     },
     "attr":{
         "id" : "<s:text name='zciId' />",
         "pid" : "<s:text name='zoneClusterId' />"
     }
 },
 </s:iterator>
 {}
 ]