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

import java.util.List;

/**
 * 权限点信息的javaBean
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class AuthInfo {
    // 权限点ID
    private String authId;

    // 权限点名称
    private String authName;

    // 权限点描述
    private String description;
    /**
     * 父id
     */
    private String pid = "";

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    // 该权限点的下一级权限点所组成的list
    private List<AuthInfo> authSubList;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("authId:" + authId);
        builder.append(",authName:" + authName);
        builder.append(",authSubList.size():" + authSubList.size());
        return builder.toString();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AuthInfo> getAuthSubList() {
        return authSubList;
    }

    public void setAuthSubList(List<AuthInfo> authSubList) {
        this.authSubList = authSubList;
    }

}
