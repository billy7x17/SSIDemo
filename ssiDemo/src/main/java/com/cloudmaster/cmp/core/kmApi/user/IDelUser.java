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

import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApiImpl.user.UserResultset;
import com.cloudmaster.cmp.web.authority.user.UserInfo;

/**
 * 用户删除接口
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IDelUser {

    /**
     * 单用户删除
     * @param userInfo
     * @return
     * @throws DelUserException
     */
    ResultInfo delUser(UserInfo userInfo) throws DelUserException;

    /**
     * 用户批量删除
     * @param userInfo
     * @return
     * @throws DelUserException
     */
    UserResultset delUsers(List<UserInfo> userInfo) throws DelUserException;

}
