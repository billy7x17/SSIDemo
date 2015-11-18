package com.cloudmaster.cmp.topology.domain;

/**
 * <b>Application describing: 拓扑图告警域</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-14 下午1:53:29
 */
public class TopoAlarmDomain {

    /**
     * 设备ID
     */
    private String agentId;

    /**
     * nvr父节点ID(如果没有，则为null)
     */
    private String nvrId;

    /**
     * vms父节点ID(如果没有，则为null)
     */
    private String vmsId;

    /**
     * 告警等级 (0 - critical(紧急/严重); 1 - major(重要/主要); 2 - minor(一般/次要); 3 - normal(警告/提醒))
     */
    private String alarmGrade;

    /**
     * 告警数量
     */
    private String count;

    /**
     * 告警分组
     */
    private String agentGroup;

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
     * @return the nvrId
     */
    public String getNvrId() {
        return nvrId;
    }

    /**
     * @param nvrId the nvrId to set
     */
    public void setNvrId(String nvrId) {
        this.nvrId = nvrId;
    }

    /**
     * @return the vmsId
     */
    public String getVmsId() {
        return vmsId;
    }

    /**
     * @param vmsId the vmsId to set
     */
    public void setVmsId(String vmsId) {
        this.vmsId = vmsId;
    }

    /**
     * @return the alarmGrade
     */
    public String getAlarmGrade() {
        return alarmGrade;
    }

    /**
     * @param alarmGrade the alarmGrade to set
     */
    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    /**
     * @return the count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * @return the agentGroup
     */
    public String getAgentGroup() {
        return agentGroup;
    }

    /**
     * @param agentGroup the agentGroup to set
     */
    public void setAgentGroup(String agentGroup) {
        this.agentGroup = agentGroup;
    }

}
