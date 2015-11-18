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
package com.cloudmaster.cmp.performance.mibinfo.web;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除MIB信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibInfoDeleteAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * MIB ID
     */
    private String mibId;

    /**
     * MIB信息
     */
    private MibinfoDomain mibinfoDomain;

    /**
     * MIB信息
     */
    private List<MibinfoDomain> mibList;

    private ThresholdDomain thresholdDomain;

    /**
     * 标识.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(MibInfoDeleteAction.class);

    public String delete() {
        String opParam[] = { getText("mib.mibName") + ": " + mibinfoDomain.getMibName() };
        logger.info(getText("function.title") + getText("log.delete.begin"));
        try {
            mibinfoDomain.setParentId(mibId);
            // 获取MIB信息
            int i = ibatisDAO.getCount("MibInfo.getMibListCount", mibinfoDomain);
            // 有子节点的情况下不能删除
            if (i > 0) {
                errorMsg = this.getText("mib.delete.error.parent");
                logger.info(errorMsg);
                operationInfo = getText("oplog.delete.error", opParam) + errorMsg;
                return SUCCESS;
            }

            // 删除规则
            ibatisDAO.deleteData("ThresholdInfo.deleteThresholdByMibId", mibId);
            // 删除MIB
            ibatisDAO.deleteData("MibInfo.deleteMibinfoById", mibId);
            msg = this.getText("common.message.delSuccess");
            operationInfo = getText("oplog.delete.success", opParam);
            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (SQLException e) {
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
        }
        return SUCCESS;
    }


    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibinfoDomain(MibinfoDomain mibinfoDomain) {
        this.mibinfoDomain = mibinfoDomain;
    }

    public MibinfoDomain getMibinfoDomain() {
        return mibinfoDomain;
    }

    public List<MibinfoDomain> getMibList() {
        return mibList;
    }

    public void setMibList(List<MibinfoDomain> mibList) {
        this.mibList = mibList;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

}
