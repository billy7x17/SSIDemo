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
package com.cloudmaster.cmp.alarm.regulation.web;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除数据
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationSuspendAction extends OperationLogAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5154409857316845494L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationSuspendAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmRegulationDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmRegulation";

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 告警平台数据库连接
     */
    private IbatisDAO ibatisDAOAlarm;

    /**
     * 中止
     * @return String
     * @param
     */
    public String suspend() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            domain.setRuleState("1"); // 0代表激活，1代表终止
            ibatisDAOAlarm.updateData("alarmPlatform.updateRuleState", domain);

            domain.setRuleState("1");// 0-正常，1-已终止，2-已删除
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".updateState", domain);

            msg = getText("message.suspend.success");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("message.suspend.error") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    /**
     * 激活
     * @return String
     * @param
     */
    public String activation() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            domain.setRuleState("0"); // 0代表激活，1代表终止
            ibatisDAOAlarm.updateData("alarmPlatform.updateRuleState", domain);

            domain.setRuleState("0");// 0-正常，1-已终止，2-已删除
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".updateState", domain);

            msg = getText("message.activation.success");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("message.activation.error") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    public AlarmRegulationDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRegulationDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public IbatisDAO getIbatisDAOAlarm() {
        return ibatisDAOAlarm;
    }

    public void setIbatisDAOAlarm(IbatisDAO ibatisDAOAlarm) {
        this.ibatisDAOAlarm = ibatisDAOAlarm;
    }

}
