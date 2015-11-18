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
 * 添加设备
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceAddAction extends OperationLogAction implements IAuthIdGetter {

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
    private static LogService logger = LogService.getLogger(DeviceAddAction.class);

    public String add() {
        String opParam[] = { getText("device.deviceName") + ": " + deviceDomain.getAgentName() };
        logger.info(getText("function.title") + getText("log.add.begin"));

        try {
            // 添加设备信息
            DeviceDomain bean = getDeviceDomain();
            getIbatisDAO().insertData("DeviceInfo.addDeviceDomain", bean);

            // 同时向zone_cluster_instance_tab表入数据，供cmagent读取
            // 获取设备ID
            String agentId = (String) ibatisDAO.getSingleRecord("DeviceInfo.getCurrPara", null);
            bean.setZciId(agentId);
            bean.setcMDBIDRef(agentId);
            // 获取资源类型
            String type = bean.getTypeId();
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
            bean.setResourceType("CIDC-RT-" + group);
            bean.setLeafName(bean.getAgentIp());
            bean.setLocalTableName(bean.getAgentName());
            ibatisDAO.insertData("DeviceInfo.insertZoneClusterInstance", bean);

            // 如果设备是Encoder(E4V)包含通道名称,入库
            if ("46".equals(type)
                    && (null != bean.getChannelId() && !("").equals(bean.getChannelId()))
                    && (null != bean.getChannelName() && !("").equals(bean.getChannelName()))) {
                String[] channelIdStr = bean.getChannelId().split(", ");
                String[] channelNameStr = bean.getChannelName().split(", ");
                for (int i = 0; i < channelNameStr.length; i++) {
                    // if (channelNameStr[i] != null && channelNameStr[i] != "" &&
                    // !(" ").equals(channelNameStr[i])) {
                    bean.setAgentId(agentId);
                    bean.setChannelName(channelNameStr[i]);
                    bean.setChannelId(channelIdStr[i]);
                    ibatisDAO.insertData("DeviceInfo.insertChannel", bean);
                    // }
                }
            }
            msg = getText("common.message.addSuccess");
            operationInfo = getText("oplog.add.success", opParam);
            logger.info(getText("function.title") + getText("log.add.end"));
        } catch (Exception e) {
            forward = "INPUT";
            logger.info(getText("function.title") + getText("common.message.addError"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    /**
     * 页面ajax校验是否存在重复记录，设备编码重复
     */
    public void deviceDuplicateIDCheck() {
        int result;
        try {
            result = ibatisDAO.getCount("DeviceInfo.existIDCount", deviceDomain);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            if (result != 0) {
                pw.write(getText("device.js.duplicateIDCheck.isExist"));
            } else {
                pw.write("");
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
    }

    /**
     * 页面ajax校验是否存在重复记录，采集IP重复
     */
    public void deviceDuplicateIPCheck() {
        int result;
        try {
            result = ibatisDAO.getCount("DeviceInfo.existIPCount", deviceDomain);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            if (result != 0) {
                pw.write(getText("device.js.duplicateIPCheck.isExist"));
            } else {
                pw.write("");
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
    }

    /**
     * 页面ajax校验是否存在重复记录，交换机端口和设备名称重复
     */
    public void deviceDuplicateCheckAdd() {
        int nameResult;
        int portResult;
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            nameResult = ibatisDAO.getCount("DeviceInfo.existNameCount", deviceDomain);
            if (!("41").equals(deviceDomain.getTypeId())) {
                portResult = ibatisDAO.getCount("DeviceInfo.existPortCount", deviceDomain);
                if (nameResult != 0) {
                    pw.write(getText("device.js.duplicateNameCheck.isExist"));
                } else if (portResult != 0) {
                    pw.write(getText("device.js.duplicatePortCheck.isExist"));
                } else {
                    pw.write("");
                }
            } else {
                if (nameResult != 0) {
                    pw.write(getText("device.js.duplicateNameCheck.isExist"));
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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
