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

//import com.neusoft.mid.iamp.logger.LogService;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.core.kmApi.user.ISearchUser;
import com.cloudmaster.cmp.core.kmApi.user.SearchUserException;
import com.cloudmaster.cmp.util.PageUtil;
import com.cloudmaster.cmp.web.authority.user.UserInfo;

/**
 * 用户管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SearchUserImpl implements ISearchUser {
    private IbatisDAO ibatisDAO;

    // private static LogService logger = LogService.getLogger(SearchUserImpl.class);

    @Override
    public UserInfo searchUser(UserInfo userInfo) throws SearchUserException {
        UserInfo result;
        try {
            result = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getUserInfo", userInfo);
        } catch (Exception e) {
            throw new SearchUserException(e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> searchUsers(UserInfo userInfo) throws SearchUserException {
        List<UserInfo> result;
        try {
            result = ibatisDAO.getData("UserInfo.getUserSearchInfos", userInfo);
        } catch (Exception e) {
            throw new SearchUserException(e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> serchUsersByPage(UserInfo userInfo, int skipResult, int maxResult)
            throws SearchUserException {
        List<UserInfo> result;
        try {
            int count = ibatisDAO.getCount("UserInfo.getUserSearchInfoCount", userInfo);
            // int pageSize = (skipResult - 1) * maxResult;
            PageUtil pu = new PageUtil(count, maxResult);
            result = ibatisDAO.getObjectsByPage("UserInfo.getUserSearchInfos", userInfo, pu
                    .getStartOfPage(skipResult), maxResult);
        } catch (Exception e) {
            throw new SearchUserException(e);
        }
        return result;
    }

    @Override
    public int serchUserCount(UserInfo userInfo) throws SearchUserException {
        // 用户个数
        int userCount = 0;
        try {
            userCount = ibatisDAO.getCount("UserInfo.getUserSearchInfoCount", userInfo);
        } catch (Exception e) {
            throw new SearchUserException(e);
        }
        return userCount;
    }
    
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
}
