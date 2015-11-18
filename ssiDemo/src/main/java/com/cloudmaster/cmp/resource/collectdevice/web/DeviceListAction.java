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
package com.cloudmaster.cmp.resource.collectdevice.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.util.SqlUtil;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 获取设备信息
 * @author <a href="mailto:wang-rongguang@neusoft.com"> wang-rongguang </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DeviceListAction extends PageAction implements IAuthIdGetter, IOperationLog {

    private static final long serialVersionUID = 1L;

    // 设备信息
    private List<DeviceDomain> deviceList = new ArrayList<DeviceDomain>();

    // 跳转页面
    private String forward = "SUCCESS";

    /**
     * 日志，功能模块名称
     */
    private String functionName;

    private DeviceDomain domain = new DeviceDomain();

    /**
     * 日志，操作信息
     */
    private String operationInfo;

    /**
     * 日志，操作类型
     */
    private String opType;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceListAction.class);

    /**
     * 设备分组
     */
    private List<DeviceDomain> deviceGroupList = new ArrayList<DeviceDomain>();

    /**
     * 所属站点
     */
    private List<DeviceDomain> siteList = new ArrayList<DeviceDomain>();

    /**
     * 错误信息.
     */
    private String errorMsg;

    public String base() {
        try {
            // 获取设备分组
            deviceGroupList = ibatisDAO.getData("DeviceInfo.getDeviceGroupList", null);
            for (DeviceDomain temp : deviceGroupList) {
                if (("NVR").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.NVR"));
                } else if (("IP-SAN").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.IP-SAN"));
                } else if (("Encoder").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.Encoder"));
                } else if (("IPC").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.IPC"));
                } else if (("Switch").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.Switch"));
                } else if (("VMS").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.VMS"));
                } else if (("D4").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.D4"));
                } else if (("Keyboard").equals(temp.getAgentGroup())) {
                    temp.setGroupName(getText("device.group.Keyboard"));
                }
            }
            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            // 如果当前用户是中心管理员，页面提供按照站点查询关键字
            if (("1").equals(user.getRoleType())) {
                DeviceDomain device = new DeviceDomain();
                // 获取所属站点(2号线)
                device.setLineNum("2");
                siteList = this.getIbatisDAO().getData("DeviceInfo.getSites", device);
            }
        } catch (Exception e) {
            logger.error(getText("device.search.action.error"), e);
        }
        return "base";
    }

    /**
     * 获取设备信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public String deviceList() {
        logger.info(getText("function.title") + getText("log.list.begin"));

        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));
            // 增加查询
            domain.setQtype(request.getParameter("qtype"));
            String query = request.getParameter("query");
            if (null != query && !query.equals("")) {
                domain.setQuery(SqlUtil.specialToNormal(query));
            } else {
                domain.setQuery(query);
            }
            // 此操作必须在调用getpage()方法之前
            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));

            // 用于批量导入的浏览，需要传入导入的设备编码串
            if (null != domain.getImportDeviceFlag() && !"".equals(domain.getImportDeviceFlag())) {
                String importDevice = (String) ActionContext.getContext().getSession()
                        .get("importDevice");
                if (null != importDevice && !"".equals(importDevice)) {
                    domain.setImportDevice(importDevice);
                    String[] seq;
                    List<String> list = new ArrayList<String>();
                    seq = importDevice.substring(1, importDevice.length() - 1).split(",");
                    for (int i = 0, n = seq.length; i < n; i++) {
                        list.add(seq[i].trim());
                    }
                    domain.setIdevice(list);
                }
            } else {
                Map session = (Map) ActionContext.getContext().get("session");
                session.remove("importDevice");
                domain.setImportDeviceFlag("");
                domain.setIdevice(null);
            }

            // 获取当前用户信息
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            if (("2").equals(user.getRoleType())) {
                domain.setSiteId(String.valueOf(user.getSiteId()));
            }

            // 获取设备信息
            deviceList = getPage("DeviceInfo.getDeviceListCount", "DeviceInfo.getDeviceList",
                    domain);

            // 获取设备采集频率
            String agentFrequency = getFrequency();

            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (DeviceDomain bean : deviceList) {
                fgjd.setRowId(bean.getAgentId()); // 设置行标识
                fgjd.addColdata(bean.getLocalTableIDRef());
                fgjd.addColdata(bean.getAgentName());
                // 根据ganglia采集频率，获取设备状态。查询设备是否有ping告警，如果无则为在线（0），否则离线（1）
                bean.setAgentFrequency(agentFrequency);
                String statusValue = (String) ibatisDAO.getSingleRecord(
                        "DeviceInfo.getResourceStatus", bean);
                fgjd.addColdata(statusValue);
                fgjd.addColdata(bean.getAgentGroup());
                fgjd.addColdata(bean.getTypeName());
                fgjd.addColdata(bean.getSiteName());
                fgjd.addColdata(bean.getAgentIp());
                fgjd.addColdata(bean.getDeviceType());
                fgjd.addColdata(bean.getAgentComp());
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
            operationInfo = getText("oplog.list.success");
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            forward = "error";
            logger.info(getText("function.title") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
        return forward;
    }

    /**
     * 读取ganglia采集频率
     */
    public String getFrequency() {
        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        String agentFrequency = "";
        try {
            prop.load(classLoader.getResourceAsStream("conf/other/System.properties"));
            agentFrequency = prop.getProperty("agentFrequency");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agentFrequency;
    }

    public void setDeviceList(List<DeviceDomain> deviceList) {
        this.deviceList = deviceList;
    }

    public List<DeviceDomain> getDeviceList() {
        return deviceList;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpType() {
        return opType;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    public DeviceDomain getDomain() {
        return domain;
    }

    public void setDomain(DeviceDomain domain) {
        this.domain = domain;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return Returns the deviceGroupList.
     */
    public List<DeviceDomain> getDeviceGroupList() {
        return deviceGroupList;
    }

    /**
     * @param deviceGroupList The deviceGroupList to set.
     */
    public void setDeviceGroupList(List<DeviceDomain> deviceGroupList) {
        this.deviceGroupList = deviceGroupList;
    }

    /**
     * @return Returns the siteList.
     */
    public List<DeviceDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList The siteList to set.
     */
    public void setSiteList(List<DeviceDomain> siteList) {
        this.siteList = siteList;
    }

}
