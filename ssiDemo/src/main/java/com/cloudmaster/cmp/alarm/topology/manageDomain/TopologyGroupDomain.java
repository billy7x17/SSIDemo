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
package com.cloudmaster.cmp.alarm.topology.manageDomain;

import java.io.Serializable;

/**
 * 拓扑分组管理Domain
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 July 2013
 */
public class TopologyGroupDomain implements Serializable {
    private static final long serialVersionUID = 7911881341932143663L;
    private String groupID;
    private String groupName;
    /*0:主分组、1:子分组*/
    private String groupType;
    /*样式ID*/
    private String styleID;
    /*flexgrid排序字段*/
    private String sortName;
    private String sortOrder;
    /*样式表字段*/
    //  VARCHAR 1   否   是否自动布局。0:false、1:true
    private String zoom_overview;
    //  VARCHAR 1   否   是否动态更新    0:false 1:true
    private String increment;
    //  VARCHAR 8   否   背景填充色   16进制表示  默认：0xFFFFFF
    private String bg_fill_color;
    //  VARCHAR 8   否   背景倾斜色   16进制表示  默认：0xFFFFFF
    private String bg_gradient_color;
    //  VARCHAR 8   否   选择色 16进制表示   默认：0X15A230
    private String select_color;
    //  VARCHAR 255 否   导航
    private String navigation_name;
    //  VARCHAR 1   否   是否显示左倾面板    0:false 1:true
    private String tree;
    //  VARCHAR 20  否   布局  枚举： GridAutoLayout：表格布局 AutoLayout：自动布局
    private String layout;
    //  VARCHAR 20  是   样式：选自动布局时使用   枚举：round    symmetry
    //  topbottom bottomtop leftright   rightleft   默认：topbottom
    private String style;
    //  VARCHAR 20  是   图片
    private String image;

    public String getSortName() {
        return sortName;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupType() {
        return groupType;
    }
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    public String getStyleID() {
        return styleID;
    }
    public void setStyleID(String styleID) {
        this.styleID = styleID;
    }
    public String getZoom_overview() {
        return zoom_overview;
    }
    public void setZoom_overview(String zoom_overview) {
        this.zoom_overview = zoom_overview;
    }
    public String getIncrement() {
        return increment;
    }
    public void setIncrement(String increment) {
        this.increment = increment;
    }
    public String getBg_fill_color() {
        return bg_fill_color;
    }
    public void setBg_fill_color(String bg_fill_color) {
        this.bg_fill_color = bg_fill_color;
    }
    public String getBg_gradient_color() {
        return bg_gradient_color;
    }
    public void setBg_gradient_color(String bg_gradient_color) {
        this.bg_gradient_color = bg_gradient_color;
    }
    public String getSelect_color() {
        return select_color;
    }
    public void setSelect_color(String select_color) {
        this.select_color = select_color;
    }
    public String getNavigation_name() {
        return navigation_name;
    }
    public void setNavigation_name(String navigation_name) {
        this.navigation_name = navigation_name;
    }
    public String getTree() {
        return tree;
    }
    public void setTree(String tree) {
        this.tree = tree;
    }
    public String getLayout() {
        return layout;
    }
    public void setLayout(String layout) {
        this.layout = layout;
    }
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
