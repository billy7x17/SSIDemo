package com.cloudmaster.cmp.topology.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class TopoUtil {

    private final static Logger logger = Logger.getLogger("TopoUtil".getClass());

    public static Element getSetting() {

        Element setting = new Element("setting");

        /* auto */
        Element auto = new Element("auto");

        Element zoomOverview = new Element("zoomOverview").addContent("false");

        Element increment = new Element("increment").addContent("true");

        auto.addContent(zoomOverview).addContent(increment);

        setting.addContent(auto);

        /* style */
        Element style = new Element("style");

        Element bg_fill_color = new Element("bg_fill_color").addContent("0xFFFFFF");

        Element bg_gradient_color = new Element("bg_gradient_color").addContent("0xFFFFFF");

        Element selectColor = new Element("selectColor").addContent("0X15A230");

        style.addContent(bg_fill_color).addContent(bg_gradient_color).addContent(selectColor);

        setting.addContent(style);

        /* toolbar */
        Element toolbar = new Element("toolbar");

        Element navigationName = new Element("navigationName").addContent("首页");

        toolbar.addContent(navigationName);

        setting.addContent(toolbar);

        return setting;
    }

    /**
     * 生成jsp文件
     * @param doc 作图完成后的Document对象
     * @param name 文件名
     */
    public static void generateJspFile(Document doc, String name) {

        HttpServletRequest req = ServletActionContext.getRequest();

        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlout = new XMLOutputter(format);

        String filename = req.getSession().getServletContext().getRealPath("/") + "twaver/" + name
                + ".jsp";
//        logger.info("Path Test : " + filename);
        String jspHead = "<%@page contentType=\"text/xml; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n";
        OutputStream os = null;
        try {
            os = new FileOutputStream(filename);

            synchronized (doc) {
                os.write(jspHead.getBytes());
                xmlout.output(doc, os);
            }

        } catch (Exception e) {
            logger.error("createData error", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("Close jsp file error:", e);
                }
            }
        }
    }
}
