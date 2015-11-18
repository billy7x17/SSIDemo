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

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleDelAction extends OperationLogAction implements IAuthIdGetter {
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(RoleDelAction.class);

    private static final String RET_FAILURE = "FAILURE";

    private String roleId;

    private String roleName;

    private String errorMsg;

    public String execute() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String opParam[]={getText("function.lable.roleName") + ": " + roleName};
        // 判断该角色是否已被删除
        RoleInfo singleRoleInfo = null;
        try {
            singleRoleInfo = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getSingleRole", roleId
                    .trim());
        } catch (Exception e) {
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error",opParam) + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.delete.error"),e);
            return RET_FAILURE;
        }
        if (null == singleRoleInfo) {
            errorMsg = getText("common.message.delError")+getText("该角色已被删除！");
            operationInfo = getText("oplog.delete.error",opParam)+getText("该角色已被删除");    
            return RET_FAILURE;
        }

        // 设与该角色相关联的用户数为0
        int userCount = 0;
        try {
            userCount = (Integer) ibatisDAO.getSingleRecord("RoleInfo.getRoleUserCount", roleId);
        } catch (Exception e) {
            errorMsg = getText("common.message.delError")+getText("该角色有用户关联！");
            operationInfo = getText("oplog.delete.error",opParam)+getText("该角色有用户关联");  
            logger.info(getText("function.title") + getText("log.delete.error"),e);
            return RET_FAILURE;
        }
        if (userCount > 0) {
            errorMsg = getText("common.message.delError")+getText("该角色有用户关联！");
            operationInfo = getText("oplog.delete.error",opParam)+getText("该角色有用户关联");  
            return RET_FAILURE;
        }
        try {
            List<BatchVO> roleInfoList = new ArrayList<BatchVO>();
            roleInfoList.add(new BatchVO("DEL", "RoleInfo.deleteRoleAuth", roleId));
            roleInfoList.add(new BatchVO("DEL", "RoleInfo.deleteRoleInfo", roleId));
            ibatisDAO.updateBatchData(roleInfoList);
            msg = getText("common.message.delSuccess");
            operationInfo = getText("oplog.delete.success",opParam);  
            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error",opParam) + getText("common.message.systemError"); 
            logger.info(getText("function.title") + getText("log.delete.error"),e);
            return RET_FAILURE;
        }
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
