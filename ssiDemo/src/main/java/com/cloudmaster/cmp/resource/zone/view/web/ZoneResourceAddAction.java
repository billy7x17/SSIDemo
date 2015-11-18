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
package com.cloudmaster.cmp.resource.zone.view.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.zone.view.dao.ZoneRoomDomain;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机柜添加.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-7-18
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class ZoneResourceAddAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ZoneResourceAddAction.class);

    /**
     * 站点角色值.
     */
    private static final String ROLETYPE_SITE = "2";

    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "zoneRoom";

    /**
     * 错误信息.
     */
    private String errorMsg;

    /**
     * 站点名称.
     */
    private String siteName;

    /**
     * 站点ID.
     */
    private String siteId;

    /**
     * 角色类型.
     */
    private String roleType;

    /**
     * 机房名称.
     */
    private String zoneName;

    /**
     * 站点信息集合.
     */
    private List<ZoneRoomDomain> siteList = new ArrayList<ZoneRoomDomain>();

    /**
     * 实体.
     */
    private ZoneRoomDomain room = new ZoneRoomDomain();

    /**
     * 机房ID.
     */
    private String zoneId;
    
    /**
     * 添加前页面跳转.
     * @return
     */
    public String beforeAdd() {
        logger.info(getText("log.resource.title") + getText("log.beforeAdd.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findSiteInfo", user);
            roleType = user.getRoleType();// 角色类型:1-中心管理员,2-站点管理
            if (ROLETYPE_SITE.equals(roleType)) {// 站点角色
                siteName = siteList.get(0).getSiteName();
                siteId = String.valueOf(user.getSiteId());
            }
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("resource.zone.browse.siteInfo.error"), e);
        }

        logger.info(getText("log.resource.title") + getText("log.beforeAdd.end"));
        return "add";
    }

    /**
     * @return action映射名
     * @throws Exception
     */
    public String add() {
        logger.info(getText("log.resource.title") + getText("log.add.begin"));
        String opParam[] = { getText("resource.zone.roomName") + ": " + room.getZoneName() };
        try {
            ibatisDAO.insertData(IBATIC_NAMESPACE + ".insert", room);
            msg = getText("common.message.addSuccess");
            logger.info(getText("log.resource.title") + getText("log.add.end"));
            operationInfo = getText("oplog.add.success", opParam);
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("log.add.error"), e);
            errorMsg = getText("common.message.addError") + getText("common.message.systemError");
            operationInfo = getText("oplog.add.error", opParam)
                    + getText("common.message.systemError");
            return "adderror";

        }
        return "addsuccess";
    }

    /**
     * 验证同一站点机房名称是否相同.
     */
    public void validZoneName() {
        try {
            room.setSiteId(siteId);
            room.setZoneName(zoneName);
            if(!StringUtils.isEmpty(zoneId)){
                room.setZoneId(zoneId);
            }
            int result = (Integer) ibatisDAO.getSingleRecord(IBATIC_NAMESPACE + ".validZoneName",
                    room);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            if (result != 0) {
                pw.write(getText("resource.zone.rename"));
            } else {
                pw.write("");
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.error(getText("resource.zone.list.error"), e);
        }
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
     * @return the siteList
     */
    public List<ZoneRoomDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList the siteList to set
     */
    public void setSiteList(List<ZoneRoomDomain> siteList) {
        this.siteList = siteList;
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

    /**
     * @return the room
     */
    public ZoneRoomDomain getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(ZoneRoomDomain room) {
        this.room = room;
    }

    /**
     * @return the zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * @param zoneName the zoneName to set
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return the zoneId
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

}
