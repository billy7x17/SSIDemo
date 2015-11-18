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
package com.cloudmaster.cmp.alarm.regulation.web;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改前准备
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationBeforeEditAction extends BaseAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4171931504123284858L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationBeforeEditAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmRegulationDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmRegulation";

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 过滤规则标识
     */
    private String filterId;

    /**
     * 修改前准备
     * @return
     */
    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            domain = (AlarmRegulationDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getInfo", domain);

            if (null == domain) {
                errorMsg = getText("common.message.editError") + getText("message.data.null");
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
        }
        return forward;
    }

    public AlarmRegulationDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRegulationDomain domain) {
        this.domain = domain;
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
}
