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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParamBeforeEditAction extends BaseAction implements IAuthIdGetter {


    /**
     * 
     */
    private static final long serialVersionUID = -8202517142456488460L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> systemParamList = new ArrayList<SystemParameterDomain>();

    /**
     * 系统参数实例
     */
    private SystemParameterDomain domain;

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

    private static LogService logger = LogService.getLogger(SystemParamBeforeEditAction.class);

    /**
     * 系统参数修改前准备
     * @return
     */
    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
             domain = (SystemParameterDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getSingle", domain);
            if (domain == null) {
                errorMsg = getText("common.message.editError")
                        + getText("message.beforeEdit.error");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("message.sql.error");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.beforeEdit.error"), e);
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
        SystemParamBeforeEditAction.logger = logger;
    }

}
