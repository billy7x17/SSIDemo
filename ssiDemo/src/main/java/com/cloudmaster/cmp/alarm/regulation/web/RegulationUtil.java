/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * @(#)Rule.java 2013-5-15
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.alarm.regulation.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.cloudmaster.cmp.alarm.regulation.dao.RuleInfo;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.ResponseObject;
import com.cloudmaster.cmp.util.AlarmSystem.transfer.SenderManager;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.mid.nmr.octopus.rule.Condition;
import com.neusoft.mid.nmr.octopus.rule.Rule;
import com.neusoft.mid.nmr.octopus.rule.RuleFactory;
import com.neusoft.mid.nmr.octopus.rule.RuleInstance;
import com.neusoft.mid.nmr.octopus.rule.config.OptionsEnum;
import com.neusoft.mid.nmr.octopus.rule.object.ExpressionCondition;
import com.neusoft.mid.nmr.octopus.rule.object.IntegerCondition;
import com.neusoft.mid.nmr.octopus.rule.object.RuleAnd;
import com.neusoft.mid.nmr.octopus.rule.object.RuleOr;
import com.neusoft.mid.nmr.octopus.rule.object.StringCondition;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-5-15 下午01:20:29
 */
public class RegulationUtil {

    private static String actionAnd = "and";

    private static String actionOr = "or";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(RegulationUtil.class);

    /**
     * json生成规则表达式
     * @param json
     * @return
     */
    public static String json2regexp(String json) {

        logger.debug("json:" + json);
        String regexp = "(";
        try {
            JSONArray array = JSONArray.fromObject(json);
            List<RuleInfo> ruleInfo = (List<RuleInfo>) JSONArray
                    .toCollection(array, RuleInfo.class);

            Map<String, String> conditoinMap = new HashMap<String, String>();
            Map<String, String> relationMap = new HashMap<String, String>();
            String firstRelation = "";
            for (int i = 0; i < ruleInfo.size(); i++) {
                RuleInfo rule = ruleInfo.get(i);

                logger.debug("rule" + i + ",Level:" + rule.getLevel() + ",Name:" + rule.getName()
                        + ",ParentId:" + rule.getpId() + ",Relation:" + rule.getRelation()
                        + ",RuleColumn:" + rule.getRuleColumn() + ",RuleColumnType:"
                        + rule.getRuleColumnType() + ",RuleOperator:" + rule.getRuleOperator()
                        + ",RuleValue:" + rule.getRuleValue() + ",Id:" + rule.getId());

                String level = rule.getLevel();
                if (level.equals("0")) {
                    firstRelation = rule.getRelation();
                } else if (level.equals("1")) {
                    String secondRelation = rule.getRelation();
                    String id = rule.getId();
                    conditoinMap.put(id, "(");
                    relationMap.put(id, secondRelation);
                } else {
                    String culomn = rule.getRuleColumn();
                    String value = rule.getRuleValue();
                    String type = rule.getRuleColumnType();
                    String operator = rule.getRuleOperator();
                    String pId = rule.getpId();
                    if (type.equals("String")) { // string类型，值加引号
                        value = "\"" + value + "\"";
                    } else { // 数字类型，culomn为number(culomn)
                        culomn = "number(" + culomn + ")";
                    }

                    String condition = "";
                    if (operator.equals("contain")) {
                        condition = "(contain(" + culomn + "," + value + "))";
                    } else if (operator.equals("regexp")) {
                        condition = "(regexp(" + culomn + "," + value + "))";
                    } else if (operator.equals("notContain")) {
                        condition = "(notContain(" + culomn + "," + value + "))";
                    } else {
                        condition = "(" + culomn + operator + value + ")";
                    }
                    String conditionStr = (String) conditoinMap.get(pId);
                    String relation = (String) relationMap.get(pId);
                    conditoinMap.put(pId, conditionStr + condition + relation);
                }
            }

            for (Map.Entry<String, String> m : conditoinMap.entrySet()) {
                String value = m.getValue();
                regexp = regexp + value.substring(0, value.length() - 2) + ")" + firstRelation;
            }
            regexp = regexp.substring(0, regexp.length() - 2) + ")";
        } catch (Exception e) {
            logger.info("create regexp error:", e);
        }
        return regexp;
    }

    /**
     * json生成规则XMl
     * @param json
     * @param ruleName
     * @param path
     * @return
     * @throws Exception
     */
    public static String json2regXml(String json, String ruleName, String path) throws Exception {

        logger.debug("json:" + json);
        JSONArray array = JSONArray.fromObject(json);
        List<RuleInfo> ruleInfo = (List<RuleInfo>) JSONArray.toCollection(array, RuleInfo.class);

        Map<String, Rule> secondLevelMap = new HashMap<String, Rule>();
        Rule firstRule = null;
        for (int i = 0; i < ruleInfo.size(); i++) {
            RuleInfo rule = ruleInfo.get(i);

            logger.debug("rule" + i + ",Level:" + rule.getLevel() + ",Name:" + rule.getName()
                    + ",ParentId:" + rule.getpId() + ",Relation:" + rule.getRelation()
                    + ",RuleColumn:" + rule.getRuleColumn() + ",RuleColumnType:"
                    + rule.getRuleColumnType() + ",RuleOperator:" + rule.getRuleOperator()
                    + ",RuleValue:" + rule.getRuleValue() + ",Id:" + rule.getId());

            String level = rule.getLevel();
            if (level.equals("0")) {
                String firstRelation = rule.getRelation();
                if (firstRelation.equals("&&")) {
                    firstRule = new RuleAnd(ruleName);
                    firstRule.setAction(actionAnd);
                } else {
                    firstRule = new RuleOr(ruleName);
                    firstRule.setAction(actionOr);
                }
            } else if (level.equals("1")) {
                String secondRelation = rule.getRelation();
                String id = rule.getId();
                Rule secondRule;
                if (secondRelation.equals("&&")) {
                    secondRule = new RuleAnd();
                    secondRule.setAction(actionAnd);
                } else {
                    secondRule = new RuleOr();
                    secondRule.setAction(actionOr);
                }
                secondLevelMap.put(id, secondRule);
            } else {
                String culomn = rule.getRuleColumn();
                String value = rule.getRuleValue();
                String type = rule.getRuleColumnType();
                String operator = rule.getRuleOperator();
                String pId = rule.getpId();
                Condition condition = null;
                if (type.equals("Int") || type.equals("Long")) {
                    condition = new IntegerCondition(culomn, culomn, value,
                            transferOperator(operator));
                } else if (operator.equals("regexp")) { // 数字类型
                    condition = new ExpressionCondition(culomn, culomn, value,
                            transferOperator(operator));
                } else {
                    condition = new StringCondition(culomn, culomn, value,
                            transferOperator(operator));
                }

                Rule secondRule = (Rule) secondLevelMap.get(pId);
                secondRule.addCondition(condition);
                secondLevelMap.put(pId, secondRule);
            }
        }
        RuleInstance instance = RuleFactory.getRuleInstance(path);

        // 添加规则条件
        for (Map.Entry<String, Rule> m : secondLevelMap.entrySet()) {
            Rule rule = m.getValue();
            firstRule.addRule(rule);
        }
        // 删除规则
        instance.delRule(ruleName);
        // 添加到规则
        instance.addRule(firstRule);
        // 获取规则XML
        String xml = instance.getRuleXML(ruleName);
        logger.info("规则XML：" + xml);
        return xml;
    }

    /**
     * json生成规则XMl
     * @param json
     * @param ruleName
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteregXml(String ruleName, String path) throws Exception {

        boolean isDelete = false;
        RuleInstance instance = RuleFactory.getRuleInstance(path);
        // 删除规则
        isDelete = instance.delRule(ruleName);
        return isDelete;
    }

    private static OptionsEnum transferOperator(String pageValue) {
        OptionsEnum operator = null;
        if (pageValue.equals("==")) {
            operator = OptionsEnum.OPITON_EQUAL;
        } else if (pageValue.equals("!=")) {
            operator = OptionsEnum.OPTION_NOT_EQUAL;
        } else if (pageValue.equals(">")) {
            operator = OptionsEnum.OPITON_GREATER;
        } else if (pageValue.equals(">=")) {
            operator = OptionsEnum.OPITON_GREATER_EQUAL;
        } else if (pageValue.equals("<")) {
            operator = OptionsEnum.OPTION_LESS;
        } else if (pageValue.equals("<=")) {
            operator = OptionsEnum.OPTION_LESS_EQUAL;
        } else if (pageValue.equals("contain")) {
            operator = OptionsEnum.OPTION_CONTAIN;
        } else if (pageValue.equals("notContain")) {
            operator = OptionsEnum.OPTION_NOT_CONTAIN;
        } else if (pageValue.equals("regexp")) {
            operator = OptionsEnum.OPTION_EXPRESS;
        }
        return operator;
    }

    /**
     * 向研发中心同步xml
     * @param ruleName
     * @param regXml
     * @param url
     * @return
     * @throws Exception
     */
    public static boolean ruleXmlSync(String ruleName, String regXml, String url, String operateType)
            throws Exception {
        boolean isOk = false;

        // 向研发中心发送xml
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap = new HashMap<String, String>();
        paramMap.put("SERVER_URL", url);
        paramMap.put("type", "ruleSync");
        paramMap.put("syncType", operateType); // “1”表示新增规则、“2”表示修改规则、“3”表示删除规则
        paramMap.put("ruleName", ruleName);
        SenderManager sender = new SenderManager();
        ResponseObject response = sender.send(regXml, paramMap);
        String status = response.getStatus();
        if (status.equals("0")) {
            logger.info("同步规则信息成功");
            isOk = true;
        } else {
            logger.info("同步规则信息失败 " + response.getError() + " " + response.getValue("message"));
        }
        return isOk;
    }

    public static void main(String[] args) {
        String ruleName = "测试xml";
        try {
            Rule firstRule = new RuleAnd(ruleName);
            firstRule.setAction(actionAnd);

            Rule secondRule = new RuleOr();
            secondRule.setAction(actionOr);

            Condition conditiona = new StringCondition("title", "title", "qwer",
                    OptionsEnum.OPITON_EQUAL);
            Condition conditionb = new ExpressionCondition("text", "text", "包含",
                    OptionsEnum.OPTION_CONTAIN);
            Condition conditionc = new IntegerCondition("lid", "lid", "2",
                    OptionsEnum.OPITON_GREATER);
            secondRule.addCondition(conditiona);
            secondRule.addCondition(conditionb);
            secondRule.addCondition(conditionc);

            Rule secondRule1 = new RuleAnd();
            secondRule1.setAction(actionAnd);

            Condition condition1 = new IntegerCondition("lid", "lid", "2",
                    OptionsEnum.OPITON_GREATER_EQUAL);
            Condition condition2 = new ExpressionCondition("text", "text", "内容",
                    OptionsEnum.OPTION_CONTAIN);
            Condition condition3 = new StringCondition("eventTime", "eventTime",
                    "2013-05-19 23:34:34", OptionsEnum.OPITON_EQUAL);

            secondRule1.addCondition(condition1);
            secondRule1.addCondition(condition2);
            secondRule1.addCondition(condition3);

            firstRule.addRule(secondRule);
            firstRule.addRule(secondRule1);

            RuleInstance instance = RuleFactory.getRuleInstance("E://temp");
            // 添加到规则队列
            instance.addRule(firstRule);
            // 获取规则XML
            String xml = instance.getRuleXML(ruleName);
            System.out.println("xml:" + xml);

            instance.delRule(ruleName);

            firstRule = new RuleAnd(ruleName);
            firstRule.setAction(actionAnd);

            secondRule = new RuleAnd("secondLevel");
            secondRule.setAction(actionAnd);

            condition1 = new StringCondition("text", "text", "5", OptionsEnum.OPITON_EQUAL);

            condition2 = new ExpressionCondition("title", "title", "baohan",
                    OptionsEnum.OPTION_CONTAIN);

            secondRule.addCondition(condition1);
            secondRule.addCondition(condition2);

            firstRule.addRule(secondRule);

            instance.addRule(firstRule);
            xml = instance.getRuleXML(ruleName);
            System.out.println("xml:" + xml);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
