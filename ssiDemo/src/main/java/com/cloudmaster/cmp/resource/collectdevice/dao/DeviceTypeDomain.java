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

/**
 * 设备类型
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceTypeDomain {

    // 类型主键
    private String rowId;

    // 类型
    private String agentType;

    // 名称
    private String agentName;

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }
}
