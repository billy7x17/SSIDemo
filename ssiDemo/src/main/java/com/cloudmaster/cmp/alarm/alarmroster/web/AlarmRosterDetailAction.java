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
package com.cloudmaster.cmp.alarm.alarmroster.web;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.alarm.alarmaoster.dao.AlarmRosterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosterDetailAction extends OperationLogAction implements IAuthIdGetter {
    private static final long serialVersionUID = 5945354626226347310L;

    /**
     * 结果列表
     */
    private List<AlarmRosterDomain> alarmrosterList;

    /**
     * 告警花名册Bean
     */
    private AlarmRosterDomain alarmroster = new AlarmRosterDomain();

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    private String errorMsg;

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmRoster";

    private static LogService logger = LogService.getLogger(AlarmRosterDetailAction.class);

    /**
     * 物理机型号详细信息展示
     * @return
     */
    public String detail() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam[] = { alarmroster.getRosterID() };
        try {
            if(alarmroster.getType().equals("0")){
                alarmroster = (AlarmRosterDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getType0", alarmroster);
                if (alarmroster == null) {
                    errorMsg = getText("common.message.detailError") + getText("该记录不存在！");
                    operationInfo = getText("oplog.detail.error", opParam) + getText("该记录不存在！");
                    forward = "ERROR";
                }
                operationInfo = getText("oplog.detail.success", opParam);
            }else if(alarmroster.getType().equals("1")){
                alarmroster = (AlarmRosterDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getType1", alarmroster);
                alarmroster.setAlarmTitle(alarmroster.getAlarmTitle()+"("+alarmroster.getAgentName()+")");
                if (alarmroster == null) {
                    errorMsg = getText("common.message.detailError") + getText("该记录不存在！");
                    operationInfo = getText("oplog.detail.error", opParam) + getText("该记录不存在！");
                    forward = "ERROR";
                }
                operationInfo = getText("oplog.detail.success", opParam);
            }
           
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.detailError") + getText("message.sql.error");
            operationInfo = getText("oplog.detail.error", opParam) + getText("message.sql.error");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.detail.error"), e);
        }
        return forward;
    }


    public List<AlarmRosterDomain> getAlarmrosterList() {
        return alarmrosterList;
    }

    public void setAlarmrosterList(List<AlarmRosterDomain> alarmrosterList) {
        this.alarmrosterList = alarmrosterList;
    }

    public AlarmRosterDomain getAlarmroster() {
        return alarmroster;
    }

    public void setAlarmroster(AlarmRosterDomain alarmroster) {
        this.alarmroster = alarmroster;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRosterDetailAction.logger = logger;
    }

}
