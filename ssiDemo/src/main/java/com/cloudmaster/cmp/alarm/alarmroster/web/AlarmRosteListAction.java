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

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.alarmaoster.dao.AlarmRosterDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 
 * 过滤规则管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRosteListAction extends PageAction implements IOperationLog, IAuthIdGetter {
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

    /**
     * ibatis 空间名
     */
    private static final String IBATIS_NAMESPACE = "alarmRoster";

    private static LogService logger = LogService.getLogger(AlarmRosteListAction.class);

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

    private String errorMsg;

    public String base() {
        return "base";
    }

    /**
     *告警花名冊浏览
     * @return String
     * @param
     */
    public String list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            // 增加排序操作
            alarmroster.setSortName(request.getParameter("sortname"));
            alarmroster.setSortOrder(request.getParameter("sortorder"));
            // 增加查询
            alarmroster.setQtype(request.getParameter("qtype"));
            String query = request.getParameter("query");
            if (null != query && !query.equals("")) {
                alarmroster.setQuery(SqlUtil.specialToNormal(query));
            } else {
                alarmroster.setQuery(query);
            }
            // 此操作必须在调用getpage()方法之前
            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));
            alarmrosterList = getPage(IBATIS_NAMESPACE + ".getAlarmRosterCount", IBATIS_NAMESPACE
                    + ".getalarmrosterSearch", alarmroster);
            if (null == alarmrosterList || alarmrosterList.size() == 0) {
                operationInfo = getText("oplog.list.error") + getText("common.message.nodata");

            } else {
                for (int i = 0; i < alarmrosterList.size(); i++) {
                    if (alarmrosterList.get(i).getType().equals("0")) {
                        alarmroster.setRosterID(alarmrosterList.get(i).getRosterID());
                        AlarmRosterDomain bean = (AlarmRosterDomain) ibatisDAO.getSingleRecord(
                                IBATIS_NAMESPACE + ".getType0", alarmroster);
                        if (bean == null) {
                            alarmrosterList.get(i).setEventName("");
                        } else {
                            alarmrosterList.get(i).setEventName(bean.getEventName());
                        }
                    } else if (alarmrosterList.get(i).getType().equals("1")) {
                        alarmroster.setRosterID(alarmrosterList.get(i).getRosterID());
                        AlarmRosterDomain bean = (AlarmRosterDomain) ibatisDAO.getSingleRecord(
                                IBATIS_NAMESPACE + ".getType1", alarmroster);
                        if (bean == null) {
                            alarmrosterList.get(i).setAlarmTitle("");
                        } else {
                            alarmrosterList.get(i).setAlarmTitle(
                                    bean.getAlarmTitle()+"("+bean.getAgentName()+")");
                        }

                    }
                }
                operationInfo = getText("oplog.list.success");
            }
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (AlarmRosterDomain bean : alarmrosterList) {
                fgjd.setRowId(bean.getRosterID()); // 设置行标识
                fgjd.addColdata(bean.getManufactureID());
                fgjd.addColdata(bean.getType()); // 设置此行 第一列内容
                if (bean.getType().equals("0")) {
                    fgjd.addColdata(bean.getEventName());
                } else if (bean.getType().equals("1")) {
                    fgjd.addColdata(bean.getAlarmTitle());
                }
                fgjd.addColdata(bean.getAlarmGrade());
                fgjd.addColdata(bean.getDescription());
                fgjd.addColdata(bean.getModifyTime());
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            logger.info("=========fgjd.toString()===============" + fgjd.toString());
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.list.end"));

        } catch (Exception e) {
            logger.error(getText("function.title") + getText("log.list.error"), e);
            operationInfo = getText("function.title") + getText("log.list.error");
        }
        return null;
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

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRosteListAction.logger = logger;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
