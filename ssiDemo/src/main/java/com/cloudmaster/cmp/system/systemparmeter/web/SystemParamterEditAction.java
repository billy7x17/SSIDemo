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

import java.sql.SQLException;
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
public class SystemParamterEditAction extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

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

    private static LogService logger = LogService.getLogger(SystemParamterEditAction.class);

    /**
     * 系统参数修改
     * @return String
     * @param
     */
    public String edit() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.paramKey") + ": " + domain.getParamKey() };
        try {
            // 获取系统当前时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            domain.setUpdateTime(df.format(date));
            int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".update", domain);
            if (i > 0) {
                // 如果参数是日志管理保留时间值，那么同步需要更新日志保留分区删除表信息
                if (domain.getOrganize().equals(getText("log.functionName"))
                        && domain.getParamKey().equals("logRetentionTime")) {
                    ibatisDAO.updateData(IBATIS_NAMESPACE + ".updatePartitionTab", domain);
                }

                msg = getText("common.message.editSuccess");
                operationInfo = getText("oplog.edit.success", opParam);
                logger.info(getText("function.title") + getText("log.edit.end"));
            } else {
                errorMsg = getText("common.message.editError") + getText("message.sql.error");
                operationInfo = getText("oplog.edit.error", opParam) + getText("message.sql.error");
                forward = "INPUT";
                logger.info(getText("function.title") + getText("log.edit.end"));
            }

        } catch (SQLException e) {
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            forward = "INPUT";
            logger.error(getText("function.title") + getText("log.edit.error"), e);

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

    public static String getIBATIS_NAMESPACE() {
        return IBATIS_NAMESPACE;
    }

    public static void setIBATIS_NAMESPACE(String ibatis_namespace) {
        IBATIS_NAMESPACE = ibatis_namespace;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        SystemParamterEditAction.logger = logger;
    }

}
