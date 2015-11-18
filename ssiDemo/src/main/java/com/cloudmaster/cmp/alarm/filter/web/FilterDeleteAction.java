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
package com.cloudmaster.cmp.alarm.filter.web;

import com.cloudmaster.cmp.alarm.filter.dao.AlarmFilterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FilterDeleteAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5154409857316845494L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(FilterDeleteAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmFilterDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmFilter";

    /**
     * 错误消息
     */
    private String errorMsg;
    
    /**
     * 过滤规则标识
     */
    private String filterId;

    /**
     * 修改
     * @return String
     * @param
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String opParam[]={domain.getFilterId()};
        try {
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".delete", domain);

            msg = getText("common.message.delSuccess");
            logger.info(getText("function.title") + getText("log.delete.end"));
            operationInfo = getText("oplog.delete.success",opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error",opParam) + getText("common.message.systemError");
        }
        return forward;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public AlarmFilterDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmFilterDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    
}
