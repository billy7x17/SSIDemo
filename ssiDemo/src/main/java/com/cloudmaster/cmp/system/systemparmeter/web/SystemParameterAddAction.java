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
package com.cloudmaster.cmp.system.systemparmeter.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParameterAddAction extends OperationLogAction implements IAuthIdGetter {


    /**
     * serial
     */
    private static final long serialVersionUID = -6244204774807213909L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> systemParamList = new ArrayList<SystemParameterDomain>();

    /**
     * 系统参数实例
     */
    private SystemParameterDomain domain = new SystemParameterDomain();

    /**
     * 错误信息提示
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "systemparameter";

    private static LogService logger = LogService.getLogger(SystemParameterAddAction.class);

    /**
     * 系统参数添加
     * @return String
     * @param
     */
    public String add() {
        logger.info(getText("function.title") + getText("log.add.begin"));
        String opParam[] = { getText("field.label.paramKey") + ": " + domain.getParamKey()};
        try {
            //获取系统当前时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            domain.setUpdateTime(df.format(date));
            // 根据分组与系统参数名称成联合主键查询参数内容是否存在
            int i = ibatisDAO.getCount(IBATIS_NAMESPACE + ".getOrganize", domain);
            if (i <= 0) {
                domain = (SystemParameterDomain) ibatisDAO.insertData(IBATIS_NAMESPACE + ".insert",
                        domain);
                msg = getText("common.message.addSuccess");
                operationInfo = getText("oplog.add.success", opParam);
            } else {

                errorMsg = getText("common.message.addError") + getText("message.add.exist");
                operationInfo = getText("oplog.add.error", opParam);
                forward = "INPUT";
            }
            logger.info(getText("function.title") + getText("log.add.end"));

        } catch (Exception e) {
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam) + getText("common.message.systemError");
            forward = "INPUT";
            logger.error(getText("function.title") + getText("log.add.error"),e);
        }
        return forward;
    }

    public List<SystemParameterDomain> getSystemParamList() {
        return systemParamList;
    }

    public void setSystemParamList(List<SystemParameterDomain> systemParamList) {
        this.systemParamList = systemParamList;
    }

    public SystemParameterDomain getDomain() {
        return domain;
    }

    public void setDomain(SystemParameterDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
