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
package com.cloudmaster.cmp.core.kmApiImpl.user;

import java.util.List;

import com.cloudmaster.cmp.web.authority.user.UserInfo;

/**
 * 用来定义用户列表信息
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserResultset {
    /**
     * 本次查询的结果总数
     */
    private int resultCount = 0;

    /**
     * 正确用户List
     */
    private List<UserInfo> userInfoList;

    /**
     * 错误用户List
     */
    private List<UserInfo> errUserInfoList;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<UserInfo> getErrUserInfoList() {
        return errUserInfoList;
    }

    public void setErrUserInfoList(List<UserInfo> errUserInfoList) {
        this.errUserInfoList = errUserInfoList;
    }
}
