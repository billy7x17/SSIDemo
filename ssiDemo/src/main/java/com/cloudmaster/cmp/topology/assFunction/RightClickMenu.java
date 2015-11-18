package com.cloudmaster.cmp.topology.assFunction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.Authenticater;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * <b>Application describing: 拓扑图右键菜单生成Action </b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-9-3 下午2:00:30
 */
public class RightClickMenu extends BaseAction {

    private static final long serialVersionUID = -5596864705254545213L;

    private final Logger logger = Logger.getLogger("RightClickMenu".getClass());

    private String agentId;

    private String nmsDBName;

    public void menu() {

        /* 鉴权 */
        Map<String, Object> session = ActionContext.getContext().getSession();

        Authenticater auth = (Authenticater) session.get("authenticater");

        String html = "";

        /* 查看/编辑设备详细信息权限 */
        if (auth.getAuthIdList().contains("02_08_04_00")
                || auth.getAuthIdList().contains("02_08_03_00")) {

            DeviceDomain deviceDomain = null;

            try {
                deviceDomain = (DeviceDomain) ibatisDAO.getSingleRecord("DeviceInfo.detail",
                        agentId);
            } catch (SQLException e) {
                logger.error("拓扑图：根据设备ID查询是否有设备时发生异常", e);
            }

            /* 判断被右击的节点是不是设备 */
            if (null != deviceDomain)
                html += "<li onmousedown='getDetail("
                        + agentId
                        + ",\""
                        + deviceDomain.getAgentName()
                        + "\")' oncontextmenu='return false;' ><a href='#'><span class='icon-edi icon'></span><span class='opera-label'>设备详情</span></a></li>";

        }
        /* 查看告警信息权限 */
        if (auth.getAuthIdList().contains("05_01_00_00")) {
            /* 查看该设备是否有告警 */
            Integer count = null;

            Map<String, String> paras = new HashMap<String, String>();

            paras.put("nmsDB", nmsDBName);
            paras.put("agentId", agentId);

            try {
                count = (Integer) ibatisDAO.getSingleRecord("TopoGraph.getAlarmDetailCount", paras);
            } catch (SQLException e) {
                logger.error("查询id为" + agentId + "的设备时出现异常", e);
                e.printStackTrace();
            }

            if (!new Integer(0).equals(count)) {
                html += "<li onmousedown='getAlarm("
                        + agentId
                        + ")' oncontextmenu='return false;' ><a href='#'><span class='icon-detail icon'></span><span class='opera-label'>告警详情</span></a></li>";
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter pw = null;

        response.setCharacterEncoding("utf-8");
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(html); // 转换json数据格式 传递前台
        pw.flush();
        pw.close();

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
}
