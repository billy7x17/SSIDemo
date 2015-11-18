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

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public enum OperationLogKeys {
    /**
     * 运行平台产品ID
     */
    PRODUCT_ID("0"),
    /**
     * 运行平台产品版本
     */
    PRODUCT_VERSION("1"),
    /**
     * 运行平台产品名称
     */
    PRODUCT_NAME("IAMP系统"),
    /**
     * 运行平台服务器实例ID
     */
    SERVER_INS_ID("0"),
    /**
     * 运行平台服务器实例名称
     */
    SERVER_INS_NAME("IAMP系统服务器");

    private String strValue;

    OperationLogKeys(String str) {
        strValue = str;
    }

    public String toString() {
        return strValue;
    }
}
