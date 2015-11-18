package com.cloudmaster.cmp.topology.buildGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.resource.site.dao.SiteInfo;
import com.cloudmaster.cmp.topology.util.TopoUtil;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.cloudmaster.cmp.web.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * <b>Application describing: 拓扑图构建布局 </b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-11 下午5:18:55
 */
public class DevicesGraph extends BaseAction {

    private static final long serialVersionUID = 104656330279472666L;

    private static final Logger logger = Logger.getLogger(DevicesGraph.class);

    private String siteId;// 站点id

    private String siteName;// 站点名称

    private Element layout;// 界面展示层

    private Map<String, List<Element>> nvrChildrenMap; // nvr 与 摄像机、编码器List 的映射Map

    @SuppressWarnings("unchecked")
    public String drawGraph() throws Exception {
        logger.info("开始作拓扑图");

        /* 从session中获取站点 */
        Map<?, ?> session = ActionContext.getContext().getSession();

        UserInfo user = (UserInfo) session.get(SessionKeys.userInfo);

        if (isNull(siteId)) {
            siteId = String.valueOf(user.getSiteId());
        }

        SiteInfo site = (SiteInfo) ibatisDAO.getSingleRecord("SiteInfo.detail", siteId.toString());

        siteName = site.getSiteName();

        List<DeviceDomain> list = ibatisDAO.getData("TopoGraph.getDeviceGraph",
                Integer.valueOf(siteId));

        // 创建根节点
        Element data = new Element("construction");
        // 将根节点添加到
        Document doc = new Document(data);
        // 一层子节点
        Element setting = TopoUtil.getSetting();

        Element synch = new Element("synch");

        layout = new Element("layout").setAttribute("type", "AutoLayout").setAttribute("style",
                "topbottom"); // 样式决定

        final String imagePrefix = "images/nodes/";

        final String imageSuffix = ".png";

        // 将一层子节点添加到根节点中
        data.addContent(setting);
        data.addContent(synch);

        /* 绘制节点(固定4行) */
        List<Element> row1 = new ArrayList<Element>();
        List<Element> row2 = new ArrayList<Element>();
        List<Element> row3 = new ArrayList<Element>();
        List<Element> row4 = new ArrayList<Element>();

        /* 存储该行ID的数组 */
        final List<Integer> arr1Index = new ArrayList<Integer>();

        if (null == nvrChildrenMap) {
            nvrChildrenMap = new HashMap<String, List<Element>>();
        }

        /* 第一行只有站点节点 */
        Element root = addDetailNode("root", siteName, imagePrefix + "root" + imageSuffix);

        /* 根节点没有双击事件 */
        root.removeAttribute("type");

        row1.add(root);

        for (DeviceDomain node : list) {
            String nvr = node.getNvrId();
            String vms = node.getVmsId();
            if (isNull(nvr) && isNull(vms)) {
                row2.add(addDetailNode(node.getAgentId(), node.getAgentName(),
                        imagePrefix + node.getAgentGroup() + imageSuffix));
                /* 第二行数据加入至排序数组 */
                arr1Index.add(Integer.valueOf(node.getAgentId()));
            } else if (isNull(nvr) && !isNull(vms)) {
                /* 第三行节点添加父节点ID “vmsId”属性 ， 以便排序 */
                row3.add(addDetailNode(node.getAgentId(), node.getAgentName(),
                        imagePrefix + node.getAgentGroup() + imageSuffix)
                        .setAttribute("vmsId", vms));
            } else if (!isNull(nvr) && !isNull(vms)) {

                String parentId = node.getAgentGroup() + "_" + nvr;

                if (!nvrChildrenMap.containsKey(parentId)) {
                    /* 如果这是该nvr下的第一个子节点，则新增Nvr节点 */
                    row4.add(addParentNode(parentId, "", imagePrefix + node.getAgentGroup()
                            + "_parent" + imageSuffix)); // 摄像机、编码器的父节点命名：IPC、ENCODER
                    // +
                    // 其父节点NVRID

                    nvrChildrenMap.put(parentId, new ArrayList<Element>());
                }

                nvrChildrenMap.get(parentId).add(
                        addDetailNode(node.getAgentId(), node.getAgentName(),
                                imagePrefix + node.getAgentGroup() + imageSuffix));
            }
        }

        /* 第三行排序 */
        Object[] row3Temp = row3.toArray();

        Arrays.sort(row3Temp, new Comparator<Object>() {

            @Override
            public final int compare(Object a, Object b) {

                Element aElem = (Element) a;

                Element bElem = (Element) b;

                Integer aId = Integer.valueOf(aElem.getAttributeValue("vmsId"));

                Integer bId = Integer.valueOf(bElem.getAttributeValue("vmsId"));

                if (null != aId && null != bId) {
                    if (arr1Index.indexOf(aId) > arr1Index.indexOf(bId)) {
                        return 1;
                    } else if (arr1Index.indexOf(aId) == arr1Index.indexOf(bId)) {
                        return 0;
                    } else {
                        return -1;
                    }
                } else {
                    return 0;
                }

            }

        });

        /* 将排好序的数组重新赋值给List */
        row3 = new ArrayList<Element>();
        for (Object object : Arrays.asList(row3Temp)) {
            row3.add((Element) object);
        }

        /* 将数据传给布局方法 */
        List<List<Element>> dataList = new ArrayList<List<Element>>();

        dataList.add(row1);
        dataList.add(row2);
        dataList.add(row3);
        dataList.add(row4);

        layout = new GraphLayout().gridAutoLayout(dataList);

        /* 绘制连线 */
        /* nvr及其子节点 */
        for (Iterator<String> i3 = nvrChildrenMap.keySet().iterator(); i3.hasNext();) {
            String parentId = i3.next();
            String[] args = parentId.split("_");
            String nvrId = args[1];
            addLink(parentId, nvrId);
        }
        /* 其它节点 */
        for (Iterator<DeviceDomain> i2 = list.iterator(); i2.hasNext();) {
            DeviceDomain node = (DeviceDomain) i2.next();
            if (isNull(node.getNvrId()) && !isNull(node.getVmsId())) {
                addLink(node.getVmsId(), node.getAgentId());
            }
            /*
             * if (null != node.getNvrId()) { addLink(node.getNvrId(), node.getAgentId()); }
             */

            if (isNull(node.getVmsId()) && isNull(node.getNvrId())) {
                addLink("root", node.getAgentId());
            }
        }

        data.addContent(layout);

        TopoUtil.generateJspFile(doc, "topCanvas"+siteId);

        logger.info("基础拓扑图绘制完成");

        /* 重定向传参给页面 */
        ServletActionContext.getContext().getSession().put("topoSiteId", siteId);

        return new DevicesSubGraph().drawSubGraph(nvrChildrenMap);
    }

