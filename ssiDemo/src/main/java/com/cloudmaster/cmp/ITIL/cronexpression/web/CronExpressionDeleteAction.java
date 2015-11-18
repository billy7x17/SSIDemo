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
public class CronExpressionDeleteAction extends OperationLogAction implements IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = -2282772914104645177L;

    private static LogService logger = LogService.getLogger(CronExpressionAddAction.class);

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
     * 异常提示信息
     */
    private String errorMsg;

    private QuzrtzManager quzrtzManager;

    /**
     * 删除
     * @return forward
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String opParam = domain.getTaskID();
        try {
            domain = (CronExpressionManagerDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".detail", domain);
            if (null == domain) {
                msg = getText("cronExpression.delete.fail");
            } else {
                Map<String, String> corn = new HashMap<String, String>();
                corn.put("TASK_ID", domain.getTaskID());
                corn.put("TASK_TYPE", domain.getTaskType());
                String ssID = domain.getSsID();
                if (ssID == null || "".equals(ssID)) {
                    QuzrtzUtil.deleteTask(quzrtzManager, corn);
                } else {
                    // 获得子系统RMI服务地址
                    String rmiURL = SystemPropertiesLoader.getString("quartz.rmiURL."
                            + domain.getSsID());
                    if (rmiURL == null) {
                        throw new Exception(getText("cronExpression.quartz.error.rmi.url"));
                    }
                    QuartzRMIServiceClient rmiClient = new QuartzRMIServiceClient(rmiURL);
                    rmiClient.deleteTask(corn);
                }
                int deleted = ibatisDAO.deleteData(IBATIS_NAMESPACE + ".delete", domain);
                if (deleted == 1) {
                    msg = getText("common.message.delSuccess");
                    operationInfo = getText("oplog.delete.success", opParam);
                } else {
                    errorMsg = getText("common.message.delError")
                            + getText("cronExpression.error.sql");
                }
            }
            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            forward = ERROR;
        }
        return forward;
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
}
