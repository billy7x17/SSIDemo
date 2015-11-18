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
import java.io.Serializable;
import java.util.List;
/**
 * 多sheet 不定列
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
public class ResourceData implements Serializable{
    /** * */
    private static final long serialVersionUID = 1L;
    private String title;
    private String[] heads;
    private List<String[]> bodys;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String[] getHeads() {
        return heads;
    }
    public void setHeads(String[] heads) {
        this.heads = heads;
    }
    public List<String[]> getBodys() {
        return bodys;
    }
    public void setBodys(List<String[]> bodys) {
        this.bodys = bodys;
    }
}
