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
package com.cloudmaster.cmp.authority.auth;

/**
 * 鉴权接口，用于判断用户是否有操作权限
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IAuthenticater {
    /**
     * 用于判断用户是否有查看报表的权限
     * @param reportId 报表Id
     * @return false 鉴权失败，true 鉴权成功
     */
    boolean authenticateReport(String reportId);

    /**
     * 用于判断用户是否有其他操作功能的权限
     * @param 权限点ID
     * @return false 鉴权失败，true 鉴权成功
     */
    boolean authenticateUrl(String authId);
}
