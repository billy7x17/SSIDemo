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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 详细信息
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationDetailAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8533093569731122556L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationDetailAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmRegulationDomain domain;

    /**
     * bean对象
     */
    private AlarmRegulationDomain actionDomain = new AlarmRegulationDomain();

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmRegulation";

    /**
     * 错误消息
     */
    private String errorMsg;
    
    /**
     * 重定义内容
     */
    private List<AlarmRegulationDomain> redefineContentList;

    /**
     * 修改
     * @return String
     * @param
     */
    public String detail() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {
            domain = (AlarmRegulationDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getInfo", domain);

            List li = (List) ibatisDAO.getData(IBATIS_NAMESPACE + ".getRuleAction", domain);
            if (null != li && !li.isEmpty()) {
                String ruleAction = "";
                Iterator it = li.iterator();
                while (it.hasNext()) {
                    AlarmRegulationDomain bean = (AlarmRegulationDomain) it.next();
                    if (bean.getRuleAction().equals("1")) {
                        ruleAction += "1";
                    } else if (bean.getRuleAction().equals("2")) {
                        ruleAction += "2";
                    } else if (bean.getRuleAction().equals("3")) {
                        actionDomain = bean;
                        ruleAction += "3";
                    } else if (bean.getRuleAction().equals("4")) {
                        ruleAction += "4";
                    } else {
                        redefineContentList = (List) ibatisDAO.getData(IBATIS_NAMESPACE
                                + ".getRedefineContent", domain);
                        ruleAction += "5";
                    }
                }
                actionDomain.setRuleAction(ruleAction);
                // 只重定义级别，没有重定义内容
                if (null == redefineContentList || redefineContentList.isEmpty()) {
                    redefineContentList = new ArrayList<AlarmRegulationDomain>();
                } else if (redefineContentList.size() == 1
                        && (null == redefineContentList.get(0).getOriginalContent() || redefineContentList
                                .get(0).getOriginalContent().equals(""))) {
                    domain.setLevelName(redefineContentList.get(0).getLevelName());
                    redefineContentList = new ArrayList<AlarmRegulationDomain>();
                } else {
                    domain.setLevelName(redefineContentList.get(0).getLevelName());
                }
            }

            if (null == domain) {
                errorMsg = getText("common.message.detailError") + getText("message.data.null");
                operationInfo = getText("oplog.detail.error", opParam)
                        + getText("message.data.null");
            } else {
                operationInfo = getText("oplog.detail.success", opParam);
            }

            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.detail.error"), e);
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
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

    public AlarmRegulationDomain getActionDomain() {
        return actionDomain;
    }

    public void setActionDomain(AlarmRegulationDomain actionDomain) {
        this.actionDomain = actionDomain;
    }

    public List<AlarmRegulationDomain> getRedefineContentList() {
        return redefineContentList;
    }

    public void setRedefineContentList(List<AlarmRegulationDomain> redefineContentList) {
        this.redefineContentList = redefineContentList;
    }

}
