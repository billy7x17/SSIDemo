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
package com.cloudmaster.cmp.ITIL.cronexpression.dao;

import com.cloudmaster.cmp.web.BaseDomain;

/**
 * @author <a href="mailto:na.x@neusoft.com"> na.x </a>
 * @version 1.0.0 18 Mar 2012
 */
public class CronExpressionManagerDomain extends BaseDomain {

	/**
	 * 任务id
	 */
	private String taskID;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 时间表达式
	 */
	private String cronExpression;

	/**
	 * 任务创建时间
	 */
	private String addTime;

	/**
	 * 任务修改时间
	 */
	private String updateTime;

	/**
	 * 任务类型
	 */
	private String taskType;

	/**
	 * spring中配置的beanID
	 */
	private String beanID;

	/**
	 * 任务参数
	 */
	private String taskParam;

	/**
	 * 任务状态
	 */
	private String taskStatus;

	/**
	 * 任务删除标识
	 */
	private String deleteFlag;

	/**
	 * 查询开始时间
	 */
	private String startTime = "";

	/**
	 * 查询结束时间
	 */
	private String endTime = "";

	/**
	 * 任务执行时间
	 */
	private String executeTime;

	/**
	 * 任务执行结果
	 */
	private String executeResult;

	/**
	 * 任务执行日志
	 */
	private String executeLog;

	/**
	 * 2012-07-17 zhaoc 添加 排序名称
	 * @return
	 */
	private String sortName;

	/**
	 * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
	 * @return
	 */
	private String sortOrder;

	/**
	 * flexigrid查询字段名
	 */
	private String qtype;

	/**
	 * flexigrid查询字段值
	 */
	private String query;

	/**
	 * 子系统标识
	 */
	private String ssID;

	/**
	 * @return Returns the ssID.
	 */
	public String getSsID() {
		return ssID;
	}

	/**
	 * @param ssID The ssID to set.
	 */
	public void setSsID(String ssID) {
		this.ssID = ssID;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(String taskParam) {
		this.taskParam = taskParam;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public String getExecuteLog() {
		return executeLog;
	}

	public void setExecuteLog(String executeLog) {
		this.executeLog = executeLog;
	}

	/**
	 * @return Returns the beanID.
	 */
	public String getBeanID() {
		return beanID;
	}

	/**
	 * @param beanID The beanID to set.
	 */
	public void setBeanID(String beanID) {
		this.beanID = beanID;
	}

}
