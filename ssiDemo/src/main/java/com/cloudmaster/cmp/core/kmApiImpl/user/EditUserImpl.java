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

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.core.ResultCodeEnum;
import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApi.user.EditUserException;
import com.cloudmaster.cmp.core.kmApi.user.IEditUser;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class EditUserImpl implements IEditUser {
    private IbatisDAO ibatisDAO;

    private static LogService logger = LogService.getLogger(EditUserImpl.class);

    @Override
    public ResultInfo editUser(UserInfo userInfo) throws EditUserException {
        try {
            // 判断是否有该用户
            int userCount = ibatisDAO.getCount("UserInfo.getUserCount", userInfo);
            if (userCount == 0) {
                if (logger.isDebugEnable()) {
                    logger.debug("数据库中不存在ID为 [" + userInfo.getUserId() + "] 用戶["
                            + userInfo.getUserName() + "] !");
                }
                return getResult("error", "数据库中不存在ID为 [" + userInfo.getUserId() + "]  的用戶 ["
                        + userInfo.getUserName() + "] !");
            } else {
                ibatisDAO.updateData("UserInfo.updateUserInfo", userInfo);
                return getResult("success", "更新用戶ID为 [" + userInfo.getUserId() + "]  的用戶 ["
                        + userInfo.getUserName() + "] !");
            }
        } catch (Exception e) {
            throw new EditUserException(e);
        }
    }

    @Override
    public UserResultset editUsers(List<UserInfo> userInfo) throws EditUserException {

        List<UserInfo> errUserList = new ArrayList<UserInfo>();
        List<UserInfo> userList = new ArrayList<UserInfo>();
        UserResultset result = new UserResultset();

        // 判断是否有该用户
        for (int i = 0; i < userInfo.size(); i++) {
            try {
                int userCount = ibatisDAO.getCount("UserInfo.getUserCount", userInfo.get(i));
                if (userCount == 0) {
                    if (logger.isDebugEnable()) {
                        logger.debug("数据库中不存在ID为 [" + userInfo.get(i).getUserId() + "]  的用戶 ["
                                + userInfo.get(i).getUserName() + "] !");
                    }
                    // 添加至错误用户列表
                    errUserList.add(userInfo.get(i));
                } else {
                    ibatisDAO.updateData("UserInfo.updateUserInfo", userInfo.get(i));
                    if (logger.isDebugEnable()) {
                        logger.debug("更新用戶ID为 [" + userInfo.get(i).getUserId() + "] 的用戶 ["
                                + userInfo.get(i).getUserName() + "]!");
                    }
                    // 添加至成功List
                    userList.add(userInfo.get(i));
                }
            } catch (Exception e) {
                // throw new EditUserException(e);
                logger.error(ResultCodeEnum.FALSE, "更新用户 [" + userInfo.get(i).getUserName()
                        + "]异常！", e);
                errUserList.add(userInfo.get(i));
            }
        }
        result.setErrUserInfoList(errUserList);
        result.setUserInfoList(userList);

        return result;
    }

    private ResultInfo getResult(String isSuccess, String message) {
        ResultInfo result = new ResultInfo();
        if (isSuccess.equals("success")) {
            result.setResultCode(ResultCodeEnum.SUCCESS);
        } else {
            result.setResultCode(ResultCodeEnum.FALSE);
        }
        result.setMessage(message);
        return result;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
