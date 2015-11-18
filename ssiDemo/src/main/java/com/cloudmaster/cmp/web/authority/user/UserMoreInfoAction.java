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
package com.cloudmaster.cmp.web.authority.user;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 展示用户详细信息的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserMoreInfoAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(UserMoreInfoAction.class);

    private static String RET_FAILURE = "FAILURE";

    private transient UserInfo userInfo;

    private String userId;

    private List<RoleInfo> myRoleList;

    private String errorMsg;

    private String forward = "SUCCESS";

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam[] = { getText("web.table.columnName.userName") + ": " + userInfo.getUserName() };
        try {
            functionName = "用户管理";
            opType = "View";
            userInfo = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getSingleUser", userId);

            if (null == userInfo) {
                errorMsg = getText("common.message.detailError") + getText("该用户不存在！");
                operationInfo = getText("oplog.detail.error", opParam) + getText("该用户不存在");
                forward = "ERROR";
                return forward;
            }
            myRoleList = ibatisDAO.getData("RoleInfo.getUserRole", userId);
            if (myRoleList.size() > 0) {
                operationInfo = getText("oplog.detail.success", opParam);
            }else {
                errorMsg = getText("common.message.detailError") + getText("该用户不存在！");
                operationInfo = getText("oplog.detail.error", opParam) + getText("该用户不存在");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.detailError") + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam) + getText("common.message.systemError");
            forward = "ERROR";
            logger.info(getText("function.title") + getText("log.detail.error"), e);
        }
        return forward;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        UserMoreInfoAction.logger = logger;
    }

    public static String getRET_FAILURE() {
        return RET_FAILURE;
    }

    public static void setRET_FAILURE(String ret_failure) {
        RET_FAILURE = ret_failure;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<RoleInfo> getMyRoleList() {
        return myRoleList;
    }

    public void setMyRoleList(List<RoleInfo> myRoleList) {
        this.myRoleList = myRoleList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
