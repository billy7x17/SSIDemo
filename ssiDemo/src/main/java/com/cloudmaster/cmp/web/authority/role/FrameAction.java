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

import com.cloudmaster.cmp.operationlog.OperationLogAction;

/**
 * @author <a href="mailto:kangbo@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FrameAction extends OperationLogAction {

    /**
     * 跳转到left页面
     * @return String
     * @param
     */
    public String frame() {
        return "SUCCESS";
    }

}
