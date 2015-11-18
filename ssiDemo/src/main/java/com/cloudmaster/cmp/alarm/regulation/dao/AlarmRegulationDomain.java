/**
 * @(#)AlarmRuleDomain.java 2013-4-9
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.alarm.regulation.dao;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-9 下午03:45:47
 */
public class AlarmRegulationDomain {
    // 标识
    private String ID;

    // 规则名称，唯一
    private String ruleName;

    // 规则描述
    private String ruleDesc;

    // 规则tree节点信息
    private String ruleInfo;

    // 规则正则表达式
    private String ruleRegexp;

    // 规则正则表达式
    private String ruleState;

    // 规则事件，1-自动清除，2-自动确认，3-自动通知，4-自动过滤，5-重定义
    private String ruleAction;

    // 新告警通知清除时是否通知，0-不通知,1-通知
    private String clearNotify;

    // 通知方式,1-邮件通知，2-短信通知,3-邮件和短信'
    private String notifyType;

    // 动通知人员,记录通知人员的ID
    private String notifyPerson;

    // 邮件标题
    private String notifyTitle;

    // 邮件内容
    private String notifyContent;

    // 短信内容
    private String notifySMS;

    // 短信通知人员ID
    private String notifyPersonId;

    // 短信通知人员邮件
    private String notifyPersonMail;

    // 短信通知人员手机号
    private String notifyPersonMobile;

    // 重定义告警级别ID
    private String levelId;

    // 重定义告警级别名称
    private String levelName;

    // 重定义告警内容
    private String redefineContent;

    // 重定义原始告警内容
    private String originalContent;

    // 重定义告警字段
    private String redefineColumn;

    // 重定义类型，研发中心定义，1--过滤,2--升降级,3--重定义
    private String alarmFlowName;

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

    public String getNotifyPersonMail() {
        return notifyPersonMail;
    }

    public void setNotifyPersonMail(String notifyPersonMail) {
        this.notifyPersonMail = notifyPersonMail;
    }

    public String getNotifyPersonMobile() {
        return notifyPersonMobile;
    }

    public void setNotifyPersonMobile(String notifyPersonMobile) {
        this.notifyPersonMobile = notifyPersonMobile;
    }

    public String getRuleRegexp() {
        return ruleRegexp;
    }

    public void setRuleRegexp(String ruleRegexp) {
        this.ruleRegexp = ruleRegexp;
    }

    public String getClearNotify() {
        return clearNotify;
    }

    public void setClearNotify(String clearNotify) {
        this.clearNotify = clearNotify;
    }

    public String getNotifySMS() {
        return notifySMS;
    }

    public void setNotifySMS(String notifySMS) {
        this.notifySMS = notifySMS;
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

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleAction() {
        return ruleAction;
    }

    public void setRuleAction(String ruleAction) {
        this.ruleAction = ruleAction;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyPerson() {
        return notifyPerson;
    }

    public void setNotifyPerson(String notifyPerson) {
        this.notifyPerson = notifyPerson;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getRuleInfo() {
        return ruleInfo;
    }

    public void setRuleInfo(String ruleInfo) {
        this.ruleInfo = ruleInfo;
    }

    public String getNotifyPersonId() {
        return notifyPersonId;
    }

    public void setNotifyPersonId(String notifyPersonId) {
        this.notifyPersonId = notifyPersonId;
    }

    public String getRuleState() {
        return ruleState;
    }

    public void setRuleState(String ruleState) {
        this.ruleState = ruleState;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getRedefineContent() {
        return redefineContent;
    }

    public void setRedefineContent(String redefineContent) {
        this.redefineContent = redefineContent;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getRedefineColumn() {
        return redefineColumn;
    }

    public void setRedefineColumn(String redefineColumn) {
        this.redefineColumn = redefineColumn;
    }

    public String getAlarmFlowName() {
        return alarmFlowName;
    }

    public void setAlarmFlowName(String alarmFlowName) {
        this.alarmFlowName = alarmFlowName;
    }

}
