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
package com.cloudmaster.cmp.alarm.topology.manageWeb;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.alarm.topology.manageDomain.TopologyGroupDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 拓扑分组列表方法
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TopologyGroupListAction extends PageAction implements IAuthIdGetter, IOperationLog {
    private static final long serialVersionUID = 2289922048622071060L;

    private static LogService logger = LogService.getLogger(TopologyGroupListAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "topologyManage";

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
     * 错误消息
     */
    private String errorMsg;

    /**
     * 拓扑设备Domain
     */
    TopologyGroupDomain domain = new TopologyGroupDomain();
    /**
     * 拓扑设备List
     */
    List<TopologyGroupDomain> list = new ArrayList<TopologyGroupDomain>();
    /**
     * 跳转方法
     */
    public String beforeList() {
        return forward;
    }

    /**
     * 拓扑分组列表页面
     * @return
     */
    public String list() {
        logger.info(getText("function.title") + getText("log.groupList.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));

            // 此操作必须在调用getpage()方法之前
            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));
            
            list = getPage(IBATIS_NAMESPACE + ".getgroupCount", IBATIS_NAMESPACE + ".getgroupList", domain);
            
            logger.info(getText("function.title") + getText("log.groupList.end"));
            operationInfo = getText("oplog.groupList.success");
            
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (TopologyGroupDomain bean : list) {
                fgjd.setRowId(bean.getGroupID()); // 设置行标识
                fgjd.addColdata(bean.getGroupID());
                fgjd.addColdata(bean.getGroupName());
            }
            
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            logger.info("json:" + fgjd.toString());
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.groupList.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.groupList.error"), e);
            operationInfo = getText("oplog.groupList.error") + getText("common.message.systemError");
        }
        return null;
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
    public String getForward() {
        return forward;
    }
    public void setForward(String forward) {
        this.forward = forward;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public TopologyGroupDomain getDomain() {
        return domain;
    }
    public void setDomain(TopologyGroupDomain domain) {
        this.domain = domain;
    }
}
