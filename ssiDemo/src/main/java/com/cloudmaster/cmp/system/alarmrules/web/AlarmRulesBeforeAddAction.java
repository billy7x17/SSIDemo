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
package com.cloudmaster.cmp.system.alarmrules.web;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.system.alarmrules.dao.AlarmRulesDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRulesBeforeAddAction extends BaseAction implements IAuthIdGetter {

    /**
     * 
     */
    private static final long serialVersionUID = -4422828931221342914L;

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmRulesList = new ArrayList<AlarmRulesDomain>();

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> LevelList = new ArrayList<AlarmRulesDomain>();

    /**
     * 结果列表
     */
    private List<AlarmRulesDomain> alarmTitleList = new ArrayList<AlarmRulesDomain>();

    /**
     * 告警配置Bean
     */
    private AlarmRulesDomain domain;

    /**
     * 信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "alarmrules";

    /**
     * 设备类型ID，ajax异步获取告警标题
     */
    private String deviceId;

    private static LogService logger = LogService.getLogger(AlarmRulesBeforeAddAction.class);

    /**
     * 告警配置添加前准备
     * @return
     */
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        try {
            alarmRulesList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getTypeID", domain);
            LevelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getLevelName", domain);

            if (alarmRulesList.size() > 0) {
                forward = "SUCCESS";
            } else {
                errorMsg = getText("message.beforeAdd.error");
                forward = "ERROR";
            }
            logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        } catch (SQLException e) {
            logger.error(getText("function.title") + getText("log.beforeAdd.error"), e);
            errorMsg = getText("common.message.addError") + getText("message.sql.error");
            forward = "ERROR";
        }

        return forward;
    }

    /**
     * ajax获取设备下的告警标题
     * @return
     */
    public void getDeviceTitle() {
        try {
            logger.info(getText("function.title") + getText("before.add.function"));

            String titleCondition = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getDeviceTitleCondition", deviceId);

            AlarmRulesDomain titleParam = new AlarmRulesDomain();
            titleParam.setType("(type='1' or type='4')");
            titleParam.setAlarmTitleCondition(titleCondition);
            List<AlarmRulesDomain> deviceTitleLi = ibatisDAO.getData(IBATIS_NAMESPACE
                    + ".getAlarmTitle", titleParam);

            JSONArray titleArr = new JSONArray();
            if (deviceTitleLi == null || deviceTitleLi.size() == 0) {
                errorMsg = this.getText("threshold.before.Mid.action.error");
                logger.info(errorMsg);
            } else {
                for (int i = 0; i < deviceTitleLi.size(); i++) {
                    AlarmRulesDomain titleBean = deviceTitleLi.get(i);
                    JSONObject titleObj = new JSONObject();
                    titleObj.put("tcId", titleBean.getAlarmTitle());
                    titleObj.put("tcTitle", titleBean.getAlarmTitleContent());
                    titleArr.add(titleObj);
                }
            }

            Map<String, JSONArray> jsonMap = new HashMap<String, JSONArray>();
            jsonMap.put("title", titleArr);
            JSONObject jsonObj = JSONObject.fromObject(jsonMap);

            logger.debug("jsonStr:" + jsonObj.toString());
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonObj.toString());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError");
            logger.error(errorMsg, e);
        }
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        AlarmRulesBeforeAddAction.logger = logger;
    }

    public List<AlarmRulesDomain> getAlarmRulesList() {
        return alarmRulesList;
    }

    public void setAlarmRulesList(List<AlarmRulesDomain> alarmRulesList) {
        this.alarmRulesList = alarmRulesList;
    }

    public AlarmRulesDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRulesDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
    }

    public List<AlarmRulesDomain> getLevelList() {
        return LevelList;
    }

    public void setLevelList(List<AlarmRulesDomain> levelList) {
        LevelList = levelList;
    }

    public List<AlarmRulesDomain> getAlarmTitleList() {
        return alarmTitleList;
    }

    public void setAlarmTitleList(List<AlarmRulesDomain> alarmTitleList) {
        this.alarmTitleList = alarmTitleList;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
