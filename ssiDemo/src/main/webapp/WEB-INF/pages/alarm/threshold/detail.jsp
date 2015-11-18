<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
</script>
	<div class="BasicInformation">
          <h3 class="H3-base"><s:property value="thresholdDomain.eventName"/></h3>
       	 <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
   	</div>
   	 
      <div class="baseTitl">基本信息</div>
      <div class="baseTab scroll-pane">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		    <%--  --%>
		    <tr>
            <td><strong><s:text name="threshold.alarmIdentityID"/><s:text name="common.lable.point"/></strong></td>
            <th> <s:property value="thresholdDomain.alarmIdentityID"/></th>
            </tr>
           
		    <tr>
            <td width="30"><strong><s:text name="threshold.name"/><s:text name="common.lable.point"/></strong></td>
            <th width="175"> <s:property value="thresholdDomain.eventName"/></th>
            </tr>
            
            <tr>
            <td><strong><s:text name="threshold.thresholdTitle"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.tcTitle"/></th>
            </tr>
             
            <tr>
            <td><strong><s:text name="threshold.level"/><s:text name="common.lable.point"/></strong></td>
            <th><s:text name="thresholdDomain.levelName"/></th>
            </tr>

            <tr>
            <td><strong><s:text name="threshold.thresholdDeviceType"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.agentType"/></th>
            </tr>
            <s:if test="thresholdDomain.typeId=='20'">
             <tr>
            <td><strong><s:text name="threshold.system"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.systemName"/></th>
            </tr>
            </s:if>
            <tr>
            <td><strong><s:text name="threshold.OID"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.oid"/></th>
            </tr>
            
             <tr>
            <td><strong><s:text name="threshold.sign" /><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.perConditionName"/></th>
            </tr>
            <tr>
            <td><strong><s:text name="threshold.threshold" /><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.interval"/></th>
            </tr>
            
            <tr>
            <td><strong><s:text name="threshold.createTime"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.createTime"/></th>
            </tr>
            <tr>
            <td><strong><s:text name="threshold.agentDesc"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.eventTypeDesc"/></th>
            </tr>
            <tr>
            <td><strong><s:text name="threshold.alarmImpact"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="thresholdDomain.alarmImpact"/></th>
            </tr>
           
		</table>
	</div>