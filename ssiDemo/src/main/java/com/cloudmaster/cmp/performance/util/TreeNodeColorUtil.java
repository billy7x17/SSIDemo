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
package com.cloudmaster.cmp.performance.util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.performance.physics.dao.PMResourceInfoNew;
import com.cloudmaster.cmp.performance.tree.dao.PerformanceTreeDomain;
import com.cloudmaster.cmp.performance.tree.dao.PingDomain;
/**
 * @author <a href="mailto:na.x@neusoft.com">naxu</a>
 * @version 1.0.0 18 Mar 2012
 */
public class TreeNodeColorUtil{
    private static final long serialVersionUID = -7160341448089096869L;
    /**
     * ibatis 空间名
     */
    private static final String IBATIS_NAMESPACE = "PerformanceTree";
    /**
     * ping不同图标
     */
    private String noPing = "noPing";
    /**
     * 工程中图标
     */
    private String noPingWork = "noPingWork";
    /**
     * 构造函数传入
     */
    private Boolean ping;
    private IbatisDAO ibatisDAOCM;
    private IbatisDAO ibatisDAO;
    private Boolean perTeeeNodeRedSwitch;
    public TreeNodeColorUtil(Boolean ping, IbatisDAO ibatisDAOCM, IbatisDAO ibatisDAO,
            Boolean perTeeeNodeRedSwitch) {
        super();
        this.ping = ping;
        this.ibatisDAOCM = ibatisDAOCM;
        this.ibatisDAO = ibatisDAO;
        this.perTeeeNodeRedSwitch = perTeeeNodeRedSwitch;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void ifHaveValue(String resourceType, List<PerformanceTreeDomain> vmLi)
            throws SQLException {
        HttpServletRequest request = ServletActionContext.getRequest();
        //获取当天时间0点
        Calendar zeroClock = Calendar.getInstance();
        zeroClock.set(Calendar.AM_PM, 0);
        zeroClock.set(Calendar.HOUR, 0);
        zeroClock.set(Calendar.MINUTE, 0);
        zeroClock.set(Calendar.SECOND, 0);
        if (ping == true && "CIDC-RT-VM".equals(resourceType)) {
            // 遍历树节点，查询每一个节点是否可以ping通，如果ping不同，则将该节点图标改为ping不同图标
            List list = new ArrayList();
            list = ibatisDAO.getData(IBATIS_NAMESPACE + ".getNoPingVM", "");
            String noPingIP = "";
            // 获取所有虚拟机集合（包括ID字段和IP字段的集合）
            List<PingDomain> iplist = ibatisDAO.getData(IBATIS_NAMESPACE
                    + ".getVmIPUseVMID", "");
            String pmIP = "";
            String pmID = "";
            if (null != list && list.size() > 0) {
                for (int a = 0; a < list.size(); a++) {
                    noPingIP = (String) list.get(a);
                    for (int b = 0; b < iplist.size(); b++) {
                        pmIP = iplist.get(b).getIp();
                        if (null != pmIP && null != noPingIP) {
                            if (!"".equals(noPingIP) && !"".equals(pmIP)
                                    && pmIP.equals(noPingIP)) {
                                pmID = iplist.get(b).getId();
                                for (int c = 0; c < vmLi.size(); c++) {
                                    if (null != vmLi.get(c).getLocalTableIdRef()
                                            && vmLi.get(c).getLocalTableIdRef().equals(
                                                    pmID)) {
                                        vmLi.get(c).setIcon(noPing);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (perTeeeNodeRedSwitch && "CIDC-RT-VM".equals(resourceType)) {
            List<PMResourceInfoNew> list = new ArrayList<PMResourceInfoNew>();
            //定义一个存储IP的数组
            String[] perHidArray = new String[vmLi.size()];
            for (int s = 0; s < vmLi.size(); s++) {
                perHidArray[s] = vmLi.get(s).getIp();
            }
            PMResourceInfoNew pmrin = new PMResourceInfoNew();
            pmrin.setPer_time(zeroClock.getTime());
            pmrin.setPerHidArray(perHidArray);
            list = (List < PMResourceInfoNew >)ibatisDAOCM.getData (
                    "VMRes.getResourceForAll", pmrin);
            for (int s = 0; s < vmLi.size(); s++) {
                boolean colorFlag = false;
                for (int ss = 0 ; ss < list.size(); ss++) {
                    if (vmLi.get(s).getIp().equals(list.get(ss).getPer_hid())) {
                        colorFlag = true;
                        break;
                    }
                }
                if (!colorFlag) {
                    vmLi.get(s).setColor("red");
                }
            }
        }
        //物理机
        if (perTeeeNodeRedSwitch && "CIDC-RT-SRV".equals(resourceType)) {
            // 遍历树节点，查询每一个节点下是否有数据，如果没有数据，则将字体变红，如果有数据则默认黑色字体
            List<PMResourceInfoNew> list = new ArrayList<PMResourceInfoNew>();
            //定义一个存储IP的数组
            String[] perHidArray = new String[vmLi.size()];
            for (int s = 0; s < vmLi.size(); s++) {
                perHidArray[s] = vmLi.get(s).getIp();
            }
            PMResourceInfoNew pmrin = new PMResourceInfoNew();
            pmrin.setPer_time(zeroClock.getTime());
            pmrin.setPerHidArray(perHidArray);
            list = (List < PMResourceInfoNew >)ibatisDAOCM.getData (
                    "PMRes.getResourceForAll", pmrin);
            for (int s = 0; s < vmLi.size(); s++) {
                boolean colorFlag = false;
                for (int ss = 0 ; ss < list.size(); ss++) {
                    if (vmLi.get(s).getIp().equals(list.get(ss).getPer_hid())) {
                        colorFlag = true;
                        break;
                    }
                }
                if (!colorFlag) {
                    vmLi.get(s).setColor("red");
                }
            }
        }
        if (ping == true && "CIDC-RT-SRV".equals(resourceType)) {
            // 遍历树节点，查询每一个节点是否可以ping通，如果ping不同，则将该节点图标改为ping不同图标
            List list = new ArrayList();
            list = ibatisDAO.getData(IBATIS_NAMESPACE + ".getNoPingPM", "");
            String noPingIP = "";
            // 获取所有物理机集合（包括ID字段和IP字段的集合）
            List<PingDomain> iplist = ibatisDAO.getData(IBATIS_NAMESPACE
                    + ".getAllPMIDAndIP", "");
            String pmIP = "";
            String pmState="";
            String pmID = "";
            if (list.size() > 0 || null != iplist) {
                for (int b = 0; b < iplist.size(); b++) {
                    pmIP = iplist.get(b).getIp();
                    pmState = iplist.get(b).getPmInstallState();
                    pmID = iplist.get(b).getId();
                    if ("1".equals(pmState)) {
                        for (int c = 0; c < vmLi.size(); c++) {
                            if (null != vmLi.get(c).getLocalTableIdRef()
                                    && vmLi.get(c).getLocalTableIdRef().equals(
                                            pmID)) {
                                vmLi.get(c).setIcon(noPingWork);
                                vmLi.get(c).setColor("red");
                            }
                        }
                        continue;
                    }
                    for (int a = 0; a < list.size(); a++) {
                        noPingIP = (String) list.get(a);
                        if (null != pmIP && null != noPingIP) {
                            if (!"".equals(noPingIP) && !"".equals(pmIP)
                                    && pmIP.equals(noPingIP)) {
                                for (int c = 0; c < vmLi.size(); c++) {
                                    if (null != vmLi.get(c).getLocalTableIdRef()
                                            && vmLi.get(c).getLocalTableIdRef().equals(
                                                    pmID)) {
                                        //只有PING或者Ganglia采集异常两类告警才进行显示，而且是告警未清除的
                                        if (null != iplist.get(b).getPmInstallState()
                                                && "1".equals(iplist.get(b)
                                                        .getPmInstallState())) {
                                            vmLi.get(c).setIcon(noPingWork);
                                        } else {
                                            vmLi.get(c).setIcon(noPing);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //设置物理机节点名称后面所承载的虚拟机个数
        if (null != vmLi && !vmLi.isEmpty() && "CIDC-RT-SRV".equals(resourceType)) {
            for (int k = 0; k < vmLi.size(); k++) {
                // 遍历查询每一个物理机下所承载的虚拟机个数
                String pmID = vmLi.get(k).getLocalTableIdRef();
                Integer count = ibatisDAO.getCount(IBATIS_NAMESPACE + ".getPMLoadVMCount",
                        pmID);
                if (null != count && !count.equals(0)) {
                    String tempName = vmLi.get(k).getZoneClusterName();
                    vmLi.get(k).setZoneClusterName(tempName + "(" + count + ")");
                }
            }
        }
    }
    public Boolean getPing() {
        return ping;
    }
    public void setPing(Boolean ping) {
        this.ping = ping;
    }
    public String getNoPing() {
        return noPing;
    }
    public void setNoPing(String noPing) {
        this.noPing = noPing;
    }
    public IbatisDAO getIbatisDAOCM() {
        return ibatisDAOCM;
    }
    public void setIbatisDAOCM(IbatisDAO ibatisDAOCM) {
        this.ibatisDAOCM = ibatisDAOCM;
    }
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
    public String getNoPingWork() {
        return noPingWork;
    }
    public void setNoPingWork(String noPingWork) {
        this.noPingWork = noPingWork;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }
    public Boolean getPerTeeeNodeRedSwitch() {
        return perTeeeNodeRedSwitch;
    }
    public void setPerTeeeNodeRedSwitch(Boolean perTeeeNodeRedSwitch) {
        this.perTeeeNodeRedSwitch = perTeeeNodeRedSwitch;
    }
}
