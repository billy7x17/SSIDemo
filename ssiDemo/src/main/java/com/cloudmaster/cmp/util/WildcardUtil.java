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

/**
 * 特殊字符转换工具类
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public final class WildcardUtil {
    private WildcardUtil() {
    }

    /**
     * 把字符串"%", "_", "'"进行转义
     * @param input 源字符串
     * @return String 转义后字符串
     */
    public static String encode(String input) {
        if (input != null) {
            input = input.trim();
            input = input.replace("\\", "\\\\");
            input = input.replaceAll("%", "\\\\%");
            input = input.replaceAll("_", "\\\\_");
            input = input.replaceAll("'", "''");
        }
        return input;
    }

    /**
     * 截取一定长度的字符串，并把该字符串"%", "_", "'"进行转义
     * @param input 源字符串
     * @param length 长度
     * @return String 转义后字符串
     */
    public static String encode(String input, int length) {
        if (input != null) {
            if (input.length() > length) {
                input = input.substring(0, length);
            }
            input = input.trim();
            input = input.replace("\\", "\\\\");
            input = input.replaceAll("%", "\\\\%");
            input = input.replaceAll("_", "\\\\_");
            input = input.replaceAll("'", "''");
        }
        return input;
    }

    /**
     * 把字符串"%", "_", "'"进行反转义
     * @param input 源字符串
     * @return String 转义后字符串
     */
    public static String decode(String input) {
        if (input != null) {
            input = input.trim();
            input = input.replaceAll("\\\\%", "%");
            input = input.replaceAll("\\\\_", "_");
            input = input.replaceAll("''", "'");
            input = input.replace("\\\\", "\\");
        }
        return input;
    }
}
