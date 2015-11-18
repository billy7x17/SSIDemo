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
package com.cloudmaster.cmp.web.operationlog;

import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 浏览操作日志详细信息的ACTION
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LogSearchDetailAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(LogSearchDetailAction.class);

    private OperationLogInfo operationLogInfo;

    public OperationLogInfo getOperationLogInfo() {
        return operationLogInfo;
    }

    private String logId;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String execute() {
        try {
            operationLogInfo = (OperationLogInfo) ibatisDAO.getSingleRecord("LogInfo.getLogInfo",
                    logId);
        } catch (Exception e) {
            logger.error(OperationStatusCode.LOG_SEARCH_DETAIL_FAILED,
                    getText("LogSearchDetailAction.search.fail") + e.getMessage(), e);
            this.addActionError(getText("LogSearchDetailAction.search.fail"));
            return "ERROR";
        }
        return "SUCCESS";
    }

}
