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
package com.cloudmaster.cmp.alarm.topology.manageDomain;

import java.io.Serializable;

/**
 * 拓扑分组管理Domain
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 July 2013
 */
public class TopologyDeviceDomain implements Serializable {
    private static final long serialVersionUID = -4285718269278051054L;
    /*设备ID*/
    private String deviceID;
    /*设备名称*/
    private String deviceName;
    /*设备IP*/
    private String deviceIP;
    /**
     * 设备类型
     * 0-物理机、1-小型机
     */
    private String deviceType;
    /*设备所属分组ID*/
    private String groupID;
    /*设备所属分组名称*/
    private String groupName;
    /*flexgrid排序字段*/
    private String sortName;
    private String sortOrder;
    /*0-普通节点、1-钻取节点*/
    private String node_type;
    public String getNode_type() {
        return node_type;
    }
    public void setNode_type(String node_type) {
        this.node_type = node_type;
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
    public String getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
    public String getDeviceIP() {
        return deviceIP;
    }
    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
