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


import java.util.ArrayList;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.cluster.view.dao.ClusterCabinetDomain;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机柜修改.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-23
 */
@SuppressWarnings({"unchecked","deprecation"})
public class ClusterResourceEditAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * 标识.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ClusterResourceEditAction.class);
   
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
     * 机柜ID.
     */
    private String clusterId;
    /**
     * 编辑前操作(查询) 2011-12-23 下午03:47:00
     * @return action映射名
     * @throws Exception
     */
    public String beforeEdit() throws Exception {
        logger.info(getText("resource.cluster.title") + getText("log.beforeEdit.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            roleType = user.getRoleType();
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findSiteInfo", user);
            cluster = (ClusterCabinetDomain) ibatisDAO.getSingleRecord(IBATIC_NAMESPACE+".detail", clusterId);
            zoneList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findZoneBySiteId",
                    cluster.getSiteId());
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
        }
        logger.info(getText("resource.cluster.title") + getText("log.beforeEdit.end"));
        return "edit";
    }

    /**
     * @return action映射名
     */
    public String edit() {
        logger.info(getText("resource.cluster.title") + getText("log.edit.begin"));
        String opParam[] = { getText("resource.cluster.clusterName") + ": " + cluster.getClusterName() };
        try {
             ibatisDAO.updateData(IBATIC_NAMESPACE+".update", cluster);
             
             msg = getText("common.message.editSuccess");
             logger.info(getText("resource.cluster.title") + getText("log.edit.end"));
             operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.edit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            return "editerror";
        }
        logger.info(getText("resource.cluster.title") + getText("log.edit.end"));
        return "editsuccess";
    }

    /**
     * @return the clusterId
     */
    public String getClusterId() {
        return clusterId;
    }

    /**
     * @param clusterId the clusterId to set
     */
    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
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
    
}
