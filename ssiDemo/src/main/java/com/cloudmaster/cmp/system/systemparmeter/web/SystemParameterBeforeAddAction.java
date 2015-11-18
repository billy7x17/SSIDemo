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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParameterBeforeAddAction extends BaseAction implements  IAuthIdGetter, IOperationLog {

    private static final long serialVersionUID = 1L;

    /**
     * 信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private static LogService logger = LogService.getLogger(SystemParameterBeforeAddAction.class);

    /**
     * 系统参数添加前准备
     * @return
     */
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        return forward;
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
}
