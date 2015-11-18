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
package com.cloudmaster.cmp.alarm.filter.dao;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmFilterDomain {

    /**
     * 过滤规则标识
     */
    private String filterId;

    /**
     * 过滤规则名称
     */
    private String filterName;

    /**
     * 过滤规则状态 0-无效，1-有效
     */
    private String filterStatus;

    /**
     * 过滤规则标识
     */
    private String modifyTime;

    /**
     * 规则匹配ID
     */
    private String rosterId;
    
    /**
     * 规则匹配名称
     */
    private String rosterName;

    /**
     * 查询，过滤规则开始时间
     */
    private String startTime;

    /**
     * 查询，过滤规则结束时间
     */
    private String endTime;

    /**
     * 规则匹配，厂家名称
     */
    private String manufactureId;

    /**
     * 规则匹配，告警OID
     */
    private String alarmIdentify;

    /**
     * 规则匹配，告警OID名称
     */
    private String alarmIdentifyName;

    /**
     * 规则匹配，原始告警级别
     */
    private String originalAlarmGrade;

    /**
     * 规则匹配，映射告警级别
     */
    private String alarmGrade;

    /**
     * 规则匹配，规则匹配类型
     */
    private String rosterType;

    /**
     * 规则匹配，规则匹配阀值ID
     */
    private String rosterThresholdID;

    /**
     * 规则匹配，规则匹配阀值名称
     */
    private String thresholdName;

    /**
     * 规则匹配，规则匹配规则名称
     */
    private String accessName;

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

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getAlarmIdentify() {
        return alarmIdentify;
    }

    public void setAlarmIdentify(String alarmIdentify) {
        this.alarmIdentify = alarmIdentify;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRosterId() {
        return rosterId;
    }

    public void setRosterId(String rosterId) {
        this.rosterId = rosterId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getOriginalAlarmGrade() {
        return originalAlarmGrade;
    }

    public void setOriginalAlarmGrade(String originalAlarmGrade) {
        this.originalAlarmGrade = originalAlarmGrade;
    }

    public String getAlarmIdentifyName() {
        return alarmIdentifyName;
    }

    public void setAlarmIdentifyName(String alarmIdentifyName) {
        this.alarmIdentifyName = alarmIdentifyName;
    }

    public String getRosterType() {
        return rosterType;
    }

    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    public String getRosterThresholdID() {
        return rosterThresholdID;
    }

    public void setRosterThresholdID(String rosterThresholdID) {
        this.rosterThresholdID = rosterThresholdID;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

}
