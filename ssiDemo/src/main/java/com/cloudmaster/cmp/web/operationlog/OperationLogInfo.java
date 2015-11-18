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
package com.cloudmaster.cmp.web.operationlog;

import com.cloudmaster.cmp.resource.view.dao.ExcelAnnotation;
import com.cloudmaster.cmp.web.BaseDomain;

/**
 * 操作日志BEAN
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class OperationLogInfo extends BaseDomain {

    @ExcelAnnotation(exportName = "用户登录名称")
    private String userId;

    @ExcelAnnotation(exportName = "用户姓名")
    private String userName;

    @ExcelAnnotation(exportName = "IP地址")
    private String ip;

    @ExcelAnnotation(exportName = "操作时间")
    private String dateTime;

    // 操作功能
    @ExcelAnnotation(exportName = "业务模块")
    private String action;

 // 操作类型， 例如登录登出
    @ExcelAnnotation(exportName = "操作类型")
    private String opType;
    
    // 操作信息
    @ExcelAnnotation(exportName = "操作描述")
    private String operationInfo;
    
    // 用于向数据库插入与取出数据，与数据库相应表中的相对应
    private Long logId;

    // 返回一字符串，表示分配给session的唯一的标识符
    private String sessionId;

    private String productId;

    private String productName;

    private String serverInsId;

    private String serverName;

    private String productVersion;
    
    private String startTime;  //起始时间
    
    private String endTime;    //终止时间

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * flexigrid查询字段名
     */
    private String qtype;

    /**
     * flexigrid查询字段值
     */
    private String query;

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getServerInsId() {
        return serverInsId;
    }

    public void setServerInsId(String serverInsId) {
        this.serverInsId = serverInsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("logId:" + logId);
        sb.append(",userId:" + userId);
        sb.append(",userName:" + userName);
        sb.append(",ip:" + ip);
        sb.append(",sessionId:" + sessionId);
        sb.append(",action:" + logId);
        sb.append(",operationInfo:" + operationInfo);
        sb.append(",dateTime:" + dateTime);
        sb.append(",productId:" + productId);
        sb.append(",productName:" + productName);
        sb.append(",serverInsId:" + serverInsId);
        sb.append(",serverName:" + serverName);
        sb.append(",opType:" + opType);
        return sb.toString();
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpType() {
        return opType;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
