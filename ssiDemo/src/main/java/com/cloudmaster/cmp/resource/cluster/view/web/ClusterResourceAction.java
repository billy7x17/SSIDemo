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
package com.cloudmaster.cmp.resource.cluster.view.web;


import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.cluster.view.dao.ClusterCabinetDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机柜浏览
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-22
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class ClusterResourceAction extends PageAction implements IAuthIdGetter, IOperationLog {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ClusterResourceAction.class);

    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "clusterCabinet";

    /**
     * 站点角色值.
     */
    private static final String ROLETYPE_SITE = "2";

    /**
     * 日志内容.
     */
    private String operationInfo;

    /**
     * 名称.
     */
    private String functionName;

    /**
     * 操作类型.
     */
    private String opType;

    /**
     * 实体类.
     */
    private ClusterCabinetDomain cluster = new ClusterCabinetDomain();

    /**
     * 站点信息.
     */
    private List<ClusterCabinetDomain> siteList = new ArrayList<ClusterCabinetDomain>();
    
    /**
     * 站点类型.
     */
    private String roleType;

    /**
     * 仅跳转
     * @return action映射名
     */
    public String init() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            roleType = user.getRoleType();
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findSiteInfo", user);
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("resource.zone.browse.siteInfo.error"), e);
        }
        return SUCCESS;
    }

    /**
     * 初始化页面列表
     */
    public void list() {
        logger.info(getText("log.resource.title") + getText("log.list.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8");
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            if (ROLETYPE_SITE.equals(user.getRoleType())
                    && StringUtils.isEmpty(cluster.getSiteId())) {// 给站点角色添加站点值.
                cluster.setSiteId(String.valueOf(user.getSiteId()));
            }
            
            cluster.setSortName(request.getParameter("sortname"));
            cluster.setSortOrder(request.getParameter("sortorder"));
            cluster.setQtype(request.getParameter("qtype"));
            cluster.setQuery(request.getParameter("query"));
            String rp = request.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            List<ClusterCabinetDomain> clusterList = getPage(IBATIC_NAMESPACE + ".getCount",
                    IBATIC_NAMESPACE + ".getList", cluster);
            FlexGridJSONData fgjd = new FlexGridJSONData();
            fgjd.setPage(getPage());
            fgjd.setTotal(getTotalCount());
            for (ClusterCabinetDomain cluster : clusterList) {
                fgjd.setRowId(cluster.getClusterId());
                fgjd.addColdata(cluster.getClusterName());
                fgjd.addColdata(cluster.getZoneName());
                fgjd.addColdata(cluster.getSiteName());
                fgjd.addColdata(cluster.getBrand());
                fgjd.addColdata(cluster.getDescription());
                fgjd.addColdata(cluster.getClusterId());
            }
            fgjd.commitData();
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString());
            pw.flush();
            pw.close();
            operationInfo = getText("oplog.list.success");
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
        logger.info(getText("log.resource.title") + getText("log.list.end"));
    }

    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOpType() {
        return opType;
    }

    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @param operationInfo the operationInfo to set
     */
    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    /**
     * @param opType the opType to set
     */
    public void setOpType(String opType) {
        this.opType = opType;
    }

    /**
     * @return the cluster
     */
    public ClusterCabinetDomain getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void setCluster(ClusterCabinetDomain cluster) {
        this.cluster = cluster;
    }

    /**
     * @return the siteList
     */
    public List<ClusterCabinetDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList the siteList to set
     */
    public void setSiteList(List<ClusterCabinetDomain> siteList) {
        this.siteList = siteList;
    }

    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
