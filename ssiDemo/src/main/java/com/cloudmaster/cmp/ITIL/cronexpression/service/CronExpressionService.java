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
package com.cloudmaster.cmp.ITIL.cronexpression.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.neusoft.mid.enzyme.service.BaseService;
import com.neusoft.mid.enzyme.service.ServiceException;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:na.x@neusoft.com"> na.x </a>
 * @version 1.0.0 18 Mar 2012
 */
public class CronExpressionService implements BaseService {

    private static LogService logger = LogService.getLogger(CronExpressionService.class);

    private IbatisDAO ibatisDAO;

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(Map<String, Object> serviceMap) throws ServiceException, Exception {
        try {
            List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
            reList = ibatisDAO.getData("cronExpressionManager.getTaskListForLoader", null);
            return reList;
        } catch (Exception e) {
            logger.info("加载任务管理数据异常", e);
            throw new ServiceException();
        }
    }

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
