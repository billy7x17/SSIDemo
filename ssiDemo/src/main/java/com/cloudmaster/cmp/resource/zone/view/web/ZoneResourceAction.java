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

import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.resource.zone.view.dao.ZoneRoomDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 分区列表浏览
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class ZoneResourceAction extends PageAction implements IAuthIdGetter, IOperationLog {
    /**
     * 标识.
     */
    private static final long serialVersionUID = 1L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(ZoneResourceAction.class);

    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "zoneRoom";
    
    /**
     * 站点角色值.
     */
    private static final String ROLETYPE_SITE = "2";

    /**
     * 日志内容.
     */
    private String operationInfo;

    /**
     * 名称.
     */
    private String functionName;

    /**
     * 操作类型.
     */
    private String opType;

    /**
     * 机房.
     */
    private ZoneRoomDomain room = new ZoneRoomDomain();

    /**
     * 站点信息.
     */
    private List<ZoneRoomDomain> siteList = new ArrayList<ZoneRoomDomain>();
    
    /**
     * 站点角色类型.
     */
    private String roleType;

    /**
     * 仅跳转
     * @return action映射名
     */

    public String init() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
            roleType = user.getRoleType();
            siteList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findSiteInfo", user);
        } catch (Exception e) {
            logger.info(getText("resource.zone.list.error") + getText("resource.zone.browse.siteInfo.error"), e);
        }
        return SUCCESS;
    }

    /**
     * 初始化页面列表
     */
    public String list() {
        logger.info(getText("log.resource.title") + getText("log.list.begin"));
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8");
            UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
           
            if(ROLETYPE_SITE.equals(user.getRoleType()) && StringUtils.isEmpty(room.getSiteId())){//给站点角色添加站点值.
                 room.setSiteId(String.valueOf(user.getSiteId()));
            }
            
            room.setSortName(request.getParameter("sortname"));
            room.setSortOrder(request.getParameter("sortorder"));
            room.setQtype(request.getParameter("qtype"));
            room.setQuery(request.getParameter("query"));
            
            String rp = request.getParameter("rp");
            if (null != rp) {
                setPageSize(Integer.parseInt(rp));
            }
            
            List<ZoneRoomDomain> roomList = getPage(IBATIC_NAMESPACE+".getCount", IBATIC_NAMESPACE+".getList", room);
           
            FlexGridJSONData fgjd = new FlexGridJSONData();
            fgjd.setPage(getPage());
            fgjd.setTotal(getTotalCount());
            for(ZoneRoomDomain zoneRoom :roomList){
                fgjd.setRowId(zoneRoom.getZoneId());//设置行标识
                fgjd.addColdata(zoneRoom.getZoneName());
                fgjd.addColdata(zoneRoom.getSiteName());
                fgjd.addColdata(zoneRoom.getPrincipal());
                fgjd.addColdata(zoneRoom.getAddress());
                fgjd.addColdata(zoneRoom.getDescription());
                fgjd.addColdata(zoneRoom.getZoneId());
            }
            fgjd.commitData();
            PrintWriter pw = response.getWriter();
            pw.write(fgjd.toString());
            pw.flush();
            pw.close();
            operationInfo = getText("oplog.list.success");
            logger.info(getText("log.resource.title") + getText("log.list.end"));
        } catch (Exception e) {
            logger.info(getText("resource.zone.list.error") + getText("log.list.error"), e);
            operationInfo = getText("oplog.list.error") + getText("common.message.systemError");
        }
      
        return null;
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
