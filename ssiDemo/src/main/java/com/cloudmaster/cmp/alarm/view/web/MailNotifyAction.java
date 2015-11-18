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
import com.cloudmaster.cmp.util.mail.SendMail;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MailNotifyAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3139437927841976875L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(MailNotifyAction.class);

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

    public String mailNotify() {
        logger.info(getText("function.title") + getText("log.mail.begin"));
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() };
        isSuccess = "1";
        try {

            // 输入校验，填写的手机号码非法
            String mailStr = domain.getMailMember();
            // 替换为英文分号，去除两边分号
            mailStr = trimBothSide(mailStr);
            // 验证是否是移动手机号码
            if (!checkMail(mailStr, ";")) {
                errorMsg = getText("message.mail.addrError");
                isSuccess = "0";
                return "input";
            }

            // 发送邮件
            SendMail sendmail = new SendMail(domain.getMailTitle(), domain.getMailMember());
            if (null != domain.getMailContent()) {
                sendmail.setMessage(domain.getMailContent());
            }
            sendmail.send();

            operationInfo = getText("oplog.mail.success", opParam);
            logger.info(getText("function.title") + getText("log.mail.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.mail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.mail.error"), e);
            isSuccess = "0";
            errorMsg = getText("message.mail.systemError");
        }
        return "input";
    }

    /**
     * 邮件通知
     */
    public void alarmMailNotify() {
        logger.info(getText("function.title") + getText("log.mail.begin"));
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() + ","
                + getText("field.label.mailMember") + ": " + domain.getMailMember() };
        String result = "0"; // 0-成功，1-失败，系统异常，2-失败，手机号格式错误
        try {

            String mailStr = domain.getMailMember();
            // 替换为英文分号，去除两边分号
            mailStr = trimBothSide(mailStr);
            // 验证是否合法
            if (!checkMail(mailStr, ";")) {
                result = "2";
            } else {
                // 发送邮件
                SendMail sendmail = new SendMail(domain.getMailTitle(), domain.getMailMember());
                if (null != domain.getMailContent()) {
                    sendmail.setMessage(domain.getMailContent());
                }
                sendmail.send();
                result = "0";
            }

        } catch (Exception e) {
            operationInfo = getText("oplog.mail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.mail.error"), e);
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
            logger.info(getText("function.title") + getText("log.mail.end"));
            operationInfo = getText("oplog.mail.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.mail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.mail.error"), e);
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
     * 根据给定的长字符串number，使用分隔符split进行分割， 验证每个分割后的邮件地址是否合法
     * @param number 包含分割符的邮件地址字符串
     * @return true 邮件地址合法， false 存在不合法邮件地址
     */
    private boolean checkMail(String src, String split) {

        String[] mailArr = src.split(split);

        if (null != mailArr && mailArr.length > 0) {
            for (int i = 0; i < mailArr.length; i++) {
                String mail = mailArr[i].toString();
                // if (!mail.matches("^[a-zA-Z0-9_]+[@][a-zA-Z0-9]+([\\.com]|[\\.cn])$")) {
                if (!mail.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
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

}
