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
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改初始化
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceBeforeUpdateAction extends BaseAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(DeviceBeforeUpdateAction.class);

    /**
     * 设备ID
     */
    private String agentId;

    /**
     * 设备信息
     */
    private DeviceDomain deviceDomain;

    /**
     * 通道
     */
    private List<DeviceDomain> channelList;

    /**
     * 所属站点
     */
    private List<DeviceDomain> siteList;

    /**
     * 所属机房
     */
    private List<DeviceDomain> zoneList;

    /**
     * 所属机柜
     */
    private List<DeviceDomain> clusterList;

    /**
     * 设备分组
     */
    private List<DeviceDomain> deviceGroupList;

    /**
     * 设备类型
     */
    private List<DeviceDomain> deviceTypeList;

    /**
     * 所属VMS
     */
    private List<DeviceDomain> vmsList;

    /**
     * 所属NVR
     */
    private List<DeviceDomain> nvrList;

    @SuppressWarnings("unchecked")
    public String beforeUpdate() {
        logger.info(getText("function.title") + getText("before.edit.function"));
        try {
            // 获取设备信息
            deviceDomain = (DeviceDomain) ibatisDAO.getSingleRecord("DeviceInfo.detail", agentId);

            // 获取所属站点(2号线)
            DeviceDomain temp = new DeviceDomain();
            temp.setLineNum("2");
            siteList = this.getIbatisDAO().getData("DeviceInfo.getSites", temp);

            // 获取设备分组
            getGroupList();
            // 获取通道信息
            if ("46".equals(deviceDomain.getTypeId())) {
                channelList = this.getIbatisDAO().getData("DeviceInfo.getChannels", deviceDomain);
                if(channelList.size() == 0){
                    channelList = this.getIbatisDAO().getData("DeviceInfo.getChannelIds", null);
                }
            } else {
                channelList = this.getIbatisDAO().getData("DeviceInfo.getChannelIds", null);
            }
        } catch (Exception e) {
            errorMsg = getText("common.message.editError");
            logger.info(errorMsg, e);
        }
        return forward;
    }

    /**
     * 获取设备分组
     */
    private void getGroupList() throws SQLException {
        deviceGroupList = ibatisDAO.getData("DeviceInfo.getDeviceGroupList", null);
        for (DeviceDomain temp : deviceGroupList) {
            if (("NVR").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.NVR"));
            } else if (("IP-SAN").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.IP-SAN"));
            } else if (("Encoder").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Encoder"));
            } else if (("IPC").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.IPC"));
            } else if (("Switch").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Switch"));
            } else if (("VMS").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.VMS"));
            } else if (("D4").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.D4"));
            } else if (("Keyboard").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Keyboard"));
            }
        }
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

    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }

    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

    /**
     * @return Returns the forward.
     */
    public String getForward() {
        return forward;
    }

    /**
     * @param forward The forward to set.
     */
    public void setForward(String forward) {
        this.forward = forward;
    }

    /**
     * @return Returns the siteList.
     */
    public List<DeviceDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList The siteList to set.
     */
    public void setSiteList(List<DeviceDomain> siteList) {
        this.siteList = siteList;
    }

    /**
     * @return Returns the zoneList.
     */
    public List<DeviceDomain> getZoneList() {
        return zoneList;
    }

    /**
     * @param zoneList The zoneList to set.
     */
    public void setZoneList(List<DeviceDomain> zoneList) {
        this.zoneList = zoneList;
    }

    /**
     * @return Returns the clusterList.
     */
    public List<DeviceDomain> getClusterList() {
        return clusterList;
    }

    /**
     * @param clusterList The clusterList to set.
     */
    public void setClusterList(List<DeviceDomain> clusterList) {
        this.clusterList = clusterList;
    }

    /**
     * @return Returns the deviceGroupList.
     */
    public List<DeviceDomain> getDeviceGroupList() {
        return deviceGroupList;
    }

    /**
     * @param deviceGroupList The deviceGroupList to set.
     */
    public void setDeviceGroupList(List<DeviceDomain> deviceGroupList) {
        this.deviceGroupList = deviceGroupList;
    }

    /**
     * @return Returns the vmsList.
     */
    public List<DeviceDomain> getVmsList() {
        return vmsList;
    }

    /**
     * @param vmsList The vmsList to set.
     */
    public void setVmsList(List<DeviceDomain> vmsList) {
        this.vmsList = vmsList;
    }

    /**
     * @return Returns the nvrList.
     */
    public List<DeviceDomain> getNvrList() {
        return nvrList;
    }

    /**
     * @param nvrList The nvrList to set.
     */
    public void setNvrList(List<DeviceDomain> nvrList) {
        this.nvrList = nvrList;
    }

    /**
     * @param deviceTypeList The deviceTypeList to set.
     */
    public void setDeviceTypeList(List<DeviceDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    /**
     * @return Returns the deviceTypeList.
     */
    public List<DeviceDomain> getDeviceTypeList() {
        return deviceTypeList;
    }

    /**
     * @return Returns the channelList.
     */
    public List<DeviceDomain> getChannelList() {
        return channelList;
    }

    /**
     * @param channelList The channelList to set.
     */
    public void setChannelList(List<DeviceDomain> channelList) {
        this.channelList = channelList;
    }

}
