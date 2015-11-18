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

import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PersonalEditInfoAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PersonalEditInfoAction.class);

    private transient UserInfo userInfo;

    private String validateFail;

    private String errorMsg;

    private String userName;

    private String mobile;

    private String email;

    private String address;

    private String description;

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

    public String getValidateFail() {
        return validateFail;
    }

    public void setValidateFail(String validateFail) {
        this.validateFail = validateFail;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
