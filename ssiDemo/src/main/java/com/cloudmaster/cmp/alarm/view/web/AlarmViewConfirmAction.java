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

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.container.ibatis.BatchVO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DateUtil;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警浏览
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmViewConfirmAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -975406630357832937L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmViewConfirmAction.class);

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmView";

    /**
     * bean对象
     */
    private AlarmViewDomain domain;

    /**
     * 研发中心数据库名称
     */
    private String nmsDBName;

    public void alarmConfirm() {
        logger.info(getText("function.title") + getText("log.confirm.begin"));
        String result = "fail";
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAgentName() };
        try {
            UserInfo userInfo = (UserInfo) ServletActionContext.getContext().getSession()
                    .get(SessionKeys.userInfo);
            String userID = userInfo.getUserId();
            String time = DateUtil.dateToStr(new Date());

            domain.setConfirmPerson(userID);
            domain.setConfirmTime(time);
            domain.setNmsDB(nmsDBName);
            int i = ibatisDAO.updateData(IBATIS_NAMESPACE + ".updateConfirm", domain);
            if (i > 0) {
                result = "success";
            }
        } catch (Exception e) {
            operationInfo = getText("oplog.confirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.confirm.error"), e);
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
            logger.info(getText("function.title") + getText("log.confirm.end"));
            operationInfo = getText("oplog.confirm.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.confirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.confirm.error"), e);
        }
    }

    /**
     * 批量确认告警
     */
    public void alarmBatchConfirm() {
        logger.info(getText("function.title") + getText("log.batchConfirm.begin"));

        String result = "fail";
        String opParam[] = { getText("field.label.agentName") + ": " + domain.getAlarmID() };
        try {
            UserInfo userInfo = (UserInfo) ServletActionContext.getContext().getSession()
                    .get(SessionKeys.userInfo);
            String userID = userInfo.getUserId();
            String time = DateUtil.dateToStr(new Date());

            /*组装批量查询组件*/
            List<BatchVO> batchList = new ArrayList<BatchVO>();
            
            if (null != domain && null != domain.getAlarmID()) {
                String [] ids = domain.getAlarmID().split(",");
                for (int i = 0; i < ids.length; i++){
                    AlarmViewDomain para = new AlarmViewDomain();
                    para.setConfirmPerson(userID);
                    para.setConfirmTime(time);
                    para.setNmsDB(nmsDBName);
                    para.setAlarmID(ids[i]);
                    batchList.add(new BatchVO("MOD", IBATIS_NAMESPACE + ".updateConfirm", para));    
                }
            }
            
            ibatisDAO.updateBatchData(batchList);
            
            result = "success";
        } catch (Exception e) {
            operationInfo = getText("oplog.confirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.batchConfirm.error"), e);
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
            logger.info(getText("function.title") + getText("log.confirm.end"));
            operationInfo = getText("oplog.batchConfirm.success", opParam);
        } catch (Exception e) {
            operationInfo = getText("oplog.batchConfirm.error", opParam)
                    + getText("common.message.systemError");
            logger.info(getText("function.title") + getText("log.batchConfirm.error"), e);
        }
    }

    public AlarmViewDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmViewDomain domain) {
        this.domain = domain;
    }

    public String getNmsDBName() {
        return nmsDBName;
    }

    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }
}
