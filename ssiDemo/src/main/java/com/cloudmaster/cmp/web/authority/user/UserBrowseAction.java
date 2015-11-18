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
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.core.kmApi.user.ISearchUser;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户列表浏览
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserBrowseAction extends PageAction implements IAuthIdGetter, IOperationLog {

    private static final long serialVersionUID = -3288882325281914928L;

    private static LogService logger = LogService.getLogger(UserBrowseAction.class);

    private ISearchUser searchUser;

    private List<UserInfo> userList;
    
    private List<SiteInfo> sites;

    private UserInfo userInfo;

    private String errorMsg;

    private String functionName = "";

    private String operationInfo = "";

    private String opType = "";

    private static final String SUCCESS = "SUCCESS";

    private static final String RET_FAILURE = "FAILURE";
    
    private String forward = SUCCESS;
    
    public String tolist() throws SQLException {
        sites = (List<SiteInfo>) ibatisDAO.getData("SiteInfo.getSiteName", null); 
        return forward;
    }

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        functionName = "用户管理";
        opType = "View";
        if (null != errorMsg) {
            this.addFieldError("errorMsg", errorMsg);
        }
        try {
            HttpServletRequest jsprequest = ServletActionContext.getRequest();
            
            UserInfo user = (UserInfo)jsprequest.getSession().getAttribute(SessionKeys.userInfo.toString());
            
            String roleType = user.getRoleType();
            
            // 增加排序操作
            if (null == userInfo) {
                userInfo = new UserInfo();
            }
            userInfo.setSortName(jsprequest.getParameter("sortname"));
            userInfo.setSortOrder(jsprequest.getParameter("sortorder"));
            userInfo.setQtype(jsprequest.getParameter("qtype"));
            userInfo.setQuery(jsprequest.getParameter("query"));
            String rp = jsprequest.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            if ("2".equals(roleType)) {
                /*站点管理员*/
                userInfo.setSiteId(user.getSiteId());
            }
            userList = getPage("UserInfo.getUserSearchInfoCount", "UserInfo.getUserSearchInfos",
                    userInfo);
            
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
            forward = SUCCESS;

        } catch (Exception e) {
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.list.error"), e);
            forward = RET_FAILURE;
        }
        try {
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (UserInfo ahd : userList) {
                fgjd.setRowId(ahd.getUserId()); // 设置行标识
                fgjd.addColdata(ahd.getUserId());// 设置此行 第一列内容
                fgjd.addColdata(ahd.getUserName()); // 设置此行 第二列内容
                fgjd.addColdata(ahd.getRoleName());
                fgjd.addColdata(ahd.getSiteName());
                fgjd.addColdata(ahd.getSex());
                fgjd.addColdata(ahd.getEmail());
                fgjd.addColdata(ahd.getMobile());
                fgjd.addColdata(ahd.getPersonnelId());
                fgjd.addColdata(ahd.getCreatTime());
            }
            fgjd.commitData(); // 提交数据
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.debug("组装flexigrid数据异常！", e);
            e.printStackTrace();
        }
        return null;
    }

    public ISearchUser getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(ISearchUser searchUser) {
        this.searchUser = searchUser;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

}
