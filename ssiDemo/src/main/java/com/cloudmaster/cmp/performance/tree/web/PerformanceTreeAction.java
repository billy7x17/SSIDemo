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
package com.cloudmaster.cmp.performance.tree.web;
import com.cloudmaster.cmp.web.BaseAction;
/**
 * @author <a href="mailto:na.x@neusoft.com">naxu</a>
 * @version 1.0.0 18 Mar 2012
 */
public class PerformanceTreeAction extends BaseAction {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     *forward
     */
    private String forward = SUCCESS;

    public String perfomanceTree() {
        return forward;
    }
}
