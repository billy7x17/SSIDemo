<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script type="text/JavaScript">
//-->
</script>
<div class="BasicInformation">
    <h3><s:property value="domain.taskName"/></h3>
    <ul>
    </ul>
    <span class="close"><a href="javascript:closeDetail();"><img src="<%=request.getContextPath()%>/themes/blue/images/close.png"/></a></span>
</div>
<div class="baseTitl">基本信息</div>
<div class="baseTab scroll-pane">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
	            <td><s:text name="cronExpression.manager.taskID"/>：</td>
	            <th><s:property value="domain.taskID"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.taskname"/>：</td>
	            <th><s:property value="domain.taskName"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.excutetime"/>：</td>
	            <th><s:property value="domain.cronExpression"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.excutetimeDesc" />：</td>
	            <th id="cronExpression"></th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.addtime"/>：</td>
	            <th><s:property value="domain.addTime"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.updatetime"/>：</td>
	            <th><s:property value="domain.updateTime"/>&nbsp;</th>
            </tr>
            <!--
            <tr>
                <td><s:text name="cronExpression.manager.tasktype"/>：</td>
                <th><s:property value="domain.taskType"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.taskpara"/>：</td>
	            <th><s:property value="domain.taskParam"/>&nbsp;</th>
            </tr>
             -->
             <tr>
	            <td><s:text name="cronExpression.manager.ssID"/>：</td>
	            <th><s:property value="domain.ssID"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.beanID"/>：</td>
	            <th><s:property value="domain.beanID"/>&nbsp;</th>
            </tr>
            <tr>
	            <td><s:text name="cronExpression.manager.taskstatus"/>：</td>
	            <th>
					<s:if test="domain.taskStatus==0"><s:text name="cronExpression.taskstatus.effective" /></s:if>
                    <s:elseif test="domain.taskStatus==1"><s:text name="cronExpression.taskstatus.invalid" /></s:elseif>
                </th>
            </tr>
	 </table>
</div>
<script type="text/JavaScript">
	var expression = "<s:property value='domain.cronExpression'/>";
	document.getElementById("cronExpression").innerHTML = translateCronExpression(expression);
</script>