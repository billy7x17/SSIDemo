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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.autoclear.service.AlarmNotify;
import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DateUtil;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.AlarmInfoBean;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.AlarmReport;
import com.cloudmaster.cmp.util.threadpool.ThreadPoolModule;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewClearAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3854591781669066989L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmViewClearAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 告警通知处理类
     */
    private AlarmNotify alarmNotify;

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;

    public void alarmClear() {
        logger.info(getText("function.title") + getText("log.clear.begin"));
        String result = "fail";
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() };
        try {
            UserInfo userInfo = (UserInfo) ServletActionContext.getContext().getSession().get(
                    SessionKeys.userInfo);
            String userID = userInfo.getUserId();
            String time = DateUtil.dateToStr(new Date());
            domain.setNmsDB(nmsDBName);
            AlarmViewDomain bean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".detail", domain);

            AlarmInfoBean reportBean = new AlarmInfoBean();
            reportBean.setAgentIp(bean.getAlarmIP());
            reportBean.setAlarmType(bean.getAlarmType());
            reportBean.setAlarmTitle(bean.getAlarmTitle());
            reportBean.setAlarmLevel("5"); // 清除告警
            reportBean.setAlarmLevelOriginal(bean.getAlarmGrade());//原始级别
            reportBean.setAlarmOid(bean.getAlarmIdentify());
            reportBean.setAlarmContent(bean.getAlarmContent());
            reportBean.setAgentType(bean.getDeviceType());
            reportBean.setAlarmImpact(bean.getAlarmImpact());
            reportBean.setAlarmIdentityID(bean.getAlarmReportOID());
            reportBean.setAlarmTitleId(bean.getAlarmTitleId());
            reportBean.setObjectID(bean.getDeviceId());
            reportBean.setSystemName(bean.getDeviceName());

            AlarmReport alarmReport = new AlarmReport();
            alarmReport.reportAlarm(reportBean);

            // if (i > 0) {
            result = "success";

            // 记录清除人员信息
            domain.setClearPerson(userID);
            domain.setClearTime(time);
            domain.setNmsDB(nmsDBName);
            int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".updateClear", domain);
            
            // 清除告警，向亚信平台发送告警数据
            // sendAlarm2Asia(domain);
            // clearNotify(domain);
            // }
        } catch (Exception e) {
            operationInfo = getText("oplog.clear.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.clear.error"), e);
        }
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.clear.end"));
            operationInfo = getText("oplog.clear.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.clear.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.clear.error"), e);
        }
    }
    
    /**
     * 批量清除告警
     */
    public void alarmBatchClear(){
        
        logger.info(getText("function.title") + getText("log.batchClear.begin"));
        String result = "fail";
        String opParam[] = { domain.getAlarmID() };
        try {
            UserInfo userInfo = (UserInfo) ServletActionContext.getContext().getSession().get(
                    SessionKeys.userInfo);
            String userID = userInfo.getUserId();
            String time = DateUtil.dateToStr(new Date());
            
            String[] ids = domain.getAlarmID().split(",");
            
            List<BatchVO> batchList = new ArrayList<BatchVO>();
            
            for (int i = 0; i < ids.length; i++) {
                
                AlarmViewDomain para = new AlarmViewDomain();
                
                para.setClearPerson(userID);
                para.setClearTime(time);
                para.setNmsDB(nmsDBName);
                para.setAlarmID(ids[i]);
                
                AlarmViewDomain bean = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                        + ".detail", para);

                AlarmInfoBean reportBean = new AlarmInfoBean();
                reportBean.setAgentIp(bean.getAlarmIP());
                reportBean.setAlarmType(bean.getAlarmType());
                reportBean.setAlarmTitle(bean.getAlarmTitle());
                reportBean.setAlarmLevel("5"); // 清除告警
                reportBean.setAlarmLevelOriginal(bean.getAlarmGrade());//原始级别
                reportBean.setAlarmOid(bean.getAlarmIdentify());
                reportBean.setAlarmContent(bean.getAlarmContent());
                reportBean.setAgentType(bean.getDeviceType());
                reportBean.setAlarmImpact(bean.getAlarmImpact());
                reportBean.setAlarmIdentityID(bean.getAlarmReportOID());
                reportBean.setAlarmTitleId(bean.getAlarmTitleId());
                reportBean.setObjectID(bean.getDeviceId());
                reportBean.setSystemName(bean.getDeviceName());

                AlarmReport alarmReport = new AlarmReport();
                alarmReport.reportAlarm(reportBean);

                batchList.add(new BatchVO("MOD", IBATIS_NAMESPACE + ".updateClear" , para));
                
            }
            
            ibatisDAO.updateBatchData(batchList);
            
            result = "success";
            
        } catch (Exception e) {
            operationInfo = getText("oplog.clear.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.clear.error"), e);
        }
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.batchClear.end"));
            operationInfo = getText("oplog.clear.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.clear.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.batchClear.error"), e);
        }
        
    }

    private void clearNotify(AlarmViewDomain bean) {
        try {
            AlarmViewDomain alarm = (AlarmViewDomain) ibatisDAO.getSingleRecord(IBATIS_NAMESPACE
                    + ".detail", bean);
            if (null != alarm && null != alarm.getNewAlarmNotifyRule()
                    && !alarm.getNewAlarmNotifyRule().equals("")) {
                putThreadPool(alarm);
            }
        } catch (Exception e) {
            logger.error(getText("function.title") + " clear alarm notify eror:", e);
        }
    }

    /**
     * 将任务放入线程池
     * @param bean
     */
    private void putThreadPool(final AlarmViewDomain bean) {
        ThreadPoolModule.getInstance().getCmpthreadpool().execute(new Runnable() {
            public void run() {
                alarmNotify.manualClearAlarmNotify(bean);
            }
        });
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public AlarmNotify getAlarmNotify() {
        return alarmNotify;
    }

    public void setAlarmNotify(AlarmNotify alarmNotify) {
        this.alarmNotify = alarmNotify;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

}
