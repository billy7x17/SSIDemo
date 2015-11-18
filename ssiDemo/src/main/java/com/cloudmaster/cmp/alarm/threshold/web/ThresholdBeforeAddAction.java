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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.threshold.dao.AlarmLevelDomain;
import com.cloudmaster.cmp.alarm.threshold.dao.ThresholdContentDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceTypeDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 添加阈值初始化
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdBeforeAddAction extends BaseAction implements IAuthIdGetter {

    /**
     * 设备类型ID
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
     * 设备类型信息
     */
    private List<DeviceTypeDomain> deviceListType;

    /**
     * 告警等级
     */
    private List<AlarmLevelDomain> levelList;

    /**
     * MIB信息
     */
    private List<MibinfoDomain> mibList;

    /**
     * 阈值标题
     */
    private List<ThresholdContentDomain> thresholdContentDomainList;

    private JSONArray jsonarray;

    private JSONObject jsonobject;

    /**
     * MIB对象
     */
    private MibinfoDomain mibinfoDomain;

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(ThresholdBeforeAddAction.class);

    /* 页面传递过来的告警唯一标识，用来标识用户输入的是否唯一 */
    private String alarmIdentityID;

    /**
     * 业务实例信息
     */
    private List<ThresholdContentDomain> systemList;

    @SuppressWarnings("unchecked")
    public String beforeAdd() {

        try {
            logger.info(getText("function.title") + getText("before.add.function"));
            // 获取设备信息
            setDeviceListType(this.getIbatisDAO().getData("DeviceInfo.getDeviceTypeList", null));
            // 获取告警等级
            setLevelList(this.getIbatisDAO().getData("ThresholdInfo.getAlarmLevelList", null));

            // 业务实例
            systemList = ibatisDAO.getData("ThresholdInfo.getSystemList", null);

            if (getDeviceListType() == null || getDeviceListType().size() == 0) {
                errorMsg = this.getText("threshold.before.thresholdDevice.action.error");
                logger.info(errorMsg);
            } else if (getLevelList() == null || getLevelList().size() == 0) {
                errorMsg = this.getText("threshold.before.thresholdLevel.action.error");
                logger.info(errorMsg);
            }
        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError");
            logger.error(errorMsg, e);
        }

        return forward;
    }

    /**
     * ajax获取设备下的指标
     * @return
     */
    public void getDeviceOID() {
        try {
            logger.info(getText("function.title") + getText("before.add.function"));
            MibinfoDomain bean = new MibinfoDomain();
            bean.setTypeId(deviceId);
            List<MibinfoDomain> deviceMibLi = ibatisDAO.getData("MibInfo.getMibList", bean);

            String titleCondition = (String) ibatisDAO.getSingleRecord(
                    "ThresholdInfo.getDeviceTitleCondition", deviceId);

            ThresholdContentDomain titleParam = new ThresholdContentDomain();
            titleParam.setType("(type='2' or type='4')");
            titleParam.setAlarmTitleCondition(titleCondition);
            List<ThresholdContentDomain> deviceTitleLi = ibatisDAO.getData(
                    "ThresholdInfo.getThresholdContentList", titleParam);

            JSONArray mibArr = new JSONArray();
            if (deviceMibLi == null || deviceMibLi.size() == 0) {
                errorMsg = this.getText("threshold.before.Mid.action.error");
                logger.info(errorMsg);
            } else {
                for (int i = 0; i < deviceMibLi.size(); i++) {
                    MibinfoDomain mibBean = deviceMibLi.get(i);
                    JSONObject mibObj = new JSONObject();
                    mibObj.put("id", mibBean.getMibId());
                    mibObj.put("name", mibBean.getMibName());
                    mibArr.add(mibObj);
                }
            }

            JSONArray titleArr = new JSONArray();
            if (deviceTitleLi == null || deviceTitleLi.size() == 0) {
                errorMsg = this.getText("threshold.before.Mid.action.error");
                logger.info(errorMsg);
            } else {
                for (int i = 0; i < deviceTitleLi.size(); i++) {
                    ThresholdContentDomain titleBean = deviceTitleLi.get(i);
                    JSONObject titleObj = new JSONObject();
                    titleObj.put("tcId", titleBean.getTcId());
                    titleObj.put("tcTitle", titleBean.getTcTitle());
                    titleArr.add(titleObj);
                }
            }

            Map<String, JSONArray> jsonMap = new HashMap<String, JSONArray>();
            jsonMap.put("mib", mibArr);
            jsonMap.put("title", titleArr);
            JSONObject jsonObj = JSONObject.fromObject(jsonMap);

            logger.debug("jsonStr:" + jsonObj.toString());
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonObj.toString());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            errorMsg = this.getText("common.message.addError");
            logger.error(errorMsg, e);
        }
    }

    /**
     * ajax校验上报告警OID唯一
     */
    public void validateAlarmIdentityID() {
        String result = "";
        try {
            int count = ibatisDAO.getCount("ThresholdInfo.existCount", alarmIdentityID);
            if (count > 0) {
                // if (true) {
                result = "exist";
            }
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
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

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
    }

    public List<ThresholdContentDomain> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<ThresholdContentDomain> systemList) {
        this.systemList = systemList;
    }

}
