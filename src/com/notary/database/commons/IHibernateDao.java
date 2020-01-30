package com.notary.database.commons;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
public interface IHibernateDao
{

    public void delete( Object entity );

    public void deleteAll( Collection entities );

    public List find( String queryString );

    public List find( String queryString,Object value );

    public List find( String queryString,Object[] values );

    public int findCount( final String queryString,String distinctField );

    public int findCount( final String queryString );

    public int findCount( final String queryString,Object value );

    public int findCount( final String queryString,Object[] values );

    public List findAPage( final String queryString,final int firstResult,final int maxResults );

    public List findAPage( final String queryString,final Object value,final int firstResult,final int maxResults );

    public List findAPage( final String queryString,final Object[] values,final int firstResult,final int maxResults );

    public List findByNamedParam( String queryString,String[] paramNames,Object[] values );

    public List findByNamedQuery( String queryName );

    public List findByNamedQuery( String queryName,Object value );

    public List findByNamedQuery( String queryName,Object[] values );

    public List findByNamedQueryAndNamedParam( String queryName,String[] paramNames,Object[] values );

    public List findByNamedQueryAndNamedParam( String queryName,String paramName,Object value );

    public List findByNamedQueryAndValueBean( String queryName,Object valueBean );

    public List findByValueBean( String queryString,Object valueBean );

    public Object get( Class entityClass, Serializable id );

    public Iterator iterate( String queryString );

    public Iterator iterate( String queryString,Object value );

    public Iterator iterate( String queryString,Object[] values );

    public Object load( Class entityClass,Serializable id );

    public void load( Object entity,Serializable id );

    public List loadAll( Class entityClass );

    public void refresh( Object entity );

    public Serializable save( Object entity );

    public void save( String entityName,Object entity );

    public void saveOrUpdateAll( Collection entities );

    public void saveOrUpdate( Object entity );

    public void update( Object entity );

    public Object execute( HibernateCallback hibernateCallBack );

    public int bulkUpdate( String queryString );

    public int bulkUpdate( String queryString,Object value );

    public int bulkUpdate( String queryString,Object[] values );

    public Object merge( Object entity );

    public Object merge( String entityName,Object entity );

    int findCountBySql(String sql, Object[] o);

    <T> List<T> find(DetachedCriteria criteria);

    int findCount(DetachedCriteria criteria);

    <T> List<T> find(DetachedCriteria criteria, int firstResult, int pageCount);

    Session getHibernateSession();
    
    public void insertDataBatch(final List<Object> result);
}
