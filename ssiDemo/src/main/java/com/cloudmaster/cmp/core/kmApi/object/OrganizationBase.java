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
package com.cloudmaster.cmp.core.kmApi.object;

/**
 * 组织机构管理
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public abstract class OrganizationBase {
    /**
     * 组织编号
     */
    private String orgId = "";

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
