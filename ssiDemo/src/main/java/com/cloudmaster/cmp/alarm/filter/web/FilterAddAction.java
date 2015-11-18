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

import java.util.Arrays;
import java.util.List;

import com.cloudmaster.cmp.alarm.filter.dao.AlarmFilterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FilterAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4746719597111641998L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(FilterAddAction.class);

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
     * 告警花名冊添加
     * @return String
     * @param
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String opParam[]={domain.getFilterName()};
        try {

            int j = ibatisDAO.getCount(IBATIS_NAMESPACE + ".existCount", domain);
            if (j > 0) {
                errorMsg = getText("common.message.addError") + getText("messgae.rule.all.exist");
                operationInfo = getText("oplog.add.error",opParam) + getText("messgae.rule.all.exist");
                forward = "input";
                return forward;
            }

            ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert", domain);

            msg = getText("common.message.addSuccess");
            logger.info(getText("function.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success",opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error",opParam) + getText("common.message.systemError");
            forward = "input";
        }
        return forward;
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
