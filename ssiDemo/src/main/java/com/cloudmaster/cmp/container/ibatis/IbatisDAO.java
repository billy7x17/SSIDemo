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
package com.cloudmaster.cmp.container.ibatis;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * Description:数据库操作接口
 * </p>
 * @author <a href="mailto:zhaoxb@neusoft.com">xiaobin zhao</a>
 * @version 1.0.0 18 Mar 2012
 * @since Date: 2009-3-20 下午02:47:07
 */
public interface IbatisDAO {

    /**
     * 获取多行数据
     * @param statement sql 声明ID
     * @param business 输入参数
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    List getData(String statement, Object business) throws SQLException;

    /**
     * 获取单行数据
     * @param statement
     * @param business
     * @return
     * @throws SQLException
     */
    Object getSingleRecord(String statement, Object business) throws SQLException;

    /**
     * 插入单行记录
     * @param statement
     * @param business
     * @return
     * @throws SQLException
     */
    Object insertData(String statement, Object business) throws SQLException;

    /**
     * 更新单条记录
     * @param statement
     * @param business
     * @return
     * @throws SQLException
     */
    int updateData(String statement, Object business) throws SQLException;

    /**
     * 删除单条记录
     * @param statement
     * @param id
     * @return
     * @throws SQLException
     */
    int deleteData(String statement, Object id) throws SQLException;

    /**
     * 批量执行多条语句
     * @param statement
     * @param id
     * @return
     * @throws SQLException
     */
    void updateBatchData(List<BatchVO> list) throws SQLException;

    /**
     * 获取总记录数
     * @param clazz
     * @param obj
     * @return
     * @throws SQLException
     */
    int getCount(String statement, Object obj) throws SQLException;

    /**
     * 按页取记录
     * @param clazz
     * @param obj
     * @param skipResults
     * @param maxResults
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    List getObjectsByPage(String statement, Object obj, int skipResults, int maxResults)
            throws SQLException;
}
