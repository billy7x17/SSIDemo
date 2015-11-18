package com.neusoft.nmr.octopus.business.alarm;

import java.io.Serializable;

public class Alarm implements Serializable {

	private static final long serialVersionUID = -5078102479242346345L;

	/** 告警编号 */
	private Integer id;

	/** 资源编号 ! */
	private String rid;

	/** 告警级别! */
	private Integer lid;

	/** 告警ID! */
	private String nid;

	/** 确认用户编号 */
	private Integer auid;

	/** 终止用户编号 */
	private Integer tuid;

	/** 发生时间! */
	private String eventTime;

	/** 发生原因 */
	private String probeCause;

	/** 告警标题 */
	private String title;

	/** 告警状态 */
	private Integer status;

	/** 告警正文 */
	private String text;

	/** 创建时间 */
	private String createTime;

	/** 告警来源 */
	private Integer origin;

	/** 清除时间 */
	private String clearTime;

	/** 确认时间 */
	private String acknowledgementTime;

	/** 终止时间 */
	private String terminationTime;

	/** 告警类别! */
	private String type;

	/** 级别调整 ! */
	private Integer original;

	/** 父告警编号 */
	private Integer pid;

	/** 以下是公有云私有属性 */

	/** 公有云上报告警OID */
	private String oid;

	/** 告警设备类型，页面展示 */
	private String deviceType;

	/** 告警影响描述 */
	private String alarmImpact;

	/** 清除人员 */
	private String cuid;

	/** 确认状态 */
	private String confirmStatus;

	/** 清除状态 */
	private String clearStatus;

	/** 告警标题ID */
	private String alarmTitleID;

	/** 发生告警的资源实例ID-业务告警时书写业务系统标识ID */
	private String objectID;

	/** 发生告警的设备或系统名称 */
	private String systemName;
	
	/** 发生告警的源对象的IP地址*/
	private String SourceIP;

	public String getSourceIP() {
		return SourceIP;
	}

	public void setSourceIP(String sourceIP) {
		SourceIP = sourceIP;
	}

	/** 公有云私有属性 END */

	public String getAlarmTitleID() {
		return alarmTitleID;
	}

	public void setAlarmTitleID(String alarmTitleID) {
		this.alarmTitleID = alarmTitleID;
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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAlarmImpact() {
		return alarmImpact;
	}

	public void setAlarmImpact(String alarmImpact) {
		this.alarmImpact = alarmImpact;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(String clearStatus) {
		this.clearStatus = clearStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getAuid() {
		return auid;
	}

	public void setAuid(Integer auid) {
		this.auid = auid;
	}

	public Integer getTuid() {
		return tuid;
	}

	public void setTuid(Integer tuid) {
		this.tuid = tuid;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getProbeCause() {
		return probeCause;
	}

	public void setProbeCause(String probeCause) {
		this.probeCause = probeCause;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

	public String getAcknowledgementTime() {
		return acknowledgementTime;
	}

	public void setAcknowledgementTime(String acknowledgementTime) {
		this.acknowledgementTime = acknowledgementTime;
	}

	public String getTerminationTime() {
		return terminationTime;
	}

	public void setTerminationTime(String terminationTime) {
		this.terminationTime = terminationTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOriginal() {
		return original;
	}

	public void setOriginal(Integer original) {
		this.original = original;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
}
