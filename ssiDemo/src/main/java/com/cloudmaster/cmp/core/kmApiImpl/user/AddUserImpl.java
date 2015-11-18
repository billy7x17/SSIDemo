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

import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.core.CommonStatusCode;
import com.cloudmaster.cmp.core.ResultCodeEnum;
import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApi.user.AddUserException;
import com.cloudmaster.cmp.core.kmApi.user.IAddUser;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.cloudmaster.cmp.web.authority.user.UserRoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AddUserImpl implements IAddUser {
    private IbatisDAO ibatisDAO;


    // 默认角色ID
    private String defaultRoleID;

    private static LogService logger = LogService.getLogger(AddUserImpl.class);

    @Override
    public ResultInfo addUser(UserInfo userInfo) throws AddUserException {
        try {
            // 判断用户名是否重复
            int userCount = ibatisDAO.getCount("UserInfo.getUserCount", userInfo);
            if (userCount > 0) {
                if (logger.isDebugEnable()) {
                    logger.debug("数据库中已存在[" + userInfo.getUserId() + "]用戶!");
                }
                return getResult("error", "数据库中已存在ID为 [" + userInfo.getUserId() + "] 的用戶 ["
                        + userInfo.getUserName() + "]!");
            } else {
                // ibatisDAO.insertData("UserInfo.insertUserInfo", userInfo);
                ArrayList<BatchVO> addUserRoleList = new ArrayList<BatchVO>();
                // 添加用户
                addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserInfo", userInfo));
                // 添加角色
                if (userInfo.getSelectvalue() == null) {
                    // 角色为空时默认添加角色
                    UserRoleInfo tmp = new UserRoleInfo();
                    tmp.setRoleId(defaultRoleID);
                    tmp.setUserId(userInfo.getUserId());
                    addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserRole", tmp));
                } else {
                    for (int i = 0; i < userInfo.getSelectvalue().size(); i++) {
                        UserRoleInfo tmp = new UserRoleInfo();
                        tmp.setRoleId(userInfo.getSelectvalue().get(i));
                        tmp.setUserId(userInfo.getUserId());
                        addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserRole", tmp));
                    }
                }
                ibatisDAO.updateBatchData(addUserRoleList);

                if (logger.isDebugEnable()) {
                    logger.debug(" userID: " + userInfo.getUserId());
                }
                return getResult("success", "添加用戶ID为 [" + userInfo.getUserId() + "] 的用戶 ["
                        + userInfo.getUserName() + "] 成功!");
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.SQL_INSERT_ERROR, "创建用户失败!");
            throw new AddUserException(e);
        }
    }

    @Override
    public UserResultset addUsers(List<UserInfo> userInfo) throws AddUserException {

        List<UserInfo> errUserList = new ArrayList<UserInfo>();
        List<UserInfo> userList = new ArrayList<UserInfo>();
        UserResultset result = new UserResultset();

        for (int i = 0; i < userInfo.size(); i++) {
            try {
                int userCount = ibatisDAO.getCount("UserInfo.getUserCount", userInfo.get(i));
                if (userCount > 0) {
                    if (logger.isDebugEnable()) {
                        logger.debug("数据库中已存在ID为 [" + userInfo.get(i).getUserId() + "] 的用戶 ["
                                + userInfo.get(i).getUserName() + "] !");
                    }
                    // 添加至错误用户列表
                    errUserList.add(userInfo.get(i));
                } else {
                    // ibatisDAO.insertData("UserInfo.insertUserInfo", userInfo.get(i));
                    List<BatchVO> addUserRoleList = new ArrayList<BatchVO>();
                    // 添加用户
                    addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserInfo", userInfo
                            .get(i)));
                    // 添加角色
                    if (userInfo.get(i).getSelectvalue() == null) {
                        // 角色为空时默认添加角色
                        UserRoleInfo tmp = new UserRoleInfo();
                        tmp.setRoleId(defaultRoleID);
                        tmp.setUserId(userInfo.get(i).getUserId());
                        addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserRole", tmp));
                    } else {
                        for (int j = 0; j < userInfo.get(i).getSelectvalue().size(); j++) {
                            UserRoleInfo tmp = new UserRoleInfo();
                            tmp.setRoleId(userInfo.get(j).getSelectvalue().get(j));
                            tmp.setUserId(userInfo.get(j).getUserId());
                            addUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserRole", tmp));
                        }
                    }
                    ibatisDAO.updateBatchData(addUserRoleList);
                

                    // 添加至成功List
                    userList.add(userInfo.get(i));
                }
            } catch (Exception e) {
                logger.error(ResultCodeEnum.FALSE, "添加用户 [" + userInfo.get(i).getUserName()
                        + "]异常！", e);
                // throw new AddUserException(e);
                // 添加至错误用户列表
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


    public String getDefaultRoleID() {
        return defaultRoleID;
    }

    public void setDefaultRoleID(String defaultRoleID) {
        this.defaultRoleID = defaultRoleID;
    }

}
