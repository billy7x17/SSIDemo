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
package com.cloudmaster.cmp.system.alarmrules.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.system.alarmrules.dao.AlarmRulesDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class alarmrulesBeforeEditAction extends BaseAction implements IAuthIdGetter {

    /**
     * 
     */
    private static final long serialVersionUID = -8071792019402180372L;

    /*
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmRulesList = new ArrayList<AlarmRulesDomain>();

    /*
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmTitleList = new ArrayList<AlarmRulesDomain>();

    /*
     * 结果列表
     */
    private List<AlarmRulesDomain> LevelList = new ArrayList<AlarmRulesDomain>();

    /**
     * 告警配置Bean
     */
    private AlarmRulesDomain alarmrules = new AlarmRulesDomain();

    /**
     * 错误信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmrules";

    private static LogService logger = LogService.getLogger(alarmrulesBeforeEditAction.class);

    /**
     * 修改前准备
     * @return
     */
    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            // String rowID = request.getParameter("rowID");
            // alarmrules.setRowID(Integer.parseInt(rowID));
            alarmrules = (AlarmRulesDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getSingle", alarmrules);
            alarmRulesList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getTypeID", null);
            LevelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getLevelName", alarmrules);

            String titleCondition = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getDeviceTitleCondition", alarmrules.getTypeID());
            AlarmRulesDomain titleParam = new AlarmRulesDomain();
            titleParam.setType("(type='1' or type='4')");
            titleParam.setAlarmTitleCondition(titleCondition);
            alarmTitleList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmTitle", titleParam);

            if (alarmrules == null) {
                errorMsg = getText("common.message.editError")
                        + getText("message.beforeEdit.error");
                forward = "ERROR";
            } else if (alarmRulesList.size() == 0) {
                errorMsg = getText("common.message.editError")
                        + getText("message.beforeEdit.error");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("message.sql.error");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.beforeEdit.error"), e);
        }
        return forward;
    }

    public List<AlarmRulesDomain> getAlarmRulesList() {
        return alarmRulesList;
    }

    public void setAlarmRulesList(List<AlarmRulesDomain> alarmRulesList) {
        this.alarmRulesList = alarmRulesList;
    }

    public AlarmRulesDomain getAlarmrules() {
        return alarmrules;
    }

    public void setAlarmrules(AlarmRulesDomain alarmrules) {
        this.alarmrules = alarmrules;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        alarmrulesBeforeEditAction.logger = logger;
    }

    public List<AlarmRulesDomain> getLevelList() {
        return LevelList;
    }

    public void setLevelList(List<AlarmRulesDomain> levelList) {
        LevelList = levelList;
    }

    public List<AlarmRulesDomain> getAlarmTitleList() {
        return alarmTitleList;
    }

    public void setAlarmTitleList(List<AlarmRulesDomain> alarmTitleList) {
        this.alarmTitleList = alarmTitleList;
    }

}
