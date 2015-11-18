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

/**
 * 操作日志查询条件BEAN
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class SearchLogInfo {
    private String userId;

    private String userName;

    private String ip;

    private String sessionId;

    // private String productId;
    //
    // private String serverInsId;

    private String startTime;

    private String endTime;

    // private String productVersion;

    // public String getProductVersion() {
    // return productVersion;
    // }
    //
    // public void setProductVersion(String productVersion) {
    // this.productVersion = productVersion;
    // }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    // public String getProductId() {
    // return productId;
    // }
    //
    // public void setProductId(String productId) {
    // this.productId = productId;
    // }
    //
    // public String getServerInsId() {
    // return serverInsId;
    // }
    //
    // public void setServerInsId(String serverInsId) {
    // this.serverInsId = serverInsId;
    // }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("userId:" + userId);
        sb.append(",userName:" + userName);
        sb.append(",ip:" + ip);
        sb.append(",sessionId:" + sessionId);
        sb.append(",startTime:" + startTime);
        sb.append(",endTime:" + endTime);
        // sb.append(",productId:" + productId);
        return sb.toString();
    }
}
