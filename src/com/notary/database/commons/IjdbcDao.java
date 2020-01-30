package com.notary.database.commons;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface IjdbcDao {

	/**
	 * 无参数查�?
	 * 
	 * @param sql
	 * @return
	 */
	public abstract void delete(String sql);

	/**
	 * 带参数删�?
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract int delete(String sql, Object[] params);

	/**
	 * 带参数批量更�?大批�?
	 * @param sql   预编译sql 占位�?�?
	 * @param list1  参数值集�?
	 * @param sqlTypes  参数对应的java.sql.Types
	 * @return
	 */
	public abstract int batchSqlUpdate(String sql, List<Object> list,
			int[] sqlTypes);

	/**
	 * 无参修改
	 * @param sql
	 * @return
	 */
	public abstract int update(String sql);

	/**
	 * 带参数修�?
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract int update(String sql, Object[] params);

	/**
	 * 无参新增
	 * @param sql
	 */
	public abstract int save(String sql);

	/**
	 * 带参新增
	 * @param sql
	 * @param params
	 */
	public abstract int save(String sql, Object[] params);

	/**
	 * 无参数查�?
	 * 
	 * @param sql
	 * @return
	 */
	
	public abstract SqlRowSet queryList(String sql);

	/**
	 * 带参数查�?
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract SqlRowSet queryList(String sql, Object[] params);
	
	
	/**
	 * 带参数查�?for mysql
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract SqlRowSet queryList_mysql(String sql, Object[] params);

	/**
	 * 无参查询总页�?
	 * @param sql
	 * @return
	 */
	public abstract int queryCount(String sql);

	/**
	 * 带参查询总页�?
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract int queryCount(String sql, Object[] params);

	/**
	 * 无参分页查询
	 * @param sql
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	
	public abstract SqlRowSet queryList(String sql, int firstResult, int maxResult);

	/**
	 * 带参分页查询
	 * @param sql
	 * @param params
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	
	public abstract SqlRowSet queryList(String sql, Object[] params,
			int firstResult, int maxResult);
	
	
	/**
	 * 带参分页查询for mysql
	 * @param sql
	 * @param params
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public SqlRowSet queryList_mysql(String sql, Object[] params, 
			int firstResult, int maxResult);
	
	@SuppressWarnings("rawtypes")
	public List find( String sql );
    
    
    public void call(String sql, final Object[] params, final Object[] outParams);

}