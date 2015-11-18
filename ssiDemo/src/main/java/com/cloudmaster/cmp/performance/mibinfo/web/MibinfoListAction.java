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
package com.cloudmaster.cmp.performance.mibinfo.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取MIB信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibinfoListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     * 标识.
     */
    private static final long serialVersionUID = 1L;


    /**
     * MIB信息
     */
    private List<MibinfoDomain> mibList = new ArrayList<MibinfoDomain>();

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
    private static LogService logger = LogService.getLogger(MibinfoListAction.class);

    /**
     * 实体.
     */
    private MibinfoDomain domain = new MibinfoDomain();

    /**
     * 设备类型.
     */
    private List<DeviceDomain> deviceTypeList;

    /**
     * 父节点mib
     */
    private List<MibinfoDomain> parentMibInfoList;

    /*
     * 控制页面跳转
     */
    @SuppressWarnings("unchecked")
    public String base() {
        logger.info(getText("function.title") + getText("log.beforeSearch.begin"));
        try {
            MibinfoDomain mibInfo= new MibinfoDomain();
            mibInfo.setIsCollection("1");
            
            deviceTypeList = this.getIbatisDAO().getData("MibInfo.getDeviceTypeList", null);
            setParentMibInfoList(this.getIbatisDAO().getData("MibInfo.getMibList", mibInfo));
            if (deviceTypeList.isEmpty()) {
                msg=this.getText("mib.before.deviceType.action.error");
                logger.info(msg);
            }
            logger.info(getText("function.title") + getText("log.beforeSearch.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.beforeSearch.error"),e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 获取MIB信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public void mibinfoList() {
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
            MibinfoDomain mibInfo= new MibinfoDomain();
            mibInfo.setIsCollection("1");
            // 获取设备类型信息
            setDeviceTypeList(this.getIbatisDAO().getData("MibInfo.getDeviceTypeList", null));
            mibInfo.setSortName(domain.getSortName());
            mibInfo.setSortOrder(domain.getSortOrder());
            setParentMibInfoList(this.getIbatisDAO().getData("MibInfo.getMibBaseList", mibInfo));
            // 获取MIB信息
            mibList = getPage("MibInfo.getMibListCount", "MibInfo.getMibBaseList", domain);
             if (mibList.isEmpty()) {
                 msg=this.getText("mess.data.not.found");
                 logger.info(msg);
             }
            operationInfo = getText("oplog.list.success");
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (MibinfoDomain bean : mibList) {
                fgjd.setRowId(bean.getMibId()); // 设置行标识
                fgjd.addColdata(bean.getMibName());
                fgjd.addColdata(bean.getTypeName()); // 设置此行 第二列内容
                fgjd.addColdata(bean.getOid());
                fgjd.addColdata(bean.getIndexGroup());
                fgjd.addColdata(bean.getIndexUnit());
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
    }

    public List<MibinfoDomain> getMibList() {
        return mibList;
    }

    public void setMibList(List<MibinfoDomain> mibList) {
        this.mibList = mibList;
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
        return functionName;
    }

    public MibinfoDomain getDomain() {
        return domain;
    }

    public void setDomain(MibinfoDomain domain) {
        this.domain = domain;
    }

    /**
     * @return the deviceTypeList
     */
    public List<DeviceDomain> getDeviceTypeList() {
        return deviceTypeList;
    }

    /**
     * @param deviceTypeList the deviceTypeList to set
     */
    public void setDeviceTypeList(List<DeviceDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public List<MibinfoDomain> getParentMibInfoList() {
        return parentMibInfoList;
    }

    public void setParentMibInfoList(List<MibinfoDomain> parentMibInfoList) {
        this.parentMibInfoList = parentMibInfoList;
    }

}
