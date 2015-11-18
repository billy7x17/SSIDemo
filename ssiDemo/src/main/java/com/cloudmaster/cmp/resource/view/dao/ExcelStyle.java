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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * 表头 与 数据 样式
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ExcelStyle { 
    /**
     * 表头样式
     * @param workbook
     * @param headStyle
     * @return excel样式
     */
    public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook ,HSSFCellStyle headStyle)  
    {  
        headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        headStyle.setWrapText(true);
        // 生成字体  
        HSSFFont font = workbook.createFont();  
        font.setFontName("宋体");
//        font.setColor(HSSFColor.VIOLET.index);  
        font.setFontHeightInPoints((short) 9);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样样式  
        headStyle.setFont(font);  
        return headStyle;  
          
    }  
    /**
     * 数据样式
     * @param workbook
     * @param bodyStyle
     * @return excel样式
     */
    public static HSSFCellStyle setbodyStyle(HSSFWorkbook workbook ,HSSFCellStyle bodyStyle)  
    {  
//        bodyStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
//        bodyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
//        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
//        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
//        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
//        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
//        bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        bodyStyle.setWrapText(true);
        // 生成字体  
//        HSSFFont font2 = workbook.createFont();  
//        font2.setFontName("宋体");
//        font2.setFontHeightInPoints((short) 10); 
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
//        // 把字体应用到当前的样样式  
//        bodyStyle.setFont(font2);  
        
        //设置文本格式
        HSSFDataFormat format = workbook.createDataFormat();
        bodyStyle.setDataFormat(format.getFormat("@"));
        return bodyStyle;  
    }
}  
 
