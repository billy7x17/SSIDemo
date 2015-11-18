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
package com.cloudmaster.cmp.core.kmApi.user;

import java.util.List;

import com.cloudmaster.cmp.web.authority.user.UserInfo;

/**
 * 用户查询接口
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface ISearchUser {

    /**
     * 查询单用户
     * @param userInfo
     * @return
     * @throws SearchUserException
     */
    UserInfo searchUser(UserInfo userInfo) throws SearchUserException;

    /**
     * 查询用户
     * @param userInfo
     * @return
     * @throws SearchUserException
     */
    List<UserInfo> searchUsers(UserInfo userInfo) throws SearchUserException;

    /**
     * 分页查询用户
     * @param userInfo
     * @param skipResults
     * @param maxResults
     * @return
     * @throws SearchUserException
     */
    List<UserInfo> serchUsersByPage(UserInfo userInfo, int skipResult, int maxResult)
            throws SearchUserException;

    /**
     * 查询用户数量
     * @param userInfo
     * @return
     * @throws SearchUserException
     */
    int serchUserCount(UserInfo userInfo) throws SearchUserException;
    // UserResultset SearchUserList(UserInfo userInfo) throws SearchUserException;
    // UserResultset SerchUserByPage(UserInfo userInfo, int skipResult, int maxResult)
    // throws SearchUserException;
}
