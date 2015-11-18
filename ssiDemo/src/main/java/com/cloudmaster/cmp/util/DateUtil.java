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
package com.cloudmaster.cmp.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public final class DateUtil {

    // 日期格式
    private static final String DATELONG = "yyyy-MM-dd HH:mm:ss";

    // 日期格式
    private static final String DATESHORT = "yyyy-MM-dd";

    // 时间格式
    private static final String TIME = " 00:00:00";

    // 日期格式
    private static final String TIMESTEMP = "yyyyMMddHHmmss";

    /**
     * 构造函数
     */
    private DateUtil() {
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * @param strDate 时间字符串
     * @return Date 日期
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATELONG);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     * @param strDate 时间字符串
     * @return Date 日期
     */
    public static Date strToDateShort(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATESHORT);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     * @param dateDate 日期
     * @return 时间字符创
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATELONG);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd 00:00:00
     * @param dateDate 日期
     * @return 时间字符串
     */
    public static String dateToStrDefault(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATESHORT);
        String dateString = formatter.format(dateDate) + TIME;
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     * @param dateDate 日期
     * @return 时间字符串
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATESHORT);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * TIMESTEMP 此时间格式用于生成格林尼治时间，精确到秒， 24小时制，若需精确到12小时制修改kk为hh 若精确到毫秒在结尾增加大写s
     * @return 时间字符串
     */
    public static String getCalendarTime() {
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat(TIMESTEMP);
        String sysDatetime = fmt.format(rightNow.getTime());
        return sysDatetime;
    }

    /**
     * 将一个日期转换成另外一种格式
     * @param strDate 转换日期
     * @param startForm 转换日期初始格式
     * @param endForm 转换日期最终格式
     * @return String 时间字符串
     */
    public static String changedateStr(String dateStr, String startForm, String endForm) {

        Date date = null;
        String sysDatetime = null;
        try {
            date = new SimpleDateFormat(startForm).parse(dateStr);
            SimpleDateFormat fmt = new SimpleDateFormat(endForm);
            sysDatetime = fmt.format(date);

        } catch (ParseException e) {
            System.out.println("日期不符合规范或日期输入格式错误。");
            System.out.println(startForm + "的格式输入。");
            e.printStackTrace();
        }
        return sysDatetime;
    }

}
