package com.notary.database.commons;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author shiyeming
 * 
 */
/**
 * @author shiyeming
 *
 */
@Repository("jdbcDaoImpl")
public class JdbcDaoImpl extends JdbcDaoSupport implements IjdbcDao {
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#delete(java.lang.String)
	 */
	public void delete(String sql) {
		this.getJdbcTemplate().execute(sql);
	}

	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#delete(java.lang.String, java.lang.Object[])
	 */
	public int delete(String sql, Object[] params) {
		return this.getJdbcTemplate().update(sql, params);
	}

	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#batchSqlUpdate(java.lang.String, java.util.List, int[])
	 */
	public int batchSqlUpdate(String sql, List<Object> list, int[] sqlTypes) {
		int succNum = 0;
		try {
			BatchSqlUpdate bsu = new BatchSqlUpdate(this.getDataSource(), sql);
			bsu.setBatchSize(100);
			bsu.setTypes(sqlTypes);
			for (int i = 0; i < list.size(); ++i) {
				bsu.update((list.get(i)).toString());
			}
			int result[] = bsu.flush();
			succNum = result.length;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return succNum;
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#update(java.lang.String)
	 */
	public int update(String sql){
		return this.getJdbcTemplate().update(sql);
	}
	
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#update(java.lang.String, java.lang.Object[])
	 */
	public int update(String sql,Object[] params){
		return this.getJdbcTemplate().update(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#save(java.lang.String)
	 */
	public int save(String sql){
		return this.getJdbcTemplate().update(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#save(java.lang.String, java.lang.Object[])
	 */
	public int save(String sql,Object[] params){
		return this.getJdbcTemplate().update(sql, params);
	}
	

	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryList(java.lang.String)
	 */
	public SqlRowSet queryList(String sql) {
		return this.getJdbcTemplate().queryForRowSet(sql);
	}

	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryList(java.lang.String, java.lang.Object[])
	 */
	public SqlRowSet queryList(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForRowSet(sql, params);
	}
	
	public SqlRowSet queryList_mysql(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForRowSet(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryCount(java.lang.String)
	 */
	public int queryCount(String sql){
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from (");
		query.append(sql);
		query.append(" )");
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryCount(java.lang.String, java.lang.Object[])
	 */
	public int queryCount(String sql,Object[] params){
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from (");
		query.append(sql);
		query.append(" ) m");
		return this.getJdbcTemplate().queryForInt(query.toString(), params);
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryList(java.lang.String, int, int)
	 */
	
	public SqlRowSet queryList(String sql,int firstResult,int maxResult){
		StringBuffer querySql = new StringBuffer();
		querySql.append("select t.* from ( ");
		querySql.append(sql);
		querySql.append(" ) t");
		querySql.append(" where t.row_num > " + firstResult);
		querySql.append(" and t.row_num <= " + maxResult);
//		return this.getJdbcTemplate().queryForList(querySql.toString());
		return this.getJdbcTemplate().queryForRowSet(querySql.toString());
	}
	
	/* (non-Javadoc)
	 * @see com.longcheer.dao.impl.IjdbcDao#queryList(java.lang.String, java.lang.Object[], int, int)
	 */
	
	public SqlRowSet queryList(String sql, Object[] params, int firstResult, int maxResult){
		StringBuffer querySql = new StringBuffer();
		querySql.append("select t.* from ( ");
		querySql.append(sql);
		querySql.append(" ) t");
		querySql.append(" where t.row_num > " + firstResult);
		querySql.append(" and t.row_num <= " + maxResult);
		//return this.getJdbcTemplate().queryForList(querySql.toString(),params);
		return this.getJdbcTemplate().queryForRowSet(querySql.toString(), params);
	}
	
	//for mysql
	public SqlRowSet queryList_mysql(String sql, Object[] params, int firstResult, int maxResult){
		StringBuffer querySql = new StringBuffer();
		querySql.append("select t.* from ( ");
		querySql.append(sql);
		querySql.append(" ) t");
		querySql.append(" limit " + firstResult);
		querySql.append(" , " + maxResult);
		//return this.getJdbcTemplate().queryForList(querySql.toString(),params);
		return this.getJdbcTemplate().queryForRowSet(querySql.toString(), params);
	}
	
    @SuppressWarnings("unchecked")
	public List find( String sql )
    {
        return this.getJdbcTemplate( ).queryForList( sql );
    }
    
    public void call(String sql, final Object[] params, final Object[] outParams) {
    	this.getJdbcTemplate().execute(sql, new CallableStatementCallback(){
    		public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
    			if(params != null) {
    				for(int i = 0; i < params.length; i++) {
    					cs.setObject((i+1), params[i]);
    				}
    			}
    			if(outParams != null) {
    				for(int j = 0; j < outParams.length; j++) {
    					cs.registerOutParameter((params.length + j + 1), Types.INTEGER);
    				}
    			}
    			return cs.execute();
    		}
    	});
    }
}
