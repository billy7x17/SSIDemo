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
import java.util.ArrayList;
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
public class AlarmRosterEditAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 阀值列表
     */
    private List<AlarmRosterDomain> idList = new ArrayList<AlarmRosterDomain>();

    /**
     * 规则列表
     */
    private List<AlarmRosterDomain> rowIDList = new ArrayList<AlarmRosterDomain>();

    /**
     * 告警花名册Bean
     */
    private AlarmRosterDomain alarmroster;

    /**
     * 错误提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static final String IBATIS_NAMESPACE = "alarmRoster";

    /**
     * 告警等级列表
     */
    private List<AlarmRosterDomain> levelList;

    private static LogService logger = LogService.getLogger(AlarmRosterEditAction.class);

    /**
     * 修改
     * @return String
     * @param
     */
    public String edit() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { alarmroster.getRosterID() };
        try {
            // 一条阀值或告警只能配置一条规则，判断是否已经配置规则匹配
            if (checkAlarmRoster(alarmroster)) {
                errorMsg = getText("alarmRoster.check." + alarmroster.getType());
                beforeInput();
                return "INPUT";
            }
            if (alarmroster.getType().equals("0")) {
                // 查询阀值名称
                // idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
                // rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
                alarmroster.setId(alarmroster.getThresholdID());
                int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".updateAlarmRoster", alarmroster);
                if (i >= 0) {
                    msg = getText("common.message.editSuccess");
                    operationInfo = getText("oplog.edit.success", opParam);
                } else {
                    errorMsg = getText("common.message.editError") + getText("message.edit.exist");
                    operationInfo = getText("oplog.edit.error", opParam)
                            + getText("message.edit.exist");
                    beforeInput();
                    forward = "INPUT";
                    return forward;
                }
            } else if (alarmroster.getType().equals("1")) {
                // 规则名称
                // idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
                // rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
                alarmroster.setId(alarmroster.getRowID());
                int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".updateAlarmRoster", alarmroster);
                if (i >= 0) {
                    msg = getText("common.message.editSuccess");
                    operationInfo = getText("oplog.edit.success", opParam);
                } else {
                    errorMsg = getText("common.message.editError") + getText("message.edit.exist");
                    operationInfo = getText("oplog.edit.error", opParam)
                            + getText("message.edit.exist");
                    beforeInput();
                    forward = "INPUT";
                    return forward;
                }
            }
            logger.info(getText("function.title") + getText("log.edit.end"));

        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("message.sql.error");
            operationInfo = getText("oplog.edit.error", opParam) + getText("message.sql.error");
            forward = "INPUT";
            logger.info(getText("function.title") + getText("log.edit.error"), e);
        }
        return forward;
    }

    /**
     * 校验所选阀值或告警是否已经添加过规则
     * @param alarmroster
     * @return boolean true-已添加，false-未添加
     * @throws SQLException
     */
    public boolean checkAlarmRoster(AlarmRosterDomain alarmroster) throws SQLException {
        if ("0".equals(alarmroster.getType())) {
            alarmroster.setId(alarmroster.getThresholdID());
        } else if ("1".equals(alarmroster.getType())) {
            alarmroster.setId(alarmroster.getRowID());
        }
        int i = ibatisDAO.getCount("alarmRoster.checkAlarmRoster", alarmroster);
        return i > 0;
    }

    /**
     * 异常流跳转回添加页面所需参数
     * @return
     */
    public void beforeInput() {
        try {
            // 告警级别列表
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevelList", null);
            // 查询阀值名称
            idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
            rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
            for (int i = 0; i < rowIDList.size(); i++) {
                rowIDList.get(i).setAlarmTitle(
                        rowIDList.get(i).getAlarmTitle() + "(" + rowIDList.get(i).getAgentName()
                                + ")");
            }
        } catch (SQLException e) {
            logger.error("获取规则匹配添加页信息异常", e);
        }
    }

    public List<AlarmRosterDomain> getIdList() {
        return idList;
    }

    public void setIdList(List<AlarmRosterDomain> idList) {
        this.idList = idList;
    }

    public List<AlarmRosterDomain> getRowIDList() {
        return rowIDList;
    }

    public void setRowIDList(List<AlarmRosterDomain> rowIDList) {
        this.rowIDList = rowIDList;
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

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRosterEditAction.logger = logger;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<AlarmRosterDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<AlarmRosterDomain> levelList) {
        this.levelList = levelList;
    }

}