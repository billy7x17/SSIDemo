<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
[
<s:iterator value="OIDList">
{"typeID":"<s:property value="typeID" />",
"alarmIdentify":"<s:property value="alarmIdentify" />",
"alarmIdentifyName":"<s:property value="alarmIdentifyName" />"},
</s:iterator>
]
   