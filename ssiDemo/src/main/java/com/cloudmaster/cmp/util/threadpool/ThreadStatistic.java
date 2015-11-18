/*******************************************************************************
 * @(#)ThreadStatistic.java 2010-12-21
 *
 * Copyright 2010 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.threadpool;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2010-12-21 下午06:29:34
 */
public class ThreadStatistic {

    private Timer timer = new Timer();

    public ThreadStatistic() {
        if (Constants.ThreadpoolStatisticEnable == true) {
            timer.schedule(new StatisticTask(), 5000, 5 * 1000);
            System.out.println("线程池统计功能启动。");
        }
    }

    class StatisticTask extends TimerTask {

        @Override
        public void run() {
            int timingSmsThreadPoolActiveCount = ThreadPoolModule.getInstance()
                    .getCmpthreadpool().getActiveCount();
            int timingSmsThreadPoolPoolSize = ThreadPoolModule.getInstance()
                    .getCmpthreadpool().getPoolSize();
            long timingSmsThreadPoolTaskCount = ThreadPoolModule.getInstance()
                    .getCmpthreadpool().getTaskCount();
            long timingSmsThreadPoolCompletedTaskCount = ThreadPoolModule.getInstance()
                    .getCmpthreadpool().getCompletedTaskCount();
            int timingSmsThreadPoolQueueSize = ThreadPoolModule.getInstance()
                    .getCmpthreadpool().getQueue().size();

            System.out.println("线程池，当前正在执行的线程数目  ：" + timingSmsThreadPoolActiveCount);
            System.out.println("线程池，当前池中的线程数目      ：" + timingSmsThreadPoolPoolSize);
            System.out.println("线程池，已经被计划执行的任务数目：" + timingSmsThreadPoolTaskCount);
            System.out.println("线程池，已经完成的任务数目      ：" + timingSmsThreadPoolCompletedTaskCount);
            System.out.println("线程池，队列中的任务数目      ：" + timingSmsThreadPoolQueueSize + "\n\n");

        }
    }
}
