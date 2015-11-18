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
package com.cloudmaster.cmp.util.AlarmSystem.transfer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.greenpineyu.fel.common.StringUtils;

/**
 * @author <a href="mailto:shen.di@neusoft.com"> shendi </a>
 * @version $Revision 1.1 $ 2013-5-30 下午2:55:46
 */
public class AlarmReport {
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(AlarmReport.class);

    /**
     * 告警上报处理
     * @param alarmInfoBean
     * @throws Exception
     */
    public void reportAlarm(AlarmInfoBean alarmInfoBean) throws Exception {

        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            // 读取System.properties
            prop.load(classLoader.getResourceAsStream("conf/other/Alarm.properties"));
            TransportObject report = new TransportObject();
            // 设置请求消息类型,固定代码
            report.setValue("business", "alarmProcessor");
            report.setValue("res", "1");
            // 上报告警的URL
            String reportAlarmUrl = prop.getProperty("reportAlarmUrl");
            report.setValue("serverUrl", reportAlarmUrl);
            // 告警管理对象,资源IP
            report.setValue("rid", alarmInfoBean.getAgentIp());
            // 告警类型
            report.setValue("type", alarmInfoBean.getAlarmType());
            // 告警时间
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            report.setValue("eventTime", time); // 时间
            // 告警标题
            report.setValue("title", alarmInfoBean.getAlarmTitle());
            // 告警级别 Indeterminate-待定（0）, Critical-严重（1）, Major-主要（2）， Minor-次要（3）,
            // Warning-警告（4），
            report.setValue("lid", alarmInfoBean.getAlarmLevel());
            // 同一管理对象告警的ID(OID)
            report.setValue("nid", alarmInfoBean.getAlarmOid());
            // 告警正文
            report.setValue("text", alarmInfoBean.getAlarmContent());
            // 原始告警级别 取值范围参见告警级别字段
            if (StringUtils.isEmpty(alarmInfoBean.getAlarmLevelOriginal())) {
                report.setValue("original", alarmInfoBean.getAlarmLevel());
            } else {
                report.setValue("original", alarmInfoBean.getAlarmLevelOriginal());
            }

            // 告警设备类型 取值范围mals_nm_agent_type_t表中的TYPE_ID
            report.setValue("deviceType", alarmInfoBean.getAgentType());
            // 告警的影响
            report.setValue("AlarmImpact", alarmInfoBean.getAlarmImpact());
            // 告警的标准OID
            String alarmIdentityID = alarmInfoBean.getAlarmIdentityID();
            if (null == alarmIdentityID || alarmIdentityID.equals("")) {
                report.setValue("oid", prop.getProperty("AlarmIdentityID"));
            } else {
                report.setValue("oid", alarmInfoBean.getAlarmIdentityID());
            }

            // 告警标题ID
            if ((null == alarmInfoBean.getAlarmTitleId() || alarmInfoBean.getAlarmTitleId().equals(
                    ""))) {
                report.setValue("alarmTitleID", prop.getProperty("default.alarmTitleId"));
            } else {
                report.setValue("alarmTitleID", alarmInfoBean.getAlarmTitleId());
            }
            // 发生告警的资源实例ID 业务告警时书写业务系统标识ID
            if (null == alarmInfoBean.getObjectID() || alarmInfoBean.getObjectID().equals("")) {
                report.setValue("objectID", prop.getProperty("default.objectID"));
            } else {
                report.setValue("objectID", alarmInfoBean.getObjectID());
            }

            // 发生告警的设备或业务系统名称。
            if (null == alarmInfoBean.getSystemName() || alarmInfoBean.getSystemName().equals("")) {
                report.setValue("systemName", prop.getProperty("default.systemName"));
            } else {
                report.setValue("systemName", alarmInfoBean.getSystemName());
            }

            logger.info("上报告警，serverUrl:" + report.getValue("serverUrl") + ",rid:"
                    + report.getValue("rid") + ",type:" + report.getValue("type") + ",eventTime:"
                    + report.getValue("eventTime") + ",title:" + report.getValue("title") + ",lid:"
                    + report.getValue("lid") + ",original:" + report.getValue("original") + ",nid:"
                    + report.getValue("nid") + ",text:" + report.getValue("text") + ",original:"
                    + report.getValue("original") + ",deviceType:" + report.getValue("deviceType")
                    + ",alarmImpact:" + report.getValue("AlarmImpact") + ",oid:"
                    + report.getValue("oid") + ",alarmTitleID:" + report.getValue("alarmTitleID")
                    + ",objectID:" + report.getValue("objectID") + ",systemName:"
                    + report.getValue("systemName"));

            ResponseObject response = null;
            SenderManager manager = new SenderManager();
            response = manager.send(report);
            String status = response.getStatus();
            if (status.equals("0")) {
                logger.info("上报告警成功");
            } else {
                logger.info("上报告警失败 " + response.getError() + " " + response.getValue("message"));
            }

        } catch (Exception e) {
            logger.info("处理告警上报出现异常", e);
            throw e;
        }
    }
}
