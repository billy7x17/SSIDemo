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
package com.cloudmaster.cmp.core.kmApi.user;

import com.cloudmaster.cmp.core.kmApi.KmApiException;

/**
 * 用户管理
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version 1.0.0 18 Mar 2012
 */
public class DelUserException extends KmApiException {

    private static final long serialVersionUID = 1L;

    public DelUserException(Throwable cause) {
        super("DelUserException:" + cause.getMessage(), cause);
    }
}
