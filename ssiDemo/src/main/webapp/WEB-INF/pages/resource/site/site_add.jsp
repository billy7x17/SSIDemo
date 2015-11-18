<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
	<h2 class="sec-label"><s:text name="resource.site.title"/></h2>
	<ul class="bread-cut">
	  <li><s:text name="resource.title.show.ci"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="resource.site.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.add"></s:text>
	</ul>
  </div>
</div>
<!-- 表单内容 -->
<div class="rightDisplayFwSj"> 
	<div class="formGroup">	
	   <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
	   <form id="mainForm" method="post" action="addSite.action" > 
			<table class="addTable" align="center" cellpadding="0" cellspacing="0" >
	       		<tr>
				   <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="site.table.siteName"/><s:text name="common.lable.point"/></td>
				   <td class="tdInput" valign="top">
				     <input type="text" id="siteName" name="site.siteName" class="input" maxlength="20"/>
				   </td>
				   <td class="tdVad" valign="top"><div id="siteNameTip"></div></td>
				</tr>
	
				<tr>
				   <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="site.table.lineName"/><s:text name="common.lable.point"/></td>
				   <td class="tdInput" valign="top">
				     <s:select list="lineMap" key="key" value="value" id="lineNum" name="site.lineNum" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}" />
				   </td>
				   <td class="tdVad" valign="top"><div id="lineNumTip"></div></td>
				</tr>
	
				<tr>
				   <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="site.table.siteType"/><s:text name="common.lable.point" /></td>
				   <td  class="tdInput" valign="top">
				 	  <s:select list="typeMap" key="key" value="value" id="siteType" name="site.siteType" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}"/>
				   </td>
				   <td class="tdVad" valign="top"><div id="siteTypeTip"></div></td>
				</tr>
				
				<tr>
				   <td class="tdLabel" align="right" valign="top"><s:text name="site.table.description"/><s:text name="common.lable.point"/></td>
				   <td  class="tdInput" valign="top">        
				         <textarea name="site.description" id="desc" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"></textarea>
				    </td>
			        <td class="tdVad" valign="top"><div id="descTip"></div></td>
				</tr> 
	         </table>  
	    </form> 
	</div>  
	<div class="messages succcess" style="top: 25px; left: 37%; ">
  		<div id="msgTip" class="msgSuccess"></div>
    </div>
	 <div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="siteList.action" class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div> 
</div>
    

<script type="text/JavaScript">
//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。

$(function(){
	
	$.formValidator.initConfig({formid:"mainForm",wideword:false});
	//名称
	$("#siteName").formValidator({
			onshow : '<s:text name="site.js.onShow.siteName" />',
			onfocus : '<s:text name="site.js.onShow.siteName" />',
			oncorrect : '<s:text name="site.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="site.js.emptyerror" />'
			},
			onerror : '<s:text name="site.js.not.null.name" />',
			onerrormax : '<s:text name="site.js.onshow.20char" />'
		});

		//线路

		$("#lineNum").formValidator({
			onshow : '<s:text name="site.headerValue" />',
			onfocus : '<s:text name="site.headerValue" />',
			oncorrect : '<s:text name="site.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="site.js.emptyerror" />'
			},
			onerror : '<s:text name="site.headerValue" />',
			onerrormax : '<s:text name="site.headerValue" />'
		});
		//站点类型

		$("#siteType").formValidator({
			onshow : '<s:text name="site.headerValue" />',
			onfocus : '<s:text name="site.headerValue" />',
			oncorrect : '<s:text name="site.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="site.js.emptyerror" />'
			},
			onerror : '<s:text name="site.headerValue" />',
			onerrormax : '<s:text name="site.headerValue" />'
		});
		
		//描述desc
		$("#desc").formValidator({
			onshow : '<s:text name="site.js.onshow.100char" />',
			onfocus : '<s:text name="site.js.onshow.100char" />',
			oncorrect : '<s:text name="site.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="site.js.emptyerror" />'
			},
			onerror : '<s:text name="site.js.not.null.name" />',
			onerrormax : '<s:text name="site.js.onshow.100char" />'
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

		//去掉鼠标移入移出效果
		$("tr").each(function() {
			var tr_bgColor = $(this).css("background-color");
			$(this).hover(function() {
				$(this).css("background-color", "transparent");
			}, function() {
				$(this).css("background-color", "transparent");
			});
		});
	});

	function validate() {
		//验证同一线路站点名称是否相同.
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck) {
			var siteName = $("#siteName").val();
			var lineNum = $("#lineNum").val();
			$.ajax({
				type : 'POST',
				url : 'validSameSiteName.action',
				data : {
					siteName : siteName,
					lineNum : lineNum
				},
				dataType : 'text',
				success : function(data) {
					if (data == "" || data == null) {
						$("#mainForm").submit();
					} else {
						$("#siteNameTip").text("");
						$("#siteNameTip").removeClass().addClass("onError");
						$("#siteNameTip").text(data);
					}
				}
			});
		}
	}

	function validateTextarea() {
		if ($("#desc").val().length > 100) {
			$("#desc").val($("#desc").val().substring(0, 100));
		}
	}

	//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
</script>      