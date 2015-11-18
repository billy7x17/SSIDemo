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
package com.cloudmaster.cmp.alarm.view.web;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.systemparmeter.service.SystemParameterService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class BeforeMailNotifyAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3139437927841976875L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(BeforeMailNotifyAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 告警ID
     */
    private String alarmID;
    
    /**
     * 系统参数服务
     */
    private SystemParameterService sysParaService;

    public SystemParameterService getSysParaService() {
        return sysParaService;
    }

    public void setSysParaService(SystemParameterService sysParaService) {
        this.sysParaService = sysParaService;
    }

    public String beforeMailNotify() {
        logger.info(getText("function.title") + getText("log.beforemail.begin"));
        try {
            AlarmViewDomain paraDomain = new AlarmViewDomain();
            paraDomain.setAlarmID(alarmID);
            domain = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".detail",
                    paraDomain);
            
            String titleTemplate = sysParaService.getSystemParameterValue("alarmMailTitle");
            String contentTemplate = sysParaService.getSystemParameterValue("alarmMailContent");
            
            String content = domain.getAlarmContent();
            if(null != contentTemplate && !contentTemplate.equals("")){
                content = contentTemplate.replace("${content}", content);    
            }
            domain.setMailContent(content);
            
            String title = domain.getAlarmTitle();
            if(null != titleTemplate && !titleTemplate.equals("")){
                title = titleTemplate.replace("${content}", title);    
            }
            domain.setMailTitle(title);
            
            operationInfo = getText("oplog.beforemail.success");
            logger.info(getText("function.title") + getText("log.beforemail.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.beforemail.error")
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforemail.error"), e);
        }
        return SUCCESS;
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

}
