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
package com.cloudmaster.cmp.web.authority.role;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.auth.AuthInfo;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 进入添加角色页面的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleAddInfoAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(RoleAddInfoAction.class);

    private List<AuthInfo> authRootList;

    private List<AuthInfo> authList;

    private transient IRoleTreeCreator roleTreeCreator;

    private String errorMsg;

    private String roleId;

    private String roleName;

    private String status;

    private String description;

    private String validateFail;

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        functionName = "角色管理";
        opType = "Add";

        if (null != validateFail) {
            this.addFieldError("status", validateFail);
        }

        if (null != errorMsg) {
            this.addActionError(errorMsg);
        }

        try {
            authList = ibatisDAO.getData("RoleInfo.getAuthInfos", null);
        } catch (SQLException e) {
            String errmsg = getText("read.all.authority.exception");
            this.addActionError(errmsg);
            logger.error(AuthorityStatusCode.READ_DB_EXCEPTION, errmsg + e.getMessage(), e);
            return "FAILURE";
        }

        // 创建角色的权限树
        // authRootList = roleTreeCreator.creatRoleTree(authList);

        for (AuthInfo obj : authList) {
            String id = "";
            String pid = "";
            id = obj.getAuthId();
            if (obj.getAuthId().endsWith("_00_00_00")) {
                // 判断结尾“_00_00_00”为第一级放置level1中
                pid = "-1";
            } else if (obj.getAuthId().endsWith("_00_00")) {
                // 判断结尾“_00_00”为第二级放置level2中
                pid = id.substring(0, 3);
                pid += "00_00_00";
            } else if (obj.getAuthId().endsWith("_00")) {
                // 判断结尾“_00”为第三级放置level3中
                pid = id.substring(0, 6);
                pid += "00_00";
            } else {
                // 第四级放置level4中
                pid = id.substring(0, 9);
                pid += "00";
            }
           obj.setPid(pid);

        }// end for i
        logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        return "SUCCESS";
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AuthInfo> getAuthRootList() {
        return authRootList;
    }

    public void setAuthRootList(List<AuthInfo> authRootList) {
        this.authRootList = authRootList;
    }

    public IRoleTreeCreator getRoleTreeCreator() {
        return roleTreeCreator;
    }

    public void setRoleTreeCreator(IRoleTreeCreator roleTreeCreator) {
        this.roleTreeCreator = roleTreeCreator;
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

    public List<AuthInfo> getAuthList() {
        return authList;
    }

    public void setAuthList(List<AuthInfo> authList) {
        this.authList = authList;
    }

}
