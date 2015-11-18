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

import java.util.HashMap;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionBeforeSearchAction extends BaseAction implements IOperationLog,
        IAuthIdGetter {

    /**
     * 
     */
    private static final long serialVersionUID = 1428150259649115795L;

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 任务类型
     */
    private HashMap<String, String> taskType = new HashMap<String, String>();

    /**
     * 异常提示信息
     */
    private String errorMsg;

    private static LogService logger = LogService.getLogger(CronExpressionAddAction.class);

    /**
     * 查询跳转页面
     * @return forward
     */
    public String beforeSearch() {
        logger.info(getText("function.title") + getText("log.beforeSearch.begin"));
        try {
            logger.info(getText("function.title") + getText("log.beforeSearch.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeSearch.error"),e);
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HashMap<String, String> getTaskType() {
        return taskType;
    }

    public void setTaskType(HashMap<String, String> taskType) {
        this.taskType = taskType;
    }
}
