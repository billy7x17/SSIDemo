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
package com.cloudmaster.cmp.resource.view.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;

/**
 * Excel 导出
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
@Controller
public class ExportExcel<T> {
    /**
     * 表格头样式
     */
    private HSSFCellStyle headStyle;
    /**
     * 表格体样式
     */
    private HSSFCellStyle bodyStyle;
    public ExportExcel() {
    }
    public ExportExcel(HSSFCellStyle hStyle, HSSFCellStyle bStyle) {
        this.headStyle = hStyle;
        this.bodyStyle = bStyle;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化日期

    /**
     * 判断字符串是否在字符串数组中
     * @param substring
     * @param source
     * @return 参数substring 是否存在于 source
     */
    private boolean isIn(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }
        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 一个sheet 且有对应的 javabean
     * @param maxRow 每个sheet的最大行数
     * @param title 标题
     * @param dataset 集合
     * @param args 要屏蔽的列字段名数组
     * @throws Exception
     */
    public InputStream exportExcel(int maxRow, String title, Collection<T> dataset, String[] args)
            throws Exception {
        // 声明一个工作薄
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 首先检查数据看是否是正确的
            Iterator<T> its = dataset.iterator();
            if (dataset == null || !its.hasNext() || title == null || baos == null) {
                throw new Exception("传入的数据不对！");
            }
            // 取得实际泛型类
            T ts = (T) its.next();
            Class tCls = ts.getClass();
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = null;
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
            style = ExcelStyle.setHeadStyle(workbook, style);

            // 得到所有字段

            Field filed[] = ts.getClass().getDeclaredFields();
            // 标题
            List<String> exportfieldtile = new ArrayList<String>();
            // 导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            // 遍历整个filed
            for (int i = 0; i < filed.length; i++) {
                Field f = filed[i];
                ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
                String fieldname = f.getName();
                // 如果设置了annottion 并且 字段名没有在被排除的数组中
                if (exa != null && !isIn(fieldname, args)) {
                    // 添加到需要导出的字段的方法

                    String exprot = exa.exportName();
                    // 添加到标题
                    exportfieldtile.add(exprot);
                    String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase()
                            + fieldname.substring(1);

                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    methodObj.add(getMethod);
                }
            }

            int index = 0;
            int titleIndex = 1;
            HSSFRow row = null;
            // 循环整个集合
            its = dataset.iterator();
            while (its.hasNext()) {
                if (index == 0) {// index 代表每个sheet页的第一条真实数据,即写入第一条真实数据之前，创建表头
                    sheet = workbook.createSheet(title + "_" + titleIndex);
                    titleIndex++;
                    // 设置表格默认列宽度为20个字节
                    sheet.setDefaultColumnWidth(20);
                    // 产生表格标题行
                    row = sheet.createRow(0);
                    for (int i = 0; i < exportfieldtile.size(); i++) {
                        HSSFCell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        HSSFRichTextString text = new HSSFRichTextString(exportfieldtile.get(i));
                        cell.setCellValue(text);
                    }
                }
                // 从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                T t = (T) its.next();
                for (int k = 0; k < methodObj.size(); k++) {
                    HSSFCell cell = row.createCell(k);
                    Method getMethod = methodObj.get(k);
                    Object value = getMethod.invoke(t, new Object[] {});
                    String textValue = getValue(value);
                    cell.setCellValue(textValue);
                    
//                    // 生成一个样式
//                    HSSFCellStyle bodystyle = workbook.createCellStyle();
//                    // 设置样式
//                    bodystyle = ExcelStyle.setbodyStyle(workbook, bodystyle);
//                    cell.setCellStyle(bodystyle);

                }
                if (index == maxRow) {
                    index = 0;
                }
            }
            workbook.write(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            baos.close();
        }
    }

    /**
     * 数据替换
     * @param value
     * @return 格式化后的内容
     * @throws ParseException
     */
    @SuppressWarnings( { "static-access" })
    private String getValue(Object value) throws ParseException {
        String textValue = "";
        if (null == value) {
            return textValue;
        }
        if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            textValue = "是";
            if (!bValue) {
                textValue = "否";
            }
        } else if (value instanceof GregorianCalendar) {
            GregorianCalendar calendar = (GregorianCalendar) value;
            Date d = calendar.getTime();
            textValue = sdf.format(d);
        } else {
            textValue = value.toString();
        }
        return textValue;
    }

    /**
     * 多个sheet 列不定时使用
     * @param rdList
     * @param out
     * @throws Exception
     */
    public InputStream exportExcelResource(List<ResourceData> rdList) throws Exception {
        // 声明一个工作薄
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 首先检查数据看是否是正确的
            if (rdList == null || rdList.size() == 0 || baos == null) {
                throw new Exception("传入的数据不对！");
            }
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (ResourceData rd : rdList) {
                // 生成一个表格
                HSSFSheet sheet = workbook.createSheet(rd.getTitle());
                // 设置表格默认列宽度为20个字节
                sheet.setDefaultColumnWidth(20);
                // 生成一个样式
                HSSFCellStyle defaultHeadStyle = workbook.createCellStyle();
                // 设置标题样式
                defaultHeadStyle = ExcelStyle.setHeadStyle(workbook, defaultHeadStyle);
                // 产生表格标题行
                HSSFRow row = sheet.createRow(0);
                for (int i = 0; i < rd.getHeads().length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(headStyle!=null?headStyle:defaultHeadStyle);
                    HSSFRichTextString text = new HSSFRichTextString(rd.getHeads()[i]);
                    cell.setCellValue(text);
                }
                // 产生数据
                for (int i = 0; i < rd.getBodys().size(); i++) {
                    row = sheet.createRow(i + 1);
                    for (int j = 0; j < rd.getBodys().get(i).length; j++) {
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue(rd.getBodys().get(i)[j]);
                        // 生成一个样式
                        HSSFCellStyle defaultBodyStyle = workbook.createCellStyle();
                        // 设置样式
                        defaultBodyStyle = ExcelStyle.setbodyStyle(workbook, defaultBodyStyle);
                        cell.setCellStyle(bodyStyle!=null?bodyStyle:defaultBodyStyle);
                    }
                }
            }
            workbook.write(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            baos.close();
        }
    }
}
