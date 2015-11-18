/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.resource.collectdevice.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改设备
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceUpdateAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * 设备信息
     */
    private DeviceDomain deviceDomain;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceUpdateAction.class);

    public String update() {
        String opParam[] = { getText("device.deviceName") + ": " + deviceDomain.getAgentName() };
        logger.info(getText("function.title") + getText("edit.function"));

        try {
            // 修改设备信息
            getIbatisDAO().updateData("DeviceInfo.updateDeviceDomain", deviceDomain);

            // 同时向zone_cluster_instance_tab表更新数据，供cmagent读取
            deviceDomain.setZciId(deviceDomain.getAgentId());
            // 获取资源类型
            String type = deviceDomain.getTypeId();
            String group = "";
            if ("31".equals(type) || "32".equals(type) || "42".equals(type)) {
                group = "NVR";
            } else if ("33".equals(type) || "34".equals(type)) {
                group = "IPSAN";
            } else if ("35".equals(type) || "46".equals(type)) {
                group = "Encoder";
            } else if ("36".equals(type) || "37".equals(type) || "38".equals(type)
                    || "39".equals(type) || "40".equals(type)) {
                group = "IPC";
            } else if ("41".equals(type)) {
                group = "Switch";
            } else if ("43".equals(type) || "44".equals(type)) {
                group = "VMS";
            } else if ("45".equals(type)) {
                group = "D4";
            } else if ("47".equals(type)) {
                group = "Keyword";
            }
            deviceDomain.setResourceType("CIDC-RT-" + group);
            deviceDomain.setLeafName(deviceDomain.getAgentIp());
            deviceDomain.setLocalTableName(deviceDomain.getAgentName());
            ibatisDAO.updateData("DeviceInfo.editZoneClusterInstance", deviceDomain);

            // 如果设备是Encoder(E4V)包含通道名称,入库
            if ("46".equals(type)
                    && (null != deviceDomain.getChannelId() && !("").equals(deviceDomain
                            .getChannelId()))
                    && (null != deviceDomain.getChannelName() && !("").equals(deviceDomain
                            .getChannelName()))) {
                // 先删除通道信息
                ibatisDAO.deleteData("DeviceInfo.deleteChannel", deviceDomain.getAgentId());
                String[] channelIdStr = deviceDomain.getChannelId().split(", ");
                String[] channelNameStr = deviceDomain.getChannelName().split(", ");
                for (int i = 0; i < channelNameStr.length; i++) {
                    // if (channelNameStr[i] != null && channelNameStr[i] != "" &&
                    // !(" ").equals(channelNameStr[i])) {
                    // 批量导入新通道信息
                    deviceDomain.setChannelName(channelNameStr[i]);
                    deviceDomain.setChannelId(channelIdStr[i]);
                    ibatisDAO.insertData("DeviceInfo.insertChannel", deviceDomain);
                    // }
                }
            }

            msg = this.getText("common.message.editSuccess");
            operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            forward = "INPUT";
            errorMsg = this.getText("common.message.editError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            logger.info(errorMsg, e);
        }
        return forward;
    }

    /**
     * 页面ajax校验是否存在重复记录，采集IP和交换机端口重复
     */
    public void deviceDuplicateCheckEdit() {
        int idResult;
        int nameResult;
        int ipResult;
        int portResult;
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            idResult = ibatisDAO.getCount("DeviceInfo.existIDCount", deviceDomain);
            nameResult = ibatisDAO.getCount("DeviceInfo.existNameCount", deviceDomain);
            ipResult = ibatisDAO.getCount("DeviceInfo.existIPCount", deviceDomain);
            if (!("41").equals(deviceDomain.getTypeId())) {
                portResult = ibatisDAO.getCount("DeviceInfo.existPortCount", deviceDomain);
                if (idResult != 0) {
                    pw.write(getText("device.js.duplicateIDCheck.isExist"));
                } else if (nameResult != 0) {
                    pw.write(getText("device.js.duplicateNameCheck.isExist"));
                } else if (portResult != 0) {
                    pw.write(getText("device.js.duplicatePortCheck.isExist"));
                } else if (ipResult != 0) {
                    pw.write(getText("device.js.duplicateIPCheck.isExist"));
                } else {
                    pw.write("");
                }
            } else {
                if (idResult != 0) {
                    pw.write(getText("device.js.duplicateIDCheck.isExist"));
                } else if (nameResult != 0) {
                    pw.write(getText("device.js.duplicateNameCheck.isExist"));
                } else if (ipResult != 0) {
                    pw.write(getText("device.js.duplicateIPCheck.isExist"));
                } else {
                    pw.write("");
                }
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }

    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

}
