<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/JavaScript">
</script>
	<div class="BasicInformation">
          <h3 class="H3-base"><s:property value="alarmroster.manufactureID"/></h3>
       	 <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
   	</div>
   	 
      <div class="baseTitl">基本信息</div>
      <div class="baseTab scroll-pane">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
            <td width="30"><strong><s:text name="alarmroster.rosterID"/><s:text name="common.lable.point"/></strong></td>
            <th width="175"> <s:property value="alarmroster.rosterID"/></th>
            </tr>
          <tr>
            <td><strong><s:text name="alarmroster.manufactureID"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="alarmroster.manufactureID"/></th>
           </tr>	          
            <tr>
            <td><strong><s:text name="alarmroster.type"/><s:text name="common.lable.point"/></strong></td>
            <th>
            	<s:if test="alarmroster.type==0">
            		<s:text name="alarmroster.type.0"/>
            	</s:if>
            	<s:if test="alarmroster.type==1">
            		<s:text name="alarmroster.type.1"/>
            	</s:if>
            </th>
            </tr>
            <!-- 如果类型是0 显示阀值名称-->
            
            <s:if test="alarmroster.type==0">
             <tr>
           		 <td><strong><s:text name="alarmroster.thresholdID"/><s:text name="common.lable.point"/></strong></td>
            	 <th><s:property value="alarmroster.eventName"/></th>
          	 </tr>	                
            </s:if>
              <!-- 如果类型是0 规则名称-->
            <s:elseif test="alarmroster.type==1">
             <tr>
           		 <td><strong><s:text name="alarmroster.rowID"/><s:text name="common.lable.point"/></strong></td>
            	 <th><s:property value="alarmroster.alarmTitle"/></th>
          	 </tr>	                
            </s:elseif>
            
            <tr>
            <td><strong><s:text name="alarmroster.alarmGrade"/><s:text name="common.lable.point"/></strong></td>
            <th>
            	<s:property value="alarmroster.alarmGradeName"/>
            </th>
            </tr>
             <tr>
            <td><strong><s:text name="alarmroster.modifyTime"/><s:text name="common.lable.point"/></strong></td>
            <th><s:property value="alarmroster.modifyTime"/></th>
            </tr>
            <tr>
            <td><strong><s:text name="alarmroster.description"/><s:text name="common.lable.point"/></strong></td>
            <th>
           		<s:property value="alarmroster.description"/>
            </th>
            </tr>
		</table>
		</div>