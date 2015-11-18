package com.cloudmaster.cmp.resource.site.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 站点列表展示.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-28
 */
@SuppressWarnings({"deprecation","unchecked"})
public class SiteListAction extends PageAction implements IAuthIdGetter,IOperationLog{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5001910508757677902L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SiteListAction.class);
    
    /**
     * 命名空间.
     */
    private static final String NAME_SPACE = "SiteInfo";
    
    /**
     * 站点角色.
     */
    private static final String ROLETYPE_SITE = "2";
    
    /**
     * 站点实体.
     */
    private SiteInfo site = new SiteInfo();
    
    /**
     * 站点类型.
     */
    private Map<String, String> siteTypeMap = new TreeMap<String, String>();
    
    /**
     * 日志内容.
     */
    private String operationInfo ;
    
    /**
     * 功能名称.
     */
    private String functionName;
    
    /**
     * 操作类型.
     */
    private String opType;
    
    /**
     * 角色类型.
     */
    private String roleType;
    
    /**
     * 页面跳转.
     * @return
     */
    public String init(){
        HttpServletRequest request = ServletActionContext.getRequest();
        UserInfo user = (UserInfo)request.getSession().getAttribute("userInfo");
        roleType = user.getRoleType();
        siteTypeMap.put(getText("resource.site.type1"), getText("resource.site.typeName1"));
        siteTypeMap.put(getText("resource.site.type2"), getText("resource.site.typeName2"));
        siteTypeMap.put(getText("resource.site.type3"), getText("resource.site.typeName3"));
        siteTypeMap.put(getText("resource.site.type4"), getText("resource.site.typeName4"));
        return SUCCESS;
    }

    

    public void list(){
        logger.info(getText("resource.site.title")+getText("log.list.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            UserInfo user = (UserInfo)request.getSession().getAttribute("userInfo");
            if(ROLETYPE_SITE.equals(user.getRoleType())){
                site.setSiteId(user.getSiteId());
            }
            
            site.setSortOrder(request.getParameter("sortorder"));
            site.setSortName(request.getParameter("sortname"));
            site.setQtype(request.getParameter("qtype"));
            site.setQuery(request.getParameter("query"));
            
            String rp = request.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            
            List<SiteInfo> list = getPage(NAME_SPACE+".getCount",NAME_SPACE+ ".getList", site);
            FlexGridJSONData fgjd = new FlexGridJSONData();
            fgjd.setPage(getPage());
            fgjd.setTotal(getTotalCount());
            
            for(SiteInfo site:list){
                fgjd.setRowId(String.valueOf(site.getSiteId()));
                fgjd.addColdata(site.getSiteName());
                fgjd.addColdata(site.getLineNum());
                fgjd.addColdata(site.getSiteType());
                fgjd.addColdata(site.getDescription());
                fgjd.addColdata(String.valueOf(site.getSiteId()));
            }
            fgjd.commitData();
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString());
            pw.flush();
            pw.close();
            
            operationInfo = getText("oplog.list.success");
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
    }
    
    @Override
    public String getOperationInfo() {
        return operationInfo;
    }

    @Override
    public String getOperationFunction() {
        return functionName;
    }

    @Override
    public String getOpType() {
        return opType;
    }

    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @param operationInfo the operationInfo to set
     */
    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    /**
     * @param opType the opType to set
     */
    public void setOpType(String opType) {
        this.opType = opType;
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
     * @return the siteTypeMap
     */
    public Map<String, String> getSiteTypeMap() {
        return siteTypeMap;
    }

    /**
     * @param siteTypeMap the siteTypeMap to set
     */
    public void setSiteTypeMap(Map<String, String> siteTypeMap) {
        this.siteTypeMap = siteTypeMap;
    }



    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }



    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
