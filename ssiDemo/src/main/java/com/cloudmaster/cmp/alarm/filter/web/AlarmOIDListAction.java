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
package com.cloudmaster.cmp.alarm.filter.web;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.filter.dao.AlarmFilterDomain;
import com.cloudmaster.cmp.util.FlexGridJSONData;
import com.cloudmaster.cmp.web.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 过滤管理
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class AlarmOIDListAction extends PageAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2235626059456084000L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(AlarmOIDListAction.class);

    /**
     * 跳转页面
     */
    private String forward = "SUCCESS";

    /**
     * bean对象
     */
    private AlarmFilterDomain domain = new AlarmFilterDomain();

    /**
     * 列表
     */
    private List<AlarmFilterDomain> OIDList;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmFilter";

    /**
     * 列表页面
     * @return
     */
    public String list() {
        logger.info(getText("function.title") + getText("log.list.begin"));
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            // 增加排序操作
            domain.setSortName(request.getParameter("sortname"));
            domain.setSortOrder(request.getParameter("sortorder"));
            // 此操作必须在调用getpage()方法之前
            String rp = request.getParameter("rp");
            setPageSize(Integer.parseInt(rp));
            OIDList = getPage(IBATIS_NAMESPACE + ".rosterCount", IBATIS_NAMESPACE + ".rosterList",
                    domain);
            FlexGridJSONData fgjd = new FlexGridJSONData(); // json数据组装器
            fgjd.setPage(getPage()); // 设置 页码
            fgjd.setTotal(getTotalCount()); // 设置总页数
            for (AlarmFilterDomain bean : OIDList) {
                fgjd.setRowId(bean.getRosterId()); // 设置行标识
                fgjd.addColdata(bean.getManufactureId());
                if (bean.getRosterType().equals("0")) {
                    fgjd.addColdata(bean.getThresholdName());
                } else {
                    fgjd.addColdata(bean.getAccessName());
                }
                fgjd.addColdata(bean.getAlarmGrade());
                fgjd.addColdata(bean.getRosterType());
                fgjd.addColdata(bean.getModifyTime());
            }
            fgjd.commitData(); // 提交数据

            response.setCharacterEncoding("utf-8");
            PrintWriter pw;
            pw = response.getWriter();
            pw.write(fgjd.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();
            logger.info(getText("function.title") + getText("log.list.end"));
        } catch (Exception e) {
            logger.info(getText("function.title") + getText("log.list.error"), e);
        }
        return forward;
    }

    public String base() {
        return "base";
    }

    public AlarmFilterDomain getDomain() {
        return domain;
    }

    public void setDomain(AlarmFilterDomain domain) {
        this.domain = domain;
    }

    public List<AlarmFilterDomain> getOIDList() {
        return OIDList;
    }

    public void setOIDList(List<AlarmFilterDomain> list) {
        OIDList = list;
    }

}
