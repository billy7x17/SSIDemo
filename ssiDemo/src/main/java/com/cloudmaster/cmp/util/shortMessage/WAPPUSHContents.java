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

/**
 * 发送短信
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class WAPPUSHContents {

    //        public static byte SZ_WAPPUSH_HEADER1[] = { 0x0B, 0x05, 0x04, 0x0B, (byte) 0x84, 0x23,
    //            (byte) 0xF0, 0x00, 0x03, 0x03, 0x01, 0x01 };
    public static byte SZ_WAPPUSH_HEADER1[] = { 0x00, 0x03, 0x03, 0x01, 0x01 };

    //    public final static byte SZ_WAPPUSH_HEADER2[] = { 0x29, 0x06, 0x06, 0x03, (byte) 0xAE,
    //            (byte) 0x81, (byte) 0xEA, (byte) 0x8D, (byte) 0xCA };

    public static final byte SZ_WAPPUSH_HEADER2[] = { 0x55, 0x06, 0x04, 0x03, (byte) 0xAE,
            (byte) 0x81, (byte) 0xEA };

    public static final byte SZ_WAPPUSH_INDICATOR[] = { 0x02, 0x05, 0x6A, 0x00, 0x45, (byte) 0xC6,
            0x08, 0x0C, 0x03 };

    public static final byte SZ_WAPPUSH_DISPLAY_TEXTHEADER[] = { 0x00, 0x01, 0x03 };

    public static final byte SZ_ENDOFWAPPUSH[] = { 0x00, 0x01, 0x01 };

    public static final short REGISTERED_DELIVERY = (short) Integer.parseInt((PropertiesMessages
            .getString("ssmm.wap.push.delivery")));

    public static final short MSG_LEVEL = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.msgLevel"));

    public static final String SERVICE_ID = PropertiesMessages.getString("ssmm.wap.push.serviceId");

    public static final short FEE_USERTYPE = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.feeUserType"));

    public static final short FEE_TERMINAL_TYPE = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.feeTerminalType"));

    public static final short MSG_FMT = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.msgFmt"));

    public static final String MSG_SRC = PropertiesMessages.getString("ssmm.mpiag.id");

    public static final String FEETYPE = PropertiesMessages.getString("ssmm.wap.push.feetype");

    public static final String FEECODE = PropertiesMessages.getString("ssmm.wap.push.feecode");

    public static final String SRCID = PropertiesMessages.getString("ssmm.wap.push.srcId");

    public static final short DESTUSR_TL = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.destUsrTotal"));

    public static final short DEST_TERMINAL_TYPE = (short) Integer.parseInt(PropertiesMessages
            .getString("ssmm.wap.push.destTerminalType"));
}
