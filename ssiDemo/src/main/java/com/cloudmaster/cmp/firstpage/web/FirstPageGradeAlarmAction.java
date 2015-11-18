package com.cloudmaster.cmp.firstpage.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudmaster.cmp.alarm.view.dao.AlarmViewDomain;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.IOperationLog;
import com.cloudmaster.cmp.web.PageAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * <b>Application describing: 实时告警列表按告警级别分类图标Action</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-7-15 下午2:34:22
 */
public class FirstPageGradeAlarmAction extends PageAction implements IOperationLog {

    private static final long serialVersionUID = 1395303978558443783L;

    private static LogService logger = LogService.getLogger(FirstPageGradeAlarmAction.class);

    /**
     * 站点ID；
     */
    private String siteId;

    /**
     * 颜色数组 分别对应告警级别0,1,2,3
     */
    private final String[] colors = { "#F83D3D", "#FF7335", "#FFB500", "#DDD32C" };

    @SuppressWarnings("unchecked")
    public String execute() {

        List<AlarmViewDomain> list = null;

        AlarmViewDomain domain = new AlarmViewDomain();

        domain.setNmsDB(nmsDBName);

        domain.setQueryValue(siteId);

        try {
            list = ibatisDAO.getData("resourceReport.getCountsByGrade", domain);
        } catch (SQLException e) {
            logger.debug("首页实时告警查询异常", e);
            e.printStackTrace();
        }
        Map<String, String> ob = new HashMap<String, String>();
        for (Iterator<?> it = list.iterator(); it.hasNext();) {
            AlarmViewDomain temp = (AlarmViewDomain) it.next();
            ob.put(temp.getAlarmGrade(), temp.getCount());
        }

        JSONArray arr = new JSONArray();

        for (Integer i = 0; i < 4; i++) {
            JSONObject jsob = new JSONObject();
            if (null != ob.get(i.toString())) {
                jsob.put("y", Integer.valueOf(ob.get(i.toString())));
            } else {
                jsob.put("y", 0);
            }
            jsob.put("color", colors[i]);
            arr.add(jsob);
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter pw = null;

        response.setCharacterEncoding("utf-8");
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(arr.toString()); // 转换json数据格式 传递前台
        pw.flush();

        return null;
    }

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
