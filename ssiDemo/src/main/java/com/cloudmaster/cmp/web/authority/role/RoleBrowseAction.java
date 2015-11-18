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
package com.cloudmaster.cmp.web.authority.role;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 浏览角色信息的aciton
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleBrowseAction extends PageAction implements IAuthIdGetter, IOperationLog {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(RoleBrowseAction.class);

    private List<RoleInfo> roleList;

    private String errorMsg;

    private String functionName = "";

    private String operationInfo = "";

    private String opType = "";

    private String forward = "SUCCESS";
    
    private RoleInfo domain;

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    private String validateFail;
    
    public String tolist() {
        return forward;
    }

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        functionName = "角色管理";
        opType = "View";
        if (null != validateFail) {
            this.addFieldError("status", validateFail);
        }

        if (null != errorMsg) {
            this.addActionError(errorMsg);
        }
        try {
            HttpServletRequest jsprequest = ServletActionContext.getRequest();
            // 增加排序操作
            if(null == domain){
                domain = new RoleInfo();
            }
            domain.setSortName(jsprequest.getParameter("sortname"));
            domain.setSortOrder(jsprequest.getParameter("sortorder"));
            domain.setQtype(jsprequest.getParameter("qtype"));
            domain.setQuery(jsprequest.getParameter("query"));
            String rp = jsprequest.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            roleList = getPage("RoleInfo.getRoleInfoCount", "RoleInfo.getRoleInfos", domain);
            if (roleList.size() > 0) {
            } else {
                operationInfo = getText("function.title") + getText("oplog.list.error");
                forward = "ERROR";
            }
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            forward = "ERROR";
            logger.info(getText("function.title") + getText("log.list.error"), e);
        }
        try {
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (RoleInfo ahd : roleList) {
                fgjd.setRowId(ahd.getRoleId()); // 设置行标识
                fgjd.addColdata(ahd.getRoleName()); // 设置此行 第二列内容
                fgjd.addColdata(ahd.getRoleType());     /*添加角色类型*/
                fgjd.addColdata(ahd.getStatus());   // ……
                fgjd.addColdata(ahd.getDescription());
                fgjd.addColdata(ahd.getCreatTime());
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

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getValidateFail() {
        return validateFail;
    }

    public void setValidateFail(String validateFail) {
        this.validateFail = validateFail;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    /**
     * 为了能取到配置文件中的值
     * @param functionName
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String getOpType() {
        return opType;
    }

    /**
     * 为了能取到配置文件中的值
     * @param functionName
     */
    public void setOpType(String opType) {
        this.opType = opType;
    }

    public RoleInfo getDomain() {
        return domain;
    }

    public void setDomain(RoleInfo domain) {
        this.domain = domain;
    }

}
