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
package com.cloudmaster.cmp.alarm.threshold.web;

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询阈值信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdBeforeSearchAction extends BaseAction implements IAuthIdGetter {

    // 错误信息
    private String errorMsg;

    // 跳转页面
    private String forward = "SUCCESS";

    // 设备类型
    private List<ThresholdDomain> deviceTypeList = new ArrayList<ThresholdDomain>();

    // 告警等级
    private List<ThresholdDomain> levelList = new ArrayList<ThresholdDomain>();

    private ThresholdDomain domain = new ThresholdDomain();

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdBeforeSearchAction.class);

    /**
     * 查询初始化
     * @return
     */
    @SuppressWarnings("unchecked")
    public String beforeSearch() {

        try {
            logger.info(getText("function.title") + getText("before.search.function"));
            // 获取设备类型信息
            deviceTypeList = ibatisDAO.getData("ThresholdInfo.getDeviceTypeList", domain);
            // 获取告警等级
            levelList = ibatisDAO.getData("ThresholdInfo.getSearchAlarmLevelList", domain);
            if (getDeviceTypeList() == null || getDeviceTypeList().size() == 0) {
                msg = this.getText("threshold.before.deviceType.action.error");
                logger.info(msg);
            } else if (getLevelList() == null || getLevelList().size() == 0) {
                msg = this.getText("threshold.before.thresholdLevel.action.error");
                logger.info(msg);
            }
        } catch (Exception e) {
            errorMsg = this.getText("threshold.search.action.error");
            logger.error(errorMsg, e);
        }
        return forward;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<ThresholdDomain> getDeviceTypeList() {
        return deviceTypeList;
    }

    public void setDeviceTypeList(List<ThresholdDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public List<ThresholdDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<ThresholdDomain> levelList) {
        this.levelList = levelList;
    }

    public ThresholdDomain getDomain() {
        return domain;
    }

    public void setDomain(ThresholdDomain domain) {
        this.domain = domain;
    }

}
