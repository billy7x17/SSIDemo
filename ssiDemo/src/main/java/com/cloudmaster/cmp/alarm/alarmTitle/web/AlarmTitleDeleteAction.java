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

import java.sql.SQLException;

import com.cloudmaster.cmp.alarm.alarmTitle.dao.AlarmTitleDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警标题管理
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleDeleteAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5154409857316845494L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmTitleDeleteAction.class);


    /**
     * bean对象
     */
    private AlarmTitleDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmTitle";


    /**
     * 标题ID
     */
    private String tcId;

    /**
     * 修改
     * @return String
     * @param
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        String[] opParam = {getText("field.label.tcTitle") + ": " + domain.getTcTitle()};
        try {
            //如果被引用，则不可删除
            if (checkTcId(domain)) {
                errorMsg = getText("message.alarmTitle.checkTcId");
                return SUCCESS;
            }

            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".delete", domain);
            msg = getText("common.message.delSuccess");
            logger.info(getText("function.title") + getText("log.delete.end"));
            operationInfo = getText("oplog.delete.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam) + getText("common.message.systemError");
        }
        return SUCCESS;
    }

    /**
     * 校验tcId是否被其他表引用
     * @return
     */
    public boolean checkTcId(AlarmTitleDomain domain) throws SQLException {
        int i = ibatisDAO.getCount(IBATIS_NAMESPACE + ".checkDelete", domain);
        return i > 0;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public AlarmTitleDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmTitleDomain domain) {
        this.domain = domain;
    }


}
