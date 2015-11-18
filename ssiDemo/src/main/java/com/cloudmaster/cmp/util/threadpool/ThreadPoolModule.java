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

package com.cloudmaster.cmp.util.threadpool;

import java.util.concurrent.ThreadPoolExecutor;


public class ThreadPoolModule {

    private static final ThreadPoolModule threadPoolModule = new ThreadPoolModule();

    // 线程池工厂
    private static final ThreadPoolFactory threadPoolFactory = new ThreadPoolFactory();

    // 线程池
    private static final ThreadPoolExecutor cmpThreadPool = threadPoolFactory
            .newTheadPool(Constants.cmpThreadPoolName);

    private final ThreadStatistic sta = new ThreadStatistic();

    private ThreadPoolModule() {
    }

    public static ThreadPoolModule getInstance() {
        return threadPoolModule;
    }

    public static ThreadPoolExecutor getCmpthreadpool() {
        return cmpThreadPool;
    }

}
