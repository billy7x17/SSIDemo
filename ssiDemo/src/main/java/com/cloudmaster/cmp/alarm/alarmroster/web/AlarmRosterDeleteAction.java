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
public class AlarmRosterDeleteAction extends OperationLogAction implements IAuthIdGetter {
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
     * 信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmRoster";

    private static LogService logger = LogService.getLogger(AlarmRosterDeleteAction.class);

    /**
     * 告警花名删除
     * @return String
     * @param
     */
    public String delete() {
        logger.info(getText("function.title") + getText("log.delete.begin"));
        // HttpServletRequest request = ServletActionContext.getRequest();
        // String rosterID = request.getParameter("rosterID");
        // alarmroster.setRosterID(rosterID);
        String opParam[] = { alarmroster.getRosterID() };
        try {
            // 删除前查询筛选器中是否占用rostID
            int j = ibatisDAO.getCount(IBATIS_NAMESPACE + ".getRostID", alarmroster);
            if (j > 0) {
                errorMsg = getText("common.message.delError") + getText("message.rostID.exist");
                operationInfo = getText("oplog.delete.error", opParam) + errorMsg;
                forward = "ERROR";
            } else {
                int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".deleteAlarmRoster", alarmroster);
                if (i > 0) {
                    msg = getText("common.message.delSuccess");
                    operationInfo = getText("oplog.delete.success", opParam);
                } else {
                    errorMsg = getText("common.message.delError") + getText("message.sql.error");
                    operationInfo = getText("oplog.delete.error", opParam)
                            + getText("message.sql.error");
                    forward = "ERROR";
                }
            }

            logger.info(getText("function.title") + getText("log.delete.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.delError") + getText("message.sql.error");
            operationInfo = getText("oplog.delete.error", opParam) + getText("message.sql.error");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.delete.error"), e);
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
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
        AlarmRosterDeleteAction.logger = logger;
    }
}
