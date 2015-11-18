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
package com.cloudmaster.cmp.performance.physics.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
/*******************************************************************************
 * @(#)PMResourceSearchAction.java 2012-1-6
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.performance.physics.dao.PMInfo;
import com.cloudmaster.cmp.performance.physics.dao.PMResourceInfoNew;
import com.cloudmaster.cmp.performance.util.PerformaceDateUtil;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 物理机资源分配Tab页查询
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class PMResourceSearchAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4083541745621318510L;
  
    /**
     * 物理机性能数据集合
     */
    private List < PMResourceInfoNew > pmResInfos;
   
    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(PMResourceSearchAction.class);
    /**
     * 在性能页面头部添加标志，点击的是哪一个机器
     */
    private String ip;
    
    /**
     * 设备种类
     */
    private String deviceType;
    
    /**
     * 设备IP.
     */
    private String deviceIp;
    
    public String init() {
        
        return forward;
    }
    
    //获取折线图数据，集合中的最后一个就是折线图下的表格数据
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void searchResource() {
        logger.info(getText("function.title") + getText("pm.resource.in"));
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            //直接根据ID查询物理机表状态获取工程中
            deviceIp = request.getParameter("deviceIp");
            DeviceDomain device = new DeviceDomain();
            device.setAgentIp(deviceIp);
            device.setAgentFrequency(getFrequency());
            String state = (String) ibatisDAO.getSingleRecord("DeviceInfo.getResourceStatus",device);
            if (null != state && "1".equals(state)) {
            } else {
                //由于分区集群表的修改将leaf字段中的ip修改为了名称,所以我需要根据id去本地表查询ip
                
                //获取当天时间0点
                PMResourceInfoNew pmrin = new PMResourceInfoNew();
                pmrin.setPer_hid(deviceIp);
                PerformaceDateUtil performanceDate = new PerformaceDateUtil(ibatisDAO);
                pmrin.setPer_time(performanceDate.getStartTime());
                //查询基本tab页的性能指标数据
                pmResInfos = (List < PMResourceInfoNew >)ibatisDAO.getData (
                        "PMRes.getResource", pmrin);
                //查询功耗和进程的性能指标数据
            }
            //增加判断，如果查询结果集合为空，则对空数据进行处理
            //为了调整有无数据，以及不同机器之间数据时间段情况的不同，将时间段修改为统一时间段
            if(pmResInfos.isEmpty()){
                setStartAndEndTime();
            }
            //解析集合，拼为JSONArray数组
            JSONArray jsonArray = new JSONArray();
            for (int z = 0; z < pmResInfos.size(); z++) {
                jsonArray.add(pmResInfos.get(z));
            }
            pw.write(jsonArray.toString());
            
            logger.info(getText("pm.resource.out"));
            operationInfo = getText("pm.resource.success");
         } catch (Exception e) {
                logger.error(getText("function.title")+getText("pm.resource.error"), e);
                operationInfo = getText("pm.resource.fail");
        } finally {
            try {
                pw.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 在时间轴上增加气势和结束时间点
     */
    private void setStartAndEndTime() {
        PerformaceDateUtil performanceDate = new PerformaceDateUtil(ibatisDAO);
        Calendar now = Calendar.getInstance();
        PMResourceInfoNew prn = new PMResourceInfoNew();
        prn.setPer_time(performanceDate.getStartTime());
        pmResInfos.add(0,prn);
        PMResourceInfoNew prn1 = new PMResourceInfoNew();
        prn1.setPer_time(now.getTime());
        pmResInfos.add(prn1);
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getAlreadyConfTab(){
        logger.info(getText("function.title") + getText("pm.resource.getAlreadyConfTab.start"));
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            //获得设备类型
            String typeId = request.getParameter("typeId");
            List<MibinfoDomain> mibdom = (List<MibinfoDomain>)ibatisDAO.getData("PMInfo.getAlreadyConfTab", typeId);
            StringBuffer confTab = new StringBuffer();
            //判断查询的tab不为空，则将tab返回到页面
            if (null != mibdom && mibdom.size() > 0) {
                for (int i = 0 ; i < mibdom.size(); i++) {
                    if (i==0) {
                        confTab.append(mibdom.get(i).getIndexGroup());
                    } else {
                        confTab.append(";"+mibdom.get(i).getIndexGroup());
                    }
                }
            }
            pw.write(confTab.toString());
            pw.close();
            logger.info(getText("function.title") + getText("pm.resource.getAlreadyConfTab.success"));
            operationInfo = getText("pm.resource.getAlreadyConfTab.success");
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("pm.resource.getAlreadyConfTab.exception"), e);
            operationInfo = getText("pm.resource.getAlreadyConfTab.exception");
        }
    }
    //获取所有Tab下的具体指标
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getAlreadyConfTabIndex(){
        logger.info(getText("function.title") + getText("pm.resource.getAlreadyConfTabIndex.start"));
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            String typeId = request.getParameter("typeId");
            //获取除进程和功耗Tab之外的其他Tab所包含的具体指标信息
            List<MibinfoDomain> mibdom = (List<MibinfoDomain>)ibatisDAO.getData("PMInfo.getAlreadyConfTabIndex", typeId);
          

            JSONArray jsonArray = new JSONArray();
            for (int z = 0; z < mibdom.size(); z++) {
                jsonArray.add(mibdom.get(z));
            }
            pw.write(jsonArray.toString());
            pw.close();
            logger.info(getText("function.title") + getText("pm.resource.getAlreadyConfTabIndex.success"));
            operationInfo = getText("pm.resource.getAlreadyConfTabIndex.success");
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("pm.resource.getAlreadyConfTabIndex.exception"), e);
            operationInfo =  getText("pm.resource.getAlreadyConfTabIndex.exception");
        }
    }
    //查询物理机性能数据的增量信息
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void searchPMResourceIncrease(){
        logger.info(getText("function.title") + getText("pm.resource.searchPMResourceIncrease.start"));
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
          //由于分区集群表的修改将leaf字段中的ip修改为了名称,所以我需要根据id去本地表查询ip
            String ip = request.getParameter("deviceIp");
            String time = getText("resource.rangeTimeSQL");
            int timeInt = Integer.parseInt(time);
            PMInfo pminfo = new PMInfo();
            pminfo.setPmIP(ip);
            pminfo.setTime(timeInt);
            //查询基本tab页的性能指标数据
            pmResInfos = (List < PMResourceInfoNew >)ibatisDAO.getData (
                    "PMRes.getResourceIncrease", pminfo);
            
            
            //解析集合，拼为JSONArray数组
            JSONArray jsonArray = new JSONArray();
            for (int z = 0; z < pmResInfos.size(); z++) {
                jsonArray.add(pmResInfos.get(z));
            }
            
            pw.write(jsonArray.toString());
            pw.close();
            logger.info(getText("function.title") + getText("pm.resource.searchPMResourceIncrease.success"));
            operationInfo =  getText("pm.resource.searchPMResourceIncrease.success");
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("pm.resource.searchPMResourceIncrease.exception"), e);
            operationInfo = getText("pm.resource.searchPMResourceIncrease.exception");
        }
    }
    
    /**
     * 读取ganglia采集频率
     */
    public String getFrequency() {
        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        String agentFrequency = "";
        try {
            prop.load(classLoader.getResourceAsStream("conf/other/System.properties"));
            agentFrequency = prop.getProperty("agentFrequency");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agentFrequency;
    }
    
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
  
    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return the deviceIp
     */
    public String getDeviceIp() {
        return deviceIp;
    }

    /**
     * @param deviceIp the deviceIp to set
     */
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

}
