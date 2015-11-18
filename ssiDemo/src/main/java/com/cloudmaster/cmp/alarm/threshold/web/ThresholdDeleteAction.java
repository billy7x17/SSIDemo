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

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.web.MibInfoDeleteAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除阈值信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdDeleteAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 阈值 ID
     */
    private String thresholdId;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private ThresholdDomain thresholdDomain;

    private List<ThresholdDomain> thresholdList;

    private static LogService logger = LogService.getLogger(MibInfoDeleteAction.class);

    public String delete() {
        String opParam[]={thresholdId};
        logger.info(getText("function.title") + getText("delete.function"));
        try {
            // 如果关联了规则匹配，则不可删除
            if(hasRoster(thresholdDomain)){
                errorMsg = getText("message.threshold.hasRoster");
                return forward;
            }
            // 删除阈值
            ibatisDAO.deleteData("ThresholdInfo.deleteThresholdById", thresholdDomain);
            msg = this.getText("common.message.delSuccess");
            logger.info(msg);
            operationInfo = getText("oplog.delete.success",opParam);
        } catch (SQLException e) {
            errorMsg = this.getText("common.message.delError");
            logger.info(errorMsg);
            operationInfo = getText("oplog.delete.error",opParam) + errorMsg;
        }
        return forward;
    }

    /**
     * 校验阀值是否关联规则匹配
     * @param thresholdDomain
     * @throws SQLException 
     */
    public boolean hasRoster(ThresholdDomain thresholdDomain) throws SQLException {
        int i = ibatisDAO.getCount("ThresholdInfo.hasRosterCheck", thresholdDomain);
        return i > 0;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setThresholdId(String thresholdId) {
        this.thresholdId = thresholdId;
    }

    public String getThresholdId() {
        return thresholdId;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setThresholdList(List<ThresholdDomain> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public List<ThresholdDomain> getThresholdList() {
        return thresholdList;
    }

}
