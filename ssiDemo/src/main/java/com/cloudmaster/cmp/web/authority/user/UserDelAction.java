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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.core.ResultCodeEnum;
import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApi.user.IDelUser;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserDelAction extends OperationLogAction implements IAuthIdGetter {

    private static String RET_FAILURE = "FAILURE";

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(UserDelAction.class);

    private IbatisDAO ibatisDAO;

    private IDelUser delUser;

    private String userId;

    private String userName;

    private String errorMsg;

    private String forward = "SUCCESS";

    // 锁定状态
    private String disableStatus = "1";

    public String execute() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        // 若试图注销当前用户，则提示不能注销
        String opParam[] = { getText("web.table.columnName.userName") + ": " + userName };
        try {
            UserInfo userInfo = (UserInfo) ActionContext.getContext().getSession()
                    .get(SessionKeys.userInfo);
            String userID = userInfo.getUserId();

            if (userId.equals(userID)) {
                errorMsg = getText("common.message.delError") + getText("当前登录用户不能删除！");
                operationInfo = getText("oplog.delete.error", opParam) + getText("当前登录用户不能删除");
                forward = "ERROR";
            } else {
                ResultInfo result;
                functionName = "用户管理";

                UserInfo user = new UserInfo();
                user.setUserId(userId);
                user.setStatus(disableStatus);
                result = delUser.delUser(user);
                if (result != null) {
                    msg = getText("common.message.delSuccess");
                    operationInfo = getText("oplog.delete.success", opParam);
                } else {
                    errorMsg = getText("common.message.delError") + getText("该用户与角色有关联！");
                    operationInfo = getText("oplog.delete.error", opParam) + getText("该用户与角色有关联");
                    forward = "ERROR";
                }

                if (ResultCodeEnum.FALSE.equals(result.getResultCode())) {
                    errorMsg = getText("common.message.delError") + getText("该用户与角色有关联！");
                    operationInfo = getText("oplog.delete.error", opParam) + getText("该用户与角色有关联");
                    forward = "ERROR";
                } else {
                    msg = getText("common.message.delSuccess");
                    operationInfo = getText("oplog.delete.success", opParam);
                    if (logger.isInfoEnable()) {
                        logger.info(AuthorityStatusCode.UPDATE_DB_SUCCESS, msg);
                    }

                }
            }

            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam) + getText("common.message.systemError");
            forward = "ERROR";
            logger.info(getText("function.title") + getText("log.delete.error"), e);
        }
        return forward;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public IDelUser getDelUser() {
        return delUser;
    }

    public void setDelUser(IDelUser delUser) {
        this.delUser = delUser;
    }

    public static String getRET_FAILURE() {
        return RET_FAILURE;
    }

    public static void setRET_FAILURE(String ret_failure) {
        RET_FAILURE = ret_failure;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        UserDelAction.logger = logger;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(String disableStatus) {
        this.disableStatus = disableStatus;
    }

}
