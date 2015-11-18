<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="deviceList == null">
<%="null"%>
</s:if>
<s:else>
 <table width="99%" border="0" cellpadding="0"  cellspacing="0" class="list example">
          <thead>
            <tr>
              <th><s:text name="field.label.relationDevice"/></th>
            </tr>
          </thead>
          <tbody>
            <s:iterator value="deviceList" status="st4">
			  <tr <s:if test="#st4.odd == true">class="rowOddBg"</s:if><s:else>class="rowEvenBg"</s:else>>
				<td title="<s:property value="ciName"  escape="false"/>">
				  <s:if test="%{ciName.length()>30}">
				    <s:property value="ciName.substring(0,30)"  escape="false"/>...&nbsp; </br>
				  </s:if>
				  <s:else>
				    <s:property value="ciName" escape="false"/>&nbsp; </br>
				  </s:else>
		       </td>
			  </tr>
		    </s:iterator>
          </tbody>
        </table>
</s:else>