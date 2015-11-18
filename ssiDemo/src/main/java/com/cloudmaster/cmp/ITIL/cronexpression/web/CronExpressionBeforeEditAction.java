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

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
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
public class CronExpressionBeforeEditAction extends BaseAction implements IOperationLog,
        IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = 6082997810991454787L;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "cronExpressionManager";

    /**
     * 数据Bean
     */
    private CronExpressionManagerDomain domain = new CronExpressionManagerDomain();

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

    private static LogService logger = LogService.getLogger(CronExpressionBeforeEditAction.class);

    private String subsystemName;

    private String jobName;

    /**
     * 修改跳转
     * @return forward
     */
    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            domain = (CronExpressionManagerDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".detail", domain);
            if (null == domain) {
                errorMsg = getText("common.message.editError") + getText("common.message.nodata");
                forward = ERROR;
            } else {
                domain.setBeanID(getBeanIDFromTaskParam(domain.getTaskParam()));
                if (domain.getSsID() == null || "".equals(domain.getSsID())) {
                    subsystemName = getText("cronExpression.ssID.default");
                } else {
                    subsystemName = getText("quartz.subsystem.name." + domain.getSsID());
                }
                jobName = getJobNameByBeanID(domain.getSsID(), domain.getBeanID());
                jobName = jobName == null ? "" : jobName;
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
            forward = ERROR;
        }
        return forward;
    }

    /**
     * 从任务参数中获得beanID
     * @param taskParam
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getBeanIDFromTaskParam(String taskParam) {
        // logger.info("taskParam = " + taskParam);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(taskParam, Map.class);// 转成map
            if (map == null) {
                return "";
            }
            // logger.info("parameter map = " + map);
            return (String) map.get("beanId");
        } catch (Exception e) {
            logger.info("get beanID from task parameter error");
            return "";
        }
    }

    /**
     * 根据子系统标识查询任务beanID对应的jobName
     * @param ssID
     * @param beanID
     * @return
     */
    private String getJobNameByBeanID(String ssID, String beanID) {
        if (ssID == null || "".equals(ssID)) {
            ssID = "njcmp";
        }
        if (JobsUtil.jobListMap.get(ssID) == null) {
            return null;
        }
        for (Jobs.Job job : JobsUtil.jobListMap.get(ssID)) {
            if (beanID.equals(job.getBeanID())) {
                return job.getJobName();
            } else {
                continue;
            }
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

    public CronExpressionManagerDomain getDomain() {
        return domain;
    }

    public void setDomain(CronExpressionManagerDomain domain) {
        this.domain = domain;
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
     * @return Returns the subsystemName.
     */
    public String getSubsystemName() {
        return subsystemName;
    }

    /**
     * @param subsystemName The subsystemName to set.
     */
    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
