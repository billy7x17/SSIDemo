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
public class TopologyLineDomain implements Serializable {
    private static final long serialVersionUID = -7221490910508314420L;
    /*分组ID*/
    private String groupID;
    /*起始节点*/
    private String fromNode;
    /*结束节点*/
    private String toNode;
    /*0-实线、1-虚线。默认0*/
    private String lineType;
    /*设备IP*/
    private String deviceIP;
    /*设备名称*/
    private String deviceName;
    /*设备类型*/
    private String deviceType;
    private String sortName;
    private String sortOrder;
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
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getFromNode() {
        return fromNode;
    }
    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }
    public String getToNode() {
        return toNode;
    }
    public void setToNode(String toNode) {
        this.toNode = toNode;
    }
    public String getLineType() {
        return lineType;
    }
    public void setLineType(String lineType) {
        this.lineType = lineType;
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
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getDeviceIP() {
        return deviceIP;
    }
    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
}
