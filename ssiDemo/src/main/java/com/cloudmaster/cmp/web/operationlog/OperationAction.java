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

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 根据SESSIONID显示用户的相关操作
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class OperationAction extends PageAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(OperationAction.class);

    private List<OperationLogInfo> operationLogInfo;

    private String sessionId;

    private String userId;

    private String logId;

    public String getSessionId() {
        return sessionId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<OperationLogInfo> getOperationLogInfo() {
        return operationLogInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String tolist(){
        return "SUCCESS";
    }

    @SuppressWarnings("unchecked")
    public String execute() {
        SearchLogInfo logInfo = new SearchLogInfo();
        try {
            HttpServletRequest jsprequest = ServletActionContext.getRequest();
            String rp = jsprequest.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            logInfo.setSessionId(sessionId);
            operationLogInfo = getPage("LogInfo.getOperationLogInfoCount",
                    "LogInfo.getOperationLogInfos", logInfo);
        } catch (Exception e) {
            logger.error(OperationStatusCode.LOG_SEARCH_OPERATION_FAILED,
                    getText("OperationAction.search.fail") + e.getMessage(), e);
            this.addActionError(getText("OperationAction.search.fail"));
            return "ERROR";
        }
        try {
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (OperationLogInfo ahd : operationLogInfo) {
                fgjd.setRowId(Long.toString(ahd.getLogId())); // 设置行标识
                fgjd.addColdata(ahd.getDateTime());// 设置此行 第一列内容
                fgjd.addColdata(ahd.getAction()); // 设置此行 第二列内容
                fgjd.addColdata(ahd.getOperationInfo()); // ……
            }
            fgjd.commitData(); // 提交数据
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
        } catch (Exception ex) {
            logger.info("组装flexigrid数据异常！" ,ex);
        }
        return null;
    }

}
