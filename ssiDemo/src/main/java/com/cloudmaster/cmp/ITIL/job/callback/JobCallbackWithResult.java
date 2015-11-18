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
package com.cloudmaster.cmp.ITIL.job.callback;

import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;

import com.neusoft.mid.enzyme.quzrtz.QuzrtzException;
import com.neusoft.mals.ui.service.Service;
import com.neusoft.mid.enzyme.quzrtz.impls.ScriptJobCallback;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class JobCallbackWithResult extends ScriptJobCallback {
    /**
     * 日志输出类
     */
    private final static Log log = LogFactory.getLog(JobCallbackWithResult.class);

    /**
     * 服务Service
     */
    private Service service;

    @Override
    public void beforeJobExecute(JobExecutionContext context) throws QuzrtzException {
        // log.info("beforeJobExecute begin");
        context.put("service", service);
        super.beforeJobExecute(context);
        // log.info("beforeJobExecute end");
    }

    @Override
    public void postJobExecute(JobExecutionContext context) throws QuzrtzException {
        // log.info("postJobExecute begin");
        /*
         * 项目中使用taskid作为job的name
         */
        String TASK_ID = getTaskId(context);
        String EXECUTE_LOG = getTaskLog(context);

        try {
            // 当result返回结果为success时，表示job执行成功；其他则为失败
            String result = (String) context.getResult();
            log.info("jobresult = " + result);
            if (null != result && result.equals("success")) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("TASK_ID", TASK_ID);
                params.put("EXECUTE_RESULT", "0");
                params.put("EXECUTE_LOG", EXECUTE_LOG);
                service.insert("cronExpressionManager.insertTaskLog", params);
            } else {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("TASK_ID", TASK_ID);
                params.put("EXECUTE_RESULT", "1");
                params.put("EXECUTE_LOG", EXECUTE_LOG);
                service.insert("cronExpressionManager.insertTaskLog", params);
            }
        } catch (Exception e) {
            // log.info("postJobExecute error " + e);
            throw new QuzrtzException(e);
        }// end try
        context.put("service", service);
        super.postJobExecute(context);
        // log.info("postJobExecute end");
    }

    @Override
    public void onJobException(JobExecutionContext context, Exception exception)
            throws QuzrtzException {
        // log.info("onJobException begin");
        String TASK_ID = getTaskId(context);
        String EXECUTE_LOG = getTaskLog(context) + ":" + exception.getMessage();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("TASK_ID", TASK_ID);
        params.put("EXECUTE_RESULT", "1");
        params.put("EXECUTE_LOG", EXECUTE_LOG);
        try {
            service.insert("cronExpressionManager.insertTaskLog", params);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.info("onJobException error " + e);
            }
        }// end try
        context.put("service", service);
        super.onJobException(context, exception);
        // log.info("onJobException end");
    }

    private String getTaskId(JobExecutionContext context) {
        /*
         * 项目中使用taskid作为job的name
         */
        String taskid = context.getJobDetail().getName();
        return taskid;
    }

    private String getTaskLog(JobExecutionContext context) {
        /*
         * 
         */
        String tasklog = getTaskId(context) + ":" + (String) context.get("log");
        return tasklog;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
