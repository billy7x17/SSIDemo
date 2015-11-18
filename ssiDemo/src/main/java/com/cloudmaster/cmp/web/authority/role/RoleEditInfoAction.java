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
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 进入编辑角色信息页面的action
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleEditInfoAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(RoleEditInfoAction.class);

    private static final String RET_FAILURE = "FAILURE";

    private String roleId;

    private String roleName;

    private String description;

    private transient RoleInfo roleInfo;

    private List<RoleAuthInfo> roleAuthList;

    // 用户选中的该角色的所有权限点ID所组成的数组
    private String[] authIdList;

    private List<AuthInfo> authRootList;

    private List<AuthInfo> authList;

    private transient IRoleTreeCreator roleTreeCreator;

    private String errorMsg;

    private String validateFail;

    private String forward = "SUCCESS";
    
    private String userCount;

    @SuppressWarnings("unchecked")
    public String execute() {
        try {
            logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
            functionName = "角色管理";
            opType = "Update";
            if (null != validateFail) {
                this.addFieldError("status", validateFail);
            }
            if (null != errorMsg) {
                this.addActionError(errorMsg);
            }
            roleInfo = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getSingleRole", roleId);
            if (null == roleInfo) {
                errorMsg = getText("common.message.editError") + getText("角色ID不存在,或已被删除!");
                forward = "INPUT";
            } else {
                forward = "SUCCESS";
            }
            authList = ibatisDAO.getData("RoleInfo.getAuthInfos", null);

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

            // 查找角色对应的权限点
            roleAuthList = ibatisDAO.getData("RoleInfo.getRoleAuths", roleId);
            
            // 查询该角色下的用户数量
            Integer count = (Integer) ibatisDAO.getSingleRecord("RoleInfo.getRoleUserCount", roleId);
            userCount = count.toString();
            
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
        }
        return forward;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<AuthInfo> getAuthRootList() {
        return authRootList;
    }

    public void setAuthRootList(List<AuthInfo> authRootList) {
        this.authRootList = authRootList;
    }

    public List<RoleAuthInfo> getRoleAuthList() {
        return roleAuthList;
    }

    public void setRoleAuthList(List<RoleAuthInfo> roleAuthList) {
        this.roleAuthList = roleAuthList;
    }

    public String[] getAuthIdList() {
        return authIdList;
    }

    public void setAuthIdList(String[] authIdList) {
        this.authIdList = authIdList;
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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    /**
     * @return the userCount
     */
    public String getUserCount() {
        return userCount;
    }

    /**
     * @param userCount the userCount to set
     */
    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

}
