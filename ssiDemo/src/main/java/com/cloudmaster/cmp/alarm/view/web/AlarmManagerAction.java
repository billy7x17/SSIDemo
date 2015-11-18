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
package com.cloudmaster.cmp.alarm.view.web;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.Authenticater;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmManagerAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3854591781669066989L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmManagerAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    private String alrmConfInitId;

    private String alarmConfInitUrl;

    private String historyPerformInitId;

    private String historyPerformInitUrl;

    private String alarmConfTree;

    private String historyPerformTree;

    // 汪海滨增加拓扑的2级菜单（开始）
    private String topologyTree;

    private String topologyTreeInitId;

    private String topologyTreeInitUrl;

    // 汪海滨增加拓扑的2级菜单（结束）
    /**
     * 节点图标
     */
    private String leafIcon = "themes/blue/images/tree-icon-file.png";

    /**
     * 叶子图标
     */
    private String nodeIcon = "././././themes/blue/images/tree-icon-file.png";

    /**
     * 指定跳转的页面(left.jsp中的页签div的id)
     */
    private String specifiedAction;

    /**
     * 拓扑图索要查看的站点
     */
    private String siteId = null;

    @SuppressWarnings("unchecked")
    public String alarmManager() {
        logger.info("alarm manager,get tree begin");

        UserInfo userInfo = (UserInfo) ActionContext.getContext().getSession()
                .get(SessionKeys.userInfo);

        Integer userSiteId = userInfo.getSiteId();
        try {
            Authenticater authenticater = (Authenticater) ActionContext.getContext().getSession()
                    .get(SessionKeys.authenticater.toString());
            List<String> authIdLis = authenticater.getAuthIdList();

            alarmConfTree = "<root>";
            if (authIdLis.contains("05_10_02_00")) {
                alarmConfTree += "<item id='node_1' action='thresholdList.action'><content><name icon='"
                        + nodeIcon + "'>性能阀值管理</name></content></item>";
            }
            if (authIdLis.contains("05_10_03_00")) {
                alarmConfTree += "<item id='node_2' action='alarmrosterList.action'><content><name icon='"
                        + nodeIcon + "'>规则匹配管理</name></content></item>";
            }
            if (authIdLis.contains("05_10_04_00")) {
                alarmConfTree += "<item id='node_3' action='alarmFilterList.action'><content><name icon='"
                        + nodeIcon + "'>筛选器管理</name></content></item>";
            }
            if (authIdLis.contains("05_10_05_00")) {
                alarmConfTree += "<item id='node_4' action='alarmRegulationBase.action'><content><name icon='"
                        + nodeIcon + "'>告警规则管理</name></content></item>";
            }
            if (authIdLis.contains("05_10_06_00")) {
                alarmConfTree += "<item id='node_5' action='alarmTitleList.action'><content><name icon='"
                        + nodeIcon + "'>告警标题管理</name></content></item>";
            }
            if (authIdLis.contains("05_10_07_00")) {
                alarmConfTree += "<item id='node_6' action='alarmrulesList.action'><content><name icon='"
                        + nodeIcon + "'>设备告警配置</name></content></item>";
            }
            alarmConfTree += "</root>";

            if (authIdLis.contains("05_10_02_00")) {
                alrmConfInitId = "node_1";
                alarmConfInitUrl = "thresholdList.action";
            } else if (authIdLis.contains("05_10_03_00")) {
                alrmConfInitId = "node_2";
                alarmConfInitUrl = "alarmrosterList.action";
            } else if (authIdLis.contains("05_10_04_00")) {
                alrmConfInitId = "node_3";
                alarmConfInitUrl = "alarmFilterList.action";
            } else if (authIdLis.contains("05_10_05_00")) {
                alrmConfInitId = "node_4";
                alarmConfInitUrl = "alarmRegulationBase.action";
            } else if (authIdLis.contains("05_10_06_00")) {
                alrmConfInitId = "node_5";
                alarmConfInitUrl = "alarmTitleList.action";
            } else if (authIdLis.contains("05_10_07_00")) {
                alrmConfInitId = "node_6";
                alarmConfInitUrl = "alarmrulesList.action";
            } else {
                alrmConfInitId = "";
                alarmConfInitUrl = "";
            }

            if (authIdLis.contains("05_11_06_00")) {
                historyPerformInitId = "node_1";
                historyPerformInitUrl = "pmhistoryList.action";
            } else if (authIdLis.contains("05_11_07_00")) {
                historyPerformInitId = "node_2";
                historyPerformInitUrl = "vmhistoryList.action";
            } else if (authIdLis.contains("05_11_08_00")) {
                historyPerformInitId = "node_3";
                historyPerformInitUrl = "performancehistoryList.action";
            } else {
                historyPerformInitId = "";
                historyPerformInitUrl = "";
            }

            // 汪海滨增加拓扑的2级菜单（开始）
            topologyTree = "<root>";
            // 新的
            if (authIdLis.contains("05_15_00_00")) {

                SiteInfo site = new SiteInfo();

                site.setSortName("SITE_ID");

                site.setSortOrder("asc");

                List<SiteInfo> temp = null;
                
                try {
                    if ("1".equals(userInfo.getRoleType())) {
                        temp = ibatisDAO.getData("SiteInfo.getList", site);
                    } else {
                        site.setSiteId(userSiteId);
                        temp = ibatisDAO.getData("SiteInfo.getList", site);
                    }
                } catch (SQLException e) {
                    logger.info("查询站点异常", e);
                    e.printStackTrace();
                }

                for (Iterator<SiteInfo> i = temp.iterator(); i.hasNext();) {
                    SiteInfo siteInfo = (SiteInfo) i.next();
                    topologyTree += "<item id='site_" + siteInfo.getSiteId()
                            + "' action='twaverTopo.action?siteId=" + siteInfo.getSiteId()
                            + "'><content><name icon='" + leafIcon + "'>" + siteInfo.getSiteName()
                            + "</name></content></item>";
                }

            }
            topologyTree += "</root>";
            if (authIdLis.contains("05_15_00_00")) {
                topologyTreeInitId = "site_"
                        + (null == siteId ? String.valueOf(userSiteId) : siteId);
                topologyTreeInitUrl = "twaverTopo.action"
                        + (null == siteId ? "" : "?siteId=" + siteId);
            }
            // 汪海滨增加拓扑的2级菜单（结束）

            logger.info("alarm manager,get tree end");
        } catch (Exception e) {
            logger.info("alarm manager,get tree error", e);
        }
        return "success";
    }

    public String getTopologyTree() {
        return topologyTree;
    }

    public void setTopologyTree(String topologyTree) {
        this.topologyTree = topologyTree;
    }

    public String getTopologyTreeInitId() {
        return topologyTreeInitId;
    }

    public void setTopologyTreeInitId(String topologyTreeInitId) {
        this.topologyTreeInitId = topologyTreeInitId;
    }

    public String getTopologyTreeInitUrl() {
        return topologyTreeInitUrl;
    }

    public void setTopologyTreeInitUrl(String topologyTreeInitUrl) {
        this.topologyTreeInitUrl = topologyTreeInitUrl;
    }

    public String getAlrmConfInitId() {
        return alrmConfInitId;
    }

    public void setAlrmConfInitId(String alrmConfInitId) {
        this.alrmConfInitId = alrmConfInitId;
    }

    public String getAlarmConfInitUrl() {
        return alarmConfInitUrl;
    }

    public void setAlarmConfInitUrl(String alarmConfInitUrl) {
        this.alarmConfInitUrl = alarmConfInitUrl;
    }

    public String getHistoryPerformInitId() {
        return historyPerformInitId;
    }

    public void setHistoryPerformInitId(String historyPerformInitId) {
        this.historyPerformInitId = historyPerformInitId;
    }

    public String getHistoryPerformInitUrl() {
        return historyPerformInitUrl;
    }

    public void setHistoryPerformInitUrl(String historyPerformInitUrl) {
        this.historyPerformInitUrl = historyPerformInitUrl;
    }

    public String getAlarmConfTree() {
        return alarmConfTree;
    }

    public void setAlarmConfTree(String alarmConfTree) {
        this.alarmConfTree = alarmConfTree;
    }

    public String getHistoryPerformTree() {
        return historyPerformTree;
    }

    public void setHistoryPerformTree(String historyPerformTree) {
        this.historyPerformTree = historyPerformTree;
    }

    /**
     * @return the specifiedAction
     */
    public String getSpecifiedAction() {
        return specifiedAction;
    }

    /**
     * @param specifiedAction the specifiedAction to set
     */
    public void setSpecifiedAction(String specifiedAction) {
        this.specifiedAction = specifiedAction;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
