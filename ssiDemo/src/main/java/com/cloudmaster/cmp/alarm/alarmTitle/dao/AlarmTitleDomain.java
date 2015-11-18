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
package com.cloudmaster.cmp.alarm.alarmTitle.dao;

import com.cloudmaster.cmp.resource.view.dao.ExcelAnnotation;

/**
 * 告警标题管理Bean MALS_NM_THRESHOLD_CONTENT_T
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleDomain {

    /**
     * 标题ID
     */
    @ExcelAnnotation(exportName = "标题编号")
    private String tcId;

    /**
     * 告警标题
     */
    @ExcelAnnotation(exportName = "告警标题")
    private String tcTitle;

    /**
     * 告警类别
     */
    @ExcelAnnotation(exportName = "告警类别")
    private String tcType;

    /**
     * 告警标题类型，三种类型:1-设备告警、2-性能阀值告警、3-业务告警
     */
    //@ExcelAnnotation(exportName = "告警类型")
    private String type;

    /**
     * 告警唯一标识，在页面配置，上传告警时作为oid
     */
    @ExcelAnnotation(exportName = "上报告警OID")
    private String alarmIdentityID;

    /**
     * 创建时间
     */
    @ExcelAnnotation(exportName = "创建时间")
    private String createTime;
    
    /**
     * 业务实例标识
     * @ExcelAnnotation(exportName = "业务实例")
     */    
    private String systemId;
    
    /**
     * system_info_tab表中的system_name字段
     */
    //@ExcelAnnotation(exportName = "业务实例名称")
    private String systemName;

    /**
     * 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    private String query;

    private String qtype;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcId() {
        return tcId;
    }

    public String getTcTitle() {
        return tcTitle;
    }

    public void setTcTitle(String tcTitle) {
        this.tcTitle = tcTitle;
    }

    public String getTcType() {
        return tcType;
    }

    public void setTcType(String tcType) {
        this.tcType = tcType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
