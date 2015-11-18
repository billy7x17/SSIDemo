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
package com.cloudmaster.cmp.operationlog;

/**
 * 操作日志操作类型枚举
 * @author <a href="mailto:hanbj@neusoft.com">baojun.han </a>
 * @version 1.0.0 18 Mar 2012
 */
public enum OperationLogOpType {
    // 业务数据增加
    ADD("Add"),

    // 业务数据更新
    UPDATE("Update"),

    // 业务数据删除
    DEL("Del"),

    // 业务数据查阅
    VIEW("View"),

    // 登录应用资源
    LOGIN("Login"),

    // 登出应用资源
    LOGOUT("Logout"),

    // 用户添加和权限相关的任何信息，包括添加用户、用户组、权限、角色等
    ADD_PRIVILEGE("AddPrivilege"),

    // 用户删除和权限相关的任何信息，包括添加用户、用户组、权限、角色等
    DEL_PRIVILEGE("DelPrivilege"),

    // 用户更新和权限相关的任何信息，包括添加用户、用户组、权限、角色等
    UPDATE_PRIVILEGE("UpdatePrivilege"),

    // 其他类别
    OTHER("Other");

    OperationLogOpType(String value) {
        this.value = value;
    }

    private String value;

    public String toString() {
        return value;
    }
}
