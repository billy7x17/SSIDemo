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

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.util.DES3;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 进入编辑用户信息页面的action
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserEditInfoAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(UserEditInfoAction.class);

    private String userId;

    private UserInfo userInfo;

    private IbatisDAO ibatisDAO;

    private static String SUCCESS = "SUCCESS";

    private static String RET_FAILURE = "FAILURE";

    private List<RoleInfo> roleList;

    private List<RoleInfo> myRoleList;

    private List<SiteInfo> sites;

    private String errorMsg;

    private String forward = "SUCCESS";

    private DES3 des3;

    private String userGroup;

    private RoleInfo roleSelected;

    private String firstPage;

    /**
     * 员工分组显示 分组名称
     */
    private String viewText = "";

    private String siteName;

    private RoleInfo sessionRole;

    public String getViewText() {
        return viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        functionName = "用户管理";
        opType = "Update";
        UserInfo user = new UserInfo();
        try {
            /* 查询站点list */
            sites = ibatisDAO.getData("SiteInfo.getSiteName", null);
            roleList = ibatisDAO.getData("RoleInfo.getUsedRoles", null);
            /* 查询已选的角色 */
            roleSelected = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getUserRole", userId);
            user.setUserId(userId);
            userInfo = (UserInfo) ibatisDAO.getSingleRecord("UserInfo.getUserInfo", user);

            UserInfo sessionUser = (UserInfo) ServletActionContext.getContext().getSession()
                    .get(SessionKeys.userInfo);

            sessionRole = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getUserRole", sessionUser);

            for (SiteInfo temp : sites) {
                if (sessionUser.getSiteId().equals(temp.getSiteId())) {
                    siteName = temp.getSiteName();
                    break;
                }
            }

            if (userInfo.getPassword() != null && !("").equals(userInfo.getPassword())) {
                String pwd = userInfo.getPassword();
                pwd = des3.decrypt(pwd);
                userInfo.setPassword(pwd);
                if ("firstPage".equals(firstPage)) {
                    forward = firstPage;
                }
            } else {
                errorMsg = getText("common.message.editError") + getText("获取角色失败！");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            forward = "ERROR";
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
        }
        return forward;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public List<RoleInfo> getMyRoleList() {
        return myRoleList;
    }

    public void setMyRoleList(List<RoleInfo> myRoleList) {
        this.myRoleList = myRoleList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        UserEditInfoAction.logger = logger;
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

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
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

    public DES3 getDes3() {
        return des3;
    }

    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }

    /**
     * @return the roleSelected
     */
    public RoleInfo getRoleSelected() {
        return roleSelected;
    }

    /**
     * @param roleSelected the roleSelected to set
     */
    public void setRoleSelected(RoleInfo roleSelected) {
        this.roleSelected = roleSelected;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the sessionRole
     */
    public RoleInfo getSessionRole() {
        return sessionRole;
    }

    /**
     * @param sessionRole the sessionRole to set
     */
    public void setSessionRole(RoleInfo sessionRole) {
        this.sessionRole = sessionRole;
    }

}
