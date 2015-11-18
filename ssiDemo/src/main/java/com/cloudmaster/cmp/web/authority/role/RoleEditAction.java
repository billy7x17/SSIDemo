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
import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.auth.AuthInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 编辑角色信息的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleEditAction extends OperationLogAction implements IAuthIdGetter {

    private static LogService logger = LogService.getLogger(RoleEditAction.class);

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String roleName;

    private String status;

    private String description;

    private RoleInfo roleInfo;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 页面提交的角色id
     */
    private String authIds = "";

    // 用户选中的该角色的所有权限点ID所组成的数组
    private String[] authIdList;

    private String errorMsg;

    private String operationInfo = "修改角色失败！表单输入后台校验失败！";

    private String validateFail;

    private String forward = "SUCCESS";

    private List<AuthInfo> authList;

    private List<RoleAuthInfo> roleAuthList;

    public List<RoleAuthInfo> getRoleAuthList() {
        return roleAuthList;
    }

    public void setRoleAuthList(List<RoleAuthInfo> roleAuthList) {
        this.roleAuthList = roleAuthList;
    }

    public List<AuthInfo> getAuthList() {
        return authList;
    }

    public void setAuthList(List<AuthInfo> authList) {
        this.authList = authList;
    }

    public String execute() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("function.lable.roleName") + ": " + roleName };
        try {
            // 设置权限点，创建角色时可以不选择权限点
            if (authIds != null && !"".equals(authIds)) {
                authIdList = authIds.split(",");
                if (logger.isDebugEnable()) {
                    logger.debug("add role ids:" + authIds);
                }
            }
            roleInfo = new RoleInfo();
            roleInfo.setRoleId(roleId);
            roleInfo.setRoleName(roleName.trim());
            roleInfo.setStatus(null == status ? "0" : status);
            roleInfo.setDescription(description.trim());
            roleInfo.setRoleType(roleType);

            // 判断数据库中是否在已存在该角色名
            RoleInfo singleRoleInfo;
            singleRoleInfo = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getRoleInfoByName",
                    roleName.trim());

            if (null != singleRoleInfo && !singleRoleInfo.getRoleId().equals(roleId)) {
                errorMsg = getText("common.message.editError") + getText("该角色名已存在！");
                operationInfo = getText("oplog.edit.error", opParam) + getText("该角色名已存在");
                forward = "INPUT";
                // 创建角色的权限树
                // authRootList = roleTreeCreator.creatRoleTree(authList);
                authList = ibatisDAO.getData("RoleInfo.getAuthInfos", null);
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

            } else {

                List<BatchVO> roleInfoList = new ArrayList<BatchVO>();
                roleInfoList.add(new BatchVO("MOD", "RoleInfo.updateRoleInfo", roleInfo));
                // 删除旧的权限点
                roleInfoList.add(new BatchVO("DEL", "RoleInfo.deleteRoleAuth", roleId));
                // 添加新的权限点
                if (null != authIdList) {
                    for (String authId : authIdList) {
                        RoleAuthInfo roleAuthInfo = new RoleAuthInfo();
                        // roleAuthInfo.setRoleId(Integer.valueOf(roleId));
                        roleAuthInfo.setRoleId(roleId);
                        roleAuthInfo.setAuthId(authId);
                        roleInfoList
                                .add(new BatchVO("ADD", "RoleInfo.insertRoleAuth", roleAuthInfo));
                    }
                }
                ibatisDAO.updateBatchData(roleInfoList);
                msg = getText("common.message.editSuccess");
                operationInfo = getText("oplog.edit.success", opParam);
                logger.info(getText("function.title") + getText("log.edit.end"));
            }
        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.edit.error"), e);

        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.edit.error"), e);
        }
        return forward;
    }

    /***
     * public void validate() { // 验证角色描述 final int descLength = 256; if (null != description &&
     * description.length() > descLength) { description = description.substring(0, descLength - 1);
     * validateFail = getText("role.description.maxLength"); this.addFieldError("description",
     * validateFail); } // 验证角色名称 String nameRegex = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$"; if
     * (roleName.trim().equals("")) { validateFail = getText("role.name.required");
     * this.addFieldError("roleId", validateFail); } else if (!Pattern.matches(nameRegex, roleName))
     * { validateFail = getText("role.name.reg"); this.addFieldError("roleName", validateFail); } }
     */
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

    public String[] getAuthIdList() {
        return authIdList;
    }

    public void setAuthIdList(String[] authIdList) {
        this.authIdList = authIdList;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthIds() {
        return authIds;
    }

    public void setAuthIds(String authIds) {
        this.authIds = authIds;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
