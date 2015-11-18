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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import uk.ltd.getahead.dwr.WebContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * DWR 反向 AJAX 操作
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ReverseNewAlarm {
    // ibatis
    private static final String IBATIS_NAMESPACE = "alarmView";

    // 轮询时间
    private String pollTime;

    // scriptSession是否失效
    private boolean flag = false;

    // DAO
    private IbatisDAO ibatisDAO;

    // 会话集合
    private Set<ScriptSession> scriptSessions = new HashSet<ScriptSession>();

    // 推送消息列表
    private List<AlarmViewDomain> list;

    // log
    private static LogService logger = LogService.getLogger(ReverseNewAlarm.class);

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;
    
    /**
     * 构造函数
     */
    public ReverseNewAlarm() {

    }

    public void handle() throws Exception {
        // System.out.println("setup this .... ");
        while (!flag) {
            Thread.sleep(Integer.valueOf(pollTime));
            getNewAlarmInfo();
        }
    }

    /**
     * DWR3 数据推送
     */
    /*
     * public void getNewAlarmInfo3() { Browser.withCurrentPage(new Runnable() {// 启用监听客户端当前页线程
     * public void run() {// 把数据添加到客户端调用的方法中 try { WebContext webc =
     * uk.ltd.getahead.dwr.WebContextFactory.get(); HttpServletRequest request =
     * webc.getHttpServletRequest(); HttpSession session = request.getSession(); // 获得会话
     * ScriptSession sSession = WebContextFactory.get().getScriptSession(); AlarmViewDomain paraBean
     * = new AlarmViewDomain(); String alarmGrade = (String) session.getAttribute("alarmGrade");
     * String deviceType = (String) session.getAttribute("deviceType");
     * paraBean.setAlarmGrade(alarmGrade); paraBean.setDeviceType(deviceType); String sessionTime =
     * (String) session.getAttribute("alarmNotifyTime"); paraBean.setAlarmTime(sessionTime); list =
     * ibatisDAO.getData(IBATIS_NAMESPACE + ".getNewAlarm", paraBean); int dbcount = (int)
     * ibatisDAO.getCount(IBATIS_NAMESPACE + ".count", paraBean); System.out.println("list.szie = "
     * + list.size()); if (list != null && list.size() > 0) { FlexGridJSONData fgjd = new
     * FlexGridJSONData(); fgjd.setPage(1); fgjd.setTotal(dbcount); for (AlarmViewDomain ahd : list)
     * { fgjd.setRowId(ahd.getAlarmID()); fgjd.addColdata(ahd.getAlarmID());
     * fgjd.addColdata(ahd.getAlarmGrade()); fgjd.addColdata(ahd.getAlarmIP());
     * fgjd.addColdata(ahd.getConfirmStatus()); if
     * (!session.getAttribute("environmentType").equals("3")) {
     * fgjd.addColdata(ahd.getEventAlarmId()); } fgjd.addColdata(ahd.getAlarmTitle());
     * fgjd.addColdata(ahd.getAlarmContent()); fgjd.addColdata(ahd.getDeviceTypeName());
     * fgjd.addColdata(ahd.getAlarmType()); fgjd.addColdata(ahd.getCount());
     * fgjd.addColdata(ahd.getFirstAlarmTime()); fgjd.addColdata(ahd.getAlarmTime());
     * fgjd.addColdata(ahd.getAlarmIdentify()); fgjd.addColdata(ahd.getOriginalAlarmGrade()); }
     * fgjd.commitData(); session.setAttribute("alarmNotifyTime", list.get(0).getAlarmTime());
     * ScriptSessions.addFunctionCall("refreshAlarm", fgjd.toString());//回调函数 } } catch (Exception
     * e) { e.printStackTrace(); flag = true; //如果有异常跳出循环 } } }); }
     */
    /**
     * 获取最新告警信息
     * @throws Exception
     */
    public void getNewAlarmInfo() throws Exception {
        WebContext webc = uk.ltd.getahead.dwr.WebContextFactory.get();
        HttpServletRequest request = webc.getHttpServletRequest();
        HttpSession session = request.getSession();
        // 获得会话
        ScriptSession sSession = WebContextFactory.get().getScriptSession();
        AlarmViewDomain paraBean = new AlarmViewDomain();
        String alarmGrade = (String) session.getAttribute("alarmGrade");
        String deviceType = (String) session.getAttribute("deviceType");
        paraBean.setAlarmGrade(alarmGrade);
        paraBean.setDeviceType(deviceType);
        // 添加会话
        // for (ScriptSession scriptSession : scriptSessions) {
        // System.out.println("scriptSession.getid = " + scriptSession.getId() + " | " +
        // scriptSession.isInvalidated());
        // }
        scriptSessions.add(sSession);
        // if(list == null ) {
        // while (true){
        try {
            // if(sSession.isInvalidated()) break;
            // System.out.println("sSession.id = " + sSession.getId() + " sSession.isInvalidated = "
            // + sSession.isInvalidated());
            String sessionTime = (String) session.getAttribute("alarmNotifyTime");
            paraBean.setAlarmTime(sessionTime);
            paraBean.setNmsDB(nmsDBName);
            list = ibatisDAO.getData(IBATIS_NAMESPACE + ".getNewAlarm", paraBean);
            int dbcount = (int) ibatisDAO.getCount(IBATIS_NAMESPACE + ".count", paraBean);
            System.out.println("list.szie = " + list.size());
            if (list != null && list.size() > 0) {
                FlexGridJSONData fgjd = new FlexGridJSONData();
                fgjd.setPage(1);
                fgjd.setTotal(dbcount);
                for (AlarmViewDomain ahd : list) {
                    fgjd.setRowId(ahd.getAlarmID());
                    fgjd.addColdata(ahd.getAlarmID());
                    fgjd.addColdata(ahd.getAlarmGrade());
                    fgjd.addColdata(ahd.getAlarmIP());
                    fgjd.addColdata(ahd.getConfirmStatus());
                    fgjd.addColdata(ahd.getAlarmTitle());
                    fgjd.addColdata(ahd.getAlarmContent());
                    fgjd.addColdata(ahd.getDeviceTypeName());
                    fgjd.addColdata(ahd.getAlarmType());
                    fgjd.addColdata(ahd.getCount());
                    fgjd.addColdata(ahd.getFirstAlarmTime());
                    fgjd.addColdata(ahd.getAlarmTime());
                    fgjd.addColdata(ahd.getAlarmIdentify());
                    fgjd.addColdata(ahd.getOriginalAlarmGrade());
                }
                fgjd.commitData();
                session.setAttribute("alarmNotifyTime", list.get(0).getAlarmTime());
                Util util = new Util(sSession);
                util.addFunctionCall("refreshAlarm", fgjd.toString());
            }
        } catch (Exception e) {
            logger.error("getNewAlarmInfo error", e);
            flag = true; // 如果有异常跳出循环
        }
        // }
        // }
    }

    public String getPollTime() {
        return pollTime;
    }

    public void setPollTime(String pollTime) {
        this.pollTime = pollTime;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }
}
