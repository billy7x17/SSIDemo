/**
 * @(#)AlarmRegulationBeforeBindAction.java 2013-4-16
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
import java.util.Iterator;
import java.util.List;

import com.cloudmaster.cmp.alarm.regulation.dao.AlarmRegulationDomain;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-16 上午09:25:29
 */
public class AlarmRegulationBeforeBindAction extends BaseAction implements IAuthIdGetter {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4171931504123284858L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmRegulationBeforeEditAction.class);

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
     * 过滤规则标识
     */
    private String filterId;

    /**
     * 重定义级别
     */
    private List<AlarmRegulationDomain> levelList;

    /**
     * 重定义内容
     */
    private List<AlarmRegulationDomain> redefineContentList;

    /**
     * 修改前准备
     * @return
     */
    public String beforeBind() {
        logger.info(getText("function.title") + getText("log.beforeEdit.begin"));

        try {
            
            String ruleState = domain.getRuleState();
            
            // 设置默认值
            domain.setClearNotify("0"); // 默认不通知
            domain
                    .setNotifyContent("${alarm_time}，设备${alarm_IP}出现如下告警：${alarm_title}，请处理。告警内容：${alarm_content}");
            domain.setNotifySMS("${alarm_time}，设备${alarm_IP}出现如下告警：${alarm_title}，请处理");
            domain.setNotifyTitle("${alarm_time}，设备${alarm_IP}出现如下告警：${alarm_title}，请处理");

            List<AlarmRegulationDomain> li = (List<AlarmRegulationDomain>) ibatisDAO.getData(
                    IBATIS_NAMESPACE + ".getRuleAction", domain);
            if (null != li && !li.isEmpty()) {
                String ruleAction = "";
                Iterator<AlarmRegulationDomain> it = li.iterator();
                while (it.hasNext()) {
                    AlarmRegulationDomain bean = (AlarmRegulationDomain) it.next();
                    if (bean.getRuleAction().equals("1")) {
                        ruleAction += "1";
                    } else if (bean.getRuleAction().equals("2")) {
                        ruleAction += "2";
                    } else if (bean.getRuleAction().equals("3")) {
                        String ruleName = domain.getRuleName();
                        domain = bean;
                        domain.setRuleName(ruleName);
                        ruleAction += "3";
                    } else if (bean.getRuleAction().equals("4")) {
                        ruleAction += "4";
                    } else {
                        redefineContentList = (List) ibatisDAO.getData(IBATIS_NAMESPACE
                                + ".getRedefineContent", domain);
                        ruleAction += "5";
                    }
                }
                domain.setRuleAction(ruleAction);

                // 只重定义级别，没有重定义内容
                if (null == redefineContentList || redefineContentList.isEmpty()) {
                    redefineContentList = new ArrayList<AlarmRegulationDomain>();
                } else if (redefineContentList.size() == 1
                        && (null == redefineContentList.get(0).getOriginalContent() || redefineContentList
                                .get(0).getOriginalContent().equals(""))) {
                    domain.setLevelId(redefineContentList.get(0).getLevelId());
                    redefineContentList = new ArrayList<AlarmRegulationDomain>();
                } else {
                    domain.setLevelId(redefineContentList.get(0).getLevelId());
                }
            }
            domain.setRuleState(ruleState);
            levelList = (List) ibatisDAO.getData(IBATIS_NAMESPACE + ".getAlarmLevel", domain);

            logger.info(getText("function.title") + getText("log.beforeEdit.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
        }
        return forward;
    }

    public AlarmRegulationDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmRegulationDomain domain) {
        this.domain = domain;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public List<AlarmRegulationDomain> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<AlarmRegulationDomain> levelList) {
        this.levelList = levelList;
    }

    public List<AlarmRegulationDomain> getRedefineContentList() {
        return redefineContentList;
    }

    public void setRedefineContentList(List<AlarmRegulationDomain> redefineContentList) {
        this.redefineContentList = redefineContentList;
    }

}
