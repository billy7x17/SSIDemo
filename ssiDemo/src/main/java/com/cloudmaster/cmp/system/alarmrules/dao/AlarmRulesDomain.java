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
package com.cloudmaster.cmp.system.alarmrules.dao;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRulesDomain {
    private int rowID;

    /* 告警唯一标识 */
    private String alarmIdentityID;

    /* 告警的影响描述 */
    private String alarmImpact;

    private int agentID;

    private String alarmOID;

    private String alarmTitle;

    private String alarmTitleContent;

    private String eventCode;

    private String alarmIndex;

    private String alarmTime;

    private String alarmType;

    private String alarmContent;

    private String alarmLevel;

    private String alarmLevelName;

    private String troubleShooting;

    private String typeID;

    private String agentName;

    /**
     * 标题类型，1-设备trap告警、2-性能阀值告警、3-业务告警不需要配置、4-既是trap告警又是阀值告警
     */
    private String type;

    /**
     * 设备类型对应的告警标题查询条件
     */
    private String alarmTitleCondition;

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    private String qtype;

    private String query;

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public String getAlarmOID() {
        return alarmOID;
    }

    public void setAlarmOID(String alarmOID) {
        this.alarmOID = alarmOID;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getAlarmIndex() {
        return alarmIndex;
    }

    public void setAlarmIndex(String alarmIndex) {
        this.alarmIndex = alarmIndex;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getTroubleShooting() {
        return troubleShooting;
    }

    public void setTroubleShooting(String troubleShooting) {
        this.troubleShooting = troubleShooting;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAlarmLevelName() {
        return alarmLevelName;
    }

    public void setAlarmLevelName(String alarmLevelName) {
        this.alarmLevelName = alarmLevelName;
    }

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
    }

    public String getAlarmImpact() {
        return alarmImpact;
    }

    public void setAlarmImpact(String alarmImpact) {
        this.alarmImpact = alarmImpact;
    }

    public String getAlarmTitleContent() {
        return alarmTitleContent;
    }

    public void setAlarmTitleContent(String alarmTitleContent) {
        this.alarmTitleContent = alarmTitleContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlarmTitleCondition() {
        return alarmTitleCondition;
    }

    public void setAlarmTitleCondition(String alarmTitleCondition) {
        this.alarmTitleCondition = alarmTitleCondition;
    }

}
