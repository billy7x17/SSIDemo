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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.alarmrules.dao.AlarmRulesDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class alarmrulesEditAction extends OperationLogAction implements IAuthIdGetter {
    private static final long serialVersionUID = 1L;

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

    private static LogService logger = LogService.getLogger(alarmrulesEditAction.class);

    /**
     * 告警配置修改
     * @return String
     * @param
     */
    public String edit() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String typeName = "";
        try {
            typeName = ((AlarmRulesDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getTypeID", alarmrules)).getAgentName();
        } catch (SQLException e1) {
            logger.error(getText("function.title") + getText("log.edit.error"), e1);
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

            int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".update", alarmrules);
            if (i > 0) {
                msg = getText("common.message.editSuccess");
                operationInfo = getText("oplog.edit.success", opParam);
                logger.info(getText("function.title") + getText("log.edit.end"));
            } else {
                errorMsg = getText("common.message.editError") + getText("message.sql.error");
                operationInfo = getText("oplog.edit.error", opParam) + getText("message.sql.error");
                forward = "INPUT";
                logger.info(getText("function.title") + getText("log.edit.end"));
            }

        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.error(getText("function.title") + getText("log.edit.error"), e);

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
        alarmrulesEditAction.logger = logger;
    }

}
