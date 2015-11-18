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
package com.cloudmaster.cmp.alarm.threshold.web;

import java.util.List;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdContentDomain;
import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改阈值
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdEditAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private ThresholdDomain thresholdDomain;

    private List<ThresholdDomain> thresholdList;

    private String id;

    private String thresholdName;

    private String agentId;

    private String oid;

    private String level;

    private String perCondition;

    private String value;

    private String value2;

    private String description;

    private String tcTitle;

    private String alarmImpact;

    /**
     * 阀值类型
     */
    private String dealType;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdEditAction.class);

    /**
     * 业务实例
     */
    private String systemId;

    /**
     * 修改
     * @return
     */
    public String edit() {
        String opParam[] = { thresholdName };
        logger.info(getText("function.title") + getText("edit.function"));

        try {
            thresholdDomain = this.initEdit(thresholdDomain);

            ThresholdContentDomain titleParam = new ThresholdContentDomain();
            titleParam.setTcId(thresholdDomain.getTcTitle());
            ThresholdContentDomain titleBean = (ThresholdContentDomain) ibatisDAO.getSingleRecord(
                    "ThresholdInfo.getThresholdContentList", titleParam);
            if (null != titleBean) {
                thresholdDomain.setAlarmIdentityID(titleBean.getAlarmIdentityID());
            }
            // 修改阈值信息
            getIbatisDAO().updateData("ThresholdInfo.editThresholdDomain", thresholdDomain);
            msg = this.getText("common.message.editSuccess");
            logger.info(msg);
            operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            errorMsg = this.getText("common.message.editError");
            logger.error(errorMsg, e);
            operationInfo = getText("oplog.edit.error", opParam) + errorMsg;
        }
        return forward;
    }

    /**
     * 查询条件设置
     * @param deviceObject
     * @return
     */
    private ThresholdDomain initEdit(ThresholdDomain thresholdObject) {
        thresholdObject.setId(this.getId());
        thresholdObject.setEventName(this.getThresholdName());
        thresholdObject.setMibId(this.getOid());
        thresholdObject.setLevel(this.getLevel());
        thresholdObject.setPerCondition(this.getPerCondition());
        thresholdObject.setValue(this.getValue());
        thresholdObject.setEventTypeDesc(this.getDescription());
        thresholdObject.setTcTitle(getTcTitle());
        // thresholdObject.setDealType(this.getDealType());
        thresholdObject.setValue2(this.getValue2());
        thresholdObject.setAlarmImpact(alarmImpact);
        thresholdObject.setSystemId(systemId);
        return thresholdObject;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setThresholdList(List<ThresholdDomain> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public List<ThresholdDomain> getThresholdList() {
        return thresholdList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getThresholdName() {
        if (thresholdName != null) {
            thresholdName = thresholdName.trim();
        }
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public String getAgentId() {
        if (agentId != null) {
            agentId = agentId.trim();
        }
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getOid() {
        if (oid != null) {
            oid = oid.trim();
        }
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getLevel() {
        if (level != null) {
            level = level.trim();
        }
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPerCondition() {
        if (perCondition != null) {
            perCondition = perCondition.trim();
        }
        return perCondition;
    }

    public void setPerCondition(String perCondition) {
        this.perCondition = perCondition;
    }

    public String getValue() {
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        if (description != null) {
            description = description.trim();
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        if (id != null) {
            id = id.trim();
        }
        return id;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public void setTcTitle(String tcTitle) {
        this.tcTitle = tcTitle;
    }

    public String getTcTitle() {
        return tcTitle;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getAlarmImpact() {
        return alarmImpact;
    }

    public void setAlarmImpact(String alarmImpact) {
        this.alarmImpact = alarmImpact;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

}
