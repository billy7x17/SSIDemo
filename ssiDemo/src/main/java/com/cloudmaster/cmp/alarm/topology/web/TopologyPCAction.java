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
package com.cloudmaster.cmp.alarm.topology.web;

import com.cloudmaster.cmp.operationlog.OperationLogAction;

/**
 * 拓扑管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TopologyPCAction extends OperationLogAction {
    private static final long serialVersionUID = -2961508487985405480L;

    private String siteId;

    // 根据点击的机房ID，展示不同机房的拓扑图
    private String roomID;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String topologyZYC() {
        roomID = "topologyPCSubnet";
        return SUCCESS;
    }

    public String managerTopo() {
        roomID = "topologyPCSubnetM";
        return SUCCESS;
    }

    public String twaverTopo() {
        return SUCCESS;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
