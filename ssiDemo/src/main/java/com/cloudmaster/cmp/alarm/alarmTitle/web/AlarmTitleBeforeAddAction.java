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
package com.cloudmaster.cmp.alarm.alarmTitle.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 告警标题管理
 * @author <a href="mailto:li-chp@neusoft.com">li-chp</a>
 */
public class AlarmTitleBeforeAddAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2086189858021404791L;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "alarmTitle";

    /**
     * 页面传来的标题ID
     */
    private String tcId;

    private JSONArray jsonarray;

    private JSONObject jsonobject;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(AlarmTitleBeforeAddAction.class);

    /**
     * 添加前准备
     * @return
     */
    public String beforeAdd() {
        return SUCCESS;
    }

    /**
     * ajax校验告警标题Id唯一
     */
    public void validateTcId() {
        String result = "";
        try {
            int count = ibatisDAO.getCount(IBATIS_NAMESPACE + ".existCount", tcId);
            if (count > 0) {
                result = "exist";
            }
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("result", result);
            JSONObject jsonObj = JSONObject.fromObject(resultMap);
            String jsonStr = jsonObj.toString();
            logger.debug("jsonStr" + jsonStr);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.info(getText("function.title"), e);
        }
    }


    public JSONArray getJsonarray() {
        return jsonarray;
    }

    public void setJsonarray(JSONArray jsonarray) {
        this.jsonarray = jsonarray;
    }

    public JSONObject getJsonobject() {
        return jsonobject;
    }

    public void setJsonobject(JSONObject jsonobject) {
        this.jsonobject = jsonobject;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

}
