/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * @(#)BaseDomain.java 2013-8-22
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.web;

/**
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version $Revision 1.1 $ 2013-8-22 下午04:26:31
 * domain的父类，继承该类后，查询列表时sql中使用Limit限制获取的记录数量
 */
public class BaseDomain {
    /**
     * 第一个返回记录行的偏移量
     */
    private int offset;

    /**
     * 返回记录行的最大数目
     */
    private int rows;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    
}
