package com.cloudmaster.cmp.performance.ipsan.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * MIB采集性能实体.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-6-26
 */
public class PerfInfoDomain implements Serializable {

    /**
     * 标识.
     */
    private static final long serialVersionUID = 1952283365893016487L;

    /**
     * 指标名称.
     */
    private String tagetName;

    /**
     * 性能指标值.
     */
    private String tagetValue;

    /**
     * 设备ID.
     */
    private String agentId;

    /**
     * 设备IP.
     */
    private String agentIp;

    /**
     * 采集设备的名称.
     */
    private String agentName;

    /**
     * 预留字段.
     */
    private String agentType;

    /**
     * 设备厂家.
     */
    private String deviceComp;

    /**
     * 告警入库时间.
     */
    private String createTime;
    
    /**
     * 图片展示使用的时间.
     */
    private Date showTime;

    /**
     * MIB设备类型.
     */
    private String mibType;

    /**
     * MIB主键.
     */
    private String oidId;

    /**
     * 采集OID.
     */
    private String oid;

    /**
     * 父节点.
     */
    private String parentId;

    /**
     * 索引值.(同个设备多个属性的值)
     */
    private String indexNumber;

    /**
     * 采集返回信息.
     */
    private String prefMessage;

    /**
     * 性能名称.
     */
    private String mibName;
    
    /**
     * 指标组.
     */
    private String mibGroup;
    
    /**
     * 指标组值.
     */
    private String mibGroupIndex;
    
    /**
     * 单位.
     */
    private String mibUnit;
    
    /**
     * 是否是展示折线 1-展示,0-不展示.
     */
    private String ifShowLine;
    
    /**
     * 时间间隔,用于定时查询性能增量
     */
    private int time;
    
    /**
     * @return the tagetName
     */
    public String getTagetName() {
        return tagetName;
    }

    /**
     * @param tagetName the tagetName to set
     */
    public void setTagetName(String tagetName) {
        this.tagetName = tagetName;
    }

    /**
     * @return the tagetValue
     */
    public String getTagetValue() {
        return tagetValue;
    }

    /**
     * @param tagetValue the tagetValue to set
     */
    public void setTagetValue(String tagetValue) {
        this.tagetValue = tagetValue;
    }

    /**
     * @return the agentId
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * @param agentId the agentId to set
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * @return the agentIp
     */
    public String getAgentIp() {
        return agentIp;
    }

    /**
     * @param agentIp the agentIp to set
     */
    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }

    /**
     * @return the agentName
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * @param agentName the agentName to set
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * @return the agentType
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * @param agentType the agentType to set
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * @return the deviceComp
     */
    public String getDeviceComp() {
        return deviceComp;
    }

    /**
     * @param deviceComp the deviceComp to set
     */
    public void setDeviceComp(String deviceComp) {
        this.deviceComp = deviceComp;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the mibType
     */
    public String getMibType() {
        return mibType;
    }

    /**
     * @param mibType the mibType to set
     */
    public void setMibType(String mibType) {
        this.mibType = mibType;
    }

    /**
     * @return the oidId
     */
    public String getOidId() {
        return oidId;
    }

    /**
     * @param oidId the oidId to set
     */
    public void setOidId(String oidId) {
        this.oidId = oidId;
    }

    /**
     * @return the oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid the oid to set
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the indexNumber
     */
    public String getIndexNumber() {
        return indexNumber;
    }

    /**
     * @param indexNumber the indexNumber to set
     */
    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    /**
     * @return the prefMessage
     */
    public String getPrefMessage() {
        return prefMessage;
    }

    /**
     * @param prefMessage the prefMessage to set
     */
    public void setPrefMessage(String prefMessage) {
        this.prefMessage = prefMessage;
    }

    /**
     * @return the mibName
     */
    public String getMibName() {
        return mibName;
    }

    /**
     * @param mibName the mibName to set
     */
    public void setMibName(String mibName) {
        this.mibName = mibName;
    }

    /**
     * @return the mibGroup
     */
    public String getMibGroup() {
        return mibGroup;
    }

    /**
     * @param mibGroup the mibGroup to set
     */
    public void setMibGroup(String mibGroup) {
        this.mibGroup = mibGroup;
    }

    /**
     * @return the mibUnit
     */
    public String getMibUnit() {
        return mibUnit;
    }

    /**
     * @param mibUnit the mibUnit to set
     */
    public void setMibUnit(String mibUnit) {
        this.mibUnit = mibUnit;
    }

    /**
     * @return the showTime
     */
    public Date getShowTime() {
        return showTime;
    }

    /**
     * @param showTime the showTime to set
     */
    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    /**
     * @return the mibGroupIndex
     */
    public String getMibGroupIndex() {
        return mibGroupIndex;
    }

    /**
     * @param mibGroupIndex the mibGroupIndex to set
     */
    public void setMibGroupIndex(String mibGroupIndex) {
        this.mibGroupIndex = mibGroupIndex;
    }

    /**
     * @return the ifShowLine
     */
    public String getIfShowLine() {
        return ifShowLine;
    }

    /**
     * @param ifShowLine the ifShowLine to set
     */
    public void setIfShowLine(String ifShowLine) {
        this.ifShowLine = ifShowLine;
    }

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    
}
