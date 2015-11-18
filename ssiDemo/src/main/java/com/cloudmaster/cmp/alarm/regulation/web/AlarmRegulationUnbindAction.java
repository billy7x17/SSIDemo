/**
 * @(#)AlarmRegulationUnbindAction.java 2013-4-16
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

import java.util.List;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-16 上午09:30:27
 */
public class AlarmRegulationUnbindAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5154409857316845494L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationDeleteAction.class);

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
     * 修改
     * @return String
     * @param
     */
    public String unbind() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {
            ibatisDAO.updateData(IBATIS_NAMESPACE + ".deleteRuleAction", domain);
            ibatisDAO.updateData(IBATIS_NAMESPACE + ".deleteRuleRedefine", domain);

            // 删除告警平台数据
            List<AlarmRegulationDomain> alarmPlatformli = ibatisDAOAlarm.getData(
                    "alarmPlatform.getRuleRelation", domain);
            if (null != alarmPlatformli && !alarmPlatformli.isEmpty()) {
                String id = "('";
                for (AlarmRegulationDomain bean : alarmPlatformli) {
                    id = id + bean.getID() + "','";
                }
                id = id + "')";
                AlarmRegulationDomain alarmPlatformbean = new AlarmRegulationDomain();
                alarmPlatformbean.setID(id);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleRelation", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleRedefine", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleLevel", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleNofity", alarmPlatformbean);
            }

            msg = getText("message.unBind.success");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("message.unBind.error") + getText("common.message.systemError");
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
