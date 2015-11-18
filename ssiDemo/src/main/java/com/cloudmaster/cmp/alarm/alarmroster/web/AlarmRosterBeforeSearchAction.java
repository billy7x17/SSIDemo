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
package com.cloudmaster.cmp.alarm.alarmroster.web;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.alarm.alarmaoster.dao.AlarmRosterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosterBeforeSearchAction extends BaseAction implements IOperationLog,
        IAuthIdGetter {
    private static final long serialVersionUID = 1L;

    /**
     * 告警花名册Bean
     */
    private AlarmRosterDomain alarmroster;

    /**
     * 信息提示
     */
    private String message;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private String errorMsg;

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmRoster";

    private static LogService logger = LogService.getLogger(AlarmRosterBeforeSearchAction.class);

    /**
     * 告警等级列表
     */
    private List<AlarmRosterDomain> levelList;

    /**
     * 告警花名冊高级查询前准备
     * @return String
     * @param
     */
    public String beforeSearch() {
        logger.info(getText("function.title") + getText("log.beforeSearch.begin"));

        try {
            // 告警级别列表
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevelList", null);
        } catch (Exception e) {
            logger.error(getText("function.title") + getText("log.beforeAdd.error"), e);
        }
        logger.info(getText("function.title") + getText("log.beforeSearch.end"));
        return forward;
    }

    public AlarmRosterDomain getAlarmroster() {
        return alarmroster;
    }

    public void setAlarmroster(AlarmRosterDomain alarmroster) {
        this.alarmroster = alarmroster;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRosterBeforeSearchAction.logger = logger;
    }

    @Override
    public String getOpType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationFunction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<AlarmRosterDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<AlarmRosterDomain> levelList) {
        this.levelList = levelList;
    }

}
