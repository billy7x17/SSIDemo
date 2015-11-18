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
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.zone.view.dao.ZoneRoomDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 分区删除
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ZoneResourceDelAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ZoneResourceDelAction.class);

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
     * 删除机房
     * @return action映射名
     */
    @SuppressWarnings("deprecation")
    public String delete() {
        logger.info(getText("log.resource.title") + getText("log.delete.begin"));
        String opParam[] = { getText("resource.zone.roomName") + ": " + room.getZoneName() };
        try {

            int count = (Integer) ibatisDAO.getSingleRecord(IBATIC_NAMESPACE
                    + ".findClusterByZoneId", zoneId);// 查看 机房里是否存在机柜.
            if (count > 0) {
                errorMsg = getText("log.zonebycluster.delete");
                operationInfo = getText("oplog.delete.error", opParam)
                        + getText("log.zonebycluster.delete");
            } else {
                ibatisDAO.deleteData(IBATIC_NAMESPACE + ".delete", zoneId);
                
                msg = getText("common.message.delSuccess");
                logger.info(getText("log.resource.title") + getText("log.delete.end"));
                operationInfo = getText("oplog.delete.success", opParam);
            }
        } catch (Exception e) {
            logger.info(getText("log.resource.title") + getText("log.delete.error"), e);
            errorMsg = getText("common.message.delError") + getText("common.message.systemError");
            operationInfo = getText("oplog.delete.error", opParam)
                    + getText("common.message.systemError");
        }
        logger.info(getText("log.resource.title") + getText("log.delete.end"));
        return "delete";
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
     * @return Returns the room.
     */
    public ZoneRoomDomain getRoom() {
        return room;
    }

    /**
     * @param room The room to set.
     */
    public void setRoom(ZoneRoomDomain room) {
        this.room = room;
    }

}
