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
package com.cloudmaster.cmp.util.mail;

/********************************************************************

 *
 * 变更履历： 2012/1/5 初版 LZ
 *
 ********************************************************************/
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 功能： 发送热点邮件 2012-1-31 下午04:51:52 初版 LZ
 * @author <a href="mailto:zhang_l@neusoft.com"> zhanglei </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SendMail {

    /**
     * log
     */
    private static final Logger logger = Logger.getLogger(SendMail.class);

    /**
     * 是否加密配置
     */
    private String mailIsSSL = getProperties("mailIsSSL");

    /**
     * 邮件服务器地址配置
     */
    private String mailServiceIp = getProperties("mailServiceIp");

    /**
     * 邮件服务器端口配置
     */
    private int mailServicePort = Integer.valueOf(getProperties("mailServicePort"));

    /**
     * 邮件发送人登录名配置
     */
    private String username = getProperties("sendmailuser");

    /**
     * 邮件发送人登陆密码配置
     */
    private String password = getProperties("sendmailpasswd");

    /**
     * 邮件发送人邮件地址
     */
    private String mail_from = getProperties("sendmailfrom");

    /**
     * 邮件默认标题
     */
    private String mail_head_name = "this is head of this mail";

    /**
     * 邮件默认标题
     */
    private String mail_head_value = "this is head of this mail";

    /**
     * 邮件服务器地址
     */
    private String propsHost = "mail.smtp.host";

    /**
     * 邮件服务器端口
     */
    private String propsPort = "mail.smtp.port";

    /**
     * 邮件标题
     */
    private String mail_subject = "CloudMaster Email notice";

    /**
     * 收件人
     */
    private String mail_to;

    /**
     * 邮件正文
     */
    private String mail_body = "";

    /**
     * 显示发件人信息
     */
    private String personalName = getProperties("personalName");

    /**
     * 获取系统配置信息
     */
    private static Properties prop;

    /**
     * 初始化配置信息
     */
    private void initProp() {
        try {
            prop = new Properties();
            ClassLoader classLoader = this.getClass().getClassLoader();
            prop.load(classLoader.getResourceAsStream("conf/other/MailConfig.properties"));
        } catch (Exception e) {
            logger.error("get mail config error", e);
        }
    }

    /**
     * 获取配置信息
     * @param key
     * @return
     */
    private String getProperties(String key) {
        if (null == prop || null == prop.getProperty(key)) {
            initProp();
        }
        return prop.getProperty(key);
    }

    /**
     * 构造函数
     * @param strMailHead
     * @param strMailTo
     */
    public SendMail(String strMailHead, String strMailTo) {
        this.mail_subject = strMailHead;
        this.mail_to = strMailTo;
    }

    /** 多条消息时组装成一封邮件 */
    public void setMessage(String newMessage) {
        this.mail_body += "\r\n" + newMessage;
    }

    /**
     * 有证书时设置邮件会话属性
     * @return
     */
    private Properties setSysProps() {
        logger.debug("SendMail.send() 加密发送");
        Properties props = new Properties();

        String path1 = SendMail.class.getResource("").getPath();
        /*path1 = path1.substring(1, path1.length());*/

        /*logger.debug("path" + path1);*/

        String path = path1 + "neusoft.ssl";
        logger.debug(path);
        /** 此处设置公钥位置 */
        /*logger.info(path);*/
        System.setProperty("javax.net.ssl.trustStore", path);
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        /** 获取系统环境 */
        props.put(propsHost, mailServiceIp); // GlobalVariable.configBean.getServiceIP());
        /** 此处设置成为加密连接 */
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        /** 此处设置服务器端口 */
        props.put(propsPort, mailServicePort); // port);
        return props;
    }

    /**
     * 无证书时设置邮件会话属性
     * @return
     */
    private Properties setNoSysProps() {
        Properties props = new Properties();
        props.put(propsHost, mailServiceIp);
//        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", true);
        return props;
    }

    /**
     * 创建一个发送邮件的session
     * @return
     */
    private Session createSession() {
        Properties props;
        if (mailIsSSL.equals("true")) {
            props = this.setSysProps();
        } else {
            props = this.setNoSysProps();
        }
        /** 创建一个发送邮件的session */
//        return Session.getDefaultInstance(props, new Authenticator() {
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * 创建消息
     * @param session
     * @return
     */
    private MimeMessage createMimeMessage(Session session) {
        /** 设置session,和邮件服务器进行通讯 */
        MimeMessage message = new MimeMessage(session);
        /** 设置邮件主题 */
        try {
            message.setSubject(mail_subject);
            /** 设置邮件正文 */
            message.setText(mail_body);
            /** 设置邮件标题 */
            message.setHeader(mail_head_name, mail_head_value);
            /** 设置邮件发送日期 */
            message.setSentDate(new Date());
            Address address = new InternetAddress(mail_from, personalName);
            /** 设置邮件发送者的地址 */
            message.setFrom(address);
            /** 设置邮件接收方的地址 */
            String[] mailToArr = mail_to.split(";");
            for (String to : mailToArr) {
                Address toAddress = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }

        } catch (UnsupportedEncodingException ue) {
            logger.error("SendMail.send() UnsupportedEncodingException ", ue);
        } catch (MessagingException me) {
            logger.error("SendMail.send() MessagingException ", me);
        }

        return message;
    }

    /**
     * 此段代码用来发送普通电子邮件
     * @throws Exception
     */
    public void send() throws Exception {
        Session session = createSession();
        session.setDebug(false);
        MimeMessage message = createMimeMessage(session);
        /** 需要使用加密连接发送 */
        Transport t = (Transport) session.getTransport("smtp");
        try {
            t.connect(mailServiceIp,mailServicePort, username, password);
            t.sendMessage(message, message.getAllRecipients());
        } finally {
            t.close();
        }

    }

     public static void main(String[] args) {
     // SendMail sendmail = new SendMail("zhang_l", "www.831.COM", "zhang_l@neusoft.com",
     // "head-test",
     // "zhang_l@neusoft.com");
    
     SendMail sendmail = new SendMail("邮件标题-测试", "wengcz@neusoft.com");
    
     sendmail.setMessage("邮件内容\r\n测试");
    
     try {
     sendmail.send();
     } catch (Exception ex) {
     logger.error("SendMail.send() Exception ", ex);
     }
     }
}
