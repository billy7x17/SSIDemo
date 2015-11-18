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
package com.cloudmaster.cmp.alarm.history.web;

import com.cloudmaster.cmp.alarm.history.dao.AlarmHistoryDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 历史告警
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmHistoryDetailAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8533093569731122556L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmHistoryDetailAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmHistoryDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmHistory";

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 过滤规则标识
     */
    private String filterId;

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;

    /**
     * 修改
     * @return String
     * @param
     */
    public String detail() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() };
        try {

            domain.setNmsDB(nmsDBName); // 传入数据库名

            domain = (AlarmHistoryDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".detail",
                    domain);
            /* 设备类型国际化 */
            domain.setDeviceTypeName(getText("device.group." + domain.getDeviceTypeName()));
            if (null == domain) {
                errorMsg = getText("common.message.detailError") + getText("message.data.null");
                operationInfo = getText("oplog.detail.error", opParam)
                        + getText("message.data.null");
            } else {
                operationInfo = getText("oplog.detail.success", opParam);
            }

            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.detail.error"), e);
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
        }
        return forward;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public AlarmHistoryDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmHistoryDomain domain) {
        this.domain = domain;
    }

    /**
     * @return the nmsDBName
     */
    public String getNmsDBName() {
        return nmsDBName;
    }

    /**
     * @param nmsDBName the nmsDBName to set
     */
    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

}
