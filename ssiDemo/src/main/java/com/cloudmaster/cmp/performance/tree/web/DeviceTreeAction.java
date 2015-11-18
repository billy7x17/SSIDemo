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
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.performance.tree.dao.PerformanceTreeDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 设备视图树
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceTreeAction extends BaseAction {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -27518421000263874L;
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
    private static LogService logger = LogService.getLogger(DeviceTreeAction.class);

    /**
     * ibatis 空间名
     */
    private static final String IBATIS_NAMESPACE = "PerformanceTree";

    /**
     * 连接性能数据库的注入
     */
    private IbatisDAO ibatisDAOCM;

    /**
     *forward
     */
    private String forward = SUCCESS;

    /**
     * 树节点列表
     */
    private List<PerformanceTreeDomain> treeList = new ArrayList<PerformanceTreeDomain>();

    /**
     * 物理机图标
     */
    private String pmIcon = "ico_X86";

    /**
     * 虚拟机图标
     */
    private String vmIcon = "ico_X862";

    /**
     * 小型机图标
     */
    private String mmIcon = "ico_vm01Server";

    /**
     * 小型机分区图标
     */
    private String mcIcon = "ico_vm02Server";

    /**
     * 块图标
     */
    private String bsIcon = "ico_blockMemory";


    /**
     * 交换机图标
     */
    private String swIcon = "ico_Switch";

    /**
     * 防火墙图标
     */
    private String fwIcon = "resource_03";

    /**
     * 路由器图标
     */
    private String routerIcon = "resource_08";

    /**
     * 节点图标
     */
    private String leafIcon = "ico_file";

    /**
     * 叶子图标
     */
    private String nodeIcon = "ico_folder";
    /**
     * resourceZoneParentid
     */
    private String resourceZoneParentid;


    private String flag;

    private String id;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 初始化树的结构
     */
    public String deviceTree() {
        HttpServletResponse resp = ServletActionContext.getResponse();
        UserInfo user = (UserInfo)ServletActionContext.getRequest().getSession().getAttribute("userInfo");
        System.out.println(user.getRoleType());
        resp.setCharacterEncoding("utf-8");
        if (null == flag || flag.equals("")) {
            flag = "rootRT";
            getDeviceTreeRT();// 异步加载树，查询设备资源类型
        } else if (flag.equals("RT")) {
            getDeviceTreeNodes(id);
        }
        return forward;
    }

    /**
     * 初始化树的结构
     */
    @SuppressWarnings("unchecked")
    public void getDeviceTreeRT() {
        try {
            PerformanceTreeDomain zoneParaBean = new PerformanceTreeDomain();
            zoneParaBean.setIcon(nodeIcon);
            zoneParaBean.setZoneClusterId(resourceZoneParentid);
            treeList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getDeviceTree", zoneParaBean);
            List<PerformanceTreeDomain> rtList = ibatisDAO.getData(IBATIS_NAMESPACE
                    + ".getDeviceTreeRT", zoneParaBean);
            if (null != rtList) {
                for (PerformanceTreeDomain domain : rtList) {
                    domain.setLocalTableIdRef(domain.getResourceType());
                    domain.setZoneClusterName(getText(domain.getResourceType()) + "("
                            + domain.getClusterDesc() + ")");
                    domain.setParentId(resourceZoneParentid);
                    treeList.add(domain);
                }
            }
        } catch (Exception ex) {
            logger.info("DeviceTreeAction error", ex);
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getDeviceTreeNodes(String resourceType) {
        try {
            List<PerformanceTreeDomain> resourceNodeList = new ArrayList<PerformanceTreeDomain>();
            if (resourceType.equals("CIDC-RT-D4")) {
                // D4
                PerformanceTreeDomain pmParaBean = new PerformanceTreeDomain();
                pmParaBean.setLocalTableIdRef(id);
                pmParaBean.setResourceType("CIDC-RT-D4");
                pmParaBean.setParentId("CIDC-RT-D4");
                pmParaBean.setIcon(pmIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodes", pmParaBean);
            }
            else if (resourceType.equals("CIDC-RT-IPSAN")) {
                // IPSAN
                PerformanceTreeDomain vmParaBean = new PerformanceTreeDomain();
                vmParaBean.setLocalTableIdRef(id);
                vmParaBean.setResourceType("CIDC-RT-IPSAN");
                vmParaBean.setParentId("CIDC-RT-IPSAN");
                vmParaBean.setIcon(vmIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodes", vmParaBean);
            }
            else if (resourceType.equals("CIDC-RT-IPC")) {
                // IPC
                PerformanceTreeDomain asParaBean = new PerformanceTreeDomain();
                asParaBean.setLocalTableIdRef(id);
                asParaBean.setResourceType("CIDC-RT-IPC");
                asParaBean.setParentId("CIDC-RT-IPC");
                asParaBean.setIcon(pmIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodes", asParaBean);
            }
            else if (resourceType.equals("CIDC-RT-BS")) {
                // 块
                PerformanceTreeDomain bsParaBean = new PerformanceTreeDomain();
                bsParaBean.setLocalTableIdRef(id);
                bsParaBean.setResourceType("CIDC-RT-BS");
                bsParaBean.setParentId("CIDC-RT-BS");
                bsParaBean.setIcon(bsIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodes", bsParaBean);
            }
            else if (resourceType.equals("CIDC-RT-MM")) {
                // 小型机
                PerformanceTreeDomain mmParaBean = new PerformanceTreeDomain();
                mmParaBean.setLocalTableIdRef(id);
                mmParaBean.setResourceType("CIDC-RT-MM");
                mmParaBean.setParentId("CIDC-RT-MM");
                mmParaBean.setIcon(mmIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", mmParaBean);
            }
            else if (resourceType.equals("CIDC-RT-MC")) {
                // 小型机分区
                PerformanceTreeDomain mvmParaBean = new PerformanceTreeDomain();
                mvmParaBean.setLocalTableIdRef(id);
                mvmParaBean.setResourceType("CIDC-RT-MC");
                mvmParaBean.setParentId("CIDC-RT-MC");
                mvmParaBean.setIcon(mcIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", mvmParaBean);
            }
            else if (resourceType.equals("CIDC-RT-SW")) {
                // 交换机
                PerformanceTreeDomain swParaBean = new PerformanceTreeDomain();
                swParaBean.setLocalTableIdRef(id);
                swParaBean.setResourceType("CIDC-RT-SW");
                swParaBean.setParentId("CIDC-RT-SW");
                swParaBean.setIcon(swIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", swParaBean);
            }
            else if (resourceType.equals("CIDC-RT-OS")) {
                // 对象
                PerformanceTreeDomain osParaBean = new PerformanceTreeDomain();
                osParaBean.setLocalTableIdRef(id);
                osParaBean.setResourceType("CIDC-RT-OS");
                osParaBean.setParentId("CIDC-RT-OS");
                osParaBean.setIcon(bsIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodes", osParaBean);
            }
            else if (resourceType.equals("CIDC-RT-FW")) {
                // 防火墙
                PerformanceTreeDomain fwParaBean = new PerformanceTreeDomain();
                fwParaBean.setLocalTableIdRef(id);
                fwParaBean.setResourceType("CIDC-RT-FW");
                fwParaBean.setParentId("CIDC-RT-FW");
                fwParaBean.setIcon(fwIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", fwParaBean);
            }
            else if (resourceType.equals("CIDC-RT-RT")) {
                // 路由器
                PerformanceTreeDomain rtParaBean = new PerformanceTreeDomain();
                rtParaBean.setLocalTableIdRef(id);
                rtParaBean.setResourceType("CIDC-RT-RT");
                rtParaBean.setParentId("CIDC-RT-RT");
                rtParaBean.setIcon(routerIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", rtParaBean);
            }
            else if (resourceType.equals("CIDC-RT-LB")) {
                // 负载均衡器
                PerformanceTreeDomain lbParaBean = new PerformanceTreeDomain();
                lbParaBean.setLocalTableIdRef(id);
                lbParaBean.setResourceType("CIDC-RT-LB");
                lbParaBean.setParentId("CIDC-RT-LB");
                lbParaBean.setIcon(leafIcon);
                resourceNodeList = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceTreeNodesFilter", lbParaBean);
            }
            if (null != resourceNodeList && !resourceNodeList.isEmpty()) {
                //TreeNodeColorUtil tncu = new TreeNodeColorUtil(ping, ibatisDAOCM, ibatisDAO, perTeeeNodeRedSwitch);
                //tncu.ifHaveValue(resourceType, resourceNodeList);   处理性能管理字体颜色问题
                treeList.addAll(resourceNodeList);
            }
        } catch (Exception e) {
            logger.error("getDeviceTreeNodes error", e);
            forward = ERROR;
        }
    }

    public List<PerformanceTreeDomain> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<PerformanceTreeDomain> treeList) {
        this.treeList = treeList;
    }

    public String getResourceZoneParentid() {
        return resourceZoneParentid;
    }

    public IbatisDAO getIbatisDAOCM() {
        return ibatisDAOCM;
    }

    public void setIbatisDAOCM(IbatisDAO ibatisDAOCM) {
        this.ibatisDAOCM = ibatisDAOCM;
    }

    public void setResourceZoneParentid(String resourceZoneParentid) {
        this.resourceZoneParentid = resourceZoneParentid;
    }
}
