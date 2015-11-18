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
package com.cloudmaster.cmp.web.page;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

/**
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version 1.0.0 18 Mar 2012
 */
public class Page {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String PAGE_PREFIX = "page=";

    public static final String PAGE_SIZE_PREFIX = "pageSize=";

    private int curPage = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int totalCount;

    private int cNum = 5;

    private String url = "";

    private String param = "";

    private String pageTemplate = "page.ftl";

    public Page() {
        this(1, 0, DEFAULT_PAGE_SIZE, "");
    }

    public Page(int curPage, int totalSize, String queryString) {
        this(curPage, totalSize, DEFAULT_PAGE_SIZE, queryString);
    }

    public Page(int curPage, int totalSize, int pageSize, String queryString) {
        this.pageSize = pageSize;
        this.totalCount = totalSize;
        this.curPage = setCurrentPage(curPage);
        parseQueryString(queryString);
    }

    public Page(int curPage, int totalSize, int pageSize, String url, String param) {
        this.pageSize = pageSize;
        this.totalCount = totalSize;
        this.curPage = setCurrentPage(curPage);
        this.url = url;
        this.param = parseParam(param, PAGE_PREFIX);
    }

    private int setCurrentPage(int currPage) {
        if (currPage > this.getTotalPageCount()) {
            return this.getTotalPageCount();
        } else {
            return currPage;
        }
    }

    public String parseParam(String parameter, String filter) {
        StringBuffer sb = new StringBuffer();
        if (null != parameter) {
            String[] params = parameter.split("&");
            if (null != params) {
                for (int i = 0, len = params.length; i < len; i++) {
                    if (params[i].indexOf(filter) != -1) {
                        continue;
                    }
                    if (params[i].length() > 0) {
                        sb.append(params[i]);
                        if (i < len - 1) {
                            sb.append("&");
                        }
                    }
                }
            }
        }
        String paramTmp = sb.toString();
        if (paramTmp.endsWith("&")) {
            paramTmp = paramTmp.substring(0, paramTmp.length() - 1);
        }

        return paramTmp;
    }

    private void parseQueryString(String queryString) {
        if (queryString.indexOf("?") != -1) {
            String[] tmps = queryString.split("\\?");
            url = tmps[0];
            param = tmps[1];
        } else {
            url = queryString;
        }
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public boolean hasNextPage() {
        return this.getCurPage() < this.getTotalPageCount();
    }

    public boolean hasPreviousPage() {
        return this.getCurPage() > 1;
    }

    public int getStartOfPage() {
        return getStartOfPage(this.curPage, this.pageSize);
    }
    
    public int getNetStartOfPage(int count) {
        return getStartOfPage(this.curPage, this.pageSize*count);
    }

    public int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    public int getStartOfPage(int pageNo, int pgSize) {
        return (pageNo - 1) * pgSize;
    }       

    /**
     * @return Returns the cNum.
     */
    public int getCNum() {
        return cNum;
    }

    /**
     * @param num The cNum to set.
     */
    public void setCNum(int num) {
        cNum = num;
    }

    /**
     * @return Returns the pageTemplate.
     */
    public String getPageTemplate() {
        return pageTemplate;
    }

    /**
     * @param pageTemplate The pageTemplate to set.
     */
    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    /**
     * @return Returns the curPage.
     */
    public int getCurPage() {
        return curPage;
    }

    /**
     * @param curPage The curPage to set.
     */
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String sessionId = session.getId();

        return url + ";jsessionid=" + sessionId;
    }

    /**
     * @return Returns the param.
     */
    public String getParam() {
        return param;
    }

    public void setQueryString(String queryString) {
        parseQueryString(queryString);
    }

    /**
     * @param pageSize The pageSize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param totalCount The totalCount to set.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
