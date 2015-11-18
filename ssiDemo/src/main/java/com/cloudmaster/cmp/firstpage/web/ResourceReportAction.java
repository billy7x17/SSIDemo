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
package com.cloudmaster.cmp.firstpage.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.system.systemparmeter.service.SystemParameterService;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 首页
 * @author <a href="mailto:ying.zhou@neusoft.com"> ying.zhou </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ResourceReportAction extends PageAction {

    private static final long serialVersionUID = -1261165380516244748L;

    private static LogService logger = LogService.getLogger(ResourceReportAction.class);

    private SystemParameterService sysParaService;

    /**
     * 站点ID；
     */
    private String siteId;

    /**
     * 向页面返回json 格式的数据 resourcetype：资源类型pm：物理机；vm：虚拟机；block 块;小型机 mm
     * {"item0":{"resourcetype":"pm","resourceused":"80"},"item1":{"resourcetype":"vm",
     * "resourceused":"40"}}
     * @return
     */
    @SuppressWarnings("unchecked")
    public void getResourceState() {
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter pw = null;
        try {
            // 根据设置的属性来看是读取真实数据还是自定义数据
            /*
             * if ("1".equals(((SystemParameterDomain) sysParaService
             * .getSystemParameterByKey("resReportFlag")).getParamValue())) { resultDate =
             * ((SystemParameterDomain) sysParaService
             * .getSystemParameterByKey("resReportDate")).getParamValue(); } else {
             */
            /* Map resourceMap = new HashMap(); */
            // List resultZoneList = new ArrayList();

            List<?> resultList = new ArrayList<Map<String, String>>();
            JSONArray arr = new JSONArray();

            JSONObject jsonObj = null;
            // 查询系统内机房信息
            /*
             * List resultZoneList = (List) ibatisDAO.getData("resourceReport.getZoneList",
             * resourceMap); if (null == resultZoneList) { resultZoneList = new ArrayList(); }
             */
            /* 查询系统内所有设备分组类型 */
            List<String> groupList = ibatisDAO.getData("resourceReport.getAgentTypeGroup", null);
            for (Iterator<?> i = groupList.iterator(); i.hasNext();) {
                String temp = (String) i.next();
                jsonObj = new JSONObject();
                jsonObj.put("type", getText("device.group." + temp));
                jsonObj.put("used", 0);
                jsonObj.put("total", 0);
                arr.add(jsonObj);
            }
            resultList = (List<Map<String, String>>) ibatisDAO.getData(
                    "resourceReport.getResourceUsed", siteId);
            for (int j = 0; j < resultList.size(); j++) {
                jsonObj = new JSONObject();
                jsonObj.put(
                        "type",
                        getText("device.group."
                                + ((Map<String, String>) resultList.get(j)).get("type")));
                jsonObj.put("used", 0);
                jsonObj.put("total", 0);
                int index = arr.indexOf(jsonObj);
                arr.remove(jsonObj);
                jsonObj.put("used", ((Map<String, String>) resultList.get(j)).get("used"));
                jsonObj.put("total", ((Map<String, String>) resultList.get(j)).get("total"));
                arr.add(index, jsonObj);
            }

            /* logger.info("ResourceReportAction-resultDate：" + resultDate); */
            response.setCharacterEncoding("utf-8");
            pw = response.getWriter();
            pw.write(arr.toString()); // 转换json数据格式 传递前台
            pw.flush();
        } catch (Exception e) {
            logger.error(null, "资源利用率查询异常！", e);
            e.printStackTrace();
        } finally {
            if (null != pw) {
                pw.close();
            }
        }

    }

    /*
     * private BigDecimal return0(BigDecimal mapString) { BigDecimal returnValue = new
     * BigDecimal(0.00); if (null != mapString) { returnValue = mapString; } return returnValue; }
     */

    public static void main(String args[]) {

        List<?> resultZoneList = new ArrayList<Map<String, String>>();
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        jsonObj.put("pm", "60");
        jsonObj.put("vm", "30");
        jsonObj.put("bs", "40");
        jsonObj.put("mm", "50");
        jsonObject.put("item0", jsonObj);

        jsonObj = new JSONObject();
        jsonObj.put("pm", "00");
        jsonObj.put("vm", "20");
        jsonObj.put("bs", "30");
        jsonObj.put("mm", "40");
        jsonObject.put("item1", jsonObj);

        System.out.print(jsonObject);
        System.out.print(resultZoneList.size());
    }

    public SystemParameterService getSysParaService() {
        return sysParaService;
    }

    public void setSysParaService(SystemParameterService sysParaService) {
        this.sysParaService = sysParaService;
    }

    /**
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
