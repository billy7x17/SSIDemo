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
package com.cloudmaster.cmp.ITIL.cronexpression.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.Jobs;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionBeforeAddAction extends BaseAction implements IOperationLog,
        IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = -8976616156020045449L;

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 任务类型
     */
    private HashMap<String, String> taskType = new HashMap<String, String>();

    /**
     * 异常提示信息
     */
    private String errorMsg;

    /**
     * 子系统选择列表
     */
    private List<Map<String, String>> ssList;

    private static LogService logger = LogService.getLogger(CronExpressionBeforeAddAction.class);

    private String SSID;

    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        try {
            ssList = new ArrayList<Map<String, String>>();
            Set<String> ssIDSet = JobsUtil.jobListMap.keySet();
            Iterator<String> it = ssIDSet.iterator();
            while (it.hasNext()) {
                String ssID = it.next();
                if (ssID.equals("njcmp")) {// 默认子系统为管理系统(njcmp),SSID为空,在add.jsp中硬代码实现，此处不需要增加大ssList中
                    continue;
                }
                Map<String, String> ssMap = new HashMap<String, String>();
                ssMap.put("ssID", ssID);
                ssMap.put("ssText", getText("quartz.subsystem.name." + ssID));
                ssList.add(ssMap);
            }
            logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeAdd.error"), e);
        }
        return forward;
    }

    public String getJobsBySSID() {
        HttpServletResponse response = ServletActionContext.getResponse();
        if ("".equals(SSID)) {
            SSID = "njcmp";
        }
        try {
            if (JobsUtil.jobListMap.get(SSID) == null) {
                response.getWriter().write("error");
            } else {
                StringBuilder sb = new StringBuilder("[");
                for (Jobs.Job job : JobsUtil.jobListMap.get(SSID)) {
                    sb.append("{beanID:'").append(job.getBeanID()).append("',jobName:'")
                            .append(job.getJobName()).append("'},");
                }
                sb.deleteCharAt(sb.length() - 1).append("]");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sb.toString());
            }
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getOpType() {
        return null;
    }

    @Override
    public String getOperationFunction() {
        return null;
    }

    @Override
    public String getOperationInfo() {
        return null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HashMap<String, String> getTaskType() {
        return taskType;
    }

    public void setTaskType(HashMap<String, String> taskType) {
        this.taskType = taskType;
    }

    /**
     * @return Returns the ssList.
     */
    public List<Map<String, String>> getSsList() {
        return ssList;
    }

    /**
     * @param ssList The ssList to set.
     */
    public void setSsList(List<Map<String, String>> ssList) {
        this.ssList = ssList;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String sSID) {
        SSID = sSID;
    }

}
