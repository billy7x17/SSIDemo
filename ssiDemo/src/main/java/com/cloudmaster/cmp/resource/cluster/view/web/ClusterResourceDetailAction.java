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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.cluster.view.dao.ClusterCabinetDomain;

import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机柜详细信息.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-23
 */
public class ClusterResourceDetailAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 标识.
     */
    private static final long serialVersionUID = -6931734687697828335L;
    
    /**
     * log.
     */
    private static final LogService logger = LogService.getLogger(ClusterResourceDetailAction.class);
    
    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "clusterCabinet";
    
    /**
     * 机柜实体.
     */
    private ClusterCabinetDomain cluster = new ClusterCabinetDomain();
    
    /**
     * 机柜主键.
     */
    private String clusterId;
    
    /**
     * 实体.
     */
    private ClusterCabinetDomain cabinet = new ClusterCabinetDomain();
    
    /**
     * @return action映射名
     */
    @SuppressWarnings("deprecation")
    public String detail() {
        logger.info(getText("resource.cluster.title") + getText("log.detail.begin"));
        String opParam[] = { getText("resource.cluster.clusterName") + ": " + cluster.getClusterName() };
        try {
            cabinet = (ClusterCabinetDomain)ibatisDAO.getSingleRecord(IBATIC_NAMESPACE+".detail", clusterId);
            operationInfo = getText("oplog.detail.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.detail.error"), e);
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
        }
        logger.info(getText("resource.cluster.title") + getText("log.detail.end"));
        return "detail";
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
     * @return the cabinet
     */
    public ClusterCabinetDomain getCabinet() {
        return cabinet;
    }

    /**
     * @param cabinet the cabinet to set
     */
    public void setCabinet(ClusterCabinetDomain cabinet) {
        this.cabinet = cabinet;
    }

    /**
     * @return Returns the cluster.
     */
    public ClusterCabinetDomain getCluster() {
        return cluster;
    }

    /**
     * @param cluster The cluster to set.
     */
    public void setCluster(ClusterCabinetDomain cluster) {
        this.cluster = cluster;
    }
    
}
