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
 * 定义接口返回的状态码
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public enum ResultCodeEnum implements StatusCode {
    SUCCESS("0"), FALSE("1"),RESULT_CODE_SUCCESS("00000000");

    private ResultCodeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String toString() {
        return value;
    }
}
