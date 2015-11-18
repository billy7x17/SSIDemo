package com.cloudmaster.cmp.topology.assFunction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.topology.domain.TopoAlarmDomain;
import com.cloudmaster.cmp.web.BaseAction;

public class AlarmCount extends BaseAction {

    private static final long serialVersionUID = -5512156766079611255L;

    private final Logger logger = Logger.getLogger("AlarmCount".getClass());

    private String nmsDBName;

    private String siteId;

    @SuppressWarnings("unchecked")
    public void getAlarmCount() throws SQLException {

        Integer result = new Integer(0);

        Map<String, String> paras = new HashMap<String, String>();

        paras.put("nmsDB", nmsDBName);

        if (null != siteId && !"".equals(siteId)) {
            paras.put("siteId", siteId);
        }

        List<TopoAlarmDomain> list = ibatisDAO.getData("TopoGraph.getAlarmGraph", paras);

        if (null != list) result = list.size();

        HttpServletResponse resp = ServletActionContext.getResponse();

        PrintWriter pw = null;

        try {
            pw = resp.getWriter();

            pw.write(result.toString());

        } catch (IOException e) {
            logger.error("拓扑图获取告警个数Ajax获取输出流异常", e);
            e.printStackTrace();
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }

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
