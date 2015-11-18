package com.cloudmaster.cmp.alarm.view.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.operationlog.OperationLogAction;

/**
 * <b>Application describing: 通过设备ID获取实时告警详情 </b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-20 上午10:20:47
 */
public class TopoAlarmDetail extends OperationLogAction {

    private static final long serialVersionUID = 8754394775096018302L;

    private final Logger logger = Logger.getLogger("AlarmDetail".getClass());

    private String agentId;

    private String nmsDBName;

    private AlarmViewDomain domain;

    public String alarmDetail() {
        logger.info("通过设备ID获取告警详情");

        String opParam[] = { agentId };
        try {
            Map<String, String> paras = new HashMap<String, String>();

            paras.put("agentId", agentId);

            paras.put("nmsDB", nmsDBName);

            domain = (AlarmViewDomain) ibatisDAO.getSingleRecord("TopoGraph.getAlarmDetail", paras);
            /* 设备类型增加维度的国际化 */
            domain.setDeviceTypeName(getText("device.group." + domain.getDeviceTypeName()));
            operationInfo = getText("oplog.detail.success", opParam);
            logger.info("(拓扑)查询告警详情" + getText("log.detail.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
            logger.info("(拓扑)查询告警详情" + getText("log.detail.error"), e);
        }

        return SUCCESS;
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
     * @return the nmsDBName
     */
    public String getNmsDBName() {
        return nmsDBName;
    }

    /**
     * @param nmsDBName the nmsDBName to set
     */
    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    /**
     * @return the domain
     */
    public AlarmViewDomain getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    /**
     * @return the isTopo
     */
    public String getIsTopo() {
        return "true";
    }
}
