<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
  
<script type="text/javascript" language="JavaScript1.2">
window.onload = function(){
	var errorMsg ='<s:property value="errorMsg"/>';
	if(null != errorMsg && errorMsg != ""){
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}
	 $.formValidator.initConfig({formid:"mainForm",wideword:false});
	//机柜名称
		$("#clusterName").formValidator({
			onshow : '<s:text name="cluster.js.onShow.clusterName" />',
			onfocus : '<s:text name="cluster.js.onShow.clusterName" />',
			oncorrect : '<s:text name="cluster.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="cluster.js.emptyerror" />'
			},
			onerror : '<s:text name="cluster.js.not.null.name" />',
			onerrormax : '<s:text name="cluster.js.onshow.20char" />'
		});
		/* //站点
		$("#siteId").formValidator({
			onshow : '<s:text name="cluster.headerValue" />',
			onfocus : '<s:text name="cluster.headerValue" />',
			oncorrect : '<s:text name="cluster.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="cluster.js.emptyerror" />'
			},
			onerror : '<s:text name="cluster.headerValue" />'
		});
*/
		//机房
		$("#zoneId").formValidator({
			onshow : '<s:text name="cluster.headerValue" />',
			onfocus : '<s:text name="cluster.headerValue" />',
			oncorrect : '<s:text name="cluster.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="cluster.js.emptyerror" />'
			},
			onerror : '<s:text name="cluster.headerValue" />',
			onerrormax : '<s:text name="cluser.headerValue" />'
		});
		
		//品牌
		$("#brand").formValidator({
			onshow :'<s:text name="cluster.js.onShow.brand"/>',
			onfocus:'<s:text name="cluster.js.onShow.brand"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.brand"/>',
			onerrormax:'<s:text name="cluster.js.onShow.brand"/>'
		});
		//供应商
		$("#manufacturer").formValidator({
			onshow :'<s:text name="cluster.js.onShow.manufacturer"/>',
			onfocus:'<s:text name="cluster.js.onShow.manufacturer"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.manufacturer"/>',
			onerrormax:'<s:text name="cluster.js.onShow.manufacturer"/>'
		});
		//长度
		$("#length").formValidator({
			onshow :'<s:text name="cluster.js.onShow.length"/>',
			onfocus:'<s:text name="cluster.js.onShow.length"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.length"/>',
			onerrormax:'<s:text name="cluster.js.onShow.length"/>'
		});
		//宽度
		$("#width").formValidator({
			onshow :'<s:text name="cluster.js.onShow.width"/>',
			onfocus:'<s:text name="cluster.js.onShow.width"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.width"/>',
			onerrormax:'<s:text name="cluster.js.onShow.width"/>'
		});
		//宽度
		$("#heigth").formValidator({
			onshow :'<s:text name="cluster.js.onShow.heigth"/>',
			onfocus:'<s:text name="cluster.js.onShow.heigth"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.heigth"/>',
			onerrormax:'<s:text name="cluster.js.onShow.heigth"/>'
		});
		
		//高度
		$("#height").formValidator({
			onshow :'<s:text name="cluster.js.onShow.height"/>',
			onfocus:'<s:text name="cluster.js.onShow.height"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.height"/>',
			onerrormax:'<s:text name="cluster.js.onShow.height"/>'
		});
		//总U数
		$("#unum").formValidator({
			onshow :'<s:text name="cluster.js.onShow.uNum"/>',
			onfocus:'<s:text name="cluster.js.onShow.uNum"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.uNum"/>',
			onerrormax:'<s:text name="cluster.js.onShow.uNum"/>'
		});
		//总功率
		$("#power").formValidator({
			onshow :'<s:text name="cluster.js.onShow.power"/>',
			onfocus:'<s:text name="cluster.js.onShow.power"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.power"/>',
			onerrormax:'<s:text name="cluster.js.onShow.power"/>'
		});
		//规格
		$("#size").formValidator({
			onshow :'<s:text name="cluster.js.onShow.size"/>',
			onfocus:'<s:text name="cluster.js.onShow.size"/>',
			oncorrect:'<s:text name="cluster.js.oncorrect"/>'
		}).inputValidator({
			min:0,
			max:10,
			empty:{
				leftempty:false,
				rigthempty:false,
				emptyerror:'<s:text name="cluster.js.emptyerror"/>'
			},
			onerror:'<s:text name="cluster.js.onShow.size"/>',
			onerrormax:'<s:text name="cluster.js.onShow.size"/>'
		});
		//描述
		$("#desc").formValidator({
			onshow : '<s:text name="cluster.js.onshow.100char" />',
			onfocus : '<s:text name="cluster.js.onshow.100char" />',
			oncorrect : '<s:text name="cluster.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : '<s:text name="cluster.js.not.null.name" />',
			onerrormax : '<s:text name="cluster.js.onshow.100char" />'
		});
		
		
		$("#siteIdTip").removeClass().addClass("onShow");
		$("#siteIdTip").text('<s:text name="cluster.headerValue"/>');
		
	$("tr").each(function(){
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		});
	});
	};
 function validate(){
		var formCheck = $.formValidator.pageIsValid("1");
		if(formCheck){
			var clusterName = $("#clusterName").val();
			var zoneId = $("#zoneId").val();
			var clusterId = $("#clusterId").val();
			$.ajax({
				url:'validSameClusterName.action',
				data:{
					clusterName:clusterName,
					zoneId:zoneId,
					clusterId:clusterId
				},
				type:'POST',
				dataType:'text',
				success:function(data){
					if(data=="" ||data==null){
						$("#mainForm").submit();
					}else{
						$("#clusterNameTip").text("");
						$("#clusterNameTip").removeClass().addClass("onError");
						$("#clusterNameTip").text(data);
					}
				}
			});
		}
	} 
