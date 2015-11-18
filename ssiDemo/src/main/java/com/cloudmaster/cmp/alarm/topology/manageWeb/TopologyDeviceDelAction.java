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
import com.cloudmaster.cmp.alarm.topology.manageDomain.TopologyDeviceDomain;
import com.cloudmaster.cmp.alarm.topology.manageDomain.TopologyLineDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 拓扑分组内设备删除方法
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TopologyDeviceDelAction extends PageAction implements IAuthIdGetter, IOperationLog {
    private static final long serialVersionUID = 2289922048622071060L;

    private static LogService logger = LogService.getLogger(TopologyDeviceDelAction.class);

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
    TopologyDeviceDomain domain = new TopologyDeviceDomain();
    private String groupID;
    /**
     * 组内设备删除方法
     * @return
     */
    public String del() {
        logger.info(getText("function.title") + getText("log.deviceDel.begin"));
        try {
            groupID = domain.getGroupID();
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".deleteGroupNode",domain);
            TopologyLineDomain temp = new TopologyLineDomain();
            temp.setToNode(domain.getDeviceID());
            ibatisDAO.deleteData(IBATIS_NAMESPACE + ".deleteLine", temp);
            logger.info(getText("function.title") + getText("log.deviceDel.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.deviceDel.error"), e);
            operationInfo = getText("oplog.deviceDel.error") + getText("common.message.systemError");
        }
        return forward;
    }
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
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
    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }
    public TopologyDeviceDomain getDomain() {
        return domain;
    }
    public void setDomain(TopologyDeviceDomain domain) {
        this.domain = domain;
    }
}
