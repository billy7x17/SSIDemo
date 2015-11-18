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
package com.cloudmaster.cmp.alarm.standardize.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 告警采集
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmTimeRecord {

    /**
     * mals_device最后告警key
     */
    public static final String LASTTIME_MALS_DEVICE = "LT_MALS_DEVICE";

    /**
     * HOT_EVENT最后告警key
     */
    public static final String LASTTIME_HOT_EVENT = "LT_HOT_EVENT";

    /**
     * VM_HOT_EVENT最后告警key
     */
    public static final String LASTTIME_VM_HOT_EVENT = "LT_VM_HOT_EVENT";

    /**
     * NAGIOS_HOST最后告警key
     */
    public static final String LASTTIME_NAGIOS_HOST = "LT_NAGIOS_HOST";

    /**
     * NAGIOS_SERVICE最后告警key
     */
    public static final String LASTTIME_NAGIOS_SERVICE = "LT_NAGIOS_SERVICE";

    /**
     * 存放最后告警时间的map
     */
    private static Map<String, String> map = new ConcurrentHashMap<String, String>();

    /**
     * 数据库处理类
     */
    private AlarmDBProc dbProc;

    /**
     * 获取最后告警时间
     * @param str
     * @return
     */
    public String get(String str) {
        String lastTime = null;
        lastTime = (String) map.get(str);
        if (null != lastTime) {
            return lastTime;
        }
        lastTime = dbProc.getLastTime(str);
        if (null != lastTime && !lastTime.equals("")) {
            map.put(str, lastTime);
        }

        return lastTime;
    }

    /**
     * 设置最后告警时间
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        String oldValue = map.get(key);
        if (null == oldValue || value.compareTo(oldValue) > 0) {
            map.put(key, value);
        }
    }

    public AlarmDBProc getDbProc() {
        return dbProc;
    }

    public void setDbProc(AlarmDBProc dbProc) {
        this.dbProc = dbProc;
    }


}
