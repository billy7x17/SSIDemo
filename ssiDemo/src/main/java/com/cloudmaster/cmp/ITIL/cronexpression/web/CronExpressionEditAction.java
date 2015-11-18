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

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
import com.cloudmaster.cmp.ITIL.cronexpression.dao.Jobs;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.SystemPropertiesLoader;
import com.neusoft.mid.enzyme.quartz.web.util.QuzrtzUtil;
import com.neusoft.mid.enzyme.quzrtz.QuzrtzManager;
import com.neusoft.mid.enzyme.rmi.QuartzRMIServiceClient;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionEditAction extends OperationLogAction implements IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = -4004128589394809417L;

    private static LogService logger = LogService.getLogger(CronExpressionEditAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "cronExpressionManager";

    /**
     * 数据Bean
     */
    private CronExpressionManagerDomain domain = new CronExpressionManagerDomain();

    /**
     * 任务类型
     */
    private HashMap<String, String> taskType = new HashMap<String, String>();

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 异常提示信息
     */
    private String errorMsg;

    private QuzrtzManager quzrtzManager;

    private String subsystemName;

    private String jobName;

    /**
     * 修改
     * @return forward
     */
    public String edit() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam = domain.getTaskID();
        try {
            domain.setTaskParam(getTaskParam(domain.getBeanID())); // 根据beanId拼装taskParam
            if (domain.getSsID() == null || "".equals(domain.getSsID())) {
                subsystemName = getText("cronExpression.ssID.default");
            } else {
                subsystemName = getText("quartz.subsystem.name." + domain.getSsID());
            }
            jobName = getJobNameByBeanID(domain.getSsID(), domain.getBeanID());
            jobName = jobName == null ? "" : jobName;
            Map<String, String> corn = new HashMap<String, String>();
            corn.put("TASK_ID", domain.getTaskID());
            corn.put("TASK_TYPE", domain.getTaskType());
            corn.put("EXCUTE_TIME", domain.getCronExpression());
            corn.put("TASK_PARA", domain.getTaskParam());
            String taskStat = domain.getTaskStatus();
            // 任务状态为0，表示有效，修改任务
            if ("0".equals(taskStat)) {
                String ssID = domain.getSsID();
                if (ssID == null || "".equals(ssID)) {
                    QuzrtzUtil.editTask(quzrtzManager, corn);
                } else {
                    try {
                        // 获得子系统RMI服务地址
                        String rmiURL = SystemPropertiesLoader.getString("quartz.rmiURL."
                                + domain.getSsID());
                        if (rmiURL == null) {
                            throw new Exception(getText("cronExpression.quartz.error.rmi.url"));
                        }
                        QuartzRMIServiceClient rmiClient = new QuartzRMIServiceClient(rmiURL);
                        rmiClient.editTask(corn);
                        logger.info("QuzrtzUtil edit task success!");
                    } catch (Exception e) {
                        logger.info(getText("function.title") + getText("log.edit.error"), e);
                        errorMsg = getText("common.message.editError")
                                + getText("cronExpression.quartz.error.connectSubsystem");
                        operationInfo = getText("oplog.edit.error", opParam);
                        forward = "input";
                        return forward;
                    }
                }
            } else {
                // 任务状态为1，标识无效，删除任务
                String ssID = domain.getSsID();
                if (ssID == null || "".equals(ssID)) {
                    QuzrtzUtil.deleteTask(quzrtzManager, corn);
                } else {
                    try {
                        // 获得子系统RMI服务地址
                        String rmiURL = SystemPropertiesLoader.getString("quartz.rmiURL."
                                + domain.getSsID());
                        if (rmiURL == null) {
                            throw new Exception(getText("cronExpression.quartz.error.rmi.url"));
                        }
                        QuartzRMIServiceClient rmiClient = new QuartzRMIServiceClient(rmiURL);
                        rmiClient.deleteTask(corn);
                        logger.info("QuzrtzUtil edit task success!");
                    } catch (Exception e) {
                        logger.info(getText("function.title") + getText("log.edit.error"), e);
                        errorMsg = getText("common.message.editError")
                                + getText("cronExpression.quartz.error.connectSubsystem");
                        operationInfo = getText("oplog.edit.error", opParam);
                        forward = "input";
                        return forward;
                    }
                }
            }
            int changed = ibatisDAO.updateData(IBATIS_NAMESPACE + ".update", domain);
            if (changed == 1) {
                msg = getText("common.message.editSuccess");
                operationInfo = getText("oplog.edit.success", opParam);
            } else {
                errorMsg = getText("common.message.editError")
                        + getText("cronExpression.error.sql");
            }
            logger.info(getText("function.title") + getText("log.edit.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            forward = "input";
        }
        return forward;
    }

    /**
     * 将beanID组合成为任务参数
     * @param beanID
     * @return
     */
    private String getTaskParam(String beanID) {
        StringBuffer sb = new StringBuffer("{\"beanId\":\"");
        sb.append(beanID);
        sb.append("\"}");
        logger.info("task parameter is " + sb.toString());
        return sb.toString();
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

    public QuzrtzManager getQuzrtzManager() {
        return quzrtzManager;
    }

    public void setQuzrtzManager(QuzrtzManager quzrtzManager) {
        this.quzrtzManager = quzrtzManager;
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
