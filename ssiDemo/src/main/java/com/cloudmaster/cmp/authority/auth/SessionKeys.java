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
package com.cloudmaster.cmp.authority.auth;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public enum SessionKeys {
    /**
     * 用户信息对象
     */
    userInfo("userInfo"),

    /**
     * 鉴权对象
     */
    authenticater("authenticater"),

    /**
     * 用户积分信息
     */
    userTitleInfo("userTitleInfo"),

    /**
     * 操作日志的DAO
     */
    operationLogDAO("operationLogDAO");

    private String strValue;

    SessionKeys(String str) {
        strValue = str;
    }

    public String toString() {
        return strValue;
    }
}
