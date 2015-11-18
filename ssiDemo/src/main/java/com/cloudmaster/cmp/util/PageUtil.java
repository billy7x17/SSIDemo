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
package com.cloudmaster.cmp.util;

/**
 * 分页工具类
 * @author <a href="mailto:zhao.chy@neusoft.com">zhao.chy </a>
 * @version 1.0.0 18 Mar 2012
 */
public class PageUtil {
    //总数量
    private int totalCount;

    //每页显示数量
    public static final int DEFAULT_PAGE_SIZE = 10;

    //每页数量
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 构造函数
     * @param totalCount 总数量
     * @param pageSize 每页显示数量
     */
    public PageUtil(int totalCount, int pageSize) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }

    /**
     * 获取开始条数
     * @param currPage 页码
     * @return 开始条数
     */
    public int getStartOfPage(int currPage) {
        return getStartOfPage(this.getPageNum(currPage), this.pageSize);
    }

    /**
     * 获取开始条数
     * @param pageNo 页码
     * @param pgSize 每页数量
     * @return 开始条数时
     */
    private int getStartOfPage(int pageNo, int pgSize) {
        return (pageNo - 1) * pgSize;
    }

    /**
     * 获取当前页码
     * @param currPage 当前页码
     * @return 当前页码
     */
    private int getPageNum(int currPage) {
        if (currPage > this.getTotalPageCount()) {
            return this.getTotalPageCount();
        } else {
            return currPage;
        }
    }

    /**
     * 获取总页数
     * @return 总页数
     */
    private int getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }
}
