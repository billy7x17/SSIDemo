 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags"%>
 [
 <s:iterator value="list">
 {
     "data" : {
         "title" : "<s:text name='zoneClusterName' />",
         "attr":{
             "id" : "<s:text name='zoneClusterId' />"
         },
         <s:if test="parentId==0">
         "icon" : "././././themes/default/images/ico_computer.png"
         </s:if><s:else>
         "icon" : "././././themes/default/images/ico_folder.png"
         </s:else>
     },
     "attr":{
         "id" : "<s:text name='zoneClusterId' />",
         "pid" : "<s:text name='parentId' />"
     },
     "state" : "closed"
 },
 </s:iterator>
 {}
 ]