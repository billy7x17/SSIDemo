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

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.util.DateUtil;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 添加Mib
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibInfoAddAction extends OperationLogAction implements IAuthIdGetter {



    /**
     * oid名称
     */
    private String mibName;

    /**
     * oid
     */
    private String oid;

    /**
     * 设备类型
     */
    private String typeId;

    /**
     * 描述
     */
    private String description;

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
     * 是否为集合
     */
    private String isCollection;

    /**
     * 指标改造新增字段，指标单位、指标分组、是否显示在折线图上
     * @return
     */
    private String indexUnit;

    private String indexGroup;

    private String ifShowLine;

    /**
     * 业务实例Id
     */
    private String systemId;

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(MibInfoAddAction.class);

    public String add() {
        String opParam[] = { getText("mib.mibName") + ": " + mibinfoDomain.getMibName() };
        logger.info(getText("function.title") + getText("log.add.begin"));
        
        try {
            if(StringUtils.isEmpty(mibinfoDomain.getParentId())){
                mibinfoDomain.setParentId(null);
            }
            //mibinfoDomain = this.initAdd(mibinfoDomain);
            // 添加mib信息
            getIbatisDAO().insertData("MibInfo.addMibinfoDomain", mibinfoDomain);
            msg = this.getText("common.message.addSuccess");
            logger.info(getText("function.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success", opParam);
        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.add.error"), e);
            operationInfo = getText("oplog.add.error", opParam) + getText("common.message.addError");
            return  "input";
            
        }
        return SUCCESS;
    }

    /**
     * 添加指标domain设置
     * @param deviceObject
     * @return
     */
    private MibinfoDomain initAdd(MibinfoDomain mibinfoObject) {
        mibinfoObject.setOid(this.getOid());
        mibinfoObject.setMibName(this.getMibName());
        mibinfoObject.setTypeId(this.getTypeId());
        mibinfoObject.setDescription(this.getDescription());
        mibinfoObject.setCreateTime(DateUtil.getCalendarTime());
        mibinfoObject.setIsCollection(this.isCollection);
        // 指标改造新增三个字段
        mibinfoObject.setIndexUnit(indexUnit);
        mibinfoObject.setIndexGroup(indexGroup);
        mibinfoObject.setIfShowLine(ifShowLine);
        if (typeId.equals("20")) { // 业务实例类型
            //mibinfoObject.setSystemId(systemId);
        }
        String colomeName = "";
        // 列名字段需要判断
        try {
            // 若原始采集指标表中存在相同列名，则将列名字段设置为相同
            String oldColomeName = (String) ibatisDAO.getSingleRecord("MibInfo.getOldColomeName",
                    mibinfoObject);
            if (null != oldColomeName) {
                colomeName = oldColomeName;
            } else {
                // 原始表没有匹配列名，则查询MIB库中配置
                String createColomeName = "";
                if ("5".equals(indexGroup) || "6".equals(indexGroup)) {
                    createColomeName = (String) ibatisDAO.getSingleRecord(
                            "MibInfo.createPCColomeName", mibinfoObject);
                } else {
                    createColomeName = (String) ibatisDAO.getSingleRecord(
                            "MibInfo.createOtherColomeName", mibinfoObject);
                }
                if (null != createColomeName) {
                    // 若MIB库中配置列名，则将列名位数加1，作为新的列名
                    colomeName = createColomeName;
                } else if ("5".equals(indexGroup)) {
                    // MIB库中没有配置过列名
                    colomeName = "p_param1";
                } else if ("6".equals(indexGroup)) {
                    // MIB库中没有配置过列名
                    colomeName = "c_param1";
                } else {
                    // MIB库中没有配置过列名
                    colomeName = "param1";
                }
            }
            mibinfoObject.setColomeName(colomeName);
        } catch (SQLException e) {
            logger.info("initAdd error", e);
        }

        if (this.mibParentId == null || "".equals(this.mibParentId)) {
            mibinfoObject.setParentId("0");
        } else {
            mibinfoObject.setParentId(this.mibParentId);
        }

        return mibinfoObject;
    }

    /**
     * 通过设备类型，指标分组，以及指标单位判断该指标是否可以显示在折线图上
     */
    @SuppressWarnings("deprecation")
    public void showLineJudge() {
        logger.info(getText("function.title") + getText("add.function.showLineJudge.start"));
        mibinfoDomain = this.initAdd(mibinfoDomain);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            MibinfoDomain domain = (MibinfoDomain) ibatisDAO.getSingleRecord(
                    "MibInfo.getAlreadyShowIndex", mibinfoDomain);
            if (null != domain && "yes".equals(domain.getIndexUnit())) {
                pw.write("");
            } else {
                pw.write(domain.getIndexUnit());
            }
            logger.info(getText("function.title") + getText("add.function.showLineJudge.success"));
            operationInfo = getText("function.title")
                    + getText("add.function.showLineJudge.success");
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("add.function.showLineJudge.exception"), e);
            operationInfo = getText("function.title")
                    + getText("add.function.showLineJudge.exception");
        }
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        if (description != null) {
            description = description.trim();
        }
        return description;
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

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public String getIndexUnit() {
        return indexUnit;
    }

    public void setIndexUnit(String indexUnit) {
        this.indexUnit = indexUnit;
    }

    public String getIndexGroup() {
        return indexGroup;
    }

    public void setIndexGroup(String indexGroup) {
        this.indexGroup = indexGroup;
    }

    public String getIfShowLine() {
        return ifShowLine;
    }

    public void setIfShowLine(String ifShowLine) {
        this.ifShowLine = ifShowLine;
    }

}
