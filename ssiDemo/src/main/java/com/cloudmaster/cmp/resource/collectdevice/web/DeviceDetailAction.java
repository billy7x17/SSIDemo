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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 设备详细
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceDetailAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 设备信息
     */
    private DeviceDomain deviceDomain;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * 是否是拓扑图中的设备详情
     */
    private String isTopo;

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(DeviceDetailAction.class);

    public String detail() {
        String opParam[] = { getText("device.deviceName") + ": " + deviceDomain.getAgentName() };
        logger.info(getText("function.title") + getText("log.detail.begin"));
        try {

            // 获取设备信息
            deviceDomain = (DeviceDomain) this.ibatisDAO.getSingleRecord("DeviceInfo.detail",
                    deviceDomain.getAgentId());
            // 获取设备采集频率
            String agentFrequency = getFrequency();
            // 根据ganglia采集频率，获取设备状态。查询设备是否有ping告警，如果无则为在线（0），否则离线（1）
            deviceDomain.setAgentFrequency(agentFrequency);
            String statusValue = (String) ibatisDAO.getSingleRecord("DeviceInfo.getResourceStatus",
                    deviceDomain);
            deviceDomain.setStatus(statusValue);
            // 如果设备类型为Encoder(E4V)，获取通道信息
            if (("46").equals(deviceDomain.getTypeId())) {
                List<DeviceDomain> channelList = ibatisDAO.getData("DeviceInfo.getChannels",
                        deviceDomain);
                deviceDomain.setChannelList(channelList);
            }
            if (deviceDomain == null) {
                errorMsg = getText("mess.data.not.found");
                logger.info(errorMsg);
            }
            operationInfo = getText("oplog.detail.success", opParam);
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (SQLException e) {
            errorMsg = this.getText("common.message.detailError")
                    + getText("common.message.systemError");
            logger.info(getText("resource.site.title") + getText("log.detail.error"), e);
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    /**
     * 读取ganglia采集频率
     */
    public String getFrequency() {
        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        String agentFrequency = "";
        try {
            prop.load(classLoader.getResourceAsStream("conf/other/System.properties"));
            agentFrequency = prop.getProperty("agentFrequency");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agentFrequency;
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

    /**
     * @return the isTopo
     */
    public String getIsTopo() {
        return isTopo;
    }

    /**
     * @param isTopo the isTopo to set
     */
    public void setIsTopo(String isTopo) {
        this.isTopo = isTopo;
    }

}
