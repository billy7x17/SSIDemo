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

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.view.dao.ExportExcel;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 84449883480496098L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmViewListAction.class);

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

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

    public void alarmList() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        // 增加排序操作
        domain.setSortName(request.getParameter("sortname"));
        domain.setSortOrder(request.getParameter("sortorder"));
        String rp = request.getParameter("rp");
        setPageSize(Integer.parseInt(rp));

        // 页面树选中的查询条件

        String alarmGrade = (String) session.getAttribute("alarmGrade");
        String deviceType = (String) session.getAttribute("deviceType");
        if (null != domain.getAlarmGrade() && !"".equals(domain.getAlarmGrade())) {
            domain.setAlarmGrade("(" + domain.getAlarmGrade() + ")");
        } else {
            domain.setAlarmGrade(alarmGrade);
        }
        domain.setDeviceType(deviceType);

        // 保存sql条件，导出数据时使用
        saveWhereCondition(domain);
        try {
            domain.setNmsDB(nmsDBName);
            List<AlarmViewDomain> list = getPage(IBATIS_NAMESPACE + ".count", IBATIS_NAMESPACE
                    + ".list", domain);
            String maxTime = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".maxAlatmTime",
                    domain); // 记录最后时间，告警提示使用
            FlexGridJSONData fgjd = new FlexGridJSONData();
            fgjd.setPage(getPage());
            fgjd.setTotal(getTotalCount());
            for (AlarmViewDomain ahd : list) {
                fgjd.setRowId(ahd.getAlarmID());
                /* fgjd.addColdata(ahd.getAlarmID()); *//* 不显示告警流水号 */
                fgjd.addColdata("");                    //增加复选框
                fgjd.addColdata(ahd.getAgentName());
                fgjd.addColdata(ahd.getAlarmGrade());
                fgjd.addColdata(ahd.getAlarmTitle().trim());
                // fgjd.addColdata(ahd.getAlarmContent().trim());
                fgjd.addColdata(ahd.getFirstAlarmTime());
                fgjd.addColdata(ahd.getAlarmType());
                fgjd.addColdata(ahd.getLocalTableIDRef());
                fgjd.addColdata(ahd.getSiteName());
                fgjd.addColdata(ahd.getConfirmStatus());
                fgjd.addColdata(getText("device.group." + ahd.getDeviceTypeName()));
                fgjd.addColdata(ahd.getCount());
                fgjd.addColdata(ahd.getAlarmTime());
                fgjd.addColdata(ahd.getAlarmIdentify());
            }
            fgjd.commitData();
            Integer count = (Integer) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".clearedCount", domain);
            
            // session中记录最大时间、总数量，告警提示使用
            session.setAttribute("alarmNotifyTime", maxTime);
            session.setAttribute("alarmNotifyCount", String.valueOf(getTotalCount()));
            session.setAttribute("clearCount", count);  /* 此时的已被清除告警的数量 */

            // 页面查询时，查询条件中有新告警才提示
            if (null == domain.getAgentName()) {
                session.removeAttribute("agentName");
            } else {
                session.setAttribute("agentName", domain.getAgentName());
            }
            if (null == domain.getSiteName()) {
                session.removeAttribute("siteName");
            } else {
                session.setAttribute("siteName", domain.getSiteName());
            }
            if (null == domain.getAlarmTitle()) {
                session.removeAttribute("alarmTitle");
            } else {
                session.setAttribute("alarmTitle", domain.getAlarmTitle());
            }
            if (null == domain.getAlarmType()) {
                session.removeAttribute("alarmType");
            } else {
                session.setAttribute("alarmType", domain.getAlarmType());
            }
            if (null == domain.getAlarmIdentify()) {
                session.removeAttribute("alarmIdentify");
            } else {
                session.setAttribute("alarmIdentify", domain.getAlarmIdentify());
            }
            if (null == domain.getAlarmID()) {
                session.removeAttribute("alarmID");
            } else {
                session.setAttribute("alarmID", domain.getAlarmID());
            }
            if (null == domain.getConfirmStatus()) {
                session.removeAttribute("confirmStatus");
            } else {
                session.setAttribute("confirmStatus", domain.getConfirmStatus());
            }

            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString());
            pw.flush();
            pw.close();

            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.list.error"), e);
        }
    }

    /**
     * 实时告警页面，告警提醒 页面右下角，告警提醒
     */
    public void getalarmNotify() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            // 实时告警列表页面提醒，进入页面时保存属性值
            String sessionTime = "";
            if (null != session.getAttribute("alarmNotifyTime")
                    && !session.getAttribute("alarmNotifyTime").equals("")) {
                sessionTime = (String) session.getAttribute("alarmNotifyTime");
            }
            int sessionCount = 0;
            if (null != session.getAttribute("alarmNotifyCount")
                    && !session.getAttribute("alarmNotifyCount").equals("")) {
                sessionCount = Integer.valueOf((String) session.getAttribute("alarmNotifyCount"));
            }

            // 页面右下角提醒，登录时值为空，每提醒一次都更新该值
            String sysNotifyTime = "";
            if (null != session.getAttribute("sysNotifyTime")
                    && !session.getAttribute("sysNotifyTime").equals("")) {
                sysNotifyTime = (String) session.getAttribute("sysNotifyTime");
            }
            int sysNotifyCount = 0;
            if (null != session.getAttribute("sysNotifyCount")
                    && !session.getAttribute("sysNotifyCount").equals("")) {
                sysNotifyCount = Integer.valueOf((String) session.getAttribute("sysNotifyCount"));
            }

            AlarmViewDomain paraBean = new AlarmViewDomain();
            paraBean.setNmsDB(nmsDBName);
            
            /*站点判定*/
            UserInfo sessionUser = (UserInfo) session.getAttribute(SessionKeys.userInfo.toString());
            
            if (!new Integer(1).equals(sessionUser.getSiteId())) 
                paraBean.setSiteId(String.valueOf(sessionUser.getSiteId()));
            
            if (domain.getQueryType().equals("realTimeAlarmPage")) { // 实时告警页面
                String alarmGrade = (String) session.getAttribute("alarmGrade");
                String deviceType = (String) session.getAttribute("deviceType");
                paraBean.setAlarmGrade(alarmGrade);
                paraBean.setDeviceType(deviceType);

                // 页面查询时，查询条件中有新告警才提示
                if (null != session.getAttribute("agentName")) {
                    paraBean.setAgentName((String) session.getAttribute("agentName"));
                }
                if (null != session.getAttribute("siteName")) {
                    paraBean.setSiteName((String) session.getAttribute("siteName"));
                }
                if (null != session.getAttribute("alarmTitle")) {
                    paraBean.setAlarmTitle((String) session.getAttribute("alarmTitle"));
                }
                if (null != session.getAttribute("alarmType")) {
                    paraBean.setAlarmType((String) session.getAttribute("alarmType"));
                }
                if (null != session.getAttribute("alarmIdentify")) {
                    paraBean.setAlarmIdentify((String) session.getAttribute("alarmIdentify"));
                }

                if (null != session.getAttribute("alarmID")) {
                    paraBean.setAlarmID((String) session.getAttribute("alarmID"));
                }
                if (null != session.getAttribute("confirmStatus")) {
                    paraBean.setConfirmStatus((String) session.getAttribute("confirmStatus"));
                }
            } else { // 页面右下角
                List<AlarmViewDomain> deviceLi = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getDeviceType", null);
                List<AlarmViewDomain> levelLi = ibatisDAO.getData(IBATIS_NAMESPACE
                        + ".getAlarmLevel", null);
                String device = "('";
                if (null != deviceLi && !deviceLi.isEmpty()) {
                    for (AlarmViewDomain bean : deviceLi) {
                        String deviceValue = bean.getDeviceType();
                        device = device + deviceValue.replace("TYPE-", "") + "','";
                    }
                }
                device = device + "')";
                String grade = "('";
                if (null != levelLi && !levelLi.isEmpty()) {
                    for (AlarmViewDomain bean : levelLi) {
                        String levelValue = bean.getAlarmGrade();
                        grade = grade + levelValue + "','";
                    }
                }
                grade = grade + "')";
                paraBean.setAlarmGrade(grade);
                paraBean.setDeviceType(device);
            }
            String dbTime = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".maxAlatmTime",
                    paraBean);
            int dbcount = (int) ibatisDAO.getCount(IBATIS_NAMESPACE + ".count", paraBean);
            Integer clCount = (Integer) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".clearedCount", paraBean);
            
            Integer cleared = clCount.compareTo((Integer)session.getAttribute("clearCount"));
            
            String resultType = "1"; // 1-无新告警，2-出现新告警，3-出现新的重复告警
            if (domain.getQueryType().equals("realTimeAlarmPage")) { // 实时告警
                if (0 != dbcount && dbcount > sessionCount) {
                    resultType = "2";
                } else if (null != dbTime && dbTime.compareTo(sessionTime) > 0) {
                    resultType = "3";
                }
            } else { // 有下角告警提示
                if (0 != dbcount && 0 != sysNotifyCount && dbcount > sysNotifyCount) {
                    resultType = "2";
                } else if (null != dbTime && dbTime.compareTo(sysNotifyTime) > 0) {
                    resultType = "3";
                }
                session.setAttribute("sysNotifyTime", dbTime);
                session.setAttribute("sysNotifyCount", String.valueOf(dbcount));
                
                if (!new Integer(0).equals(cleared)) {
                    session.setAttribute("clearCount", clCount);
                }
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("clearCount", cleared);
            resultMap.put("notifyType", resultType);
            resultMap.put("notifyTime", dbTime);
            resultMap.put("notifyCount", dbcount);
            JSONObject jsonObj = JSONObject.fromObject(resultMap); // 格式化result 一定要是JSONObject
            String jsonStr = jsonObj.toString();
            logger.debug("alarm json:" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title") + " get new alarm notify error", e);
        }
    }

    private String processQuery(String queryType, String queryValue) {
        String newQueryValue = queryValue;
        if (queryType.equals("Alarm_Grade")) {
            if (queryValue.contains("警告")) {
                newQueryValue = "2";
            } else if (queryValue.contains("一般")) {
                newQueryValue = "3";
            } else if (queryValue.contains("重要")) {
                newQueryValue = "4";
            } else if (queryValue.contains("紧急")) {
                newQueryValue = "5";
            } else {
                newQueryValue = "nodata match query value";
            }
        } else if (queryType.equals("confirm_status")) {
            if (queryValue.contains("已确认")) {
                newQueryValue = "2";
            } else if (queryValue.contains("未确认")) {
                newQueryValue = "1";
            } else {
                newQueryValue = "nodata match query value";
            }
        }
        newQueryValue = SqlUtil.specialToNormal(newQueryValue);
        return newQueryValue;
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
            Map map = (Map) session.getAttribute("realTimeAlarmWhereCondition");
            map.put("nmsDB", nmsDBName);
            List<AlarmViewDomain> list = ibatisDAO.getData(IBATIS_NAMESPACE + ".exportList", map);
            if (null == list || list.isEmpty()) {
                list = new ArrayList();
                AlarmViewDomain bean = new AlarmViewDomain();
                list.add(bean);
            } else {
                for (AlarmViewDomain temp : list) {
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
            String[] notExport = new String[] { "alarmID", "eventAlarmId", "clearStatus",
                    "originalAlarmGrade" };

            excelStream = new ExportExcel().exportExcel(excelSheetCount, "realTime alarm data",
                    list, notExport);
            excelFileName = "realTimeAlarm.xls";
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
    private void saveWhereCondition(AlarmViewDomain domain) {
        Map map = new HashMap();

        // 获取当前用户信息
        UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
        if (("2").equals(user.getRoleType())) {
            domain.setSiteId(String.valueOf(user.getSiteId()));
            map.put("siteId", domain.getSiteId());
        } else {
            if (null != domain.getSiteId() && !domain.getSiteId().equals("")) {
                map.put("siteId", domain.getSiteId());
            }
        }

        if (null != domain.getAlarmGrade() && !domain.getAlarmGrade().equals("")) {
            map.put("alarmGrade", domain.getAlarmGrade());
        }
        if (null != domain.getDeviceType() && !domain.getDeviceType().equals("")) {
            map.put("deviceType", domain.getDeviceType());
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
            map.put("sortName", domain.getSortName());
        }
        if (null != domain.getSortOrder() && !domain.getSortOrder().equals("")) {
            map.put("sortOrder", domain.getSortOrder());
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("realTimeAlarmWhereCondition", map);

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

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }
}
