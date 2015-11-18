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
package com.cloudmaster.cmp.alarm.autoclear.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.cloudmaster.cmp.alarm.autoclear.dao.AutoClearAlarmDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.AlarmInfoBean;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.AlarmReport;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 自动清除
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AutoClearAlarmJob extends BaseJob {
    /**
     * 日志前缀
     */
    private String logBegin = "AUTO CLEAR ALARM,";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * njdb数据库dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * 研发中心数据库dao
     */
    private IbatisDAO ibatisDAORDC;

    /**
     * njdb数据库ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "AutoClearAlarm.";

    /**
     * 研发中心数据ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE_RDC = "AutoClearAlarmRDC.";

    /**
     * 自动清除告警的时间间隔，单位分钟
     */
    private String intervalTime;

    /**
     * 每次处理的阀值Id的数量
     */
    private int perSize = 50;

    /**
     * 上报告警服务 private ResSendAlarDataServicePortType asiaClient;
     */

    /**
     * 通断性告警
     */
    private String paraAlarmType = "通断性";

    /**
     * 告警通知处理类 private AlarmNotify alarmNotify;
     */

    /**
     * 告警数据库名称
     */
    private String nmsDBName;

    /**
     * 入口，自动清除性能告警
     */
    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        logger.info(logBegin + "start");
        String jobresult = "success";
        String joblog = "";

        autoClear();

        context.setResult(jobresult);// context.setResult("success")表示任务执行成功；其他则失败
        if (joblog.equals("")) {
            context.put("log", "自动清除告警结束");
        } else {
            context.put("log", joblog);
        }
        logger.info(logBegin + "end");
    }

    /**
     * 自动清除告警
     */
    public void autoClear() {
        // 性能阀值告警，自动清除
        try {
            logger.info(logBegin + "自动清除性能阀值告警");
            performanceAlamrClear();
        } catch (Exception e) {
            logger.info(logBegin + "自动清除性能阀值告警出现异常", e);
        }

        // nagios的ping不通，自动清除
        try {
            logger.info(logBegin + "自动清除nagio-ping告警");
            AutoClearAlarmDomain paraBean = new AutoClearAlarmDomain();
            paraBean.setClearIntervalTime(intervalTime);
            paraBean.setNmsDBName(nmsDBName);
            List<AutoClearAlarmDomain> nagiosAlarmLi = ibatisDAORDC.getData(IBATIS_NAMESPACE_RDC
                    + "getNagiosClearAlarm", paraBean);
            cearAlarm(nagiosAlarmLi);
        } catch (Exception e) {
            logger.info(logBegin + " 自动清除nagios告警出现异常", e);
        }

        /**
         * 通断性告警，自动清除 try { AutoClearAlarmDomain paraBean = new AutoClearAlarmDomain();
         * paraBean.setClearIntervalTime(intervalTime); paraBean.setAlarmType(paraAlarmType); List
         * disconnectedAlarmLi = ibatisDAO.getData(IBATIS_NAMESPACE + "getDisconnectedAlarm",
         * paraBean); cearAlarm(disconnectedAlarmLi); } catch (Exception e) { logger.info(logBegin +
         * " 自动清除通断性告警出现异常", e); }
         */

        /**
         * 业务系统告警，自动清除 try { AutoClearAlarmDomain paraBean = new AutoClearAlarmDomain();
         * paraBean.setClearIntervalTime(intervalTime); List businessAlarmLi =
         * ibatisDAO.getData(IBATIS_NAMESPACE + "getBusinessAlarm", paraBean);
         * cearAlarm(businessAlarmLi); } catch (Exception e) { logger.info(logBegin +
         * " 自动清除业务告警出现异常", e); }
         */
    }

    /**
     * 性能告警处理
     * @throws Exception
     */
    private void performanceAlamrClear() throws Exception {
        AutoClearAlarmDomain paraBean = new AutoClearAlarmDomain();

        List<AutoClearAlarmDomain> thresholdLi = ibatisDAO.getData(IBATIS_NAMESPACE
                + "getThreshold", null);
        if (null != thresholdLi && !thresholdLi.isEmpty()) {
            List divideLi = divide(thresholdLi, perSize); // 列表分割，防止列表过大，影响系统运行
            for (int i = 0; i < divideLi.size(); i++) {
                List<AutoClearAlarmDomain> li = (List) divideLi.get(i);

                paraBean.setClearIntervalTime(intervalTime);
                paraBean.setNmsDBName(nmsDBName);
                paraBean.setThreshodLi(li);
                List<AutoClearAlarmDomain> alarmLi = ibatisDAORDC.getData(IBATIS_NAMESPACE_RDC
                        + "getPerformanceClearAlarm", paraBean);
                cearAlarm(alarmLi);
            }
        } else {
            logger.info(logBegin + "阀值信息为空，不需要清除性能阀值告警数据");
        }
    }

    /**
     * 清除告警，
     * @param divideLi
     * @throws Exception
     */
    private void cearAlarm(List<AutoClearAlarmDomain> alarmLi) throws Exception {
        if (null != alarmLi && !alarmLi.isEmpty()) {
            logger.info(logBegin + "需要自动清除的告警数量:" + alarmLi.size());
            List divideLi = divide(alarmLi, perSize); // 列表分割，防止列表过大，影响系统运行
            for (int i = 0; i < divideLi.size(); i++) {
                List<AutoClearAlarmDomain> li = (List) divideLi.get(i);
                for (AutoClearAlarmDomain bean : li) {
                    logger.info(logBegin + "需要自动清除的告警ID:" + bean.getAlarmID() + ",IP:"
                            + bean.getAlarmIP());
                    AlarmInfoBean reportBean = new AlarmInfoBean();
                    reportBean.setAgentIp(bean.getAlarmIP());
                    reportBean.setAlarmType(bean.getAlarmType());
                    reportBean.setAlarmTitle(bean.getAlarmTitle());
                    reportBean.setAlarmLevel("5"); // 清除告警
                    reportBean.setAlarmLevelOriginal(bean.getAlarmGrade()); // 原始告警级别
                    // 接收时OID
                    reportBean.setAlarmOid(bean.getAlarmOID());
                    reportBean.setAlarmContent(bean.getAlarmContent());
                    reportBean.setAgentType(bean.getDeviceType());
                    reportBean.setAlarmImpact(bean.getAlarmImpact());
                    // 上报OID
                    reportBean.setAlarmIdentityID(bean.getAlarmReportOID());
                    reportBean.setAlarmTitleId(bean.getAlarmTitleId());
                    reportBean.setObjectID(bean.getDeviceId());
                    reportBean.setSystemName(bean.getDeviceName());

                    AlarmReport alarmReport = new AlarmReport();
                    alarmReport.reportAlarm(reportBean);
                }

            }
        } else {
            logger.info(logBegin + "没有需要自动清除的告警");
        }
    }

    /**
     * 亚信告警上报接口，清除告警，向亚信平台发送告警信息
     */
    // private void sendAlarm2Asia(AutoClearAlarmDomain bean) {
    // try {
    // List deviceLi = ibatisDAO.getData(IBATIS_NAMESPACE + "getDeviceInfo", bean);
    // if (null != deviceLi && !deviceLi.isEmpty()) {
    // for (int i = 0; i < deviceLi.size(); i++) {
    // AutoClearAlarmDomain alarm = (AutoClearAlarmDomain) deviceLi.get(i);
    // SendAlarmDataReq req = BuildAlarmData.aotuoClearBuildData(alarm);
    // asiaClient.sendAlarmData(req);
    // }
    // }
    // } catch (Exception e) {
    // logger.error(logBegin + " send alarm data to asia eror:", e);
    // }
    // }
    /**
     * 清除告警，发送通知消息
     */
    // private void clearNotify(AutoClearAlarmDomain bean) {
    // try {
    // List<AutoClearAlarmDomain> alarmLi = ibatisDAO.getData(IBATIS_NAMESPACE + "getAlarm",
    // bean);
    // if (null != alarmLi && !alarmLi.isEmpty()) {
    // for (AutoClearAlarmDomain alarmBean : alarmLi) {
    // String ruleId = alarmBean.getNewAlarmNotifyRule();
    // if (null == ruleId || ruleId.equals("")) {
    // return;
    // }
    // putThreadPool(alarmBean);
    // }
    // }
    // } catch (Exception e) {
    // logger.error(logBegin + " send alarm data to asia eror:", e);
    // }
    // }
    /**
     * 列表分割，防止列表庞大出现性能问题
     * @param li
     * @param perSize，分割后每个列表的size大小
     * @return
     */
    private List divide(List li, int perSize) {
        List newList = new ArrayList();

        int size = li.size();
        int count = (int) Math.floor(size / perSize);
        int a = size % perSize;
        if (a == 0) { // 完美分割
            for (int i = 0; i < count; i++) {
                List divideLi = li.subList(i * perSize, (i + 1) * perSize);
                newList.add(divideLi);
            }
        } else {
            for (int j = 0; j < count; j++) {
                List divideLi = li.subList(j * perSize, (j + 1) * perSize);
                newList.add(divideLi);
            }
            List divideLi = li.subList(count * perSize, count * perSize + a);
            newList.add(divideLi);
        }
        return newList;
    }

    /**
     * 将任务放入线程池
     * @param bean
     */
    // private void putThreadPool(final AutoClearAlarmDomain bean) {
    // ThreadPoolModule.getInstance().getCmpthreadpool().execute(new Runnable() {
    // public void run() {
    // alarmNotify.autoClearAlarmNotify(bean);
    // }
    // });
    // }
    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public IbatisDAO getIbatisDAORDC() {
        return ibatisDAORDC;
    }

    public void setIbatisDAORDC(IbatisDAO ibatisDAORDC) {
        this.ibatisDAORDC = ibatisDAORDC;
    }

}
