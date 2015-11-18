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
package com.cloudmaster.cmp.system.systemparmeter.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParameterListAction extends PageAction implements IOperationLog, IAuthIdGetter {
    private static final long serialVersionUID = 5945354626226347310L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> systemParamList = new ArrayList<SystemParameterDomain>();

    /**
     * 系统参数实例
     */
    private SystemParameterDomain domain = new SystemParameterDomain();

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
    private static final String IBATIS_NAMESPACE = "systemparameter";

    private static LogService logger = LogService.getLogger(SystemParameterListAction.class);

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

    /*
     * 控制页面跳转
     */
    public String base() {
        return "base";
    }

    /**
     *系统参数浏览
     * @return String
     * @param
     */
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
            systemParamList = getPage(IBATIS_NAMESPACE + ".getCount", IBATIS_NAMESPACE
                    + ".getLists", domain);
            
            operationInfo = getText("oplog.list.success");
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (SystemParameterDomain bean : systemParamList) {
                fgjd.setRowId(bean.getOrganize()+bean.getParamKey()); // 设置行标识
                fgjd.addColdata(bean.getOrganize());
                fgjd.addColdata(bean.getParamKey());
                fgjd.addColdata(bean.getParamValue());
                fgjd.addColdata(bean.getDescription());
                fgjd.addColdata(bean.getUpdateTime());
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
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            forward = "ERROR";
            logger.error(getText("function.title") + getText("log.list.error"), e);
        }
        return forward;
    }

    public List<SystemParameterDomain> getSystemParamList() {
        return systemParamList;
    }

    public void setSystemParamList(List<SystemParameterDomain> systemParamList) {
        this.systemParamList = systemParamList;
    }

    public SystemParameterDomain getDomain() {
        return domain;
    }

    public void setDomain(SystemParameterDomain domain) {
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
