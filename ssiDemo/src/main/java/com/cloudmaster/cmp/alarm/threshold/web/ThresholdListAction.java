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
package com.cloudmaster.cmp.alarm.threshold.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取阈值信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    private static final long serialVersionUID = 1L;

    // 阈值信息
    private List<ThresholdDomain> thresholdList = new ArrayList<ThresholdDomain>();

    // 设备类型
    private List<ThresholdDomain> deviceTypeList = new ArrayList<ThresholdDomain>();

    // 告警等级
    private List<ThresholdDomain> levelList = new ArrayList<ThresholdDomain>();

    /**
     * 错误信息
     */
    private String errorMsg;

    private ThresholdDomain domain = new ThresholdDomain();

    // 跳转页面
    private String forward = "SUCCESS";

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
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdListAction.class);

    /**
     * 获取阈值信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public String thresholdList() {
        logger.info(getText("function.title") + getText("list.function"));
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
            // 获取阈值信息
            thresholdList = getPage("ThresholdInfo.getThresholdListCount",
                    "ThresholdInfo.getThresholdList", domain);
            // if (thresholdList == null || thresholdList.size() == 0) {
            // msg = this.getText("mess.data.not.found");
            // logger.info(msg);
            // }
            operationInfo = getText("oplog.list.success");
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (ThresholdDomain bean : thresholdList) {
                fgjd.setRowId(bean.getId()); // 设置行标识
                fgjd.addColdata(bean.getEventName());// 告警标题
                fgjd.addColdata(bean.getLevelName());// 告警等级
                fgjd.addColdata(bean.getAgentType());// 设备类型
                fgjd.addColdata(bean.getTypeId());// 设备类型
                fgjd.addColdata(bean.getPerConditionName()); // 告警符号
                fgjd.addColdata(bean.getInterval());// 告警区间
                fgjd.addColdata(bean.getCreateTime());// 创建时间
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
        } catch (Exception e) {
            errorMsg = this.getText("threshold.search.action.error");
            logger.error(errorMsg, e);
            operationInfo = getText("oplog.list.error");
        }
        return forward;
    }

    public String base() {
        return "base";
    }

    public List<ThresholdDomain> getThresholdList() {
        return thresholdList;
    }

    public void setThresholdList(List<ThresholdDomain> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpType() {
        return opType;
    }

    @Override
    public String getOperationFunction() {
        // TODO Auto-generated method stub
        return functionName;
    }

    public ThresholdDomain getDomain() {
        return domain;
    }

    public void setDomain(ThresholdDomain domain) {
        this.domain = domain;
    }

    public List<ThresholdDomain> getDeviceTypeList() {
        return deviceTypeList;
    }

    public void setDeviceTypeList(List<ThresholdDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public List<ThresholdDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<ThresholdDomain> levelList) {
        this.levelList = levelList;
    }

}
