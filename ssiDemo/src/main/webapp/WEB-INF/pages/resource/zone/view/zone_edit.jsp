<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" language="JavaScript1.2">
window.onload = function(){
		
	$.formValidator.initConfig({formid:"mainForm",wideword:false});
	
		$("#zoneName").formValidator({
			onshow : '<s:text name="zone.js.onShow.zoneName" />',
			onfocus : '<s:text name="zone.js.onShow.zoneName" />',
			oncorrect : '<s:text name="zone.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="zone.js.emptyerror" />'
			},
			onerror : '<s:text name="zone.js.not.null.name" />',
			onerrormax : '<s:text name="zone.js.onshow.20char" />'
		});

		//站点

		$("#siteId").formValidator({
			onshow : '<s:text name="zone.headerValue" />',
			onfocus : '<s:text name="zone.headerValue" />',
			oncorrect : '<s:text name="zone.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="zone.js.emptyerror" />'
			},
			onerror : '<s:text name="zone.headerValue" />',
			onerrormax : '<s:text name="zone.headerValue" />'
		});
		//负责人

		$("#principal").formValidator({
			onshow : '<s:text name="zone.js.onShow.principal" />',
			onfocus : '<s:text name="zone.js.onShow.principal" />',
			oncorrect : '<s:text name="zone.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="zone.js.emptyerror" />'
			},
			onerror : '<s:text name="zone.js.onShow.principal" />',
			onerrormax : '<s:text name="zone.js.onShow.principal" />'
		});
		//位置

		$("#address").formValidator({
			onshow : '<s:text name="zone.js.onShow.address" />',
			onfocus : '<s:text name="zone.js.onShow.address" />',
			oncorrect : '<s:text name="zone.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 50,
			empty : {
				leftempty : true,
				rightempty : true,
				emptyerror : '<s:text name="zone.js.emptyerror" />'
			},
			onerror : '<s:text name="zone.js.onShow.address" />',
			onerrormax : '<s:text name="zone.js.onShow.address" />'
		});
		
		//描述
		$("#desc").formValidator({
			onshow : '<s:text name="zone.js.onShow.100char" />',
			onfocus : '<s:text name="zone.js.onShow.100char" />',
			oncorrect : '<s:text name="zone.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 50,
			empty : {
				leftempty : true,
				rightempty : true,
				emptyerror : '<s:text name="zone.js.emptyerror" />'
			},
			onerror : '<s:text name="zone.js.onShow.100char" />',
			onerrormax : '<s:text name="zone.js.onShow.100char" />'
		});
		
		var msg = "<s:property value='msg'/>";
		var errorMsg = "<s:property value='errorMsg'/>";
		if (msg != null && msg != "") {
			$("#msgTip").html(msg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		} else if (errorMsg != null && errorMsg != "") {
			$("#msgTip").attr("class", "msgErrors");
			$("#msgTip").html(errorMsg);
			$(".messages").show("slow");
			$(".messages").delay(5000).hide("slow");
		}
		
		$("tr").each(function(){
			var tr_bgColor = $(this).css("background-color");
			$(this).hover(function(){
			    $(this).css("background-color","transparent");
			},function(){
				$(this).css("background-color","transparent");
			})
		});
		
	};
 function validate(){
	//验证同个机房名称是否相同.
	var formCheck = $.formValidator.pageIsValid("1");
	if (formCheck) {
		var zoneName = $("#zoneName").val();
		var siteId = $("#siteId").val();
		var zoneId = $("#zoneId").val();
		$.ajax({
			type : 'POST',
			url : 'validZoneName.action',
			data : {
				zoneName : zoneName,
				siteId : siteId,
				zoneId :zoneId
			},
			dataType : 'text',
			success : function(data) {
				if (data == "" || data == null) {
						$("#mainForm").submit();
				} else {
					$("#zoneNameTip").text("");
					$("#zoneNameTip").removeClass().addClass("onError");
					$("#zoneNameTip").text(data);
				}
			}
		});
	}
  }
</script> 
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
    <h2 class="sec-label"><s:text name="function.title"/></h2>
	<ul class="bread-cut">
	  <li><s:text name="resource.title.show.ci"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="function.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.edit"></s:text>
	</ul>
  </div>
</div> 

<%-- 表单 --%>
<div class="rightDisplayFwSj">
   <div class="formGroup">
   	 <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
	 <form id='mainForm' name="mainForm" method="post" action="zoneEdit.action" > 
 		<input type="hidden" id="zoneId"  name="room.zoneId" value="<s:property value="room.zoneId"/>">
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
        	<tr>
		        <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="resource.zone.roomName"/><s:text name="common.lable.point"/></td>
		        <td class="tdInput" valign="top">
		          <input type="text" id="zoneName" name="room.zoneName" value="<s:property value="room.zoneName"/>" class="input" maxlength="20"/>
		        </td>
		        <td class="tdVad" valign="top"><div id="zoneNameTip"></div></td>
	    	</tr>
		    <tr>  
		       <s:if test="roleType==2">
	        	<td class="tdLabel" align="right" valign="top"><s:text name="resource.zone.siteName"/><s:text name="common.lable.point"/></td>
		        <td class="tdInput" valign="top" style="color: #666;">
		         	<s:property value="room.siteName"/>
		         	<input type="text"  id="siteId" value="<s:property value="room.siteId"/>" name="room.siteId" style="display: none;"/>
		        </td>
		       </s:if>
		       <s:else>
		       <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/> <s:text name="resource.zone.siteName"/><s:text name="common.lable.point"/></td>
	        	<td class="tdInput" valign="top">
	        		<s:select list="siteList" listKey="siteId" listValue="siteName" id="siteId"  name="room.siteId" cssClass="newSelect"  headerKey="" headerValue="%{getText('common.lable.select')}" />
	        	</td>
	        	<td class="tdVad" valign="top"><div id="siteIdTip"></div> </td>
		       </s:else>
            </tr>
            
             <tr>
		        <td class="tdLabel" align="right" valign="top"><s:text name="resource.zone.principal"/> <s:text name="common.lable.point"/></td>
		        <td class="tdInput" valign="top">
		       	    <input type="text" id="principal" name="room.principal" value="<s:property value="room.principal"/>"  class="input" maxlength="20"/>
		        </td>
		        <td class="tdVad" valign="top"><div id="principalTip"></div></td>
			 </tr>
     
			 <tr>
		        <td class="tdLabel" align="right" valign="top"><s:text name="resource.zone.address"/> <s:text name="common.lable.point" /></td>
		        <td  class="tdInput" valign="top">
		         	<input type="text" id="address" name="room.address" value="<s:property value="room.address"/>" class="input" maxlength="50">
		         </td>
		         <td class="tdVad" valign="top"><div id="addressTip"></div></td>
			 </tr>

			 <tr>
		         <td class="tdLabel" align="right" valign="top"><s:text name="resource.zone.description"/><s:text name="common.lable.point"/></td>
		         <td  class="tdInput" valign="top">        
                     <textarea name="room.description" id="desc"  class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"><s:property value="room.description"/> </textarea>
		         </td>
		        <td class="tdVad" valign="top"><div id="descTip"></div></td>
			</tr> 
        </table>
     </form>
   </div>    
   <div class="messages" style="top: 25px;left: 37%;">
		<div id="msgTip" class="msgErrors"></div>
   </div>
   <%-- 按钮栏 --%>
	<div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj"  onclick="validate();"><s:text name="common.button.edit"/></a></li>   
	        <li><a href="zoneResourceInit.action" class="buttonGrey"><s:text name="common.button.cancel"/></a></li>
	    </ul>
	</div>
</div>

