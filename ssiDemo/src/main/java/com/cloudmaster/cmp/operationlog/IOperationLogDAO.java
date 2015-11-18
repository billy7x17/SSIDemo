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
package com.cloudmaster.cmp.operationlog;

import java.sql.SQLException;

import com.cloudmaster.cmp.web.operationlog.OperationLogInfo;

/**
 * 向数据库中插入操作日志的接口
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IOperationLogDAO {

    /**
     * 向数据库中插入操作日志的方法
     * @param logInfo
     * @throws SQLException
     */
    void saveOperationLog(OperationLogInfo logInfo) throws SQLException;

    String getReportName(String reportId) throws SQLException;
}
