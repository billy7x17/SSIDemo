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
 * @author <a href="mailto:jiangxuan@neusoft.com">jiang xuan </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SearchExpertException extends KmApiException {

    private static final long serialVersionUID = 1L;

    public SearchExpertException(Throwable cause) {
        super("SearchExpertException:" + cause.getMessage(), cause);
    }

}