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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DES3;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改用户密码的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PswEditAction extends OperationLogAction implements IAuthIdGetter{
    private static LogService logger = LogService.getLogger(PswEditAction.class);

    private static final long serialVersionUID = 1L;

    private static final String RET_FAILURE = "FAILURE";

    private String userId;

    private String userName;

    private String password;

    private String newPassword;

    private String confPasswd;

    private String errorMsg;
    
    private String validateFail;

    private String forward ="SUCCESS";
    private DES3 des3;
    
    public String execute() {
        logger.info(getText("function.title") + getText("log.pwdedit.begin"));
        String opParam[]={getText("web.table.columnName.userName") + ": " + userName};
        // 从数据库中获取用户的旧密码，与用户输入的旧密码进行对比
        UserInfo userinfo1 = null;
        try {
           userinfo1 = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getSingleUser", userId);
        } catch (SQLException e) {
           errorMsg =getText("common.message.editError")+getText("message.sql.error");
           forward ="ERROR";
           operationInfo = getText("oplog.pwdedit.error",opParam)+getText("message.sql.error");
          logger.info(getText("function.title") + getText("log.pwdedit.error"),e);
       }

       //if (!PwdEncodeUtil.sha1Base64(getPassword()).equals(userinfo.getPassword())) {
       if (!getPassword().equals(des3.decrypt(userinfo1.getPassword()))) {
           errorMsg = getText("common.message.editError")+getText("旧密码输入错误！");     
           operationInfo = getText("oplog.pwdedit.error",opParam)+getText("旧密码输入错误");     
           forward ="ERROR";
       }else{

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
//        userInfo.setPassword(PwdEncodeUtil.sha1Base64(newPassword));

        userInfo.setPassword(des3.encrypt(newPassword));
        try {
            int i =ibatisDAO.updateData("UserInfo.updateUserPsw", userInfo);
            if(i>0){
                msg=getText("common.message.editSuccess");
                operationInfo = getText("oplog.pwdedit.success",opParam);
            } 
            else{
                errorMsg =getText("common.message.editError")+getText("message.sql.error");
                forward="ERROR";
                operationInfo = getText("oplog.pwdedit.error",opParam)+getText("message.sql.error");
            }
            logger.info(getText("function.title") + getText("log.pwdedit.end"));
        } catch (SQLException e) {
            errorMsg =getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.pwdedit.error",opParam) + getText("common.message.systemError");
            forward="ERROR";
            logger.info(getText("function.title") + getText("log.pwdedit.error"),e);
        }
       }
       return forward;
    }

   
//    public void validate() {
//
//        // 验证旧密码
//        /*
//         * final int length = 8; if (password.length() < length) { validateFail =
//         * getText("oldPassword.minlength"); this.addFieldError("password", validateFail); } else if
//         * (!SearchUtil.checkPassword(password)) { validateFail =
//         * getText("oldPassword.input.error"); this.addFieldError("password", validateFail); } //
//         * 验证新密码 if (newPassword.length() < length) { validateFail =
//         * getText("newPassword.minlength"); this.addFieldError("newPassword", validateFail); } else
//         * if (!SearchUtil.checkPassword(newPassword)) { validateFail =
//         * getText("newPassword.input.error"); this.addFieldError("newPassword", validateFail); }
//         */
//        //验证旧密码
//        try {
//            UserInfo userinfo = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getSingleUser", userId);
//            if(!password.equals(des3.decrypt(userinfo.getPassword()))){
//                validateFail = getText("password.input.error");
//                errorMsg =getText("common.message.editError");
//                this.addFieldError("password", validateFail);
//                forward="ERROR";
//            }
//            
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        // 验证新密码确认要与新密码一致
//        if (!newPassword.equals(confPasswd)) {
//            validateFail = getText("confPasswd.input.error");
//            this.addFieldError("confPasswd", validateFail);
//            forward="ERROR";
//        }
//    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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

    public String getConfPasswd() {
        return confPasswd;
    }

    public void setConfPasswd(String confPasswd) {
        this.confPasswd = confPasswd;
    }


    public String getForward() {
        return forward;
    }


    public void setForward(String forward) {
        this.forward = forward;
    }
    public DES3 getDes3() {
        return des3;
    }


    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }
}
