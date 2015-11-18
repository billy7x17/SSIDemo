package com.cloudmaster.cmp.topology.buildGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;

/**
 * <b>Application describing: 拓扑图布局方法</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-8-13 上午10:24:33
 */
class GraphLayout {

    private static final Logger logger = Logger.getLogger(GraphLayout.class);

    /**
     * 拓扑图布局主要方法
     * @param dataList 传入的节点数据
     * @return layout节点对象
     */
    Element gridAutoLayout(List<List<Element>> dataList) throws Exception {

        logger.info("拓扑图布局开始");

        Element layout = new Element("layout");

        List<Element> elems = null;

        /* 遍历每行的数据 */
        for (int i = 0; i < dataList.size(); i++) {

            Element row = new Element("row");

            /* 分别执行行布局方法 */
            if (i != dataList.size() - 1) {
                elems = rankSort(dataList.get(i));
            } else {
                elems = lastRowRank(elems, dataList.get(i));
            }

            /* 将布局好的行加入到构造中 */
            for (Element temp : elems) {
                row.addContent(temp);
            }

            /* 构造重建至所需结构 */
            layout.addContent(new Element("row").addContent(new Element("rank").addContent(row)));
        }

        layout.setAttribute("type", "GridAutoLayout").setAttribute("style", "topbottom");

        logger.info("拓扑图布局结束");

        return layout;
    }

    /**
     * 每行的拓扑布局，除了最后一行
     * @param data 该行的节点数据List
     * @return 布局后的List
     */
    private List<Element> rankSort(List<Element> data) {

        List<Element> result = new ArrayList<Element>();

        /* result.add(new Element("rank")); */

        /* 每两个节点间都有一个空rank，头尾也各有一个空rank */
        for (Element e : data) {
            result.add(new Element("rank").addContent(e));
            /* result.add(new Element("rank")); */
        }

        return result;
    }

    /**
     * 最后一行的拓扑布局
     * @param elems 倒数第二行的布局后的List(用来确定父节点位置)
     * @param data 该行的节点数据List
     * @return 布局后的List
     */
    private List<Element> lastRowRank(List<Element> elems, List<Element> data) throws Exception {

        List<String> parentSeqList = new ArrayList<String>();

        for (Element i : data) {
            if (!parentSeqList.contains(i.getAttributeValue("seq").split("_")[1])) {
                parentSeqList.add(i.getAttributeValue("seq").split("_")[1]);
            }
        }

        List<Integer> la = new ArrayList<Integer>();

        int size = elems.size() * 3;

        /* 确认该行节点应该在的位置 */
        for (int i = 0; i < elems.size(); i++) {
            /* 判断该rank下是否有节点，有节点是否为NVR节点 */
            if (null != elems.get(i).getChild("node")
                    && parentSeqList.contains(elems.get(i).getChild("node")
                            .getAttributeValue("seq"))) {

                int count = 0;

                for (Iterator<Element> j = data.iterator(); j.hasNext();) {
                    Element e = (Element) j.next();
                    if (e.getAttributeValue("seq").split("_")[1].equals(elems.get(i)
                            .getChild("node").getAttributeValue("seq"))) {
                        count++;
                    }
                }

                if (1 == count) {
                    la.add(new Integer(i * 3 + 1));
                } else if (2 == count) {
                    la.add(new Integer(i * 3));
                    la.add(new Integer(i * 3 + 2));
                } else if (3 == count) {
                    la.add(new Integer(i * 3));
                    la.add(new Integer(i * 3 + 1));
                    la.add(new Integer(i * 3 + 2));
                } else {
                    throw new Exception("NVR下包含子节点过多(多于3个)，影响拓扑布局。");
                }
                
            }
        }

        List<Element> result = new ArrayList<Element>(size);

        /* 按照取得的序列值，将数据向该行部署 */
        for (int i = 0; i < size; i++) {

            Element rank = new Element("rank");

            if (la.contains(new Integer(i))) {
                rank.addContent(data.remove(0));
            }

            result.add(rank);
        }

        return result;
    }
}
