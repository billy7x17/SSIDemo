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
package com.cloudmaster.cmp.alarm.autoclear.dao;

import java.util.List;

/**
 * 自动清除
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AutoClearAlarmDomain {

    /**
     * 阀值Id
     */
    private String thresholdId;

    /**
     * mibId
     */
    private String mibId;

    /**
     * oid
     */
    private String alarmOID;

    /**
     * oid
     */
    private String alarmImpact;

    /**
     * 上报OID
     */
    private String alarmReportOID;

    /**
     * 告警标题ID
     */
    private String alarmTitleId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 自动清除的间隔时间
     */
    private String clearIntervalTime;

    /**
     * 自增，主键
     */
    private String alarmID;

    /**
     * 0-通知，1-警告，2-次要，3-重大，4-严重
     */
    private String alarmGrade;

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
     * 告警设备Id
     */
    private String deviceId;

    /**
     * 告警数量
     */
    private String alarmCount;

    /**
     * 首次发生时间
     */
    private String firstAlarmTime;

    /**
     * 清除状态
     */
    private String clearStatus;

    /**
     * 确认状态
     */
    private String confirmStatus;

    /**
     * 新告警通知规则ID
     */
    private String newAlarmNotifyRule;

    /**
     * 数据库名称
     */
    private String nmsDBName;

    /**
     * 阀值列表
     */
    private List<AutoClearAlarmDomain> threshodLi;

    public String getAlarmOID() {
        return alarmOID;
    }

    public void setAlarmOID(String alarmOID) {
        this.alarmOID = alarmOID;
    }

    public String getAlarmImpact() {
        return alarmImpact;
    }

    public void setAlarmImpact(String alarmImpact) {
        this.alarmImpact = alarmImpact;
    }

    public String getAlarmReportOID() {
        return alarmReportOID;
    }

    public void setAlarmReportOID(String alarmReportOID) {
        this.alarmReportOID = alarmReportOID;
    }

    public String getAlarmTitleId() {
        return alarmTitleId;
    }

    public void setAlarmTitleId(String alarmTitleId) {
        this.alarmTitleId = alarmTitleId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    public List<AutoClearAlarmDomain> getThreshodLi() {
        return threshodLi;
    }

    public void setThreshodLi(List<AutoClearAlarmDomain> threshodLi) {
        this.threshodLi = threshodLi;
    }

    public String getNewAlarmNotifyRule() {
        return newAlarmNotifyRule;
    }

    public void setNewAlarmNotifyRule(String newAlarmNotifyRule) {
        this.newAlarmNotifyRule = newAlarmNotifyRule;
    }

    public String getThresholdId() {
        return thresholdId;
    }

    public void setThresholdId(String thresholdId) {
        this.thresholdId = thresholdId;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public String getClearIntervalTime() {
        return clearIntervalTime;
    }

    public void setClearIntervalTime(String clearIntervalTime) {
        this.clearIntervalTime = clearIntervalTime;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(String alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getFirstAlarmTime() {
        return firstAlarmTime;
    }

    public void setFirstAlarmTime(String firstAlarmTime) {
        this.firstAlarmTime = firstAlarmTime;
    }

    public String getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(String clearStatus) {
        this.clearStatus = clearStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }


}
