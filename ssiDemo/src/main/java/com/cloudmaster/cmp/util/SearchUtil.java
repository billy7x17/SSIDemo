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

import java.util.regex.Pattern;

/**
 * 此工具类用于在进行搜索功能时，对特殊字符进行转义
 * @author renlala
 * @version 1.0.0 18 Mar 2012
 */
public final class SearchUtil {

    // 数字个数
    private static final int NUMBERSIZE = 2;

    // 字符个数
    private static final int LETTERSIZE = 3;

    /**
     * 构造函数
     */
    private SearchUtil() {

    }

    /**
     * 将特殊字符转化为普通字符，并去掉前后空格，以便进行模糊查询
     * @param String 需要进行转义的特殊字符
     * @return String 普通字符
     */
    public static String special2Usual(String str) {
        str = str.replace("\\", "\\\\\\\\");
        str = str.replace("％", "\\%");
        str = str.replace("　", " ");
        str = str.replace("＿", "\\_");
        str = str.replace("%", "\\%");
        str = str.replace("_", "\\_");
        str = str.replace("'", "''");
        str = str.trim();
        return str;
    }

    /**
     * 校验本系统密码专用，即要求密码至少包含两个小写字母、一个数字 和一个特殊字符
     * @param test
     * @return
     */
    public static boolean checkPassword(String test) {
        String password = " " + test + " ";
        String[] numberTest = password.split("[\\d]");
        String[] letterTest = password.split("[a-z]");
        String[] specialTest = password.split("[\\W]");
        if (numberTest.length < NUMBERSIZE) {
            return false;
        }
        if (letterTest.length < LETTERSIZE) {
            return false;
        }
        if (specialTest.length < LETTERSIZE) {
            String cs = test.substring(test.length() - 1, test.length());
            if (Pattern.matches("[\\W]", cs)) {
                return true;
            } else if (password.indexOf("_") != -1) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
