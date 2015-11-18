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
package com.cloudmaster.cmp.performance.physics.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * 功耗信息属性的JavaBean
 * @author WHB
 * @version 1.0.0 18 Mar 2012
 */
public class GHInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 9139647970582341429L;
    /**
     * 当前功耗
     */
    private String consumption;
    /**
     * 进出口温度
     */
    private String temperature;
    /**
     * 时间
     */
    private Date perTime;
    /**
     * 标志
     */
    private String flag;
    public String getConsumption() {
        return consumption;
    }
    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public Date getPerTime() {
        return perTime;
    }
    public void setPerTime(Date perTime) {
        this.perTime = perTime;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
