package com.cloudmaster.cmp.resource.zone.view.dao;

import java.io.Serializable;

/**
 * 机房实体类.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-17
 */
public class ZoneRoomDomain implements Serializable{

    /**
     * 标识.
     */
    private static final long serialVersionUID = 3809587639837678653L;

    /**
     * 主键.
     */
    private String zoneId;
    
    /**
     * 机房名称.
     */
    private String zoneName;
    
    /**
     * 站点ID.
     */
    private String siteId;
    
    /**
     * 站点名称.
     */
    private String siteName;
    
    /**
     * 负责人.
     */
    private String principal;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 描述.
     */
    private String description;

    /**
     * 角色类型.1-中心角色,2-站点角色
     */
    private String roleType;
    
    /**
     *  添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;
    
    /**
     * flexigrid查询字段名
     */
    private String qtype;

    /**
     * flexigrid查询字段值
     */
    private String query;
    
    
    /**
     * @return the zoneId
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * @return the zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * @param zoneName the zoneName to set
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * @return the sortName
     */
    public String getSortName() {
        return sortName;
    }

    /**
     * @param sortName the sortName to set
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the qtype
     */
    public String getQtype() {
        return qtype;
    }

    /**
     * @param qtype the qtype to set
     */
    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
}
