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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cloudmaster.cmp.performance.physics.dao.PMInfo;
import com.cloudmaster.cmp.performance.tree.dao.PerformanceTreeDomain;
import com.cloudmaster.cmp.performance.tree.dao.ServiceViewTreeDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:na.x@neusoft.com">naxu</a>
 * @version 1.0.0 18 Mar 2012
 */
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class PerformanceTreeRefrashAction extends BaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * System.properties文件中注入的性能树节点是否为红色的判断
     */
    private Boolean perTeeeNodeRedSwitch;
    public Boolean getPerTeeeNodeRedSwitch() {
        return perTeeeNodeRedSwitch;
    }
    public void setPerTeeeNodeRedSwitch(Boolean perTeeeNodeRedSwitch) {
        this.perTeeeNodeRedSwitch = perTeeeNodeRedSwitch;
    }

    /**
     * 日志类
     */
    private static LogService logger = LogService.getLogger(PerformanceTreeRefrashAction.class);

    /**
     * javabean对象
     */
    private PerformanceTreeDomain domain = new PerformanceTreeDomain();

    /**
     * 树节点列表
     */
    private List<PerformanceTreeDomain> treeList = new ArrayList<PerformanceTreeDomain>();

    /**
     * 初始化打开节点ID
     */
    private String initId;

    /**
     * 初始化打开节点类型
     */
    private String initType;

    /**
     * ibatis 空间名
     */
    private static final String IBATIS_NAMESPACE = "PerformanceTree";

    /**
     *forward
     */
    private String forward = SUCCESS;

    /**
     * 节点图标
     */
    private String leafIcon = "././././themes/default/images/ico_file.png";

    /**
     * 叶子图标
     */
    private String nodeIcon = "././././themes/default/images/ico_folder.png";
    /**
     * 树根节点ID
     */
    private String treeRootId;
    public String getTreeRootId() {
        return treeRootId;
    }
    public void setTreeRootId(String treeRootId) {
        this.treeRootId = treeRootId;
    }
    
    /**
     * 初始化打开节点bean
     */
    private PerformanceTreeDomain initDomain = new PerformanceTreeDomain();
    /**
     * 点击IP传递过来的资源id和资源类型
     */
    private String deviceId;
    private String serverType;
    private String flagRefre = "true";
    public String getFlagRefre() {
        return flagRefre;
    }
    public void setFlagRefre(String flagRefre) {
        this.flagRefre = flagRefre;
    }
    /**
     * 初始化树的结构
     */
    public String perfomanceTree() {
        try {
            // 获取分区机柜 查询出树结构表(zone_cluster_tab)中全部内容
            PerformanceTreeDomain zoneParaBean = new PerformanceTreeDomain();
            zoneParaBean.setIcon(nodeIcon);
            List<PerformanceTreeDomain> zoneLi = ibatisDAO.getData(
                    IBATIS_NAMESPACE + ".getTree", zoneParaBean);
            treeList.addAll(zoneLi);

            Iterator it = zoneLi.iterator();
            while (it.hasNext()) {
                PerformanceTreeDomain bean = (PerformanceTreeDomain) it.next();
                String id = bean.getLocalTableIdRef();
                String pid = bean.getParentId();
                if (!pid.equals("0") && !pid.equals(treeRootId)) {
                    //物理机
                    PerformanceTreeDomain pmParaBean = new PerformanceTreeDomain();
                    pmParaBean.setLocalTableIdRef(id);
                    pmParaBean.setResourceType("CIDC-RT-SRV");
                    pmParaBean.setParentId(id + "/CIDC-RT-SRV");
                    pmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> pmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", pmParaBean);
                    if (null != pmLi && !pmLi.isEmpty()) {
                        for (int k = 0;k<pmLi.size();k++) {
                            String pmID = pmLi.get(k).getLocalTableIdRef();
                            Integer count = ibatisDAO.getCount(IBATIS_NAMESPACE
                                    + ".getPMLoadVMCount", pmID);
                            if (null != count && !count.equals(0)) {
                                String zoneClusterName = pmLi.get(k).getZoneClusterName();
                                pmLi.get(k).setZoneClusterName(zoneClusterName +"(" + count + ")");
                            }
                        }
                        treeList.addAll(pmLi);
                        
                        //机柜下存在物理机，增加物理机节点
                        PerformanceTreeDomain pmBean = new PerformanceTreeDomain();
                        pmBean.setLocalTableIdRef(id + "/CIDC-RT-SRV");
                        pmBean.setZoneClusterName(getText("CIDC-RT-SRV")+"("+pmLi.size()+")");
                        pmBean.setParentId(id);
                        pmBean.setIcon(nodeIcon);
                        treeList.add(pmBean);
                    }
                    
                    //查询各个机柜下的虚拟机
                    PerformanceTreeDomain vmParaBean = new PerformanceTreeDomain();
                    vmParaBean.setLocalTableIdRef(id);
                    vmParaBean.setResourceType("CIDC-RT-VM");
                    vmParaBean.setParentId(id + "/CIDC-RT-VM");
                    vmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> vmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", vmParaBean);
                    if (null != vmLi && !vmLi.isEmpty()) {
                        treeList.addAll(vmLi);
                        
                        //机柜下存在虚拟机，增加虚拟机节点
                        PerformanceTreeDomain vmBean = new PerformanceTreeDomain();
                        vmBean.setLocalTableIdRef(id + "/CIDC-RT-VM");
                        vmBean.setZoneClusterName(getText("CIDC-RT-VM")+"("+vmLi.size()+")");
                        vmBean.setParentId(id);
                        vmBean.setIcon(nodeIcon);
                        treeList.add(vmBean);
                    }
                    //阵列
                    PerformanceTreeDomain asParaBean = new PerformanceTreeDomain();
                    asParaBean.setLocalTableIdRef(id);
                    asParaBean.setResourceType("CIDC-RT-AS");
                    asParaBean.setParentId(id + "/CIDC-RT-AS");
                    asParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> asLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", asParaBean);
                    if (null != asLi && !asLi.isEmpty()) {
                        treeList.addAll(asLi);

                        PerformanceTreeDomain asBean = new PerformanceTreeDomain();
                        asBean.setLocalTableIdRef(id + "/CIDC-RT-AS");
                        asBean.setZoneClusterName(getText("CIDC-RT-AS")+"("+asLi.size()+")");
                        asBean.setParentId(id);
                        asBean.setIcon(nodeIcon);
                        treeList.add(asBean);
                    }
                    //块
                    PerformanceTreeDomain bsParaBean = new PerformanceTreeDomain();
                    bsParaBean.setLocalTableIdRef(id);
                    bsParaBean.setResourceType("CIDC-RT-BS");
                    bsParaBean.setParentId(id + "/CIDC-RT-BS");
                    bsParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> bsLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", bsParaBean);
                    if (null != bsLi && !bsLi.isEmpty()) {
                        treeList.addAll(bsLi);

                        PerformanceTreeDomain bsBean = new PerformanceTreeDomain();
                        bsBean.setLocalTableIdRef(id + "/CIDC-RT-BS");
                        bsBean.setZoneClusterName(getText("CIDC-RT-BS")+"("+bsLi.size()+")");
                        bsBean.setParentId(id);
                        bsBean.setIcon(nodeIcon);
                        treeList.add(bsBean);
                    }
                    //小型机
                    PerformanceTreeDomain mmParaBean = new PerformanceTreeDomain();
                    mmParaBean.setLocalTableIdRef(id);
                    mmParaBean.setResourceType("CIDC-RT-MM");
                    mmParaBean.setParentId(id + "/CIDC-RT-MM");
                    mmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> mmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", mmParaBean);
                    if (null != mmLi && !mmLi.isEmpty()) {
                        treeList.addAll(mmLi);

                        PerformanceTreeDomain osBean = new PerformanceTreeDomain();
                        osBean.setLocalTableIdRef(id + "/CIDC-RT-MM");
                        osBean.setZoneClusterName(getText("CIDC-RT-MM")+"("+mmLi.size()+")");
                        osBean.setParentId(id);
                        osBean.setIcon(nodeIcon);
                        treeList.add(osBean);
                    }
                    //小型机分区
                    PerformanceTreeDomain mvmParaBean = new PerformanceTreeDomain();
                    mvmParaBean.setLocalTableIdRef(id);
                    mvmParaBean.setResourceType("CIDC-RT-MC");
                    mvmParaBean.setParentId(id + "/CIDC-RT-MC");
                    mvmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> mvmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", mvmParaBean);
                    if (null != mvmLi && !mvmLi.isEmpty()) {
                        treeList.addAll(mvmLi);

                        PerformanceTreeDomain osBean = new PerformanceTreeDomain();
                        osBean.setLocalTableIdRef(id + "/CIDC-RT-MC");
                        osBean.setZoneClusterName(getText("CIDC-RT-MC")+"("+mvmLi.size()+")");
                        osBean.setParentId(id);
                        osBean.setIcon(nodeIcon);
                        treeList.add(osBean);
                    }
                    //交换机
                    PerformanceTreeDomain swParaBean = new PerformanceTreeDomain();
                    swParaBean.setLocalTableIdRef(id);
                    swParaBean.setResourceType("CIDC-RT-SW");
                    swParaBean.setParentId(id + "/CIDC-RT-SW");
                    swParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> swLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", swParaBean);
                    if (null != swLi && !swLi.isEmpty()) {
                        treeList.addAll(swLi);

                        PerformanceTreeDomain osBean = new PerformanceTreeDomain();
                        osBean.setLocalTableIdRef(id + "/CIDC-RT-SW");
                        osBean.setZoneClusterName(getText("CIDC-RT-SW")+"("+swLi.size()+")");
                        osBean.setParentId(id);
                        osBean.setIcon(nodeIcon);
                        treeList.add(osBean);
                    }
                    //对象
                    PerformanceTreeDomain osParaBean = new PerformanceTreeDomain();
                    osParaBean.setLocalTableIdRef(id);
                    osParaBean.setResourceType("CIDC-RT-OS");
                    osParaBean.setParentId(id + "/CIDC-RT-OS");
                    osParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> osLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getInstance123", osParaBean);
                    if (null != osLi && !osLi.isEmpty()) {
                        treeList.addAll(osLi);

                        PerformanceTreeDomain osBean = new PerformanceTreeDomain();
                        osBean.setLocalTableIdRef(id + "/CIDC-RT-OS");
                        osBean.setZoneClusterName(getText("CIDC-RT-OS")+"("+osLi.size()+")");
                        osBean.setParentId(id);
                        osBean.setIcon(nodeIcon);
                        treeList.add(osBean);
                    }
                    //定义跳转后需要打开的节点
                    initDomain = new PerformanceTreeDomain();
                    initDomain.setLocalTableIdRef(deviceId);
                    initDomain.setParentId("");
                    initDomain.setZoneClusterName("");
                    initDomain.setZoneClusterId("");
                    initDomain.setResourceType(serverType);
                    initDomain.setIcon(leafIcon);
                    initId = initDomain.getLocalTableIdRef();
                    initType = initDomain.getResourceType();
                }
            }
          //将设备视图的数据也传递到页面中
            deviceTree();
            //将业务视图的数据也传到页面中
            serviceTree();

        } catch (Exception e) {
            logger.error("perfomanceTree error", e);
            forward = ERROR;
        }
        return forward;
    }

    public PerformanceTreeDomain getDomain() {
        return domain;
    }

    public void setDomain(PerformanceTreeDomain domain) {
        this.domain = domain;
    }

    public List<PerformanceTreeDomain> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<PerformanceTreeDomain> treeList) {
        this.treeList = treeList;
    }

    public PerformanceTreeDomain getInitDomain() {
        return initDomain;
    }

    public void setInitDomain(PerformanceTreeDomain initDomain) {
        this.initDomain = initDomain;
    }

    public String getInitId() {
        return initId;
    }

    public void setInitId(String initId) {
        this.initId = initId;
    }

    public String getInitType() {
        return initType;
    }

    public void setInitType(String initType) {
        this.initType = initType;
    }

    public String getLeafIcon() {
        return leafIcon;
    }

    public void setLeafIcon(String leafIcon) {
        this.leafIcon = leafIcon;
    }

    public String getNodeIcon() {
        return nodeIcon;
    }

    public String getServerType() {
        return serverType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public void setNodeIcon(String nodeIcon) {
        this.nodeIcon = nodeIcon;
    }
    /**
     * 初始化打开节点bean
     */
    private PerformanceTreeDomain initDomain1 = new PerformanceTreeDomain();
    /**
     * 初始化打开节点ID
     */
    private String initId1;
    /**
     * 初始化打开节点类型
     */
    private String initType1;
    public String deviceTree() {
        try {
            // 只查询南基根节点
            PerformanceTreeDomain zoneParaBean = new PerformanceTreeDomain();
            zoneParaBean.setIcon(nodeIcon);
            zoneParaBean.setZoneClusterId(treeRootId);
            List<PerformanceTreeDomain> zoneLi = ibatisDAO.getData(
                    IBATIS_NAMESPACE + ".getDeviceTree", zoneParaBean);
            treeList1.addAll(zoneLi);
            int i = 0;
            Iterator it = zoneLi.iterator();
            while (it.hasNext()) {
                PerformanceTreeDomain bean = (PerformanceTreeDomain) it.next();
                String id = bean.getLocalTableIdRef();

                    //物理机
                    PerformanceTreeDomain pmParaBean = new PerformanceTreeDomain();
                    pmParaBean.setLocalTableIdRef(id);
                    pmParaBean.setResourceType("CIDC-RT-SRV");
                    pmParaBean.setParentId("CIDC-RT-SRV");
                    pmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> pmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getDeviceInstance", pmParaBean);
                    if (null != pmLi && !pmLi.isEmpty()) {
                        for (int k = 0;k<pmLi.size();k++) {
                            //遍历查询每一个物理机下所承载的虚拟机个数
                            String pmID = pmLi.get(k).getLocalTableIdRef();
                            Integer count = ibatisDAO.getCount(IBATIS_NAMESPACE
                                    + ".getPMLoadVMCount", pmID);
                            if (null != count && !count.equals(0)) {
                                String zoneClusterName = pmLi.get(k).getZoneClusterName();
                                pmLi.get(k).setZoneClusterName(zoneClusterName +"(" + count + ")");
                            }

                            //新需求，设备视图树和业务视图树下展示物理机名称和虚拟机名称
                            String pmName = (String)ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                                    + ".getPMNameUsePMID", pmID);
                            pmLi.get(k).setZoneClusterName(pmName);
                        }

                        

                        treeList1.addAll(pmLi);

                        //机柜下存在物理机，增加物理机节点
                        PerformanceTreeDomain pmBean = new PerformanceTreeDomain();
                        pmBean.setLocalTableIdRef("CIDC-RT-SRV");
                        pmBean.setZoneClusterName(getText("CIDC-RT-SRV")+"("+pmLi.size()+")");
                        pmBean.setParentId(id);
                        pmBean.setIcon(nodeIcon);
                        treeList1.add(pmBean);

                        //获取第一个打开的节点
                        if (i == 0 && null != pmLi && !pmLi.isEmpty()) {
                            initDomain1 = pmLi.get(0);
                            initId1 = pmLi.get(0).getLocalTableIdRef();
                            initType1 = "CIDC-RT-SRV";
                        }
                        i++;
                    }
                    //查询各个机柜下的虚拟机
                    PerformanceTreeDomain vmParaBean = new PerformanceTreeDomain();
                    vmParaBean.setLocalTableIdRef(id);
                    vmParaBean.setResourceType("CIDC-RT-VM");
                    vmParaBean.setParentId("CIDC-RT-VM");
                    vmParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> vmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getDeviceInstance", vmParaBean);
                    if (null != vmLi && !vmLi.isEmpty()) {
                        //新需求，设备视图树和业务视图树下展示物理机名称和虚拟机名称
                        for (int k = 0; k < vmLi.size(); k++) {
                            String vmID = vmLi.get(k).getLocalTableIdRef();
                            String vmName = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                                    + ".getVMNameUseVMID", vmID);
                            vmLi.get(k).setZoneClusterName(vmName);
                        }


                        treeList1.addAll(vmLi);
                        //机柜下存在虚拟机，增加虚拟机节点
                        PerformanceTreeDomain vmBean = new PerformanceTreeDomain();
                        vmBean.setLocalTableIdRef("CIDC-RT-VM");
                        vmBean.setZoneClusterName(getText("CIDC-RT-VM")+"("+vmLi.size()+")");
                        vmBean.setParentId(id);
                        vmBean.setIcon(nodeIcon);
                        treeList1.add(vmBean);

                        //获取第一个打开的节点
                        if (i == 0 && null != vmLi && !vmLi.isEmpty()) {
                            initDomain1 = vmLi.get(0);
                            initId1 = vmLi.get(0).getLocalTableIdRef();
                            initType1 = "CIDC-RT-VM";
                        }
                        i++;
                    }
                    //阵列
                    PerformanceTreeDomain asParaBean = new PerformanceTreeDomain();
                    asParaBean.setLocalTableIdRef(id);
                    asParaBean.setResourceType("CIDC-RT-AS");
                    asParaBean.setParentId("CIDC-RT-AS");
                    asParaBean.setIcon(leafIcon);
                    List<PerformanceTreeDomain> asLi = ibatisDAO.getData(IBATIS_NAMESPACE
                            + ".getDeviceInstance", asParaBean);
                    if (null != asLi && !asLi.isEmpty()) {
                        
                        if (perTeeeNodeRedSwitch) {
                            //遍历树节点，查询每一个节点下是否有数据，如果没有数据，则将字体变红，如果有数据则默认黑色字体
                            //CIDC-RT-SRV,CIDC-RT-VM,CIDC-RT-AS,CIDC-RT-BS,CIDC-RT-MM,CIDC-RT-MC,CIDC-RT-OS
                            List list = new ArrayList();
                            for (int s = 0; s < asLi.size(); s++) {
                                list = ibatisDAO.getData("ASInfo.getASResource", asLi.get(s).getLocalTableIdRef());
                                if (null == list || list.size() <= 0) {
                                    asLi.get(s).setColor("red");
                                }
                            }
                        }
                        
                        treeList1.addAll(asLi);

                        PerformanceTreeDomain asBean = new PerformanceTreeDomain();
                        asBean.setLocalTableIdRef("CIDC-RT-AS");
                        asBean.setZoneClusterName(getText("CIDC-RT-AS")+"("+asLi.size()+")");
                        asBean.setParentId(id);
                        asBean.setIcon(nodeIcon);
                        treeList1.add(asBean);

                        if (i == 0 && null != asLi && !asLi.isEmpty()) {
                            initDomain1 = asLi.get(0);
                            initId1 = asLi.get(0).getLocalTableIdRef();
                            initType1 = "CIDC-RT-BS";
                        }
                        i++;
                    }
                }


        } catch (Exception e) {
            logger.error("getDeviceTreeNodes error", e);
            forward = ERROR;
        }
        return forward;
    }
    /**
     * 树节点列表
     */
    private List<PerformanceTreeDomain> treeList1 = new ArrayList<PerformanceTreeDomain>();
    /**
     * resourceZoneParentid
     */
    private String resourceZoneParentid;

    public List<PerformanceTreeDomain> getTreeList1() {
        return treeList1;
    }
    public PerformanceTreeDomain getInitDomain1() {
        return initDomain1;
    }
    public void setInitDomain1(PerformanceTreeDomain initDomain1) {
        this.initDomain1 = initDomain1;
    }
    public void setTreeList1(List<PerformanceTreeDomain> treeList1) {
        this.treeList1 = treeList1;
    }
    public String getResourceZoneParentid() {
        return resourceZoneParentid;
    }
    public String getInitId1() {
        return initId1;
    }
    public void setInitId1(String initId1) {
        this.initId1 = initId1;
    }
    public void setResourceZoneParentid(String resourceZoneParentid) {
        this.resourceZoneParentid = resourceZoneParentid;
    }
    /**
     * 树节点列表
     */
    private List<ServiceViewTreeDomain> treeList2 = new ArrayList<ServiceViewTreeDomain>();

    /**
     * 初始化打开节点bean
     */
    private ServiceViewTreeDomain initDomain2 = new ServiceViewTreeDomain();

    /**
     * 初始化打开节点ID
     */
    private String initId2;

    /**
     * 初始化打开节点类型
     */
    private String initType2;
    public String serviceTree() {
        try {
            //定义一个第一个打开的节点
            ServiceViewTreeDomain paraBean = new ServiceViewTreeDomain();
            paraBean.setIcon(leafIcon);
            List < ServiceViewTreeDomain > allLi = ibatisDAO.getData(
                    IBATIS_NAMESPACE + ".getServiceNodes", paraBean);
            if (null != allLi) {
                //定义一个物理机的业务名称集合，用于拼业务名称下的物理机
                Set serviceNameSet = new HashSet();
                Set pmServiceNameSet = new HashSet();
                Set vmServiceNameSet = new HashSet();
                Set bsServiceNameSet = new HashSet();
                Set mmServiceNameSet = new HashSet();
                Set mcServiceNameSet = new HashSet();
                for (int i = 0; i < allLi.size(); i++) {
                    if ("CIDC-RT-SRV".equals(allLi.get(i).getResourceType())) {
                        pmServiceNameSet.add(allLi.get(i).getServiceName());
                        serviceNameSet.add(allLi.get(i).getServiceName());
                        //设置叶子节点的父节点ID
                        allLi.get(i).setParentId("pm" + allLi.get(i).getServiceName());
                        //控制节点颜色
                        PMInfo pminfo = new PMInfo();
                        pminfo.setPmIP(allLi.get(i).getName());
                        String time = getText("resource.rangeTimeSQL");
                        int timeInt = Integer.parseInt(time);
                        pminfo.setTime(timeInt);
                    }
                }
                //拼业务层下面的物理机名称，装到结果集当中
                Iterator pmit = pmServiceNameSet.iterator();
                while (pmit.hasNext()) {
                    String name = (String) pmit.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId("pm" + name);
                    svtd.setParentId(name);
                    svtd.setName(getText("service.Tree.srv"));
                    svtd.setResourceType("SRV");
                    svtd.setIcon(nodeIcon);
                    treeList2.add(svtd);
                }
                Iterator vmit = vmServiceNameSet.iterator();
                while (vmit.hasNext()) {
                    String name = (String) vmit.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId("vm" + name);
                    svtd.setParentId(name);
                    svtd.setName(getText("servive.Tree.vm"));
                    svtd.setResourceType("VM");
                    svtd.setIcon(nodeIcon);
                    treeList2.add(svtd);
                }
                Iterator bsit = bsServiceNameSet.iterator();
                while (bsit.hasNext()) {
                    String name = (String) bsit.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId("bs" + name);
                    svtd.setParentId(name);
                    svtd.setName(getText("CIDC-RT-BS"));
                    svtd.setResourceType("BS");
                    svtd.setIcon(nodeIcon);
                    treeList2.add(svtd);
                }
                Iterator mmit = mmServiceNameSet.iterator();
                while (mmit.hasNext()) {
                    String name = (String) mmit.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId("mm" + name);
                    svtd.setParentId(name);
                    svtd.setName(getText("CIDC-RT-MM"));
                    svtd.setResourceType("MM");
                    svtd.setIcon(nodeIcon);
                    treeList2.add(svtd);
                }
                Iterator mcit = mcServiceNameSet.iterator();
                while (mcit.hasNext()) {
                    String name = (String) mcit.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId("mc" + name);
                    svtd.setParentId(name);
                    svtd.setName(getText("CIDC-RT-MC"));
                    svtd.setResourceType("MC");
                    svtd.setIcon(nodeIcon);
                    treeList2.add(svtd);
                }
                treeList2.addAll(allLi);
                //第三步   所有服务查询结束，开始拼第一层业务名称集合
                List serviceNameLevel = new ArrayList();
                Iterator it = serviceNameSet.iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    ServiceViewTreeDomain svtd = new ServiceViewTreeDomain();
                    svtd.setId(name);
                    svtd.setParentId("0");
                    svtd.setName(name);
                    svtd.setResourceType("");
                    svtd.setIcon(nodeIcon);
                    serviceNameLevel.add(svtd);
                }
                treeList2.addAll(serviceNameLevel);
                //定义初始化打开的节点
                out:
                for (int i = 0;i<treeList2.size();i++) {
                    if ("".equals(treeList2.get(i).getResourceType())) {
                        for (int j = 0;j<treeList2.size();j++) {
                            if (treeList2.get(j).getParentId().equals(treeList2.get(i).getId())) {
                                for (int z = 0;z<treeList2.size();z++) {
                                    if (treeList2.get(z).getParentId().equals(treeList2.get(j).getId())) {
                                        initDomain2 = treeList2.get(z);
                                        initId2 = treeList2.get(z).getId();
                                        initType2 = treeList2.get(z).getResourceType();
                                        break out;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("serviceTree error", e);
            forward = ERROR;
        }
        return forward;
    }

    public List<ServiceViewTreeDomain> getTreeList2() {
        return treeList2;
    }

    public void setTreeList2(List<ServiceViewTreeDomain> treeList2) {
        this.treeList2 = treeList2;
    }

    public ServiceViewTreeDomain getInitDomain2() {
        return initDomain2;
    }

    public void setInitDomain2(ServiceViewTreeDomain initDomain2) {
        this.initDomain2 = initDomain2;
    }

    public String getInitId2() {
        return initId2;
    }

    public void setInitId2(String initId2) {
        this.initId2 = initId2;
    }

    public String getInitType2() {
        return initType2;
    }

    public void setInitType2(String initType2) {
        this.initType2 = initType2;
    }
    public String getInitType1() {
        return initType1;
    }
    public void setInitType1(String initType1) {
        this.initType1 = initType1;
    }
}
