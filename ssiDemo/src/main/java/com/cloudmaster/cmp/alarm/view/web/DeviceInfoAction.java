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
package com.cloudmaster.cmp.alarm.view.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceInfoAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3139437927841976875L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceInfoAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 异常消息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = SUCCESS;

    /**
     * 设备CI项信息列表
     */
    private List ciList;

    /**
     * 设备关联项信息列表
     */
    private Map relationMap;

    /**
     * 具体设备信息列表
     */
    private List deviceList;

    /**
     * 设备业务信息列表
     */
    private List businessList;

    /**
     * 获取具体设备信息，cmdbID
     */
    private String reLationCmdbID;

    /**
     * 获取具体设备信息，关联项
     */
    private String relationValue;

    /**
     * 查看设备信息
     * @return
     */
    public String beforeDeviceInfo() {
        logger.info(getText("function.title") + getText("log.device.begin"));
        String opParam[] = { domain.getAlarmIP() };
        ciList = new ArrayList();
        relationMap = new HashMap();
        businessList = new ArrayList();
        try {

            AlarmViewDomain deviceInfo = getDeviceInfo(domain);

            // zoneInstance = new AlarmViewDomain();
            // zoneInstance.setCmdbID("-1371747340726373715");
            // zoneInstance.setLocalID("CIDC-R-01-002-VM-00000071");
            // zoneInstance.setResourceType("CIDC-RT-VM");

            if (null == deviceInfo || null == deviceInfo.getCmdbID()
                    || deviceInfo.getCmdbID().equals("")) {
                logger.info(getText("function.title")
                        + " DeviceInfo not in zone_cluster_instance_tab,alarmIp:"
                        + domain.getAlarmIP());
                msg = getText("message.device.db.notFound");
            } else {
                String cmdbID = deviceInfo.getCmdbID();
                reLationCmdbID = cmdbID;
                String resourceType = deviceInfo.getResourceType();
                String localID = deviceInfo.getLocalID();

                logger.info(getText("function.title") + " DeviceInfo,cmdbID:" + cmdbID
                        + ",resourceType:" + resourceType + ",localID:" + localID);

                // 本地数据库获取业务信息
                if (null != resourceType && resourceType.equals("CIDC-RT-SRV")) {
                    List businessLi = (List) ibatisDAO.getData(
                            IBATIS_NAMESPACE + ".getSrvBusiness", deviceInfo);
                    if (null != businessLi && !businessLi.isEmpty()) {
                        businessList = businessLi;
                    } else {
                        logger.info(getText("function.title")
                                + " Device business not in db,LocalTable_ID_Ref:"
                                + domain.getLocalID());
                    }
                }
                if (null != resourceType && resourceType.equals("CIDC-RT-VM")) {
                    AlarmViewDomain businessBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(
                            IBATIS_NAMESPACE + ".getVmBusiness", deviceInfo);
                    if (null != businessBean && null != businessBean.getBusiness()) {
                        businessList.add(businessBean);
                    } else {
                        logger.info(getText("function.title")
                                + " Device business not in db,LocalTable_ID_Ref:"
                                + domain.getLocalID());
                    }
                }

            }

            operationInfo = getText("oplog.device.success", opParam);
            logger.info(getText("function.title") + getText("log.device.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.device.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.device.error"), e);
            errorMsg = getText("message.device.systemError");
        }
        return forward;
    }


    /**
     * 从数据库中获取设备信息
     * @param bean
     * @return
     */
    private AlarmViewDomain getDeviceInfo(AlarmViewDomain bean) {

        AlarmViewDomain resultBean = null;
        try {
            String ip = bean.getAlarmIP();
            // 0:mals_nm_alarm_device_t, 1:hot_event_tab, 2:vm_hot_event_tab,
            // 3:nagios.nagios_hoststatus, 4:nagios.nagios_servicestatus
            String sourceType = bean.getAlarmSourceType();

            if (sourceType.equals("0")) {
                resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getDeviceInfoType0", domain);
            }
            if (sourceType.equals("1")) {
                resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getDeviceInfoType1", domain);
            }
            if (sourceType.equals("2")) {
                resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getDeviceInfoType2", domain);
            }
            if (sourceType.equals("3")) {
                resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getDeviceInfoType3", domain);
            }
            if (sourceType.equals("4")) {
                resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".getDeviceInfoType4", domain);
            }
        } catch (Exception e) {
            logger.info(getText("function.title") + " get devieInfo from db error ", e);
        }
        return resultBean;
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List getCiList() {
        return ciList;
    }

    public void setCiList(List ciList) {
        this.ciList = ciList;
    }

    public Map getRelationMap() {
        return relationMap;
    }

    public void setRelationMap(Map relationMap) {
        this.relationMap = relationMap;
    }

    public List getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List businessList) {
        this.businessList = businessList;
    }

    public List getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List deviceList) {
        this.deviceList = deviceList;
    }

    public String getReLationCmdbID() {
        return reLationCmdbID;
    }

    public void setReLationCmdbID(String reLationCmdbID) {
        this.reLationCmdbID = reLationCmdbID;
    }

    public String getRelationValue() {
        return relationValue;
    }

    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue;
    }

}
