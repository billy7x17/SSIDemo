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
package com.cloudmaster.cmp.system.alarmrules.web;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.system.alarmrules.dao.AlarmRulesDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRulesListAction extends PageAction implements IOperationLog, IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmRulesList = new ArrayList<AlarmRulesDomain>();

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmRulesTypeList = new ArrayList<AlarmRulesDomain>();

    /**
     * 告警配置Bean
     */
    private AlarmRulesDomain domain = new AlarmRulesDomain();

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> LevelList = new ArrayList<AlarmRulesDomain>();

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
    private static String IBATIS_NAMESPACE = "alarmrules";

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

    private static LogService logger = LogService.getLogger(AlarmRulesListAction.class);

    public String list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));
            // 增加查询
            domain.setQtype(request.getParameter("qtype"));
            String query = request.getParameter("query");
            if (null != query && !query.equals("")) {
                domain.setQuery(SqlUtil.specialToNormal(query));
            } else {
                domain.setQuery(query);
            }
            // 此操作必须在调用getpage()方法之前
            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));
            alarmRulesList = getPage(IBATIS_NAMESPACE + ".getCount",
                    IBATIS_NAMESPACE + ".getLists", domain);
            if (alarmRulesList.size() > 0) {
                operationInfo = getText("oplog.list.success");
            } else {
                operationInfo = getText("oplog.list.error") + getText("common.message.nodata");
                forward = "ERROR";
            }
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (AlarmRulesDomain bean : alarmRulesList) {
                fgjd.setRowId(String.valueOf(bean.getRowID())); // 设置行标识
                fgjd.addColdata(bean.getAgentName());
                fgjd.addColdata(bean.getAlarmTitleContent()); // 设置此行 第二列内容
                fgjd.addColdata(bean.getAlarmOID());
                fgjd.addColdata(bean.getAlarmLevelName());
                fgjd.addColdata(bean.getAlarmIdentityID());
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
        } catch (Exception e) {
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.list.error"), e);
        }
        return forward;

    }

    public String base() {
        try {
            alarmRulesTypeList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getTypeID", domain);
            LevelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getLevelName", domain);
        } catch (SQLException e) {
            logger.error(getText("common.message.searchError"), e);
        }
        return "base";
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

    public AlarmRulesDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRulesDomain domain) {
        this.domain = domain;
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
        AlarmRulesListAction.logger = logger;
    }

    public List<AlarmRulesDomain> getAlarmRulesList() {
        return alarmRulesList;
    }

    public void setAlarmRulesList(List<AlarmRulesDomain> alarmRulesList) {
        this.alarmRulesList = alarmRulesList;
    }

    public List<AlarmRulesDomain> getAlarmRulesTypeList() {
        return alarmRulesTypeList;
    }

    public void setAlarmRulesTypeList(List<AlarmRulesDomain> alarmRulesTypeList) {
        this.alarmRulesTypeList = alarmRulesTypeList;
    }

    public List<AlarmRulesDomain> getLevelList() {
        return LevelList;
    }

    public void setLevelList(List<AlarmRulesDomain> levelList) {
        LevelList = levelList;
    }

}
