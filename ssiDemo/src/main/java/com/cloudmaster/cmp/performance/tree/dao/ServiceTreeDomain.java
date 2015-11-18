package com.cloudmaster.cmp.performance.tree.dao;

import java.io.Serializable;

/**
 * 站点树信息属性类.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-15
 */
public class ServiceTreeDomain implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8596820959961296043L;
    
    /**
     * 父节点Id.
     */
    private String parentId;
    
    /**
     * ID.
     */
    private String id;
    
    /**
     * 展示的名称.
     */
    private String sitename;
    
    /**
     * 展示的图片.
     */
    private String icon;
    
    /**
     * 站点ID.
     */
    private String siteId;
    
    /**
     * 设备IP.
     */
    private String deviceIp;
    
    /**
     * 展示资源类型.
     */
    private String resourceType;
    
    /**
     * 资源类型ID.
     */
    private String typeId;

    /**
     * 资源名称.
     */
    private String resourceName;
    
    /**
     * IP数组.
     */
    private String[] ipArray;
    
    /**
     * ganglia采集频率
     */
    private String agentFrequency;
    
    /**
     * 设备状态.
     */
    private String status;
    
    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return the sitename
     */
    public String getSitename() {
        return sitename;
    }

    /**
     * @param sitename the sitename to set
     */
    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
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

    /**
     * @return the deviceIp
     */
    public String getDeviceIp() {
        return deviceIp;
    }

    /**
     * @param deviceIp the deviceIp to set
     */
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType the resourceType to set
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
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

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the ipArray
     */
    public String[] getIpArray() {
        return ipArray;
    }

    /**
     * @param ipArray the ipArray to set
     */
    public void setIpArray(String[] ipArray) {
        this.ipArray = ipArray;
    }

    /**
     * @return the agentFrequency
     */
    public String getAgentFrequency() {
        return agentFrequency;
    }

    /**
     * @param agentFrequency the agentFrequency to set
     */
    public void setAgentFrequency(String agentFrequency) {
        this.agentFrequency = agentFrequency;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof ServiceTreeDomain){
            ServiceTreeDomain tree = (ServiceTreeDomain)obj;
            return tree.getDeviceIp().equals(this.getDeviceIp());
        }
        
        return false;
    }
}
