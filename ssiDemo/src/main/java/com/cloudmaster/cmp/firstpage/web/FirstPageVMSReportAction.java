package com.cloudmaster.cmp.firstpage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.web.BaseAction;

public class FirstPageVMSReportAction extends BaseAction {

    private static final long serialVersionUID = 8534556752573819843L;

    private final Logger logger = Logger.getLogger("FirstPageVMSReportAction".getClass());

    private String siteId;

    @SuppressWarnings("unchecked")
    public void vmsReport() {

        /* 获取站点VMS的IP */
        /* List<String> ips = ibatisDAO.getData("DeviceInfo.getAgentIpBySite", paras); */
        HashMap<String, String> result = null;
        try {
            result = (HashMap<String, String>) ibatisDAO.getSingleRecord(
                    "PMRes.getVMSReportBySite", siteId);
        } catch (SQLException e) {
            logger.error("查询站点VMS的CPU和内存利用率时异常", e);
        }

        JSONObject jsob = new JSONObject();
        
        /*CPU利用率*/
        if (null != result.get("Cpu_idle")) {
            BigDecimal cpuIdle =  new BigDecimal(result.get("Cpu_idle"));
            BigDecimal cpuUse = new BigDecimal(100).subtract(cpuIdle, new MathContext(2));
            
            JSONObject idle = new JSONObject();
            
            idle.put("name", "空闲");
            idle.put("y", cpuIdle);
            idle.put("color", "#C2C2C2");
            
            JSONObject use = new JSONObject();
            
            use.put("name", "占用");
            use.put("y", cpuUse);
            use.put("color", "#8EBFEE");
            
            JSONArray arr = new JSONArray();
            
            arr.add(idle);
            arr.add(use);
            
            jsob.put("cpu", arr);
        }
        
        
        /*内存利用率*/
        if (null != result.get("Mem_Percent")) {
            BigDecimal memUse = new BigDecimal(result.get("Mem_Percent"));
            BigDecimal memIdle = new BigDecimal(100).subtract(memUse);
            
            JSONObject idle = new JSONObject();
            
            idle.put("name", "空闲");
            idle.put("y", memIdle);
            idle.put("color", "#C2C2C2");
            
            JSONObject use = new JSONObject();
            
            use.put("name", "占用");
            use.put("y", memUse);
            use.put("color", "#8EBFEE");
            
            JSONArray arr = new JSONArray();
            
            arr.add(idle);
            arr.add(use);
            
            jsob.put("mem", arr);
        }
        
        String json = jsob.toString();
        
        HttpServletResponse resp = ServletActionContext.getResponse();
        
        PrintWriter pw = null;
        try {
            pw = resp.getWriter();
            pw.write(json);
        } catch (IOException e) {
            logger.error("VMS设备CPU、内存利用率Ajax获取流异常",e);
        }finally{
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
