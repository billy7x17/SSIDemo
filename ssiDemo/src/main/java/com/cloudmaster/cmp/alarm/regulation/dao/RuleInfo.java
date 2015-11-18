/**
 * @(#)RuleInfo.java 2013-4-15
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.alarm.regulation.dao;


/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-4-15 下午02:45:51
 */
public class RuleInfo {
    // 节点名称
    private String name;

    // 节点级别
    private String level;

    // 节点父ID
    private String pId;

    // 节点关系
    private String relation;

    // 规则字段
    private String ruleColumn;

    // 规则比较
    private String ruleOperator;

    // 规则值
    private String ruleValue;

    // 规则字段类型
    private String ruleColumnType;

    // 规则字段类型
    private String id;

    // 规则字段类型
    private boolean open;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRuleColumn() {
        return ruleColumn;
    }

    public void setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
    }

    public String getRuleOperator() {
        return ruleOperator;
    }

    public void setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getRuleColumnType() {
        return ruleColumnType;
    }

    public void setRuleColumnType(String ruleColumnType) {
        this.ruleColumnType = ruleColumnType;
    }

}
