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
package com.cloudmaster.cmp.alarm.standardize.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.alarm.autoclear.service.AlarmNotify;
import com.cloudmaster.cmp.alarm.standardize.dao.AlarmDomain;
import com.cloudmaster.cmp.util.DateUtil;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.ResponseObject;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.SenderManager;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.TransportObject;
import com.cloudmaster.cmp.util.threadpool.ThreadPoolModule;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警采集
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmBusinessProc {

    /**
     * 默认告警标识
     */
    private String defaultAlarmIdentify;

    /**
     * 默认告警级别
     */
    private String defaultAlarmGrade;

    /**
     * 默认告警标题
     */
    private String defaultAlarmTitle;

    /**
     * 日志前缀
     */
    private String logBegin = "AlarmStandardize.AlarmBusinessProc ";

    /**
     * 数据处理类
     */
    private AlarmDBProc dbProc;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * 交换机、路由器、负载均衡端口down--1.3.6.1.6.3.1.1.5.3
     * 交换机丢包率超阀值--1.3.6.1.4.1.7564.23.4.1.8.usg.port.loss.pack.rate
     * 交换机误码率超阀值--1.3.6.1.4.1.7564.23.4.1.9.usg.port.error.pack.rate
     */
    String[] str = { "1.3.6.1.6.3.1.1.5.3", "1.3.6.1.4.1.7564.23.4.1.8.usg.port.loss.pack.rate",
            "1.3.6.1.4.1.7564.23.4.1.9.usg.port.error.pack.rate" };

    List<String> specialOidLi = Arrays.asList(str);

    /**
     * 告警通知处理类
     */
    private AlarmNotify alarmNotify;

    /**
     * 上报告警URL
     */
    private String reportAlarmUrl;

    public String getReportAlarmUrl() {
        return reportAlarmUrl;
    }

    public void setReportAlarmUrl(String reportAlarmUrl) {
        this.reportAlarmUrl = reportAlarmUrl;
    }

    /**
     * 处理告警
     * @param list
     */
    public void alarmProc(List<AlarmDomain> paramList) {
        try {
            // 删除重复告警。
            List<AlarmDomain> li = listRepeatProc(paramList);

            // 告警设备不存在则丢弃告警，告警设备工程中则丢弃告警
            List<AlarmDomain> list = deviceCheckProc(li);

            Iterator<AlarmDomain> it = list.iterator();
            while (it.hasNext()) {
                AlarmDomain domain = (AlarmDomain) it.next();
                // 判断是否是清除告警
                AlarmDomain ClearTrapBean = isClearTrap(domain);
                if (null != ClearTrapBean) {
                    clearTrapProc(domain, ClearTrapBean);
                } else {
                    businessProc(domain);
                }
            }
        } catch (Exception e) {
            logger.error(logBegin + " alarmProc error:", e);
        }
    }

    /**
     * 将本次取出的list中的重复记录删除,针对nagios_servicestatus告警 nagios_servicestatus出现ping异常告警，丢弃该设备的其他告警
     * nagios_servicestatus出现NRPE异常告警，丢弃该设备的dischealth告警
     * @param domain
     * @return
     */
    private List<AlarmDomain> listRepeatProc(List<AlarmDomain> paramlist) {

        List<AlarmDomain> list = new ArrayList<AlarmDomain>();
        Map pingMap = new HashMap();
        Map nrpeMap = new HashMap();

        try {
            AlarmDomain bean = (AlarmDomain) paramlist.get(0);
            String sourceType = bean.getAlarmSourceType();
            if (!sourceType.equals("4")) { // 只对nagios_servicestatus中的告警进行处理
                return paramlist;
            }

            Iterator<AlarmDomain> it = paramlist.iterator();
            while (it.hasNext()) {
                AlarmDomain domain = (AlarmDomain) it.next();
                String alarmIdentify = domain.getAlarmIdentify();
                String ip = domain.getAlarmIP();
                // nagios_servicestatus出现ping异常告警，丢弃该设备的其他告警
                if (null != alarmIdentify && alarmIdentify.equalsIgnoreCase("commands_PING")) {
                    pingMap.put(ip, alarmIdentify);
                    list.add(domain);
                }
                // nagios_servicestatus出现NRPE异常告警，丢弃该设备的dischealth告警
                if (null != alarmIdentify && alarmIdentify.equalsIgnoreCase("commands_NRPE")) {
                    nrpeMap.put(ip, alarmIdentify);
                }
            }

            Iterator<AlarmDomain> iter = paramlist.iterator();
            while (iter.hasNext()) {
                AlarmDomain domain = (AlarmDomain) iter.next();
                String ip = domain.getAlarmIP();
                String alarmIdentify = domain.getAlarmIdentify();
                // 没有出现ping告警，
                // 并且commands_dischealth告警，并且没有commands_NRPE告警
                if (!pingMap.containsKey(ip)) {
                    if (null != alarmIdentify && alarmIdentify.equals("commands_DiskHealth")
                            && nrpeMap.containsKey(ip)) {
                        logger.info(logBegin
                                + " nagios service alarm throwAway, NRPE alarm exist,ip:" + ip
                                + ",alarmIdentify:" + alarmIdentify);
                    } else {
                        list.add(domain);
                    }
                } else {
                    logger.info(logBegin + " nagios service alarm throwAway, ping alarm exist,ip:"
                            + ip + ",alarmIdentify:" + alarmIdentify);
                }
            }

        } catch (Exception e) {
            logger.error(logBegin + " listRepeatProc error:", e);
        }
        return list;
    }

    /**
     * nagios告警， 设备不存在则丢弃该告警 工程中则丢弃告警
     * @param domain
     * @return
     */
    private List<AlarmDomain> deviceCheckProc(List<AlarmDomain> paramlist) {

        List<AlarmDomain> list = new ArrayList<AlarmDomain>();
        try {
            Iterator<AlarmDomain> iter = paramlist.iterator();
            while (iter.hasNext()) {
                AlarmDomain domain = (AlarmDomain) iter.next();
                if (dbProc.deviceCheck(domain)) {
                    list.add(domain);
                }
            }
        } catch (Exception e) {
            logger.error(logBegin + "deviceCheckProc error:", e);
        }
        return list;
    }

    /**
     * 告警业务处理
     * @param domain
     */
    private void businessProc(AlarmDomain domain) {

        // 设置默认值
        setDefault(domain);

        // nagios告警处理，获取配置信息
        if (domain.getAlarmSourceType().equals("3") || domain.getAlarmSourceType().equals("4")) {
            AlarmDomain tempDomain = dbProc.getNagiosConfig(domain);
            if (null == tempDomain) {
                logger.info(logBegin + " nagios alarm, config info is null,AlarmIP:"
                        + domain.getAlarmIP() + ",AlarmTime:" + domain.getAlarmTime()
                        + ",AlarmIdentify:" + domain.getAlarmIdentify());
                dbProc.setLastTime(domain); // 修改最后告警时间
                return;
            } else {
                domain.setAlarmTitle(tempDomain.getAlarmTitle());
                domain.setOriginalAlarmGrade(tempDomain.getOriginalAlarmGrade());
                domain.setAlarmType(tempDomain.getAlarmType());
                domain.setThresholdId(tempDomain.getThresholdId()); // 花名册处理时使用，告警入库时置为空
            }
        }

        // // 花名册处理
        // rosterProc(domain);
        // // 筛选器处理
        // boolean filtered = filterProc(domain);
        // if (filtered) {
        // logger.info(logBegin + " alarm is filtered,AlarmIP:" + domain.getAlarmIP()
        // + ",AlarmTime:" + domain.getAlarmTime() + ",AlarmIdentify:"
        // + domain.getAlarmIdentify());
        // recordFilteredAlarm(domain);
        // } else {
        recordAlarm(domain);
        // }
    }

    /**
     * 设置告警默认值
     * @param domain
     */
    private void setDefault(AlarmDomain domain) {

        // 设置AlarmIdentify默认值
        if (null == domain.getAlarmIdentify() || domain.getAlarmIdentify().equals("")) {
            domain.setAlarmIdentify(defaultAlarmIdentify);
        }

        // 设置告警标题默认值
        if (null == domain.getAlarmTitle() || domain.getAlarmTitle().equals("")) {
            domain.setAlarmTitle(defaultAlarmTitle);
        }

        // 设置告警时间默认值
        if (null == domain.getAlarmTime() || domain.getAlarmTime().equals("")) {
            domain.setAlarmTime(DateUtil.getCalendarTime());
        }
        // 设置告警类型默认值
        if (null == domain.getAlarmType()) {
            domain.setAlarmType("");
        }
    }

    /**
     * 判断是否重复告警
     * @param domain
     * @param filtered，0-正常告警，1-过滤掉的告警
     * @return 1-重复告警，2-新告警，3-丢弃不处理（针对nagios告警）
     */
    private String repeatProc(AlarmDomain domain, String filtered) {

        String isRepeat = "2"; // 1-重复告警，2-新告警，3-丢弃不处理（针对nagios告警）
        AlarmDomain bean = null;
        String dbAlarmTime = "";
        if (filtered.equals("0")) { // 正常告警

            if (specialOidLi.contains(domain.getAlarmIdentify())) {
                // 交换机、路由器、负载均衡，端口down的trap告警
                // 交换机丢包率、误码率
                bean = dbProc.getRepeatPartContent(domain);
            } else if (domain.getDeviceType().equals("15")) {
                // 业务系统告警
                bean = dbProc.getRepeatFullContent(domain);
            } else {
                bean = dbProc.getRepeat(domain);
            }
        } else { // 过滤掉的告警
            if (specialOidLi.contains(domain.getAlarmIdentify())) {
                // 交换机、路由器、负载均衡，端口down的trap告警
                // 交换机丢包率、误码率
                bean = dbProc.getFilteredRepeatPartContent(domain);
            } else if (domain.getDeviceType().equals("15")) {
                // 业务系统告警
                bean = dbProc.getFilteredRepeatFullContent(domain);
            } else {
                bean = dbProc.getFilteredRepeat(domain);
            }
        }

        if (bean != null && bean.getAlarmID() != null) {
            dbAlarmTime = bean.getAlarmTime();
            if (dbAlarmTime.compareTo(domain.getAlarmTime()) < 0) {
                domain.setAlarmID(bean.getAlarmID());
                isRepeat = "1";
            } else {
                isRepeat = "3";
            }
        }

        logger.info(logBegin + " repeatProc,AlarmIP:" + domain.getAlarmIP() + ",AlarmTime:"
                + domain.getAlarmTime() + ",AlarmIdentify:" + domain.getAlarmIdentify()
                + ",dbAlarmTime:" + dbAlarmTime + ",AlarmContent:" + domain.getAlarmContent()
                + ",result:" + isRepeat + ",filtered:" + filtered);
        return isRepeat;
    }

    /**
     * 告警花名册处理
     * @param domain
     */
    private void rosterProc(AlarmDomain domain) {
        AlarmDomain rosterBean = dbProc.getRoster(domain);

        if (null == rosterBean) {
            logger.info(logBegin + " roster is null,ThresholdId:" + domain.getThresholdId() + "ip:"
                    + domain.getAlarmIP() + ",AlarmIdentify:" + domain.getAlarmIdentify());
            if (null != domain.getOriginalAlarmGrade()
                    || !domain.getOriginalAlarmGrade().equals("")) {
                domain.setAlarmGrade(domain.getOriginalAlarmGrade());
            } else {
                domain.setAlarmGrade(defaultAlarmGrade);
            }
        } else {
            String rosterId = rosterBean.getRosterId();
            String grade = rosterBean.getAlarmGrade();
            domain.setAlarmGrade(grade);
            domain.setRosterId(rosterId);
        }
    }

    /**
     * 记录告警信息
     * @param domain
     */
    private void recordAlarm(AlarmDomain domain) {
        if(null == domain.getAlarmGrade() || domain.getAlarmGrade().equals("")){
            domain.setAlarmGrade(domain.getOriginalAlarmGrade());
        }
        String repeat = repeatProc(domain, "0"); // 1-重复告警，2-新告警，3-丢弃不处理，针对nagios告警
        if (repeat.equals("1")) {
            boolean isSuccess = dbProc.updateAlarm(domain);
            if (isSuccess) {
                dbProc.setLastTime(domain);
                // 上报告警
                reportAlarm(domain);
            }
        } else if (repeat.equals("2")) {
            boolean isSuccess = dbProc.recordAlarm(domain);
            if (isSuccess) {
                dbProc.setLastTime(domain);

                // 首次告警，向亚信平台发送告警数据
                // sendAlarm2Asia(domain);

                // 新告警通知处理
                putThreadPool(domain);

                // 上报告警
                reportAlarm(domain);
            }
        }
    }

    /**
     * 记录过滤掉的告警信息
     * @param domain
     */
    private void recordFilteredAlarm(AlarmDomain domain) {
        String repeat = repeatProc(domain, "1"); // 1-重复告警，2-新告警，3-丢弃不处理，针对nagios告警
        if (repeat.equals("1")) {
            boolean isSuccess = dbProc.updateFilteredAlarm(domain);
            if (isSuccess) {
                dbProc.setLastTime(domain);
            }
        } else if (repeat.equals("2")) {
            boolean isSuccess = dbProc.recordFilteredAlarm(domain);
            if (isSuccess) {
                dbProc.setLastTime(domain);
            }
        }
    }

    /**
     * 过滤规则处理
     * @param domain
     */
    private boolean filterProc(AlarmDomain domain) {
        boolean filtered = false;
        if (null != domain.getRosterId() && !domain.getRosterId().equals("")) {
            filtered = dbProc.filterExist(domain);
        } else {
            logger.info(logBegin + " alarm RosterId is null,not fileter,AlarmIP:"
                    + domain.getAlarmIP() + ",AlarmTime:" + domain.getAlarmTime());
        }
        return filtered;
    }

    /**
     * 判断是否是清除告警
     * @param domain
     */
    private AlarmDomain isClearTrap(AlarmDomain domain) {
        AlarmDomain clearTrapBean = null;
        String alarmOid = domain.getAlarmIdentify();
        // String deviceType = domain.getDeviceType();
        if (null != alarmOid && !alarmOid.equals("")) {
            clearTrapBean = dbProc.getClearTrapConfig(domain);
        }
        return clearTrapBean;
    }

    /**
     * 清除告警处理
     * @param domain
     */
    private void clearTrapProc(AlarmDomain domain, AlarmDomain clearTrapBean) {
        try {
            String alarmContent = domain.getAlarmContent();
            logger.info(logBegin + "clearTrapProc,ip:" + domain.getAlarmIP() + ",alarmIdentify:"
                    + domain.getAlarmIdentify() + ",deviceType:" + domain.getDeviceType()
                    + ",AlarmContent:" + domain.getAlarmContent());
            if (null != alarmContent && !alarmContent.equals("")) {
                String[] content = alarmContent.trim().split(" ");
                String contentTemp = content[1];
                domain.setAlarmContent(contentTemp);
                domain.setAlarmIdentify(clearTrapBean.getAlarmIdentify());
                dbProc.clearTrap(domain);

                // 设置告警采集最新时间
                dbProc.setLastTime(domain);
            }
        } catch (Exception e) {
            logger.info(logBegin + "clearTrapProc error:", e);
        }
    }

    /**
     * 将任务放入线程池
     * @param bean
     */
    private void putThreadPool(final AlarmDomain bean) {
        ThreadPoolModule.getInstance().getCmpthreadpool().execute(new Runnable() {
            public void run() {
                alarmNotify.newAlarmNotify(bean);
            }
        });
    }

    private void reportAlarm(AlarmDomain domain) {
        SenderManager manager = new SenderManager();
        TransportObject report = new TransportObject();
        // 设置请求消息类型,alarmProcessor表示告警上报
        report.setValue("business", "alarmProcessor");
        // 上报告警的URL
        report.setValue("serverUrl", reportAlarmUrl);
        // 告警管理对象,资源IP
        report.setValue("rid", domain.getAlarmIP());
        // 告警类型
        report.setValue("type", domain.getAlarmType());
        
        String time = domain.getAlarmTime();
        String alarmTime = DateUtil.changedateStr(time, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
        // 告警发生时间，样例数据 2012-5-12 12:21:14
        report.setValue("eventTime", alarmTime);
        // 告警标题
        report.setValue("title", domain.getAlarmTitle());
        // 告警级别 Indeterminate-待定（0）, Critical-严重（1）, Major-主要（2）， Minor-次要（3）, Warning-警告（4），
        report.setValue("lid", domain.getOriginalAlarmGrade());
        // 同一管理对象告警的ID(OID)
        report.setValue("nid", domain.getAlarmIdentify());
        // 告警正文
        report.setValue("text", domain.getAlarmContent());
        // 原始告警级别 取值范围参见告警级别字段
        report.setValue("origin", domain.getOriginalAlarmGrade());
        // 告警设备类型 取值范围mals_nm_agent_type_t表中的TYPE_ID
        report.setValue("deviceType", domain.getDeviceType());
        // 告警的影响
        report.setValue("AlarmImpact", "");
        logger.info("上报告警，serverUrl:" + reportAlarmUrl + ",rid:" + domain.getAlarmIP() + ",type:"
                + domain.getAlarmType() + ",eventTime:" + alarmTime + ",title:"
                + domain.getAlarmTitle() + ",lid:" + domain.getOriginalAlarmGrade() + ",nid:"
                + domain.getAlarmIdentify() + ",origin:" + domain.getOriginalAlarmGrade() + ",deviceType:"
                + domain.getDeviceType());
        ResponseObject response = null;
        try {
            response = manager.send(report,new HashMap());
            String status = response.getStatus();
            if (status.equals("0")) {
                logger.info("上报告警成功");
            } else {
                logger.info("上报告警失败 " + response.getError() + " " + response.getValue("message"));
            }
        } catch (Exception e) {
            logger.error("上报告警出现异常信息", e);
        }
    }

    public String getDefaultAlarmGrade() {
        return defaultAlarmGrade;
    }

    public void setDefaultAlarmGrade(String defaultAlarmGrade) {
        this.defaultAlarmGrade = defaultAlarmGrade;
    }

    public AlarmDBProc getDbProc() {
        return dbProc;
    }

    public void setDbProc(AlarmDBProc dbProc) {
        this.dbProc = dbProc;
    }

    public String getDefaultAlarmIdentify() {
        return defaultAlarmIdentify;
    }

    public void setDefaultAlarmIdentify(String defaultAlarmIdentify) {
        this.defaultAlarmIdentify = defaultAlarmIdentify;
    }

    public String getDefaultAlarmTitle() {
        return defaultAlarmTitle;
    }

    public void setDefaultAlarmTitle(String defaultAlarmTitle) {
        this.defaultAlarmTitle = defaultAlarmTitle;
    }

    public AlarmNotify getAlarmNotify() {
        return alarmNotify;
    }

    public void setAlarmNotify(AlarmNotify alarmNotify) {
        this.alarmNotify = alarmNotify;
    }

}
