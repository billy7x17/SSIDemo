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
public class AlarmRosterBeforeAddAction extends BaseAction implements IOperationLog, IAuthIdGetter {
    private static final long serialVersionUID = 1L;

    /**
     * 阀值列表
     */
    private List<AlarmRosterDomain> idList = new ArrayList<AlarmRosterDomain>();
    
    /**
     * 
     */
    private List<AlarmRosterDomain> rowIDList = new ArrayList<AlarmRosterDomain>();

    /**
     * 告警花名册Bean
     */
    private AlarmRosterDomain alarmroster;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmRoster";

    private static LogService logger = LogService.getLogger(AlarmRosterBeforeAddAction.class);

    /**
     * 告警等级列表
     */
    private List<AlarmRosterDomain> levelList;
    
    
    /**
     * 告警花名冊添加前准备
     * @return
     */
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        // 查询设备类型
        try {
            
          //告警级别列表
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevelList", null);
            
            // 查询阀值名称
            idList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getID", alarmroster);
            rowIDList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getrRowID", alarmroster);
            for (int i = 0; i < rowIDList.size(); i++) {
                rowIDList.get(i).setAlarmTitle(
                        rowIDList.get(i).getAlarmTitle() + "(" + rowIDList.get(i).getAgentName()
                                + ")");
            }
            logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(getText("function.title") + getText("log.beforeAdd.error"), e);
        }
        return forward;
    }

    //

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

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
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
