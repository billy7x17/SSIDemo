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
package com.cloudmaster.cmp.performance.mibinfo.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * MIB修改
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibInfoBeforeUpdateAction extends OperationLogAction implements IAuthIdGetter {


    /**
     * MIB ID
     */
    private String mibId;

    /**
     * MIB信息
     */
    private MibinfoDomain mibinfoDomain;

    /**
     * 设备信息
     */
    private List<DeviceDomain> deviceListType;

    /**
     * 父节点mib
     */
    private List<MibinfoDomain> parentMibInfoList;
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(MibInfoBeforeUpdateAction.class);

    /**
     * IBATIC命名.
     */
    private static final String IBATIC_NAMESPACE = "MibInfo";

    /**
     * 性能指标单位.
     */
    private Map<String, String> unitMap = null;
    
    /**
     * 类型ID.
     */
    private String typeId;
    
    /**
     * 分组信息.
     */
    private List<MibinfoDomain> groupList = new ArrayList<MibinfoDomain>();

    @SuppressWarnings("unchecked")
    public String beforeUpdate() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));
        try {
            // 获取MIB信息
            mibinfoDomain = (MibinfoDomain) this.ibatisDAO.getSingleRecord(
                    "MibInfo.getMibinfoById", mibId);

            if (mibinfoDomain == null) {
                msg = this.getText("mib.search.action.error");
                logger.info(msg);
                return "input";
            }
            setMibUnitValue();
            
            groupList = ibatisDAO.getData(IBATIC_NAMESPACE+".getMibGroupInfo", mibinfoDomain.getTypeId());
            
            // 获取设备信息
            setDeviceListType(this.getIbatisDAO().getData(IBATIC_NAMESPACE+".getDeviceTypeList", null));

            // 查询集合MIB库，并且过滤自身
            MibinfoDomain mibInfo = new MibinfoDomain();
            mibInfo.setIsUpdate("1");
            mibInfo.setIsCollection("1");
            mibInfo.setMibId(mibinfoDomain.getMibId());
            mibInfo.setTypeId(mibinfoDomain.getTypeId());
            // 获取父节点mib
            setParentMibInfoList(this.getIbatisDAO().getData("MibInfo.getUpdateMibList", mibInfo));
            if (getDeviceListType() == null || getDeviceListType().size() == 0) {
                msg = this.getText("mib.before.mibDevice.action.error");
                logger.info(msg);
            }
            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
        }
        return SUCCESS;
    }

    /**
     * 设置性能指标单位内容.
     */
    private void setMibUnitValue(){
        unitMap = new TreeMap<String, String>();
        unitMap.put("0", getText("mibinfo.mibUnit.0"));//%
        unitMap.put("1", getText("mibinfo.mibUnit.1"));//个
        unitMap.put("2", getText("mibinfo.mibUnit.2"));//MHz
        unitMap.put("3", getText("mibinfo.mibUnit.3"));//MB
        unitMap.put("4", getText("mibinfo.mibUnit.4"));//GB
        unitMap.put("5", getText("mibinfo.mibUnit.5"));//Bytes/sec
        unitMap.put("6", getText("mibinfo.mibUnit.6"));//KB
        unitMap.put("7", getText("mibinfo.mibUnit.7"));//s
        unitMap.put("8", getText("mibinfo.mibUnit.8"));//packets/sec
        unitMap.put("9", getText("mibinfo.mibUnit.9"));//无单位
        unitMap.put("10", getText("mibinfo.mibUnit.10"));//degrees
        unitMap.put("11", getText("mibinfo.mibUnit.11"));//Volts
        unitMap.put("12", getText("mibinfo.mibUnit.12"));//RPM
        unitMap.put("13", getText("mibinfo.mibUnit.13"));//Watts
        unitMap.put("14", getText("mibinfo.mibUnit.14"));//Mbps
        unitMap.put("15", getText("mibinfo.mibUnit.15"));//Byte
        unitMap.put("16", getText("mibinfo.mibUnit.16"));//℃
        unitMap.put("17", getText("mibinfo.mibUnit.17"));//IOPS
    }
    
    
    /**
     * 获得指标分组信息.
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getMibGroupInfo(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            PrintWriter out = response.getWriter();
            List<MibinfoDomain> list = ibatisDAO.getData(IBATIC_NAMESPACE+".getMibGroupInfo", typeId);
            if(!list.isEmpty()){
                JSONArray json  = new JSONArray();
                for(MibinfoDomain mib :list){
                    json.add(mib);
                }
                out.write(json.toString());
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(getText("mibinfo.indexGroup.error"), e);
        }
    }
    

    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibinfoDomain(MibinfoDomain mibinfoDomain) {
        this.mibinfoDomain = mibinfoDomain;
    }

    public MibinfoDomain getMibinfoDomain() {
        return mibinfoDomain;
    }

    /**
     * @return the deviceListType
     */
    public List<DeviceDomain> getDeviceListType() {
        return deviceListType;
    }

    /**
     * @param deviceListType the deviceListType to set
     */
    public void setDeviceListType(List<DeviceDomain> deviceListType) {
        this.deviceListType = deviceListType;
    }

    public void setParentMibInfoList(List<MibinfoDomain> parentMibInfoList) {
        this.parentMibInfoList = parentMibInfoList;
    }

    public List<MibinfoDomain> getParentMibInfoList() {
        return parentMibInfoList;
    }

    /**
     * @return the unitMap
     */
    public Map<String, String> getUnitMap() {
        return unitMap;
    }

    /**
     * @param unitMap the unitMap to set
     */
    public void setUnitMap(Map<String, String> unitMap) {
        this.unitMap = unitMap;
    }

    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the groupList
     */
    public List<MibinfoDomain> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(List<MibinfoDomain> groupList) {
        this.groupList = groupList;
    }
    
    
}
