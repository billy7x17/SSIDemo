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

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除设备信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceDeleteAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 设备ID
     */
    private String agentId;

    /**
     * 设备信息
     */
    private List<DeviceDomain> deviceList;

    /**
     * 设备信息
     */
    private DeviceDomain deviceDomain;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(DeviceDeleteAction.class);

    public String delete() {
        String opParam[] = { getText("device.deviceName") + ": " + deviceDomain.getAgentName() };
        logger.info(getText("function.title") + getText("delete.function"));
        try {
            String typeId = ((DeviceDomain) ibatisDAO.getSingleRecord("DeviceInfo.detail", agentId))
                    .getTypeId();
            // 删除设备
            ibatisDAO.deleteData("DeviceInfo.deleteDeviceById", agentId);
            // 删除zoneclusterinstance表数据
            ibatisDAO.deleteData("DeviceInfo.deleteZoneClusterInstance", agentId);
            // 如果是编码器（E4V），需要删除通道信息
            if ("46".equals(typeId)) {
                // 删除通道信息
                ibatisDAO.deleteData("DeviceInfo.deleteChannel", agentId);
            }
            msg = getText("common.message.delSuccess");
            logger.info(getText("function.title") + getText("log.delete.end"));
            operationInfo = getText("oplog.delete.success", opParam);
        } catch (SQLException e) {
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setDeviceList(List<DeviceDomain> deviceList) {
        this.deviceList = deviceList;
    }

    public List<DeviceDomain> getDeviceList() {
        return deviceList;
    }

    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }

    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }
}
