package com.cloudmaster.cmp.resource.site.web;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 璇︾粏淇℃伅.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-31
 */
@SuppressWarnings("deprecation")
public class SiteDetailAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 鏍囪瘑.
     */
    private static final long serialVersionUID = -7362861135612984252L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SiteDetailAction.class);

    /**
     * 鍛藉悕绌洪棿.
     */
    private static final String NAME_SPACE = "SiteInfo";

    /**
     * 站点对象.
     */
    private SiteInfo site = new SiteInfo();

    /**
     * 站点Id.
     */
    private String siteId;

    /**
     * 查看详情
     * @return
     */
    public String detail() {
        logger.info(getText("resource.site.title") + getText("log.detail.begin"));
        String opParam[] = { getText("site.table.siteName") + ": " + site.getSiteName() };
        try {
            site = (SiteInfo) ibatisDAO.getSingleRecord(NAME_SPACE + ".detail", siteId);
            operationInfo = getText("oplog.detail.success", opParam);
        } catch (Exception e) {
            logger.info(getText("resource.site.title") + getText("log.detail.error"), e);
            errorMsg = getText("common.message.detailError")
                    + getText("common.message.systemError");
            operationInfo = getText("oplog.detail.error", opParam)
                    + getText("common.message.systemError");
        }
        logger.info(getText("resource.site.title") + getText("log.detail.end"));
        return "SUCCESS";
    }

    public String moreInfo() {
        return "SUCCESS";
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
