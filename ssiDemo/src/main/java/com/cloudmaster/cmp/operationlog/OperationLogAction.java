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

import com.cloudmaster.cmp.web.BaseAction;

/**
 * 实现记录日志功能的action父类 在没有分页，又有记录日志的功能时，请继承该类 在有分页， 又有记录日志的功能时，请继承pageAction,再自己实现IOperationLog
 * 在又没有分页又没有记录日志的功能时，只继承BaseAction即可
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version 1.0.0 18 Mar 2012
 */
public class OperationLogAction extends BaseAction implements IOperationLog {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 为操作日志返回的功能名
     */
    protected String functionName = "";

    /**
     * 为操作日志返回的功能描述
     */
    protected String operationInfo = "";

    /**
     * 为操作日志返回的操作类型；
     */
    protected String opType = "";

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    /**
     * 为了能取到配置文件中的值
     * @param functionName
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String getOpType() {
        return opType;
    }

    /**
     * 为了能取到配置文件中的值
     * @param opType
     */
    public void setOpType(String opType) {
        this.opType = opType;
    }
}
