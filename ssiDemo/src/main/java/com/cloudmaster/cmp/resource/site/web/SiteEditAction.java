package com.cloudmaster.cmp.resource.site.web;

import java.util.Map;
import java.util.TreeMap;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 站点修改.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-29
 */
@SuppressWarnings("deprecation")
public class SiteEditAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * 标识.
     */
    private static final long serialVersionUID = 2287342180596493869L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SiteEditAction.class);
    
    /**
     * 命名空间.
     */
    private static final String NAME_SAPCE = "SiteInfo";
    
    /**
     * 线路集合.
     */
    private Map<String, String> lineMap = new TreeMap<String, String>();
    
    /**
     * 类型集合.
     */
    private Map<String, String> typeMap = new TreeMap<String, String>();
    
    /**
     * 实体.
     */
    private SiteInfo site = new SiteInfo();
    
    /**
     * 站点Id.
     */
    private String siteId;

    /**
     * 页面跳转.
     */
   
    public String editBefore(){
        logger.info(getText("resource.site.title")+getText("log.beforeEdit.begin"));
        try {
            site =(SiteInfo) ibatisDAO.getSingleRecord(NAME_SAPCE+".detail", siteId);
            setSiteLineMap();
            setSiteTypeMap();
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            return ERROR;
        }
        logger.info(getText("resource.site.title")+getText("log.beforeEdit.end"));
        return SUCCESS;
    }
    
    /**
     * 修改数据.
     * @return
     */
    public String edit(){
        logger.info(getText("resource.site.title")+getText("log.edit.begin"));
        String opParam[] = { getText("site.table.siteName") + ": " + site.getSiteName() };
        try {
            ibatisDAO.updateData(NAME_SAPCE+".updateSite", site);
            
            msg = getText("common.message.editSuccess");
            logger.info(getText("resource.site.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("log.edit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 设置地铁线路.
     */
    private void setSiteLineMap(){
       // lineMap.put(getText("resource.site.line1"), getText("resource.site.lineName1"));
        lineMap.put(getText("resource.site.line2"), getText("resource.site.lineName2"));
    }
    
    /**
     * 设置站点类型.
     */
    private void setSiteTypeMap(){
        typeMap.put(getText("resource.site.type1"), getText("resource.site.typeName1"));
        typeMap.put(getText("resource.site.type2"), getText("resource.site.typeName2"));
        typeMap.put(getText("resource.site.type3"), getText("resource.site.typeName3"));
        typeMap.put(getText("resource.site.type4"), getText("resource.site.typeName4"));
    }
    
    /**
     * @return the lineMap
     */
    public Map<String, String> getLineMap() {
        return lineMap;
    }

    /**
     * @param lineMap the lineMap to set
     */
    public void setLineMap(Map<String, String> lineMap) {
        this.lineMap = lineMap;
    }

    /**
     * @return the typeMap
     */
    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    /**
     * @param typeMap the typeMap to set
     */
    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }

    /**
     * @return the site
     */
    public SiteInfo getSite() {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(SiteInfo site) {
        this.site = site;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    
}
