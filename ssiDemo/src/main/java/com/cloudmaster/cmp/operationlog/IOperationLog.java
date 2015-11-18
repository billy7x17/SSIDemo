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
package com.cloudmaster.cmp.operationlog;

/**
 * 操作日志信息的收集日志信息接口，用于收集ACTION提供的操作功能和操作信息两个字符串
 * 需要产生日志信息的ACTION要实现该接口，返回操作功能和操作信息两个字符串
 * 返回操作功能和操作信息字符串具体要求参见“IAMP-用例规约-平台-日志管理-收集操作日志”的“7.1日志用例规约约束”
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public interface IOperationLog {
    /**
     * 获取操作信息
     * @return
     */
    String getOperationInfo();
    /**
     * 获取操作功能
     * @return
     */
    String getOperationFunction();
    /**
     * 获取操作类型
     * @
     */
    String getOpType();
    
}
