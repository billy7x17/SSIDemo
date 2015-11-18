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
package com.cloudmaster.cmp.system.systemparmeter.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:kang-b@neusoft.com"> kangbo </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SystemParameterService extends OperationLogAction {
    

    /**
     * 
     */
    private static final long serialVersionUID = -7987833659990974988L;

    /**
     * 结果列表
     */
    private List<SystemParameterDomain> systemParamList = new ArrayList<SystemParameterDomain>();

    /**
     * 系统参数实例
     */
    private SystemParameterDomain domain = new SystemParameterDomain();

    private Map<String, Object> map;

    /**
     * ibatis 空间名
     */
    private static String IBATIS_NAMESPACE = "systemparameter";

    private static LogService logger = LogService.getLogger(SystemParameterService.class);

    /**
     * 根据分组id和系统参数key值获取系统参数
     * @param organize
     * @param key
     * @return SystemParameterServiceDomain
     * @throws Exception
     */
    public SystemParameterDomain getSystemParameterByKey(String paramKey) {
        logger.info("log.getSystemParameterByKey.begin");
        try {
            domain.setParamKey(paramKey);
            SystemParameterDomain newDomain = (SystemParameterDomain) ibatisDAO.getSingleRecord(
                    IBATIS_NAMESPACE + ".getServiceDomain", domain);
            logger.info("newDomain=" + newDomain);
            if (newDomain != null) {
                return newDomain;
            }
            logger.info("log.getSystemParameterByKey.end");
        } catch (SQLException e) {
            logger.info("log.getSystemParameterByKey.error", e);
        }
        return null;
    }

    /**
     * 根据分组id和系统参数key值获取系统参数的值
     * @param organize
     * @param key
     * @return String
     */
    public String getSystemParameterValue(String paramKey) {
        logger.info("log.getSystemParameterByKey.begin");
        String rv = "";
        SystemParameterDomain newDomain = getSystemParameterByKey(paramKey);
        logger.info("newDomain=" + newDomain);
        if (newDomain != null) {
            rv = newDomain.getParamValue();
        }
        logger.info("log.getSystemParameterByKey.end");
        return rv;
    }

    /**
     * 根据分组id,获取一组系统参数
     * @param organize 分组id
     * @return List<Map<String, Object>> public List<Map<String, Object>>
     *         getSystemParameterInMap(String organize) {
     *         logger.info("log.getSystemParameterInMap.begin"); List<Map<String, Object>> rvList =
     *         new ArrayList<Map<String, Object>>(); List<SystemParameterDomain> systemParamList =
     *         getSystemParameterByOrganize(organize); for (SystemParameterDomain domain :
     *         systemParamList) { Map<String, Object> map = new HashMap<String, Object>();
     *         map.put("Organize", domain.getOrganize()); map.put("Para_Key", domain.getParamKey());
     *         map.put("Para_Value", domain.getParamValue()); map.put("Para_Describe",
     *         domain.getDescription()); map.put("Update_Time", domain.getUpdateTime());
     *         rvList.add(map); }// end for logger.info("rvList" + rvList);
     *         logger.info("log.getSystemParameterInMap.end"); if (rvList.size() > 0) { return
     *         rvList; } else { return null; } }
     */

    /**
     * 根据分组id,获取一组系统参数
     * @param organize 分组id
     * @return List<SystemParameterServiceDomain> public List<SystemParameterDomain>
     *         getSystemParameterByOrganize(String organize) {
     *         logger.info("log.getSystemParameterByOrganize.begin"); try {
     *         domain.setOrganize(organize); systemParamList = ibatisDAO.getData(IBATIS_NAMESPACE +
     *         ".getServiceList", domain); logger.info("systemParamList=" + systemParamList); if
     *         (systemParamList.size() > 0) { return systemParamList; }
     *         logger.info("log.getSystemParameterByOrganize.end"); } catch (SQLException e) {
     *         logger.info("log.getSystemParameterByOrganize.error", e); } return null; }
     */
}
