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
import java.util.ArrayList;
import java.util.List;
import com.cloudmaster.cmp.alarm.topology.manageDomain.TopologyDeviceDomain;
import com.cloudmaster.cmp.alarm.topology.manageDomain.TopologyLineDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 拓扑分组内设备添加方法
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TopologyDeviceAddAction extends PageAction implements IAuthIdGetter, IOperationLog {
    private static final long serialVersionUID = 2289922048622071060L;

    private static LogService logger = LogService.getLogger(TopologyDeviceAddAction.class);

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
    /**
     * 拓扑设备List
     */
    List<TopologyDeviceDomain> list = new ArrayList<TopologyDeviceDomain>();
    /**
     * 跳转到添加页面
     */
    public String beforeAdd() {
        return forward;
    }
    private String groupID;
    /**
     * 组内设备添加方法
     * @return
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.deviceAdd.begin"));
        try {
            groupID = domain.getGroupID();
            String deviceID[] = domain.getDeviceID().split(",");
            String deviceName[] = domain.getDeviceName().split(",");
            List<TopologyLineDomain> linelist = 
                ibatisDAO.getData(IBATIS_NAMESPACE + ".getGroupUPDevice", domain);
            
            //维护组和设备表
            for (int i = 0; i< deviceID.length;i++) {
                TopologyDeviceDomain temp = new TopologyDeviceDomain();
                temp.setGroupID(domain.getGroupID());
                temp.setDeviceID(deviceID[i]);
                temp.setDeviceType(domain.getDeviceType());
                temp.setDeviceName(deviceName[i]);
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertGroupNode", temp);
            }
            
            //维护线表，from为组的上层设备，to为设备ID
            for (int i = 0; i< deviceID.length;i++) {
                for (TopologyLineDomain line:linelist) {
                    TopologyLineDomain temp = new TopologyLineDomain();
                    temp.setGroupID(domain.getGroupID());
                    if (domain.getGroupID().equals(line.getFromNode())) {
                        temp.setFromNode(line.getToNode());
                    } else {
                        temp.setFromNode(line.getFromNode());
                    }
                    temp.setToNode(deviceID[i]);
                    ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertLine", temp);
                }
            }
            msg = "添加设备成功";
            logger.info(getText("function.title") + getText("log.deviceAdd.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.deviceAdd.error"), e);
            operationInfo = getText("oplog.deviceAdd.error") + getText("common.message.systemError");
        }
        return forward;
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
    public List<TopologyDeviceDomain> getList() {
        return list;
    }
    public void setList(List<TopologyDeviceDomain> list) {
        this.list = list;
    }
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
