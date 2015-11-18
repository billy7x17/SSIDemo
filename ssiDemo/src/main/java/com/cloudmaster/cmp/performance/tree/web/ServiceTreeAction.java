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
package com.cloudmaster.cmp.performance.tree.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.performance.tree.dao.ServiceTreeDomain;
import com.cloudmaster.cmp.performance.util.PerformaceDateUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务视图树
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2014-6-25.
 */
public class ServiceTreeAction extends PageAction implements IOperationLog {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6455286427949844993L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ServiceTreeAction.class);
    
    private String functionName;
    
    private String opType;
    /**
     * 日志
     */
    private String operationInfo;

    /**
     * 业务树.
     */
    private String serviceTree = "";

    /**
     * 节点图标
     */
    private String leafIcon = "themes/blue/images/tree-icon-file.png";


    
    
    /**
     * 业务视图树
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void serviceTree() {
        logger.info("ServiceTreeAction begin");
        HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setCharacterEncoding("utf-8");
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            List<ServiceTreeDomain> treeList = ibatisDAO.getData("PerformanceTree.showSiteTree",
                    user);
            List<ServiceTreeDomain> deviceList = ibatisDAO.getData(
                    "PerformanceTree.getSiteDeviceTree", user);// 获得设备信息
            //查询设备离线情况
            String[] ipArray =new String[deviceList.size()];
            for(int i=0;i<deviceList.size();i++){
                ipArray[i]=deviceList.get(i).getDeviceIp();
            }
            ServiceTreeDomain serviceStatus = new ServiceTreeDomain();
            serviceStatus.setIpArray(ipArray);
            serviceStatus.setAgentFrequency(new PerformaceDateUtil().getFrequency());
            if(ipArray.length>0){
                List<ServiceTreeDomain> stateList = ibatisDAO.getData("PerformanceTree.getDeviceState", serviceStatus);
                
                if(!stateList.isEmpty()){
                    Map<String, String> stateMap = new TreeMap<String, String>();
                    for(int i = 0 ;i<stateList.size();i++){
                        stateMap.put(stateList.get(i).getDeviceIp(), stateList.get(i).getStatus());
                    }
                    //插入状态值到设备中
                     for(int i = 0;i<deviceList.size();i++){
                         ServiceTreeDomain tree = deviceList.get(i);
                         if(stateList.contains(tree)){
                            deviceList.get(i).setStatus(stateMap.get(tree.getDeviceIp()));
                         }
                     }
                }
            }
            List<String> groupList = ibatisDAO.getData("PerformanceTree.findDeviceGroupName", null);//获取设备分组名称
            Map<String, List<ServiceTreeDomain>> deviceMap = saveDeviceBySite(deviceList);
            if (!treeList.isEmpty()) {
                serviceTree = "<root>";
                for (int i = 0; i < treeList.size(); i++) {
                    if (i == 0) {// 展开树中第一个节点
                        serviceTree += "<item id=\"" + treeList.get(i).getSiteId()
                                + "\"  parent_id='-1'> " + "<content><name icon='"
                                + leafIcon + "'>" + treeList.get(i).getSitename()
                                + "</name></content></item>";
                    } else {
                        serviceTree += "<item id=\"" + treeList.get(i).getSiteId()
                                + "\" parent_id='-1'> " + "<content><name icon='" + leafIcon + "'>"
                                + treeList.get(i).getSitename() + "</name></content></item>";
                    }
                    serviceTree += initDeviceType(treeList.get(i).getSiteId(),groupList);
                    if (deviceMap.containsKey(treeList.get(i).getSiteId())) {// map中是否存在子节点，存在则添加
                        serviceTree += addTreeNode(treeList.get(i).getSiteId(), deviceMap);
                    }
                }
                serviceTree += "</root>";
            }
            PrintWriter pw = resp.getWriter();
            pw.write(serviceTree);
            pw.flush();
            pw.close();
            operationInfo = getText("performance.deviceTree.success");
        } catch (Exception e) {
            logger.error(getText("performance.deviceTree.error"), e);
            operationInfo = getText("performance.deviceTree.error");
        }
    }
    /**
     * 把设备列表存放在Map里面，站点Id为主键.
     * @param deviceList
     * @return
     */
    private Map<String, List<ServiceTreeDomain>> saveDeviceBySite(List<ServiceTreeDomain> deviceList) {
        Map<String, List<ServiceTreeDomain>> map = new TreeMap<String, List<ServiceTreeDomain>>();
        map.clear();
        for (ServiceTreeDomain tree : deviceList) {
            if (!map.containsKey(tree.getSiteId())) {
                List<ServiceTreeDomain> list = new ArrayList<ServiceTreeDomain>();
                list.add(tree);
                map.put(tree.getSiteId(), list);
            } else {
                map.get(tree.getSiteId()).add(tree);
            }
        }
        return map;
    }

    
    /**
     * 添加子节点.
     * @param rootId
     * @param deviceMap
     * @return
     */
    private String addTreeNode(String rootId, Map<String, List<ServiceTreeDomain>> deviceMap) {
        StringBuffer sb = new StringBuffer();
        List<ServiceTreeDomain> deviceList = deviceMap.get(rootId);
        for (int i = 0; i < deviceList.size(); i++) {
            ServiceTreeDomain tree = deviceList.get(i);
            if (rootId.contains(tree.getSiteId())) {
                if ("CIDC-RT-VMS".equals(tree.getResourceType())) {// VMS
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-VMS_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } else if ("CIDC-RT-NVR".equals(tree.getResourceType())) {// NVR
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-NVR_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } else if ("CIDC-RT-IPSAN".equals(tree.getResourceType())) {// IPSAN
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-IPSAN_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } else if ("CIDC-RT-D4".equals(tree.getResourceType())) {// D4
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-D4_" + rootId
                            + "' typeId='" + tree.getTypeId() + "' deviceIp='" + tree.getDeviceIp()
                            + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } else if ("CIDC-RT-Encoder".equals(tree.getResourceType())) {// Encoder
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-Encoder_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content> ");
                } else if ("CIDC-RT-IPC".equals(tree.getResourceType())) {// IPC
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-IPC_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } else if ("CIDC-RT-Switch".equals(tree.getResourceType())) {// Switch
                    sb.append("<item id='zci_" + tree.getId() + "' parent_id='CIDC-RT-Switch_"
                            + rootId + "' typeId='" + tree.getTypeId() + "' deviceIp='"
                            + tree.getDeviceIp() + "' resName='"+tree.getResourceName()+"' status='"+tree.getStatus()+"'> <content>");
                } 
                if(!"CIDC-RT-Keyword".equals(tree.getResourceType())){
                    if(!StringUtils.isEmpty(tree.getStatus())&&tree.getStatus().equals("1")){
                        sb.append("<name icon='' style='color:"+getText("device.off.color")+";cursor:default;'>"
                            + tree.getResourceName() + "</name></content></item> ");
                    }else{
                        sb.append("<name icon=''>"
                            + tree.getResourceName() + "</name></content></item> ");
                    }  
                }
                
            }
        }
        return sb.toString();
    }

    /**
     * 设置设备的初始化树节点.
     * @param sb
     * @param rootId
     * @param groupList 
     * @return
     */
    private String initDeviceType(String rootId, List<String> groupList) {
        
        StringBuffer sb = new StringBuffer();
        for(String groupName:groupList){
            if("Switch".equals(groupName)){
                sb.append("<item id='CIDC-RT-Switch_" + rootId + "' parent_id='" + rootId
                        + "'><content><name icon=''>"+getText("device.group.Switch")+"</name></content></item> ");
            }else if("IP-SAN".equals(groupName)){
                sb.append("<item id='CIDC-RT-IPSAN_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.IP-SAN")+"</name></content></item> ");
            }else if("IPC".equals(groupName)){
                sb.append("<item id='CIDC-RT-"+groupName+"_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.IPC")+"</name></content></item> ");
            }else if("Encoder".equals(groupName)){
                sb.append("<item id='CIDC-RT-"+groupName+"_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.Encoder")+"</name></content></item> ");
            }else if("D4".equals(groupName)){
                sb.append("<item id='CIDC-RT-"+groupName+"_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.D4")+"</name></content></item> ");
            }else if("NVR".equals(groupName)){
                sb.append("<item id='CIDC-RT-"+groupName+"_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.NVR")+"</name></content></item> ");
            }else if("VMS".equals(groupName)){
                sb.append("<item id='CIDC-RT-"+groupName+"_" + rootId + "' parent_id='" + rootId
                        + "'> <content><name icon=''>"+getText("device.group.VMS")+"</name></content></item> ");
            }
        }
        return sb.toString();
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
    /**
     * @param operationInfo the operationInfo to set
     */
    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }
    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }
    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    /**
     * @param opType the opType to set
     */
    public void setOpType(String opType) {
        this.opType = opType;
    }
    
    public static void main(String[] args) {
        List<ServiceTreeDomain> sList = new ArrayList<ServiceTreeDomain>();
        ServiceTreeDomain tree = new ServiceTreeDomain();
        tree.setDeviceIp("10.10.127.12");
        ServiceTreeDomain tree2 = new ServiceTreeDomain();
        tree2.setDeviceIp("10.10.127.13");
        sList.add(tree);
        sList.add(tree2);
        ServiceTreeDomain tree3 = new ServiceTreeDomain();
        tree3.setDeviceIp("10.10.127.12");
        System.out.println(sList.contains(tree3));
    }
}
