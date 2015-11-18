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
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosterBeforeEditAction extends BaseAction implements IOperationLog, IAuthIdGetter {
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
    private AlarmRosterDomain alarmroster = new AlarmRosterDomain();

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmRoster";

    private String errorMsg;
    
    /**
     * 告警等级列表
     */
    private List<AlarmRosterDomain> levelList;

    /**
     * 告警花名冊修改前准备
     * @return
     */
    private static LogService logger = LogService.getLogger(AlarmRosterBeforeEditAction.class);

    public String beforeEdit() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            //告警级别列表
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevelList", null);
            
            if(alarmroster.getType().equals("0")){
                // 查询阀值名称
                idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
              //规则名称
                rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
                for (int i = 0; i < rowIDList.size(); i++) {
                    rowIDList.get(i).setAlarmTitle(
                            rowIDList.get(i).getAlarmTitle() + "(" + rowIDList.get(i).getAgentName()
                                    + ")");
                }
                alarmroster = (AlarmRosterDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getType0", alarmroster);
                
            }
            else if(alarmroster.getType().equals("1")){
                // 查询阀值名称
                idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
              //规则名称
                rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
                for (int i = 0; i < rowIDList.size(); i++) {
                    rowIDList.get(i).setAlarmTitle(
                            rowIDList.get(i).getAlarmTitle() + "(" + rowIDList.get(i).getAgentName()
                                    + ")");
                }
                alarmroster = (AlarmRosterDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getType1", alarmroster);
                alarmroster.setAlarmTitle(alarmroster.getAlarmTitle()+"("+alarmroster.getAgentName()+")");
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("message.sql.error");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.beforeEdit.error"), e);
        }
        return forward;

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
        AlarmRosterBeforeEditAction.logger = logger;
    }

    @Override
    public String getOpType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationFunction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOperationInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public List<AlarmRosterDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<AlarmRosterDomain> levelList) {
        this.levelList = levelList;
    }

}
