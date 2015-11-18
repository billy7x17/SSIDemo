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
package com.cloudmaster.cmp.alarm.filter.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cloudmaster.cmp.alarm.filter.dao.AlarmFilterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FilterSearchAction extends PageAction implements IAuthIdGetter, IOperationLog{

   

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8494252099575330615L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(FilterSearchAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";
    
    /**
     * bean对象
     */
    private AlarmFilterDomain domain;

    /**
     * 列表
     */
    private List<AlarmFilterDomain> filterList;
   
    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmFilter";
    
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
    
    /**
     * 列表页面
     * @return
     */
    public String search() {
        logger.info(getText("function.title") + getText("log.search.begin"));
        try {
           
            if(null != domain.getFilterName() && !domain.getFilterName().equals("")){
                domain.setFilterName(SqlUtil.specialToNormal(domain.getFilterName()));
            }
            if(null != domain.getRosterId() && !domain.getRosterId().equals("")){
                domain.setRosterId(SqlUtil.specialToNormal(domain.getRosterId()));
            }
            
            filterList = getPage(IBATIS_NAMESPACE + ".count", IBATIS_NAMESPACE + ".list", domain);
            if(null == filterList || filterList.isEmpty()){
                msg = getText("common.message.searchError");
            }
            logger.info(getText("function.title") + getText("log.search.end"));
            operationInfo = getText("oplog.search.success");
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.search.error"),e);
            operationInfo = getText("oplog.search.error") + getText("common.message.systemError");
        }
        return forward;
    }
    
    
    
    @Override
    public String getOpType() {
        return opType;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    public AlarmFilterDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmFilterDomain domain) {
        this.domain = domain;
    }

    public List<AlarmFilterDomain> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<AlarmFilterDomain> filterList) {
        this.filterList = filterList;
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
