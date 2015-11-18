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
 * 此接口是所有需要鉴权的aciton都要实现的接口，在action中返回一个字符串，即该action的权限点ID， 鉴权时只需得到该返回值，不必在uri中截取，方便准确
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IAuthIdGetter {

    /**
     * 获取action的权限点ID
     * @return
     */
    String getAuthId();
}
