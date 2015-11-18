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
package com.cloudmaster.cmp.alarm.threshold.web;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 阈值详细
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdDetailAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * 阈值 ID
     */
    private String thresholdId;

    private ThresholdDomain thresholdDomain;

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(ThresholdDetailAction.class);

    public String detail() {
        String opParam[]={thresholdId};
        logger.info(getText("function.title") + getText("detail.function"));

        try {

            // 获取阈值信息
            thresholdDomain = (ThresholdDomain) this.ibatisDAO.getSingleRecord(
                    "ThresholdInfo.getThresholdById", thresholdDomain.getId());
            operationInfo = getText("oplog.detail.success",opParam);
        } catch (Exception e) {
            errorMsg = this.getText("common.message.detailError");
            logger.error(errorMsg, e);
            operationInfo = getText("oplog.detail.error",opParam) + errorMsg;
        }
        return forward;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setThresholdId(String thresholdId) {
        this.thresholdId = thresholdId;
    }

    public String getThresholdId() {
        return thresholdId;
    }

}
