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
package com.cloudmaster.cmp.util.shortMessage;

import java.io.UnsupportedEncodingException;

import com.cloudmaster.cmp.system.systemparmeter.service.SystemParameterService;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.ssmm.client.ResultBean;
import com.neusoft.ssmm.client.SSMMClient;
import com.neusoft.ssmm.message.pdu.SSMMSubmit;
import com.neusoft.ssmm.message.util.ByteBuffer;

/**
 * 发送短信
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class SSMMClientManager {

    //log
    private static LogService log = LogService.getLogger(SSMMClientManager.class);

    //短信客户端
    private SSMMClient client;

    //配置文件路径
    private String ssmmPath;

    //cmpp2.0
    private static final short VERSION30 = 0x30;

    //cmpp3.0
    private static final short VERSION20 = 0x20;

    /** 系统参数服务 */
    private SystemParameterService systemParameter;

    /**
     * 初始化短信中间件。与行业网关进行连接
     * @return
     */
    public void init() {
/* edit by x_wang 霍尼网管系统暂不支持短信
        String isOpen = "0";
        try{
            isOpen = systemParameter.getSystemParameterValue("SmsOpen");
        }catch(Exception e){
            log.info("获取运行环境是否开通信功能异常 ",e);
        }
        
        if (null == isOpen || !isOpen.equals("1")) {
            log.info("该运行环境未开通信功能,短信功能开关:" + isOpen);
            return;
        }

        log.info("短信模块加载开始！配置文件路径：" + ssmmPath);
        int code = client.ssmm_init(0, ssmmPath);
        if (0 == code) {
            log.info("短信模块初始化成功！");
            int loginCode = client.ssmm_login_ismg(PropertiesMessages
                    .getString("ssmm.mpiag.loginId"), PropertiesMessages
                    .getString("ssmm.mpiag.password"), VERSION20);
            if (0 == loginCode) {
                log.info("短信模块登录网关成功！");
            } else {
                log.error("短信模块登录网关失败！返回错误代码：" + loginCode + ",id:"
                        + PropertiesMessages.getString("ssmm.mpiag.id") + ",passwd:"
                        + PropertiesMessages.getString("ssmm.mpiag.password"));
            }
        } else {
            log.error("短信模块初始化失败！返回错误代码：" + code);
        }*/
    }

    /**
     * 断开连接
     */
    public void destroy() {
        String isOpen = systemParameter.getSystemParameterValue("SmsOpen");
        if (null == isOpen || !isOpen.equals("1")) {
            log.info("该运行环境未开通信功能,短信功能开关:" + isOpen);
            return;
        }

        log.info("短信模块断开连接开始！");
        client.ssmm_exit_connection();
        log.info("短信模块断开连接成功！");
    }

    /**
     * wap push短信初始化参数
     * @return
     */
    public SSMMSubmit ssmmSubmitInit() {
        SSMMSubmit msg = new SSMMSubmit();
        msg.setRegisteredDelivery(WAPPUSHContents.REGISTERED_DELIVERY);
        msg.setMsgLevel(WAPPUSHContents.MSG_LEVEL);
        msg.setServiceId(WAPPUSHContents.SERVICE_ID);
        msg.setFeeUserType(WAPPUSHContents.FEE_USERTYPE);
        msg.setFeeTerminalType(WAPPUSHContents.FEE_TERMINAL_TYPE);
        msg.setMsgFmt(WAPPUSHContents.MSG_FMT);
        msg.setMsgSrc(WAPPUSHContents.MSG_SRC);
        msg.setFeeType(WAPPUSHContents.FEETYPE);
        msg.setFeeCode(WAPPUSHContents.FEECODE);
        msg.setSrcId(WAPPUSHContents.SRCID);
        msg.setDestUsrTotal(WAPPUSHContents.DESTUSR_TL);
        msg.setDestTerminalType(WAPPUSHContents.DEST_TERMINAL_TYPE);

        return msg;

    }

    /**
     * 发送消息
     * @param msg
     * @return
     */
    public ResultBean sendMtMessage(SSMMSubmit msg, boolean isNormal) {
        int sendFlag = 0; // 0 异步方式；1 同步方式。
        ResultBean result = null;
        if (isNormal) {
            result = client.ssmm_normal_submit(msg, sendFlag);
        } else {
            result = client.ssmm_long_submit(msg, sendFlag);
        }
        return result;
    }

    /**
     * 发送wap push短信
     * @param msg
     * @param srcPort
     * @param destPort
     * @return
     */
    public ResultBean sendWapPushMtMessage(SSMMSubmit msg, int srcPort, int destPort) {
        int sendFlag = 0; // 0 异步方式；1 同步方式。
        ResultBean result = client.ssmm_wappush_submit(msg, srcPort, destPort, sendFlag);
        return result;
    }

    /**
     * 从队列中读取上行短信
     * @return
     */
    public ResultBean getMoMessage() {
        ResultBean result = client.ssmm_get_deliver();
        return result;
    }

    /**
     * 拼凑wap push短信内容
     * @param wapPushUrl
     * @param wapPushTitle
     * @return
     */
    public byte[] getWapPushSmsContent(String wapPushUrl, String wapPushTitle) {
        ByteBuffer byteBufContent = new ByteBuffer();
        byte[] tempByte = null;
        byteBufContent.appendBytes(WAPPUSHContents.SZ_WAPPUSH_HEADER1);
        byteBufContent.appendBytes(WAPPUSHContents.SZ_WAPPUSH_HEADER2);
        byteBufContent.appendBytes(WAPPUSHContents.SZ_WAPPUSH_INDICATOR);
        try {
            byteBufContent.appendBytes(wapPushUrl.getBytes("UTF-8"));
            log.info("发送短信 url：" + wapPushUrl);
            byteBufContent.appendBytes(WAPPUSHContents.SZ_WAPPUSH_DISPLAY_TEXTHEADER);
            byteBufContent.appendBytes(wapPushTitle.getBytes("UTF-8"));
            log.info("发送短信 titil：" + wapPushTitle);
            byteBufContent.appendBytes(WAPPUSHContents.SZ_ENDOFWAPPUSH);
            tempByte = byteBufContent.getBuffer();
        } catch (UnsupportedEncodingException e) {
            log.error("组装wap push短信异常！", e);
        }
        return tempByte;
    }

    public SSMMClient getClient() {
        return client;
    }

    public void setClient(SSMMClient client) {
        this.client = client;
    }

    public String getSsmmPath() {
        return ssmmPath;
    }

    public void setSsmmPath(String ssmmPath) {
        String classPath = SSMMClientManager.class.getResource("").getPath();
        classPath = classPath.replace("com/cloudmaster/cmp/util/shortMessage/", "");
        this.ssmmPath = classPath + ssmmPath;
    }

    public SystemParameterService getSystemParameter() {
        return systemParameter;
    }

    public void setSystemParameter(SystemParameterService systemParameter) {
        this.systemParameter = systemParameter;
    }

}
