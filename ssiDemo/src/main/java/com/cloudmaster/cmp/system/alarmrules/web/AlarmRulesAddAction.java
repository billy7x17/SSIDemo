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

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.alarmrules.dao.AlarmRulesDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRulesAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 
     */
    private static final long serialVersionUID = 1210275135505558630L;

    /*
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmRulesList = new ArrayList<AlarmRulesDomain>();

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

    private static LogService logger = LogService.getLogger(AlarmRulesAddAction.class);

    /**
     * 告警配置添加
     * @return String
     * @param
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String typeName = "";
        try {
            typeName = ((AlarmRulesDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getTypeID", alarmrules)).getAgentName();
        } catch (SQLException e1) {
            logger.error(getText("function.title") + getText("log.add.error"), e1);
        }
        String opParam[] = { getText("alarmrules.typeID") + ": " + typeName };
        try {
            if (null != alarmrules && null != alarmrules.getAlarmTitle()
                    && !alarmrules.getAlarmTitle().equals("")) {
                AlarmRulesDomain titleBean = (AlarmRulesDomain) ibatisDAO.getSingleRecord(
                        IBATIS_NAMESPACE + ".getAlarmTitle", alarmrules);

                if (null != titleBean) {
                    alarmrules.setAlarmIdentityID(titleBean.getAlarmIdentityID());
                }
            }

            alarmrules = (AlarmRulesDomain) ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert",
                    alarmrules);
            msg = getText("common.message.addSuccess");
            operationInfo = getText("oplog.add.success", opParam);
            logger.info(getText("function.title") + getText("log.add.end"));

        } catch (Exception e) {
            logger.error(getText("function.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
            forward = "INPUT";
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
        AlarmRulesAddAction.logger = logger;
    }

}
