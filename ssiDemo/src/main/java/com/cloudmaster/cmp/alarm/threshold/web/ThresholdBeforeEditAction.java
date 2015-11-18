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

import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudmaster.cmp.alarm.threshold.dao.AlarmLevelDomain;
import com.cloudmaster.cmp.alarm.threshold.dao.PerConditionDomain;
import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdContentDomain;
import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceTypeDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 阈值初始化添加
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdBeforeEditAction extends BaseAction implements IAuthIdGetter {

    /**
     * 设备ID
     */
    private String deviceId;

    private String result;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * 告警等级
     */
    private List<AlarmLevelDomain> levelList;

    /**
     * MIB信息
     */
    private List<MibinfoDomain> mibList;

    /**
     * 设备类型信息
     */
    private List<DeviceTypeDomain> deviceListType;

    /**
     * 阈值 ID
     */
    private String thresholdId;

    private JSONArray jsonarray;

    private JSONObject jsonobject;

    /**
     * 阈值标题
     */
    private List<ThresholdContentDomain> thresholdContentDomainList;

    /**
     * MIB对象
     */
    private MibinfoDomain mibinfoDomain;

    /**
     * 阈值
     */
    private ThresholdDomain thresholdDomain;

    /**
     * 告警级别
     */
    private AlarmLevelDomain alarmLevelDomain;

    /**
     * 告警符号
     */
    private List<PerConditionDomain> perConditionDomainList;

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdBeforeEditAction.class);

    /**
     * 业务实例信息
     */
    private List<ThresholdContentDomain> systemList;

    @SuppressWarnings("unchecked")
    public String beforeEdit() {

        try {
            logger.info(getText("function.title") + getText("before.edit.function"));
            // 获取阈值信息
            thresholdDomain = (ThresholdDomain) ibatisDAO.getSingleRecord("ThresholdInfo.detail",
                    thresholdDomain);
            // 获取设备信息
            setDeviceListType(this.getIbatisDAO().getData("DeviceInfo.getDeviceTypeList", null));

            // 获取阈值标题
            String titleCondition = (String) ibatisDAO.getSingleRecord(
                    "ThresholdInfo.getDeviceTitleCondition", thresholdDomain.getTypeId());

            ThresholdContentDomain titleParam = new ThresholdContentDomain();
            titleParam.setType("(type='2' or type='4')");
            titleParam.setAlarmTitleCondition(titleCondition);
            thresholdContentDomainList = ibatisDAO.getData("ThresholdInfo.getThresholdContentList",
                    titleParam);

            alarmLevelDomain.setLevelId(thresholdDomain.getLevel());
            // 获取告警等级
            levelList = this.getIbatisDAO().getData("ThresholdInfo.getAlarmLevelList", null);

            // 业务实例
            systemList = ibatisDAO.getData("ThresholdInfo.getSystemList", null);

            if (deviceId != null && !"".equals(deviceId)) {
                getMibinfoDomain().setTypeId(deviceId);
                mibinfoDomain.setSortName("t1.MIB_NAME");
                mibinfoDomain.setSortOrder("desc");
                mibList = this.getIbatisDAO().getData("MibInfo.getMibList", getMibinfoDomain());
                for (int i = 0; i < mibList.size(); i++) {
                    setMibinfoDomain(mibList.get(i));
                    jsonobject.put("id", getMibinfoDomain().getMibId());
                    jsonobject.put("name", getMibinfoDomain().getMibName());
                    jsonarray.add(jsonobject);
                }
                result = jsonarray.toString();
            }

            if (getDeviceListType() == null || getDeviceListType().size() == 0) {
                msg = this.getText("threshold.before.thresholdDevice.action.error");
                logger.info(msg);
            } else if (getLevelList() == null || getLevelList().size() == 0) {
                msg = this.getText("threshold.before.thresholdLevel.action.error");
                logger.info(msg);
            }
        } catch (Exception e) {
            errorMsg = this.getText("common.message.editError");
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

    public void setLevelList(List<AlarmLevelDomain> levelList) {
        this.levelList = levelList;
    }

    public List<AlarmLevelDomain> getLevelList() {
        return levelList;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setMibList(List<MibinfoDomain> mibList) {
        this.mibList = mibList;
    }

    public List<MibinfoDomain> getMibList() {
        return mibList;
    }

    public void setJsonarray(JSONArray jsonarray) {
        this.jsonarray = jsonarray;
    }

    public JSONArray getJsonarray() {
        return jsonarray;
    }

    public void setJsonobject(JSONObject jsonobject) {
        this.jsonobject = jsonobject;
    }

    public JSONObject getJsonobject() {
        return jsonobject;
    }

    public void setMibinfoDomain(MibinfoDomain mibinfoDomain) {
        this.mibinfoDomain = mibinfoDomain;
    }

    public MibinfoDomain getMibinfoDomain() {
        return mibinfoDomain;
    }

    public void setThresholdDomain(ThresholdDomain thresholdDomain) {
        this.thresholdDomain = thresholdDomain;
    }

    public ThresholdDomain getThresholdDomain() {
        return thresholdDomain;
    }

    public void setThresholdId(String thresholdId) {
        this.thresholdId = thresholdId;
    }

    public String getThresholdId() {
        return thresholdId;
    }

    public void setAlarmLevelDomain(AlarmLevelDomain alarmLevelDomain) {
        this.alarmLevelDomain = alarmLevelDomain;
    }

    public AlarmLevelDomain getAlarmLevelDomain() {
        return alarmLevelDomain;
    }

    public void setPerConditionDomainList(List<PerConditionDomain> perConditionDomainList) {
        this.perConditionDomainList = perConditionDomainList;
    }

    public List<PerConditionDomain> getPerConditionDomainList() {
        if (perConditionDomainList == null || perConditionDomainList.size() == 0) {
            perConditionDomainList = new LinkedList<PerConditionDomain>();
            PerConditionDomain pc2 = new PerConditionDomain();
            pc2.setPerId("1");
            pc2.setPerValue("大于等于");
            PerConditionDomain pc3 = new PerConditionDomain();
            pc3.setPerId("2");
            pc3.setPerValue("大于");
            PerConditionDomain pc4 = new PerConditionDomain();
            pc4.setPerId("3");
            pc4.setPerValue("小于");
            PerConditionDomain pc5 = new PerConditionDomain();
            pc5.setPerId("4");
            pc5.setPerValue("小于等于");
            perConditionDomainList.add(pc2);
            perConditionDomainList.add(pc3);
            perConditionDomainList.add(pc4);
            perConditionDomainList.add(pc5);
        }
        return perConditionDomainList;
    }

    public void setDeviceListType(List<DeviceTypeDomain> deviceListType) {
        this.deviceListType = deviceListType;
    }

    public List<DeviceTypeDomain> getDeviceListType() {
        return deviceListType;
    }

    public void setThresholdContentDomainList(
            List<ThresholdContentDomain> thresholdContentDomainList) {
        this.thresholdContentDomainList = thresholdContentDomainList;
    }

    public List<ThresholdContentDomain> getThresholdContentDomainList() {
        return thresholdContentDomainList;
    }

    public List<ThresholdContentDomain> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<ThresholdContentDomain> systemList) {
        this.systemList = systemList;
    }

}
