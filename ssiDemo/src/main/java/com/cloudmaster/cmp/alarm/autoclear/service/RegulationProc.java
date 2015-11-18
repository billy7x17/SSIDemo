/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * @(#)RegulationClear.java 2013-4-18
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.alarm.autoclear.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.cloudmaster.cmp.alarm.autoclear.dao.AutoClearAlarmDomain;
import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.util.threadpool.ThreadPoolModule;
import com.neusoft.mid.engine.fastrule.RuleAgent;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-18 下午03:54:12
 */
public class RegulationProc extends BaseJob {
    /**
     * 日志前缀
     */
    private String logBegin = "Regulation CLEAR ALARM,";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("alarmlog");

    /**
     * 数据库dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "AutoClearAlarm.";

    /**
     * 自动清除告警的时间间隔，单位分钟
     */
    private String intervalTime;

    /**
     * 每次处理的阀值Id的数量
     */
    private int perSize = 20;

    /**
     * 告警通知处理类
     */
    private AlarmNotify alarmNotify;

    /**
     * 按照规则清除、确认告警
     */
    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        logger.info(logBegin + "start");
        String jobresult = "success";
        String joblog = "";
        try {
            regulationProc();
        } catch (Exception e) {
            jobresult = "error";
            joblog += " 规则处理，清除、确认告警出现异常" + e.getMessage();
            logger.info(logBegin + " 规则处理，清除、确认告警出现异常", e);
        }

        context.setResult(jobresult);// context.setResult("success")表示任务执行成功；其他则失败
        if (joblog.equals("")) {
            context.put("log", "规则处理，清除、确认告警结束");
        } else {
            context.put("log", joblog);
        }
        logger.info(logBegin + "end");
    }

    /**
     * 按照规则确认清除告警
     * @throws Exception
     */
    public void regulationProc() throws Exception{
        // 清除规则
        AutoClearAlarmDomain clearAlarmBean = new AutoClearAlarmDomain();
        clearAlarmBean.setClearStatus("1"); // 1-未清除，2-手工清除，3-自动清除
        clearAlarmBean.setClearIntervalTime(intervalTime);
        List<AutoClearAlarmDomain> clearAlarmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                + "getAlarm", clearAlarmBean);
        AlarmRegulationDomain clearRegBean = new AlarmRegulationDomain();
        clearRegBean.setRuleAction("1"); // 1-清除，2-确认，3-通知
        List<AlarmRegulationDomain> clearRegLi = ibatisDAO.getData(
                IBATIS_NAMESPACE + "getRule", clearRegBean);
        regulatoinAlarmProc(clearRegLi, clearAlarmLi, "1");

        // 确认规则
        AutoClearAlarmDomain confirmAlarmBean = new AutoClearAlarmDomain();
        confirmAlarmBean.setConfirmStatus("1"); // 1-未确认，2-手工确认，3-自动确认
        confirmAlarmBean.setClearIntervalTime(intervalTime);
        List<AutoClearAlarmDomain> confirmAlarmLi = ibatisDAO.getData(IBATIS_NAMESPACE
                + "getAlarm", confirmAlarmBean);
        AlarmRegulationDomain confirmRegBean = new AlarmRegulationDomain();
        confirmRegBean.setRuleAction("2"); // 1-清除，2-确认，3-通知
        List<AlarmRegulationDomain> confirmRegLi = ibatisDAO.getData(IBATIS_NAMESPACE
                + "getRule", confirmRegBean);
        regulatoinAlarmProc(confirmRegLi, confirmAlarmLi, "2");
    }
    
    /**
     * 按照规则确认清除告警
     * @param regulationLi
     * @param alarmLi
     * @param regType
     * @throws Exception
     */
    private void regulatoinAlarmProc(List<AlarmRegulationDomain> regulationLi,
            List<AutoClearAlarmDomain> alarmLi, String regType) throws Exception {

        List<AutoClearAlarmDomain> list = new ArrayList<AutoClearAlarmDomain>();
        Map<String, String> map = new HashMap<String, String>();
        if (null != regulationLi && !regulationLi.isEmpty() && null != alarmLi
                && !alarmLi.isEmpty()) {
            for (AlarmRegulationDomain regulation : regulationLi) {
                for (AutoClearAlarmDomain alarm : alarmLi) {
                    Map<String, Object> regmap = new HashMap<String, Object>();
                    regmap.put("alarmTitle", alarm.getAlarmTitle());
                    regmap.put("alarmContent", alarm.getAlarmContent());
                    regmap.put("alarmIP", alarm.getAlarmIP());
                    regmap.put("alarmType", alarm.getAlarmType());
                    regmap.put("alarmLevel", alarm.getAlarmGrade());
                    regmap.put("alarmOID", alarm.getMibId());
                    regmap.put("alarmDevice", alarm.getDeviceType());
                    regmap.put("alarmTime", alarm.getAlarmTime());

                    RuleAgent agent = new RuleAgent();
                    Object obj = agent.eval(regulation.getRuleRegexp(), regmap);

                    boolean check = true; // regulaton(regmap,regulation.getRuleRegexp());
                    if (null != obj && obj.toString().equals("true")
                            && !map.containsKey(alarm.getAlarmID())) {
                        list.add(alarm);
                        // 防止重复放入
                        map.put(alarm.getAlarmID(), alarm.getAlarmID());
                    }
                }
            }
        }
        if (null != list && !list.isEmpty()) {
            String alarmId = "('";
            AutoClearAlarmDomain bean = null;
            for (int i = 0; i < list.size(); i++) {
                bean = (AutoClearAlarmDomain) list.get(i);
                alarmId += bean.getAlarmID() + "','";

                // 清除告警，判断是否发短信
                if (regType.equals("1") && null != bean.getNewAlarmNotifyRule()
                        && !bean.getNewAlarmNotifyRule().equals("")) {
                    putThreadPool(bean);
                }
            }
            alarmId += "')";
            bean.setAlarmID(alarmId);
            if (regType.equals("1")) {
                bean.setClearStatus("1");
            } else {
                bean.setConfirmStatus("2");
            }
            ibatisDAO.updateData(IBATIS_NAMESPACE + "regulationAlarmProc", bean);

        }
    }

    /**
     * 将任务放入线程池
     * @param bean
     */
    private void putThreadPool(final AutoClearAlarmDomain bean) {
        ThreadPoolModule.getInstance().getCmpthreadpool().execute(new Runnable() {
            public void run() {
                alarmNotify.autoClearAlarmNotify(bean);
            }
        });
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getPerSize() {
        return perSize;
    }

    public void setPerSize(int perSize) {
        this.perSize = perSize;
    }

    public AlarmNotify getAlarmNotify() {
        return alarmNotify;
    }

    public void setAlarmNotify(AlarmNotify alarmNotify) {
        this.alarmNotify = alarmNotify;
    }

}
