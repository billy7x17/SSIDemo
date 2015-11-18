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

import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改MIB
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibInfoUpdateAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * oid名称
     */
    private String mibName;

    /**
     * oid
     */
    private String oid;

    /**
     * id
     */
    private String mibId;

    /**
     * 设备类型
     */
    private String typeId;

    /**
     * MIB信息
     */
    private MibinfoDomain mibinfoDomain;

    /**
     * MIB信息
     */
    private List<MibinfoDomain> mibList;

    /**
     * 父节点
     */
    private String mibParentId;

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(MibInfoUpdateAction.class);


    /**
     * 修改
     * @return
     */
    public String update() {
        String opParam[] = { getText("mib.mibName") + ": " + mibinfoDomain.getMibName() };
        logger.info(getText("function.title") + getText("log.edit.begin"));
        try {
            if(StringUtils.isEmpty(mibParentId)){
                mibinfoDomain.setParentId(null);
            }else{
                mibinfoDomain.setParentId(mibParentId);
            }
            // 修改mib信息
            getIbatisDAO().updateData("MibInfo.updateMibinfoDomain", mibinfoDomain);
            msg = this.getText("common.message.editSuccess");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            return "input";
        }
        return SUCCESS;
    }

    public void setMibName(String mibName) {
        this.mibName = mibName;
    }

    public String getMibName() {
        if (mibName != null) {
            mibName = mibName.trim();
        }
        return mibName;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOid() {
        if (oid != null) {
            oid = oid.trim();
        }
        return oid;
    }

    public void setMibinfoDomain(MibinfoDomain mibinfoDomain) {
        this.mibinfoDomain = mibinfoDomain;
    }

    public MibinfoDomain getMibinfoDomain() {
        return mibinfoDomain;
    }

    public void setMibList(List<MibinfoDomain> mibList) {
        this.mibList = mibList;
    }

    public List<MibinfoDomain> getMibList() {
        return mibList;
    }

    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public String getMibId() {
        if (mibId != null) {
            mibId = mibId.trim();
        }
        return mibId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setMibParentId(String mibParentId) {
        this.mibParentId = mibParentId;
    }

    public String getMibParentId() {
        return mibParentId;
    }

}
