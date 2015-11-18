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
package com.cloudmaster.cmp.container.ibatis;

import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 数据库操作
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TrimPropertyPlaceholderConfigurer
        extends PropertyPlaceholderConfigurer {

    @SuppressWarnings("unchecked")
    protected String parseStringValue(String strVal, Properties props, Set originalPlaceholder) {
        if (strVal != null) {
            strVal = strVal.trim();
        }
        return super.parseStringValue(strVal, props, originalPlaceholder);
    }

}
