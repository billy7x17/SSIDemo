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
package com.cloudmaster.cmp.performance.tree.dao;

import java.io.Serializable;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class ServiceViewTreeDomain implements Serializable {
    private static final long serialVersionUID = 8484672923253327787L;

    private String serviceName;

    private String id = "";

    private String parentId = "";

    private String name = "";

    private String leafName = "";
    private String localTableName = "";
    
    private String localTableIdRef = "";
    //标志该资源被申请
    private String state = "";

    private String icon = "";

    private String color = "black";

    //资源类型
    private String resourceType;

    //业务实例ID
    private String systemID;
    //业务实例父ID
    private String systemParentID;
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getId() {
        return id;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String getSystemParentID() {
        return systemParentID;
    }

    public void setSystemParentID(String systemParentID) {
        this.systemParentID = systemParentID;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLeafName() {
        return leafName;
    }

    public void setLeafName(String leafName) {
        this.leafName = leafName;
    }

    public String getLocalTableName() {
        return localTableName;
    }

    public void setLocalTableName(String localTableName) {
        this.localTableName = localTableName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLocalTableIdRef() {
        return localTableIdRef;
    }

    public void setLocalTableIdRef(String localTableIdRef) {
        this.localTableIdRef = localTableIdRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
