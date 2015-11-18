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
 * 删除机柜.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-23
 */
@SuppressWarnings({"deprecation"})
public class ClusterResourceDelAction extends OperationLogAction implements IAuthIdGetter {
   
    /**
     * 标识.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ClusterResourceDelAction.class);
    
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
     * @return action映射名
     */
    public String delete() {
        logger.info(getText("resource.cluster.title") + getText("log.delete.begin"));
        String opParam[] = { getText("resource.cluster.clusterName") + ": " + cluster.getClusterName() };
        try {
            //机柜中是否存在设备
            int count = (Integer) ibatisDAO.getSingleRecord(IBATIC_NAMESPACE+ ".findDeviceByClusterId", clusterId);
            if (count > 0) {
                errorMsg = getText("log.cluster.delete");
                operationInfo = getText("oplog.delete.error", opParam)
                        + getText("common.message.systemError");
            } else {
                ibatisDAO.deleteData(IBATIC_NAMESPACE + ".deleteCluster", clusterId);
                
                msg = getText("common.message.delSuccess");
                logger.info(getText("resource.cluster.title") + getText("log.delete.end"));
                operationInfo = getText("oplog.delete.success", opParam);
            }
        } catch (Exception e) {
            logger.info(getText("resource.cluster.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
        }
        logger.info(getText("resource.cluster.title") + getText("log.delete.end"));
        return "delete";
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
