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

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询阈值信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdSearchAction extends PageAction implements IAuthIdGetter, IOperationLog {

    // 错误信息
    private String errorMsg;

    // 跳转页面
    private String forward = "SUCCESS";

    // 阈值信息
    private ThresholdDomain thresholdDomain;

    // 阈值信息
    private List<ThresholdDomain> thresholdList;

    // 阈值名称
    private String eventName;

    // 告警等级
    private String level;

    // 阈值符号
    private String perCondition;

    // 阈值
    private String value;

    // 设备名称
    private String deviceName;

    // 设备类型
    private String deviceType;

    // 设备IP
    private String deviceIp;

    private static final long serialVersionUID = 1L;
    /**
     * 日志，功能模块名称
     */
    private String functionName;

    /**
     * 日志，操作信息
     */
    private String operationInfo;

    /**
     * 日志，操作类型
     */
    private String opType;
    /**
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdSearchAction.class);

    /**
     * 查询
     * @return
     */
    @SuppressWarnings("unchecked")
    public String search() {

        logger.info(getText("function.title") + getText("search.function"));
        thresholdDomain = this.initSearch(thresholdDomain);
        try {
            // 获取阈值信息
            setThresholdList(getPage("ThresholdInfo.getThresholdListCount",
                    "ThresholdInfo.getThresholdList", thresholdDomain));
            if (getThresholdList() == null || getThresholdList().size() == 0) {
                msg = this.getText("common.message.searchNodata");
                logger.info(msg);
            }
            operationInfo = getText("oplog.search.success");
        } catch (Exception e) {
            errorMsg=this.getText("common.message.searchError");
            logger.error(errorMsg, e);
            operationInfo = getText("oplog.search.error") + errorMsg;
        }
        return forward;
    }

    /**
     * 查询条件设置
     * @param deviceObject
     * @return
     */
    private ThresholdDomain initSearch(ThresholdDomain thresholdObj) {

        thresholdObj.setEventName(this.getEventName());
        thresholdObj.setLevel(this.getLevel());
        thresholdObj.setPerCondition(this.getPerCondition());
        thresholdObj.setValue(this.getValue());
        thresholdObj.setAgentType(this.getDeviceType());
        return thresholdObj;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public String getLevel() {
        if(level!=null){
            level = level.trim();
        }
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPerCondition() {
        if(perCondition!=null){
            perCondition = perCondition.trim();
        }
        return perCondition;
    }

    public void setPerCondition(String perCondition) {
        this.perCondition = perCondition;
    }

    public String getValue() {
        if(value!=null){
            value = value.trim();
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeviceName() {
        if(deviceName!=null){
            deviceName = deviceName.trim();
        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        if(deviceType!=null){
            deviceType = deviceType.trim();
        }
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIp() {
        if(deviceIp!=null){
            deviceIp = deviceIp.trim();
        }
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public List<ThresholdDomain> getThresholdList() {
        return thresholdList;
    }

    public void setThresholdList(List<ThresholdDomain> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpType() {
        return opType;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

}
