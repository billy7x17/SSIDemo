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
package com.cloudmaster.cmp.util.createXML;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;  
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.Marshaller;  
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
/** 
 * 使用JAXB处理JavaBean和XML的转换 
 * @create Mar 17, 2013 3:25:49 PM 
 * @author 汪海滨
 */  
public class ResourceReportXML {
    public static void main(String[] args) throws Exception {
        //第一个内部类
        CMSYNCINFO.CONFIGURATIONINFO c = new CMSYNCINFO.CONFIGURATIONINFO();
        c.setInstanceId("A001");
        c.setCiId("SWITCH");
            List<CIATTRIBUTEINFO> list = new ArrayList<CIATTRIBUTEINFO>();
            CIATTRIBUTEINFO cc = new CIATTRIBUTEINFO();
            cc.setAttributeId("SwitchType");
            cc.setAttributeValue("CISCO2950");
            CIATTRIBUTEINFO cc1 = new CIATTRIBUTEINFO();
            cc1.setAttributeId("SwitchIP");
            cc1.setAttributeValue("192.168.1.100");
            list.add(cc);
            list.add(cc1);
        c.setCIATTRIBUTEINFO(list);
//        c.setCreationTime(new XMLGregorianCalendarImpl().createDateTime(2013, 05, 07, 8, 10, 10));
//        c.setLastUpateTime(new XMLGregorianCalendarImpl().createDateTime(2013, 05, 07, 8, 58, 40));
        c.setStatus(0);
        //第二个内部类
        CMSYNCINFO.CONFIGURATIONINFO c1 = new CMSYNCINFO.CONFIGURATIONINFO();
        c1.setInstanceId("B001");
        c1.setCiId("FIREWALL");
        c1.setStatus(1);
//        c1.setCreationTime(new XMLGregorianCalendarImpl().createDateTime(2013, 05, 07, 8, 58, 40));
//        c1.setLastUpateTime(new XMLGregorianCalendarImpl().createDateTime(2013, 05, 07, 8, 58, 40));
        //将两个内部类装入到集合中
        List<CMSYNCINFO.CONFIGURATIONINFO> list2 = new ArrayList<CMSYNCINFO.CONFIGURATIONINFO>();
        list2.add(c);
        list2.add(c1);
        //将集合装入到CMSYNCINFO中
        CMSYNCINFO re = new CMSYNCINFO();
        re.setCONFIGURATIONINFO(list2);
        //调用生成XML的方法
        genertXMl(re);
    }
    public static void genertXMl(CMSYNCINFO cmsyncinfo) throws Exception {  
        StringWriter sw = new StringWriter();  
        /** 
         * 生成JAXBContext 
         */  
        //要在JavaBean中使用@XmlRootElement注解指定XML根元素,否则Marshal或者UnMarshal都会失败  
        JAXBContext jaxbContext = JAXBContext.newInstance(CMSYNCINFO.class);
        /** 
         * 将JavaBean编排为XML字符串
         */  
        Marshaller marshaller = jaxbContext.createMarshaller();
        //格式化生成XML
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        //该值默认为false,true则不会创建即头信息,即<?xml version="1.0" encoding="UTF-8" standalone="yes"?>  
        //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);  
        //直接输出到控制台  
        //marshaller.marshal(stu, System.out);  
        marshaller.marshal(cmsyncinfo, sw);
        //控制台输出打印
        //System.out.println(sw.toString());
        //设置生成XML的路径
        String resultp = ResourceReportXML.class.getResource("").getPath();
        resultp = resultp.substring(1, resultp.length());
        //System.out.println("当前类路径--------"+resultp);
//        int index = resultp.indexOf("WEB-INF");
//        resultp = "/" + resultp.substring(0, index);
        FileOutputStream fos = new FileOutputStream(new File("d:\\test.xml"));
        fos.write(sw.toString().getBytes());
        fos.close();
    }
}


