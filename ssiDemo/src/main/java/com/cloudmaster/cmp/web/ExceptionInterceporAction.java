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
package com.cloudmaster.cmp.web;

import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 异常拦截器拦截异常后，会跳到该类中
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ExceptionInterceporAction {
    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 日志相关
     */
    private static LogService logger = LogService.getLogger(ExceptionInterceporAction.class);

    public String execute() throws Exception {
        logger.error(AuthorityStatusCode.INTERCEPTION_EXCEPTION_CODE, "拦截器拦截到一个异常", exception);
        return "SUCCESS";
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

}
