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
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.cloudmaster.cmp.web.operationlog.OperationLogInfo;
import com.cloudmaster.cmp.web.operationlog.OperationLogKeys;
import com.cloudmaster.cmp.web.operationlog.OperationStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 拦截器，拦截ACTION，获取ACTION提供的操作功能和操作信息以及获取其它操作日志信息，入库
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LogInterceptor implements Interceptor {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 6301917745011112877L;

    /**
     * 日志相关
     */
    private static LogService logger = LogService.getLogger(LogInterceptor.class);

    private transient IOperationLogDAO operationLogDAO;

    public void setOperationLogDAO(IOperationLogDAO operationLogDAO) {
        this.operationLogDAO = operationLogDAO;
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        OperationLogInfo logInfo = new OperationLogInfo();

        // 执行action之前执行的interceptor的部分
        HttpServletRequest request = ServletActionContext.getRequest();

        if (null == request.getSession()) {
            return "LOGIN";
        }
        ai.invoke();

        // 执行action之后执行的interceptor的部分
        Object object = ai.getAction();

        if (object instanceof IOperationLog) {
            String operationInfo = ((IOperationLog) object).getOperationInfo();
            String operationFunction = ((IOperationLog) object).getOperationFunction();
            String opType = ((IOperationLog) object).getOpType();
            logInfo.setOperationInfo(operationInfo);
            logInfo.setAction(operationFunction);
            logInfo.setOpType(opType);

        } else {
            return null;
        }

        if (null == request.getSession()) {
            return "LOGIN";
        }
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                SessionKeys.userInfo.toString());
        if (null == userInfo) {
            return "LOGIN";
        }
        String userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        logInfo.setUserId(userId);
        logInfo.setUserName(userName);

        /**
         * 从ServletContextKeys中获取productID与server_ins_id
         */
        String productId = OperationLogKeys.PRODUCT_ID.toString();
        String serverInsId = OperationLogKeys.SERVER_INS_ID.toString();
        String productVersion = OperationLogKeys.PRODUCT_VERSION.toString();
        logInfo.setProductId(productId);
        logInfo.setServerInsId(serverInsId);
        logInfo.setProductVersion(productVersion);
        logInfo.setIp(request.getRemoteAddr());
        logInfo.setSessionId(request.getSession().getId());

        if (null == logInfo.getOperationInfo() || "".equals(logInfo.getOperationInfo())) {
            logger.warn(OperationStatusCode.LOG_INTERCEPTOR_NOOPERATIONINFO,
                    "+++++++++++++++%%%%%%%%%%%%%！！！！操作日志描述信息为空！！！%%%%%%%%%%%%++++++++++++++++++");
        } else if (null == logInfo.getAction() || "".equals(logInfo.getAction())) {
            logger.warn(OperationStatusCode.LOG_INTERCEPTOR_NOFUNCTIONNAME,
                    "+++++++++++++%%%%%%%%%%%！！！！操作日志业务模块信息为空！！！%%%%%%%%%%++++++++++++");
        } else if (null == logInfo.getOpType() || "".equals(logInfo.getOpType())) {
            logger.warn(OperationStatusCode.LOG_INTERCEPTOR_NOOPTYPE,
                    "+++++++++++++++%%%%%%%%%%%%%！！！！操作类型信息为空！！！%%%%%%%%%%%%++++++++++++++++++");
        } else {
            try {
                operationLogDAO.saveOperationLog(logInfo);
                if (logger.isDebugEnable()) {
                    logger.debug(LocalizedTextUtil.findDefaultText("operationLog.save.success",
                            new Locale("zh_CN")));
                }
            } catch (Exception e) {
                logger.error(OperationStatusCode.LOG_INTERCEPTOR_FAILED, LocalizedTextUtil
                        .findDefaultText("operationLog.save.failed", new Locale("zh_CN"))
                        + e.getMessage(), e);
                throw new Exception(e);
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    @Override
    public void init() {

    }

}
