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
package com.cloudmaster.cmp.system.systemparmeter.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParameterDeleteAction extends OperationLogAction implements IAuthIdGetter {
   

    /**
     * serial
     */
    private static final long serialVersionUID = -1178706892735675058L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> systemParamList = new ArrayList<SystemParameterDomain>();

    /**
     * 系统参数实例
     */
    private SystemParameterDomain domain = new SystemParameterDomain();

    /**
     * 错误信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "systemparameter";

    private static LogService logger = LogService.getLogger(SystemParameterDeleteAction.class);

    /**
     * 系统参数删除
     * @return String
     * @param
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String opParam[] = { getText("field.label.paramKey") + ": " + domain.getParamKey() };
        try {
            int j = ibatisDAO.getCount(IBATIS_NAMESPACE + ".getOrganize", domain);
            if (j > 0) {
                int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".delete", domain);
                if (i > 0) {
                    msg = getText("common.message.delSuccess");
                    operationInfo = getText("oplog.delete.success", opParam);
                } else {
                    errorMsg = getText("common.message.delError") + getText("message.sql.error");
                    operationInfo = getText("oplog.delete.error", opParam)
                            + getText("message.sql.error");
                    forward = "ERROR";
                }
            } else {
                errorMsg = getText("common.message.delError") + getText("message.delete.exist");
                operationInfo = getText("oplog.delete.error", opParam) + errorMsg;
                forward = "ERROR";
            }

            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam) + getText("common.message.systemError");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.delete.error"), e);
        }
        return forward;
    }

    public List<SystemParameterDomain> getSystemParamList() {
        return systemParamList;
    }

    public void setSystemParamList(List<SystemParameterDomain> systemParamList) {
        this.systemParamList = systemParamList;
    }

    public SystemParameterDomain getDomain() {
        return domain;
    }

    public void setDomain(SystemParameterDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        SystemParameterDeleteAction.logger = logger;
    }

}
