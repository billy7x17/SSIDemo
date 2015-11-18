<!--5555${url}66666-->

<tr>
<td height="30" align="center">
	<table width="98%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="30%" align="left" >每页显示 
				  <select name="pageSize" onchange="if (this.options[this.selectedIndex].value != '') {
					location = encodeURI('${url}?<#if numParam != "">${numParam}&</#if>page=${page}&pageSize=' + this.options[this.selectedIndex].value);}">
					${numPage}
				</select> 共 ${total} 条</td>
                <td width="40%" align="center">
					  <div id="gotoPage">
				        <ul>
				        <#if firstUrl != "">
				          	<li><a href="#" class="btnFirst" onclick="javaScript:window.location=encodeURI('${firstUrl}');"></a></li>
				        <#else>
				        	<li><a  class="btnFirst"></a></li>
				         </#if>
				         <#if prevUrl != ""> 
	                      	<li><a href="#" class="btnPrevious" onclick="javaScript:window.location=encodeURI('${prevUrl}');"></a></li>
	                     <#else> 
	                     	<li><a  class="btnPrevious"></a></li>
	                     </#if>
	                      <li class="WordsH">第${page}/${totalPageCount}页</li>
	                     <#if nextUrl != "">
	                      	<li><a href="#" class="btnNext2" onclick="javaScript:window.location=encodeURI('${nextUrl}');"></a></li>
	                     <#else>
	                      	<li><a class="btnNext2"></a></li>
	                     </#if>
	                     <#if lastUrl != ""> 
	                      	<li><a href="#" class="btnLast" onclick="javaScript:window.location=encodeURI('${lastUrl}');"></a></li>
	                     <#else>
	                      	<li><a  class="btnLast"></a></li>
	                      </#if>
					    </ul>
				      </div>				
			     </td>
                <td width="30%" align="right">跳转至
                  <select name="page" onchange="if (this.options[this.selectedIndex].value != '') {
					location = encodeURI('${url}?<#if param != "">${param}&</#if>page=' + this.options[this.selectedIndex].value);
						}">
							${jumpPage}
					</select></td>
              </tr>
	</table>
	</td>
</tr>