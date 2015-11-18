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
package com.cloudmaster.cmp.ITIL.cronexpression.web;

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionSearchAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     *
     */
    private static final long serialVersionUID = 2710534861112349245L;

    private static LogService logger = LogService.getLogger(CronExpressionSearchAction.class);

    /**
     * 为操作日志返回的功能名
     */
    protected String functionName = "";

    /**
     * 为操作日志返回的操作类型；
     */
    protected String opType = "";

    /**
     * 为操作日志返回的功能描述
     */
    protected String operationInfo = "";

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "cronExpressionManager";

    /**
     * 数据Bean
     */
    private CronExpressionManagerDomain domain = new CronExpressionManagerDomain();

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 周期任务列表
     */
    private List<?> list = new ArrayList<Object>();

    private String errorMsg;

    private String actionName;

    public String search() {
        logger.info(getText("function.title") + getText("log.search.begin"));
        try {
            if (null != domain.getTaskName() && !domain.getTaskName().equals("")) {
                domain.setTaskName(SqlUtil.specialToNormal(domain.getTaskName()));
            }
            list = getPage(IBATIS_NAMESPACE + ".getCount", IBATIS_NAMESPACE + ".list", domain);
            if (0 >= list.size() || null == list) {
                operationInfo = getText("oplog.list.error") + getText("common.message.searchNodata");
                msg = getText("common.message.searchNodata");
                forward = ERROR;
                return forward;
            }
            operationInfo = getText("oplog.search.success");
            logger.info(getText("function.title") + getText("log.search.end"));
        } catch (Exception ex) {
            operationInfo = getText("oplog.search.error") + getText("cronExpression.error.sql");
            errorMsg = getText("common.message.searchError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.search.error"),ex);
            forward = ERROR;
            return forward;
        }
        return forward;
    }

    @Override
    public String getOpType() {
        return null;
    }

    @Override
    public String getOperationFunction() {
        return null;
    }

    @Override
    public String getOperationInfo() {
        return null;
    }

    public CronExpressionManagerDomain getDomain() {
        return domain;
    }

    public void setDomain(CronExpressionManagerDomain domain) {
        this.domain = domain;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

}
