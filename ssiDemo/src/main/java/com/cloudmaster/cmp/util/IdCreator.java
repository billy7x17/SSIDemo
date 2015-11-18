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

/**
 * Id生产类
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public final class IdCreator {

    // 当前ID
    private static int currentId = 0;

    // 最后时间
    private static String lastTime = "";

    // 最大值
    private static final int MAX = 9999;

    /**
     * 构造函数
     */
    private IdCreator() {
    }

    /**
     * ID产生方式为：时间戳取后12位+静态序列号，如：1007121935200，其中序列号最大为9999，时间戳不同时序列号归零
     * @return id 标识号
     */
    public static synchronized String getId() {
        String timestamp = DateUtil.getCalendarTime();
        timestamp = timestamp.substring(2, timestamp.length());
        if (!lastTime.equals(timestamp) || currentId >= MAX) {
            currentId = 0;
        } else {
            currentId++;
        }
        lastTime = timestamp;
        String id = timestamp + currentId;
        return id;
    }
}
