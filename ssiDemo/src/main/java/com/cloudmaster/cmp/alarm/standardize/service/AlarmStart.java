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

import java.util.List;

import com.cloudmaster.cmp.alarm.standardize.dao.AlarmDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警采集
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmStart {

    /**
     * 日志前缀
     */
    private String logBegin = "AlarmStandardize.AlarmStart ";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * 数据处理类
     */
    private AlarmDBProc dbProc;

    /**
     * 业务处理类
     */
    private AlarmBusinessProc businessProc;

    /**
     * 
     */
    private String standardizeAlarmEnable;

    /**
     * 入口，周期采集告警
     */
    public void start() {
        if (null != standardizeAlarmEnable && standardizeAlarmEnable.equals("1")) {
//            logger.info(logBegin + "start");
            // 从mals_nm_alarm_device_t中获取告警数据
//            malsDeviceAlarmProc();
            // 从hot_event_tab采集告警数据
//            hotEventAlarmProc();
            // 从vm_hot_event_tab采集告警数据
//            vmHotEventAlarmProc();
            // 从nagios_hoststatus采集告警数据
            // nagiosHostAlarmProc();
            // 从nagios_pmstatus采集告警数据
//            nagiosServiceAlarmProc();
        } else {
//            logger.info(logBegin + " is off,do not collect alarm");
        }
    }

    /**
     * mals_nm_alarm_device_t告警处理
     */
    private void malsDeviceAlarmProc() {
        try {
            List<AlarmDomain> li = dbProc.getMalsDeviceAlarm();
            if (null != li && li.size() > 0) {
                businessProc.alarmProc(li);
            }
        } catch (Exception e) {
            logger.info(logBegin + "malsDeviceAlarmProc error:", e);
        }
    }

    /**
     * hot_event_tab告警处理
     */
    private void hotEventAlarmProc() {
        try {
            List<AlarmDomain> li = dbProc.getHotEventAlarm();
            if (null != li && li.size() > 0) {
                businessProc.alarmProc(li);
            }
        } catch (Exception e) {
            logger.info(logBegin + "hotEventAlarmProc error:", e);
        }
    }

    /**
     * vm_hot_event_tab告警处理
     */
    private void vmHotEventAlarmProc() {
        try {
            List<AlarmDomain> li = dbProc.getVmHotEventAlarm();
            if (null != li && li.size() > 0) {
                businessProc.alarmProc(li);
            }
        } catch (Exception e) {
            logger.info(logBegin + "vmHotEventAlarmProc error:", e);
        }
    }

    /**
     * nagios_hoststatus告警处理
     */
    private void nagiosHostAlarmProc() {
        try {
            List<AlarmDomain> li = dbProc.getNagiosHostAlarm();
            if (null != li && li.size() > 0) {
                businessProc.alarmProc(li);
            }
        } catch (Exception e) {
            logger.info(logBegin + "nagiosHostAlarmProc error:", e);
        }
    }

    /**
     * nagios_servicestatus告警处理
     */
    private void nagiosServiceAlarmProc() {
        try {
            List<AlarmDomain> li = dbProc.getNagiosServiceAlarm();
            if (null != li && li.size() > 0) {
                businessProc.alarmProc(li);
            }
        } catch (Exception e) {
            logger.info(logBegin + "nagiosServiceAlarmProc error:", e);
        }
    }

    public AlarmDBProc getDbProc() {
        return dbProc;
    }

    public void setDbProc(AlarmDBProc dbProc) {
        this.dbProc = dbProc;
    }

    public AlarmBusinessProc getBusinessProc() {
        return businessProc;
    }

    public void setBusinessProc(AlarmBusinessProc businessProc) {
        this.businessProc = businessProc;
    }

    public String getStandardizeAlarmEnable() {
        return standardizeAlarmEnable;
    }

    public void setStandardizeAlarmEnable(String standardizeAlarmEnable) {
        this.standardizeAlarmEnable = standardizeAlarmEnable;
    }

}
