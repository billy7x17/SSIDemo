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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.shortMessage.SSMMClientManager;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.ssmm.client.ResultBean;
import com.neusoft.ssmm.message.pdu.SSMMSubmit;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SmsNotifyAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3139437927841976875L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(SmsNotifyAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 异常消息
     */
    private String errorMsg;

    /**
     * 是否处理成功
     */
    private String isSuccess;

    private SSMMClientManager ssmmClientManager;

    /**
     * 短信通知
     */
    public void alarmSMNotify() {
        logger.info(getText("function.title") + "告警短信提醒");
        String opParam[] = { domain.getSmsMember() };
        String result = "0"; // 0-成功，1-失败，系统异常，2-失败，手机号格式错误
        try {

            String msisdnStr = domain.getSmsMember();
            // 替换为英文分号，去除两边分号
            msisdnStr = trimBothSide(msisdnStr);
            // 验证是否是移动手机号码
            if (!checkMobileNumber(msisdnStr, ";")) {
                result = "2";
            } else {
                /** 发送短信 */
                SSMMSubmit msg = ssmmClientManager.ssmmSubmitInit();
                /**
                 * 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
                 */
                msg.setFeeTerminalId("");
                logger.info("发送短信主叫号码：" + msg.getSrcId());

                String[] msisdnArr = msisdnStr.split(";");
                for (String msisdn : msisdnArr) {
                    msg.addDestTerminalId(msisdn);
                    logger.info("发送短信被叫号码：" + msisdn);
                }

                String smsContent = domain.getSmsContent().trim();
                msg.setMsgContent(smsContent.getBytes("GB2312"));

//                boolean isNormal = msg.getMsgLength() <= 140 ? true : false;
                boolean isNormal = smsContent.length() <= 70 ? true : false;
                logger.info("短信内容长度:" + smsContent.length() + ",发送短信内容:" + smsContent);
                ResultBean longMtResult = ssmmClientManager.sendMtMessage(msg, isNormal);

                // // 短信内容
                // msg.setMsgContent(ssmmClientManager.getWapPushSmsContent("",
                // domain.getSmsContent()));
                // // 源端口号9200 ,目的端口号2948
                // ResultBean mtResult = ssmmClientManager.sendWapPushMtMessage(msg, Integer
                // .parseInt(PropertiesMessages.getString("ssmm.wap.push.srcPort")), Integer
                // .parseInt(PropertiesMessages.getString("ssmm.wap.push.destPort")));
                // if (0 == mtResult.getError()) {
                // log.info("告警短信发送成功！");
                // } else {
                // log.info("告警短信发送失败，错误码为：" + mtResult.getError());
                // }
                // logger.info("发送告警短信误差时间：" + (DateUtil.subTime(alertDomain.getAlertTime())));
                result = "0";
            }
        } catch (Exception e) {
            operationInfo = getText("oplog.confirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + "告警短信提醒出现异常", e);
            result = "1";
        }
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + "告警短信提醒结束");
            operationInfo = "告警短信提醒结束";
        } catch (Exception e) {
            operationInfo = getText("oplog.confirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + "告警短信提醒出现异常", e);
        }
    }

    /**
     * // 替换为英文分号，去除两边分号
     * @param src
     * @return
     */
    private String trimBothSide(String src) {
        String rv = src;
        if (rv != null) {
            // 去掉空格回车
            rv = rv.trim();

            // 替换中文【；】
            rv = rv.replace("；", ";");

            // 去掉首端的多余【;】
            rv = rv.replaceAll("^;", "");

            // 去掉末端的多余【;】
            rv = rv.replaceAll(";$", "");
            // 去掉英文【,】
            // rv = rv.replaceAll(",", " ");
        }
        return rv;
    }

    /**
     * 根据给定的长字符串number，使用分隔符split进行分割， 验证每个分割后的号码是否是移动手机号码
     * @param number 包含分割符的长号码字符串
     * @return true 标识全部为移动手机号， false 标识为存在非移动手机号
     */
    private boolean checkMobileNumber(String number, String split) {

        String[] msisdnArr = number.split(split);

        if (null != msisdnArr && msisdnArr.length > 0) {
            for (int i = 0; i < msisdnArr.length; i++) {
                String msisdn = msisdnArr[i].toString();
                // if (!msisdn.matches("13\\d{9}") && !msisdn.matches("159\\d{8}")) {
                if (!msisdn.matches("^[1](([3][4-9])|([5][012789])|([4][7])|([8][278]))\\d{8}$")) {
                    return false;
                }
            }
        }
        return true;
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

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public SSMMClientManager getSsmmClientManager() {
        return ssmmClientManager;
    }

    public void setSsmmClientManager(SSMMClientManager ssmmClientManager) {
        this.ssmmClientManager = ssmmClientManager;
    }

}
