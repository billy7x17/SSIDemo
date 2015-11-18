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
import com.cloudmaster.cmp.core.kmApi.user.DelUserException;
import com.cloudmaster.cmp.core.kmApi.user.IDelUser;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DelUserImpl implements IDelUser {

    private IbatisDAO ibatisDAO;

    private static LogService logger = LogService.getLogger(DelUserImpl.class);

    @Override
    public ResultInfo delUser(UserInfo userInfo) throws DelUserException {
        try {
            int count = ibatisDAO.updateData("UserInfo.deleteUser", userInfo);
            int count1 = ibatisDAO.updateData("UserInfo.deleteUserRole", userInfo.getUserId());
            if (count == 1) {
                if (logger.isDebugEnable()) {
                    logger.debug("用戶ID为 [" + userInfo.getUserId() + "] 的用戶状态修改成功!");
                }
                return getResult("success", "用戶ID为 [" + userInfo.getUserId() + "] 的用戶状态修改成功!");
            } else {
                if (logger.isDebugEnable()) {
                    logger.debug("数据库中不存在ID为 [" + userInfo.getUserId() + "]  的用戶!");
                }
                return getResult("error", "数据库中不存在ID为 [" + userInfo.getUserId() + "]  的用戶!");
            }

        } catch (Exception e) {
            throw new DelUserException(e);
        }
    }

    @Override
    public UserResultset delUsers(List<UserInfo> userInfo) throws DelUserException {
        List<UserInfo> errUserList = new ArrayList<UserInfo>();
        List<UserInfo> userList = new ArrayList<UserInfo>();
        UserResultset result = new UserResultset();

        for (int i = 0; i < userInfo.size(); i++) {
            try {
                int count = ibatisDAO.updateData("UserInfo.deleteUser", userInfo.get(i));
                int count1 = ibatisDAO.updateData("UserInfo.deleteUserRole", userInfo.get(i));
                if (count == 1) {
                    if (logger.isDebugEnable()) {
                        logger.debug("用戶ID为 [" + userInfo.get(i).getUserId() + "]  的用戶状态修改成功！");
                    }
                    // 添加至成功List
                    userList.add(userInfo.get(i));
                } else {
                    if (logger.isDebugEnable()) {
                        logger.debug("数据库中不存在ID为 [" + userInfo.get(i).getUserId() + "]  的用戶！");
                    }
                    // 添加至错误用户列表
                    errUserList.add(userInfo.get(i));
                }
            } catch (Exception e) {
                // throw new DelUserException(e);
                logger.error(ResultCodeEnum.FALSE, "注销用户 [" + userInfo.get(i).getUserName()
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
