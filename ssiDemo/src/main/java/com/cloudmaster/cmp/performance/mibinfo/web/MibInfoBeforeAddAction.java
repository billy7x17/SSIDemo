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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 添加MIB初始化.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-3
 */
public class MibInfoBeforeAddAction extends BaseAction implements IAuthIdGetter {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 设备信息
     */
    private List<DeviceDomain> deviceTypeList;

    /**
     * 父节点mib
     */
    private List<MibinfoDomain> parentMibInfoList;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(MibInfoBeforeAddAction.class);

    /**
     * IBATIC命名.
     */
    private static final String IBATIC_NAMESPACE = "MibInfo";
    
    /**
     * MIB信息
     */
    private MibinfoDomain mibinfoDomain;

    /**
     * 设备类型
     */
    private String typeId;

    /**
     * OID
     */
    private String oid;

    /**
     * mib记录标识
     */
    private String mibId;
   
    /**
     * 设备类型ID.
     */
    private String deviceTypeId;
    
    /**
     * 性能指标单位.
     */
    private Map<String, String> unitMap = null ;

    @SuppressWarnings("unchecked")
    public String beforeAdd() {
        logger.info(getText("function.title") + getText("log.beforeAdd.begin"));
        try {
            setMibUnitValue();
            // 获取设备类型信息
            deviceTypeList = this.getIbatisDAO().getData(IBATIC_NAMESPACE+".getDeviceTypeList", null);
            setParentMibInfoList(new ArrayList<MibinfoDomain>());
            logger.info(getText("function.title") + getText("log.beforeAdd.end"));
        } catch (Exception e) {
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.beforeAdd.error"), e);
            return ERROR;
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
     * 获得父节点.
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getParentMibInfoByDeviceType(){
        MibinfoDomain mibInfo = new MibinfoDomain();
        mibInfo.setIsCollection("1");
        mibInfo.setTypeId(deviceTypeId);
        // 获取父节点mib
        try {
            setParentMibInfoList(this.getIbatisDAO().getData(IBATIC_NAMESPACE+".getMibList", mibInfo));
        } catch (SQLException e) {
            logger.info(errorMsg, e);
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            JSONArray jarry = new JSONArray();
            for (MibinfoDomain parDomain:parentMibInfoList) {
                jarry.add(parDomain);
            }
            pw.write(jarry.toString());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.error(getText("performance.mibinfo.parentId.error"),e);
        }
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
    
    /**
     * oid是否重复判断
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public void oRepeatJudge() {
        logger.info(getText("function.title") + getText("add.function.oRepeatJudge.start"));
        mibinfoDomain = new MibinfoDomain();
        mibinfoDomain.setOid(oid);
        mibinfoDomain.setTypeId(typeId);
        if (null != mibId && !mibId.equals("")) {
            mibinfoDomain.setMibId(mibId);
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            // 使用typeId和oid查询该设备类型下是否有重复的oid
            List<MibinfoDomain> li = ibatisDAO.getData(IBATIC_NAMESPACE+".getMibInfoByTypeIdAndOid", mibinfoDomain);
            if (null != li && !li.isEmpty()) {
                pw.write(getText("performance.mibinfo.oid.exist"));
            } else {
                pw.write("");
            }
            logger.info(getText("function.title") + getText("add.function.oRepeatJudge.success"));
        } catch (Exception e) {
            logger.error(
                    getText("function.title") + getText("add.function.oRepeatJudge.exception"), e);
        }
    }


    public String getTypeId() {
        return typeId;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return the deviceTypeList
     */
    public List<DeviceDomain> getDeviceTypeList() {
        return deviceTypeList;
    }

    /**
     * @param deviceTypeList the deviceTypeList to set
     */
    public void setDeviceTypeList(List<DeviceDomain> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public void setParentMibInfoList(List<MibinfoDomain> parentMibInfoList) {
        this.parentMibInfoList = parentMibInfoList;
    }

    public List<MibinfoDomain> getParentMibInfoList() {
        return parentMibInfoList;
    }

    public MibinfoDomain getMibinfoDomain() {
        return mibinfoDomain;
    }

    public void setMibinfoDomain(MibinfoDomain mibinfoDomain) {
        this.mibinfoDomain = mibinfoDomain;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibId(String mibId) {
        this.mibId = mibId;
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

    
}
