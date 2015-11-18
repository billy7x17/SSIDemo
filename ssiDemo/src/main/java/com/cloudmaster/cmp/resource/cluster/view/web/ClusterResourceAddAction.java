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

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.cluster.view.dao.ClusterCabinetDomain;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机柜添加
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 2014-7-22
 */
@SuppressWarnings({"unchecked","deprecation"})
public class ClusterResourceAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2168694966627782505L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ClusterResourceAddAction.class);

    /**
     * 站点角色值.
     */
    private static final String ROLETYPE_SITE = "2";
    
    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "clusterCabinet";
    
    /**
     * 站点集合.
     */
    private List<ClusterCabinetDomain> siteList = new ArrayList<ClusterCabinetDomain>();
    
    /**
     * 机房集合.
     */
    private List<ClusterCabinetDomain> zoneList = new ArrayList<ClusterCabinetDomain>();
    
    /**
     * 机柜实体.
     */
    private ClusterCabinetDomain cluster = new ClusterCabinetDomain();
    
    /**
     * 角色类型.1-中心管理员,2-站点管理员
     */
    private String roleType;
    
    /**
     * 站点ID.
     */
    private String siteId;
    
    /**
     * @return action映射名
     */
    public String beforeAdd() {
        logger.info(getText("resource.cluster.title") + getText("log.beforeAdd.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo)request.getSession().getAttribute("userInfo");
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE+".findSiteInfo", user);
            roleType = user.getRoleType();
            if(ROLETYPE_SITE.equals(roleType)){
                cluster.setSiteId(siteList.get(0).getSiteId());
                cluster.setSiteName(siteList.get(0).getSiteName());
                zoneList = ibatisDAO.getData(IBATIC_NAMESPACE+".findZoneBySiteId",String.valueOf(user.getSiteId()));
            }
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.beforeAdd.error"), e);
        } 
        logger.info(getText("resource.cluster.title") + getText("log.beforeAdd.end"));
        return "add";
    }

    /**
     * @return action映射名
     */
    public String add() {
        logger.info(getText("resource.cluster.title") + getText("log.add.begin"));
        String opParam[] = { getText("resource.cluster.clusterName") + ": " + cluster.getClusterName() };
        try {
            ibatisDAO.insertData(IBATIC_NAMESPACE+".insert", cluster);
            msg = getText("common.message.addSuccess");
            logger.info(getText("resource.cluster.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
            return "adderror";
        }
        return "addsuccess";
    }

    /**
     * 获得机房信息.
     */
    
    public void getZone(){
        logger.info(getText("resource.cluster.title") + getText("log.resource.searchZone.start"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            List<ClusterCabinetDomain> list = ibatisDAO.getData(IBATIC_NAMESPACE+".findZoneBySiteId",siteId);
            JSONArray array = new JSONArray();
            for(ClusterCabinetDomain cabinet:list){
                array.add(cabinet);
            }
            pw.write(array.toString());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.error(getText("resource.cluster.title") + getText("log.resource.searchZone.error"), e);
        }
        logger.info(getText("resource.cluster.title")+getText("log.resource.searchZone.end"));
    }
    /**
     * 验证机柜重名.
     */
    public void validClusterName() {
        logger.info(getText("resource.cluster.title")+getText("log.resource.sameClusterName.start"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            ClusterCabinetDomain cabinet = new ClusterCabinetDomain();
            cabinet.setClusterName(request.getParameter("clusterName"));
            cabinet.setZoneId(request.getParameter("zoneId"));
            if(!StringUtils.isEmpty(request.getParameter("clusterId"))){
                cabinet.setClusterId(request.getParameter("clusterId"));
            }
            int count = (Integer)ibatisDAO.getSingleRecord(IBATIC_NAMESPACE+".validSameName", cabinet);
            if(count>0){
                pw.write(getText("resource.message.cluster.samename"));
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.error(getText("log.resource.sameClusterName.error"), e);
        }
        logger.info(getText("resource.cluster.title")+getText("log.resource.sameClusterName.end"));
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
     * @return the zoneList
     */
    public List<ClusterCabinetDomain> getZoneList() {
        return zoneList;
    }

    /**
     * @param zoneList the zoneList to set
     */
    public void setZoneList(List<ClusterCabinetDomain> zoneList) {
        this.zoneList = zoneList;
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
