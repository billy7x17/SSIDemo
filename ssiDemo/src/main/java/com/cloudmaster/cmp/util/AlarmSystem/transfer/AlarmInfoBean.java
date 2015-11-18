/*******************************************************************************
 * @(#)AlarmInfoBean.java 2010-12-3
 *
 * Copyright 2010 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is cmagent to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.AlarmSystem.transfer;

/**
 * @author <a href="mailto:majch@neusoft.com">wang-rongguang </a>
 * @version $Revision 1.0 $ 2010-12-3 上午10:26:48
 */
public class AlarmInfoBean {

	private int rowid;

	private String alarmLevel;
	
	private String alarmLevelOriginal;

	private String alarmTitle;

	private String alarmContent;

	private String occurTime;

	private String createTime;

	private String confirmMan;

	private String troubleShooting;

	private String eventCode;

	private String struts;

	private String clearTime;

	private String alarmIndex;

	private String agentType;

	private String alarmOid;

	private String agentId;

	private String agentIp;

	private String alarmType;

	private String thresholdId;

	/**
	 * 告警唯一标识，标准mibId格式
	 */
	private String AlarmIdentityID;

	/**
	 * 告警设备CMDB标识
	 */
	private String AlarmImpact;

	/**
	 * 告警标题ID
	 */
	private String alarmTitleId;

	/**
	 * 发生告警的资源实例ID 业务告警时书写业务系统标识ID
	 */
	private String objectID;

	/**
	 * 发生告警的设备或系统名称。
	 */
	private String systemName;

	/**
	 * 阀值型告警阀值下限
	 */
	private String minVlaue;

	/**
	 * 阀值型告警阀值上限
	 */
	private String maxVlaue;

	/**
	 * 阀值判断条件，1-等于，2-不等于，3-区间
	 */
	private int perCondition;

	/**
	 * @return Returns the perCondition.
	 */
	public int getPerCondition() {
		return perCondition;
	}

	/**
	 * @param perCondition The perCondition to set.
	 */
	public void setPerCondition(int perCondition) {
		this.perCondition = perCondition;
	}

	public String getAlarmIdentityID() {
		return AlarmIdentityID;
	}

	public void setAlarmIdentityID(String alarmIdentityID) {
		AlarmIdentityID = alarmIdentityID;
	}

	public String getAlarmImpact() {
		return AlarmImpact;
	}

	public void setAlarmImpact(String alarmImpact) {
		AlarmImpact = alarmImpact;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmTitle() {
		return alarmTitle;
	}

	public void setAlarmTitle(String alarmTitle) {
		this.alarmTitle = alarmTitle;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getConfirmMan() {
		return confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	public String getTroubleShooting() {
		return troubleShooting;
	}

	public void setTroubleShooting(String troubleShooting) {
		this.troubleShooting = troubleShooting;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getStruts() {
		return struts;
	}

	public void setStruts(String struts) {
		this.struts = struts;
	}

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

	public String getAlarmIndex() {
		return alarmIndex;
	}

	public void setAlarmIndex(String alarmIndex) {
		this.alarmIndex = alarmIndex;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getAlarmOid() {
		return alarmOid;
	}

	public void setAlarmOid(String alarmOid) {
		this.alarmOid = alarmOid;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}

	public String getAgentIp() {
		return agentIp;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setThresholdId(String thresholdId) {
		this.thresholdId = thresholdId;
	}

	public String getThresholdId() {
		return thresholdId;
	}

	public String getAlarmTitleId() {
		return alarmTitleId;
	}

	public void setAlarmTitleId(String alarmTitleId) {
		this.alarmTitleId = alarmTitleId;
	}

	public String getMinVlaue() {
		return minVlaue;
	}

	public void setMinVlaue(String minVlaue) {
		this.minVlaue = minVlaue;
	}

	public String getMaxVlaue() {
		return maxVlaue;
	}

	public void setMaxVlaue(String maxVlaue) {
		this.maxVlaue = maxVlaue;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

    public String getAlarmLevelOriginal() {
        return alarmLevelOriginal;
    }

    public void setAlarmLevelOriginal(String alarmLevelOriginal) {
        this.alarmLevelOriginal = alarmLevelOriginal;
    }
	
}
