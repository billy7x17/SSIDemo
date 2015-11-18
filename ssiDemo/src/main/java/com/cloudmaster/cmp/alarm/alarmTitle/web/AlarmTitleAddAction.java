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
package com.cloudmaster.cmp.alarm.alarmTitle.web;

import com.cloudmaster.cmp.alarm.alarmTitle.dao.AlarmTitleDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警标题管理
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4746719597111641998L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmTitleAddAction.class);

    /**
     * bean对象
     */
    private AlarmTitleDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmTitle";

    /**
     * 告警标题添加
     * @return String
     * @param
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String[] opParam = {getText("field.label.tcTitle") + ": " + domain.getTcTitle()};
        try {
            ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert", domain);
            msg = getText("common.message.addSuccess");
            logger.info(getText("function.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam) + getText("common.message.systemError");
            return "input";
        }
        return SUCCESS;
    }

    public AlarmTitleDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmTitleDomain domain) {
        this.domain = domain;
    }

}