//改变站点信息时绑定机放信息.
	function addZone(obj) {
		var value = obj.value;
		if(value!='' && value!=null && value!=""){
			$.ajax({
				url:'getZoneList.action',
				type:'POST',
				data:{
					siteId:value
				},
				dataType:'text',
				success:function(data){
					var arrays = eval("("+data+")");
					$("#zoneId").empty();
					$("<option value=''>" + '<s:text name="common.lable.select" />' + "</option>").appendTo("#zoneId");
					for(var i=0;i<arrays.length;i++ ){
						$("<option value=\""+arrays[i].zoneId+"\">"+arrays[i].zoneName+"</option>").appendTo("#zoneId");
					}
				}
				
			});
			$("#zoneIdTip").removeClass().addClass("onShow");
			$("#zoneIdTip").text('<s:text name="cluster.headerValue" />');
		}else{
			$("#zoneId").val("");
			if($("#zoneIdTip").attr("class")=="onCorrect"){
				$("#zoneIdTip").removeClass().addClass("onShow");
				$("#zoneIdTip").text('<s:text name="cluster.headerValue" />');
			}
		}
	}
	
	function validNotEmpty(obj){
		var value = obj.value;
		if(value=="" ||value==null ||value==''){
			$("#siteIdTip").removeClass().addClass("onError");
			$("#siteIdTip").text('<s:text name="cluster.headerValue"/>');
		}else{
			$("#siteIdTip").removeClass().addClass("onCorrect");
			$("#siteIdTip").text('<s:text name="cluster.js.oncorrect" />');
		}
	}
</script> 
<%-- 当前位置 --%>
<div class="rightToolbar">
    <div class="rightToolbarCrumbs">
        <h2 class="sec-label"><s:text name="resource.cluster.title"/></h2>
		<ul class="bread-cut">
		  <li><s:text name="resource.title.show.ci"/></li>
		  <li><s:text name="common.lable.arrow"/> </li>
		  <li><s:text name="resource.cluster.title"/> </li>
		  <li><s:text name="common.lable.arrow"/></li>
		  <li><s:text name="common.title.edit"></s:text>
		</ul>
    </div>
</div>

