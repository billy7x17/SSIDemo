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
package com.cloudmaster.cmp.core.kmApi.object;

/**
 * 用户抽象类，用来控制权限
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public abstract class UserBase {
    /**
     * 用户ID
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
