package com.cloudmaster.cmp.resource.site.web;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除站点
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-29
 */
public class SiteDeteleAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 标识.
     */
    private static final long serialVersionUID = 2869320720559800816L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SiteDeteleAction.class);

    /**
     * 命名空间.
     */
    private static final String NAME_SPACE = "SiteInfo";

    /**
     * 站点.
     */
    private SiteInfo site = new SiteInfo();

    /**
     * 站点Id.
     */
    private String siteId;

    /**
     * 页面跳转.
     * @return
     */
    @SuppressWarnings("deprecation")
    public String deleteSite() {
        logger.info(getText("resource.site.title") + getText("log.delete.begin"));
        String opParam[] = { getText("site.table.siteName") + ": " + site.getSiteName() };
        try {
            int count = (Integer) ibatisDAO.getSingleRecord(NAME_SPACE + ".findZoneBySiteId",
                    siteId);
            int userCount = (Integer) ibatisDAO.getSingleRecord(NAME_SPACE + ".findUserBySiteId",
                    siteId);
            if (count > 0) {
                errorMsg = getText("resource.site.deleteSite.validZone");
                operationInfo = getText("oplog.delete.error", opParam)
                        + getText("common.message.systemError");
                return ERROR;
            }
            if (userCount > 0) {
                errorMsg = getText("resource.site.deleteSite.validUser");
                operationInfo = getText("oplog.delete.error", opParam)
                        + getText("common.message.systemError");
                return ERROR;
            }
            ibatisDAO.deleteData(NAME_SPACE + ".deleteSite", siteId);
            
            msg = getText("common.message.delSuccess");
            logger.info(getText("resource.site.title") + getText("log.delete.end"));
            operationInfo = getText("oplog.delete.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
            return ERROR;
        }
        return SUCCESS;
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

    /**
     * @return Returns the site.
     */
    public SiteInfo getSite() {
        return site;
    }

    /**
     * @param site The site to set.
     */
    public void setSite(SiteInfo site) {
        this.site = site;
    }

}
