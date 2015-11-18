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
package com.cloudmaster.cmp.core;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * KM通用状态码
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public enum CommonStatusCode implements StatusCode {
    /**
     * 执行查询SQL错误时的状态码
     */
    SQL_SELECT_ERROR("81001"),
    /**
     * 执行更新SQL错误时的状态码
     */
    SQL_UPDATE_ERROR("81002"),
    /**
     * 执行添加SQL错误时的状态码
     */
    SQL_INSERT_ERROR("81003"),
    /**
     * 执行删除SQL错误时的状态码
     */
    SQL_DELETE_ERROR("81004"),
    /**
     * 执行SQL批量操作错误时的状态码
     */
    SQL_BATCH_ERROR("81005"),
    /**
     * SQL其它错误时的状态码
     */
    SQL_OTHER_ERROR("81006"),
    /**
     * 运行时异常
     */
    COMMON_RUNTIME_EXCEPTION("82001"),
    /**
     * 业务处理时异常
     */
    COMMON_BUSINESS_EXCEPTION("83001"),
    /**
     * WebService异常
     */
    COMMON_WEBSERVICE_EXCEPTION("84001"),
    /**
     * 通用警告
     */
    COMMON_WARN("85001"),
    /**
     * Schema解析转换异常
     */
    COMMON_SCHEMA_EXCEPTION("86001");

    private final String code;

    CommonStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
