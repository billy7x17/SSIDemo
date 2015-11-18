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
package com.cloudmaster.cmp.performance.tree.dao;

import java.io.Serializable;
/**
 * 通过物理机ID获取物理机IP，是否工程中状态，和虚拟机IP的Domain
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class PingDomain implements Serializable{
    private static final long serialVersionUID = -5332141168519205721L;
    private String id;
    private String ip;
    private String pmInstallState;
    public String getPmInstallState() {
        return pmInstallState;
    }
    public void setPmInstallState(String pmInstallState) {
        this.pmInstallState = pmInstallState;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    
}
