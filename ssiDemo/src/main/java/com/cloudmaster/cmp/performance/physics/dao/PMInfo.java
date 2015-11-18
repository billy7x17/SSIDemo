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
package com.cloudmaster.cmp.performance.physics.dao;

import java.io.Serializable;

/**
 * 物理机信息属性的JavaBean
 * @author WHB
 * @version 1.0.0 18 Mar 2012
 */
public class PMInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3039742126613030909L;
    /**
     * 物理机id
     */
    private String pmID;
    /**
     * 所属分区集群id
     */
    private String clusterID;
    
   
    /**
     * 物理机名称
     */
    private String pmName;
    /**
     * 黑名单列表
     */
    private String blackList;
    /**
     * 物理机IP
     */
    private String pmIP;
    
    /**
     * 设备ID.
     */
    private String typeId;

    /**
     * 物理机申请人
     */
    private String pmProposer;
    /**
     * 物理机创建人
     */
    private String pmCreater;
    /**
     * 物理机创建时间
     */
    private String pmCreaterTime;
    /**
     * 物理机申请时间
     */
    private String pmPropTime;
    /**
     * 外围系统ID
     */
    private String pmExtHostId;
    /**
     * 电源状态
     */
    private String pmPowerState;
    /**
     * 操作系统状态
     */
    private String pmOSState;
    /**
     * 软件状态
     */
    private String pmSoftState;
    /**
     * 刷新时间
     */
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPmCreater() {
        return pmCreater;
    }

    public void setPmCreater(String pmCreater) {
        this.pmCreater = pmCreater;
    }

    public String getPmCreaterTime() {
        return pmCreaterTime;
    }

    public void setPmCreaterTime(String pmCreaterTime) {
        this.pmCreaterTime = pmCreaterTime;
    }

    public String getPmPropTime() {
        return pmPropTime;
    }

    public void setPmPropTime(String pmPropTime) {
        this.pmPropTime = pmPropTime;
    }

    public String getPmExtHostId() {
        return pmExtHostId;
    }

    public void setPmExtHostId(String pmExtHostId) {
        this.pmExtHostId = pmExtHostId;
    }

    public String getPmPowerState() {
        return pmPowerState;
    }

    public void setPmPowerState(String pmPowerState) {
        this.pmPowerState = pmPowerState;
    }

    public String getPmOSState() {
        return pmOSState;
    }

    public void setPmOSState(String pmOSState) {
        this.pmOSState = pmOSState;
    }

    public String getPmSoftState() {
        return pmSoftState;
    }

    public void setPmSoftState(String pmSoftState) {
        this.pmSoftState = pmSoftState;
    }

    public String getPmState() {
        return pmState;
    }

    public void setPmState(String pmState) {
        this.pmState = pmState;
    }
    /**
     * 物理机生产厂家
     */
    private String pmFactory;
    /**
     * 机器型号
     */
    private String pmServerType;

    /**
     * 机器名称
     */
    private String pmServerName;

    /**
     * CPU主频
     */
    private String pmCPUFrequency;

    /**
     * CPU类型
     */
    private String pmCPUType;

    /**
     * CPU单元数量
     */
    private String pmCPUNum;

    /**
     * 虚拟处理器个数
     */
    private String pmVProcessor;

    /**
     * 内存容量
     */
    private String pmMemorySize;

    /**
     * 硬盘大小
     */
    private String pmDiskSize;

    /**
     * 网卡个数
     */
    private String pmEthAdaNum;

    /**
     * 网卡规格
     */
    private String pmEthAdaType;

    /**
     * SCSI卡个数
     */
    private String pmSCSIAdaNum;

    /**
     * HBA卡个数
     */
    private String pmHBANum;

    /**
     * HBA规格
     */
    private String pmHBAType;
    /**
     * 操作系统
     */
    private String pmOS;

    /**
     * 描述
     */
    private String pmDescription;

    /**
     * 是否分配给BC-EC
     */
    private String pmState;

    //get和set方法
    public String getPmID() {
        return pmID;
    }

    public void setPmID(String pmID) {
        this.pmID = pmID;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPmIP() {
        return pmIP;
    }

    public void setPmIP(String pmIP) {
        this.pmIP = pmIP;
    }

    public String getPmProposer() {
        return pmProposer;
    }

    public void setPmProposer(String pmProposer) {
        this.pmProposer = pmProposer;
    }

    public String getPmServerType() {
        return pmServerType;
    }

    public void setPmServerType(String pmServerType) {
        this.pmServerType = pmServerType;
    }

    public String getPmServerName() {
        return pmServerName;
    }

    public void setPmServerName(String pmServerName) {
        this.pmServerName = pmServerName;
    }

    public String getPmCPUFrequency() {
        return pmCPUFrequency;
    }

    public void setPmCPUFrequency(String pmCPUFrequency) {
        this.pmCPUFrequency = pmCPUFrequency;
    }

    public String getPmCPUType() {
        return pmCPUType;
    }

    public void setPmCPUType(String pmCPUType) {
        this.pmCPUType = pmCPUType;
    }

    public String getPmCPUNum() {
        return pmCPUNum;
    }

    public void setPmCPUNum(String pmCPUNum) {
        this.pmCPUNum = pmCPUNum;
    }

    public String getPmVProcessor() {
        return pmVProcessor;
    }

    public void setPmVProcessor(String pmVProcessor) {
        this.pmVProcessor = pmVProcessor;
    }

    public String getPmMemorySize() {
        return pmMemorySize;
    }

    public void setPmMemorySize(String pmMemorySize) {
        this.pmMemorySize = pmMemorySize;
    }

    public String getPmDiskSize() {
        return pmDiskSize;
    }

    public void setPmDiskSize(String pmDiskSize) {
        this.pmDiskSize = pmDiskSize;
    }

    public String getPmEthAdaNum() {
        return pmEthAdaNum;
    }

    public void setPmEthAdaNum(String pmEthAdaNum) {
        this.pmEthAdaNum = pmEthAdaNum;
    }

    public String getPmEthAdaType() {
        return pmEthAdaType;
    }

    public void setPmEthAdaType(String pmEthAdaType) {
        this.pmEthAdaType = pmEthAdaType;
    }

    public String getPmSCSIAdaNum() {
        return pmSCSIAdaNum;
    }

    public void setPmSCSIAdaNum(String pmSCSIAdaNum) {
        this.pmSCSIAdaNum = pmSCSIAdaNum;
    }

    public String getPmHBANum() {
        return pmHBANum;
    }

    public void setPmHBANum(String pmHBANum) {
        this.pmHBANum = pmHBANum;
    }

    public String getPmHBAType() {
        return pmHBAType;
    }

    public void setPmHBAType(String pmHBAType) {
        this.pmHBAType = pmHBAType;
    }

    public String getPmOS() {
        return pmOS;
    }

    public void setPmOS(String pmOS) {
        this.pmOS = pmOS;
    }

    public String getPmDescription() {
        return pmDescription;
    }

    public void setPmDescription(String pmDescription) {
        this.pmDescription = pmDescription;
    }

    public String getPmFactory() {
        return pmFactory;
    }

    public void setPmFactory(String pmFactory) {
        this.pmFactory = pmFactory;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }
    
    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }

    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    
    
}
