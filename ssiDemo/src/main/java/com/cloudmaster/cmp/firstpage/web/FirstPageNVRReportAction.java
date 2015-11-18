package com.cloudmaster.cmp.firstpage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.web.BaseAction;

public class FirstPageNVRReportAction extends BaseAction {

    private static final long serialVersionUID = -5356266343109416142L;

    private final Logger logger = Logger.getLogger("FirstPageNVRReportAction".getClass());

    private String siteId;

    @SuppressWarnings("unchecked")
    public void nvrReport() {

        List<String> ips = null;

        Map<String, String> paras = new HashMap<String, String>();

        paras.put("siteId", siteId);
        paras.put("agentGroup", "NVR");

        try {
            ips = ibatisDAO.getData("DeviceInfo.getAgentIpBySite", paras);
        } catch (SQLException e) {
            logger.error("根据站点查询NVR设备IP异常", e);
        }

        List<BigDecimal> cpus = new ArrayList<BigDecimal>();

        List<String> NvrNames = new ArrayList<String>();

        try {
            for (String ip : ips) {

                HashMap<String, String> resultMap = (HashMap<String, String>) ibatisDAO
                        .getSingleRecord("resourceReport.getNVRReport", ip);

                if (null != resultMap) {
                    if (null != resultMap.get("cpu")) {
                        cpus.add(new BigDecimal(resultMap.get("cpu")));
                    }

                    if (null != resultMap.get("agentName")) {
                        NvrNames.add(resultMap.get("agentName"));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("根据NVR设备IP查询NVR的CPU利用率异常", e);
        }

        JSONObject obj = new JSONObject();
        
        JSONArray result = new JSONArray();

        if (!cpus.isEmpty()) {

            for (BigDecimal cpu : cpus) {
                JSONArray arr = new JSONArray();

                JSONObject idle = new JSONObject();

                idle.put("name", "空闲");
                idle.put("y", new BigDecimal(100).subtract(cpu));
                idle.put("color", "#C2C2C2");

                arr.add(idle);

                JSONObject use = new JSONObject();

                use.put("name", "占用");
                use.put("y", cpu);
                use.put("color", "#8EBFEE");

                arr.add(use);

                result.add(arr);
            }
        }
        
        obj.put("data", result);
        obj.put("NvrNames", NvrNames);

        HttpServletResponse resp = ServletActionContext.getResponse();

        PrintWriter pw = null;

        try {
            pw = resp.getWriter();

            pw.write(obj.toString());

        } catch (IOException e) {
            logger.error("NVR设备CPU利用率Ajax获取流异常", e);
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
