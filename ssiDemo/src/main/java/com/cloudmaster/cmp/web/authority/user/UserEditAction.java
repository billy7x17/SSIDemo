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
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.core.ResultInfo;
import com.cloudmaster.cmp.core.kmApi.user.IEditUser;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DES3;
import com.cloudmaster.cmp.web.authority.auth.AuthorityStatusCode;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserEditAction extends OperationLogAction implements IAuthIdGetter {

    private static LogService logger = LogService.getLogger(UserEditAction.class);

    private static final long serialVersionUID = 1L;

    private String forward = "SUCCESS";

    private IEditUser editUser;

    private UserInfo userInfo;

    private String errorMsg;

    private String userName;

    private List<RoleInfo> roleList;

    // 用户角色
    private List<String> selectvalue;

    private DES3 des3;

    public String execute() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        ResultInfo result = null;
        functionName = "用户管理";
        String opParam[] = { getText("web.table.columnName.userName") + ": " + userInfo.getUserName() };
        try {
            // 判断密码是否为空
            if (null != userInfo.getPassword() && !"".equals(userInfo.getPassword().trim())) {
                String pwd = des3.encrypt(userInfo.getPassword());
                userInfo.setPassword(pwd);
            }
            result = editUser.editUser(userInfo);
            ArrayList<BatchVO> editUserRoleList = new ArrayList<BatchVO>();
            editUserRoleList
                    .add(new BatchVO("DEL", "UserInfo.deleteUserRole", userInfo.getUserId()));
            for (String roid : userInfo.getSelectvalue()) {
                UserRoleInfo userRole = new UserRoleInfo();
                userRole.setRoleId(roid);
                userRole.setUserId(userInfo.getUserId());
                editUserRoleList.add(new BatchVO("ADD", "UserInfo.insertUserRole", userRole));
            }
            this.ibatisDAO.updateBatchData(editUserRoleList);
            msg = getText("common.message.editSuccess");
            operationInfo = getText("oplog.edit.success", opParam);
            if (logger.isInfoEnable()) {
                logger.info(AuthorityStatusCode.UPDATE_DB_SUCCESS, msg);
            }
            logger.info(getText("function.title") + getText("log.edit.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.success", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.edit.error"), e);
        }
        return forward;
    }

    public void validate() {

    }

    public IEditUser getEditUser() {
        return editUser;
    }

    public void setEditUser(IEditUser editUser) {
        this.editUser = editUser;
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

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public List<String> getSelectvalue() {
        return selectvalue;
    }

    public void setSelectvalue(List<String> selectvalue) {
        this.selectvalue = selectvalue;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DES3 getDes3() {
        return des3;
    }

    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }

}
