package com.cloudmaster.cmp.topology.buildGraph;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.topology.domain.TopoAlarmDomain;
import com.cloudmaster.cmp.topology.util.TopoUtil;
import com.cloudmaster.cmp.web.BaseAction;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.opensymphony.xwork2.ActionContext;

/**
 * <b>Application describing: 实时告警更新Action</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-14 下午3:47:50
 */
public class AlarmGraph extends BaseAction {

    private static final long serialVersionUID = -8513294186264816594L;

    private Logger logger = Logger.getLogger("AlarmGraph".getClass());

    private String nmsDBName;

    private String[] alarmGrade = { "CRITICAL", "MAJOR", "MINOR", "WARNING" };

    public void drawAlarmGraph() throws SQLException {

        logger.info("拓扑图告警全量图绘制开始");

        long start = System.currentTimeMillis();

        /* 从session中获取站点 */
        Map<?, ?> session = ActionContext.getContext().getSession();

        UserInfo user = (UserInfo) session.get(SessionKeys.userInfo);

        Integer siteId = user.getSiteId();

        Map<String, Object> paras = new HashMap<String, Object>();

        paras.put("nmsDB", nmsDBName);

        if (!new Integer(1).equals(siteId)) paras.put("siteId", siteId);

        /* 查询所有节点的告警数据 */
        @SuppressWarnings("unchecked")
        List<TopoAlarmDomain> list = ibatisDAO.getData("TopoGraph.getAlarmGraph", paras);

        // 创建根节点
        Element data = new Element("increment");
        // 将根节点添加到
        Document doc = new Document(data);
        // 一层子节点
        Element setting = TopoUtil.getSetting();

        Element synch = new Element("synch");

        data.addContent(setting).addContent(synch);

        /* 数据存入节点 */
        for (Iterator<TopoAlarmDomain> i = list.iterator(); i.hasNext();) {
            TopoAlarmDomain temp = (TopoAlarmDomain) i.next();

            /* 判空 避免告警所属的设备不存在的情况 */
            if (null != temp.getAgentId() && !"".equals(temp.getAgentId())) {
                /* 如果是子图中的节点，那么在父图中，父节点要相应产生告警 */
                /* 按时间排序，子图中最后产生的告警级别为父节点的告警级别 */
                if (null != temp.getNvrId()) {

                    String seq = temp.getAgentGroup() + "_" + temp.getNvrId();

                    Element parentAlarm = new Element("alerm")
                            .setAttribute("seq", seq)
                            .setAttribute("level",
                                    alarmGrade[Integer.valueOf(temp.getAlarmGrade())])
                            .setAttribute("count", temp.getCount());

                    data.addContent(parentAlarm);
                }

                Element alarm = new Element("alerm").setAttribute("seq", temp.getAgentId())
                        .setAttribute("level", alarmGrade[Integer.valueOf(temp.getAlarmGrade())])
                        .setAttribute("count", temp.getCount());
                data.addContent(alarm);
            }

        }

        /* 生成jsp文件 */
        TopoUtil.generateJspFile(doc, "IncrementMVC_" + siteId);

        /* 刷新页面上的告警设备ID数组 */

        JSONArray arr = JSONArray.fromObject(list);

        HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setCharacterEncoding("utf-8");
        PrintWriter pw = null;
        try {
            pw = resp.getWriter();
            pw.write(arr.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            logger.error("拓扑图刷新页面上的告警设备ID数组时，调用ajax异常", e);
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }

        long end = System.currentTimeMillis();

        logger.info("拓扑图告警全量图绘制完成");
        logger.info("用时 " + (end - start) + "ms");
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
}
