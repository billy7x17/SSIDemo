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

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户列表浏览
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserListAction extends PageAction implements IOperationLog {

    /**
     * 标识.
     */
    private static final long serialVersionUID = -3288882325281914928L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(UserListAction.class);

    private UserInfo userInfo;

    private String functionName = "";

    private String operationInfo = "";

    private String opType = "";

    /**
     * 用户名.
     */
    private String userName;
    
    /**
     * 站点.
     */
    private String siteId;
    
    @SuppressWarnings("unchecked")
    public void list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            // 增加排序操作
            if (null == userInfo) {
                userInfo = new UserInfo();
            }
            if(!StringUtils.isEmpty(userName)){
                userInfo.setUserName(userName);
            }
            if(!StringUtils.isEmpty(siteId)&& !siteId.equals("null")){
                userInfo.setSiteId(Integer.valueOf(siteId));
            }
            userInfo.setSortName("u.CREATE_TIME");
            userInfo.setSortOrder("desc");
            List<UserInfo> userList = ibatisDAO.getData("UserInfo.getUserSearchInfos", userInfo);
            JSONArray userArray = new JSONArray();
            for(UserInfo user: userList){
                userArray.add(user);
            }
            List<SiteInfo> siteList = ibatisDAO.getData("SiteInfo.findSiteByLine", null);
            JSONArray siteArray = new JSONArray();
            for(SiteInfo site : siteList){
                siteArray.add(site);
            }
            PrintWriter pw = response.getWriter();
            pw.write(userArray+";"+siteArray);
            pw.flush();
            pw.close();
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.list.error"), e);
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    /**
     * 为了能取到配置文件中的值
     * @param functionName
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String getOpType() {
        return opType;
    }

    /**
     * 为了能取到配置文件中的值
     * @param functionName
     */
    public void setOpType(String opType) {
        this.opType = opType;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
