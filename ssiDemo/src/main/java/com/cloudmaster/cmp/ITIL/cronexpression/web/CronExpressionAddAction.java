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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
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
public class CronExpressionAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = 4951505484586663653L;

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

    private static LogService logger = LogService.getLogger(CronExpressionAddAction.class);

    /**
     * 子系统选择列表
     */
    private List<Map<String, String>> ssList;

    /**
     * 添加
     * @return forward
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String opParam = domain.getTaskID();
        try {
            String taskId = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".getTaskId",
                    null);
            domain.setTaskID(taskId);
            domain.setTaskParam(getTaskParam(domain.getBeanID())); // 根据beanId拼装taskParam
            Map<String, String> corn = new HashMap<String, String>();
            corn.put("TASK_ID", domain.getTaskID());
            corn.put("TASK_TYPE", domain.getTaskType());
            corn.put("EXCUTE_TIME", domain.getCronExpression());
            corn.put("TASK_PARA", domain.getTaskParam());

            // 判断子系统ID是否为空，为空则为管理系统本地任务，非空则为对应子系统任务，使用RMI远程调用子系统任务管理方法
            String ssID = domain.getSsID();
            if (ssID == null || "".equals(ssID)) {
                if ("0".equals(domain.getTaskStatus())) {
                    // 任务同步,当状态为有效时，添加任务
                    QuzrtzUtil.addTask(quzrtzManager, corn);
                }
            } else {
                try {
                    // 获得子系统RMI服务地址
                    String rmiURL = SystemPropertiesLoader.getString("quartz.rmiURL."
                            + domain.getSsID());
                    if (rmiURL == null) {
                        throw new Exception(getText("cronExpression.quartz.error.rmi.url"));
                    }
                    // 任务同步,当状态为有效时，添加任务
                    QuartzRMIServiceClient rmiClient = new QuartzRMIServiceClient(rmiURL);
                    if ("0".equals(domain.getTaskStatus())) {
                        rmiClient.addTask(corn);
                    }
                } catch (Exception e) {
                    errorMsg = getText("common.message.addError")
                            + getText("cronExpression.quartz.error.connectSubsystem");
                    operationInfo = getText("oplog.add.error", opParam);
                    forward = "input";
                    logger.info("QuzrtzUtil.addTask error", e);
                    beforeAdd();
                    return forward;
                }
            }
            ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert", domain);
            operationInfo = getText("oplog.add.success", opParam);
            msg = getText("common.message.addSuccess");
            logger.info(getText("function.title") + getText("log.add.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("cronExpression.error.sql");
            logger.info(getText("function.title") + getText("log.add.error"), e);
            forward = "input";
            beforeAdd();
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
     * 日期控件跳转
     * @return forward
     */
    public String cronExpression() {
        return SUCCESS;
    }

    /**
     * 异常时返回添加页面，获取子系统列表
     */
    public void beforeAdd() {
        String[] ssArr = SystemPropertiesLoader.getString("quartz.subsystem.list").split(",");
        ssList = new ArrayList<Map<String, String>>();
        for (String ssID : ssArr) {
            Map<String, String> ssMap = new HashMap<String, String>();
            ssMap.put("ssID", ssID);
            ssMap.put("ssText", getText("quartz.subsystem.name." + ssID));
            ssList.add(ssMap);
        }
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

}
