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
package com.cloudmaster.cmp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表页面工具类
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class FlexGridJSONData {
    /**
     * 构造函数
     */
    public FlexGridJSONData() {

    }

    // 页数
    private int page = 1;

    // 总数量
    private int total = 0;

    // 记录列表
    private List<RowData> rows = new ArrayList<RowData>();

    // 记录ID
    private String rowid = null;

    // 记录对象
    private List<String> coldatas = null;

    /**
     * 获取页数
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置页码。
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    /**
     * 设置总记录数
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowData> getRows() {
        return rows;
    }

    public void setRows(List<RowData> rows) {
        this.rows = rows;
    }

    public void addRow(String rowid, List<String> coldatas) {
        RowData rd = new RowData(rowid, coldatas);
        this.rows.add(rd);
    }

    /**
     * 设置每一行的id。 配合addColdata(),commitData()方法是用。 例：setRowId("row1"); addColdata("1");
     * addColdata("2"); setRowId("row2"); addColdata("a"); addColdata("b"); commitData(); 表示
     * 1，2两个数据都为行row1中第一列，第二列的数据。 a,b 两个数据都为row2中第一列，第二列的数据。 commitData()的调用表示，row2行的数据已经组织完成。
     * 在设置row2行的数据时，会自动提交row1行的数据。
     * @param rowid
     */
    public void setRowId(String rowid) {
        commitData();
        this.rowid = rowid;
        this.coldatas = null;
    }

    /**
     * 该方法配合setRowId和commitData()使用。 该方法必须在调用setRowId()后调用，否则会抛出NullPointerException 请参考setRowId的说明
     * @param coldata 每一列数据
     */
    public void addColdata(String coldata) {
        if (rowid == null) throw new NullPointerException("please set rowid");
        if (coldatas == null) coldatas = new ArrayList<String>();
        if (coldata != null) {
            coldata = coldata.replace("\\", "\\\\");
            coldata = coldata.replace("\b", "\\\b");
            coldata = coldata.replace("\t", "\\\t");
            coldata = coldata.replace("\f", "\\\f");
            coldata = coldata.replace("\"", "\\\"");
            // coldata = coldata.replace("\n", "\\\n");
            // coldata = coldata.replace("\r", "\\\r");
            coldata = coldata.replaceAll("(\r\n|\r|\n|\n\r)", " ");
        }
        coldatas.add(coldata);
    }

    /**
     * 提交数据
     */
    public void commitData() {
        if (this.rowid != null && this.coldatas != null) {
            addRow(this.rowid, this.coldatas);
            this.rowid = null;
            this.coldatas = null;
        }
    }

    /*
     * 这里生成的是符合flexgrid for jquery 的json格式字符串 其格式如下： { page:1, total:0, rows:[
     * {id:'row2',cell:['col','col','col','col','col','col']},
     * {id:'row3',cell:['col','col','col','col','col','col']},
     * {id:'row1',cell:['col','col','col','col','col','col']} ] }
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"page\":").append(page).append(",");
        sb.append("\"total\":").append(total).append(",");
        sb.append("\"rows\":[");

        int keynum = 1;
        List<RowData> rowdatalist = this.rows;
        for (RowData rowdata : rowdatalist) {
            sb.append("  {\"id\":\"").append(rowdata.getRowid()).append("\",").append("\"cell\":[");
            int i = 1;
            List<String> coldatalist = rowdata.getColdata();
            for (String data : coldatalist) {
                sb.append("\"").append(data).append("\"");
                if (i < coldatalist.size()) {
                    sb.append(",");
                }
                i++;
            }

            if (keynum < rowdatalist.size()) {
                sb.append("]},");
            } else {
                sb.append("]}");
            }

            keynum++;
        }
        sb.append("  ]}");
        return sb.toString();
    }

    public class RowData {
        String rowid = null;

        List<String> coldata = null;

        public RowData() {

        }

        public RowData(String rowid, List<String> coldata) {
            this.rowid = rowid;
            this.coldata = coldata;
        }

        public List<String> getColdata() {
            return coldata;
        }

        public String getRowid() {
            return rowid;
        }

        public void setColdata(List<String> coldata) {
            this.coldata = coldata;
        }

        public void setRowid(String rowid) {
            this.rowid = rowid;
        }
    }

    public static void main(String args[]) {

        ArrayList<String> list = new ArrayList<String>();
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");

        FlexGridJSONData fgjd = new FlexGridJSONData();
        fgjd.setRowId("row1");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.setRowId("row2");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.setRowId("row3");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.commitData();
        System.out.println(fgjd.toString());

    }
}