<%-- 表单 --%>
<div class="rightDisplayFwSj">
   <div class="formGroup">
   	<div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
	<form id='mainForm' name="mainForm" method="post" action="clusterEdit.action" > 
		<input type="hidden" id="clusterId" name="cluster.clusterId" value="<s:property value="cluster.clusterId"/>"/>
        <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
        	<tr>
        		<td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="resource.cluster.clusterName"/><s:text name="common.lable.point"/></td>
		        <td class="tdInput" valign="top">
		          <input type="text" id="clusterName" name="cluster.clusterName" value="<s:property value="cluster.clusterName"/>" class="input" maxlength="20"/>
		        </td>
		        <td class="tdVad" valign="top"><div id="clusterNameTip"></div></td>
         	</tr>
         <s:if test="roleType==2">
         	<tr>
         		<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.siteName"/><s:text name="common.lable.point"/> </td>
         		<td class="tdInput" valign="top" style="color: #666;">
         			<s:property value="cluster.siteName"/>
         			<input type="hidden" id="siteId" name="cluster.siteId" value="<s:property value="cluster.siteId"/>" />
         		</td>
         	</tr>
         	<tr>
         		<td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="resource.cluster.zoneName"/><s:text name="common.lable.point"/> </td>
         		<td class="tdInput" valign="top">
         			<s:select list="zoneList" listKey="zoneId"  listValue="zoneName" id="zoneId" name="cluster.zoneId" cssClass="newSelect"  headerKey="" headerValue="%{getText('common.lable.select')}"/>
         		</td>
         		<td class="tdVad" valign="top"><div id="zoneIdTip"></div></td>
         	</tr>
        </s:if>
        <s:else>
        	<tr>
         		<td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="resource.cluster.siteName"/><s:text name="common.lable.point"/> </td>
         		<td class="tdInput" valign="top">
         			<s:select list="siteList" listKey="siteId"  listValue="siteName" id="siteId" name="cluster.siteId" 
         				cssClass="newSelect"  onchange="addZone(this);" onblur="validNotEmpty(this);"  headerKey="" headerValue="%{getText('common.lable.select')}"/>
         		</td>
         		<td class="tdVad" valign="top"><div id="siteIdTip"></div></td>
         	</tr>
         	<tr>
         		<td class="tdLabel" align="right" valign="top"><s:text name="common.red.point"/><s:text name="resource.cluster.zoneName"/><s:text name="common.lable.point"/> </td>
         		<td class="tdInput" valign="top">
         			<s:select list="zoneList" listKey="zoneId"  listValue="zoneName" id="zoneId" name="cluster.zoneId" cssClass="newSelect"  headerKey="" headerValue="%{getText('common.lable.select')}"/>
         		</td>
         		<td class="tdVad" valign="top"><div id="zoneIdTip"></div></td>
         	</tr>
        </s:else>
        	<tr>
         		<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.brand"/><s:text name="common.lable.point"/> </td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="brand" name="cluster.brand" value="<s:property value="cluster.brand"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="brandTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.manufacturer"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="manufacturer" name="cluster.manufacturer" value="<s:property value="cluster.manufacturer"/>" class="input" maxlength="20"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="manufacturerTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.length"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="length" name="cluster.length" value="<s:property value="cluster.length"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="lengthTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.width"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="width" name="cluster.width" value="<s:property value="cluster.width"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="widthTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.height"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="height" name="cluster.height" value="<s:property value="cluster.height"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="heightTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.unum"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="unum" name="cluster.Unum" value="<s:property value="cluster.Unum"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="unumTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.power"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="power" name="cluster.power" value="<s:property value="cluster.power"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="powerTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.size"/><s:text name="common.lable.point"/></td>
        		<td class="tdInput" valign="top">
        			<input type="text" id="size" name="cluster.size" value="<s:property value="cluster.size"/>" class="input" maxlength="10"/>
        		</td>
        		<td class="tdVad" valign="top"><div id="sizeTip"></div> </td>
        	</tr>
        	<tr>
       			<td class="tdLabel" align="right" valign="top"><s:text name="resource.cluster.description"/><s:text name="common.lable.point"/></td>
        		<td  class="tdInput" valign="top">        
                    <textarea name="cluster.description" id="desc" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"><s:property value="cluster.description"/> </textarea>
			    </td>
			    <td class="tdVad" valign="top"><div id="descTip"></div></td>
        	</tr>
        </table>
     </form>  
    </div>
	<div class="messages" style="top: 25px;left: 37%;">
	  <div id="msgTip" class="msgErrors">
	  </div>
	</div>  
	<%-- 按钮栏 --%>
	<div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj"  onclick="validate();"><s:text name="common.button.edit"/></a></li>   
	        <li><a href="clusterResourceInit.action" class="buttonGrey" ><s:text name="common.button.cancel"/></a></li>
	    </ul>
	</div>
</div>
