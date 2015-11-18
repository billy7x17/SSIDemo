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
package com.cloudmaster.cmp.util.shortMessage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发送短信
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public final class PropertiesMessages {
    // ResourceBundle
    private static ResourceBundle RESOURCE_BUNDLE = null;

    // log
    private static LogService log = LogService.getLogger(PropertiesMessages.class);

    /**
     * 初始化，绑定数据
     */
    private static void initResourceBundle() {
        String classPath = PropertiesMessages.class.getResource("").getPath();
        classPath = classPath.replace("com/cloudmaster/cmp/util/shortMessage/", "");
        classPath = classPath + "conf/other/SMConfig.properties";
        try {
            // RESOURCE_BUNDLE = ResourceBundle.getBundle(classPath,Locale.getDefault());
            InputStream in = new BufferedInputStream(new FileInputStream(classPath));
            RESOURCE_BUNDLE = new PropertyResourceBundle(in);
        } catch (Exception e) {
            log.info("initResourceBundle error,配置文件路径：" + classPath, e);
        }
    }

    /**
     * 获取配置
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            if (null == RESOURCE_BUNDLE) {
                initResourceBundle();
            }
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * 获取配置
     * @param key
     * @param messages
     * @return
     */
    public static String getString(String key, String[] messages) {
        try {
            if (null == RESOURCE_BUNDLE) {
                initResourceBundle();
            }
            String pattern = RESOURCE_BUNDLE.getString(key);
            return MessageFormat.format(pattern, messages);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static void main(String[] args) {
        String[] argg = new String[] { "2009-01-01", "001", "001" };
         System.out.println(PropertiesMessages.getString("mail.alert.subjec", argg));
        System.out.println(PropertiesMessages.getString("ssmm.mpiag.id"));

    }
}
