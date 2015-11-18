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
package com.cloudmaster.cmp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 登录成功后首页Action
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version 1.0.0 18 Mar 2012 修改人：WRG 修改原因：检查用户虚拟职位是否正确 修改时间：2010-12-31 下午17:29:48
 */
public class EmptyAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(EmptyAction.class);

    private static String isManagers = "1";

    // 是否是专家
    private boolean experts = false;

    private boolean managers = false;

    private int nextLevelPoint;

    private int bLevelPoint;

    private String sites;

    private String alarmGrades;

    private String nmsDBName;

    public String execute() {
        return "SUCCESS";
    }

    /**
     * 进入首页
     * @return
     */
    @SuppressWarnings("unchecked")
    public String firstPage() {
        UserInfo userInfo = (UserInfo) ActionContext.getContext().getSession()
                .get(SessionKeys.userInfo);

        Integer siteId = userInfo.getSiteId();

        SiteInfo site = new SiteInfo();

        site.setSortName("SITE_ID");

        site.setSortOrder("asc");

        List<SiteInfo> temp = null;

        try {
            if (new Integer(1).equals(siteId)) {
                temp = ibatisDAO.getData("SiteInfo.getList", site);
            } else {
                site.setSiteId(siteId);
                temp = ibatisDAO.getData("SiteInfo.getList", site);
            }
        } catch (SQLException e) {
            logger.info("查询站点异常", e);
            e.printStackTrace();
        }

        JSONArray result = JSONArray.fromObject(temp);

        sites = result.toString().replace('"', '\'');

        alarmGrades = alarmGrade(siteId);

        return "SUCCESS";
    }

    /**
     * ajax获取通过站点id获取能看到的站点告警级别
     */
    public void getGradesByAjax() {
        UserInfo userInfo = (UserInfo) ActionContext.getContext().getSession()
                .get(SessionKeys.userInfo);

        Integer siteId = userInfo.getSiteId();

        String result = alarmGrade(siteId);

        HttpServletResponse resp = ServletActionContext.getResponse();

        PrintWriter pw = null;

        try {
            pw = resp.getWriter();

            pw.write(result);
        } catch (IOException e) {
            logger.info("ajax获取站点告警级别io流异常");
            e.printStackTrace();
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * 复用通过站点id获取能看到的站点告警级别
     * @param siteId 站点id
     * @return 告警级别json串
     */
    private String alarmGrade(Integer siteId) {

        Map<String, String> para = new HashMap<String, String>();

        para.put("nmsDB", nmsDBName);

        List<Map<String, String>> grades = null;
        try {
            if (new Integer(1).equals(siteId)) {
                grades = ibatisDAO.getData("TopoGraph.getSiteLatestAlarmGrade", para);
            } else {
                para.put("siteId", String.valueOf(siteId));
                grades = ibatisDAO.getData("TopoGraph.getSiteLatestAlarmGrade", para);
            }
        } catch (SQLException e) {
            logger.info("查询站点异常", e);
            e.printStackTrace();
        }

        JSONArray arr = new JSONArray();

        for (Iterator<Map<String, String>> i = grades.iterator(); i.hasNext();) {
            Map<String, String> s = (Map<String, String>) i.next();
            JSONObject ob = new JSONObject();
            ob.put("grade", s.get("alarmGrade"));
            ob.put("siteId", s.get("siteId"));
            arr.add(ob);
        }

        return arr.toString().replace('"', '\'');
    }

    /**
     * 检验用户虚拟职位
     */
    private void verifyPosition(String userId) {

        // 判断修正用户积分操作是否成功
        boolean flag = false;
        String errmsg = "用户积分出现异常！";

        // try {
        // flag = pointManager.verifyLevelPoint(userTitleInfo, nextLevelPoint, userId);
        // } catch (PointManagerException e) {
        // e.printStackTrace();
        // logger.error(LoginStatusCode.LOGIN_EXCEPTION, errmsg + e.getMessage(), e);
        // }

        // 如果检验用户虚拟职位有错，不能重新查询会造成死循环。
        // 改 为在日志中提示管理员用户积分出现异常，调整数据库数据。
        if (flag) {
            // this.firstPage();
            logger.info(errmsg);
        }

    }

    /**
     * 判断进入模板
     * @return
     */
    /*
     * public String homePage() { UserInfo userInfo = (UserInfo)
     * ActionContext.getContext().getSession().get( SessionKeys.userInfo); // experts =
     * isExpert(userInfo); if (null != userInfo.getUserTemplet() &&
     * userInfo.getUserTemplet().equals(isManagers)) { // 如果是管理人员显示管理人员模板 this.setManagers(true);
     * return "MANAGERSPAGE"; } else { // 默认普通人员模板 return "SUCCESS"; } }
     */

    /**
     * 判断是否专家
     * @param userInfo
     * @return Boolean
     */
    private boolean isExpert(UserInfo userInfo) {
        boolean tmpExpert = false;
        int expertCount = 0;
        // try {
        // // 专家列表中是否有该用户
        // expertCount = ibatisDAO.getCount("expertManager.getExpertCountByUser", userInfo
        // .getUserId());
        // } catch (Exception e) {
        // String errmsg = "判断专家列表是否有 [ " + userInfo.getUserName() + " ] 失败";
        // logger.error(LoginStatusCode.LOGIN_EXCEPTION, errmsg + e.getMessage(), e);
        // }
        // 判断是否专家
        if (expertCount > 0) {
            tmpExpert = true;
        }
        return tmpExpert;
    }

    // 鉴权失败后跳转
    public String noauthority() {
        return "SUCCESS";
    }

    //
    // public IPointManager getPointManager() {
    // return pointManager;
    // }
    //
    // public void setPointManager(IPointManager pointManager) {
    // this.pointManager = pointManager;
    // }

    public boolean isExperts() {
        return experts;
    }

    public void setExperts(boolean experts) {
        this.experts = experts;
    }

    public int getBLevelPoint() {
        return bLevelPoint;
    }

    public void setBLevelPoint(int levelPoint) {
        bLevelPoint = levelPoint;
    }

    public boolean isManagers() {
        return managers;
    }

    public void setManagers(boolean managers) {
        this.managers = managers;
    }

    public int getNextLevelPoint() {
        return nextLevelPoint;
    }

    public void setNextLevelPoint(int nextLevelPoint) {
        this.nextLevelPoint = nextLevelPoint;
    }

    /**
     * @return the sites
     */
    public String getSites() {
        return sites;
    }

    /**
     * @param sites the sites to set
     */
    public void setSites(String sites) {
        this.sites = sites;
    }

    /**
     * @return the nmsDBName
     */
    public String getNmsDBName() {
        return nmsDBName;
    }

    /**
     * @param nmsDBName the nmsDBName to set
     */
    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    /**
     * @return the alarmGrades
     */
    public String getAlarmGrades() {
        return alarmGrades;
    }

    /**
     * @param alarmGrades the alarmGrades to set
     */
    public void setAlarmGrades(String alarmGrades) {
        this.alarmGrades = alarmGrades;
    }

}
