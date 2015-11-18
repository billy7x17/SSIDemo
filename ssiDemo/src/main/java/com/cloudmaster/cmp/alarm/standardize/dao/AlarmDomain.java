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
package com.cloudmaster.cmp.alarm.standardize.dao;

/**
 * 告警采集
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmDomain {
    /**
     * 自增，主键
     */
    private String alarmID;

    /**
     * 0-mals_nm_alarm_device_t 1-hot_event_tab 2-vm_hot_event_tab 3-nagios_pmstatus
     * 4-nagios_servicestatus
     */
    private String alarmSourceType;

    /**
     * 接入类型是SNMP的，此字段为trapOid，接入类型是DB的，此字段为系统配置的标识
     */
    private String alarmIdentify;

    /**
     * 0-通知，1-警告，2-次要，3-重大，4-严重
     */
    private String alarmGrade;

    /**
     * 告警原始级别
     */
    private String originalAlarmGrade;

    /**
     * 告警类型 通断性，端口，CPU，内存，SWAP，进程，硬盘，网卡，系统服务 ，域控服务器 ，DNS服务器 ，日志 ，页交换 ，进程 ，吞吐量 ，控制器信息 ，总线信息 ，缓存信息
     * ，电源信息 ，电池信息 ，块设备，数据流 ，数据延实 ，丢包率 ，VRF ，VPN
     */
    private String alarmType;

    /**
     * 告警设备类型 1 -物理机，2-虚拟机，3-磁盘阵列，4-网络，5-svc块存储，6-IBM虚拟机，7-IBM磁盘阵列，8-日立磁盘阵列
     */
    private String deviceType;

    /**
     * 告警标题
     */
    private String alarmTitle;

    /**
     * 告警内容
     */
    private String alarmContent;

    /**
     * 告警时间 YYYYMMDDHHMMSS
     */
    private String alarmTime;

    /**
     * 告警IP
     */
    private String alarmIP;

    /**
     * 清除时间 YYYYMMDDHHMMSS
     */
    private String clearTime;

    /**
     * 清除人员标识
     */
    private String clearPerson;

    /**
     * 确认时间 YYYYMMDDHHMMSS
     */
    private String confirmTime;

    /**
     * 确认人员标识
     */
    private String confirmPerson;

    /**
     * 规则匹配的规则标识
     */
    private String rosterId;

    /**
     * 原始告警信息中的阀值ID
     */
    private String thresholdId;

    /**
     * 规则匹配中的类型,0-阀值类型，1-规则类型
     */
    private String rosterType;

    /**
     * 告警设备Id
     */
    private String deviceId;

    /**
     * 告警设备CMDB标识
     */
    private String cmdbId;

    /**
     * 新告警通知规则ID
     */
    private String newAlarmNotifyRule;

    public String getNewAlarmNotifyRule() {
        return newAlarmNotifyRule;
    }

    public void setNewAlarmNotifyRule(String newAlarmNotifyRule) {
        this.newAlarmNotifyRule = newAlarmNotifyRule;
    }

    public String getCmdbId() {
        return cmdbId;
    }

    public void setCmdbId(String cmdbId) {
        this.cmdbId = cmdbId;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getAlarmSourceType() {
        return alarmSourceType;
    }

    public void setAlarmSourceType(String alarmSourceType) {
        this.alarmSourceType = alarmSourceType;
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

    public String getOriginalAlarmGrade() {
        return originalAlarmGrade;
    }

    public void setOriginalAlarmGrade(String originalAlarmGrade) {
        this.originalAlarmGrade = originalAlarmGrade;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmIP() {
        return alarmIP;
    }

    public void setAlarmIP(String alarmIP) {
        this.alarmIP = alarmIP;
    }

    public String getClearTime() {
        return clearTime;
    }

    public void setClearTime(String clearTime) {
        this.clearTime = clearTime;
    }

    public String getClearPerson() {
        return clearPerson;
    }

    public void setClearPerson(String clearPerson) {
        this.clearPerson = clearPerson;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmPerson() {
        return confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson) {
        this.confirmPerson = confirmPerson;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRosterId() {
        return rosterId;
    }

    public void setRosterId(String rosterId) {
        this.rosterId = rosterId;
    }

    public String getThresholdId() {
        return thresholdId;
    }

    public void setThresholdId(String thresholdId) {
        this.thresholdId = thresholdId;
    }

    public String getRosterType() {
        return rosterType;
    }

    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
