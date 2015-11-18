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
package com.cloudmaster.cmp.alarm.view.dao;

import com.cloudmaster.cmp.resource.view.dao.ExcelAnnotation;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com">wengcz</a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewDomain {

    /**
     * 自增，主键
     */
    @ExcelAnnotation(exportName = "告警流水号")
    private String alarmID;

    /**
     * 0-SNMP， 1-DB， 默认0
     */
    private String alarmSourceType;

    /**
     * 告警IP
     */
    private String alarmIP;

    /**
     * 告警对象
     */
    @ExcelAnnotation(exportName = "告警对象")
    private String agentName;

    /**
     * 告警级别
     */
    @ExcelAnnotation(exportName = "告警级别")
    private String alarmGrade;

    /**
     * 告警标题
     */
    @ExcelAnnotation(exportName = "告警标题")
    private String alarmTitle;

    /**
     * 首次告警时间
     */
    @ExcelAnnotation(exportName = "首次发生时间")
    private String firstAlarmTime;

    /**
     * 通断性等内容
     */
    @ExcelAnnotation(exportName = "告警分类")
    private String alarmType;

    /**
     * 设备编码
     */
    @ExcelAnnotation(exportName = "设备编码")
    private String LocalTableIDRef;

    private String siteId;

    /**
     * 所属站点
     */
    @ExcelAnnotation(exportName = "所属站点")
    private String siteName;

    /**
     * 告警内容
     */
    private String alarmContent;

    /**
     * 确认状态
     */
    @ExcelAnnotation(exportName = "告警状态")
    private String confirmStatus;

    /**
     * 设备类型
     */
    @ExcelAnnotation(exportName = "告警设备类型")
    private String deviceTypeName;

    /**
     * 告警重复数量
     */
    @ExcelAnnotation(exportName = "重复次数")
    private String count;

    /**
     * 告警时间 YYYYMMDDHHMMSS
     */
    @ExcelAnnotation(exportName = "最后发生时间")
    private String alarmTime;

    /**
     * 接入类型是SNMP的，此字段为trapOid，接入类型是DB的，此字段为系统配置的标识
     */
    @ExcelAnnotation(exportName = "告警标识ID")
    private String alarmIdentify;

    /**
     * 告警级别名称
     */
    private String alarmGradeName;

    /**
     * 告警原始级别
     */
    @ExcelAnnotation(exportName = "厂家告警级别")
    private String originalAlarmGrade;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 告警设备Id
     */
    private String deviceId;

    /**
     * 清除状态
     */
    @ExcelAnnotation(exportName = "清除状态")
    private String clearStatus;

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
     * 短信通知，收信人
     */
    private String smsMember;

    /**
     * 短信通知，短信内容
     */
    private String smsContent;

    /**
     * 邮件通知，邮件人员
     */
    private String mailMember;

    /**
     * 邮件通知，邮件标题
     */
    private String mailTitle;

    /**
     * 邮件通知，邮件内容
     */
    private String mailContent;

    /** ================ 设备信息 ================ */

    /**
     * 设备CI属性名称
     */
    private String ciName;

    /**
     * 设备CI属性值
     */
    private String ciValue;

    /**
     * 设备关联业务
     */
    private String business;

    /**
     * 设备CMDB标识
     */
    private String cmdbID;

    /**
     * 设备类型 物理机：CIDC-RT-SRV，虚拟机：CIDC-RT-VM
     */
    private String resourceType;

    /**
     * 设备本地ID
     */
    private String localID;

    // ////////////////事件//////////////////////////////
    /**
     * 事件ID
     */
    private String eventID;

    /**
     * 事件名称
     */
    private String eventName;

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

    /**
     * 事件发生时间
     */
    private String eventOccurTime;

    /**
     * 查询字段
     */
    private String queryType;

    /**
     * 查询值
     */
    private String queryValue;

    /**
     * 告警设备机房
     */
    private String zoneName;

    /**
     * 告警设备机柜
     */
    private String cabinetName;

    /**
     * 网元IP
     */
    private String deviceIp;

    /**
     * 设备状态，0-在用，1-工程中，2-退网
     */
    private String deviceStatus;

    /**
     * 告警清除操作说明
     */
    private String clearDesc;

    /**
     * 告警确认操作说明
     */
    private String confirmDesc;

    /**
     * 新告警通知规则ID
     */
    private String newAlarmNotifyRule;

    /**
     * 研发中心数据库名称
     * @return
     */
    private String nmsDB;

    /**
     * 告警影响
     * @return
     */
    private String alarmImpact;

    /**
     * 告警上报OID
     * @return
     */
    private String alarmReportOID;

    /**
     * 告警标题ID
     * @return
     */
    private String alarmTitleId;

    /**
     * 告警设备名称
     * @return
     */
    private String deviceName;

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

    public String getNewAlarmNotifyRule() {
        return newAlarmNotifyRule;
    }

    public void setNewAlarmNotifyRule(String newAlarmNotifyRule) {
        this.newAlarmNotifyRule = newAlarmNotifyRule;
    }

    public String getClearDesc() {
        return clearDesc;
    }

    public void setClearDesc(String clearDesc) {
        this.clearDesc = clearDesc;
    }

    public String getConfirmDesc() {
        return confirmDesc;
    }

    public void setConfirmDesc(String confirmDesc) {
        this.confirmDesc = confirmDesc;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
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

    public String getSmsMember() {
        return smsMember;
    }

    public void setSmsMember(String smsMember) {
        this.smsMember = smsMember;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getMailMember() {
        return mailMember;
    }

    public void setMailMember(String mailMember) {
        this.mailMember = mailMember;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getFirstAlarmTime() {
        return firstAlarmTime;
    }

    public void setFirstAlarmTime(String firstAlarmTime) {
        this.firstAlarmTime = firstAlarmTime;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public String getCiValue() {
        return ciValue;
    }

    public void setCiValue(String ciValue) {
        this.ciValue = ciValue;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCmdbID() {
        return cmdbID;
    }

    public void setCmdbID(String cmdbID) {
        this.cmdbID = cmdbID;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(String clearStatus) {
        this.clearStatus = clearStatus;
    }

    public String getEventOccurTime() {
        return eventOccurTime;
    }

    public void setEventOccurTime(String eventOccurTime) {
        this.eventOccurTime = eventOccurTime;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }

    public String getNmsDB() {
        return nmsDB;
    }

    public void setNmsDB(String nmsDB) {
        this.nmsDB = nmsDB;
    }

    /**
     * @return Returns the agentName.
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * @param agentName The agentName to set.
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * @return Returns the localTableIDRef.
     */
    public String getLocalTableIDRef() {
        return LocalTableIDRef;
    }

    /**
     * @param localTableIDRef The localTableIDRef to set.
     */
    public void setLocalTableIDRef(String localTableIDRef) {
        LocalTableIDRef = localTableIDRef;
    }

    /**
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * @return Returns the siteName.
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName The siteName to set.
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
