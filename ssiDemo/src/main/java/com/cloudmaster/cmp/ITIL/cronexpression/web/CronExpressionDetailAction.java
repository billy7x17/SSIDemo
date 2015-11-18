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

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
import com.cloudmaster.cmp.ITIL.cronexpression.dao.Jobs;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionDetailAction extends OperationLogAction implements IAuthIdGetter {

    /**
     *
     */
    private static final long serialVersionUID = -6086127088596217886L;

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

    /**
     * 详细
     * @return forward
     */
    public String detail() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam = domain.getTaskID();
        try {
            domain = (CronExpressionManagerDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".detail", domain);
            if (null == domain) {
                errorMsg = getText("common.message.detailError") + getText("common.message.nodata");
                forward = ERROR;
            } else {
                domain.setBeanID(getBeanIDFromTaskParam(domain.getTaskParam()));
                String jobName = getJobNameByBeanID(domain.getSsID(), domain.getBeanID());
                domain.setBeanID(jobName == null ? "" : jobName);
                if (domain.getSsID() == null || "".equals(domain.getSsID())) {
                    domain.setSsID(getText("cronExpression.ssID.default"));
                } else {
                    domain.setSsID(getText("quartz.subsystem.name." + domain.getSsID()));
                }
            }
            operationInfo = getText("oplog.detail.success", opParam);
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.detail.error"), e);
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
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(taskParam, Map.class);// 转成map
            if (map == null) {
                return "";
            }
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
}
