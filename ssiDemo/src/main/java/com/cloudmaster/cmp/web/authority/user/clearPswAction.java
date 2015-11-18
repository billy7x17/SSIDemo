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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DES3;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class clearPswAction extends OperationLogAction implements IAuthIdGetter {

    private static LogService logger = LogService.getLogger(clearPswAction.class);

    private static final long serialVersionUID = 1L;

    private static String SUCCESS = "SUCCESS"; //$NON-NLS-1$

    private static String RET_FAILURE = "FAILURE"; //$NON-NLS-1$

    private List<UserInfo> userList;

    private String validateFail;

    private UserInfo userinfo;

    private String errorMsg;

    private List<RoleInfo> roleList;

    private String forward = "SUCCESS";

    private DES3 des3;

    public DES3 getDes3() {
        return des3;
    }

    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }

    // 重置人员密码
    public String execute() {
        logger.info(getText("function.title") + getText("log.clearpwd.begin"));
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("userId"); //$NON-NLS-1$
        String opParam[] = { id };
        if (null != validateFail) {
            this.addFieldError("status", validateFail); //$NON-NLS-1$
        }

        if (null != errorMsg) {
            this.addActionError(errorMsg);
        }
        if (id != null) {
            try {
                userinfo = new UserInfo();
                userinfo.setUserId(id);
                userinfo.setPassword(Messages.getString("default.password"));
                int i = ibatisDAO.updateData("UserInfo.updatePsw", userinfo);
                if (i > 0) {
                    msg = getText("重置密码成功！");
                    operationInfo = getText("oplog.clearpwd.success", opParam);
                } else {
                    errorMsg = getText("重置密码失败！");
                    forward = "ERROR";
                    operationInfo = getText("oplog.clearpwd.error", opParam) + getText("重置密码失败！");
                }
                logger.info(getText("function.title") + getText("log.clearpwd.begin"));
            } catch (SQLException e) {
                errorMsg = getText("重置密码失败！");
                operationInfo = getText("oplog.clearpwd.error", opParam) + getText("重置密码失败！");
                forward = "ERROR";
                logger.info(getText("function.title") + getText("log.clearpwd.error"), e);
            }
        }

        return forward; //$NON-NLS-1$
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        clearPswAction.logger = logger;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static void setSUCCESS(String success) {
        SUCCESS = success;
    }

    public static String getRET_FAILURE() {
        return RET_FAILURE;
    }

    public static void setRET_FAILURE(String ret_failure) {
        RET_FAILURE = ret_failure;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    public String getValidateFail() {
        return validateFail;
    }

    public void setValidateFail(String validateFail) {
        this.validateFail = validateFail;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
