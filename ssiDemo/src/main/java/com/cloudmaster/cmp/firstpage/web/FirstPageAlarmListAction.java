package com.cloudmaster.cmp.firstpage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

public class FirstPageAlarmListAction extends PageAction implements IOperationLog {

    private static final long serialVersionUID = 1395303978558443783L;

    private static LogService logger = LogService.getLogger(FirstPageAlarmListAction.class);

    /**
     * 为操作日志返回的功能名
     */
    protected String functionName = "";

    /**
     * 为操作日志返回的功能描述
     */
    protected String operationInfo = "";

    /**
     * 为操作日志返回的操作类型；
     */
    protected String opType = "";

    private String nmsDBName;

    /**
     * 站点ID；
     */
    private String siteId;

    @SuppressWarnings("unchecked")
    public String execute() {

        List<AlarmViewDomain> list = null;

        AlarmViewDomain domain = new AlarmViewDomain();

        domain.setNmsDB(nmsDBName);

        domain.setQueryType("siteId");

        domain.setQueryValue(siteId);

        try {
            list = ibatisDAO.getData("resourceReport.getTop10Alarms", domain);
        } catch (SQLException e) {
            logger.debug("首页实时告警查询异常", e);
            e.printStackTrace();
        }

        /* 设备分组改为汉字 */
        for (AlarmViewDomain i : list) {
            i.setDeviceTypeName(getText("device.group." + i.getDeviceTypeName()));
        }

        JSONArray jsonObject = JSONArray.fromObject(list);

        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter pw = null;

        response.setCharacterEncoding("utf-8");
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(jsonObject.toString()); // 转换json数据格式 传递前台
        pw.flush();
        return null;
    }

    @Override
    public String getOperationInfo() {
        return opType;
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
     * @return the nmsDBName
     */
    public String getNmsDBName() {
        return nmsDBName;
    }

    /**
     * @param nmsDBName the nmsDBName to set
     */
    public void setNmsDBName(String nmsDBName) {
        this.nmsDBName = nmsDBName;
    }

    /**
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
