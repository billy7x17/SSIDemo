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
package com.cloudmaster.cmp.web.login;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public enum LoginStatusCode implements StatusCode {

    // 登录失败
    LOGIN_FAILURE("40409"),

    // 登录成功
    LOGIN_SUCCESS("40410"),

    // 登出成功
    LOGOUT_SUCCESS("40411"),

    // 从数据库中读取信息时异常
    LOGIN_EXCEPTION("40412");

    private final String code;

    LoginStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
