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
package com.cloudmaster.cmp.alarm.regulation.web;

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmRegulationBindAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5990414034562982193L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationBindAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmRegulationDomain domain;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmRegulation";

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 告警平台数据库连接
     */
    private IbatisDAO ibatisDAOAlarm;

    /**
     * 修改
     * @return String
     * @param
     */
    @SuppressWarnings("unchecked")
    public String bind() {
        logger.info(getText("function.title") + getText("log.edit.begin"));
        String opParam[] = { getText("field.label.ruleName") + ": " +domain.getRuleName() };
        try {

            ibatisDAO.updateData(IBATIS_NAMESPACE + ".deleteRuleAction", domain);
            ibatisDAO.updateData(IBATIS_NAMESPACE + ".deleteRuleRedefine", domain);

            // 删除告警平台数据
            List<AlarmRegulationDomain> alarmPlatformli = ibatisDAOAlarm.getData(
                    "alarmPlatform.getRuleRelation", domain);
            if (null != alarmPlatformli && !alarmPlatformli.isEmpty()) {
                String id = "('";
                for (AlarmRegulationDomain bean : alarmPlatformli) {
                    id = id + bean.getID() + "','";
                }
                id = id + "')";
                AlarmRegulationDomain alarmPlatformbean = new AlarmRegulationDomain();
                alarmPlatformbean.setID(id);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleRelation", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleRedefine", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleLevel", alarmPlatformbean);
                ibatisDAOAlarm.deleteData("alarmPlatform.deleteRuleNofity", alarmPlatformbean);
            }

            String ruleActoin = domain.getRuleAction();
            if (ruleActoin.contains("1")) { // 1-自动清除
                AlarmRegulationDomain bean = new AlarmRegulationDomain();
                bean.setID(domain.getID());
                bean.setRuleAction("1");
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleAction", bean);
            }
            if (ruleActoin.contains("2")) {// 2-自动确认
                AlarmRegulationDomain bean = new AlarmRegulationDomain();
                bean.setID(domain.getID());
                bean.setRuleAction("2");
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleAction", bean);

                // 告警平台添加数据
                bean.setAlarmFlowName("5"); // 1--过滤,2--升降级,3--重定义,4-自动清除，5-自动确认，6-通知
                bean.setRuleName(domain.getRuleName());
                bean.setRuleState(domain.getRuleState());
                ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", bean);
            }
            if (ruleActoin.contains("3")) {// 3-自动通知
                domain.setRuleAction("3");
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleAction", domain);
                // 告警平台添加数据
                domain.setAlarmFlowName("6"); 
                ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", domain);
                
                String id = domain.getID();
                List<AlarmRegulationDomain> templi = ibatisDAOAlarm.getData(
                        "alarmPlatform.getRuleRelation", domain);
                for (AlarmRegulationDomain tempBean : templi) {
                    String alarmFlowName = tempBean.getAlarmFlowName();
                    String ruleID = tempBean.getID();
                    if (alarmFlowName.equals("6")) {
                        domain.setID(ruleID); //告警平台的ID值
                        break;
                    }
                }
                ibatisDAOAlarm.insertData("alarmPlatform.insertRuleNotify", domain);
                domain.setID(id); //恢复Id值
            }
            if (ruleActoin.contains("4")) {// 4-自动过滤
                AlarmRegulationDomain bean = new AlarmRegulationDomain();
                bean.setID(domain.getID());
                bean.setRuleAction("4");
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleAction", bean);
                // 告警平台添加数据
                bean.setAlarmFlowName("1"); 
                bean.setRuleName(domain.getRuleName());
                bean.setRuleState(domain.getRuleState());
                ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", bean);
            }
            if (ruleActoin.contains("5")) {// 5-重定义
                AlarmRegulationDomain bean = new AlarmRegulationDomain();
                bean.setID(domain.getID());
                bean.setRuleAction("5");
                ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleAction", bean);
                List<AlarmRegulationDomain> li = getRedefineContentList();
                if (null != li && !li.isEmpty()) {
                    // 告警平台添加数据
                    bean.setRuleName(domain.getRuleName());
                    bean.setRuleState(domain.getRuleState());
                    bean.setAlarmFlowName("2"); 
                    ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", bean);
                    bean.setAlarmFlowName("3"); 
                    ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", bean);

                    String ruleLevelId = "";
                    String ruleRedefineId = "";
                    List<AlarmRegulationDomain> templi = ibatisDAOAlarm.getData(
                            "alarmPlatform.getRuleRelation", domain);
                    for (AlarmRegulationDomain tempBean : templi) {
                        String alarmFlowName = tempBean.getAlarmFlowName();
                        String ruleID = tempBean.getID();
                        if (alarmFlowName.equals("2")) {
                            ruleLevelId = ruleID;
                        } else if (alarmFlowName.equals("3")) {
                            ruleRedefineId = ruleID;
                        }
                    }
                    bean.setID(ruleLevelId);
                    bean.setLevelId(domain.getLevelId());
                    ibatisDAOAlarm.insertData("alarmPlatform.insertRuleLevel", bean);

                    for (AlarmRegulationDomain redefineBean : li) {
                        redefineBean.setID(domain.getID());
                        redefineBean.setLevelId(domain.getLevelId());
                        ibatisDAO
                                .insertData(IBATIS_NAMESPACE + ".insertRuleRedefine", redefineBean);

                        // 告警平台添加数据
                        redefineBean.setID(ruleRedefineId);
                        redefineBean.setRedefineColumn("text"); // 重定义告警内容字段
                        ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRedefine", redefineBean);
                    }
                } else {
                    bean.setLevelId(domain.getLevelId());
                    ibatisDAO.insertData(IBATIS_NAMESPACE + ".insertRuleRedefine", bean);
                    // 告警平台添加数据
                    bean.setRuleName(domain.getRuleName());
                    bean.setAlarmFlowName("2"); 
                    bean.setRuleState(domain.getRuleState());
                    ibatisDAOAlarm.insertData("alarmPlatform.insertRuleRelation", bean);
                    List<AlarmRegulationDomain> templi = ibatisDAOAlarm.getData(
                            "alarmPlatform.getRuleRelation", domain);
                    String ruleLevelId = "";
                    for (AlarmRegulationDomain tempBean : templi) {
                        String alarmFlowName = tempBean.getAlarmFlowName();
                        String ruleID = tempBean.getID();
                        if (alarmFlowName.equals("2")) {
                            ruleLevelId = ruleID;
                        }
                    }
                    bean.setID(ruleLevelId);
                    bean.setLevelId(domain.getLevelId());
                    ibatisDAOAlarm.insertData("alarmPlatform.insertRuleLevel", bean);
                }
            }
            msg = getText("message.bind.success");
            logger.info(getText("function.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);

        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.edit.error"), e);
            errorMsg = getText("message.bind.error") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            forward = "INPUT";
        }
        return forward;
    }

    public List<AlarmRegulationDomain> getRedefineContentList() {
        List<AlarmRegulationDomain> redefineContentList = new ArrayList<AlarmRegulationDomain>();
        if (null != domain.getOriginalContent() && !domain.getOriginalContent().equals("")) {
            String[] originalContent = domain.getOriginalContent().split(",");
            String[] redefineContent = domain.getRedefineContent().split(",");
            AlarmRegulationDomain temp = null;
            for (int i = 0; i < originalContent.length; i++) {
                temp = new AlarmRegulationDomain();
                temp.setOriginalContent(originalContent[i].trim());
                temp.setRedefineContent(redefineContent[i].trim());
                redefineContentList.add(temp);
            }
        }
        return redefineContentList;
    }

    public AlarmRegulationDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRegulationDomain domain) {
        this.domain = domain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public IbatisDAO getIbatisDAOAlarm() {
        return ibatisDAOAlarm;
    }

    public void setIbatisDAOAlarm(IbatisDAO ibatisDAOAlarm) {
        this.ibatisDAOAlarm = ibatisDAOAlarm;
    }

}
