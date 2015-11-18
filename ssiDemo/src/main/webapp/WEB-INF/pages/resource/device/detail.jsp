<script>
var winHeight = $(window).height() - 17; //17px为workarea的margin-top
$(".baseTab").height(winHeight - ($(".BasicInformation").height()+11) - (18 + 12*2));
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- begin -->
<div class="BasicInformation">
	<h2>
		<s:property value="deviceDomain.agentName" />
	</h2>
	<span class="close"><a href="javascript:closeDetail();"><img
			src="<%=request.getContextPath()%>/themes/blue/images/close.png" /></a></span>
</div>
<div class="baseTitl click" style="width: 100%;cursor: default;text-decoration: none;">
	<s:text name="common.title.newDetil" />
</div>
<div class="baseTab scroll-pane">
	<form action="updateDetailByAjax.action" id="form" method="post">
	<table width="250" border="0" cellspacing="0" cellpadding="0">
		<s:hidden value="%{deviceDomain.agentId}" name="deviceDomain.agentId"></s:hidden>
		<tr>
			<td><s:text name="device.localTableIDRef" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.localTableIDRef" />" /></th>
		</tr>
		<tr>
			<td><s:text name="device.deviceName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.agentName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="device.status" /> <s:text
					name="common.lable.point" /></td>
			<th><s:if test='deviceDomain.status == "0"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.status.0" />" />
				</s:if> <s:elseif test='deviceDomain.status == "1"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.status.1" />" />
				</s:elseif></th>
		</tr>
		<tr>
			<td><s:text name="device.agentGroup" /> <s:text
					name="common.lable.point" /></td>
			<th><s:if test='deviceDomain.agentGroup == "NVR"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.NVR" />" />
				</s:if> <s:elseif test='deviceDomain.agentGroup == "IP-SAN"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.IP-SAN" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "Encoder"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.Encoder" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "IPC"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.IPC" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "Switch"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.Switch" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "VMS"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.VMS" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "D4"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.D4" />" />
				</s:elseif> <s:elseif test='deviceDomain.agentGroup == "Keyboard"'>
					<input type="text" readonly="readonly"
						value="<s:text name="device.group.Keyboard" />" />
				</s:elseif></th>
		</tr>
		<tr>
			<td><s:text name="device.typeName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.typeName" />" /></th>
		</tr>
		<tr>
			<td><s:text name="device.deviceType" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly" name="deviceDomain.deviceType"
				value="<s:property value="deviceDomain.deviceType" />" /></th>
		</tr>
		<tr>
			<td><s:text name="device.agentComp" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly" name="deviceDomain.agentComp"
				value="<s:property value="deviceDomain.agentComp" />" /></th>
		</tr>
		<!-- if the device is NVR,IP-SAN,Encoder(not E4V), it has diskNum attribute -->
		<s:if
			test="deviceDomain.typeId == '31' || deviceDomain.typeId == '32' || deviceDomain.typeId == '42'
			 || deviceDomain.typeId == '33' || deviceDomain.typeId == '34' || deviceDomain.typeId == '35'">
			<tr>
				<td><s:text name="device.diskNum" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly" name="deviceDomain.diskNum"
					value="<s:property value="deviceDomain.diskNum" />" /></th>
			</tr>
		</s:if>
		<!-- if the device is Encoder(E4V), it has channel attribute -->
		<s:if test="deviceDomain.typeId == '46'">
			<s:iterator value="deviceDomain.channelList">
				<tr>
					<td><s:text name="device.channelId" /> <s:property
							value="channelId" /> <s:text name="common.lable.point" /></td>
					<th><input type="text" readonly="readonly"
						value="<s:property value="channelName" />" /></th>
				</tr>
			</s:iterator>
		</s:if>
		<tr>
			<td><s:text name="device.siteName" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.siteName" />" /></th>
		</tr>
		<!-- if the device is IPC, it does't have zone or cluster attribute -->
		<s:if
			test="deviceDomain.typeId != '36' && deviceDomain.typeId != '37'
		&& deviceDomain.typeId != '38' && deviceDomain.typeId != '39' && deviceDomain.typeId != '40'">
			<tr>
				<td><s:text name="device.zoneName" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.zoneName" />" /></th>
			</tr>
			<tr>
				<td><s:text name="device.clusterName" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.clusterName" />" /></th>
			</tr>
		</s:if>
		<!-- if the device is not H3C, it has vmsId attribute -->
		<s:if test="deviceDomain.typeId != '41' && deviceDomain.typeId != '43' && deviceDomain.typeId != '44'">
			<tr>
				<td><s:text name="device.vmsName" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.vmsName" />" /></th>
			</tr>
		</s:if>
		<!-- if the device is Encoder or IPC, it has nvrId attribute -->
		<s:if
			test="deviceDomain.typeId == '35' || deviceDomain.typeId == '46' || deviceDomain.typeId == '36' || deviceDomain.typeId == '37'
		|| deviceDomain.typeId == '38' || deviceDomain.typeId == '39' || deviceDomain.typeId == '40'">
			<tr>
				<td><s:text name="device.nvrName" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.nvrName" />" /></th>
			</tr>
		</s:if>
		<!-- if the device is not H3C, it has switch attribute -->
		<s:if test="deviceDomain.typeId != '41'">
			<tr>
				<td><s:text name="device.switchName" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.switchName" />" /></th>
			</tr>
			<tr>
				<td><s:text name="device.switchPort" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.switchPort" />" /></th>
			</tr>
		</s:if>
		<tr>
			<td><s:text name="device.deviceIp" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.agentIp" />" /></th>
		</tr>
		<s:if test='deviceDomain.acquisitionMode == "1"'>
			<tr>
				<td><s:text name="device.clPort" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.clPort" />" /></th>
			</tr>
			<tr>
				<td><s:text name="device.community" /> <s:text
						name="common.lable.point" /></td>
				<th><input type="text" readonly="readonly"
					value="<s:property value="deviceDomain.community" />" /></th>
			</tr>
		</s:if>
		<tr>
			<td><s:text name="device.agentDesc" /> <s:text
					name="common.lable.point" /></td>
			<th><textarea rows="3" cols="20" readonly="readonly" name="deviceDomain.agentDesc" contenteditable="true" ><s:property value="deviceDomain.agentDesc"/></textarea></th>
		</tr>
		<tr>
			<td><s:text name="device.createTime" /> <s:text
					name="common.lable.point" /></td>
			<th><input type="text" readonly="readonly"
				value="<s:property value="deviceDomain.createTime" />" /></th>
		</tr>
	</table>
	</form>
	<ul style="padding:2px;border: 0;">
		<s:if test="#session.authenticater.authIdList.contains('02_08_03_00')&&isTopo.equals(\"true\")">
			<li id="confirmLi" class="pageButon1"><a href="#" onclick="editDetail(this);return false;"><font color='#FFFFFF'><s:text name="device.button.edit" /></font></a></li>
		</s:if> 
	</ul>
	<br/>
