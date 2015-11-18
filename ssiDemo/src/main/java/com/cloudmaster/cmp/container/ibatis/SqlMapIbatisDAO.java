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
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 实现数据库存储操作
 * @author <a href="mailto:zhaoxb@neusoft.com">Xiaobin Zhao </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SqlMapIbatisDAO extends SqlMapClientDaoSupport implements IbatisDAO {

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(SqlMapIbatisDAO.class);

    @SuppressWarnings("unchecked")
    public List getData(String statement, Object business) throws SQLException {
        return (List) getSqlMapClientTemplate().queryForList(statement, business);
    }

    /*
     * (non-Javadoc)
     * @see com.neusoft.ms.persistence.iface.configIface.BusinessDAO#
     * insertBusiness(java.lang.Object)
     */
    public Object insertData(String statement, Object business) throws SQLException {
        return getSqlMapClientTemplate().insert(statement, business);

    }

    /*
     * (non-Javadoc)
     * @see com.neusoft.ms.persistence.iface.configIface.BusinessDAO#
     * updateBusiness(java.lang.Object)
     */
    public int updateData(String statement, Object business) throws SQLException {
        return getSqlMapClientTemplate().update(statement, business);

    }

    /*
     * (non-Javadoc)
     * @see com.neusoft.ms.persistence.iface.configIface.BusinessDAO#
     * deleteBusiness(java.lang.String)
     */
    public int deleteData(String statement, Object id) throws SQLException {
        return getSqlMapClientTemplate().delete(statement, id);

    }

    /*
     * (non-Javadoc)
     * @see com.neusoft.smiasms.persistence.iface.config.IbatisDAO#batchData(java.util.List)
     */
    public void updateBatchData(List<BatchVO> list) throws SQLException {
        SqlMapClient sqlMapClient = getSqlMapClient();
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("========================================updateBatchData begin,"
                + currentTimeMillis);
        try {
            sqlMapClient.startTransaction();
            for (Iterator<BatchVO> it = list.iterator(); it.hasNext();) {
                BatchVO batchVO = it.next();
                String operate = batchVO.getOperate();
                logger.info("========================================updateBatchData execsql,"
                        + currentTimeMillis + "," + operate + ",sql:" + batchVO.getString());
                if ("ADD".equals(operate)) {
                    sqlMapClient.insert(batchVO.getString(), batchVO.getObject());
                } else if ("DEL".equals(operate)) {
                    sqlMapClient.delete(batchVO.getString(), batchVO.getObject());
                } else if ("MOD".equals(operate)) {
                    if (sqlMapClient.update(batchVO.getString(), batchVO.getObject()) == 0) {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new UnsupportedOperationException("Invalidate operator '" + operate
                            + "' in batch VO");
                }
            }
            sqlMapClient.commitTransaction();
        } finally {
            sqlMapClient.endTransaction();
            logger.info("========================================updateBatchData endTransaction,"
                    + currentTimeMillis + ",time:"
                    + (System.currentTimeMillis() - currentTimeMillis) / 1000);
        }
    }

    public Object getSingleRecord(String statement, Object business) throws SQLException {
        return getSqlMapClientTemplate().queryForObject(statement, business);
    }

    @SuppressWarnings("unchecked")
    public List getObjectsByPage(String statement, Object obj, int skipResults, int maxResults) {
        return getSqlMapClientTemplate().queryForList(statement, obj, skipResults, maxResults);
    }

    public int getCount(String statement, Object obj) {
        Object result = getSqlMapClientTemplate().queryForObject(statement, obj);
        if (null == result) {
            throw new RuntimeException(
                    "The result is null.Please check the sql statement.tip:Use NVL function");
        }
        return ((Integer) result).intValue();
    }
}
