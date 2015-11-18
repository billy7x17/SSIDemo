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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 字符串工具类
 * @author <a href="mailto:jiangxuan@neusoft.com">jiang xuan </a>
 * @version 1.0.0 18 Mar 2012
 */
public final class StringUtil {
    private StringUtil() {
    }

    /**
     * 将一个string按照某个参数进行分隔，regex为分隔参数
     * @param string
     * @param regex
     * @return
     */
    public static ArrayList<String> truncate(String string, String regex) {
        String tempString = string;
        if (null == string) {
            return null;
        }
        String[] result = tempString.split(regex);
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();
        if (null != result) {
            if (result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                    if (null != result[i] && !result[i].trim().equals("")) {
                        if (!map.containsKey(result[i])) {
                            list.add(result[i].trim());
                            map.put(result[i].trim(), result[i].trim());
                        }
                    }
                }
            }
            // else {
            // result = null;
            // }
        }
        return list.size() > 0 ? list : null;
    }

    /**
     * 将一个string按照中英文逗号进行分隔，
     * @param string
     * @return
     */
    public static ArrayList<String> truncate(String string) {
        if (null == string) {
            return null;
        }
        String tempString = string.replace("，", ",");
        return truncate(tempString, ",");
    }

    /**
     * 将一个string数组中的元素拼接在一期，regex是拼接时的分隔符
     * @param list
     * @param regex
     * @return
     */
    public static String joint(List<String> list, String regex) {
        StringBuffer sb = new StringBuffer();
        boolean judge = true;
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (null != list.get(i) && !list.get(i).equals("") && !list.get(i).equals(regex)) {
                    if (!judge) {
                        sb.append(regex);
                        judge = false;
                    }
                    sb.append(list.get(i));
                    if (judge) {
                        judge = false;
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将一个string数组中的元素拼接在一期，英文逗号是拼接时的分隔符
     * @param list
     * @return
     */
    public static String joint(List<String> list) {
        StringBuffer sb = new StringBuffer();
        boolean judge = true;
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (null != list.get(i) && !list.get(i).equals("") && !list.get(i).equals(",")) {
                    if (!judge) {
                        sb.append(",");
                        judge = false;
                    }
                    sb.append(list.get(i));
                    if (judge) {
                        judge = false;
                    }
                }
            }
        }
        return sb.toString();
    }

}