</div>
<script type="text/javascript">
<s:if test="!#session.authenticater.authIdList.contains('02_08_00_00')">
	readonly();
</s:if> 
function readonly(){
	$(".baseTab table input,textarea").each(function(){
		if($(this).prop("name") != null&&$(this).prop("name") != ""){
			
			$(this).removeAttr("readonly");
		}
	});
}

function editDetail(obj){
	$(obj).html('<font color="#FFFFFF"><s:text name="device.button.save" /></font>');
	$("#confirmLi").parent().append('<li id="confirmLi" class="pageButonRed"><a href="#" onclick="toReadOnly(this);return false;"><font color="#FFFFFF"><s:text name="device.button.cancel" /></font></a></li>');
	$(obj).attr('onclick','saveDetail()');
	readonly();
}

function saveDetail(){
	var form = $("#form").serialize();
	toReadOnly($("#confirmLi").parent().children("li:last").children("a"));
	$.ajax({
		 	 type: "POST",
			 url: "updateDetailByAjax.action",
			 dataType:"json",
			 async: false,
			 data: form,
			 success: function(text){
				if (0 == text) {
					alert('修改失败');
				}
				else{
					alert('修改成功');
				}
			 }
	}); 
}

function toReadOnly(obj){
	
	$(".baseTab table input,textarea").each(function(){
		$(this).attr("readonly","readonly");
	});
	
	$(obj).parent().remove();
	
	$("#confirmLi").children("a").html('<font color="#FFFFFF"><s:text name="device.button.edit" /></font>');
	
	$("#confirmLi").children("a").attr("onclick","editDetail(this)");
	
}
</script>