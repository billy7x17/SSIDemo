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
package com.cloudmaster.cmp.container.ibatis;

/*
 * @(#)BatchVO.java
 *
 * Short Message Internet Access Solution Management System
 * Copyright 2004 Neusoft MID. All Rights Reserved.
 * @author mid<mid@neusoft.com>
 * @version 1.0.0 18 Mar 2012
 */


/**
 * <p>
 * 批处理VO列表：用于事务控制多次的修改。
 * </p>
 * @author <a href="mailto:sunxiwei@neusoft.com">XiWei.SUN</a>
 * @version 1.0.0 18 Mar 2012
 */

public class BatchVO {
    // 操作类型:ADD,DEL,MOD
    private String operate;

    // 实际操作的DAO的传入VO
    private Object object;

    // 调用ibatis的sql语句ID
    private String string;

    /**
     * @return Returns the operate.
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param operate The operate to set.
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * @param operate
     * @param object
     * @param string
     */
    public BatchVO(String operate, String string, Object object) {
        super();
        this.operate = operate;
        this.object = object;
        this.string = string;
    }

    /**
     * @return Returns the object.
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object The object to set.
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return Returns the string.
     */
    public String getString() {
        return string;
    }

    /**
     * @param string The string to set.
     */
    public void setString(String string) {
        this.string = string;
    }
}
