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
package com.cloudmaster.cmp.ITIL.job;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.neusoft.mals.ui.service.Service;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.enzyme.script.ScriptUtils;

/**
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ScriptJob extends BaseJob {

    /**
     * 日志输出类
     */
    private final static Log log = LogFactory.getLog(ScriptJob.class);

    /**
     * 服务service
     */
    private Service service;

    @SuppressWarnings("unchecked")
    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        JobDataMap map = context.getMergedJobDataMap();
        String uri = map.getString("uri");
        if (log.isInfoEnabled()) {
            log.info("uri:" + uri);
        }
        Map<String, Object> parameter = new HashMap<String, Object>(map.getWrappedMap());
        if (uri != null && !"".equals(uri)) {
            parameter.put("service", service);
            Object log = ScriptUtils.executeScript(new URI(uri), parameter);
            context.put("log", log.toString());
        }else{
            if (log.isInfoEnabled()) {
                log.info("skip job:" + context.getJobDetail().getFullName());
            }
            context.put("log", "skip job:" + context.getJobDetail().getFullName());
        }//end if
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
