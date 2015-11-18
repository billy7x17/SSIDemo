package com.cloudmaster.cmp.topology.buildGraph;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.cloudmaster.cmp.topology.util.TopoUtil;
import com.cloudmaster.cmp.web.BaseAction;

/**
 * <b>Application describing: 构建子拓扑图</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-14 上午9:36:53
 */
class DevicesSubGraph extends BaseAction {

    private static final long serialVersionUID = 3154106573729598764L;

    private static final Logger logger = Logger.getLogger("DevicesSubGraph".getClass());

    private Element layout;

    /**
     * 作图入口
     * @param nvrChildrenMap 子图的数据Map(key:子图父节点seq,value:子图数据List)
     * @return 作图完成
     */
    String drawSubGraph(Map<String, List<Element>> nvrChildrenMap) {

        if (null != nvrChildrenMap) {
            logger.info("开始做子拓扑图");

            for (Iterator<String> i = nvrChildrenMap.keySet().iterator(); i.hasNext();) {
                String index = (String) i.next();

                if (null != nvrChildrenMap.get(index)) {
                    TopoUtil.generateJspFile(drawGraph(nvrChildrenMap.get(index), "Sub" + index),
                            "subCanvas" + index);
                }

            }

            logger.info("子拓扑图绘制完成");

        }

        return "success";
    }

    /**
     * 作子拓扑图
     * @param elems 数据List
     * @param index 父节点seq
     * @return 作图完成后的Document对象
     */
    private Document drawGraph(List<Element> elems, String index) {

        // 创建根节点
        Element data = new Element("construction");
        // 将根节点添加到
        Document doc = new Document(data);
        // 一层子节点
        Element setting = TopoUtil.getSetting();

        Element synch = new Element("synch");

        layout = new Element("layout").setAttribute("type", "GridAutoLayout").setAttribute("style",
                "topbottom"); // 样式决定

        // 将一层子节点添加到根节点中
        data.addContent(setting);
        data.addContent(synch);

        Element topRow = new Element("row");

        topRow.addContent(new Element("rank"));

        Element bottomRow = new Element("row");

        bottomRow.addContent(new Element("rank"));

        for (int i = 0; i < elems.size(); i++) {
            if (0 == i % 2) {
                bottomRow.addContent(new Element("rank").addContent(elems.get(i)));
                bottomRow.addContent(new Element("rank"));
            } else {
                topRow.addContent(new Element("rank").addContent(elems.get(i)));
                topRow.addContent(new Element("rank"));
            }
        }

        /* 第一行节点 */
        layout.addContent(new Element("row").addContent(new Element("rank").addContent(topRow)));

        /* 拓扑图的根节点 --> 站点节点 */
        Element root = addNode(index, "", elems.get(0).getAttributeValue("image"));

        /* 根节点没有双击事件 */
        root.removeAttribute("type");

        layout.addContent(new Element("row").addContent(new Element("rank").addContent(new Element(
                "row").addContent(new Element("rank").addContent(root)))));

        /* 第三行节点 */
        layout.addContent(new Element("row").addContent(new Element("rank").addContent(bottomRow)));

        /* 绘制连线 */
        for (Iterator<Element> i2 = elems.iterator(); i2.hasNext();) {
            Element node = (Element) i2.next();
            addLink(index, node.getAttributeValue("seq"));
        }

        data.addContent(layout);

        return doc;
    }

    /**
     * 增加节点
     * @param seq 节点序列号
     * @param name 节点名称
     * @param image 图片
     */
    private Element addNode(String seq, String name, String image) {
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
     * 增加连线
     * @param 节点1序列号
     * @param 节点2序列号
     */
    private void addLink(String seq1, String seq2) {
        layout.addContent(new Element("link")
                .setAttribute("from", seq1)
                .setAttribute("to", seq2)
                .addContent(
                        new Element("property").setAttribute("name", "link.pattern")
                                .setAttribute("type", "style").setAttribute("value", "5,5")));
    }
}
