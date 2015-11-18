<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<script type="text/JavaScript">
</script>
	<div class="BasicInformation">
          <h3 class="H3-base"><s:property value="domain.filterName"/></h3>
       	 <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
   	</div>
   	 
      <div class="baseTitl">基本信息</div>
      <div class="baseTab scroll-pane">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td width="30"><strong><s:text name="field.label.filterName"/><s:text name="common.lable.point"/></strong></td>
		      <th width="175"><s:property value="domain.filterName"/></th>
		    </tr>
		    <tr>
			  <td><strong><s:text name="field.label.rosterId"/><s:text name="common.lable.point"/></strong></td>
		      <th><s:property value="domain.rosterId"/></th>
		    </tr>
			
		    <tr>
			  <td><strong><s:text name="field.label.rosterName"/><s:text name="common.lable.point"/></strong></td>
		      <th><s:property value="domain.manufactureId"/></th>
		    </tr>
		     <tr>
			  <td><strong><s:text name="field.label.rosterType"/><s:text name="common.lable.point"/></strong></td>
		      <th>
		         <s:if test="domain.rosterType==0">
				      阀值类型&nbsp; 
				 </s:if>
				 <s:elseif test="domain.rosterType==1">
				     规则类型&nbsp; 
				</s:elseif>
				
		      </th>
		    </tr>
		    <tr>
			  <td><strong>阀值(规则)名称<s:text name="common.lable.point"/></strong></td>
		      <th>
		         <s:if test="domain.rosterType==0">
				     <s:property value="domain.thresholdName"/>&nbsp; 
				 </s:if>
				 <s:elseif test="domain.rosterType==1">
					<s:property value="domain.accessName"/>&nbsp; 
				  </s:elseif>
				   <s:else>
					&nbsp; 
				  </s:else>
              </th>
		    </tr>
		    
			<tr>
			  <td><strong>映射后告警级别<s:text name="common.lable.point"/></strong></td>
		      <th><s:property value="domain.alarmGrade"/></th>
		    </tr>
			
		    <tr>
			  <td><strong><s:text name="field.label.filterStatus"/><s:text name="common.lable.point"/></strong></td>
		      <th><s:property value="domain.filterStatus"/></th>
		    </tr>     
		    <tr>
			  <td><strong><s:text name="field.label.modifyTime"/><s:text name="common.lable.point"/></strong></td>
		      <th><s:property value="domain.modifyTime"/></th>
		    </tr>     		
		</table>
	</div>