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
package com.cloudmaster.cmp.alarm.view.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewDetailAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3139437927841976875L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmViewDetailAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 告警ID
     */
    private String alarmID;

    /**
     * 获取具体设备信息，cmdbID
     */
    private String reLationCmdbID;

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;

    /**
     * 详细页CI项属性排序，防火墙
     */
    private String ciOrderFW;

    /**
     * 详细页CI项属性排序，路由器
     */
    private String ciOrderRT;

    /**
     * 详细页CI项属性排序，阵列
     */
    private String ciOrderAS;

    /**
     * 详细页CI项属性排序，交换机
     */
    private String ciOrderSW;

    /**
     * 详细页CI项属性排序，小型机
     */
    private String ciOrderMM;

    /**
     * 详细页CI项属性排序，物理机
     */
    private String ciOrderSRV;

    /**
     * 详细页CI项属性排序，块存储
     */
    private String ciOrderBS;

    /**
     * 详细页CI项属性排序，小型机分区
     */
    private String ciOrderMC;

    /**
     * 详细页CI项属性排序，虚拟机
     */
    private String ciOrderVM;

    /**
     * 详细页CI项属性排序，对象存储
     */
    private String ciOrderOS;

    /**
     * 详细页CI项属性排序，负载均衡
     */
    private String ciOrderLB;

    /**
     * 告警详细信息 本方法控制页面展示基本信息、邮件通知、短信通知、生成事件 页面上设备CI项、关联CI项、虚拟机关联业务，ajax方式获取
     * @return
     */
    public String alarmDetail() {
        logger.info(getText("function.title") + getText("log.detail.begin"));
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() };
        try {
            AlarmViewDomain paraDomain = new AlarmViewDomain();
            paraDomain.setAlarmID(alarmID);
            paraDomain.setNmsDB(nmsDBName);
            domain = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".detail",
                    paraDomain);
            /*设备类型增加维度的国际化*/
            domain.setDeviceTypeName(getText("device.group."+domain.getDeviceTypeName()));
            operationInfo = getText("oplog.detail.success", opParam);
            logger.info(getText("function.title") + getText("log.detail.end"));
        } catch (Exception e) {
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.detail.error"), e);
        }

        return SUCCESS;
    }

    // public String alarmDetail() {
    // logger.info(getText("function.title") + getText("log.detail.begin"));
    // String opParam[] = { alarmID };
    // try {
    // AlarmViewDomain paraDomain = new AlarmViewDomain();
    // paraDomain.setAlarmID(alarmID);
    // domain = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + ".detail",
    // paraDomain);
    //
    // ciList = new ArrayList();
    // relationMap = new HashMap();
    // businessList = new ArrayList();
    // deviceList = new ArrayList();
    //
    // AlarmViewDomain deviceInfo = getDeviceInfo(domain);
    //
    // // deviceInfo = new AlarmViewDomain();
    // // deviceInfo.setCmdbID("2129123159331600684");
    // // deviceInfo.setLocalID("CIDC-R-01-209-VM-00000044");
    // // deviceInfo.setResourceType("CIDC-RT-VM");
    //
    // if (null == deviceInfo || null == deviceInfo.getCmdbID()
    // || deviceInfo.getCmdbID().equals("")) {
    // logger.info(getText("function.title")
    // + " DeviceInfo not in zone_cluster_instance_tab,alarmIp:"
    // + domain.getAlarmIP());
    // } else {
    // String cmdbID = deviceInfo.getCmdbID();
    // reLationCmdbID = cmdbID;
    // String resourceType = deviceInfo.getResourceType();
    // String localID = deviceInfo.getLocalID();
    //
    // logger.info(getText("function.title") + " DeviceInfo,cmdbID:" + cmdbID
    // + ",resourceType:" + resourceType + ",localID:" + localID);
    //
    // // 本地数据库获取业务信息
    // if (null != resourceType && resourceType.equals("CIDC-RT-SRV")) {
    // // List businessLi = (List) ibatisDAO.getData(
    // // IBATIS_NAMESPACE + ".getSrvBusiness", deviceInfo);
    // // if (null != businessLi && !businessLi.isEmpty()) {
    // // businessList = businessLi;
    // // } else {
    // // logger.info(getText("function.title")
    // // + " Device business not in db,LocalTable_ID_Ref:"
    // // + domain.getLocalID());
    // // }
    // }
    // if (null != resourceType && resourceType.equals("CIDC-RT-VM")) {
    // // AlarmViewDomain businessBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(
    // // IBATIS_NAMESPACE + ".getVmBusiness", deviceInfo);
    // // if (null != businessBean && null != businessBean.getBusiness()) {
    // // businessList.add(businessBean);
    // // } else {
    // // logger.info(getText("function.title")
    // // + " Device business not in db,LocalTable_ID_Ref:"
    // // + domain.getLocalID());
    // // }
    //
    // // 从onecmdb获取业务信息
    // CMDBUtil util = CMDBUtil.getInstance();
    // // cmdbID = "-8400423855074299411";
    // CiBean cb = util.getCiInstanceById(cmdbID);
    // if (null != cb && null != cb.toStringValue("VM_Business")
    // && !cb.toStringValue("VM_Business").equals("")) {
    // String business = cb.toStringValue("VM_Business");
    // AlarmViewDomain businessBean = new AlarmViewDomain();
    // businessBean.setBusiness(business);
    // businessList.add(businessBean);
    // } else {
    // logger.info(getText("function.title") + " VM_Business is null,cmdbID:"
    // + cmdbID + ",alarmIP:" + domain.getAlarmIP());
    // }
    // }
    //
    // CMDBUtil util = CMDBUtil.getInstance();
    // if (null != cmdbID && !cmdbID.equals("")) {
    //
    // // 获取CI属性名称，CI属性值
    // CiBean ciBean = util.getCiInstanceById(cmdbID);
    // CiBean attrBean = util.getCiBase(ciBean.getDerivedFrom());
    // if (null != ciBean && null != attrBean && null != attrBean.getAttributes()) {
    // logger.info("alarm detail,attrBean.getAttributes().size:"
    // + attrBean.getAttributes().size());
    // for (AttributeBean ab : attrBean.getAttributes()) {
    // logger.info("alarm detail,ci.getType:" + ab.getType()
    // + ", ci.getDisplayName:" + ab.getDisplayName());
    // if (ab.getType().startsWith("xs:")) {
    // AlarmViewDomain bean = new AlarmViewDomain();
    // bean.setCiName(ab.getDisplayName());
    // bean.setCiValue(ciBean.toStringValue(ab.getAlias()));
    // ciList.add(bean);
    // }
    // }
    //
    // // 获取关联项，具体关联项
    // Map<String, List<String>> map = util.getComplexInstanceList(ciBean);
    // if (null != map && !map.isEmpty()) {
    // // relationMap = map;
    //
    // Iterator iterator = map.entrySet().iterator();
    // while (iterator.hasNext()) {
    // Entry entry = (Entry) iterator.next();
    // String key = (String) entry.getKey();
    // List li = (List) entry.getValue();
    // if (null != li && !li.isEmpty()) {
    // AlarmViewDomain deviceType = new AlarmViewDomain();
    // deviceType.setCiName("关联项类型：" + key);
    // deviceList.add(deviceType);
    // String mapKey = "关联项(" + key + ")";
    // String mapValue = "";
    // for (int i = 0; i < li.size(); i++) {
    // AlarmViewDomain device = new AlarmViewDomain();
    // String ciName = getDeviceInfoShowName((String) li.get(i));
    // if (null == ciName || ciName.equals("")) {
    // mapValue = mapValue + (String) li.get(i) + "</br>";
    // device.setCiName((String) li.get(i));
    // } else {
    // mapValue = mapValue + ciName + "</br>";
    // device.setCiName(ciName);
    // }
    //
    // deviceList.add(device);
    // }
    // logger.info("alarm view, get relation ci,type:" + mapKey
    // + ",value:" + mapValue);
    // relationMap.put(mapKey, mapValue);
    // }
    // }
    // }
    // } else {
    // logger.info("no data from cmdb by id:" + cmdbID);
    // msg = getText("message.device.cmdb.notFound");
    // }
    // }
    // }
    //
    // operationInfo = getText("oplog.detail.success", opParam);
    // logger.info(getText("function.title") + getText("log.detail.end"));
    // } catch (Exception e) {
    // operationInfo = getText("oplog.detail.error", opParam)
    // + getText("common.message.systemError");
    // logger.info(getText("function.title") + getText("log.detail.error"), e);
    // }
    //
    // return SUCCESS;
    // }

    /**
     * 从数据库中获取设备信息
     * @param bean
     * @return
     */
    private AlarmViewDomain getDeviceInfo(AlarmViewDomain bean) {

        AlarmViewDomain resultBean = null;
        try {
            String ip = bean.getAlarmIP();
            // 0:mals_nm_alarm_device_t, 1:hot_event_tab, 2:vm_hot_event_tab,
            // 3:nagios.nagios_hoststatus, 4:nagios.nagios_servicestatus
            // String sourceType = bean.getAlarmSourceType();
            // if (sourceType.equals("0")) {
            // resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
            // + ".getDeviceInfoType0", domain);
            // }
            // if (sourceType.equals("1")) {
            // resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
            // + ".getDeviceInfoType1", domain);
            // }
            // if (sourceType.equals("2")) {
            // resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
            // + ".getDeviceInfoType2", domain);
            // }
            // if (sourceType.equals("3")) {
            // resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
            // + ".getDeviceInfoType3", domain);
            // }
            // if (sourceType.equals("4")) {
            // resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
            // + ".getDeviceInfoType4", domain);
            // }
            logger.info("alarm get device info by ip:" + ip);
            resultBean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".getDeviceInfoByIP", domain);

        } catch (Exception e) {
            logger.info(getText("function.title") + " get devieInfo from db error ", e);
        }
        return resultBean;
    }



    /**
     * 相关性分析 虚拟机出现告警，将虚拟机上的业务展示出来 交换机端口down，将端口对应的物理机展示出来 物理机故障，将物理机上安装的虚拟机展示出来
     */
    public void alarmBusiness() {
        logger.info(getText("function.title") + "相关性分析开始");

        String opParam[] = { domain.getAlarmIP() };
        List businessList = new ArrayList();
        try {
            if (domain.getDeviceType().equals("1")) { // 物理机
                businessList = ibatisDAO.getData(IBATIS_NAMESPACE + ".pmRelationVm", domain);
                if (null != businessList && !businessList.isEmpty()) {
                    AlarmViewDomain businessBean = new AlarmViewDomain();
                    businessBean.setBusiness("该告警影响如下相关虚拟机：");
                    businessList.add(0, businessBean);
                }
            } else if (domain.getDeviceType().equals("4")) { // 交换机

                String alarmContent = domain.getAlarmContent();
                if (null != alarmContent && !alarmContent.equals("")
                        && alarmContent.trim().split(" ").length > 2) {
                    String[] content = alarmContent.trim().split(" ");
                    String contentTemp = content[1];
                    domain.setAlarmContent(contentTemp);
                    businessList = ibatisDAO
                            .getData(IBATIS_NAMESPACE + ".switchRelationPm", domain);
                }

                if (null != businessList && !businessList.isEmpty()) {
                    AlarmViewDomain businessBean = new AlarmViewDomain();
                    businessBean.setBusiness("该告警影响如下相关物理机：");
                    businessList.add(0, businessBean);
                }

            } else if (domain.getDeviceType().equals("2")) { // 虚拟机
                businessList = ibatisDAO.getData(IBATIS_NAMESPACE + ".vmRelationSystem", domain);
                if (null != businessList && !businessList.isEmpty()) {
                    AlarmViewDomain businessBean = new AlarmViewDomain();
                    businessBean.setBusiness("该告警影响如下相关业务：");
                    businessList.add(0, businessBean);
                }
            }

        } catch (Exception e) {
            logger.info(getText("function.title") + " get alarm device CI error ", e);
        }
        try {

            JSONArray jsonObj = JSONArray.fromObject(businessList);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + "相关性分析结束");
            operationInfo = getText("oplog.detail.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.detail.error"), e);
        }
    }

    /**
     * 根据设备类型得到CI项属性展示顺序
     * @param resourceType
     * @return
     */
    private String getResourceOrder(String resourceType) {
        String resourceOrder = null;
        if (null == resourceType || resourceType.trim().equals("")) {
            resourceOrder = null;
        } else if (resourceType.contains("-RT-FW")) { // 防火墙
            resourceOrder = ciOrderFW;
        } else if (resourceType.contains("-RT-RT")) { // 路由器
            resourceOrder = ciOrderRT;
        } else if (resourceType.contains("-RT-AS")) { // 阵列
            resourceOrder = ciOrderAS;
        } else if (resourceType.contains("-RT-SW")) { // 交换机
            resourceOrder = ciOrderSW;
        } else if (resourceType.contains("-RT-MM")) { // 小型机
            resourceOrder = ciOrderMM;
        } else if (resourceType.contains("-RT-SRV")) { // 物理机
            resourceOrder = ciOrderSRV;
        } else if (resourceType.contains("-RT-BS")) { // 块存储
            resourceOrder = ciOrderBS;
        } else if (resourceType.contains("-RT-MC")) { // 小型机分区
            resourceOrder = ciOrderMC;
        } else if (resourceType.contains("-RT-VM")) { // 虚拟机
            resourceOrder = ciOrderVM;
        } else if (resourceType.contains("-RT-OS")) { // 对象存储
            resourceOrder = ciOrderOS;
        } else if (resourceType.contains("-RT-LB") || resourceType.contains("-RT-ELB")) { // 负载均衡
            resourceOrder = ciOrderLB;
        }
        return resourceOrder;
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getReLationCmdbID() {
        return reLationCmdbID;
    }

    public void setReLationCmdbID(String reLationCmdbID) {
        this.reLationCmdbID = reLationCmdbID;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    public String getCiOrderFW() {
        return ciOrderFW;
    }

    public void setCiOrderFW(String ciOrderFW) {
        this.ciOrderFW = ciOrderFW;
    }

    public String getCiOrderRT() {
        return ciOrderRT;
    }

    public void setCiOrderRT(String ciOrderRT) {
        this.ciOrderRT = ciOrderRT;
    }

    public String getCiOrderAS() {
        return ciOrderAS;
    }

    public void setCiOrderAS(String ciOrderAS) {
        this.ciOrderAS = ciOrderAS;
    }

    public String getCiOrderSW() {
        return ciOrderSW;
    }

    public void setCiOrderSW(String ciOrderSW) {
        this.ciOrderSW = ciOrderSW;
    }

    public String getCiOrderMM() {
        return ciOrderMM;
    }

    public void setCiOrderMM(String ciOrderMM) {
        this.ciOrderMM = ciOrderMM;
    }

    public String getCiOrderBS() {
        return ciOrderBS;
    }

    public void setCiOrderBS(String ciOrderBS) {
        this.ciOrderBS = ciOrderBS;
    }

    public String getCiOrderVM() {
        return ciOrderVM;
    }

    public void setCiOrderVM(String ciOrderVM) {
        this.ciOrderVM = ciOrderVM;
    }

    public String getCiOrderOS() {
        return ciOrderOS;
    }

    public void setCiOrderOS(String ciOrderOS) {
        this.ciOrderOS = ciOrderOS;
    }

    public String getCiOrderLB() {
        return ciOrderLB;
    }

    public void setCiOrderLB(String ciOrderLB) {
        this.ciOrderLB = ciOrderLB;
    }

    public String getCiOrderSRV() {
        return ciOrderSRV;
    }

    public void setCiOrderSRV(String ciOrderSRV) {
        this.ciOrderSRV = ciOrderSRV;
    }

    public String getCiOrderMC() {
        return ciOrderMC;
    }

    public void setCiOrderMC(String ciOrderMC) {
        this.ciOrderMC = ciOrderMC;
    }

}
