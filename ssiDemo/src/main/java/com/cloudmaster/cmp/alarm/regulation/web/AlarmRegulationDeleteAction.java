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

import java.util.List;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除数据
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationDeleteAction extends OperationLogAction implements IAuthIdGetter {

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
     * 项研发中心同步xml的url
     */
    private String ruleXmlSyncUrl;

    /**
     * 规则xml的存放路径
     */
    private String ruleXmlPath;

    /**
     * 修改
     * @return String
     * @param
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            // 删除规则xml
            RegulationUtil.deleteregXml(domain.getRuleName(), ruleXmlPath);
            String regXml = "";
            // 向研发中心同步规则xml
            boolean isOk = RegulationUtil.ruleXmlSync(domain.getRuleName(), regXml, ruleXmlSyncUrl,
                    "3");
            if (!isOk) {
                throw new Exception("同步规则xml信息失败");
            }

            domain.setRuleState("2"); // 0-正常，1-已终止，2-已删除
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".updateDeleteState", domain);

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

            msg = getText("common.message.delSuccess");
            logger.info(getText("function.title") + getText("log.delete.end"));
            operationInfo = getText("oplog.delete.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
            forward = "ERROR";
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

    public String getRuleXmlSyncUrl() {
        return ruleXmlSyncUrl;
    }

    public void setRuleXmlSyncUrl(String ruleXmlSyncUrl) {
        this.ruleXmlSyncUrl = ruleXmlSyncUrl;
    }

    public String getRuleXmlPath() {
        return ruleXmlPath;
    }

    public void setRuleXmlPath(String ruleXmlPath) {
        this.ruleXmlPath = ruleXmlPath;
    }

}
