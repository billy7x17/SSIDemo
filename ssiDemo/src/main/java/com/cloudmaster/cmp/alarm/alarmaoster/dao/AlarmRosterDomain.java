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
package com.cloudmaster.cmp.alarm.alarmaoster.dao;

import java.io.Serializable;

/**
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosterDomain implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 9157031546574296628L;

    /*
     * 花名册ID
     */
    private String rosterID;

    /*
     * 厂家标识
     */
    private String manufactureID;

    /*
     *  告警级别
     */
    private String alarmGrade;
    
    /*
     * 告警级别名称
     */
    private String alarmGradeName;


    /*
     * 修改时间
     */
    private String modifyTime;

    /*
     * 描述
     */
    private String description;

    /*
     * 开始时间
     */
    private String startTime;

    /*
     * 结束时间
     */
    private String endTime;

    private String type;

    private String id;

    private String thresholdID;

    private String eventName;

    private String rowID;

    private String alarmTitle;

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    private String agentName;

    private String typeId;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    private String query;

    private String qtype;
    
    
    
    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


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

    public String getThresholdID() {
        return thresholdID;
    }

    public void setThresholdID(String thresholdID) {
        this.thresholdID = thresholdID;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getRosterID() {
        return rosterID;
    }

    public void setRosterID(String rosterID) {
        this.rosterID = rosterID;
    }

    public String getManufactureID() {
        return manufactureID;
    }

    public void setManufactureID(String manufactureID) {
        this.manufactureID = manufactureID;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
