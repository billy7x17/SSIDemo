<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type='text/javascript'
	src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript'
	src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/dwr/interface/DeviceBeforeAddAction.js'></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/JavaScript">
	//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
	$(function() {
		$.formValidator.initConfig({
			formid : "edit_form",
			wideword : false
		});

		//设备编号
		$("#localTableIDRef").formValidator({
			onshow : '<s:text name="device.js.onshow.localTableIDRef" />',
			onfocus : '<s:text name="device.js.onshow.localTableIDRef" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 32,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.js.onshow.localTableIDRef" />',
			onerrormax : '<s:text name="device.js.32char" />'
		});

		//设备名称
		$("#deviceName").formValidator({
			onshow : '<s:text name="device.js.onshow.deviceName" />',
			onfocus : '<s:text name="device.js.onshow.deviceName" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 32,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.js.onshow.deviceName" />',
			onerrormax : '<s:text name="device.js.32char" />'
		});

		// 所属站点
		$("#siteIdTip").removeClass().addClass("onShow");
		$("#siteIdTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#siteId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 26,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerrormin : '<s:text name="device.headerValue" />',
			onerror : '<s:text name="device.headerValue" />'
		});
		*/

		// ip地址
		$("#deviceIp")
				.formValidator({
					onshow : '<s:text name="device.js.onshow.deviceIp" />',
					onfocus : '<s:text name="device.js.onshow.deviceIp" />',
					oncorrect : '<s:text name="device.js.oncorrect" />'
				})
				.regexValidator(
						{
							regexp : "^(([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){2}(\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])))$",
							onerror : '<s:text name="validator.deviceIp.onerror" />'
						}).inputValidator({
					min : 1,
					max : 15,
					empty : {
						leftempty : false,
						rightempty : false,
						emptyerror : '<s:text name="device.js.emptyerror" />'
					},
					onerror : '<s:text name="device.js.onshow.deviceIp" />',
					onerrormax : '<s:text name="device.js.15char" />'
				});

		//设备分组
		$("#agentGroupTip").removeClass().addClass("onShow");
		$("#agentGroupTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#agentGroup").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 9,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
		*/	

		//设备类型
		$("#typeId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});

		//设备型号
		$("#deviceType").formValidator({
			onshow : '<s:text name="device.js.onshow.deviceType" />',
			onfocus : '<s:text name="device.js.onshow.deviceType" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 32,
			onerrormax : '<s:text name="device.js.32char" />'
		});

		//设备厂商
		$("#agentComp").formValidator({
			onshow : '<s:text name="device.js.onshow.devicecom" />',
			onfocus : '<s:text name="device.js.onshow.devicecom" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 32,
			onerrormax : '<s:text name="device.js.32char" />'
		});

		//描述验证
		$("#agentDesc").formValidator({
			onshow : '<s:text name="device.js.onshow.deviceagentDesc" />',
			onfocus : '<s:text name="device.js.onshow.deviceagentDesc" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 200,
			onerrormax : '<s:text name="device.js.200char" />'
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

		changeGroup("<s:property value="deviceDomain.agentGroup"/>");
		changeType("<s:property value="deviceDomain.typeId"/>");
		changeSite("<s:property value="deviceDomain.siteId"/>");
		changeZone("<s:property value="deviceDomain.zoneId"/>");
	});
	
	function validNotEmpty(obj,id){
		var value = obj.value;
		if(value=="" ||value==null ||value==''){
			$("#"+id).removeClass().addClass("onError");
			$("#"+id).text('<s:text name="device.headerValue"/>');
			if(id == 'agentGroupTip'){
				$("#typeIdTip").removeClass().addClass("onError");
				$("#typeIdTip").text('<s:text name="device.headerValue"/>');
			}
			if(id == 'siteIdTip'){
				if($("#zoneIdTr").is(":visible")){
					$("#zoneIdTip").removeClass().addClass("onError");
					$("#zoneIdTip").text('<s:text name="device.headerValue"/>');
					changeZone("");
					$("#clusterIdTip").removeClass().addClass("onError");
					$("#clusterIdTip").text('<s:text name="device.headerValue"/>');
				}
				if($("#vmsIdTr").is(":visible")){
					$("#vmsIdTip").removeClass().addClass("onError");
					$("#vmsIdTip").text('<s:text name="device.headerValue"/>');
				}
				if($("#nvrIdTr").is(":visible")){
					$("#nvrIdTip").removeClass().addClass("onError");
					$("#nvrIdTip").text('<s:text name="device.headerValue"/>');
				}
				if($("#switchIdTr").is(":visible")){
					$("#switchIdTip").removeClass().addClass("onError");
					$("#switchIdTip").text('<s:text name="device.headerValue"/>');
				}
				if($("#switchPortTr").is(":visible")){
					$("#switchPortTip").removeClass().addClass("onError");
					$("#switchPortTip").text('<s:text name="device.js.onshow.switchPort"/>');
				}
			}
		}else{
			$("#"+id).removeClass().addClass("onCorrect");
			$("#"+id).text('<s:text name="device.js.oncorrect" />');
		}
	}
	
	function checkSwitchPort(obj,id){
		var value = obj.value;
		if(value=="" ||value==null ||value==''){
			$("#"+id).removeClass().addClass("onError");
			$("#"+id).text('<s:text name="device.js.onshow.switchPort"/>');
		}else{
			var reg=/^([1-9]*[1-9][0-9]*)*$/;
			if(!reg.test(value)){
				$("#"+id).removeClass().addClass("onError");
				$("#"+id).text('<s:text name="device.js.number"/>');
			}else{
				$("#"+id).removeClass().addClass("onCorrect");
				$("#"+id).text('<s:text name="device.js.oncorrect" />');
			}
		}
	}
	
	function checkDiskNum(obj,id){
		var value = obj.value;
		if(value=="" ||value==null ||value==''){
			$("#"+id).removeClass().addClass("onCorrect");
			$("#"+id).text('<s:text name="device.js.oncorrect" />');
		}else{
			var reg=/^[0-9]*$/;
			if(!reg.test(value)){
				$("#"+id).removeClass().addClass("onError");
				$("#"+id).text('<s:text name="device.js.number"/>');
			}else{
				$("#"+id).removeClass().addClass("onCorrect");
				$("#"+id).text('<s:text name="device.js.oncorrect" />');
			}
		}
	}
	
	function checkClPort(obj,id){
		var value = obj.value;
		if(value=="" ||value==null ||value==''){
			$("#"+id).removeClass().addClass("onError");
			$("#"+id).text('<s:text name="device.js.onshow.deviceclPort"/>');
		}else{
			var reg=/^([1-9]*[1-9][0-9]*)*$/;
			if(!reg.test(value)){
				$("#"+id).removeClass().addClass("onError");
				$("#"+id).text('<s:text name="device.js.number"/>');
			}else{
				$("#"+id).removeClass().addClass("onCorrect");
				$("#"+id).text('<s:text name="device.js.oncorrect" />');
			}
		}
	}
	
	function validate() {
		var agentGroup = $("#agentGroup").val();
		if(agentGroup ==""|| agentGroup==null ||agentGroup==''){
			$("#agentGroupTip").removeClass().addClass("onError");
			$("#agentGroupTip").text('<s:text name="device.headerValue"/>');
			return false;
		}
		<s:if test="#session.userInfo.roleType == 1">
			var siteId = $("#siteId").val();
			if(siteId ==""|| siteId==null ||siteId==''){
				$("#siteIdTip").removeClass().addClass("onError");
				$("#siteIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
		</s:if>
		if($("#zoneIdTr").is(":visible")){
			var zoneId = $("#zoneId").val();
			if(zoneId ==""|| zoneId==null ||zoneId==''){
				$("#zoneIdTip").removeClass().addClass("onError");
				$("#zoneIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
			var clusterId = $("#clusterId").val();
			if(clusterId ==""|| clusterId==null ||clusterId==''){
				$("#clusterIdTip").removeClass().addClass("onError");
				$("#clusterIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
		}
		if($("#nvrIdTr").is(":visible")){
			var nvrId = $("#nvrId").val();
			if(nvrId ==""|| nvrId==null ||nvrId==''){
				$("#nvrIdTip").removeClass().addClass("onError");
				$("#nvrIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
		}
		if($("#vmsIdTr").is(":visible")){
			var vmsId = $("#vmsId").val();
			if(vmsId ==""|| vmsId==null ||vmsId==''){
				$("#vmsIdTip").removeClass().addClass("onError");
				$("#vmsIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
		}
		if($("#switchIdTr").is(":visible")){
			var switchId = $("#switchId").val();
			if(switchId ==""|| switchId==null ||switchId==''){
				$("#switchIdTip").removeClass().addClass("onError");
				$("#switchIdTip").text('<s:text name="device.headerValue"/>');
				return false;
			}
		}
		var formCheck = $.formValidator.pageIsValid("1");
		if (formCheck) {
			var typeId = $("#typeId").val();
			var switchId = $("#switchId").val();
			var switchPort = $("#switchPort").val();
			var siteId = $("#siteId").val();
			var deviceIp = $("#deviceIp").val();
			var localTableIDRef = $("#localTableIDRef").val();
			var agentName = $("#deviceName").val();
			var agentId = '<s:property value="deviceDomain.agentId"/>';

			$
					.ajax({
						async : false,
						url : "deviceDuplicateCheckEdit.action",
						type : "post",
						dataType : "text",
						data : {
							'deviceDomain.switchId' : switchId,
							'deviceDomain.switchPort' : switchPort,
							'deviceDomain.siteId' : siteId,
							'deviceDomain.agentIp' : deviceIp,
							'deviceDomain.agentId' : agentId,
							'deviceDomain.typeId' : typeId,
							'deviceDomain.localTableIDRef' : localTableIDRef,
							'deviceDomain.agentName' : agentName
						},
						success : function(data) {
							if (data == "" || data == null) {
								$("#mainForm").submit();
							} else if (data == '<s:text name="device.js.duplicateIDCheck.isExist" />') {
								$("#localTableIDRefTip").text("");
								$("#localTableIDRefTip").removeClass()
										.addClass("onError");
								$("#localTableIDRefTip").text(data);
							} else if (data == '<s:text name="device.js.duplicateNameCheck.isExist" />') {
								$("#deviceNameTip").text("");
								$("#deviceNameTip").removeClass().addClass("onError");
								$("#deviceNameTip").text(data);
							} else if (data == '<s:text name="device.js.duplicatePortCheck.isExist" />') {
								$("#switchPortTip").text("");
								$("#switchPortTip").removeClass().addClass(
										"onError");
								$("#switchPortTip").text(data);
							} else if (data == '<s:text name="device.js.duplicateIPCheck.isExist" />') {
								$("#deviceIpTip").text("");
								$("#deviceIpTip").removeClass().addClass(
										"onError");
								$("#deviceIpTip").text(data);
							}
						}
					});
		}
	}

	function validateTextarea() {
		if ($("#agentDesc").val().length > 200) {
			$("#agentDesc").val($("#agentDesc").val().substring(0, 200));
		}
	}

	function changeSite(siteId) {
		var deviceDomain = {};
		deviceDomain.siteId = siteId;
		dwr.engine.setAsync(false);
		// 获取机房
		DeviceBeforeAddAction.getZoneIdList(deviceDomain, function callBack(
				data) {
			DWRUtil.removeAllOptions("zoneId");
			DWRUtil.addOptions("zoneId", data, "zoneId", "zoneName");
			DWRUtil.setValue("zoneId",
					"<s:property value="deviceDomain.zoneId"/>");
		});
		if(siteId ==""|| siteId==null ||siteId==''){
			changeZone("");
		}
		// 获取所属VMS
		DeviceBeforeAddAction.getVmsGroupList(deviceDomain, function callBack(
				data) {
			DWRUtil.removeAllOptions("vmsId");
			DWRUtil.addOptions("vmsId", data, "vmsId", "vmsName");
			DWRUtil.setValue("vmsId",
					"<s:property value="deviceDomain.vmsId"/>");
		});
		// 获取所属NVR
		DeviceBeforeAddAction.getNvrGroupList(deviceDomain, function callBack(
				data) {
			DWRUtil.removeAllOptions("nvrId");
			DWRUtil.addOptions("nvrId", data, "nvrId", "nvrName");
			DWRUtil.setValue("nvrId",
					"<s:property value="deviceDomain.nvrId"/>");
		});
		// 获取交换机
		DeviceBeforeAddAction.getSwitchGroupList(deviceDomain, function callBack(
				data) {
			DWRUtil.removeAllOptions("switchId");
			DWRUtil.addOptions("switchId", data, "switchId", "switchName");
			DWRUtil.setValue("switchId",
					"<s:property value="deviceDomain.switchId"/>");
		});
	}

	function changeZone(zoneId) {
		var deviceDomain = {};
		deviceDomain.zoneId = zoneId;
		dwr.engine.setAsync(false);
		// 获取机柜
		DeviceBeforeAddAction.getClusterIdList(deviceDomain, function callBack(
				data) {
			DWRUtil.removeAllOptions("clusterId");
			DWRUtil.addOptions("clusterId", data, "clusterId", "clusterName");
			DWRUtil.setValue("clusterId",
					"<s:property value="deviceDomain.clusterId"/>");
		});
	}

	function changeGroup(group) {
		dwr.engine.setAsync(false);
		// 获取设备类型
		DeviceBeforeAddAction.getTypeList(group, function callBack(data) {
			DWRUtil.removeAllOptions("typeId");
			DWRUtil.addOptions("typeId", data, "typeId", "typeName");
			DWRUtil.setValue("typeId",
					"<s:property value="deviceDomain.typeId"/>");
		});
	}

	/* --------采用subagent采集，故此方法暂时无用！Begin--------
	// 按照采集类型分类，针对snmp采集方式的设备再具体分类
	function changeType(type) {
		if (type == '47') {
			//采集方式，keyboard无采集方式
			$("#acquisitionMode").val("4");
			//端口，采集方式为snmp才有端口和共同体
			$("#clPortTr").hide();
			$("#clPort").val("");
			//共同体，采集方式为snmp才有端口和共同体
			$("#communityTr").hide();
			$("#community").val("");
			// 交换机端口
			switchPortShow();
			//硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
			$("#diskNumTr").hide();
			$("#diskNum").val("");
			//通道,Encoder（E4V）包含
			<s:iterator value="channelList">
			$("#channelName<s:text name="channelId" />Tr").hide();
			$("#channelName<s:text name="channelId" />").val("");
			</s:iterator>
			// 机房
			zoneShow();
			// 机柜
			clusterShow();
			//所属VMS
			vmsShow();
			//所属NVR
			$("#nvrIdTr").hide();
			$("#nvrId").val("");
		} else if (type == '43' || type == '44' || type == '45') {
			//采集方式，VMS和D4采集方式为ganglia
			$("#acquisitionMode").val("0");
			//端口，采集方式为snmp才有端口和共同体
			$("#clPortTr").hide();
			$("#clPort").val("");
			//共同体，采集方式为snmp才有端口和共同体
			$("#communityTr").hide();
			$("#community").val("");
			// 交换机端口
			switchPortShow();
			//硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
			$("#diskNumTr").hide();
			$("#diskNum").val("");
			//通道,Encoder（E4V）包含
			<s:iterator value="channelList">
			$("#channelName<s:text name="channelId" />Tr").hide();
			$("#channelName<s:text name="channelId" />").val("");
			</s:iterator>
			// 机房
			zoneShow();
			// 机柜
			clusterShow();
			//所属VMS
			if (type == '43' || type == '44') {
				$("#vmsIdTr").hide();
				$("#vmsId").val("");
			} else {
				vmsShow();
			}
			//所属NVR
			$("#nvrIdTr").hide();
			$("#nvrId").val("");
		} else if (type != '') {
			//采集方式，除keyboard、VMS、D4其他设备采集方式为snmp
			$("#acquisitionMode").val("1");
			//端口，采集方式为snmp才有端口和共同体
			clPortShow();
			//共同体，采集方式为snmp才有端口和共同体
			communityShow();
			if ((type == '31' || type == '32' || type == '42')
					|| (type == '33' || type == '34') || type == '35') {
				// 交换机端口
				switchPortShow();
				// 硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
				diskNumShow();
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				vmsShow();
				//所属NVR
				if (type == '35') {
					nvrShow();
				} else {
					$("#nvrIdTr").hide();
					$("#nvrId").val("");
				}
			} else if (type == '36' || type == '37' || type == '38'
					|| type == '39' || type == '40') {
				// 交换机端口
				switchPortShow();
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				$("#zoneIdTr").hide();
				$("#zoneId").val("");
				// 机柜
				$("#clusterIdTr").hide();
				$("#clusterId").val("");
				//所属VMS
				vmsShow();
				//所属NVR
				nvrShow();
			} else if (type == '41') {
				// 交换机端口，当设备为交换机时无此属性
				$("#switchPortTr").hide();
				$("#switchPort").val("");
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				$("#vmsIdTr").hide();
				$("#vmsId").val("");
				//所属NVR
				$("#nvrIdTr").hide();
				$("#nvrId").val("");
			} else if (type == '46') {
				<s:iterator value="channelList">
				$("#channelName<s:text name="channelId" />Tr").show();
				$("#channelName<s:text name="channelId" />")
						.formValidator({
							onshow : '<s:text name="device.js.32char" />',
							onfocus : '<s:text name="device.js.32char" />',
							oncorrect : '<s:text name="device.js.oncorrect" />'
						})
						.inputValidator(
								{
									min : 0,
									max : 32,
									empty : {
										leftempty : false,
										rightempty : false,
										emptyerror : '<s:text name="device.js.emptyerror" />'
									},
									onerror : '<s:text name="device.js.onshow.devicecommunity" />',
									onerrormax : '<s:text name="device.js.32char" />'
								});
				</s:iterator>

				// 交换机端口
				switchPortShow();
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				vmsShow();
				//所属NVR
				nvrShow();
			}
		}
	}
	--------采用subagent采集，故此方法暂时无用！End--------*/
	
	// 按照设备不同属性分类
	function changeType(type) {
		if (type == '47') {
			//采集方式，keyboard无采集方式
			$("#acquisitionMode").val("4");
			//端口，采集方式为snmp才有端口和共同体
			$("#clPortTr").hide();
			$("#clPort").val("");
			//共同体，采集方式为snmp才有端口和共同体
			$("#communityTr").hide();
			$("#community").val("");
			// 交换机
			switchShow();
			// 交换机端口
			switchPortShow();
			//硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
			$("#diskNumTr").hide();
			$("#diskNum").val("");
			//通道,Encoder（E4V）包含
			<s:iterator value="channelList">
			$("#channelName<s:text name="channelId" />Tr").hide();
			$("#channelName<s:text name="channelId" />").val("");
			</s:iterator>
			// 机房
			zoneShow();
			// 机柜
			clusterShow();
			//所属VMS
			vmsShow();
			//所属NVR
			$("#nvrIdTr").hide();
			$("#nvrId").val("");
		} else if (type == '43' || type == '44' || type == '45') {
			//采集方式，设备采集方式为snmp
			$("#acquisitionMode").val("1");
			//端口，采集方式为snmp才有端口和共同体
			clPortShow();
			//共同体，采集方式为snmp才有端口和共同体
			communityShow();
			// 交换机
			switchShow();
			// 交换机端口
			switchPortShow();
			//硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
			$("#diskNumTr").hide();
			$("#diskNum").val("");
			//通道,Encoder（E4V）包含
			<s:iterator value="channelList">
			$("#channelName<s:text name="channelId" />Tr").hide();
			$("#channelName<s:text name="channelId" />").val("");
			</s:iterator>
			// 机房
			zoneShow();
			// 机柜
			clusterShow();
			//所属VMS
			if (type == '43' || type == '44') {
				$("#vmsIdTr").hide();
				$("#vmsId").val("");
			} else {
				vmsShow();
			}
			//所属NVR
			$("#nvrIdTr").hide();
			$("#nvrId").val("");
		} else if (type != '') {
			//采集方式，设备采集方式为snmp
			$("#acquisitionMode").val("1");
			//端口，采集方式为snmp才有端口和共同体
			clPortShow();
			//共同体，采集方式为snmp才有端口和共同体
			communityShow();
			if ((type == '31' || type == '32' || type == '42')
					|| (type == '33' || type == '34') || type == '35') {
				// 交换机
				switchShow();
				// 交换机端口
				switchPortShow();
				// 硬盘个数，当设备类型为NVR、IP-SAN和Encoder（除E4V）时，设备包含硬盘个数属性
				diskNumShow();
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				vmsShow();
				//所属NVR
				if (type == '35') {
					nvrShow();
				} else {
					$("#nvrIdTr").hide();
					$("#nvrId").val("");
				}
			} else if (type == '36' || type == '37' || type == '38'
					|| type == '39' || type == '40') {
				// 交换机
				switchShow();
				// 交换机端口
				switchPortShow();
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				$("#zoneIdTr").hide();
				$("#zoneId").val("");
				// 机柜
				$("#clusterIdTr").hide();
				$("#clusterId").val("");
				//所属VMS
				vmsShow();
				//所属NVR
				nvrShow();
			} else if (type == '41') {
				// 交换机，当设备为交换机时无此属性
				$("#switchIdTr").hide();
				$("#switchId").val("");
				// 交换机端口，当设备为交换机时无此属性
				$("#switchPortTr").hide();
				$("#switchPort").val("");
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				//通道,Encoder（E4V）包含
				<s:iterator value="channelList">
					$("#channelName<s:text name="channelId" />Tr").hide();
					$("#channelName<s:text name="channelId" />").val("");
				</s:iterator>
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				$("#vmsIdTr").hide();
				$("#vmsId").val("");
				//所属NVR
				$("#nvrIdTr").hide();
				$("#nvrId").val("");
			} else if (type == '46') {
				<s:iterator value="channelList">
				$("#channelName<s:text name="channelId" />Tr").show();
				$("#channelName<s:text name="channelId" />")
						.formValidator({
							onshow : '<s:text name="device.js.32char" />',
							onfocus : '<s:text name="device.js.32char" />',
							oncorrect : '<s:text name="device.js.oncorrect" />'
						})
						.inputValidator(
								{
									min : 0,
									max : 32,
									empty : {
										leftempty : false,
										rightempty : false,
										emptyerror : '<s:text name="device.js.emptyerror" />'
									},
									onerror : '<s:text name="device.js.onshow.devicecommunity" />',
									onerrormax : '<s:text name="device.js.32char" />'
								});
				</s:iterator>

				// 交换机
				switchShow();
				// 交换机端口
				switchPortShow();
				// 硬盘个数
				$("#diskNumTr").hide();
				$("#diskNum").val("");
				// 机房
				zoneShow();
				// 机柜
				clusterShow();
				//所属VMS
				vmsShow();
				//所属NVR
				nvrShow();
			}
		}
	}

	//所属VMS
	function vmsShow() {
		$("#vmsIdTr").show();
		$("#vmsIdTip").removeClass().addClass("onShow");
		$("#vmsIdTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#vmsId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 1000,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
		*/
	}

	//采集端口
	function clPortShow() {
		$("#clPortTr").show();
		$("#clPortTip").removeClass().addClass("onShow");
		$("#clPortTip").text('<s:text name="device.js.onshow.deviceclPort"/>');
		/*
		$("#clPort").formValidator({
			onshow : '<s:text name="device.js.onshow.deviceclPort" />',
			onfocus : '<s:text name="device.js.onshow.deviceclPort" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).regexValidator({
			regexp : "^([1-9]*[1-9][0-9]*)*$",
			onerror : '<s:text name="device.js.number" />'
		}).inputValidator({
			min : 1,
			max : 5,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerrormax : '<s:text name="device.js.5char" />'
		});
		*/
	}

	//共同体
	function communityShow() {
		$("#communityTr").show();
		$("#communityTip").removeClass().addClass("onShow");
		$("#communityTip").text('<s:text name="device.js.onshow.devicecommunity"/>');
		/*
		$("#community").formValidator({
			onshow : '<s:text name="device.js.onshow.devicecommunity" />',
			onfocus : '<s:text name="device.js.onshow.devicecommunity" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 20,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.js.onshow.devicecommunity" />',
			onerrormax : '<s:text name="device.js.20char" />'
		});
		*/
	}

	// 交换机
	function switchShow() {
		/*$("#switchIdTr").show();
		$("#switchIdTip").removeClass().addClass("onShow");
		$("#switchIdTip").text('<s:text name="device.headerValue"/>');*/
		
		$("#switchIdTr").show();
		$("#switchId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 1000,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
	}
	
	// 交换机端口
	function switchPortShow() {
		$("#switchPortTr").show();
		$("#switchPortTip").removeClass().addClass("onShow");
		$("#switchPortTip").text('<s:text name="device.js.onshow.switchPort"/>');
		/*
		$("#switchPort").formValidator({
			onshow : '<s:text name="device.js.onshow.switchPort" />',
			onfocus : '<s:text name="device.js.onshow.switchPort" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).regexValidator({
			regexp : "^([1-9]*[1-9][0-9]*)*$",
			onerror : '<s:text name="device.js.number" />'
		}).inputValidator({
			min : 1,
			max : 5,
			onerrormax : '<s:text name="device.js.5char" />'
		});
		*/
	}

	// 硬盘个数
	function diskNumShow() {
		$("#diskNumTr").show();
		$("#diskNumTip").removeClass().addClass("onShow");
		$("#diskNumTip").text('<s:text name="device.js.onshow.diskNum"/>');
		/*
		$("#diskNum").formValidator({
			onshow : '<s:text name="device.js.onshow.diskNum" />',
			onfocus : '<s:text name="device.js.onshow.diskNum" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).regexValidator({
			regexp : "^[0-9]*$",
			onerror : '<s:text name="device.js.number" />'
		}).inputValidator({
			min : 0,
			max : 2,
			onerrormax : '<s:text name="device.js.2char" />'
		});
		*/
	}

	//机房
	function zoneShow() {
		$("#zoneIdTr").show();
		$("#zoneIdTip").removeClass().addClass("onShow");
		$("#zoneIdTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#zoneId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 1000,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
		*/
	}

	//机柜
	function clusterShow() {
		$("#clusterIdTr").show();
		$("#clusterIdTip").removeClass().addClass("onShow");
		$("#clusterIdTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#clusterId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 1000,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
		*/
	}

	//所属NVR
	function nvrShow() {
		$("#nvrIdTr").show();
		$("#nvrIdTip").removeClass().addClass("onShow");
		$("#nvrIdTip").text('<s:text name="device.headerValue"/>');
		/*
		$("#nvrId").formValidator({
			onshow : '<s:text name="device.headerValue" />',
			onfocus : '<s:text name="device.headerValue" />',
			oncorrect : '<s:text name="device.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 1000,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="device.js.emptyerror" />'
			},
			onerror : '<s:text name="device.headerValue" />',
			onerrormax : '<s:text name="device.headerValue" />'
		});
		*/
	}

	//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
</script>
<%-- 当前位置 --%>
<div class="rightToolbar">
	<div class="rightToolbarCrumbs">
		<h2 class="sec-label">
			<s:text name="function.title" />
		</h2>
		<ul class="bread-cut">
			<li><s:text name="resource.title.show.ci" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="function.title" /></li>
			<li><s:text name="common.lable.arrow" /></li>
			<li><s:text name="common.title.edit"></s:text>
		</ul>
	</div>
</div>
<!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
		<form id="mainForm" method="post" action="deviceUpdate.action">
		<s:set name="channelList" value="channelList"></s:set>
			<s:hidden name="deviceDomain.agentId"></s:hidden>
			<div class="formLabel">
				<s:text name="common.lable.baseInfo" />
				<s:text name="common.lable.line" />
			</div>
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<s:hidden id="acquisitionMode" name="deviceDomain.acquisitionMode" />
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.localTableIDRef" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="localTableIDRef" name="deviceDomain.localTableIDRef"
						value="<s:property value="deviceDomain.localTableIDRef"/>"
						class="input" maxlength="32" /></td>
					<td class="tdVad" valign="top"><div id="localTableIDRefTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.deviceName" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="deviceName" name="deviceDomain.agentName"
						value="<s:property value="deviceDomain.agentName"/>" class="input"
						maxlength="32" /></td>
					<td class="tdVad" valign="top"><div id="deviceNameTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.agentGroup" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><s:select
							list="deviceGroupList" listKey="agentGroup" listValue="groupName"
							name="deviceDomain.agentGroup" id="agentGroup"
							cssClass="newSelect" headerKey=""
							headerValue="%{getText('common.lable.select')}"
							onblur="validNotEmpty(this,'agentGroupTip');"
							onchange="changeGroup(this.value)"></s:select></td>
					<td class="tdVad" valign="top"><div id="agentGroupTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.typeName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.typeId" id="typeId"
						onclick="changeType(this.value);"
						onchange="changeType(this.value);" /></td>
					<td class="tdVad" valign="top"><div id="typeIdTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="device.deviceType" /> <s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="deviceType" name="deviceDomain.deviceType"
						value="<s:property value="deviceDomain.deviceType"/>"
						class="input" maxlength="32" /></td>
					<td class="tdVad" valign="top"><div id="deviceTypeTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="device.agentComp" /> <s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="agentComp" name="deviceDomain.agentComp"
						value="<s:property value="deviceDomain.agentComp"/>" class="input"
						maxlength="32" /></td>
					<td class="tdVad" valign="top"><div id="agentCompTip"></div></td>
				</tr>
				<tr id="diskNumTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="device.diskNum" /> <s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="diskNum" name="deviceDomain.diskNum"
						value="<s:property value="deviceDomain.diskNum"/>" class="input"
						maxlength="2" onblur="checkDiskNum(this,'diskNumTip');" onkeyup="checkDiskNum(this,'diskNumTip')"/></td>
					<td class="tdVad" valign="top"><div id="diskNumTip"></div></td>
				</tr>
				<s:iterator value="channelList">
					<tr id="channelName<s:text name="channelId" />Tr"
						style="display: none;">
						<td class="tdLabel" align="right" valign="top"><s:text
								name="device.channelId" /> <s:property value="channelId" /> <s:text
								name="common.lable.point" /></td>
						<td class="tdInput" valign="top"><input type="text"
							id="channelName<s:text name="channelId" />"
							name="deviceDomain.channelName"
							value="<s:property value="channelName" />"
							class="input" maxlength="32" /> <s:hidden
								name="deviceDomain.channelId" value="%{channelId}" /></td>
						<td class="tdVad" valign="top"><div
								id="channelName<s:text name="channelId" />Tip"></div></td>
					</tr>
				</s:iterator>
			</table>
			<div class="formLabel">
				<s:text name="common.lable.serviceInfo" />
				<s:text name="common.lable.line" />
			</div>
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<s:if test="#session.userInfo.roleType == 1">
					<tr>
						<td class="tdLabel" align="right" valign="top"><s:text
								name="common.red.point" /> <s:text name="device.siteName" /> <s:text
								name="common.lable.point" /></td>
						<td class="tdInput" valign="top"><s:select list="siteList"
								listKey="siteId" listValue="siteName" id="siteId"
								name="deviceDomain.siteId" cssClass="newSelect" headerKey=""
								headerValue="%{getText('common.lable.select')}"
								onblur="validNotEmpty(this,'siteIdTip');"
								onchange="changeSite(this.value)">
							</s:select></td>
						<td class="tdVad" valign="top"><div id="siteIdTip"></div></td>
					</tr>
				</s:if>
				<s:elseif test="#session.userInfo.roleType == 2">
					<tr>
						<td class="tdLabel" align="right" valign="top"><s:text
								name="device.siteName" /> <s:text name="common.lable.point" /></td>
						<td class="tdInput" valign="top" style="color: #666;"><s:property
								value="deviceDomain.siteName" /> <input type="hidden"
							id="siteId" name="deviceDomain.siteId"
							value="<s:property value="deviceDomain.siteId"/>" /></td>
					</tr>
				</s:elseif>
				<tr id="zoneIdTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.zoneName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.zoneId" id="zoneId"
						onblur="validNotEmpty(this,'zoneIdTip');"
						onchange="changeZone(this.value);" /></td>
					<td class="tdVad" valign="top"><div id="zoneIdTip"></div></td>
				</tr>
				<tr id="clusterIdTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.clusterName" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.clusterId" id="clusterId" onblur="validNotEmpty(this,'clusterIdTip');"/></td>
					<td class="tdVad" valign="top"><div id="clusterIdTip"></div></td>
				</tr>
				<tr id="vmsIdTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.vmsName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.vmsId" id="vmsId" onblur="validNotEmpty(this,'vmsIdTip');"/></td>
					<td class="tdVad" valign="top"><div id="vmsIdTip"></div></td>
				</tr>
				<tr id="nvrIdTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.nvrName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.nvrId" id="nvrId" onblur="validNotEmpty(this,'nvrIdTip');"/></td>
					<td class="tdVad" valign="top"><div id="nvrIdTip"></div></td>
				</tr>
				<tr id="switchIdTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.switchName" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><select
						name="deviceDomain.switchId" id="switchId" onblur="validNotEmpty(this,'nvrIdTip');"/></td>
					<td class="tdVad" valign="top"><div id="switchIdTip"></div></td>
				</tr>
				<tr id="switchPortTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.switchPort" />
						<s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="switchPort" name="deviceDomain.switchPort"
						value="<s:property value="deviceDomain.switchPort"/>"
						class="input" maxlength="5" onblur="checkSwitchPort(this,'switchPortTip');" onkeyup="checkSwitchPort(this,'switchPortTip')"/></td>
					<td class="tdVad" valign="top"><div id="switchPortTip"></div></td>
				</tr>
			</table>
			<div class="formLabel">
				<s:text name="common.lable.agentInfo" />
				<s:text name="common.lable.line" />
			</div>
			<table class="addTable" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.deviceIp" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="deviceIp" name="deviceDomain.agentIp"
						value="<s:property value="deviceDomain.agentIp"/>" class="input"
						maxlength="15" /></td>
					<td class="tdVad" valign="top"><div id="deviceIpTip"></div></td>
				</tr>
				<tr id="clPortTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.clPort" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="clPort" name="deviceDomain.clPort"
						value="<s:property value="deviceDomain.clPort"/>" class="input"
						maxlength="5" onblur="checkClPort(this,'clPortTip');" onkeyup="checkClPort(this,'clPortTip')"/></td>
					<td class="tdVad" valign="top"><div id="clPortTip"></div></td>
				</tr>
				<tr id="communityTr" style="display: none;">
					<td class="tdLabel" align="right" valign="top"><s:text
							name="common.red.point" /> <s:text name="device.community" /> <s:text
							name="common.lable.point" /></td>
					<td class="tdInput" valign="top"><input type="text"
						id="community" name="deviceDomain.community"
						value="<s:property value="deviceDomain.community"/>" class="input"
						maxlength="20" onblur="validNotEmpty(this,'communityTip');" onkeyup="validNotEmpty(this,'communityTip')"/></td>
					<td class="tdVad" valign="top"><div id="communityTip"></div></td>
				</tr>
				<tr>
					<td class="tdLabel" align="right" valign="top"><s:text
							name="device.agentDesc" /> <s:text name="common.lable.point" /></td>
					<td class="tdInput" valign="top">
						<div>
							<label for="textarea"></label>
							<textarea name="deviceDomain.agentDesc" id="agentDesc"
								value="<s:property value="deviceDomain.agentDesc"/>"
								class="newTextarea" onkeydown="validateTextarea();"
								onkeyup="validateTextarea();"></textarea>
						</div>
					</td>
					<td class="tdVad" valign="top"><div id="agentDescTip"></div></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 提示信息start -->
	<div class="messages" style="top: 25px; left: 37%"">
		<div id="msgTip" class="msgErrors"></div>
	</div>
	<!-- 提示信息end-->
	<%-- 按钮栏 --%>
	<div class="serverEventButton">
		<ul class="rightToolButtonjhjFwSj">
			<li><a href="#" class="buttonFwSj"
				onclick="validate();return false;"><s:property
						value="getText('common.button.add')" /></a></li>
			<li><a href="deviceList.action" class="buttonGrey"><s:property
						value="getText('common.button.cancel')" /></a></li>
		</ul>
	</div>
</div>
<!--滚动条 scroll end-->