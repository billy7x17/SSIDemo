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
package com.cloudmaster.cmp.alarm.filter.web;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FilterBeforeAddAction extends BaseAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2086189858021404791L;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private static LogService logger = LogService.getLogger(FilterBeforeAddAction.class);

    /**
     * 添加前准备
     * @return
     */
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        return forward;
    }
}
