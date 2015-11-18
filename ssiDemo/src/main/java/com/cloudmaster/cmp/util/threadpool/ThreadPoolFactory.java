/*******************************************************************************
 * @(#)ThreadPoolFactory.java 2010-12-21
 *
 * Copyright 2010 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2010-12-21 下午06:07:35
 */
public class ThreadPoolFactory {

    private final Logger log = Logger.getLogger(ThreadPoolFactory.class);

    public ThreadPoolExecutor newTheadPool(String threadPoolName) {
        ThreadPoolExecutor threadPool = null;
        if (threadPoolName.equalsIgnoreCase(Constants.cmpThreadPoolName)) {
            threadPool = generateThreadPool(Constants.cmpThreadPoolCoreSize,
                    Constants.cmpThreadPoolMaxSize,
                    Constants.cmpThreadPoolkeepAliveTime,
                    Constants.cmpThreadPoolQueueCapacity, threadPoolName);
        } else {
            log.error("[ThreadPoolFactory] 错误的线程池名字");
        }
        return threadPool;
    }

    /**
     * @return threadPool
     */
    private ThreadPoolExecutor generateThreadPool(int corePoolSize, int maxPoolSize,
            int keepAliveTime, int queueSize, final String threadPoolName) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue(queueSize));
        threadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.error("" + threadPoolName + "队列已满");
            }
        });
        return threadPool;
    }
    /**
     * 初始化线程池 ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit
     * unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
     * corePoolSize:线程池维护线程的最少数量, maximumPoolSiz:线程池维护线程的最大数量,keepAliveTime:线程池维护线程所允许的空闲时间,unit:
     * 线程池维护线程所允许的空闲时间的单位,workQueue:线程池所使用的缓冲队列,handler:线程池对拒绝任务的处理策略
     * ________________________________________________________________________________________
     * 当一个任务通过execute(Runnable)方法欲添加到线程池时：
     * 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。 如果此时线程池中的数量等于
     * corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
     * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
     * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
     * 也就是：处理任务的优先级为： 核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     * 当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
     * unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性：
     * NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。
     * workQueue我常用的是：java.util.concurrent.ArrayBlockingQueue handler有四个选择：
     * ThreadPoolExecutor.AbortPolicy() 抛出java.util.concurrent.RejectedExecutionException异常
     * ThreadPoolExecutor.CallerRunsPolicy() 由调用者执行这个任务 ThreadPoolExecutor.DiscardOldestPolicy()
     * 抛弃旧的任务 ThreadPoolExecutor.DiscardPolicy() 抛弃当前的任务
     * ____________________________________________________________________________________________
     * 小例子： 构造一个线程池 ThreadPoolExecutor producerPool = new ThreadPoolExecutor(2, 4, 0,
     * TimeUnit.SECONDS, new ArrayBlockingQueue(3), new ThreadPoolExecutor.DiscardOldestPolicy());
     * 执行任务 producerPool.execute(new ThreadPoolTask(new Runnable()));
     */
}
