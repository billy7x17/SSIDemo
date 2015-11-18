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
import com.cloudmaster.cmp.resource.collectdevice.web.DeviceAddAction;
import com.cloudmaster.cmp.util.DateUtil;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 添加阈值
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /* 告警唯一标识 */
    private String alarmIdentityID;

    /* 告警的影响描述 */
    private String alarmImpact;

    /**
     * 阈值名称
     */
    private String thresholdName;

    /**
     * 设备ID
     */
    private String agentId;

    /**
     * OID
     */
    private String oid;

    /**
     * 告警等级
     */
    private String level;

    /**
     * 告警符号
     */
    private String perCondition;

    /**
     * 阈值
     */
    private String value;

    /**
     * 阈值
     */
    private String value2;

    /**
     * 描述
     */
    private String description;

    /**
     * 阈值信息
     */
    private ThresholdDomain thresholdDomain;

    /**
     * 阈值信息
     */
    private List<ThresholdDomain> thresholdList;

    /**
     * 阀值类型
     */
    private String dealType;

    /**
     * 阈值标题
     */
    private String tcTitle;

    /**
     * 业务实例
     */
    private String systemId;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceAddAction.class);

    private static final long serialVersionUID = 1L;

    public String add() {
        String opParam[] = { thresholdName };
        logger.info(getText("function.title") + getText("add.function"));
        try {
            thresholdDomain = initAdd(thresholdDomain);

            ThresholdContentDomain titleParam = new ThresholdContentDomain();
            titleParam.setTcId(thresholdDomain.getTcTitle());
            ThresholdContentDomain titleBean = (ThresholdContentDomain) ibatisDAO.getSingleRecord(
                    "ThresholdInfo.getThresholdContentList", titleParam);
            if (null != titleBean) {
                thresholdDomain.setAlarmIdentityID(titleBean.getAlarmIdentityID());
            }
            // 添加阈值信息
            getIbatisDAO().insertData("ThresholdInfo.addThresholdDomain", thresholdDomain);
            msg = this.getText("common.message.addSuccess");
            logger.info(msg);
            operationInfo = getText("oplog.add.success", opParam);
        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError");
            logger.info(errorMsg, e);
            this.forward = "INPUT";
            operationInfo = getText("oplog.add.error", opParam) + errorMsg;
        }
        return forward;
    }

    /**
     * 条件设置
     * @param deviceObject
     * @return
     */
    private ThresholdDomain initAdd(ThresholdDomain thresholObject) {

        thresholObject.setLevel(this.getLevel());
        thresholObject.setValue(this.getValue());
        thresholObject.setMibId(this.getOid());
        thresholObject.setEventName(this.getThresholdName());
        thresholObject.setPerCondition(this.getPerCondition());
        thresholObject.setEventTypeDesc(this.getDescription());
        // thresholObject.setDealType(this.getDealType());
        thresholObject.setCreateTime(DateUtil.getCalendarTime());
        thresholObject.setTcTitle(this.getTcTitle());
        thresholObject.setValue2(this.getValue2());
        thresholObject.setAlarmIdentityID(alarmIdentityID);
        thresholObject.setAlarmImpact(alarmImpact);
        thresholObject.setAlarmImpact(alarmImpact);
        thresholObject.setSystemId(systemId);
        return thresholObject;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        if (agentId != null) {
            agentId = agentId.trim();
        }
        return agentId;
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

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        if (level != null) {
            level = level.trim();
        }
        return level;
    }

    public void setPerCondition(String perCondition) {
        this.perCondition = perCondition;
    }

    public String getPerCondition() {
        if (perCondition != null) {
            perCondition = perCondition.trim();
        }
        return perCondition;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        if (value != null) {
            value = value.trim();
        }
        return value;
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

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public String getThresholdName() {
        if (thresholdName != null) {
            thresholdName = thresholdName.trim();
        }
        return thresholdName;
    }

    public void setThresholdList(List<ThresholdDomain> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public List<ThresholdDomain> getThresholdList() {
        return thresholdList;
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

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
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
