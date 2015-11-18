/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * @(#)RegulationNotify.java 2013-4-19
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.alarm.autoclear.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.alarm.autoclear.dao.AutoClearAlarmDomain;
import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.alarm.standardize.dao.AlarmDomain;
import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.system.systemparmeter.service.SystemParameterService;
import com.cloudmaster.cmp.util.mail.SendMail;
import com.cloudmaster.cmp.util.shortMessage.SSMMClientManager;
import com.neusoft.mid.engine.fastrule.RuleAgent;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.ssmm.client.ResultBean;
import com.neusoft.ssmm.message.pdu.SSMMSubmit;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-19 上午10:51:13
 */
public class AlarmNotify {
    /**
     * 日志前缀
     */
    private String logBegin = "Regulation CLEAR ALARM,";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * 数据库dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "AutoClearAlarm.";

    /**
     * 短信处理
     */
    private SSMMClientManager ssmmClientManager;

    /**
     * 系统参数服务
     */
    private SystemParameterService sysParaService;

    /**
     * 新告警通知处理
     * @param alarm
     */
    public void newAlarmNotify(AlarmDomain alarm) {
        logger.debug("==========新告警通知线程开始处理==============");
        try {
            AlarmRegulationDomain clearRegBean = new AlarmRegulationDomain();
            clearRegBean.setRuleAction("3"); // 1-清除，2-确认，3-通知
            List<AlarmRegulationDomain> clearRegLi = ibatisDAO.getData(
                    IBATIS_NAMESPACE + "getRule", clearRegBean);

            List<String> ruleli = checkRule(clearRegLi, alarm);

            if (!ruleli.isEmpty()) {
                newAlarmnotify(ruleli, alarm);
                recordRule(ruleli, alarm);
            }

        } catch (Exception e) {
            logger.error("新告警通知失败", e);
        }
    }

    /**
     * 自动清除、按照规则清除告警通知处理
     * 
     * @param alarm
     */
    public void autoClearAlarmNotify(AutoClearAlarmDomain alarm) {
        logger.debug("========自动清除告警通知线程开始处理=======");
        try {
            String ruleId = alarm.getNewAlarmNotifyRule();
            if(null == ruleId || ruleId.equals("")){
                return;
            }

            AlarmRegulationDomain clearRegBean = new AlarmRegulationDomain();
            clearRegBean.setID(ruleId); // 1-清除，2-确认，3-通知

            List<AlarmRegulationDomain> clearRegLi = ibatisDAO.getData(IBATIS_NAMESPACE
                    + "getClearNotifyRule", clearRegBean);

            if (!clearRegLi.isEmpty()) {
                autoClearNotify(clearRegLi, alarm);
            }

        } catch (Exception e) {
            logger.error("清除告警通知失败", e);
        }
    }

    /**
     * 手工清除告警通知处理
     * @param alarm
     */
    public void manualClearAlarmNotify(AlarmViewDomain alarm) {
        logger.debug("========手工清除告警通知线程开始处理=======");
        try {
            String ruleId = alarm.getNewAlarmNotifyRule();
            if(null == ruleId || ruleId.equals("")){
                return;
            }
            
            AlarmRegulationDomain clearRegBean = new AlarmRegulationDomain();
            clearRegBean.setID(ruleId); // 1-清除，2-确认，3-通知

            List<AlarmRegulationDomain> clearRegLi = ibatisDAO.getData(IBATIS_NAMESPACE
                    + "getClearNotifyRule", clearRegBean);

            if (!clearRegLi.isEmpty()) {
                manualClearNotify(clearRegLi, alarm);
            }

        } catch (Exception e) {
            logger.error("清除告警通知失败", e);
        }
    }

    /**
     * 自动清除告警处理
     * @param clearRegLi
     * @param alarm
     * @throws Exception
     */
    private void autoClearNotify(List<AlarmRegulationDomain> clearRegLi, AutoClearAlarmDomain alarm)
            throws Exception {

        for (int i = 0; i < clearRegLi.size(); i++) {
            AlarmRegulationDomain rule = clearRegLi.get(i);

            String notifyType = rule.getNotifyType();
            String message = sysParaService.getSystemParameterValue("clearAlarmNotifyMessage");
            message = replaceContent(message, alarm);

            Map<String, String> userMap = getUserInfo(rule.getNotifyPersonId());

            // 1-邮件通知，2-短信通知,3-邮件和短信
            if (notifyType.equals("1")) {
                sendMail(message, message, userMap.get("mail").toString());
            }
            if (notifyType.equals("2")) {
                sendSms(message, userMap.get("mobile").toString());
            }
            if (notifyType.equals("3")) {
                sendMail(message, message, userMap.get("mail").toString());
                sendSms(message, userMap.get("mobile").toString());
            }

        }
    }

    /**
     * 手工清除告警处理
     * @param clearRegLi
     * @param alarm
     * @throws Exception
     */
    private void manualClearNotify(List<AlarmRegulationDomain> clearRegLi, AlarmViewDomain alarm)
            throws Exception {

        for (int i = 0; i < clearRegLi.size(); i++) {
            AlarmRegulationDomain rule = clearRegLi.get(i);

            String notifyType = rule.getNotifyType();
            String message = sysParaService.getSystemParameterValue("clearAlarmNotifyMessage");
            message = replaceContent(message, alarm);

            Map<String, String> userMap = getUserInfo(rule.getNotifyPersonId());

            // 1-邮件通知，2-短信通知,3-邮件和短信
            if (notifyType.equals("1")) {
                sendMail(message, message, userMap.get("mail").toString());
            }
            if (notifyType.equals("2")) {
                sendSms(message, userMap.get("mobile").toString());
            }
            if (notifyType.equals("3")) {
                sendMail(message, message, userMap.get("mail").toString());
                sendSms(message, userMap.get("mobile").toString());
            }

        }
    }

    /**
     * 新告警通知处理,判断是否符合规则
     * @param regulationLi
     * @param alarm
     * @return
     */
    private List<String> checkRule(List<AlarmRegulationDomain> regulationLi, AlarmDomain alarm) {
        List<String> li = new ArrayList<String>();
        try {
            if (null != regulationLi && !regulationLi.isEmpty()) {
                for (AlarmRegulationDomain regulation : regulationLi) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("alarmTitle", alarm.getAlarmTitle());
                    map.put("alarmContent", alarm.getAlarmContent());
                    map.put("alarmIP", alarm.getAlarmIP());
                    map.put("alarmType", alarm.getAlarmType());
                    map.put("alarmLevel", alarm.getAlarmGrade());
                    map.put("alarmOID", alarm.getAlarmIdentify());
                    map.put("alarmDevice", alarm.getDeviceType());
                    map.put("alarmTime", alarm.getAlarmTime());

                    RuleAgent agent = new RuleAgent();
                    Object obj = agent.eval(regulation.getRuleRegexp(), map);
                    if (null != obj && obj.toString().equals("true")) { // regulationCheck(map,alarm)
                        li.add(regulation.getID());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("规则校验失败", e);
        }
        return li;
    }

    /**
     * 新告警通知，记录规则ID，清除告警时判断是否通知
     * @param li
     * @param alarm
     */
    private void recordRule(List<String> li, AlarmDomain alarm) {
        try {
            String ruleID = "";
            for (int i = 0; i < li.size(); i++) {
                ruleID += ((ruleID == "" ? "" : ",") + li.get(i));
            }
            ruleID = "('" + ruleID.replace(",", "','") + "')";
            alarm.setNewAlarmNotifyRule(ruleID);
            ibatisDAO.updateData(IBATIS_NAMESPACE + "updateAlarmRule", alarm);
        } catch (Exception e) {
            logger.error("记录规则ID失败", e);
        }

    }

    /**
     * 新告警通知处理
     * @param li
     * @param alarm
     * @throws Exception
     */
    private void newAlarmnotify(List<String> li, AlarmDomain alarm) throws Exception {

        for (int i = 0; i < li.size(); i++) {
            AlarmRegulationDomain bean = new AlarmRegulationDomain();
            bean.setID(li.get(i));
            bean.setRuleAction("3"); // 1-清除，2-确认，3-通知
            // 获取通知规则
            AlarmRegulationDomain rule = (AlarmRegulationDomain) ibatisDAO.getSingleRecord(
                    IBATIS_NAMESPACE + "getRuleAction", bean);
            if (null == rule) {
                continue;
            }
            String notifyType = rule.getNotifyType();
            String notifyTitle = replaceContent(rule.getNotifyTitle(), alarm);
            String notifyContent = replaceContent(rule.getNotifyContent(), alarm);
            String notifySms = replaceContent(rule.getNotifySMS(), alarm);

            String userMobile = "";
            String userMail = "";
            Map userMap = getUserInfo(rule.getNotifyPersonId());

            // 1-邮件通知，2-短信通知,3-邮件和短信
            if (notifyType.equals("1")) {
                sendMail(notifyTitle, notifyContent, userMap.get("mail").toString());
            }
            if (notifyType.equals("2")) {
                sendSms(notifySms, userMap.get("mobile").toString());
            }
            if (notifyType.equals("3")) {
                sendMail(notifyTitle, notifyContent, userMap.get("mail").toString());
                sendSms(notifySms, userMap.get("mobile").toString());
            }

        }
    }

    /**
     * 替换通知内容
     * @param value
     * @param alarm
     * @return
     */
    private String replaceContent(String value, AlarmViewDomain alarm) {
        // 告警内容为${alarm_content},
        // 告警标题${alarm_title}，
        // 告警时间${alarm_time}，
        // 告警类型${alarm_type}，
        // 告警设备类型${device_type}，
        // 告警设备IP${alarm_IP}
        value = value.replace("${alarm_content}", alarm.getAlarmContent());
        value = value.replace("${alarm_title}", alarm.getAlarmTitle());
        value = value.replace("${alarm_time}", alarm.getAlarmTime());
        value = value.replace("${alarm_type}", alarm.getAlarmType());
        value = value.replace("${device_type}", alarm.getDeviceType());
        value = value.replace("${alarm_IP}", alarm.getAlarmIP());
        return value;
    }

    /**
     * 替换通知内容
     * @param value
     * @param alarm
     * @return
     */
    private String replaceContent(String value, AutoClearAlarmDomain alarm) {
        // 告警内容为${alarm_content},
        // 告警标题${alarm_title}，
        // 告警时间${alarm_time}，
        // 告警类型${alarm_type}，
        // 告警设备类型${device_type}，
        // 告警设备IP${alarm_IP}
        value = value.replace("${alarm_content}", alarm.getAlarmContent());
        value = value.replace("${alarm_title}", alarm.getAlarmTitle());
        value = value.replace("${alarm_time}", alarm.getAlarmTime());
        value = value.replace("${alarm_type}", alarm.getAlarmType());
        value = value.replace("${device_type}", alarm.getDeviceType());
        value = value.replace("${alarm_IP}", alarm.getAlarmIP());
        return value;
    }

    /**
     * 替换通知内容
     * @param value
     * @param alarm
     * @return
     */
    private String replaceContent(String value, AlarmDomain alarm) {
        // 告警内容为${alarm_content},
        // 告警标题${alarm_title}，
        // 告警时间${alarm_time}，
        // 告警类型${alarm_type}，
        // 告警设备类型${device_type}，
        // 告警设备IP${alarm_IP}
        value = value.replace("${alarm_content}", alarm.getAlarmContent());
        value = value.replace("${alarm_title}", alarm.getAlarmTitle());
        value = value.replace("${alarm_time}", alarm.getAlarmTime());
        value = value.replace("${alarm_type}", alarm.getAlarmType());
        value = value.replace("${device_type}", alarm.getDeviceType());
        value = value.replace("${alarm_IP}", alarm.getAlarmIP());
        return value;
    }

    /**
     * 获取通知用户信息
     * @param userID
     * @return
     */
    private Map<String, String> getUserInfo(String userID) {
        Map<String, String> userMap = new HashMap<String, String>();
        try {
            userID = "('" + userID.replace(",", "','") + "')";
            List<AlarmRegulationDomain> userLi = ibatisDAO.getData(IBATIS_NAMESPACE
                    + "getRuleNotifyPerson", userID);

            String userMobile = "";
            String userMail = "";
            if (null != userLi || !userLi.isEmpty()) {
                for (int i = 0; i < userLi.size(); i++) {
                    AlarmRegulationDomain bean = userLi.get(i);
                    String mail = bean.getNotifyPersonMail();
                    if (null != mail && !mail.equals("")) {
                        userMail += ((userMail == "" ? "" : ";") + mail);
                    }
                    String mobile = bean.getNotifyPersonMobile();
                    if (null != mobile && !mobile.equals("")) {
                        userMobile += ((userMobile == "" ? "" : ";") + mobile);
                    }
                }
            }
            userMap.put("mobile", userMobile);
            userMap.put("mail", userMail);
        } catch (Exception e) {
            logger.info("获取通知人员失败:", e);
        }

        return userMap;
    }

    /**
     * 发送邮件
     * @param title
     * @param content
     * @param addr
     */
    private void sendMail(String title, String content, String addr) {
        try {
            if (null == addr || addr.equals("")) {
                return;
            }
            SendMail sendmail = new SendMail(title, addr);
            logger.info("发送邮件标题：" + title);
            logger.info("发送邮件收件人：" + addr);
            if (null != content) {
                sendmail.setMessage(content);
            }
            sendmail.send();
        } catch (Exception e) {
            logger.info("发送邮件失败:", e);
        }
    }

    /**
     * 发送短信
     * @param content
     * @param mobile
     */
    private void sendSms(String content, String mobile) {
        try {
            if (null == mobile || mobile.equals("")) {
                return;
            }
            /** 发送短信 */
            SSMMSubmit msg = ssmmClientManager.ssmmSubmitInit();
            /**
             * 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
             */
            msg.setFeeTerminalId("");
            logger.info("发送短信主叫号码：" + msg.getSrcId());

            String[] msisdnArr = mobile.split(";");
            for (String msisdn : msisdnArr) {
                msg.addDestTerminalId(msisdn);
                logger.info("发送短信被叫号码：" + msisdn);
            }

            msg.setMsgContent(content.trim().getBytes("GB2312"));
            // boolean isNormal = msg.getMsgLength() <= 140 ? true : false;
            boolean isNormal = content.trim().length() <= 70 ? true : false;
            logger.info("短信内容长度:" + content.trim().length() + ",发送短信内容:" + content.trim());
            ResultBean longMtResult = ssmmClientManager.sendMtMessage(msg, isNormal);
        } catch (Exception e) {
            logger.info("发送短信失败:", e);
        }
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public SSMMClientManager getSsmmClientManager() {
        return ssmmClientManager;
    }

    public void setSsmmClientManager(SSMMClientManager ssmmClientManager) {
        this.ssmmClientManager = ssmmClientManager;
    }

    public SystemParameterService getSysParaService() {
        return sysParaService;
    }

    public void setSysParaService(SystemParameterService sysParaService) {
        this.sysParaService = sysParaService;
    }

}
