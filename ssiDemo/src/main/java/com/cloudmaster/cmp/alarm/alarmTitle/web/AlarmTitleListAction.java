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

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.alarmTitle.dao.AlarmTitleDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警标题管理
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmTitleListAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

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

    /**
     * 列表页面
     * @return
     */
    @SuppressWarnings("unchecked")
    public String list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();

            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));

            //增加查询
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
            titleList = getPage(IBATIS_NAMESPACE + ".count", IBATIS_NAMESPACE + ".list", domain);

            logger.info(getText("function.title") + getText("log.list.end"));
            operationInfo = getText("oplog.list.success");

            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (AlarmTitleDomain bean : titleList) {
                fgjd.setRowId(bean.getTcId()); // 设置行标识
                fgjd.addColdata(bean.getTcId());
                fgjd.addColdata(bean.getTcTitle());
                fgjd.addColdata(bean.getTcType());
                fgjd.addColdata(bean.getAlarmIdentityID());
                fgjd.addColdata(bean.getCreateTime());
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
        return forward;
    }

    /**
     * 跳转方法
     */
    public String base() {
        return "base";
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

    public AlarmTitleDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmTitleDomain domain) {
        this.domain = domain;
    }

    public List < AlarmTitleDomain > getTitleList() {
        return titleList;
    }

    public void setTitleList(List < AlarmTitleDomain > titleList) {
        this.titleList = titleList;
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
