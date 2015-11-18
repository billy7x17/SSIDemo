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

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在销毁session时,将session中存储的文件名称所对应的文件删除
 * @author <a href="mailto:jiangxuan@neusoft.com">jiang xuan </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SessionListener implements HttpSessionListener {

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
     * .HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
     * .http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String path = (String) session.getAttribute("tempPath");
        if (null != path && !"".endsWith(path)) {
            FileUtil.delFolder(new File(path));
        }

        // 将流程图所保存路径下的文件删除
        String uploadPath = (String) session.getAttribute("uploadPath");
        if (null != uploadPath && !"".endsWith(uploadPath)) {
            FileUtil.delFolder(new File(uploadPath));
        }
    }

}
