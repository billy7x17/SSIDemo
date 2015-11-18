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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewAction extends BaseAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7037112106575281744L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmViewAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView.";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 告警类型列表
     */
    private List<AlarmViewDomain> typeList;

    /**
     * 所属站点
     */
    private List<AlarmViewDomain> siteList;

    private List levelList;

    public String alarmView() {
        HttpServletRequest request = ServletActionContext.getRequest();

        // 页面树选中的查询条件
        HttpSession session = request.getSession();
        session.setAttribute("alarmGrade", domain.getAlarmGrade());
        session.setAttribute("deviceType", domain.getDeviceType());
        try {
            typeList = ibatisDAO.getData(IBATIS_NAMESPACE + "getAlarmType", null);
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + "getAlarmLevel", null);
            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            // 如果当前用户是中心管理员，页面提供按照站点查询关键字
            if (("1").equals(user.getRoleType())) {
                siteList = ibatisDAO.getData(IBATIS_NAMESPACE + "getSites", null);
            }
        } catch (Exception e) {
            typeList = new ArrayList();
            logger.info("alrm view:get alarm type list error", e);
        }
        logger.info("alarm view");
        return SUCCESS;
    }

    public void alarmViewTree() {
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();

            List deviceLi = ibatisDAO.getData(IBATIS_NAMESPACE + "getDeviceType", null);

            String alarmViewTree = "<root>";
            alarmViewTree += "<item id='root_1' parent_id='0' state='open' class='jstree-checked'>";
            alarmViewTree += "<content>";
            alarmViewTree += "<name icon='././././themes/blue/images/tree-icon-file.png'>告警设备类型</name>";
            alarmViewTree += "</content>";
            alarmViewTree += "</item>";

            String deviceTree = getDeviceTree(deviceLi);
            alarmViewTree += deviceTree;

            List levelLi = ibatisDAO.getData(IBATIS_NAMESPACE + "getAlarmLevel", null);

            alarmViewTree += "<item id='root_2' parent_id='0' state='open' class='jstree-checked'>";
            alarmViewTree += "<content>";
            alarmViewTree += "<name icon='././././themes/blue/images/tree-icon-file.png'>告警级别</name>";
            alarmViewTree += "</content>";
            alarmViewTree += "</item>";

            String levelTree = getLevelTree(levelLi, session);
            alarmViewTree += levelTree;

            // alarmViewTree += "<item id='GRADE-5' parent_id='root_2' class='jstree-checked'>";
            // alarmViewTree += "<content>";
            // if (session.getAttribute("environmentType").equals("3")) {
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning1.png'>一级告警</name>";
            // }else{
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning1.png'>紧急告警</name>";
            // }
            // alarmViewTree += "</content>";
            // alarmViewTree += "</item>";
            //
            //
            // alarmViewTree += "<item id='GRADE-4' parent_id='root_2' class='jstree-checked'>";
            // alarmViewTree += "<content>";
            // if (session.getAttribute("environmentType").equals("3")) {
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning2.png'>二级告警</name>";
            // }else{
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning2.png'>重要告警</name>";
            // }
            // alarmViewTree += "</content>";
            // alarmViewTree += "</item>";
            //
            //
            // alarmViewTree += "<item id='GRADE-3' parent_id='root_2' class='jstree-checked'>";
            // alarmViewTree += "<content>";
            // if (session.getAttribute("environmentType").equals("3")) {
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning6.png'>三级告警</name>";
            // }else{
            // alarmViewTree +=
            // "<name icon='././././themes/default/images/ico_Warning6.png'>一般告警</name>";
            // }
            // alarmViewTree += "</content>";
            // alarmViewTree += "</item>";
            //
            //
            // alarmViewTree += "<item id='GRADE-2' parent_id='root_2' class='jstree-checked'>";
            // alarmViewTree += "<content>";
            // if (session.getAttribute("environmentType").equals("3")) {
            // alarmViewTree +=
            // " <name icon='././././themes/default/images/ico_Warning4.png'>四级告警</name>";
            // }else{
            // alarmViewTree +=
            // " <name icon='././././themes/default/images/ico_Warning4.png'>警告告警</name>";
            // }
            // alarmViewTree += " </content>";
            // alarmViewTree += " </item>";

            alarmViewTree += "</root>";

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            PrintWriter pw = response.getWriter();
            logger.debug("alarmViewTree:" + alarmViewTree);
            pw.write(alarmViewTree); // 传递前台
            pw.flush();
            pw.close();

        } catch (Exception e) {
            logger.info("alrm view:get tree error", e);
        }
    }

    private String getDeviceTree(List<AlarmViewDomain> deviceLi) {

        String templateTree = "<item id='TreeID' parent_id='root_1' class='jstree-checked'>";
        templateTree += "<content>";
        templateTree += "<name>TreeName</name>";
        templateTree += " </content>";
        templateTree += "</item>";

        String deviceTree = "";
        for (AlarmViewDomain bean : deviceLi) {
            String treeData = templateTree;
            String typeId = bean.getDeviceType();
            String typeName = bean.getDeviceTypeName();
            String ico = getIco(typeId);
            treeData = treeData.replace("TreeID", typeId);
            treeData = treeData.replace("TreeIco", ico);
            treeData = treeData.replace("TreeName", getText("device.group." + typeName));

            deviceTree += treeData;
        }
        return deviceTree;
    }

    private String getLevelTree(List<AlarmViewDomain> levelLi, HttpSession session) {

        String tempTree = "<item id='GRADE-LEVELVALUE' parent_id='root_2' class='jstree-checked'>";
        tempTree += "<content>";
        tempTree += "<name icon='././././themes/blue/images/ico_Warning4.png'>LEVELNAME</name>";
        tempTree += "</content>";
        tempTree += "</item>";

        String levleTree = "";
        for (AlarmViewDomain bean : levelLi) {
            String treeData = tempTree;
            String levelValue = bean.getAlarmGrade();
            String levelName = bean.getAlarmGradeName();
            treeData = treeData.replace("LEVELVALUE", levelValue);
            treeData = treeData.replace("LEVELNAME", levelName);

            if (levelValue.equals("0")) { // critical(紧急/严重)
                treeData = treeData.replace("ico_Warning4.png", "warn-red-16.png");
            } else if (levelValue.equals("1")) { // major（重要/主要）
                treeData = treeData.replace("ico_Warning4.png", "warn-org-16.png");
            } else if (levelValue.equals("2")) { // minor（一般/次要）
                treeData = treeData.replace("ico_Warning4.png", "warn-yellow-16.png");
            } else if (levelValue.equals("3")) { // normal（警告/提醒）
                treeData = treeData.replace("ico_Warning4.png", "warn-lemon-16.png");
            }

            levleTree += treeData;
        }
        return levleTree;
    }

    private String getIco(String typeId) {
        String ico = "././././themes/default/images/";
        if (typeId.equals("TYPE-1")) { // x86物理机
            ico += "ico_vm01Server.png";
        } else if (typeId.equals("TYPE-2")) {// x86虚拟机
            ico += "ico_X862.png";
        } else if (typeId.equals("TYPE-4")) {// 交换机
            ico += "ico_Switch.png";
        } else if (typeId.equals("TYPE-5")) {// 块存储
            ico += "ico_blockMemory.png";
        } else if (typeId.equals("TYPE-6")) {// IBM虚拟机
            ico += "ico_sPartition.png";
        } else if (typeId.equals("TYPE-7")) {// IBM XIV磁盘阵列
            ico += "ico_IBMXIVDisc.png";
        } else if (typeId.equals("TYPE-8")) {// 日立 VSP磁盘阵列
            ico += "ico_VSPDisc.png";
        } else if (typeId.equals("TYPE-9")) {// 小型机
            ico += "ico_sComputer.png";
        } else if (typeId.equals("TYPE-10")) {// 富士通阵列
            ico += "ico_IBMXIVDisc.png";
        } else if (typeId.equals("TYPE-11")) {// 对象存储
            ico += "ico_blockMemory.png";
        } else if (typeId.equals("TYPE-12")) {// 防火墙
            ico += "resource_03.png";
        } else if (typeId.equals("TYPE-13")) {// 路由器
            ico += "resource_08.png";
        } else if (typeId.equals("TYPE-100")) {// 其他
            ico += "ico_sPartition.png";
        } else {
            ico += "ico_sPartition.png";
        }
        return ico;
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public List<AlarmViewDomain> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AlarmViewDomain> typeList) {
        this.typeList = typeList;
    }

    /**
     * @return the levelList
     */
    public List getLevelList() {
        return levelList;
    }

    /**
     * @param levelList the levelList to set
     */
    public void setLevelList(List levelList) {
        this.levelList = levelList;
    }

    /**
     * @return Returns the siteList.
     */
    public List<AlarmViewDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList The siteList to set.
     */
    public void setSiteList(List<AlarmViewDomain> siteList) {
        this.siteList = siteList;
    }

}
