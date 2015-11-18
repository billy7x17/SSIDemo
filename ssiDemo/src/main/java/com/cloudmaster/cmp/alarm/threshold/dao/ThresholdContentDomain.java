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
package com.cloudmaster.cmp.alarm.threshold.dao;

/**
 * 告警阈值标题信息Bean MALS_NM_THRESHOLD_CONTENT_T
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdContentDomain {

    private String tcId;

    private String tcTitle;

    private String tcType;

    private String tcDesc;

    private String createTime;

    private String alarmIdentityID;

    /**
     * 查询标题时的限制条件
     */
    private String alarmTitleCondition;

    /**
     * 标题类型，1-设备trap告警、2-性能阀值告警、3-业务告警不需要配置、4-既是trap告警又是阀值告警
     */
    private String type;

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

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
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

    public String getTcDesc() {
        return tcDesc;
    }

    public void setTcDesc(String tcDesc) {
        this.tcDesc = tcDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
