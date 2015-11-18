<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
</script>
	<div class="BasicInformation">
         <h2><s:property value="mibinfoDomain.mibName"/></h2>
       	 <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
   	</div>
   	 
      <div class="baseTitl click" style="width: 100%;cursor:default;text-decoration: none;"><s:text name="common.lable.baseInfo"/> </div>
      <div class="baseTab scroll-pane">
		<table width="250" border="0" cellspacing="0" cellpadding="0">
			<tr>
	            <td><s:text name="mib.mibName"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:property value="mibinfoDomain.mibName"/>"/>  </th>
            </tr>
            <tr>
	            <td><s:text name="mib.mibDeviceName"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:text name="mibinfoDomain.typeName"/>"/></th>
            </tr>
          	<tr>
	            <td><s:text name="mib.oid"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:text name="mibinfoDomain.oid"/>"/></th>
            </tr>
             
            <tr >
	            <td><s:text name="mib.indexUnit"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:text name="mibinfoDomain.indexUnit"/>"/></th>
	            </tr>
            <tr >
	            <td><s:text name="mib.indexGroup"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:text name="mibinfoDomain.indexGroup"/>"/></th>
	            </tr>
	      <s:if test="mibinfoDomain.typeId=='32' || mibinfoDomain.typeId=='35' || mibinfoDomain.typeId=='36' ||mibinfoDomain.typeId=='37' ||mibinfoDomain.typeId=='43' ||mibinfoDomain.typeId=='44' ||mibinfoDomain.typeId=='45' ||mibinfoDomain.typeId=='46' ||mibinfoDomain.type=='47'">
	     
            <tr >
	            <td><s:text name="mib.ifShowLine"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:text name="mibinfoDomain.ifShowLine"/>"/></th>
            </tr>
          </s:if>
          
           <s:if test="mibinfoDomain.typeId=='31' || mibinfoDomain.typeId=='32' || mibinfoDomain.typeId=='33' ||mibinfoDomain.typeId=='34' ||mibinfoDomain.typeId=='35' ||mibinfoDomain.typeId=='42'">
            <tr>
	            <td><s:text name="mib.parent.point"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:property value="mibinfoDomain.parentName"/>"/></th>
            </tr>
             <tr>
	            <td><s:text name="mib.is.collection1"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:property value="mibinfoDomain.isCollectionName"/>"/></th>
            </tr>
            </s:if>
            <tr>
	            <td><s:text name="mib.createTime"/><s:text name="common.lable.point"/></td>
	            <th><input type="text" readonly="readonly" value="<s:property value="mibinfoDomain.createTime"/>"/></th>
            </tr>
            <tr>
	            <td><s:text name="mib.agentDesc"/><s:text name="common.lable.point"/></td>
	            <th><textarea rows="3" cols="20" readonly="readonly" contenteditable="true" ><s:property value="mibinfoDomain.description"/></textarea></th>
            </tr>
		</table>
	</div>