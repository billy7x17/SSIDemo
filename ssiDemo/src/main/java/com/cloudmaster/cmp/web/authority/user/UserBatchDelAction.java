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
import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserBatchDelAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(UserBatchDelAction.class);

    private static final String RET_FAILURE = "FAILURE";

    // 页面上选中的需要进行批量注销的所有用户Id组成的数组
    private String[] ids;

    private String errorMsg;

    public String execute() {

        if (null == ids) {
            errorMsg = getText("user.batchdel.select.no");
            operationInfo = getText("user.batchdel.fail") + errorMsg;
            return RET_FAILURE;
        }

        // 查询选中的用户的当前状态,若其中有被注销的，就提示“批量注销失败！”
        String status;
        for (String id : ids) {
            try {
                status = (String) ibatisDAO.getSingleRecord("getUserStatus", id);
            } catch (SQLException e) {
                errorMsg = MessageFormat.format(getText("read.personal.exception"), id);
                operationInfo = getText("user.batchdel.fail") + errorMsg;
                logger.error(AuthorityStatusCode.READ_DB_EXCEPTION, errorMsg + e.getMessage(), e);
                return RET_FAILURE;
            }
            if (status.equals("1")) {
                errorMsg = getText("user.batchdel.fail");
                operationInfo = errorMsg;
                return RET_FAILURE;
            }
        }

        // 当前状态都为可用，则进行批量注销
        List<BatchVO> idsList = new ArrayList<BatchVO>();
        for (String id : ids) {
            idsList.add(new BatchVO("MOD", "delUser", id));
        }
        try {
            ibatisDAO.updateBatchData(idsList);
        } catch (SQLException e) {
            errorMsg = getText("user.batchdel.fail");
            operationInfo = errorMsg;
            logger.error(AuthorityStatusCode.BATCH_UPDATE_EXCEPTION, errorMsg + e.getMessage(), e);
            return RET_FAILURE;
        }
        msg = getText("user.batchdel.success");
        operationInfo = msg;
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
