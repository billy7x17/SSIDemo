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

import net.sf.json.JSONArray;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.alarm.regulation.dao.RuleInfo;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationEditAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5990414034562982193L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationEditAction.class);

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
    public String edit() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            String json = domain.getRuleInfo();
            JSONArray array = JSONArray.fromObject(json);
            List<RuleInfo> ruleInfo = (List<RuleInfo>) JSONArray
                    .toCollection(array, RuleInfo.class);

            String regexp = RegulationUtil.json2regexp(domain.getRuleInfo());
            domain.setRuleRegexp(regexp);

            // 生成规则xml
            String regXml = RegulationUtil.json2regXml(domain.getRuleInfo(), domain.getRuleName(),
                    ruleXmlPath);
            // 向研发中心同步规则xml
            boolean isOk = RegulationUtil.ruleXmlSync(domain.getRuleName(), regXml, ruleXmlSyncUrl,
                    "2");
            if (!isOk) {
                throw new Exception("同步规则xml信息失败");
            }

            ibatisDAO.updateData(IBATIS_NAMESPACE + ".edit", domain);

            msg = getText("common.message.editSuccess");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            forward = "INPUT";
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
