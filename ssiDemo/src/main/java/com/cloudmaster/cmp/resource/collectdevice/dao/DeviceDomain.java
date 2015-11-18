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
package com.cloudmaster.cmp.resource.collectdevice.dao;

import java.util.List;

import com.cloudmaster.cmp.util.DateUtil;

/**
 * 设备信息Bean MALS_NM_AGENT_INFO_T
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceDomain {

    // 设备ID
    private String agentId;

    // 设备名称
    private String agentName;

    // 设备状态 在线/离线
    private String status;

    // ganglia采集频率
    private String agentFrequency;

    // 设备分组
    private String agentGroup;

    // 设备分组名称
    private String groupName;

    // 类型主键
    private String typeId;

    // 设备类型名称
    private String typeName;

    // 设备IP
    private String agentIp;

    // 端口
    private String clPort;

    // 共同体
    private String community;

    // 采集方式
    private String acquisitionMode;

    // 设备型号
    private String deviceType;

    // 设备厂家
    private String agentComp;

    // 硬盘个数（IP-SAN）
    private String diskNum;

    // 所属站点
    private String siteId;

    // 所属站点名称
    private String siteName;

    // 站点线路
    private String lineNum;

    // 站点类型
    private String siteType;

    // 站点类型名称
    private String siteTypeName;

    // 所属机房
    private String zoneId;

    // 机房名称
    private String zoneName;

    // 所属机柜
    private String clusterId;

    // 所属机柜名称
    private String clusterName;

    // 所属VMS
    private String vmsId;

    // 所属VMS名称
    private String vmsName;

    // 所属NVR
    private String nvrId;

    // 所属NVR名称
    private String nvrName;

    // 交换机
    private String switchId;

    // 交换机名称
    private String switchName;

    // 交换机端口
    private String switchPort;

    // 通道列表
    private List<DeviceDomain> channelList;

    // 通道ID
    private String channelId;

    // 通道名称
    private String channelName;

    // 设备描述
    private String agentDesc;

    // 创建时间
    private String createTime;

    // 协议
    private String versionNum;

    // 以下是zone_cluster_instance_tab表需要维护的字段 begin
    // 设备ID
    private String zciId;

    // 资源类型
    private String resourceType;

    // 设备编号
    private String localTableIDRef;

    // IP地址
    private String leafName;

    private String cMDBIDRef;

    // 设备名称
    private String localTableName;

    // 以上是zone_cluster_instance_tab表需要维护的字段 end

    // 导入标识
    private String importDeviceFlag;

    private String importDevice;

    private List<String> idevice;

    // 导入文件
    private String batchfile;

    private String batchfileContentType;

    private String batchfileFileName;

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    private String qtype;

    private String query;

    private String rowId;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentIp() {
        return agentIp;
    }

    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }

    public String getAgentComp() {
        return agentComp;
    }

    public void setAgentComp(String agentComp) {
        this.agentComp = agentComp;
    }

    public String getAgentDesc() {
        return agentDesc;
    }

    public void setAgentDesc(String agentDesc) {
        this.agentDesc = agentDesc;
    }

    public String getCreateTime() {

        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClPort() {
        return clPort;
    }

    public void setClPort(String clPort) {
        this.clPort = clPort;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCreateTimeForm(String dateStr) {
        if (dateStr != null && !"".equals(dateStr)) {
            this.createTime = DateUtil.changedateStr(dateStr, "yyyy/MM/dd", "yyyyMMdd");
        }
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return Returns the deviceType.
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The deviceType to set.
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return Returns the diskNum.
     */
    public String getDiskNum() {
        return diskNum;
    }

    /**
     * @param diskNum The diskNum to set.
     */
    public void setDiskNum(String diskNum) {
        this.diskNum = diskNum;
    }

    /**
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * @return Returns the clusterId.
     */
    public String getClusterId() {
        return clusterId;
    }

    /**
     * @param clusterId The clusterId to set.
     */
    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * @return Returns the vmsId.
     */
    public String getVmsId() {
        return vmsId;
    }

    /**
     * @param vmsId The vmsId to set.
     */
    public void setVmsId(String vmsId) {
        this.vmsId = vmsId;
    }

    /**
     * @return Returns the nvrId.
     */
    public String getNvrId() {
        return nvrId;
    }

    /**
     * @param nvrId The nvrId to set.
     */
    public void setNvrId(String nvrId) {
        this.nvrId = nvrId;
    }

    /**
     * @return Returns the versionNum.
     */
    public String getVersionNum() {
        return versionNum;
    }

    /**
     * @param versionNum The versionNum to set.
     */
    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * @return Returns the acquisitionMode.
     */
    public String getAcquisitionMode() {
        return acquisitionMode;
    }

    /**
     * @param acquisitionMode The acquisitionMode to set.
     */
    public void setAcquisitionMode(String acquisitionMode) {
        this.acquisitionMode = acquisitionMode;
    }

    /**
     * @return Returns the siteName.
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName The siteName to set.
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return Returns the clusterName.
     */
    public String getClusterName() {
        return clusterName;
    }

    /**
     * @param clusterName The clusterName to set.
     */
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    /**
     * @return Returns the lineNum.
     */
    public String getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum The lineNum to set.
     */
    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return Returns the siteType.
     */
    public String getSiteType() {
        return siteType;
    }

    /**
     * @param siteType The siteType to set.
     */
    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    /**
     * @return Returns the zciId.
     */
    public String getZciId() {
        return zciId;
    }

    /**
     * @param zciId The zciId to set.
     */
    public void setZciId(String zciId) {
        this.zciId = zciId;
    }

    /**
     * @return Returns the resourceType.
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType The resourceType to set.
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * @return Returns the localTableIDRef.
     */
    public String getLocalTableIDRef() {
        return localTableIDRef;
    }

    /**
     * @param localTableIDRef The localTableIDRef to set.
     */
    public void setLocalTableIDRef(String localTableIDRef) {
        this.localTableIDRef = localTableIDRef;
    }

    /**
     * @return Returns the leafName.
     */
    public String getLeafName() {
        return leafName;
    }

    /**
     * @param leafName The leafName to set.
     */
    public void setLeafName(String leafName) {
        this.leafName = leafName;
    }

    /**
     * @return Returns the cMDBIDRef.
     */
    public String getcMDBIDRef() {
        return cMDBIDRef;
    }

    /**
     * @param cMDBIDRef The cMDBIDRef to set.
     */
    public void setcMDBIDRef(String cMDBIDRef) {
        this.cMDBIDRef = cMDBIDRef;
    }

    /**
     * @return Returns the localTableName.
     */
    public String getLocalTableName() {
        return localTableName;
    }

    /**
     * @param localTableName The localTableName to set.
     */
    public void setLocalTableName(String localTableName) {
        this.localTableName = localTableName;
    }

    /**
     * @return Returns the switchPort.
     */
    public String getSwitchPort() {
        return switchPort;
    }

    /**
     * @param switchPort The switchPort to set.
     */
    public void setSwitchPort(String switchPort) {
        this.switchPort = switchPort;
    }

    /**
     * @return Returns the agentGroup.
     */
    public String getAgentGroup() {
        return agentGroup;
    }

    /**
     * @param agentGroup The agentGroup to set.
     */
    public void setAgentGroup(String agentGroup) {
        this.agentGroup = agentGroup;
    }

    /**
     * @return Returns the groupName.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName The groupName to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return Returns the vmsName.
     */
    public String getVmsName() {
        return vmsName;
    }

    /**
     * @param vmsName The vmsName to set.
     */
    public void setVmsName(String vmsName) {
        this.vmsName = vmsName;
    }

    /**
     * @return Returns the nvrName.
     */
    public String getNvrName() {
        return nvrName;
    }

    /**
     * @param nvrName The nvrName to set.
     */
    public void setNvrName(String nvrName) {
        this.nvrName = nvrName;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the agentFrequency.
     */
    public String getAgentFrequency() {
        return agentFrequency;
    }

    /**
     * @param agentFrequency The agentFrequency to set.
     */
    public void setAgentFrequency(String agentFrequency) {
        this.agentFrequency = agentFrequency;
    }

    /**
     * @return Returns the importDeviceFlag.
     */
    public String getImportDeviceFlag() {
        return importDeviceFlag;
    }

    /**
     * @param importDeviceFlag The importDeviceFlag to set.
     */
    public void setImportDeviceFlag(String importDeviceFlag) {
        this.importDeviceFlag = importDeviceFlag;
    }

    /**
     * @return Returns the siteTypeName.
     */
    public String getSiteTypeName() {
        return siteTypeName;
    }

    /**
     * @param siteTypeName The siteTypeName to set.
     */
    public void setSiteTypeName(String siteTypeName) {
        this.siteTypeName = siteTypeName;
    }

    /**
     * @return Returns the batchfile.
     */
    public String getBatchfile() {
        return batchfile;
    }

    /**
     * @param batchfile The batchfile to set.
     */
    public void setBatchfile(String batchfile) {
        this.batchfile = batchfile;
    }

    /**
     * @return Returns the importDevice.
     */
    public String getImportDevice() {
        return importDevice;
    }

    /**
     * @param importDevice The importDevice to set.
     */
    public void setImportDevice(String importDevice) {
        this.importDevice = importDevice;
    }

    /**
     * @return Returns the idevice.
     */
    public List<String> getIdevice() {
        return idevice;
    }

    /**
     * @param idevice The idevice to set.
     */
    public void setIdevice(List<String> idevice) {
        this.idevice = idevice;
    }

    /**
     * @return Returns the batchfileContentType.
     */
    public String getBatchfileContentType() {
        return batchfileContentType;
    }

    /**
     * @param batchfileContentType The batchfileContentType to set.
     */
    public void setBatchfileContentType(String batchfileContentType) {
        this.batchfileContentType = batchfileContentType;
    }

    /**
     * @return Returns the batchfileFileName.
     */
    public String getBatchfileFileName() {
        return batchfileFileName;
    }

    /**
     * @param batchfileFileName The batchfileFileName to set.
     */
    public void setBatchfileFileName(String batchfileFileName) {
        this.batchfileFileName = batchfileFileName;
    }

    /**
     * @return Returns the channelId.
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId The channelId to set.
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return Returns the channelName.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName The channelName to set.
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
     * @return Returns the switchId.
     */
    public String getSwitchId() {
        return switchId;
    }

    /**
     * @param switchId The switchId to set.
     */
    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    /**
     * @return Returns the switchName.
     */
    public String getSwitchName() {
        return switchName;
    }

    /**
     * @param switchName The switchName to set.
     */
    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

}
