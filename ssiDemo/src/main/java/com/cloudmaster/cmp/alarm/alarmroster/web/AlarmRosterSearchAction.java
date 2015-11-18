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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.alarm.alarmaoster.dao.AlarmRosterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosterSearchAction extends PageAction implements IOperationLog,IAuthIdGetter {
    private static final long serialVersionUID = 5945354626226347310L;

    /**
     * 结果列表
     */
    private List<AlarmRosterDomain> alarmrosterList = new ArrayList<AlarmRosterDomain>();

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
    private static final String IBATIS_NAMESPACE = "alarmRoster";
    
    private static LogService logger = LogService.getLogger(AlarmRosterSearchAction.class);
    
    
    /**
     * 为操作日志返回的功能名
     */
    protected String functionName = "";

    /**
     * 为操作日志返回的功能描述
     */
    protected String operationInfo = "";

    /**
     * 为操作日志返回的操作类型；
     */
    protected String opType = "";
    /**
     * 物理机型号高级查询
     * @return
     */
    public String search() {
        logger.info(getText("function.title") + getText("log.search.begin"));
        try {           
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
            if(alarmroster.getStartTime()!=null && !alarmroster.getStartTime().equals("")){
                String startTime = df1.format(df.parse(alarmroster.getStartTime()));
                alarmroster.setStartTime(startTime);
            }
           if(alarmroster.getEndTime()!=null&&!alarmroster.getEndTime().equals("")){
            String endTime=df1.format(df.parse(alarmroster.getEndTime()));    
            alarmroster.setEndTime(endTime);
           }         
           alarmrosterList=getPage(IBATIS_NAMESPACE + ".getAlarmRosterCount", IBATIS_NAMESPACE
                   + ".getalarmrosterSearch", alarmroster);
           
            if(alarmrosterList.size()>0){
                operationInfo = getText("oplog.search.success");
            }else{
                msg = getText("common.message.nodata");
                operationInfo = getText("oplog.search.error") + getText("common.message.nodata");
                forward="ERROR";
            }
            logger.info(getText("function.title") + getText("log.search.end"));
        } catch (Exception e) {
            errorMsg=getText("common.message.searchError");
            operationInfo = getText("oplog.search.error") + getText("common.message.searchError");
            forward="ERROR";
            logger.error(getText("function.title") + getText("log.search.error"),e);
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

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRosterSearchAction.logger = logger;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }
    
    public String getOpType() {
        return opType;
    }

    public String getOperationFunction() {
        return functionName;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

}
