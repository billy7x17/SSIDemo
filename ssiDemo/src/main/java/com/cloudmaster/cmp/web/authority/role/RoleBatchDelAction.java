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
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 批量注销角色的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleBatchDelAction extends BaseAction implements IAuthIdGetter {
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(RoleBatchDelAction.class);

    private static final String RET_FAILURE = "FAILURE";

    // 页面上选中的需要进行批量注销的所有角色Id组成的数组
    private String[] ids;

    private String errorMsg;

    @SuppressWarnings("unchecked")
    public String execute() {

        if (null == ids) {
            errorMsg = getText("role.batchdel.select.no");
            // operationInfo = getText("role.batchdel.fail") + errorMsg;
            return RET_FAILURE;
        }

        // 判断选中的角色中是否有用户相关联
        int userCount = 0;
        for (String id : ids) {
            try {
                List<UserInfo> userInfo = ibatisDAO.getData("getUserRoleInfos", id);
                userCount = userInfo.size();
            } catch (SQLException e) {
                errorMsg = getText("role.related.user.exception");
                // operationInfo = getText("role.batchdel.fail") + errorMsg;
                logger.error(AuthorityStatusCode.READ_DB_EXCEPTION, errorMsg + e.getMessage(), e);
                return RET_FAILURE;
            }
            // 若有用户相关联，则提示“选中的角色有用户相关联，不能批量注销”
            if (userCount != 0) {
                errorMsg = getText("role.batchdel.related.user");
                // operationInfo = getText("role.batchdel.fail") + errorMsg;
                return RET_FAILURE;
            }
        }

        // 若没有用户相关联，则查询选中的角色的当前状态,若其中有被注销的，就提示“批量注销失败！”
        String status;
        for (String id : ids) {
            try {
                status = (String) ibatisDAO.getSingleRecord("getRoleStatus", id);
            } catch (SQLException e) {
                errorMsg = getText("role.related.user.exception");
                // operationInfo = getText("role.batchdel.fail") + errorMsg;
                logger.error(AuthorityStatusCode.READ_DB_EXCEPTION, errorMsg + e.getMessage(), e);
                return RET_FAILURE;
            }
            if (status.equals("1")) {
                errorMsg = getText("role.batchdel.fail");
                // operationInfo = errorMsg;
                return RET_FAILURE;
            }
        }

        // 没有用户相关联，且当前状态都为可用，则进行批量注销
        List<BatchVO> idsList = new ArrayList<BatchVO>();
        for (String id : ids) {
            idsList.add(new BatchVO("MOD", "delRole", id));
        }
        try {
            ibatisDAO.updateBatchData(idsList);
        } catch (SQLException e) {
            errorMsg = getText("role.batchdel.fail");
            // operationInfo = errorMsg;
            logger.error(AuthorityStatusCode.BATCH_UPDATE_EXCEPTION, errorMsg + e.getMessage(), e);
            return RET_FAILURE;
        }
        msg = getText("role.batchdel.success");
        // operationInfo = msg;
        if (logger.isInfoEnable()) {
            logger.info(AuthorityStatusCode.BATCH_UPDATE_SUCCESS, msg);
        }
        return "SUCCESS";
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
