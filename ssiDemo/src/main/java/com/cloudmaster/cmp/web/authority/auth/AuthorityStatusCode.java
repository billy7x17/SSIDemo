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
package com.cloudmaster.cmp.web.authority.auth;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 权限管理模块的状态码
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public enum AuthorityStatusCode implements StatusCode {

    // 从数据库中读取信息时异常
    READ_DB_EXCEPTION("40401"),

    // 向数据库中插入信息时异常
    INSERT_DB_EXCEPTION("40402"),

    // 更新数据库信息时异常
    UPDATE_DB_EXCEPTION("40403"),

    // 批量注销时异常
    BATCH_UPDATE_EXCEPTION("40404"),

    // 向数据库中插入数据成功
    INSERT_DB_SUCCESS("40405"),

    // 更新数据库信息成功
    UPDATE_DB_SUCCESS("40406"),

    // 批量注销成功
    BATCH_UPDATE_SUCCESS("40407"),

    // 全局拦截器的状态码
    INTERCEPTION_EXCEPTION_CODE("40408");

    private final String code;

    AuthorityStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
