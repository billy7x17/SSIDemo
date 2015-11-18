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

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.neusoft.mid.enzyme.quzrtz.JobCallback;
import com.neusoft.mid.enzyme.quzrtz.QuzrtzException;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class JobCallbackWithResultNoScript implements JobCallback {
    /**
     * 日志输出类
     */
    private final static Log log = LogFactory.getLog(JobCallbackWithResultNoScript.class);

    /**
     * 服务Service
     */
    private IbatisDAO ibatisDAO;

    public void beforeJobExecute(JobExecutionContext context) throws QuzrtzException {
    }

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
                ibatisDAO.insertData("cronExpressionManager.insertTaskLog", params);
                log.info("任务执行成功");
            } else {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("TASK_ID", TASK_ID);
                params.put("EXECUTE_RESULT", "1");
                params.put("EXECUTE_LOG", EXECUTE_LOG);
                ibatisDAO.insertData("cronExpressionManager.insertTaskLog", params);
                log.info("任务执行失败");
            }
        } catch (Exception e) {
            // log.info("postJobExecute error " + e);
            throw new QuzrtzException(e);
        }// end try
        context.put("service", ibatisDAO);
        // log.info("postJobExecute end");
    }

    public void onJobException(JobExecutionContext context, Exception exception)
            throws QuzrtzException {
        // log.info("onJobException begin");
        log.info("任务执行出现异常");
        String TASK_ID = getTaskId(context);
        String EXECUTE_LOG = getTaskLog(context) + ":" + exception.getMessage();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("TASK_ID", TASK_ID);
        params.put("EXECUTE_RESULT", "1");
        params.put("EXECUTE_LOG", EXECUTE_LOG);
        try {
            ibatisDAO.insertData("cronExpressionManager.insertTaskLog", params);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.info("onJobException error " + e);
            }
        }// end try
        context.put("service", ibatisDAO);
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
         * context中log作为任务执行日志
         */
        String tasklog = (String) context.get("log");
        return tasklog;
    }

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
