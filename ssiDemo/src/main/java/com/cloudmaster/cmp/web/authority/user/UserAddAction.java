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

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.core.ResultCodeEnum;
import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApi.user.IAddUser;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.util.DES3;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户添加
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserAddAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 955368538585884613L;

    private static LogService logger = LogService.getLogger(UserAddAction.class);

    private IAddUser addUser;

    private UserInfo userInfo;

    private static String SUCCESS = "SUCCESS";

    private static String RET_FAILURE = "FAILURE";

    private String errorMsg;

    private List<UserRoleInfo> addRoleList;

    private String forward = "SUCCESS";

    private List<RoleInfo> roleList;

    private List<RoleInfo> myRoleList;

    private DES3 des3;

    private List<SiteInfo> sites;

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        ResultInfo result = null;
        functionName = "用户管理";
        String opParam[] = { getText("web.table.columnName.userName") + ": " + userInfo.getUserName() };
        try {
            String pwd = des3.encrypt(userInfo.getPassword());
            userInfo.setPassword(pwd);
            result = addUser.addUser(userInfo);
            if (ResultCodeEnum.SUCCESS.equals(result.getResultCode())) {
                msg = getText("common.message.addSuccess");
                operationInfo = getText("oplog.add.success", opParam);
            } else {
                // 角色列表
                roleList = ibatisDAO.getData("RoleInfo.getUsedRoles", null);
                this.addActionError(result.getMessage());
                String psw = des3.decrypt(userInfo.getPassword());
                userInfo.setPassword(psw);
                errorMsg = getText("common.message.addError") + getText("message.add.exist");
                operationInfo = getText("oplog.add.error", opParam) + getText("message.add.exist");
                /* 当跳转INPUT时，需要准备页面LIST */
                sites = (List<SiteInfo>) ibatisDAO.getData("SiteInfo.getSiteName", null);
                forward = "INPUT";
            }
            logger.info(getText("function.title") + getText("log.add.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.add.error"), e);
        }
        return forward;
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

    public IAddUser getAddUser() {
        return addUser;
    }

    public void setAddUser(IAddUser addUser) {
        this.addUser = addUser;
    }

    public List<UserRoleInfo> getAddRoleList() {
        return addRoleList;
    }

    public void setAddRoleList(List<UserRoleInfo> addRoleList) {
        this.addRoleList = addRoleList;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        UserAddAction.logger = logger;
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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public List<RoleInfo> getMyRoleList() {
        return myRoleList;
    }

    public void setMyRoleList(List<RoleInfo> myRoleList) {
        this.myRoleList = myRoleList;
    }

    public DES3 getDes3() {
        return des3;
    }

    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }

    /**
     * @return the sites
     */
    public List<SiteInfo> getSites() {
        return sites;
    }

    /**
     * @param sites the sites to set
     */
    public void setSites(List<SiteInfo> sites) {
        this.sites = sites;
    }

}