    /**
     * 增加详细信息节点
     * @param seq 节点ID
     * @param name 节点名称
     * @param image 节点图片
     * @return 节点对象
     */
    private Element addDetailNode(String seq, String name, String image) {

        return new Element("node")
                .setAttribute("seq", seq)
                .setAttribute("name", name)
                .setAttribute("image", image)
                .setAttribute("type", "detailCanvas")
                .
                /* 与该节点相连的连线与该节点同样闪烁 */
                addContent(
                        new Element("property").setAttribute("name", "linkAlerm").setAttribute(
                                "value", "true"))
                .addContent(
                        new Element("property").setAttribute("type", "style")
                                .setAttribute("name", "label.font").setAttribute("value", "YaHei"))
                .addContent(
                        new Element("property").setAttribute("type", "style")
                                .setAttribute("name", "label.embed").setAttribute("value", "true"));

    }

    /**
     * 增加IPC和ENCODER的父节点
     * @param seq 父节点ID
     * @param name 父节点名称
     * @param image 节点图片
     * @return 节点对象
     */
    private Element addParentNode(String seq, String name, String image) {
        return new Element("node")
                .setAttribute("seq", seq)
                .setAttribute("name", name)
                .setAttribute("image", image)
                .setAttribute("type", "subCanvas")
                .
                /* 与该节点相连的连线与该节点同样闪烁 */
                addContent(
                        new Element("property").setAttribute("name", "linkAlerm").setAttribute(
                                "value", "true"))
                .addContent(
                        new Element("property").setAttribute("type", "style")
                                .setAttribute("name", "label.font").setAttribute("value", "YaHei"))
                .addContent(
                        new Element("property").setAttribute("type", "style")
                                .setAttribute("name", "label.embed").setAttribute("value", "true"));
    }

    /**
     * 增加连线
     * @param 节点1序列号
     * @param 节点2序列号
     */
    private void addLink(String seq1, String seq2) {
        layout.addContent(new Element("link").setAttribute("from", seq1).setAttribute("to", seq2));
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    private boolean isNull(String str) {
        return null == str || "".equals(str);
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
