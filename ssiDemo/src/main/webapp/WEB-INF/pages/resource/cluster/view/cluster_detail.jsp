<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<div class="BasicInformation">
	<h2><s:property value="cabinet.clusterName" /></h2>
	<span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
</div>
<div class="baseTitl click" style="width: 100%;cursor: default;text-decoration: none;"><s:text name="common.title.newDetil" /></div>
<div class="baseTab scroll-pane">
	<table width="250" border="0" cellspacing="0" cellpadding="0"> 
		<tr>
           <td><s:text name="resource.cluster.clusterName"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.clusterName"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.zoneName"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.zoneName"/>"/></th>
        </tr>
         <tr>
           <td><s:text name="resource.cluster.siteName"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.siteName"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.brand"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.brand"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.manufacturer"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.manufacturer"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.length"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.length"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.width"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly"  value="<s:property value="cabinet.width"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.height"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.height"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.unum"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.Unum"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.power"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.power"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.size"/><s:text name="common.lable.point"/></td>
           <th><input type="text" readonly="readonly" value="<s:property value="cabinet.size"/>"/></th>
        </tr>
        <tr>
           <td><s:text name="resource.cluster.description"/><s:text name="common.lable.point"/></td>
           <th> <textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="cabinet.description"/></textarea></th>
        </tr>
	</table>
</div>

