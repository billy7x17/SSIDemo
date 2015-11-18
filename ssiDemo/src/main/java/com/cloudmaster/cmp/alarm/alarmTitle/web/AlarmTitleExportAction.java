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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.alarm.alarmTitle.dao.AlarmTitleDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.view.dao.ExportExcel;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

public class AlarmTitleExportAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmTitleExportAction.class);

    /**
     * bean对象
     */
    private AlarmTitleDomain domain = new AlarmTitleDomain();

    /**
     * 列表
     */
    private List < AlarmTitleDomain > titleList;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmTitle";


    /**
     * 日志，功能模块名称
     */
    private String functionName;

    /**
     * 日志，操作信息
     */
    private String operationInfo;

    /**
     * 日志，操作类型
     */
    private String opType;
    

    private InputStream excelStream;
    private String fileFileName;
    public String export(){
        fileFileName = "AlarmTitle.xls";
        return SUCCESS;
    }
    
    /**
     *导出告警标题excel   
     */   
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public InputStream getDownloadFile(){
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            List<AlarmTitleDomain> sheetinfos = new ArrayList<AlarmTitleDomain>();
            sheetinfos = ibatisDAO.getData(IBATIS_NAMESPACE + ".exportAll", domain);
            if (null == sheetinfos || sheetinfos.size()<=0) {
                AlarmTitleDomain temp = new AlarmTitleDomain();
                sheetinfos.add(temp);
            }
            excelStream = new ExportExcel().exportExcel(6000, "告警标题", sheetinfos, new String[]{"sortName","sortOrder"});
            
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {        
            operationInfo = getText("function.title") + getText("log.list.error");
            logger.info(getText("function.title") + getText("log.list.error"), e);
        }
        
        return excelStream;
    }
    

    public AlarmTitleDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmTitleDomain domain) {
        this.domain = domain;
    }

    public List<AlarmTitleDomain> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<AlarmTitleDomain> titleList) {
        this.titleList = titleList;
    }


    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmTitleExportAction.logger = logger;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    @Override
    public String getOperationFunction() {
        return null;
    }
}
