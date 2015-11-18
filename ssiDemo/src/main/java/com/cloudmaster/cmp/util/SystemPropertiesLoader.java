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
package com.cloudmaster.cmp.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:shen.di@neusoft.com">shen di</a>
 * @version $Revision 1.1 $ 2013-3-13 下午2:55:34
 */
public class SystemPropertiesLoader {
    private static final String BUNDLE_NAME = "conf.other.System"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private SystemPropertiesLoader() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
