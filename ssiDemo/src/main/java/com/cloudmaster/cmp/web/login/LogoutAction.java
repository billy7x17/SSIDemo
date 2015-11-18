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
package com.cloudmaster.cmp.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.IOperationLogDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.cloudmaster.cmp.web.operationlog.OperationLogInfo;
import com.cloudmaster.cmp.web.operationlog.OperationLogKeys;
import com.cloudmaster.cmp.web.operationlog.OperationStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户登出系统时要清空session中的所有内容
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LogoutAction extends OperationLogAction {

    private static LogService logger = LogService.getLogger(LogoutAction.class);

    private static final long serialVersionUID = 1L;

    private transient IOperationLogDAO operationLogDAO;

    public void setOperationLogDAO(IOperationLogDAO operationLogDAO) {
        this.operationLogDAO = operationLogDAO;
    }

    public String execute() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        String operationInfo = null;
        if (null != session) {
            UserInfo userInfo = (UserInfo) session.getAttribute(SessionKeys.userInfo.toString());

            OperationLogInfo logInfo = new OperationLogInfo();

            String productId = OperationLogKeys.PRODUCT_ID.toString();
            String serverInsId = OperationLogKeys.SERVER_INS_ID.toString();
            String productVersion = OperationLogKeys.PRODUCT_VERSION.toString();
            logInfo.setProductId(productId);
            logInfo.setServerInsId(serverInsId);
            logInfo.setProductVersion(productVersion);
            logInfo.setIp(request.getRemoteAddr());
            logInfo.setSessionId(request.getSession().getId());

            if (null != userInfo) {
                String userId = userInfo.getUserId();
                String userName = userInfo.getUserName();
                operationInfo = MessageFormat.format(getText("user.logout.system"), userId,
                        userName);
                // 收集操作日志
                logInfo.setOperationInfo(operationInfo);
                logInfo.setAction(getOperationFunction());
                logInfo.setOpType(getOpType());
                logInfo.setUserId(userId);
                logInfo.setUserName(userName);

                try {
                    operationLogDAO.saveOperationLog(logInfo);
                } catch (SQLException e) {
                    logger.error(OperationStatusCode.LOG_INTERCEPTOR_FAILED,
                            getText("operationLog.save.failed") + e.getMessage(), e);
                }

                session.removeAttribute(SessionKeys.userInfo.toString());
                session.removeAttribute(SessionKeys.authenticater.toString());
                session.removeAttribute(SessionKeys.operationLogDAO.toString());
                session.invalidate();

            }
        }
        if (logger.isInfoEnable()) {
            logger.info(LoginStatusCode.LOGOUT_SUCCESS, operationInfo);
        }
        return "SUCCESS";
    }
}
