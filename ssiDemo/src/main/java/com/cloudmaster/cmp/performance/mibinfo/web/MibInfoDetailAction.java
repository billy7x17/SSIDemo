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

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * MIB详细
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibInfoDetailAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * MIB ID
     */
    private String mibId;

    /**
     * MIB信息
     */
    private MibinfoDomain mibinfoDomain;
    /**
     * 设备信息
     */
    private DeviceDomain deviceDomain;

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(MibInfoDetailAction.class);

    public String detail() {
        String opParam[]={ getText("mib.mibName") + ": " + mibinfoDomain.getMibName() };
        logger.info(getText("function.title") + getText("log.detail.begin"));
        try {

            // 获取MIB信息
            mibinfoDomain = (MibinfoDomain) this.ibatisDAO.getSingleRecord(
                    "MibInfo.getDetailMibinfoById", mibId);

            if(mibinfoDomain==null){
                msg = this.getText("mib.search.action.error");
                logger.info(msg);
                return  "input";
            }
           
            MibinfoDomain parentMib = (MibinfoDomain)ibatisDAO.getSingleRecord
                                ("MibInfo.getMibinfoById", mibinfoDomain.getParentId());
            
            if(parentMib!=null){
                mibinfoDomain.setParentName(parentMib.getMibName());
                mibinfoDomain.setParentId(parentMib.getMibId());
            }
           
            operationInfo = getText("oplog.detail.success",opParam);
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.detail.error"), e);
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
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

    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }

    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

}
