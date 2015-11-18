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

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 节点工具类
 * @author <a href="mailto:jiangxuan@neusoft.com">jiang xuan </a>
 * @version 1.0.0 18 Mar 2012
 */
public final class NodeManager {

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(NodeManager.class);

    /**
     * 构造函数
     */
    private NodeManager() {
    }

    /**
     * 根据子节点向上寻找父节点的列表，返回父节点id列表按照顺序排列
     * @param id 子节点id
     * @param ibatisDAO 数据库对象
     * @return
     */
    public static List<String> getParentIdList(String id, IbatisDAO ibatisDAO) {
        List<String> parnetIds = new ArrayList<String>();
        if (null != id) {
            parnetIds.add(0, id);
            if (id.startsWith("-")) {
                return parnetIds;
            }
        } else {
            return null;
        }
        while (true) {
            String pid = null;
            try {
                pid = (String) ibatisDAO.getSingleRecord("knowledgeManager.getParentId", id);
            } catch (Exception e) {
                logger.debug("获取节点ID为" + id + "的父节点时出现异常！");
                return null;
            }
            if (null != pid) {
                if (pid.startsWith("-")) {
                    break;
                }
                parnetIds.add(0, pid);
                id = pid;
            } else {
                break;
            }
        }
        return parnetIds;
    }

    /**
     * 返回父节点id的字符串，个id之间以“；”相隔
     * @param 子节点id
     * @param ibatisDAO 数据库对象
     * @return
     */
    public static String getParentIds(String id, IbatisDAO ibatisDAO) {
        String resultIds = null;
        if (null != id) {
            resultIds = id;
            if (id.startsWith("-")) {
                return id;
            }
        } else {
            return null;
        }
        while (true) {
            String pid = null;
            try {
                pid = (String) ibatisDAO.getSingleRecord("knowledgeManager.getParentId", id);
            } catch (Exception e) {
                logger.debug("获取节点ID为" + id + "的父节点时出现异常！");
                return null;
            }
            if (null != pid) {
                if (pid.startsWith("-")) {
                    break;
                }
                resultIds = pid + ";" + resultIds;
                id = pid;
            } else {
                break;
            }
        }
        return resultIds;
    }

    public void getChildIds() {

    }
}
