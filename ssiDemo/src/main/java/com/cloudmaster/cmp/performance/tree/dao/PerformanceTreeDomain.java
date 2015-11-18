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

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class PerformanceTreeDomain {
    
    private String zoneClusterId = "";
    
    private String parentId = "";
    
    private String zoneClusterName = "";
    
    private String clusterDesc = "";
    
    private String zciId = "";
    
    private String resourceType = "";
    
    private String localTableIdRef = "";
    
    private String leafName = "";
    
    private String extIdRef = "";
    
    private String cmdbIdRef = "";
    
    private String icon = "";
    
    private String color = "black";
    
    private String pmName="";
    
    private String vmName="";
    
    private String mmName="";
    private String mvmName="";
    private String asName="";
    private String swName="";
    private String fwName="";
    private String rtName="";
    private String lbName="";
    
    /*业务视图树展示单独增加的*/
    private String systemType;
    private String systemId;
    /*业务视图树按照业务实例名称和资源名称查询增加的*/
    private String systemTypeName;
    private String systemName;
    private String localTableName;
    private String resourceTypeName;
    private String ip;
    private int countService;
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getMmName() {
        return mmName;
    }
    public void setMmName(String mmName) {
        this.mmName = mmName;
    }
    public String getMvmName() {
        return mvmName;
    }
    public void setMvmName(String mvmName) {
        this.mvmName = mvmName;
    }
    public String getAsName() {
        return asName;
    }
    public void setAsName(String asName) {
        this.asName = asName;
    }
    public String getSwName() {
        return swName;
    }
    public String getFwName() {
        return fwName;
    }
    public void setFwName(String fwName) {
        this.fwName = fwName;
    }
    public String getRtName() {
        return rtName;
    }
    public void setRtName(String rtName) {
        this.rtName = rtName;
    }
    public String getLbName() {
        return lbName;
    }
    public void setLbName(String lbName) {
        this.lbName = lbName;
    }
    public void setSwName(String swName) {
        this.swName = swName;
    }
    public String getPmName() {
        return pmName;
    }
    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
    public String getVmName() {
        return vmName;
    }
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }
    public String getZoneClusterId() {
        return zoneClusterId;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public void setZoneClusterId(String zoneClusterId) {
        this.zoneClusterId = zoneClusterId;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getZoneClusterName() {
        return zoneClusterName;
    }

    public void setZoneClusterName(String zoneClusterIdName) {
        this.zoneClusterName = zoneClusterIdName;
    }

    public String getClusterDesc() {
        return clusterDesc;
    }

    public void setClusterDesc(String clusterDesc) {
        this.clusterDesc = clusterDesc;
    }

    public String getZciId() {
        return zciId;
    }

    public void setZciId(String zciId) {
        this.zciId = zciId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getLocalTableIdRef() {
        return localTableIdRef;
    }

    public void setLocalTableIdRef(String localTableIdRef) {
        this.localTableIdRef = localTableIdRef;
    }

    public String getLeafName() {
        return leafName;
    }

    public void setLeafName(String leafName) {
        this.leafName = leafName;
    }

    public String getExtIdRef() {
        return extIdRef;
    }

    public void setExtIdRef(String extIdRef) {
        this.extIdRef = extIdRef;
    }

    public String getCmdbIdRef() {
        return cmdbIdRef;
    }

    public void setCmdbIdRef(String cmdbIdRef) {
        this.cmdbIdRef = cmdbIdRef;
    }
    public String getSystemType() {
        return systemType;
    }
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    public String getSystemName() {
        return systemName;
    }
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    public String getLocalTableName() {
        return localTableName;
    }
    public void setLocalTableName(String localTableName) {
        this.localTableName = localTableName;
    }
    public String getSystemTypeName() {
        return systemTypeName;
    }
    public void setSystemTypeName(String systemTypeName) {
        this.systemTypeName = systemTypeName;
    }
    public String getResourceTypeName() {
        return resourceTypeName;
    }
    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }
    public int getCountService() {
        return countService;
    }
    public void setCountService(int countService) {
        this.countService = countService;
    }
    
}
