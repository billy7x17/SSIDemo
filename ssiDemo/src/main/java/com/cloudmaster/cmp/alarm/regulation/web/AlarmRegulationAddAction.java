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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 规则添加
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4746719597111641998L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationAddAction.class);

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
     * 告警花名冊添加
     * @return String
     * @param
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            int j = ibatisDAO.getCount(IBATIS_NAMESPACE + ".existCount", domain);
            if (j > 0) {
                errorMsg = getText("common.message.addError") + getText("message.add.dataExsit");
                operationInfo = getText("oplog.add.error", opParam)
                        + getText("message.add.dataExsit");
                forward = "INPUT";
                return forward;
            }

            // 生成规则表达式
            String regexp = RegulationUtil.json2regexp(domain.getRuleInfo());
            domain.setRuleRegexp(regexp);

            // 生成规则xml
            String regXml = RegulationUtil.json2regXml(domain.getRuleInfo(), domain.getRuleName(),
                    ruleXmlPath);

            // 向研发中心同步规则xml
            boolean isOk = RegulationUtil.ruleXmlSync(domain.getRuleName(), regXml, ruleXmlSyncUrl,
                    "1");
            if (!isOk) {
                throw new Exception("同步规则xml信息失败");
            }

            ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert", domain);

            msg = getText("common.message.addSuccess");
            logger.info(getText("function.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
            forward = "INPUT";
        }
        return forward;
    }

    /**
     * 页面ajax校验规则名称唯一
     */
    public void validatorRuleName() {
        String result = "";
        try {
            int count = ibatisDAO.getCount(IBATIS_NAMESPACE + ".existCount", domain);
            if (count > 0) {
                result = "exist";
            }
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public AlarmRegulationDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRegulationDomain domain) {
        this.domain = domain;
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
