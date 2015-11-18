<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script type="text/JavaScript">
<!--

//关闭浏览器窗口的处理
window.onbeforeunload=modelClose;

var parentWin;

$(function(){
	//显示提示消息
	var msg = "<s:property value='msg'/>";
	var errorMsg = "<s:property value='errorMsg'/>";
	if(msg != null && msg != ""){
		$("#msgTip").html(msg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}else if(errorMsg != null && errorMsg != ""){
		$("#msgTip").attr("class","msgErrors");
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}

	var checkString = window.dialogArguments;	
	var css = checkString.split(",");
	var checkAll = $("#checkID");
	$(checkAll).removeAttr("class");
	$("input + span").remove();
	var cbox = $("input[name=cbox]");
	for(var i = 0; i < cbox.length;i++){
		$(cbox[i]).removeAttr("class");
		$("input + span").remove();
		for(var j = 0; j < css.length;j++){
			if(cbox[i].value==css[j]){
				$(cbox[i]).attr("checked",true);
			}
		}
	}

	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
	//	alert(tr_bgColor);	
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		})
	})
	

});

function modelClose(){
	var cbox = $("input[name=cbox]");
	var id = "";
	var mail = "";
	var mobile = "";
	for(var i = 0; i < cbox.length;i++){
		if(cbox[i].checked){
			id += ((id!=''?",":"") + cbox[i].value);
			//获取用户姓名
			var aa = "#" + cbox[i].value;
			var tr=$(aa);
			
			var userMobile = $(tr).children().eq(4).text();
			userMobile = $.trim(userMobile);
			mobile += ((mail!=''?";":"") + userMobile);
			
			var userMail = $(tr).children().eq(5).text();
			userMail = $.trim(userMail);
			mail += ((mail!=''?";":"") + userMail);
		}
	}
	//alert(id + "@#neu&*" + mail + "@#neu&*" + mobile);
	window.returnValue = id + "@#neu&*" + mail + "@#neu&*" + mobile;
	window.close();
	}

//-->
</script>
<form action="" method="post" name="mainForm" id="mainForm">
  <!--内容部分 main star-->
  <div class="main">
    <!--工作区 iframeWorkarea start-->
    <div class="iframeWorkarea">
      <!--选项卡 tab start-->
      <div class="tabBar"><a href="#" class="tabCurrent" style="text-decoration:none"><s:text name="common.title.list"/></a></div>
      <!--选项卡 tab end-->
      <!--工具条 toolbar start-->
      <div class="toolbar">
      
      </div>
      <!--工具条 toolbar end-->
      <!--滚动条 scroll start-->
      <div class="scroll-pane workareaMain">
        <!--表格 list start-->
        <table width="99%" border="0" cellpadding="0"  cellspacing="0" class="list example">
        <s:if test="abList != null">
			<thead>
			<tr>
			<th class="listChe"></th>
			<th>
				唯一标识
			</th>
			<s:iterator value="abList" status="ab" id="abid">
			<s:if test="alias!='icon'">
			<th <s:if test="alias=='AS_ID'">style="width:200px;"</s:if>><s:property value="displayName"/></th>
			</s:if>
			</s:iterator>
			</tr>
			</thead>
		<tbody>
				<s:iterator value="beans" status="bean" id="bs">
					<tr <s:if test="#bean.odd==true">class="rowOddBg"</s:if><s:else>class="rowEvenBg"</s:else> id="<s:property value='alias'/>">
						<%--  <td class="listChe"><input name="funRadio" type="radio" id="funRadio" onclick="changeToolBar('<s:property value="alias" />');" value="<s:property value="id"/>" alias="<s:property value="alias" />"/></td>--%>
						<td class="listChe"><input name="cbox" type="checkbox"" id="cbox" value="<s:property value="alias" />" /></td>
						<td>
							<s:property value="alias"/>
						</td>
						<s:iterator value="abList" status="ab" id="ab2id">
							<s:if test="#ab2id.alias!='icon'">
							<td  title="<s:set var="flagstr" value="abc" /><s:iterator value="#bs.attributeValues" status="av" id="avid"><s:if test="#ab2id.alias==#avid.alias"><s:if test="#flagstr!=abc">&#10;<s:property value="#avid.value"/></s:if><s:else><s:property value="#avid.value"/><s:set var="flagstr" value="#abid.alias" /></s:else></s:if></s:iterator>">
							<s:set var="flagstr" value="abc" />
							<s:iterator value="#bs.attributeValues" status="av" id="avid">
								<s:if test="#ab2id.alias==#avid.alias">
								<s:if test="#flagstr!=abc"><br/><s:if test="%{#avid.value.length()>30}">
								      	<s:property value="#avid.value.substring(0,30)"/><s:text name="common.lable.ellipsis" /> 
									  </s:if>
									  <s:else>
										 <s:property value="#avid.value"/>
									  </s:else>
								</s:if>
								<s:else>
									<s:if test="%{#avid.value.length()>30}">
								      	<s:property value="#avid.value.substring(0,30)"/><s:text name="common.lable.ellipsis" /> 
									  </s:if>
									  <s:else>
										 <s:property value="#avid.value"/>
									  </s:else>
									<s:set var="flagstr" value="#abid.alias" />
								</s:else>
									&nbsp;
								</s:if>
							</s:iterator>
							</td>
							</s:if>
							</s:iterator>
					</tr>
				</s:iterator>
			</tbody>
		</s:if>
	</table>
        <!--表格 list end-->
      </div>
      <!--滚动条 scroll end-->
      <!--分页 buttonbar start-->
      <%-- 
      <s:if test="%{pageBar != null && pageBar != ''}">
        <div class="buttonbar">
         <s:property value="pageBar" escape="false" />
        </div>
	  </s:if>
	  --%>
      <!--分页 buttonbar end-->
      
      <div class="buttonbar">
			<!--按钮条 buttonbar start-->
			<div class="buttonbarLef">
			       <a href="#" class="btnImp btn_disable testMessage3"  onclick="modelClose();"><s:text name="common.button.add"/></a>   
			</div>
			<!--按钮条 buttonbar end-->
		</div>
      <!--分页 buttonbar end-->
      
      <%--   --%>
      <div class="messages succcess">
        <div id="msgTip" class="msgSuccess"></div>
      </div>  
    
      
    </div>
    <!--工作区 iframeWorkarea end-->
  </div>
  <!--内容部分 main end-->
</form>
  