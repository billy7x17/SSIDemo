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
 * sql特殊字符处理
 * <b>Date:</b>2009-12-22<br>
 * @author 宋阳
 * @version 1.0.0 18 Mar 2012
 * @version 1.0.0 18 Mar 2012
 *  
 */

public class SqlUtil {
    
	/**
     * 构造函数
     */
    private SqlUtil() {
    }

    /**
	 * 将特殊字符转化为普通字符，并去掉前后空格，以便进行模糊查询
	 * @param String需要进行转义的特殊字符
	 * @return String 普通字符
	 */
	public static String specialToNormal(String str) {
		//wangmingli add begin
		if (str == null) {
			return null;
		}
		//wangmingli add end
		
		// str = str.replace("\\", "\\\\\\\\");
		str = str.replace("\\", "\\\\");
		str = str.replace("％", "\\%");
		str = str.replace("　", " ");
		str = str.replace("＿", "\\_");
		str = str.replace("%", "\\%");
		str = str.replace("_", "\\_");
		str = str.replace("'", "''");
		// str = str.trim();
		return str;
	}
}
