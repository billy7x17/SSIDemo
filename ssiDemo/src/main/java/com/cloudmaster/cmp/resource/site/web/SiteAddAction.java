package com.cloudmaster.cmp.resource.site.web;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 站点添加.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-29
 */
@SuppressWarnings("deprecation")
public class SiteAddAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * 标识.
     */
    private static final long serialVersionUID = 1185746802979514764L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SiteAddAction.class);
    
    /**
     * 命名空间.
     */
    private static final String NAME_SPACE = "SiteInfo";
    
    /**
     * 线路.
     */
    private Map<String, String> lineMap = new TreeMap<String, String>();
    
    /**
     * 站点类型.
     */
    private Map<String, String> typeMap = new TreeMap<String, String>();
    
    /**
     * 站点.
     */
    private SiteInfo site = new SiteInfo();
    
    /**
     * 站点名称.
     */
    private String siteName;
    
    /**
     * 地铁线路.
     */
    private String lineNum;
    
    /**
     * 站点Id.
     */
    private String siteId;
    
    public String addBefore(){
        logger.info(getText("resource.site.title")+getText("log.beforeAdd.begin"));
        setSiteLineMap();//线路
        setSiteTypeMap();//类型
        logger.info(getText("resource.site.title")+getText("log.beforeAdd.end"));
        return SUCCESS;
    }

    /**
     * 添加站点.
     * @return
     */
    public String addSite() {
        logger.info(getText("resource.site.title") + getText("log.add.begin"));
        String opParam[] = { getText("site.table.siteName") + ": " + site.getSiteName() };
        try {
            ibatisDAO.insertData(NAME_SPACE + ".insertSite", site);
            
            msg = getText("common.message.addSuccess");
            operationInfo = getText("oplog.add.success", opParam);
            logger.info(getText("resource.site.title") + getText("log.add.end"));
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("common.message.addError"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
            return ERROR;
        }
        return SUCCESS;
    }
    
    /**
     * 验证站点名称是否重复
     */
    public void validSameName(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            site.setSiteName(siteName);
            site.setLineNum(lineNum);
            if(!StringUtils.isEmpty(siteId)){
                site.setSiteId(Integer.valueOf(siteId));
            }
            int count = (Integer)ibatisDAO.getSingleRecord(NAME_SPACE+".findSiteName", site);
            if(count>0){
                pw.write(getText("resource.site.siteName.same"));
            }
            pw.flush();
            pw.close();
            logger.info(getText("resource.site.siteName.same.error"));
        } catch (Exception e) {
            logger.error(getText("resource.site.siteName.same.error"), e);
        }
    }
    
    /**
     * 设置地铁线路.
     */
    private void setSiteLineMap(){
        //lineMap.put(getText("resource.site.line1"), getText("resource.site.lineName1"));
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
