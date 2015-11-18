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
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警标题管理
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleBeforeEditAction extends BaseAction implements IAuthIdGetter {
   
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4171931504123284858L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmTitleBeforeEditAction.class);

    /**
     * bean对象
     */
    private AlarmTitleDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmTitle";


    /**
     * 修改前准备
     * @return
     */
    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            domain = (AlarmTitleDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".getInfo",
                    domain);
         // 业务实例
            if (null == domain) {
                errorMsg = getText("common.message.editError") + getText("message.data.null");
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
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
