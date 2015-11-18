package com.cloudmaster.cmp.resource.site.dao;

/**
 * <b>Application describing: 地铁站点信息bean</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-7-3 上午9:32:58
 */
public class SiteInfo {

    /**
     * 站点ID 自动递增
     */
    private Integer siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 所属线路 ：1 - 1号线 , 2 - 2号线
     */
    private String lineNum;

    /**
     * 站点类型(监控地点类型): 1 - 控制中心, 2 - 地铁站, 3 - 车辆段 ,4 - 停车场
     */
    private String siteType;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序名称
     */
    private String sortName;

    /**
     * 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;
    
    /**
     * flexigrid查询字段名
     */
    private String qtype;

    /**
     * flexigrid查询字段值
     */
    private String query;
    
    /**
     * @return the siteId
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the lineNum
     */
    public String getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum the lineNum to set
     */
    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return the siteType
     */
    public String getSiteType() {
        return siteType;
    }

    /**
     * @param siteType the siteType to set
     */
    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the sortName
     */
    public String getSortName() {
        return sortName;
    }

    /**
     * @param sortName the sortName to set
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the qtype
     */
    public String getQtype() {
        return qtype;
    }

    /**
     * @param qtype the qtype to set
     */
    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

}
