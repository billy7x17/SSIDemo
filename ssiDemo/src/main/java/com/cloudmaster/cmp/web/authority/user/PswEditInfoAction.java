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
import java.text.MessageFormat;
import java.util.Map;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 进入修改个人密码页面的action
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PswEditInfoAction extends BaseAction implements IAuthIdGetter{
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PswEditInfoAction.class);

    private transient UserInfo userInfo;

    private String errorMsg;

    private String validateFail;

    private String password;

    private String newPassword;

    private String confPasswd;

    @SuppressWarnings("unchecked")
    public String execute() {

        if (null != validateFail) {
            this.addFieldError("status", validateFail);
        }
        if (null != errorMsg) {
            this.addActionError(errorMsg);
        }

        // 从session中取得当前用户信息，得到userId
        Map session = ActionContext.getContext().getSession();
        if (null == session) {
            return "LOGIN";
        }
        // 从session中取得当前用户信息，得到userId
        UserInfo currentUser = (UserInfo) session.get("userInfo");
        if (null == currentUser) {
            return "LOGIN";
        }
        String userId = currentUser.getUserId();

        try {
            userInfo = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getSingleUser", userId);
        } catch (SQLException e) {
            String errmsg = MessageFormat.format(getText("read.personal.exception"), userId);
            this.addActionError(errmsg);
            logger.error(AuthorityStatusCode.READ_DB_EXCEPTION, errmsg + e.getMessage(), e);
            return "FAILURE";
        }
        return "SUCCESS";
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getValidateFail() {
        return validateFail;
    }

    public void setValidateFail(String validateFail) {
        this.validateFail = validateFail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfPasswd() {
        return confPasswd;
    }

    public void setConfPasswd(String confPasswd) {
        this.confPasswd = confPasswd;
    }
}
