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
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.web.authority.role.RoleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户添加前获取角色信息
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserAddInfoAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private List<RoleInfo> roleList;

    private UserInfo userInfo = new UserInfo();

    private String errorMsg;

    private String forward = "SUCCESS";

    private RoleInfo sessionRole;

    private List<SiteInfo> sites;

    private String siteName;

    private static LogService logger = LogService.getLogger(UserAddInfoAction.class);

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        functionName = "用户管理";
        opType = "Add";
        try {
            /* 查询站点list */
            sites = ibatisDAO.getData("SiteInfo.getSiteName", null);

            roleList = ibatisDAO.getData("RoleInfo.getUsedRoles", null);

            UserInfo userTemp = (UserInfo) ServletActionContext.getContext().getSession()
                    .get(SessionKeys.userInfo);

            sessionRole = (RoleInfo) ibatisDAO.getSingleRecord("RoleInfo.getUserRole", userTemp);

            for (SiteInfo temp : sites) {
                if (userTemp.getSiteId().equals(temp.getSiteId())) {
                    siteName = temp.getSiteName();
                    break;
                }
            }

            if (roleList.size() > 0) {

            } else {
                errorMsg = getText("role.no.role");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeAdd.error"), e);
            return "ERROR";
        }
        return forward;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        UserAddInfoAction.logger = logger;
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
