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
 * 直接跳转到serviceTopology.jsp的action
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ServiceTopologyAction extends OperationLogAction {
    private static final long serialVersionUID = -8057140144706560257L;

    private String roomID;

    public String execute() throws Exception {
        roomID = "service-nj";
        return SUCCESS;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

}
