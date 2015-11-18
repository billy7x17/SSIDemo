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
import java.util.List;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.web.operationlog.OperationLogInfo;

/**
 * 向数据库中插入操作日志的DAO
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class OperationLogDAO implements IOperationLogDAO {

    private IbatisDAO ibatisDAO;

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 插入单行记录
     * @param statement
     * @param business
     * @return
     * @throws SQLException
     */
    public void saveOperationLog(OperationLogInfo logInfo) throws SQLException {
        String info = logInfo.getOperationInfo();
        if(null != info && info.length()>512){
            logInfo.setOperationInfo(info.substring(0, 512));
        }
        ibatisDAO.insertData("LogInfo.insertOperationLog", logInfo);
    }

    /**
     * 通过报表ID获取报表名称
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getReportName(String reportId) throws SQLException {
//        List<ReportInfo> list = ibatisDAO.getData("getReportName", reportId);
        String reportName = null;
//        if (null != list) {
//            if (list.size() > 0) {
//                reportName = list.get(0).getReportName();
//            }
//        }
        return reportName;
    }

}
