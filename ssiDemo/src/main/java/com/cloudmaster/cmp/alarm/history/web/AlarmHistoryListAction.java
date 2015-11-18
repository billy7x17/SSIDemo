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
package com.cloudmaster.cmp.alarm.history.web;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.history.dao.AlarmHistoryDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.view.dao.ExportExcel;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 历史告警
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmHistoryListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2235626059456084000L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmHistoryListAction.class);

    /**
     * bean对象
     */
    private AlarmHistoryDomain domain;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * 列表
     */
    private List<AlarmHistoryDomain> historyList;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmHistory";

    /**
     * 日志，功能模块名称
     */
    private String functionName;

    /**
     * 日志，操作信息
     */
    private String operationInfo;

    /**
     * 日志，操作类型
     */
    private String opType;

    /**
     * 告警标题列表
     */
    private List<AlarmHistoryDomain> titleList;

    /**
     * 告警设备类型列表
     */
    private List<AlarmHistoryDomain> deviceList;

    /**
     * 告警级别列表
     */
    private List<AlarmHistoryDomain> levelList;

    /**
     * 告警类型列表
     */
    private List<AlarmHistoryDomain> typeList;

    /**
     * 所属站点
     */
    private List<AlarmHistoryDomain> siteList;

    /**
     * 数据导出，输出流变量
     */
    private InputStream excelStream;

    /**
     * 数据导出，下载文件名
     */
    private String excelFileName;

    /**
     * 数据导出，每个sheet页的记录数量
     */
    private int excelSheetCount = 20000;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;

    /**
     * 进入列表页面
     * @return
     */
    public String base() {
        return "basesuccess";
    }

    /**
     * 添加前准备
     * @return
     */
    public String beforeSearch() {
        try {
            logger.info(getText("function.title") + getText("log.beforeSearch.begin"));
            titleList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmTitle", null);
            typeList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmType", null);
            deviceList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getDeviceType", null);
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevel", null);
            
            /*设备类型 国际化*/
            for (AlarmHistoryDomain i : deviceList) 
                i.setDeviceTypeName(getText("device.group." + i.getDeviceTypeName()));
            
            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            // 如果当前用户是中心管理员，页面提供按照站点查询关键字
            if (("1").equals(user.getRoleType())) {
                siteList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getSites", null);
            }
            logger.info(getText("function.title") + getText("log.beforeSearch.end"));
        } catch (SQLException e) {
            logger.info(getText("function.title") + getText("log.beforeSearch.error"), e);
        }
        return forward;
    }

    /**
     * 列表页面
     * @return
     */
    public String list() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));
            domain.setNmsDB(nmsDBName);

            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));
            titleList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmTitle", null);
            typeList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmType", null);
            deviceList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getDeviceType", null);
            levelList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevel", null);
            
            /*设备类型 国际化*/
            for (AlarmHistoryDomain i : deviceList) 
                i.setDeviceTypeName(getText("device.group." + i.getDeviceTypeName()));
            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            // 如果当前用户是中心管理员，页面提供按照站点查询关键字
            if (("1").equals(user.getRoleType())) {
                siteList = ibatisDAO.getData(IBATIS_NAMESPACE + ".getSites", null);
            }

            // 保存sql条件，导出数据时使用
            saveWhereCondition(domain);

            historyList = getPage(IBATIS_NAMESPACE + ".count", IBATIS_NAMESPACE + ".list", domain);
            logger.info(getText("function.title") + getText("log.list.end"));
            operationInfo = getText("oplog.list.success");

            FlexGridJSONData fgjd = new FlexGridJSONData();
            fgjd.setPage(getPage());
            fgjd.setTotal(getTotalCount());
            for (AlarmHistoryDomain ahd : historyList) {
                fgjd.setRowId(ahd.getAlarmID());

                /*
                 * 页面显示顺序为： 告警对象(IP) --> 告警级别 --> 告警标题 --> --> --> 告警分类 --> 告警状态(确认状态) --> 设备类型 -->
                 * 重复次数 --> 最后发生时间 -->
                 */

                /* fgjd.addColdata(ahd.getAlarmID()); *//* 不显示流水号 */
                fgjd.addColdata(ahd.getAgentName());
                fgjd.addColdata(ahd.getAlarmGrade());
                fgjd.addColdata(ahd.getAlarmTitle().trim());
                fgjd.addColdata(ahd.getAlarmType());
                fgjd.addColdata(ahd.getLocalTableIDRef());
                fgjd.addColdata(ahd.getSiteName());
                fgjd.addColdata(ahd.getConfirmStatus());
                fgjd.addColdata(ahd.getClearStatus());
                fgjd.addColdata(getText("device.group." + ahd.getDeviceTypeName()));
                fgjd.addColdata(ahd.getCount());
                fgjd.addColdata(ahd.getAlarmTime());
            }
            fgjd.commitData();

            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
        return null;
    }

    /**
     * 数据导出
     * @throws Exception
     */
    public String export() throws Exception {

        logger.info(getText("function.title") + "导出数据开始");
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            Map map = (Map) session.getAttribute("historyAlarmWhereCondition");
            map.put("nmsDB", nmsDBName);
            List<AlarmHistoryDomain> list = ibatisDAO
                    .getData(IBATIS_NAMESPACE + ".exportList", map);
            if (null == list || list.isEmpty()) {
                list = new ArrayList();
                AlarmHistoryDomain bean = new AlarmHistoryDomain();
                list.add(bean);
            } else {
                for (AlarmHistoryDomain temp : list) {
                    if (("NVR").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.NVR"));
                    } else if (("IP-SAN").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.IP-SAN"));
                    } else if (("Encoder").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.Encoder"));
                    } else if (("IPC").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.IPC"));
                    } else if (("Switch").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.Switch"));
                    } else if (("VMS").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.VMS"));
                    } else if (("D4").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.D4"));
                    } else if (("Keyboard").equals(temp.getDeviceTypeName())) {
                        temp.setDeviceTypeName(getText("device.group.Keyboard"));
                    }
                }
            }

            String[] notExport = new String[] { "alarmID", "eventAlarmId" };

            excelStream = new ExportExcel().exportExcel(excelSheetCount, "history alarm data",
                    list, notExport);
            excelFileName = "historyAlarm.xls";
            return "returnStream";
        } catch (Exception e) {
            logger.info(getText("function.title") + " 导出数据出现异常", e);
            errorMsg = "导出数据出现异常";
            return "ERROR";
        }
    }

    /**
     * 保存查询条件，数据导出时使用
     * @param domain
     */
    private void saveWhereCondition(AlarmHistoryDomain domain) {
        Map map = new HashMap();

        // 获取当前用户信息
        UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
        if (("2").equals(user.getRoleType())) {
            domain.setSiteId(String.valueOf(user.getSiteId()));
            map.put("siteId", "('" + domain.getSiteId() + "')");
        }else{
            if (null != domain.getSiteId() && !domain.getSiteId().equals("")) {
                map.put("siteId", "('" + domain.getSiteId() + "')");
            }
        }

        if (null != domain.getAlarmGrade() && !domain.getAlarmGrade().equals("")) {
            map.put("alarmGrade", "('" + domain.getAlarmGrade() + "')");
        }
        if (null != domain.getDeviceType() && !domain.getDeviceType().equals("")) {
            map.put("deviceType", "('" + domain.getDeviceType() + "')");
        }
        if (null != domain.getAgentName() && !domain.getAgentName().equals("")) {
            map.put("agentName", domain.getAgentName());
        }
        if (null != domain.getAlarmTitle() && !domain.getAlarmTitle().equals("")) {
            map.put("alarmTitle", domain.getAlarmTitle());
        }
        if (null != domain.getAlarmType() && !domain.getAlarmType().equals("")) {
            map.put("alarmType", domain.getAlarmType());
        }
        if (null != domain.getAlarmIdentify() && !domain.getAlarmIdentify().equals("")) {
            map.put("alarmIdentify", domain.getAlarmIdentify());
        }
        if (null != domain.getAlarmID() && !domain.getAlarmID().equals("")) {
            map.put("alarmID", domain.getAlarmID());
        }
        if (null != domain.getConfirmStatus() && !domain.getConfirmStatus().equals("")) {
            map.put("confirmStatus", domain.getConfirmStatus());
        }
        if (null != domain.getSortName() && !domain.getSortName().equals("")) {
            if (domain.getSortName().equals("Alarm_ID")) {
                map.put("sortName", "alarm.Alarm_ID");
            } else if (domain.getSortName().equals("gradeLevel.NAME")) {
                map.put("sortName", "level.name");
            } else {
                map.put("sortName", domain.getSortName());
            }
        }
        if (null != domain.getSortOrder() && !domain.getSortOrder().equals("")) {
            map.put("sortOrder", domain.getSortOrder());
        }
        if (null != domain.getStartTime() && !domain.getStartTime().equals("")) {
            map.put("startTime", domain.getStartTime());
        }
        if (null != domain.getEndTime() && !domain.getEndTime().equals("")) {
            map.put("endTime", domain.getEndTime());
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("historyAlarmWhereCondition", map);

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

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public AlarmHistoryDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmHistoryDomain domain) {
        this.domain = domain;
    }

    public List<AlarmHistoryDomain> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<AlarmHistoryDomain> historyList) {
        this.historyList = historyList;
    }

    public List<AlarmHistoryDomain> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<AlarmHistoryDomain> titleList) {
        this.titleList = titleList;
    }

    public List<AlarmHistoryDomain> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<AlarmHistoryDomain> deviceList) {
        this.deviceList = deviceList;
    }

    public List<AlarmHistoryDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<AlarmHistoryDomain> levelList) {
        this.levelList = levelList;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public int getExcelSheetCount() {
        return excelSheetCount;
    }

    public void setExcelSheetCount(int excelSheetCount) {
        this.excelSheetCount = excelSheetCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    /**
     * @return Returns the typeList.
     */
    public List<AlarmHistoryDomain> getTypeList() {
        return typeList;
    }

    /**
     * @param typeList The typeList to set.
     */
    public void setTypeList(List<AlarmHistoryDomain> typeList) {
        this.typeList = typeList;
    }

    /**
     * @return Returns the siteList.
     */
    public List<AlarmHistoryDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList The siteList to set.
     */
    public void setSiteList(List<AlarmHistoryDomain> siteList) {
        this.siteList = siteList;
    }

}
