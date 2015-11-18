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
package com.cloudmaster.cmp.alarm.topology.web;

import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.cloudmaster.cmp.alarm.topology.dao.DynamicRackDomain;
import com.cloudmaster.cmp.alarm.topology.dao.ServiceTopologyDomain;
import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.util.createXML.CreateXML;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.enzyme.script.ScriptUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * 拓扑管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TopologyAction extends BaseJob {
    private static final long serialVersionUID = -1620461567391550726L;

    private static LogService logger = LogService.getLogger(TopologyAction.class);

    private List<AlarmViewDomain> alarmDomains;

    private CreateXML executeTopo = new CreateXML();

    private IbatisDAO ibatisDAO;

    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        logger.info("Create XML begin");
        String resultp = TopologyAction.class.getResource("").getPath();
        resultp = resultp.substring(1, resultp.length());

        logger.info("原始路径:" + resultp);
        int index = resultp.indexOf("WEB-INF");
        resultp = "/" + resultp.substring(0, index);
        // resultp = resultp + "flex/";

        // int index = resultp.indexOf("WEB-INF");
        // if (index == -1) {
        // index = resultp.indexOf("bin");
        // }
        // resultp = resultp.substring(0, index);
        // if (resultp.startsWith("jar")) {
        // resultp = resultp.substring(10);
        // } else if (resultp.startsWith("file")) {
        // resultp = resultp.substring(6);
        // }
        // if (resultp.endsWith("/")) {
        // resultp = resultp.substring(0, resultp.length() - 1);
        // }
        logger.info("截取后路径：" + resultp);

        JobDataMap map = context.getMergedJobDataMap();
        String uri = map.getString("uri");// 获取任务参数中json数据里配置的"uri"的值
        Map<String, Object> parameter = new HashMap<String, Object>(map.getWrappedMap());
        String jobresult = "success";
        try {
            alarmDomains = new ArrayList<AlarmViewDomain>();
            alarmDomains = ibatisDAO.getData("alarmDetail.getAllMessage", "");
            // 生成告警拓扑图的XML
            String result = executeTopo.createData(alarmDomains, resultp);
            // 生成动态机柜的XML
            String result1 = createDynamicXML(alarmDomains, resultp);
            // 生成业务拓扑图的XML
            getNagiosServiceAlarm(alarmDomains, resultp);
            // 生成公有云1000台机器的不同机房拓扑图的XML(实现转取的XMl)
            // createTopologyPC(alarmDomains,resultp);
            if (result.equals("success") && result1.equals("success")) {
                logger.info("xml create success!");
            } else {
                jobresult = "failed";
                logger.info("xml create failed!");
            }
        } catch (Exception e) {
            jobresult = "error";
            logger.info("xml create error!", e);
        }
        context.setResult(jobresult);// context.setResult("success")表示任务执行成功；其他则失败
        // 如果任务参数中配置了所需执行的script的uri，则执行scriot；否则，不执行
        if (uri != null && !"".equals(uri)) {
            Object log = ScriptUtils.executeScript(new URI(uri), parameter);
            context.put("log", "script execute error：" + log.toString());
        } else {
            if (jobresult.equals("success"))
                context.put("log", "无");
            else
                context.put("log", "xml create failed!");
        }
        logger.info("Create XML end");
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    private String resourceZoneParentid;

    // 机房集合
    List<DynamicRackDomain> roomlist = new ArrayList<DynamicRackDomain>();

    // 机柜集合
    List<DynamicRackDomain> racklist = new ArrayList<DynamicRackDomain>();

    // 设备集合
    List<DynamicRackDomain> devicelist = new ArrayList<DynamicRackDomain>();

    /**
     * 性能管理页面动态机房的拓扑图
     * @param alarmDomains
     * @param path
     * @return
     */
    private String createDynamicXML(List<AlarmViewDomain> alarmDomains, String path) {
        String result1 = "success";
        try {
            logger.info("Info:dynamicRack--begin");
            // 首先查询所有机房信息
            // resourceZoneParentid = "CIDC-Z-01-000";
            // if ("3".equals(environmentType)) {
            roomlist = ibatisDAO.getData("alarmDetail.getAllRoom", resourceZoneParentid);
            // } else {
            // roomlist = ibatisDAO.getData("alarmDetail.getNJRoom", resourceZoneParentid);
            // }
            // for循环，遍历机房集合，查询出每一个机房中的机柜（一个机房一个XML,将生成 roomlist.size()个xml）
            for (int i = 0; i < roomlist.size(); i++) {
                // 调用cmdb查询机房的行和列
                String row = "10";
                String colum = "6";
                String roomID = roomlist.get(i).getRoomID();
                // 创建根节点
                Element data = new Element("data");
                // 将根节点添加到
                Document doc = new Document(data);
                // 一层子节点room
                Element room = new Element("room");
                room.setAttribute("row", row + "");
                room.setAttribute("column", colum + "");
                // 一层子节点groups
                Element groups = new Element("groups");
                if (null == row || "".equals(row)) {
                    row = "10";
                }
                for (int r = 1; r <= Integer.parseInt(row); r++) {
                    Element group = new Element("group");
                    group.setAttribute("name", r + "列");
                    group.setAttribute("id", r + "g");
                    groups.addContent(group);
                }

                // 查询出每一个机房中的所有机柜
                racklist = ibatisDAO.getData("alarmDetail.getAllRack", roomlist.get(i));
                Element racks = new Element("racks");
                Element devices = new Element("devices");
                Element resources = new Element("resources");
                data.addContent(room);
                data.addContent(groups);
                // for循环，遍历机柜集合，查询出每一个机柜中的设备
                // 定义设备id的初始值
                int zz = 0;
                for (int j = 0; j < racklist.size(); j++) {
                    // 调用cmdb获取机柜所在行号和列号
                    String rackRow = "1";
                    String rackColum = "1";
                    String rackID = racklist.get(j).getRackID();
                    if (null == rackRow || "".equals(rackRow)) {
                        rackRow = "1";
                    }
                    if (null == rackColum || "".equals(rackColum)) {
                        rackColum = "1";
                    }
                    Element el = new Element("rack");
                    el.setAttribute("id", j + "r");
                    el.setAttribute("xaxis", rackRow + "");
                    el.setAttribute("yaxis", rackColum + "");
                    el.setAttribute("level", "-1");
                    el.setAttribute("groupid", rackRow + "g");
                    if (null != racklist.get(j).getRackName()) {
                        el.setAttribute("rackName", racklist.get(j).getRackName());
                    } else {
                        el.setAttribute("rackName", "");
                    }
                    el.setAttribute("mark", "");
                    el.setAttribute("percent", "");
                    racks.addContent(el);
                    // 查询出机柜中所有的设备（关联设备表查询设备的名称）
                    devicelist = ibatisDAO.getData("alarmDetail.getAllDevice", racklist.get(j));
                    for (int z = 0; z < devicelist.size(); z++) {
                        DynamicRackDomain drd = devicelist.get(z);
                        String deviceType = drd.getDeviceType();
                        Element e2 = new Element("device");
                        e2.setAttribute("id", zz + "d");
                        zz++;
                        e2.setAttribute("rackid", j + "r");
                        if (null != deviceType) {
                            if ("CIDC-RT-SRV".equals(deviceType) && null != drd.getPmName()) {
                                drd.setDeviceName(drd.getPmName());
                                drd.setDeviceType("物理机");
                            } else if ("CIDC-RT-VM".equals(deviceType) && null != drd.getVmName()) {
                                drd.setDeviceName(drd.getVmName());
                                drd.setDeviceType("虚拟机");
                            } else if ("CIDC-RT-MM".equals(deviceType) && null != drd.getMmName()) {
                                drd.setDeviceName(drd.getMmName());
                                drd.setDeviceType("小型机");
                            } else if ("CIDC-RT-MC".equals(deviceType) && null != drd.getMcName()) {
                                drd.setDeviceName(drd.getMcName());
                                drd.setDeviceType("小型机分区");
                            } else if ("CIDC-RT-AS".equals(deviceType) && null != drd.getAsName()) {
                                drd.setDeviceName(drd.getAsName());
                                drd.setDeviceType("存储设备");
                            } else if ("CIDC-RT-BS".equals(deviceType) && null != drd.getBsName()) {
                                drd.setDeviceName(drd.getBsName());
                                drd.setDeviceType("存储设备");
                            } else {
                                drd.setDeviceName(drd.getStName());
                                drd.setDeviceType("交换机");
                            }
                            if (null == drd.getDeviceName()) {
                                e2.setAttribute("name", "");
                            } else {
                                e2.setAttribute("name", drd.getDeviceName());
                            }
                            if (null == drd.getDeviceIP()) {
                                e2.setAttribute("ip", "");
                            } else {
                                e2.setAttribute("ip", drd.getDeviceIP());
                            }
                            if (null == drd.getDeviceType()) {
                                e2.setAttribute("type", "");
                            } else {
                                e2.setAttribute("type", drd.getDeviceType());
                            }
                        }
                        e2.setAttribute("mark", "");
                        e2.setAttribute("level", "-1");
                        e2.setAttribute("levelShow", "正常");
                        devices.addContent(e2);
                    }
                }
                data.addContent(racks);
                data.addContent(devices);
                data.addContent(resources);

                Element deviceResources = new Element("deviceResources");
                resources.addContent(deviceResources);
                // 读数据库的告警信息，拼成告警的节点如下格式------------------------------------------------
                for (int a = 0; a < alarmDomains.size(); a++) {
                    AlarmViewDomain alarm = alarmDomains.get(a);
                    // 获取告警IP
                    String alarmip = alarm.getAlarmIP();
                    for (int j = 0; j < devices.getContentSize(); j++) {
                        Element device = (Element) devices.getContent(j);
                        String deviceip = device.getAttributeValue("ip");
                        if (null != alarmip && null != deviceip) {
                            if (alarmip.equals(deviceip)) {
                                // 拼告警信息节点
                                Element el = new Element("deviceResource");
                                // 节点id对应xml设备id
                                el.setAttribute("nodeid", device.getAttributeValue("id"));
                                // 其它信息来自告警对象
                                if (null != alarm.getAlarmGrade()) {
                                    el.setAttribute("alarmGrade", alarm.getAlarmGrade());
                                }
                                if (null != alarm.getAlarmID()) {
                                    el.setAttribute("alarmID", alarm.getAlarmID());
                                }
                                if (null != alarm.getAlarmType()) {
                                    el.setAttribute("alarmType", alarm.getAlarmType());
                                }
                                if (null != alarm.getAlarmTitle()) {
                                    el.setAttribute("alarmTitle", alarm.getAlarmTitle());
                                }
                                if (null != alarm.getAlarmContent()) {
                                    el.setAttribute("alarmContent", alarm.getAlarmContent());
                                }
                                if (null != alarm.getAlarmTime()) {
                                    el.setAttribute("alarmTime", alarm.getAlarmTime());
                                }
                                if (null != alarm.getFirstAlarmTime()) {
                                    el.setAttribute("firstAlarmTime", alarm.getFirstAlarmTime());
                                }
                                if (null != alarm.getAlarmIP()) {
                                    el.setAttribute("objectID", alarm.getAlarmIP());
                                }
                                if (null != alarm.getCount()) {
                                    el.setAttribute("count", alarm.getCount());
                                }
                                deviceResources.addContent(el);
                                // 由于有告警，将告警信息节点 的 告警级别对应到 设备 节点
                                // 并且是最高告警级别对应到设备节点
                                if (Integer.parseInt(alarm.getAlarmGrade()) > Integer
                                        .parseInt(device.getAttributeValue("level"))) {
                                    device.setAttribute("level", alarm.getAlarmGrade());
                                    // if ("0".equals(alarm.getAlarmGrade())) {
                                    // device.setAttribute("levelShow","清除");
                                    // } else if ("1".equals(alarm.getAlarmGrade())) {
                                    // device.setAttribute("levelShow","不确定");
                                    // } else
                                    if ("2".equals(alarm.getAlarmGrade())) {
                                        device.setAttribute("levelShow", "警告");
                                    } else if ("3".equals(alarm.getAlarmGrade())) {
                                        device.setAttribute("levelShow", "一般");
                                    } else if ("4".equals(alarm.getAlarmGrade())) {
                                        device.setAttribute("levelShow", "重要");
                                    } else if ("5".equals(alarm.getAlarmGrade())) {
                                        device.setAttribute("levelShow", "紧急");
                                    } else {
                                        device.setAttribute("levelShow", "正常");
                                    }
                                }
                                devices.removeContent(j);
                                devices.addContent(j, device);
                                // 然后将该设备对应的机柜 同样设置告警级别
                                String rockid = device.getAttributeValue("rackid");
                                for (int z = 0; z < racks.getContentSize(); z++) {
                                    Element rack = (Element) racks.getContent(z);
                                    // 循环遍历机柜节点，找到该设备所属机柜的节点
                                    if (rockid.equals(rack.getAttributeValue("id"))) {
                                        // 判断该机柜节点的告警级别 与 当前设备节点的告警级别的大小
                                        if (Integer.parseInt(alarm.getAlarmGrade()) > Integer
                                                .parseInt(rack.getAttributeValue("level"))) {
                                            rack.setAttribute("level", alarm.getAlarmGrade());
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                Format format = Format.getPrettyFormat();
                XMLOutputter xmlout = new XMLOutputter(format);
                // xmlout.output(doc, new FileOutputStream(path+"/flex/rack-nj.xml"));
                // xmlout.output(doc, new FileOutputStream("E:/flex/" + roomlist.get(i).getRoomID()
                // + ".xml"));
                xmlout.output(doc, new FileOutputStream(path + "/flex/"
                        + roomlist.get(i).getRoomID() + ".xml"));
            }
        } catch (Exception e1) {
            result1 = "fail";
            logger.error("createDynamicXML error", e1);
        }
        logger.info("Info:dynamicRack--end");
        return result1;
    }

    public String getResourceZoneParentid() {
        return resourceZoneParentid;
    }

    public void setResourceZoneParentid(String resourceZoneParentid) {
        this.resourceZoneParentid = resourceZoneParentid;
    }

    /**
     * nagios数据库
     */
    private IbatisDAO ibatisDAONagios;

    public IbatisDAO getIbatisDAONagios() {
        return ibatisDAONagios;
    }

    public void setIbatisDAONagios(IbatisDAO ibatisDAONagios) {
        this.ibatisDAONagios = ibatisDAONagios;
    }

    /**
     * 日志前缀
     */
    private String logBegin = "Service Topology,";

    /**
     * 获取nagios_pmstatus中的告警数据,拼成业务视图拓扑
     */
    public void getNagiosServiceAlarm(List<AlarmViewDomain> alarmDomains, String path) {
        // 创建根节点
        Element data = new Element("data");
        // 将根节点添加到
        Document doc = new Document(data);
        // 一层子节点room
        Element nodes = new Element("nodes");
        Element links = new Element("links");
        Element resources = new Element("resources");
        Element nodeResources = new Element("nodeResources");
        resources.addContent(nodeResources);
        data.addContent(nodes);
        data.addContent(links);
        data.addContent(resources);
        // xml骨干拼接完成
        // 无论数据库中是否有采集到四个节点的告警数据，xml都有四个节点，先拼接四个节点（有可能是更多的节点）
        // 拼完四个节点的基本信息，后根据采集信息修正四个节点的告警级别，并且拼告警信息节点
        try {
            logger.info("Info:serviceTopoloty--begin");
            // 获取业务拓扑图的四个节点的信息
            List<ServiceTopologyDomain> serviceToD = ibatisDAO.getData(
                    "alarmDetail.getServiceTopologyXML", "");
            for (int s = 0; s < serviceToD.size(); s++) {
                Element node = new Element("node");
                node.setAttribute("id", "" + s);
                node.setAttribute("ip", serviceToD.get(s).getIpAddress());
                node.setAttribute("name", serviceToD.get(s).getDisplayName());
                node.setAttribute("image", "SW");
                node.setAttribute("level", "0");
                // 节点信息完善后，将节点加入到nodes中
                nodes.addContent(node);
            }
            // 最后将节点连接到一起，拼接连线信息，四个节点三条线，0连接1,2,3
            // 要求集合中njrs节点的ID必须是 0 from njrs节点 to 各个节点
            for (int l = 0; l < serviceToD.size() - 1; l++) {
                Element link = new Element("link");
                link.setAttribute("id", "link1");
                link.setAttribute("from", "0");
                link.setAttribute("to", "" + (l + 1));
                links.addContent(link);
            }

            // 一层子节点resources的子节点
            for (int i = 0; i < alarmDomains.size(); i++) {
                AlarmViewDomain alarm = alarmDomains.get(i);
                // 获取告警IP
                String alarmip = alarm.getAlarmIP();
                for (int j = 0; j < nodes.getContentSize(); j++) {
                    Element device = (Element) nodes.getContent(j);
                    String deviceip = device.getAttributeValue("ip");
                    if (null != alarmip && null != deviceip) {
                        if (alarmip.equals(deviceip)) {
                            // 拼告警信息节点
                            Element nodeResource = new Element("nodeResource");
                            // 节点id对应xml设备id
                            nodeResource.setAttribute("nodeid", device.getAttributeValue("id"));
                            // 其它信息来自告警对象
                            nodeResource.setAttribute("alarmGrade",
                                    null != alarm.getAlarmGrade() ? alarm.getAlarmGrade() : "");
                            nodeResource.setAttribute("alarmID", null != alarm.getAlarmID() ? alarm
                                    .getAlarmID() : "");
                            nodeResource.setAttribute("alarmType",
                                    null != alarm.getAlarmType() ? alarm.getAlarmType() : "");
                            nodeResource.setAttribute("alarmTitle",
                                    null != alarm.getAlarmTitle() ? alarm.getAlarmTitle() : "");
                            nodeResource.setAttribute("alarmContent", null != alarm
                                    .getAlarmContent() ? alarm.getAlarmContent() : "");
                            nodeResource.setAttribute("alarmTime",
                                    null != alarm.getAlarmTime() ? alarm.getAlarmTime() : "");
                            nodeResource.setAttribute("firstAlarmTime", null != alarm
                                    .getFirstAlarmTime() ? alarm.getFirstAlarmTime() : "");
                            nodeResource.setAttribute("objectID",
                                    null != alarm.getAlarmIP() ? alarm.getAlarmIP() : "");
                            nodeResource.setAttribute("count", null != alarm.getCount() ? alarm
                                    .getCount() : "");
                            nodeResources.addContent(nodeResource);
                            // 由于有告警，将告警信息节点 的 告警级别对应到 设备 节点
                            device.setAttribute("level", alarm.getAlarmGrade());
                            break;
                        }
                    }
                }
            }
            Format format = Format.getPrettyFormat();
            XMLOutputter xmlout = new XMLOutputter(format);
            xmlout.output(doc, new FileOutputStream(path + "/flex/service-nj.xml"));
            logger.info("Info:serviceTopoloty--end");
        } catch (Exception e) {
            logger.info(logBegin + "getNagiosPmAlarm error:" + e.getMessage());
            logger.info(logBegin + "error:", e);
        }
    }

    /**
     * 公有云拓扑图
     * @param alarmDomains
     * @param path
     * @return
     */
    private String createDynamicPC(List<AlarmViewDomain> alarmDomains, String path) {
        String result1 = "success";
        try {
            logger.info("Info:dynamicPC--begin");
            // for循环，遍历机房集合，查询出每一个机房中的机柜（一个机房一个XML,将生成 roomlist.size()个xml）
            for (int i = 0; i < roomlist.size(); i++) {
                // 创建根节点
                Element data = new Element("data");
                // 将根节点添加到
                Document doc = new Document(data);
                // 一层子节点room
                Element nodes = new Element("nodes");
                Element resources = new Element("resources");
                Element nodeResources = new Element("nodeResources");
                resources.addContent(nodeResources);
                data.addContent(nodes);
                data.addContent(resources);

                racklist = ibatisDAO.getData("alarmDetail.getAllRack", roomlist.get(i));
                int zz = 0;
                for (int j = 0; j < racklist.size(); j++) {
                    // 查询出机柜中所有的设备（关联设备表查询设备的名称）
                    devicelist = ibatisDAO.getData("alarmDetail.getAllDevice", racklist.get(j));
                    for (int z = 0; z < devicelist.size(); z++) {
                        DynamicRackDomain drd = devicelist.get(z);
                        String deviceType = drd.getDeviceType();
                        Element node = new Element("node");
                        node.setAttribute("id", zz + "d");
                        zz++;
                        if (null != deviceType) {
                            if ("CIDC-RT-SRV".equals(deviceType) && null != drd.getPmName()) {
                                drd.setDeviceName(drd.getPmName());
                                drd.setDeviceType("SRV");
                            } else if ("CIDC-RT-VM".equals(deviceType) && null != drd.getVmName()) {
                                drd.setDeviceName(drd.getVmName());
                                drd.setDeviceType("VM");
                            } else if ("CIDC-RT-MM".equals(deviceType) && null != drd.getMmName()) {
                                drd.setDeviceName(drd.getMmName());
                                drd.setDeviceType("MM");
                            } else if ("CIDC-RT-MC".equals(deviceType) && null != drd.getMcName()) {
                                drd.setDeviceName(drd.getMcName());
                                drd.setDeviceType("MC");
                            } else if ("CIDC-RT-AS".equals(deviceType) && null != drd.getAsName()) {
                                drd.setDeviceName(drd.getAsName());
                                drd.setDeviceType("AS");
                            } else if ("CIDC-RT-BS".equals(deviceType) && null != drd.getBsName()) {
                                drd.setDeviceName(drd.getBsName());
                                drd.setDeviceType("AS");
                            } else if ("CIDC-RT-SW".equals(deviceType) && null != drd.getBsName()) {
                                drd.setDeviceName(drd.getStName());
                                drd.setDeviceType("SW");
                            }
                            if (null == drd.getDeviceName()) {
                                node.setAttribute("name", "");
                            } else {
                                node.setAttribute("name", drd.getDeviceName());
                            }
                            if (null == drd.getDeviceIP()) {
                                node.setAttribute("ip", "");
                            } else {
                                node.setAttribute("ip", drd.getDeviceIP());
                            }
                            if (null == drd.getDeviceType()) {
                                node.setAttribute("type", "");
                            } else {
                                node.setAttribute("type", drd.getDeviceType());
                            }
                        }
                        node.setAttribute("level", "-1");
                        nodes.addContent(node);
                    }
                }

                // 一层子节点resources的子节点
                for (int a = 0; a < alarmDomains.size(); a++) {
                    AlarmViewDomain alarm = alarmDomains.get(a);
                    // 获取告警IP
                    String alarmip = alarm.getAlarmIP();
                    for (int j = 0; j < nodes.getContentSize(); j++) {
                        Element device = (Element) nodes.getContent(j);
                        String deviceip = device.getAttributeValue("ip");
                        if (null != alarmip && null != deviceip) {
                            if (alarmip.equals(deviceip)) {
                                // 拼告警信息节点
                                Element nodeResource = new Element("nodeResource");
                                // 节点id对应xml设备id
                                nodeResource.setAttribute("nodeid", device.getAttributeValue("id"));
                                // 其它信息来自告警对象
                                nodeResource.setAttribute("alarmGrade", null != alarm
                                        .getAlarmGrade() ? alarm.getAlarmGrade() : "");
                                nodeResource.setAttribute("alarmID",
                                        null != alarm.getAlarmID() ? alarm.getAlarmID() : "");
                                nodeResource.setAttribute("alarmType",
                                        null != alarm.getAlarmType() ? alarm.getAlarmType() : "");
                                nodeResource.setAttribute("alarmTitle", null != alarm
                                        .getAlarmTitle() ? alarm.getAlarmTitle() : "");
                                nodeResource.setAttribute("alarmContent", null != alarm
                                        .getAlarmContent() ? alarm.getAlarmContent() : "");
                                nodeResource.setAttribute("alarmTime",
                                        null != alarm.getAlarmTime() ? alarm.getAlarmTime() : "");
                                nodeResource.setAttribute("firstAlarmTime", null != alarm
                                        .getFirstAlarmTime() ? alarm.getFirstAlarmTime() : "");
                                nodeResource.setAttribute("objectID",
                                        null != alarm.getAlarmIP() ? alarm.getAlarmIP() : "");
                                nodeResource.setAttribute("count", null != alarm.getCount() ? alarm
                                        .getCount() : "");
                                nodeResources.addContent(nodeResource);
                                // 由于有告警，将告警信息节点 的 告警级别对应到 设备 节点
                                device.setAttribute("level", alarm.getAlarmGrade());
                                break;
                            }
                        }
                    }
                }
                Format format = Format.getPrettyFormat();
                XMLOutputter xmlout = new XMLOutputter(format);
                // xmlout.output(doc, new FileOutputStream("E:/flex/" + roomlist.get(i).getRoomID()
                // + "PC.xml"));
                xmlout.output(doc, new FileOutputStream(path + "/flex/"
                        + roomlist.get(i).getRoomID() + "PC.xml"));
            }
        } catch (Exception e1) {
            logger.info("Info:dynamicPC--error,e1");
            logger.error("Info:dynamicPC--error,e1");
            result1 = "fail";
        }
        logger.info("Info:dynamicPC--end");
        return result1;
    }

    /**
     * 公有云拓扑图
     * @param alarmDomains
     * @param path
     * @return
     */
    private String createTopologyPC(List<AlarmViewDomain> alarmDomains, String path) {
        String result1 = "success";
        try {
            logger.info("Info:dynamicPC--begin");
            // for循环，遍历机房集合，查询出每一个机房中的机柜（一个机房一个XML,将生成 roomlist.size()个xml）
            int zz = 0;
            // 创建根节点
            Element data = new Element("data");
            Document doc = new Document(data);
            Element rooms = new Element("rooms");
            // 一层子节点room
            Element nodes = new Element("nodes");
            Element resources = new Element("resources");
            Element nodeResources = new Element("nodeResources");
            resources.addContent(nodeResources);
            data.addContent(nodes);
            data.addContent(resources);
            data.addContent(rooms);
            for (int i = 0; i < roomlist.size(); i++) {
                // 将根节点添加到
                racklist = ibatisDAO.getData("alarmDetail.getAllRack", roomlist.get(i));
                Element room = new Element("room");
                // room.setAttribute("id",roomlist.get(i).getRoomID()!=null?roomlist.get(i).getRoomID():"");
                room.setAttribute("name", roomlist.get(i).getRoomName() != null ? roomlist.get(i)
                        .getRoomName() : "");
                room.setAttribute("level", "-1");
                rooms.addContent(room);
                for (int j = 0; j < racklist.size(); j++) {
                    // 查询出机柜中所有的设备（关联设备表查询设备的名称）
                    devicelist = ibatisDAO.getData("alarmDetail.getAllDevice", racklist.get(j));
                    for (int z = 0; z < devicelist.size(); z++) {
                        DynamicRackDomain drd = devicelist.get(z);
                        String deviceType = drd.getDeviceType();
                        Element node = new Element("node");
                        node.setAttribute("id", zz + "d");
                        zz++;
                        // 加条件，只展示物理机的拓扑图
                        if (null != deviceType && "CIDC-RT-SRV".equals(deviceType)) {
                            if ("CIDC-RT-SRV".equals(deviceType) && null != drd.getPmName()) {
                                drd.setDeviceName(drd.getPmName());
                                drd.setDeviceType("SRV");
                            } else if ("CIDC-RT-VM".equals(deviceType) && null != drd.getVmName()) {
                                drd.setDeviceName(drd.getVmName());
                                drd.setDeviceType("VM");
                            } else if ("CIDC-RT-MM".equals(deviceType) && null != drd.getMmName()) {
                                drd.setDeviceName(drd.getMmName());
                                drd.setDeviceType("MM");
                            } else if ("CIDC-RT-MC".equals(deviceType) && null != drd.getMcName()) {
                                drd.setDeviceName(drd.getMcName());
                                drd.setDeviceType("MC");
                            } else if ("CIDC-RT-AS".equals(deviceType) && null != drd.getAsName()) {
                                drd.setDeviceName(drd.getAsName());
                                drd.setDeviceType("AS");
                            } else if ("CIDC-RT-BS".equals(deviceType) && null != drd.getBsName()) {
                                drd.setDeviceName(drd.getBsName());
                                drd.setDeviceType("AS");
                            } else if ("CIDC-RT-SW".equals(deviceType) && null != drd.getBsName()) {
                                drd.setDeviceName(drd.getStName());
                                drd.setDeviceType("SW");
                            }
                            if (null == drd.getDeviceName()) {
                                node.setAttribute("name", "");
                            } else {
                                node.setAttribute("name", drd.getDeviceName());
                            }
                            if (null == drd.getDeviceIP()) {
                                node.setAttribute("ip", "");
                            } else {
                                node.setAttribute("ip", drd.getDeviceIP());
                            }
                            if (null == drd.getDeviceType()) {
                                node.setAttribute("type", "");
                            } else {
                                node.setAttribute("type", drd.getDeviceType());
                            }
                        }
                        node.setAttribute("level", "-1");
                        node.setAttribute("pid", roomlist.get(i).getRoomID() != null ? roomlist
                                .get(i).getRoomName() : "");
                        nodes.addContent(node);
                    }
                }

                // 查询告警信息，设置节点的告警级别
                for (int a = 0; a < alarmDomains.size(); a++) {
                    AlarmViewDomain alarm = alarmDomains.get(a);
                    // 获取告警IP
                    String alarmip = alarm.getAlarmIP();
                    for (int j = 0; j < nodes.getContentSize(); j++) {
                        Element device = (Element) nodes.getContent(j);
                        String deviceip = device.getAttributeValue("ip");
                        if (null != alarmip && null != deviceip) {
                            if (alarmip.equals(deviceip)) {
                                // 拼告警信息节点
                                Element nodeResource = new Element("nodeResource");
                                // 节点id对应xml设备id
                                nodeResource.setAttribute("nodeid", device.getAttributeValue("id"));
                                // 其它信息来自告警对象
                                nodeResource.setAttribute("alarmGrade", null != alarm
                                        .getAlarmGrade() ? alarm.getAlarmGrade() : "");
                                nodeResource.setAttribute("alarmID",
                                        null != alarm.getAlarmID() ? alarm.getAlarmID() : "");
                                nodeResource.setAttribute("alarmType",
                                        null != alarm.getAlarmType() ? alarm.getAlarmType() : "");
                                nodeResource.setAttribute("alarmTitle", null != alarm
                                        .getAlarmTitle() ? alarm.getAlarmTitle() : "");
                                nodeResource.setAttribute("alarmContent", null != alarm
                                        .getAlarmContent() ? alarm.getAlarmContent() : "");
                                nodeResource.setAttribute("alarmTime",
                                        null != alarm.getAlarmTime() ? alarm.getAlarmTime() : "");
                                nodeResource.setAttribute("firstAlarmTime", null != alarm
                                        .getFirstAlarmTime() ? alarm.getFirstAlarmTime() : "");
                                nodeResource.setAttribute("objectID",
                                        null != alarm.getAlarmIP() ? alarm.getAlarmIP() : "");
                                nodeResource.setAttribute("count", null != alarm.getCount() ? alarm
                                        .getCount() : "");
                                nodeResources.addContent(nodeResource);
                                // 由于有告警，将告警信息节点 的 告警级别对应到 设备 节点
                                device.setAttribute("level", alarm.getAlarmGrade());
                                break;
                            }
                        }
                    }
                }

                // 通过节点的告警级别设置机房的告警级别
                for (int roomIndex = 0; roomIndex < rooms.getContentSize(); roomIndex++) {
                    Element r = (Element) rooms.getContent(roomIndex);
                    for (int j = 0; j < nodes.getContentSize(); j++) {
                        int rle = Integer.parseInt(r.getAttributeValue("level"));
                        Element device = (Element) nodes.getContent(j);
                        int dele = Integer.parseInt(device.getAttributeValue("level"));
                        if (r.getAttributeValue("name").equals(device.getAttributeValue("pid"))
                                && dele > rle) {
                            r.setAttribute("level", dele + "");
                        }
                    }
                }
            }
            Format format = Format.getPrettyFormat();
            XMLOutputter xmlout = new XMLOutputter(format);
            // xmlout.output(doc, new FileOutputStream("E:/flex/topologyPC.xml"));
            xmlout.output(doc, new FileOutputStream(path + "/flex/topologyPCSubnet.xml"));
        } catch (Exception e) {
            logger.info("Info:dynamicPC--error", e);
            logger.error("Info:dynamicPC--error", e);
            result1 = "fail";
        }
        logger.info("Info:dynamicPC--end");
        return result1;
    }
}
