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


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.zone.view.dao.ZoneRoomDomain;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 机房修改
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class ZoneResourceEditAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ZoneResourceEditAction.class);

    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "zoneRoom";

    /**
     * 实体.
     */
    private ZoneRoomDomain room = new ZoneRoomDomain();

    /**
     * 机房ID.
     */
    private String zoneId;

    /**
     * 站点信息集合.
     */
    private List<ZoneRoomDomain> siteList = new ArrayList<ZoneRoomDomain>();

    /**
     * 角色类型.
     */
    private String roleType;

    /**
     * 编辑前操作(查询) 2011-12-23 下午03:47:00
     * @return action映射名
     * @throws Exception
     */
    public String beforeEdit() throws Exception {
        logger.info(getText("log.resource.title") + getText("log.beforeEdit.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findSiteInfo", user);
            roleType = user.getRoleType();// 角色类型:1-中心管理员,2-站点管理
            room = (ZoneRoomDomain) ibatisDAO.getSingleRecord(IBATIC_NAMESPACE + ".detail", zoneId);
            logger.info(getText("log.resource.title") + getText("log.resource.beforeedit.success"));
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("log.beforeEdit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
        }
        logger.info(getText("log.resource.title") + getText("log.beforeEdit.end"));
        return "edit";
    }

    /**
     * @return action映射名
     */
    public String edit() {
        logger.info(getText("log.resource.title") + getText("log.edit.begin"));
        String opParam[] = { getText("resource.zone.roomName") + ": " + room.getZoneName() };
        try {
            ibatisDAO.updateData(IBATIC_NAMESPACE+".update", room);
            msg = getText("common.message.editSuccess");
            logger.info(getText("log.resource.title") + getText("log.edit.end"));
            operationInfo = getText("oplog.edit.success", opParam);
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("log.edit.error"), e);
            errorMsg = getText("common.message.editError") + getText("common.message.systemError");
            operationInfo = getText("oplog.edit.error", opParam)
                    + getText("common.message.systemError");
            return "editerror";
        }
        logger.info(getText("log.resource.title") + getText("log.edit.end"));
        return "editsuccess";
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

}
