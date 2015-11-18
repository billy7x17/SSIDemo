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
package com.cloudmaster.cmp.web;

import java.sql.SQLException;
import java.util.List;

import com.cloudmaster.cmp.web.page.Page;
import com.cloudmaster.cmp.web.page.PageHelper;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 所有要进行分页的Action的父类,提供基本的分页方法
 * @author <a href="mailto:liy_neu@neusoft.com">liyang</a>
 * @version 1.0.0 18 Mar 2012
 */
public abstract class PageAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PageAction.class);

    private int page = 1;

    private int pageSize = Page.DEFAULT_PAGE_SIZE;

    private int totalCount;

    private String url;

    private String param;

    private String pageBar;

    private String sortname;

    private String sortorder;

    private String qtype;

    private String query;
    
    private String rp;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getPageBar() {
        return pageBar;
    }

    public void setPageBar(String pageBar) {
        this.pageBar = pageBar;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    
    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    /**
     * 有条件查询的分页方法 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID 参数obj为查询条件
     */
    @SuppressWarnings("unchecked")
    protected final List getPage(String countStatement, String resultSetStatement, Object obj) {
        List list = null;
        try {
            totalCount = ibatisDAO.getCount(countStatement, obj);

            // 页面传上来页码数，查询数据库时数据被删除一部分，需要判断并将页码数改为实际值
            int totalPageCount;
            if (totalCount % getPageSize() == 0) {
                totalPageCount = totalCount / getPageSize();
            } else {
                totalPageCount = totalCount / getPageSize() + 1;
            }
            if (getPage() != 1 && getPage() > totalPageCount) {
                setPage(totalPageCount);
            }

            Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam());
            this.pageBar = PageHelper.getPageBar(pageObj);
            list = ibatisDAO.getObjectsByPage(resultSetStatement, obj, pageObj.getStartOfPage(),
                    getPageSize());
        } catch (SQLException e) {
            logger.error(null, "分页出错！" + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 有条件查询的分页方法 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID 参数obj为查询条件
     * 在sql中使用limit，限制获取的记录数量。展示大数据表列表信息时使用
     */
    @SuppressWarnings("unchecked")
    protected final List getPageUseLimit(String countStatement, String resultSetStatement,
            Object obj) {
        List list = null;
        try {
            totalCount = ibatisDAO.getCount(countStatement, obj);

            // 页面传上来页码数，查询数据库时数据被删除一部分，需要判断并将页码数改为实际值
            int totalPageCount;
            if (totalCount % getPageSize() == 0) {
                totalPageCount = totalCount / getPageSize();
            } else {
                totalPageCount = totalCount / getPageSize() + 1;
            }
            if (getPage() != 1 && getPage() > totalPageCount) {
                setPage(totalPageCount);
            }

            Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam());
            // this.pageBar = PageHelper.getPageBar(pageObj); //数据量大时内存溢出，flexgrid不用该分页框架
            if (obj instanceof BaseDomain) {
                int offset = pageObj.getStartOfPage();
                if (offset < 0) {
                    offset = 0;
                }
                ((BaseDomain) obj).setOffset(offset);
                ((BaseDomain) obj).setRows(getPageSize());
            }
            logger
                    .info("StartOfPage:" + pageObj.getStartOfPage() + ",getPageSize:"
                            + getPageSize());
            list = ibatisDAO.getData(resultSetStatement, obj);
        } catch (SQLException e) {
            logger.error(null, "分页出错！" + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 无条件查询的分页方法 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID
     */
    @SuppressWarnings("unchecked")
    protected final List getPage(String countStatement, String resultSetStatement) {
        List list = null;
        try {
            totalCount = ibatisDAO.getCount(countStatement, null);
            Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam());
            this.pageBar = PageHelper.getPageBar(pageObj);
            list = ibatisDAO.getObjectsByPage(resultSetStatement, null, pageObj.getStartOfPage(),
                    getPageSize());
        } catch (SQLException e) {
            logger.error(null, "分页出错！" + e.getMessage(), e);
        }
        return list;
    }


    /**
     * 交换机的分页查询方法
     */
    @SuppressWarnings("unchecked")
    protected final List getNetPage(String countStatement, String resultSetStatement, Object obj,
            int count) {
        List list = null;
        try {
            totalCount = ibatisDAO.getCount(countStatement, obj);
            Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam());
            this.pageBar = PageHelper.getPageBar(pageObj);
            list = ibatisDAO.getObjectsByPage(resultSetStatement, obj, pageObj
                    .getNetStartOfPage(count), getPageSize() * count);
        } catch (SQLException e) {
            logger.error(null, "分页出错！" + e.getMessage(), e);
        }
        return list;
    }
}
