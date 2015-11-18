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
package com.cloudmaster.cmp.alarm.standardize.service;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.alarm.standardize.dao.AlarmDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警采集
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmDBProc {

    /**
     * 缓存上次采集最大告警时间
     */
    private AlarmTimeRecord timeRecord;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "AlarmStandardize.";

    /**
     * njdb数据库
     */
    private IbatisDAO ibatisDAO;

    /**
     * cloudmaster数据库
     */
    private IbatisDAO ibatisDAOCM;

    /**
     * nagios数据库
     */
    private IbatisDAO ibatisDAONagios;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * 日志前缀
     */
    private String logBegin = "AlarmStandardize.dbProc";

    /**
     * 获取mals_nm_alarm_device_t中告警数据
     * @return
     */
    public List<AlarmDomain> getMalsDeviceAlarm() {
        List<AlarmDomain> li = null;
        try {
            String lastTime = timeRecord.get(AlarmTimeRecord.LASTTIME_MALS_DEVICE);
            AlarmDomain domain = new AlarmDomain();
            if (null != lastTime && !lastTime.equals("")) {
                domain.setAlarmTime(lastTime);
            }
            li = ibatisDAO.getData(IBATIS_NAMESPACE + "getMalsDeviceAlarm", domain);
            if (null == li || li.isEmpty()) {
                logger.debug(logBegin + "no alarm data from mals_device,lastTime:" + lastTime);
            }
        } catch (Exception e) {
            logger.info(logBegin + "getMalsDeviceAlarm error:", e);
        }
        return li;
    }

    /**
     * 获取hot_event_tab中的告警数据
     * @return
     */
    public List<AlarmDomain> getHotEventAlarm() {
        List<AlarmDomain> li = null;
        try {
            String lastTime = timeRecord.get(AlarmTimeRecord.LASTTIME_HOT_EVENT);
            AlarmDomain domain = new AlarmDomain();
            if (null != lastTime && !lastTime.equals("")) {
                domain.setAlarmTime(lastTime);
            }
            li = ibatisDAOCM.getData(IBATIS_NAMESPACE + "getHotEventAlarm", domain);
            if (null == li || li.isEmpty()) {
                logger.debug(logBegin + "no alarm data from hotEvent,lastTime:" + lastTime);
            }
        } catch (Exception e) {
            logger.info(logBegin + "getHotEventAlarm error:", e);
        }
        return li;
    }

    /**
     * 获取vm_hot_event_tab中的告警数据
     * @return
     */
    public List<AlarmDomain> getVmHotEventAlarm() {
        List<AlarmDomain> li = null;
        try {
            String lastTime = timeRecord.get(AlarmTimeRecord.LASTTIME_VM_HOT_EVENT);
            AlarmDomain domain = new AlarmDomain();
            if (null != lastTime && !lastTime.equals("")) {
                domain.setAlarmTime(lastTime);
            }
            li = ibatisDAOCM.getData(IBATIS_NAMESPACE + "getVmHotEventAlarm", domain);
            if (null == li || li.isEmpty()) {
                logger.debug(logBegin + "no alarm data from vmHotEvent,lastTime:" + lastTime);
            }
        } catch (SQLException e) {
            logger.info(logBegin + "getVmHotEventAlarm error:", e);
        }
        return li;
    }

    /**
     * 获取nagios_pmstatus中的告警数据
     * @return
     */
    public List<AlarmDomain> getNagiosHostAlarm() {
        List<AlarmDomain> li = null;
        try {
            String lastTime = timeRecord.get(AlarmTimeRecord.LASTTIME_NAGIOS_HOST);
            AlarmDomain domain = new AlarmDomain();
            if (null != lastTime && !lastTime.equals("")) {
                domain.setAlarmTime(lastTime);
            }
            li = ibatisDAONagios.getData(IBATIS_NAMESPACE + "getNagiosHostAlarm", domain);
            if (null == li || li.isEmpty()) {
                logger.debug(logBegin + "no alarm data from nagiosHost,lastTime:" + lastTime);
            }
        } catch (SQLException e) {
            logger.info(logBegin + "getNagiosHostAlarm error:", e);
        }
        return li;
    }

    /**
     * 获取nagios_pmstatus中的告警数据
     * @return
     */
    public List<AlarmDomain> getNagiosServiceAlarm() {
        List<AlarmDomain> li = null;
        try {
            String lastTime = timeRecord.get(AlarmTimeRecord.LASTTIME_NAGIOS_SERVICE);
            AlarmDomain domain = new AlarmDomain();
            if (null != lastTime && !lastTime.equals("")) {
                domain.setAlarmTime(lastTime);
            }
            li = ibatisDAONagios.getData(IBATIS_NAMESPACE + "getNagiosServiceAlarm", domain);
            if (null == li || li.isEmpty()) {
                logger.debug(logBegin + "no alarm data from nagiosService,lastTime:" + lastTime);
            }
        } catch (SQLException e) {
            logger.info(logBegin + "getNagiosServiceAlarm error:", e);
        }
        return li;
    }

    /**
     * 获取最后告警时间
     * @param str
     * @return
     */
    public String getLastTime(String str) {
        String lastTime = null;

        /**
         * 告警来源类型 0-mals_nm_alarm_device_t 1-hot_event_tab 2-vm_hot_event_tab 3-nagios_pmstatus
         * 4-nagios_servicestatus
         */
        AlarmDomain domain = new AlarmDomain();
        if (str.equals(AlarmTimeRecord.LASTTIME_MALS_DEVICE)) {
            domain.setAlarmSourceType("0");
        } else if (str.equals(AlarmTimeRecord.LASTTIME_HOT_EVENT)) {
            domain.setAlarmSourceType("1");
        } else if (str.equals(AlarmTimeRecord.LASTTIME_VM_HOT_EVENT)) {
            domain.setAlarmSourceType("2");
        } else if (str.equals(AlarmTimeRecord.LASTTIME_NAGIOS_HOST)) {
            domain.setAlarmSourceType("3");
        } else if (str.equals(AlarmTimeRecord.LASTTIME_NAGIOS_SERVICE)) {
            domain.setAlarmSourceType("4");
        } else {
            return null;
        }

        try {
            lastTime = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "lastTime", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getLastTime error:", e);
        }
        return lastTime;
    }

    /**
     * 设置最后告警时间
     * @param domain
     */
    public void setLastTime(AlarmDomain domain) {
        String sourceType = domain.getAlarmSourceType();
        String lastTime = domain.getAlarmTime();
        /**
         * 告警来源类型 0-mals_nm_alarm_device_t 1-hot_event_tab 2-vm_hot_event_tab 3-nagios_pmstatus
         * 4-nagios_servicestatus
         */
        if (null != sourceType && !sourceType.equals("") && null != lastTime
                && !lastTime.equals("")) {
            if (sourceType.equals("0")
                    && (timeRecord.get(AlarmTimeRecord.LASTTIME_MALS_DEVICE) == null || timeRecord
                            .get(AlarmTimeRecord.LASTTIME_MALS_DEVICE).compareTo(lastTime) < 0)) {
                timeRecord.put(AlarmTimeRecord.LASTTIME_MALS_DEVICE, lastTime);
            } else if (sourceType.equals("1")
                    && (timeRecord.get(AlarmTimeRecord.LASTTIME_HOT_EVENT) == null || timeRecord
                            .get(AlarmTimeRecord.LASTTIME_HOT_EVENT).compareTo(lastTime) < 0)) {
                timeRecord.put(AlarmTimeRecord.LASTTIME_HOT_EVENT, lastTime);
            } else if (sourceType.equals("2")
                    && (timeRecord.get(AlarmTimeRecord.LASTTIME_VM_HOT_EVENT) == null || timeRecord
                            .get(AlarmTimeRecord.LASTTIME_VM_HOT_EVENT).compareTo(lastTime) < 0)) {
                timeRecord.put(AlarmTimeRecord.LASTTIME_VM_HOT_EVENT, lastTime);
            } else if (sourceType.equals("3")
                    && (timeRecord.get(AlarmTimeRecord.LASTTIME_NAGIOS_HOST) == null || timeRecord
                            .get(AlarmTimeRecord.LASTTIME_NAGIOS_HOST).compareTo(lastTime) < 0)) {
                timeRecord.put(AlarmTimeRecord.LASTTIME_NAGIOS_HOST, lastTime);
            } else if (sourceType.equals("4")
                    && (timeRecord.get(AlarmTimeRecord.LASTTIME_NAGIOS_SERVICE) == null || timeRecord
                            .get(AlarmTimeRecord.LASTTIME_NAGIOS_SERVICE).compareTo(lastTime) < 0)) {
                timeRecord.put(AlarmTimeRecord.LASTTIME_NAGIOS_SERVICE, lastTime);
            }
        }
    }

    /**
     * 获取重复告警信息
     * @param domain
     * @return
     */
    public AlarmDomain getRepeat(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO
                    .getSingleRecord(IBATIS_NAMESPACE + "repeatAlarm", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getRepeatAlarm error:", e);
        }
        return bean;
    }

    /**
     * 交换机、路由器、负载均衡，端口down的trap告警 
     * 交换机丢包率、误码率
     * @param domain
     * @return
     */
    public AlarmDomain getRepeatPartContent(AlarmDomain domain) {
        AlarmDomain bean = null;
        String alarmContent = domain.getAlarmContent();
        if (null == alarmContent || alarmContent.equals("")) {
            return null;
        }
        try {
            String[] content = alarmContent.trim().split(" ");
            String contentTemp = content[1];
            domain.setAlarmContent(contentTemp);

            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + "repeatAlarmPartContent", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getRepeatPartContent error:", e);
        }
        domain.setAlarmContent(alarmContent);
        return bean;
    }

    /**
     * 获取业务系统重复告警信息
     * @param domain
     * @return
     */
    public AlarmDomain getRepeatFullContent(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + "repeatAlarmFullContent", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getRepeatFullContent error:", e);
        }

        return bean;
    }

    /**
     * 获取过滤掉的重复告警信息
     * @param domain
     * @return
     */
    public AlarmDomain getFilteredRepeat(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(
                    IBATIS_NAMESPACE + "filteredRepeatAlarm", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getFilteredRepeat error:", e);
        }
        return bean;
    }

    /**
     * 交换机、路由器、负载均衡，端口down的trap告警
     *  交换机丢包率、误码率
     * @param domain
     * @return
     */
    public AlarmDomain getFilteredRepeatPartContent(AlarmDomain domain) {
        AlarmDomain bean = null;
        String alarmContent = domain.getAlarmContent();
        if (null == alarmContent || alarmContent.equals("")) {
            return null;
        }
        try {
            String[] content = alarmContent.trim().split(" ");
            String contentTemp = content[1];
            domain.setAlarmContent(contentTemp);
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + "filteredRepeatAlarmPartContent", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getFilteredRepeatPartContent error:", e);
        }
        domain.setAlarmContent(alarmContent);
        return bean;
    }

    /**
     * 获取过滤掉的业务系统重复告警信息
     * @param domain
     * @return
     */
    public AlarmDomain getFilteredRepeatFullContent(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + "filteredRepeatAlarmFullContent", domain);
        } catch (Exception e) {
            logger.info(logBegin + "getFilteredRepeatFullContent error:", e);
        }

        return bean;
    }

    /**
     * 获取花名册信息
     * @param domain
     * @return
     */
    public AlarmDomain getRoster(AlarmDomain domain) {
        AlarmDomain resultBean = null;
        try {
            AlarmDomain paramBean = new AlarmDomain();
            if (domain.getAlarmSourceType().equals("3") || domain.getAlarmSourceType().equals("4")) {
                paramBean.setRosterType("1"); // 0-阀值类型，1-规则类型
                paramBean.setThresholdId(domain.getThresholdId());
                resultBean = (AlarmDomain) ibatisDAO.getSingleRecord(
                        IBATIS_NAMESPACE + "getRoster", paramBean);
            } else if (domain.getAlarmSourceType().equals("0")
                    && (null == domain.getThresholdId() || domain.getThresholdId().equals(""))) {
                paramBean.setRosterType("1"); // 采集子系统接收的trap告警thresholdID为空
                paramBean.setAlarmIdentify(domain.getAlarmIdentify());
                resultBean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + "getTrapRoster", paramBean);
            } else {
                paramBean.setRosterType("0"); // 性能数据
                paramBean.setThresholdId(domain.getThresholdId());
                resultBean = (AlarmDomain) ibatisDAO.getSingleRecord(
                        IBATIS_NAMESPACE + "getRoster", paramBean);
            }

        } catch (Exception e) {
            logger.info(logBegin + "getRoster error:", e);
        }
        return resultBean;
    }

    /**
     * 记录告警信息
     * @param domain
     * @return
     */
    public boolean recordAlarm(AlarmDomain domain) {
        boolean isRecord = false;
        try {
            String id = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "getSeqId", null);
            domain.setAlarmID(id);
            if (domain.getAlarmIdentify().equals("3") || domain.getAlarmIdentify().equals("4")) {
                domain.setThresholdId(null);
            }
            ibatisDAO.insertData(IBATIS_NAMESPACE + "insertAlarm", domain);
            isRecord = true;
        } catch (Exception e) {
            logger.info(logBegin + "recordAlarm error:", e);
        }
        return isRecord;
    }

    /**
     * 修改重复告警信息
     * @param domain
     * @return
     */
    public boolean updateAlarm(AlarmDomain domain) {
        boolean isRecord = false;
        try {
            ibatisDAO.updateData(IBATIS_NAMESPACE + "updateAlarm", domain);
            isRecord = true;
        } catch (Exception e) {
            logger.info(logBegin + "updateAlarm error:", e);
        }
        return isRecord;

    }

    /**
     * 记录过滤掉的告警信息
     * @param domain
     * @return
     */
    public boolean recordFilteredAlarm(AlarmDomain domain) {
        boolean isRecord = false;
        try {
            String id = (String) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "getSeqId", null);
            domain.setAlarmID(id);
            ibatisDAO.insertData(IBATIS_NAMESPACE + "insertFilteredAlarm", domain);
            // }
            isRecord = true;
        } catch (Exception e) {
            logger.info(logBegin + "recordFilteredAlarm error:", e);
        }
        return isRecord;
    }

    /**
     * 修改过滤掉的重复告警信息
     * @param domain
     * @return
     */
    public boolean updateFilteredAlarm(AlarmDomain domain) {
        boolean isRecord = false;
        try {
            ibatisDAO.updateData(IBATIS_NAMESPACE + "updateFilteredAlarm", domain);
            isRecord = true;
        } catch (Exception e) {
            logger.info(logBegin + "updateFilteredAlarm error:", e);
        }
        return isRecord;

    }

    /**
     * 查询是否存在过滤配置信息
     * @param domain
     * @return
     */
    public boolean filterExist(AlarmDomain domain) {
        boolean exist = false;
        try {
            int i = ibatisDAO.getCount(IBATIS_NAMESPACE + "filterCount", domain);
            if (i > 0) {
                exist = true;
            }
        } catch (Exception e) {
            logger.info(logBegin + "filterExist error:", e);
        }
        return exist;
    }

    /**
     * 获取nagios告警的配置信息
     * @param domain
     * @return
     */
    public AlarmDomain getNagiosConfig(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "getNagiosConfig",
                    domain);
        } catch (Exception e) {
            logger.info(logBegin + "getNagiosConfig error:", e);
        }
        return bean;
    }

    /**
     * 亚信告警上报接口，获取告警设备信息
     * @param domain
     * @return
     */
    public AlarmDomain getDeviceInfo(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "getDeviceInfo",
                    domain);
        } catch (Exception e) {
            logger.info(logBegin + "getDeviceInfo error:", e);
        }
        return bean;
    }

    /**
     * 告警设备信息是否存在，是否工程中
     * @param domain
     * @return
     */
    public boolean deviceCheck(AlarmDomain domain) {
        boolean deviceOK = false;
        try {

            AlarmDomain bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + "getClusterInstance", domain);

            if (null == bean) {
                logger.info(logBegin + " alarm throwAway, device not exist in db,ip:"
                        + domain.getAlarmIP() + ",alarmIdentify:" + domain.getAlarmIdentify());
            }
        } catch (Exception e) {
            logger.info(logBegin + "deviceCheck error:", e);
        }
        return deviceOK;
    }

    /**
     * 清除告警trap, 获取清除告警配置信息
     * @param domain
     * @return
     */
    public AlarmDomain getClearTrapConfig(AlarmDomain domain) {
        AlarmDomain bean = null;
        try {
            bean = (AlarmDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE + "getClearTrapConfig",
                    domain);
        } catch (Exception e) {
            logger.info(logBegin + "getClearTrapConfig error:", e);
        }
        return bean;
    }

    /**
     * 清除告警trap, 清除告警
     * @param domain
     * @return
     */
    public void clearTrap(AlarmDomain domain) {
        try {
            ibatisDAO.updateData(IBATIS_NAMESPACE + "clearTrap", domain);
        } catch (Exception e) {
            logger.info(logBegin + "clearTrap error:", e);
        }
    }

    public AlarmTimeRecord getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(AlarmTimeRecord timeRecord) {
        this.timeRecord = timeRecord;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public IbatisDAO getIbatisDAOCM() {
        return ibatisDAOCM;
    }

    public void setIbatisDAOCM(IbatisDAO ibatisDAOCM) {
        this.ibatisDAOCM = ibatisDAOCM;
    }

    public IbatisDAO getIbatisDAONagios() {
        return ibatisDAONagios;
    }

    public void setIbatisDAONagios(IbatisDAO ibatisDAONagios) {
        this.ibatisDAONagios = ibatisDAONagios;
    }

}
