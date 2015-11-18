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
package com.cloudmaster.cmp.web.operationlog;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.resource.view.dao.ExportExcel;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 浏览操作日志的ACTION
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LogBrowseAction extends PageAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(LogBrowseAction.class);

    private List<OperationLogInfo> operationLogInfo;

    OperationLogInfo domain;

    private String forward = "SUCCESS";

    /**
     * 数据导出，输出流变量
     */
    private InputStream excelStream;

    /**
     * 数据导出，下载文件名
     */
    private String excelFileName;

    /**
     * 数据导出，每个sheet页的记录数量
     */
    private int excelSheetCount = 20000;

    /**
     * 错误消息
     */
    private String errorMsg;

    public String tolist() {
        return forward;
    }

    @SuppressWarnings("unchecked")
    public String execute() {
        try {
            HttpServletRequest jsprequest = ServletActionContext.getRequest();
            if (null == domain) {
                domain = new OperationLogInfo();
            }
            // 增加排序操作
            domain.setSortName(jsprequest.getParameter("sortname"));
            domain.setSortOrder(jsprequest.getParameter("sortorder"));
            // domain.setQtype(jsprequest.getParameter("qtype"));
            // domain.setQuery(jsprequest.getParameter("query"));
            String rp = jsprequest.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            // 保存sql条件，导出数据时使用
            saveWhereCondition(domain);
            operationLogInfo = getPageUseLimit("LogInfo.getAllLogInfoCount",
                    "LogInfo.getAllLogInfo", domain);
        } catch (Exception e) {
            logger.error(OperationStatusCode.LOG_BROWSE_FAILED,
                    getText("LogBrowseAction.browse.fail") + e.getMessage(), e);
            this.addActionError(getText("LogBrowseAction.browse.norecorder"));
            forward = "ERROR";
        }
        try {
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (OperationLogInfo ahd : operationLogInfo) {
                fgjd.setRowId(ahd.getLogId() + ""); // 设置行标识
                // fgjd.addColdata(ahd.getLogId() + "");// 设置此行 第一列内容
                fgjd.addColdata(ahd.getUserId()); // 设置此行 第二列内容
                fgjd.addColdata(ahd.getUserName()); // ……
                fgjd.addColdata(ahd.getIp());
                fgjd.addColdata(ahd.getDateTime());
                fgjd.addColdata(ahd.getAction());
                fgjd.addColdata(ahd.getOpType());
                fgjd.addColdata(ahd.getOperationInfo());
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
        return null;
    }

    /**
     * 数据导出
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String export() throws Exception {

        logger.info(getText("operationLog.manager") + "导出数据开始");
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            HashMap<String, String> map = (HashMap<String, String>) session
                    .getAttribute("OperationLogWhereCondition");
            List<OperationLogInfo> list = ibatisDAO.getData("LogInfo.exportList", map);
            if (null == list || list.isEmpty()) {
                list = new ArrayList<OperationLogInfo>();
                OperationLogInfo bean = new OperationLogInfo();
                list.add(bean);
            } else {
                String opType = "";
                for (OperationLogInfo temp : list) {
                    opType = temp.getOpType();
                    if ("Add".equals(opType)) {
                        temp.setOpType("添加");
                    } else if ("Update".equals(opType)) {
                        temp.setOpType("编辑");
                    } else if ("Del".equals(opType)) {
                        temp.setOpType("删除");
                    } else if ("View".equals(opType)) {
                        temp.setOpType("查看");
                    } else if ("Login".equals(opType)) {
                        temp.setOpType("登录");
                    } else if ("Logout".equals(opType)) {
                        temp.setOpType("登出");
                    } else if ("Other".equals(opType)) {
                        temp.setOpType("其他");
                    } else if ("AddPrivilege".equals(opType)) {
                        temp.setOpType("添加");
                    } else if ("DelPrivilege".equals(opType)) {
                        temp.setOpType("删除");
                    } else if ("UpdatePrivilege".equals(opType)) {
                        temp.setOpType("编辑");
                    }
                }
            }
            String[] notExport = new String[] {};

            excelStream = new ExportExcel<OperationLogInfo>().exportExcel(excelSheetCount,
                    "operationLogData", list, notExport);
            excelFileName = "operationLogData.xls";
            logger.info(getText("operationLog.manager") + "导出数据结束");
            return "returnStream";
        } catch (Exception e) {
            logger.info(getText("function.title") + " 导出数据出现异常", e);
            errorMsg = "导出数据出现异常";
            return "ERROR";
        }
    }

    /**
     * 保存查询条件，数据导出时使用
     * @param domain
     */
    private void saveWhereCondition(OperationLogInfo domain) {
        Map<String, String> map = new HashMap<String, String>();
        if (null != domain.getQtype() && !domain.getQtype().equals("")) {
            map.put("qtype", domain.getQtype());
        }
        if (null != domain.getUserId() && !domain.getUserId().equals("")) {
            map.put("userId", domain.getUserId());
        }
        if (null != domain.getUserName() && !domain.getUserName().equals("")) {
            map.put("userName", domain.getUserName());
        }
        if (null != domain.getIp() && !domain.getIp().equals("")) {
            map.put("ip", domain.getIp());
        }
        if (null != domain.getStartTime() && !domain.getStartTime().equals("")) {
            map.put("startTime", domain.getStartTime());
        }
        if (null != domain.getEndTime() && !domain.getEndTime().equals("")) {
            map.put("endTime", domain.getEndTime());
        }
        if (null != domain.getSortName() && !domain.getSortName().equals("")) {
            map.put("sortName", domain.getSortName());
        }
        if (null != domain.getSortOrder() && !domain.getSortOrder().equals("")) {
            map.put("sortOrder", domain.getSortOrder());
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("OperationLogWhereCondition", map);

    }

    public List<OperationLogInfo> getOperationLogInfo() {
        return operationLogInfo;
    }

    public OperationLogInfo getDomain() {
        return domain;
    }

    public void setDomain(OperationLogInfo domain) {
        this.domain = domain;
    }

    /**
     * @return the excelStream
     */
    public InputStream getExcelStream() {
        return excelStream;
    }

    /**
     * @param excelStream the excelStream to set
     */
    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    /**
     * @return the excelFileName
     */
    public String getExcelFileName() {
        return excelFileName;
    }

    /**
     * @param excelFileName the excelFileName to set
     */
    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
