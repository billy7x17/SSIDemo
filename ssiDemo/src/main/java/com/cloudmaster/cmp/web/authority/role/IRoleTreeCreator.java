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
package com.cloudmaster.cmp.web.authority.role;

import java.util.List;

import com.cloudmaster.cmp.web.authority.auth.AuthInfo;

/**
 * 此接口用于构造角色的权限点树形结构
 * @param authorityList 所有的权限点所组成的list
 * @return list 构造好的树形结构
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IRoleTreeCreator {
    List<AuthInfo> creatRoleTree(List<AuthInfo> authorityList);
}
