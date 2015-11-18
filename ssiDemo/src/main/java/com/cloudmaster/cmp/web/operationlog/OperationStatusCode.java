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

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public enum OperationStatusCode implements StatusCode {

    /**
     * 操作日志的浏览失败
     */
    LOG_BROWSE_FAILED("40501"),
    /**
     * 操作日志的高级搜索失败
     */
    LOG_SEARCH_FAILED("40502"),
    /**
     * 操作日志的搜索失败
     */
    LOG_SEARCH_FINDBYID_FAILED("40503"),
    /**
     * 操作日志的搜索详细浏览失败
     */
    LOG_SEARCH_DETAIL_FAILED("40504"),
    /**
     * 操作日志的相关操作失败
     */
    LOG_SEARCH_OPERATION_FAILED("40505"),
    /**
     * 操作日志保存失败
     */
    LOG_INTERCEPTOR_FAILED("40506"),
    /**
     * 操作日志保存成功
     */
    LOG_INTERCEPTOR_SUCCESS("40507"),
    /**
     * 此模块没有配置操作日志
     */
    LOG_INTERCEPTOR_NOCONFIG("40508"),
    /**
     * session为空
     */
    LOG_INTERCEPTOR_NOSESSION("40509"),
    /**
     * operationInfo为空
     */
    LOG_INTERCEPTOR_NOOPERATIONINFO("40510"),
    /**
     * functionName为空
     */
    LOG_INTERCEPTOR_NOFUNCTIONNAME("40511"),
    /**
     * opType为空
     */
    LOG_INTERCEPTOR_NOOPTYPE("40512");

    private final String code;

    OperationStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
