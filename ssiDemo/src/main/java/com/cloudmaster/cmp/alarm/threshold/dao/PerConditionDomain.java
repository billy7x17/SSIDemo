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
package com.cloudmaster.cmp.alarm.threshold.dao;

/**
 * 告警比较符号信息Bean
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PerConditionDomain {

    private String perId;

    private String perValue;

    public void setPerId(String perId) {
        this.perId = perId;
    }

    public String getPerId() {
        return perId;
    }

    public void setPerValue(String perValue) {
        this.perValue = perValue;
    }

    public String getPerValue() {
        return perValue;
    }
}
