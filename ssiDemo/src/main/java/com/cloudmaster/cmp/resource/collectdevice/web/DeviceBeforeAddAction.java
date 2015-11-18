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
import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 添加设备初始化
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceBeforeAddAction extends BaseAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceBeforeAddAction.class);

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

    /**
     * 交换机
     */
    private List<DeviceDomain> switchList;

    /**
     * 设备
     */
    private DeviceDomain deviceDomain = new DeviceDomain();

    @SuppressWarnings("unchecked")
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        try {
            DeviceDomain device = new DeviceDomain();
            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            // 当用户为站点管理员，添加站点时默认为当前站点
            if (("1").equals(user.getRoleType())) {
                // 获取所属站点(2号线)
                device.setLineNum("2");
                siteList = this.getIbatisDAO().getData("DeviceInfo.getSites", device);
            } else if (("2").equals(user.getRoleType())) {
                String siteId = String.valueOf(user.getSiteId());
                device.setSiteId(siteId);
                String siteName = ((DeviceDomain) this.getIbatisDAO().getSingleRecord(
                        "DeviceInfo.getSites", device)).getSiteName();
                deviceDomain.setSiteId(siteId);
                deviceDomain.setSiteName(siteName);
            }
            // 获取设备分组
            getGroupList();
            // 获取通道信息
            channelList = this.getIbatisDAO().getData("DeviceInfo.getChannelIds", null);
            logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError")
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeAdd.error"), e);
            forward = "error";
        }
        return forward;
    }

    /**
     * 获取机房信息
     */
    public List<DeviceDomain> getZoneIdList(DeviceDomain device) {
        try {
            zoneList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setZoneId("");
            // info.setZoneName(getText("common.lable.select"));
            info.setZoneName("-----------请选择-----------");
            zoneList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO()
                    .getData("DeviceInfo.getZones", device);
            zoneList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.zone.action.error");
            logger.info(errorMsg + e, e);
        }
        return zoneList;
    }

    /**
     * 获取机柜信息
     */
    public List<DeviceDomain> getClusterIdList(DeviceDomain device) {
        try {
            clusterList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setClusterId("");
            // info.setClusterName(getText("common.lable.select"));
            info.setClusterName("-----------请选择-----------");
            clusterList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO().getData("DeviceInfo.getClusters",
                    device);
            clusterList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.cluster.action.error");
            logger.info(errorMsg + e, e);
        }
        return clusterList;
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

    /**
     * 获取设备类型信息
     */
    public List<DeviceDomain> getTypeList(String agentGroup) {
        try {
            deviceTypeList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setTypeId("");
            // info.setTypeName(getText("common.lable.select"));
            info.setTypeName("-----------请选择-----------");
            deviceTypeList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO().getData(
                    "DeviceInfo.getDeviceTypeList", agentGroup);
            deviceTypeList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.deviceType.action.error");
            logger.info(errorMsg + e, e);
        }
        return deviceTypeList;
    }

    /**
     * 获取所属VMS
     */
    public List<DeviceDomain> getVmsGroupList(DeviceDomain device) {
        try {
            vmsList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setVmsId("");
            // info.setVmsName(getText("common.lable.select"));
            info.setVmsName("-----------请选择-----------");
            vmsList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO().getData("DeviceInfo.getVms", device);
            vmsList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.vms.action.error");
            logger.info(errorMsg + e, e);
        }
        return vmsList;
    }

    /**
     * 获取所属NVR
     */
    public List<DeviceDomain> getNvrGroupList(DeviceDomain device) {
        try {
            nvrList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setNvrId("");
            // info.setNvrName(getText("common.lable.select"));
            info.setNvrName("-----------请选择-----------");
            nvrList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO().getData("DeviceInfo.getNvrs", device);
            nvrList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.nvr.action.error");
            logger.info(errorMsg + e, e);
        }
        return nvrList;
    }

    /**
     * 获取交换机
     */
    public List<DeviceDomain> getSwitchGroupList(DeviceDomain device) {
        try {
            switchList = new ArrayList<DeviceDomain>();
            DeviceDomain info = new DeviceDomain();
            info.setSwitchId("");
            // info.setSwitchName(getText("common.lable.select"));
            info.setSwitchName("-----------请选择-----------");
            switchList.add(info);

            List<DeviceDomain> infoList = this.getIbatisDAO().getData("DeviceInfo.getSwitchs", device);
            switchList.addAll(infoList);
        } catch (SQLException e) {
            errorMsg = this.getText("device.before.nvr.action.error");
            logger.info(errorMsg + e, e);
        }
        return switchList;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setDeviceTypeList(List<DeviceDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public List<DeviceDomain> getDeviceTypeList() {
        return deviceTypeList;
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
     * @return Returns the deviceDomain.
     */
    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

    /**
     * @param deviceDomain The deviceDomain to set.
     */
    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
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

    /**
     * @return Returns the switchList.
     */
    public List<DeviceDomain> getSwitchList() {
        return switchList;
    }

    /**
     * @param switchList The switchList to set.
     */
    public void setSwitchList(List<DeviceDomain> switchList) {
        this.switchList = switchList;
    }

}
