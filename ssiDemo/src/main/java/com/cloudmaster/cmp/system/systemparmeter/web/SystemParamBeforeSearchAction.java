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
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParamBeforeSearchAction extends BaseAction implements IOperationLog,
        IAuthIdGetter {


    /**
     * serial
     */
    private static final long serialVersionUID = -1883989635430182033L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> organizeList = new ArrayList<SystemParameterDomain>();

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> paramKeyList = new ArrayList<SystemParameterDomain>();

    private List<SystemParameterDomain> paramValueList = new ArrayList<SystemParameterDomain>();

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

    private static LogService logger = LogService.getLogger(SystemParamBeforeSearchAction.class);

    public String beforeSearch() {
        logger.info(getText("function.title") + getText("log.beforeSearch.begin"));
        logger.info(getText("function.title") + getText("log.beforeSearch.end")); 
        return forward;
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
        SystemParamBeforeSearchAction.logger = logger;
    }

    @Override
    public String getOpType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationFunction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SystemParameterDomain> getOrganizeList() {
        return organizeList;
    }

    public void setOrganizeList(List<SystemParameterDomain> organizeList) {
        this.organizeList = organizeList;
    }

    public List<SystemParameterDomain> getParamKeyList() {
        return paramKeyList;
    }

    public void setParamKeyList(List<SystemParameterDomain> paramKeyList) {
        this.paramKeyList = paramKeyList;
    }

    public List<SystemParameterDomain> getParamValueList() {
        return paramValueList;
    }

    public void setParamValueList(List<SystemParameterDomain> paramValueList) {
        this.paramValueList = paramValueList;
    }

}
