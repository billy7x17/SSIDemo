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
package com.cloudmaster.cmp.ITIL.cronexpression.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.CronExpressionManagerDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class CronExpressionListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     *
     */
    private static final long serialVersionUID = -4416221784739349711L;

    private static LogService logger = LogService.getLogger(CronExpressionListAction.class);

    /**
     * 为操作日志返回的功能名
     */
    protected String functionName = "";

    /**
     * 为操作日志返回的操作类型；
     */
    protected String opType = "";

    /**
     * 为操作日志返回的功能描述
     */
    protected String operationInfo = "";

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "cronExpressionManager";

    /**
     * 数据Bean
     */
    private CronExpressionManagerDomain domain = new CronExpressionManagerDomain();

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 周期任务列表
     */
    private List<CronExpressionManagerDomain> list = new ArrayList<CronExpressionManagerDomain>();

    private String errorMsg;

    private String actionName;

    /**
     * 读取任务配置文件Jobs.xml
     */
    public CronExpressionListAction() {
        if (JobsUtil.jobListMap == null) {
            JobsUtil.init();
        }
    }

    /**
     * 列表页面跳转方法
     * @return
     */
    public String tolist() {
        return forward;
    }

    @SuppressWarnings("unchecked")
    public String list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletRequest jsprequest = ServletActionContext.getRequest();
            // 增加排序及查询操作
            domain.setSortName(jsprequest.getParameter("sortname"));
            domain.setSortOrder(jsprequest.getParameter("sortorder"));
            domain.setQtype(jsprequest.getParameter("qtype"));
            domain.setQuery(jsprequest.getParameter("query"));
            String rp = jsprequest.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            if (null != domain.getTaskName() && !domain.getTaskName().equals("")) {
                domain.setTaskName(SqlUtil.specialToNormal(domain.getTaskName()));
            }
            list = getPageUseLimit(IBATIS_NAMESPACE + ".getCount", IBATIS_NAMESPACE + ".list",
                    domain);
            if (null == list || 0 >= list.size()) {
                operationInfo = getText("oplog.list.error") + getText("common.message.nodata");
                forward = ERROR;
            }
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception ex) {
            operationInfo = getText("oplog.list.error") + getText("cronExpression.error.sql");
            logger.info(getText("function.title") + getText("log.list.error"), ex);
            forward = ERROR;
        }
        try {
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (CronExpressionManagerDomain ahd : list) {
                fgjd.setRowId(ahd.getTaskID()); // 设置行标识
                fgjd.addColdata(ahd.getTaskID());
                fgjd.addColdata(ahd.getTaskName());
                fgjd.addColdata(ahd.getAddTime());
                fgjd.addColdata(ahd.getCronExpression());
                fgjd.addColdata("");// 执行时间描述
                fgjd.addColdata(ahd.getTaskStatus());
            }
            fgjd.commitData(); // 提交数据
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info("组装flexigrid数据异常！");
        }
        // return forward;
        return null;
    }

    @Override
    public String getOpType() {
        return null;
    }

    @Override
    public String getOperationFunction() {
        return null;
    }

    @Override
    public String getOperationInfo() {
        return null;
    }

    public CronExpressionManagerDomain getDomain() {
        return domain;
    }

    public void setDomain(CronExpressionManagerDomain domain) {
        this.domain = domain;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<CronExpressionManagerDomain> list) {
        this.list = list;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

}
