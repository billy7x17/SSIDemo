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
import java.util.regex.Pattern;

import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改个人信息的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PersonalEditAction extends BaseAction {

    private static LogService logger = LogService.getLogger(PersonalEditAction.class);

    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String sex;

    private String mobile;

    private String phone;

    private String email;

    private String address;

    private String zipcode;

    private String description;

    private String errorMsg;

    private String operationInfo = "修改个人信息失败！表单输入后台校验失败！";

    private String validateFail;

    public String execute() {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName.trim());
        userInfo.setSex(sex);
        userInfo.setMobile(mobile.trim());
        /*userInfo.setPhone(phone.trim());*/
        userInfo.setEmail(email.trim());
        /*userInfo.setAddress(address.trim());*/
        /*userInfo.setZipcode(zipcode.trim());*/
        userInfo.setDescription(description.trim());

        try {
            ibatisDAO.updateData("UserInfo.updatePersonalInfo", userInfo);
        } catch (SQLException e) {
            errorMsg = MessageFormat.format(getText("personal.edit.fail"), userId, userName);
            operationInfo = errorMsg;
            logger.error(AuthorityStatusCode.UPDATE_DB_EXCEPTION, errorMsg + e.getMessage(), e);
            return "FAILURE";
        }

        msg = MessageFormat.format(getText("personal.edit.success"), userId, userName);
        operationInfo = msg;
        if (logger.isInfoEnable()) {
            logger.info(AuthorityStatusCode.UPDATE_DB_SUCCESS, msg);
        }
        return "SUCCESS";
    }

    public void validate() {

        // 验证用户描述
        final int descLength = 256;
        if (null != description && description.length() > descLength) {
            description = description.substring(0, descLength - 1);
            validateFail = getText("user.description.maxLength");
            this.addFieldError("description", validateFail);
        }
        // 验证用户名称
        String nameRegex = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
        if (userName.trim().equals("")) {
            validateFail = getText("user.name.required");
            this.addFieldError("userName", validateFail);
        } else if (!Pattern.matches(nameRegex, userName)) {
            validateFail = getText("user.name.reg");
            this.addFieldError("userName", validateFail);
        }

        // 验证手机号码
        String mobileReg = "^((\\+86)|(86))?[1]\\d{10}$";
        if (!Pattern.matches(mobileReg, mobile)) {
            validateFail = getText("mobile.input.error");
            this.addFieldError("mobile", validateFail);
        }

        // 验证邮箱
        String emailReg = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        if (!Pattern.matches(emailReg, email)) {
            validateFail = getText("email.input.error");
            this.addFieldError("email", validateFail);
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOperationInfo() {
        return operationInfo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
