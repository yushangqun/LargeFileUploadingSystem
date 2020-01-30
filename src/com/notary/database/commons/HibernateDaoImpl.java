package com.notary.database.commons;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.notary.utils.ListUtil;

@Repository("hibernateDao")
public class HibernateDaoImpl extends HibernateDaoSupport implements
		IHibernateDao {

	@Resource(name = "sessionFactory")
	public void setMy(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void delete(Object entity) {
		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().delete(entity);
		tx.commit();
		this.getHibernateSession().close();
	}

	public void deleteAll(Collection entities) {

		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().deleteAll(entities);
		tx.commit();
		this.getHibernateSession().close();
	}

	public List find(String queryString) {
		return this.getHibernateTemplate().find(queryString);
	}

	public List find(String queryString, Object value) {
		return this.getHibernateTemplate().find(queryString, value);
	}

	public List find(String queryString, Object[] values) {
		return this.getHibernateTemplate().find(queryString, values);
	}

	public int findCount(final String queryString, String distinctField) {
		Integer amount = new Integer(0);
		int sql_from = queryString.indexOf("from");
		int sql_orderby = queryString.indexOf(" order by");
		StringBuffer countStrBuff = new StringBuffer("");
		List result;
		String countStr = "";
		if (true) {
			if (sql_orderby > 0) {
				countStrBuff.append("select count(distinct ").append(
						distinctField).append(") ").append(
						queryString.substring(sql_from, sql_orderby));
			} else {
				countStrBuff.append("select count(distinct ").append(
						distinctField).append(") ").append(
						queryString.substring(sql_from));
			}
			countStr = countStrBuff.toString();
		}

		result = this.getHibernateTemplate().find(countStr);
		if (!result.isEmpty()) {
			amount = Integer.parseInt(result.get(0).toString());
		} else
			return 0;
		return amount.intValue();
	}

	public int findCount(final String queryString) {
		Integer amount = new Integer(0);
		int sql_from = queryString.indexOf("from");
		int sql_orderby = queryString.indexOf(" order by");
		StringBuffer countStrBuff = new StringBuffer("");
		if (sql_orderby > 0) {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from, sql_orderby));
		} else {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from));
		}
		final String countStr = countStrBuff.toString();
		List result = this.getHibernateTemplate().find(countStr);
		if (!result.isEmpty()) {
			amount = Integer.parseInt(result.get(0).toString());
		} else
			return 0;
		return amount.intValue();
	}

	public int findCount(final String queryString, Object value) {
		Integer amount = new Integer(0);
		int sql_from = queryString.indexOf("from");
		int sql_orderby = queryString.indexOf(" order by");
		StringBuffer countStrBuff = new StringBuffer("");
		if (sql_orderby > 0) {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from, sql_orderby));
		} else {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from));
		}
		final String countStr = countStrBuff.toString();
		List result = this.getHibernateTemplate().find(countStr, value);
		if (!result.isEmpty()) {
			amount = Integer.parseInt(result.get(0).toString());
		} else
			return 0;
		return amount.intValue();
	}

	public int findCount(final String queryString, Object[] values) {
		Integer amount = new Integer(0);
		int sql_from = queryString.indexOf("from");
		int sql_orderby = queryString.indexOf(" order by");
		StringBuffer countStrBuff = new StringBuffer("");
		if (sql_orderby > 0) {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from, sql_orderby));
		} else {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from));
		}
		final String countStr = countStrBuff.toString();
		List result = this.getHibernateTemplate().find(countStr, values);
		if (!result.isEmpty()) {
			amount = Integer.parseInt(result.get(0).toString());
		} else
			return 0;
		return amount.intValue();
	}

	public List findAPage(final String queryString, final int firstResult,
			final int maxResults) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setMaxResults(maxResults);
				query.setFirstResult(firstResult);
				List resultList = query.list();
				session.flush();
				if (null != session)
					session.close();
				return resultList;
			}

		});
	}

	public List findAPage(final String queryString, final Object value,
			final int firstResult, final int maxResults) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setParameter(0, value);
				query.setMaxResults(maxResults);
				query.setFirstResult(firstResult);
				List resultList = query.list();
				session.flush();
				if (null != session)
					session.close();
				return resultList;
			}

		});
	}

	public List findAPage(final String queryString, final Object[] values,
			final int firstResult, final int maxResults) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				query.setMaxResults(maxResults);
				query.setFirstResult(firstResult);
				List resultList = query.list();
				session.flush();
				if (null != session)
					session.close();
				return resultList;
			}

		});
	}

	public List findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return this.getHibernateTemplate().findByNamedParam(queryString,
				paramNames, values);
	}

	public List findByNamedQuery(String queryName) {
		return this.getHibernateTemplate().findByNamedQuery(queryName);
	}

	public List findByNamedQuery(String queryName, Object value) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	public List findByNamedQuery(String queryName, Object[] values) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public List findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
	}

	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramName, value);
	}

	public List findByNamedQueryAndValueBean(String queryName, Object valueBean) {
		return this.getHibernateTemplate().findByNamedQueryAndValueBean(
				queryName, valueBean);
	}

	public List findByValueBean(String queryString, Object valueBean) {
		return this.getHibernateTemplate().findByValueBean(queryString,
				valueBean);
	}

	public Object get(Class entityClass, Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public Iterator iterate(String queryString) {
		return this.getHibernateTemplate().iterate(queryString);
	}

	public Iterator iterate(String queryString, Object value) {
		return this.getHibernateTemplate().iterate(queryString, value);
	}

	public Iterator iterate(String queryString, Object[] values) {
		return this.getHibernateTemplate().iterate(queryString, values);
	}

	public Object load(Class entityClass, Serializable id) {
		return this.getHibernateTemplate().load(entityClass, id);
	}

	public void load(Object entity, Serializable id) {
		this.getHibernateTemplate().load(entity, id);
	}

	public List loadAll(Class entityClass) {
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	public void refresh(Object entity) {
		this.getHibernateTemplate().refresh(entity);
	}

	public Serializable save(Object entity) {
		Serializable s = null;
		Transaction tx = this.getHibernateSession().beginTransaction();
		s = this.getHibernateTemplate().save(entity);
		tx.commit();
		this.getHibernateSession().close();
		return s;
	}

	public void save(String entityName, Object entity) {
		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().save(entityName, entity);
		tx.commit();
		this.getHibernateSession().close();
	}

	public void saveOrUpdateAll(Collection entities) {
		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().saveOrUpdateAll(entities);
		tx.commit();
		// this.getHibernateSession().close();
	}

	public void saveOrUpdate(Object entity) {
		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().saveOrUpdate(entity);
		tx.commit();
		this.getHibernateSession().close();
	}

	public void update(Object entity) {
		Transaction tx = this.getHibernateSession().beginTransaction();
		this.getHibernateTemplate().update(entity);
		tx.commit();
		this.getHibernateSession().close();
	}

	public Object execute(HibernateCallback hibernateCallBack) {
		return this.getHibernateTemplate().execute(hibernateCallBack);
	}

	public int bulkUpdate(String queryString) {

		return this.getHibernateTemplate().bulkUpdate(queryString);
	}

	public int bulkUpdate(String queryString, Object value) {
		return this.getHibernateTemplate().bulkUpdate(queryString, value);
	}

	public int bulkUpdate(String queryString, Object[] values) {
		return this.getHibernateTemplate().bulkUpdate(queryString, values);
	}

	public Object merge(Object entity) {
		return this.getHibernateTemplate().merge(entity);
	}

	public Object merge(String entityName, Object entity) {
		return this.getHibernateTemplate().merge(entityName, entity);
	}

	public int findCountBySql(String queryString, Object[] o) {
		Integer amount = new Integer(0);
		int sql_from = queryString.indexOf("from");
		int sql_orderby = queryString.indexOf(" order by");
		StringBuffer countStrBuff = new StringBuffer("");
		if (sql_orderby > 0) {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from, sql_orderby));
		} else {
			countStrBuff.append("select count(*) ").append(
					queryString.substring(sql_from));
		}
		// final String countStr = countStrBuff.toString( );
		SQLQuery query = this.getSession().createSQLQuery(
				MessageFormat.format(queryString, o));
		List result = query.list();
		if (!result.isEmpty()) {
			amount = Integer.parseInt(result.get(0).toString());
		} else
			return 0;
		return amount.intValue();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(final DetachedCriteria criteria) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria executableCriteria = criteria
						.getExecutableCriteria(session);
				executableCriteria.setCacheable(true);
				List list = executableCriteria.list();
				session.flush();
				session.close();
				return ListUtil.convert(list);
			}

		});
	}

	public int findCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		int resultCount = (Integer) criteria.getExecutableCriteria(
				this.getSession()).uniqueResult();
		this.getSession().flush();
		// this.releaseSession(this.getSession());
		return resultCount;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(final DetachedCriteria criteria,
			final int firstResult, final int pageCount) {

		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria executableCriteria = criteria
						.getExecutableCriteria(session);
				executableCriteria.setFirstResult(firstResult);
				executableCriteria.setMaxResults(pageCount);
				executableCriteria.setCacheable(true);
				List list = executableCriteria.list();
				session.flush();
				session.close();
				return ListUtil.convert(list);
			}

		});
	}

	public static HibernateDaoImpl getFromApplicationContext(
			ApplicationContext ctx) {
		return (HibernateDaoImpl) ctx.getBean("BaseDAO");
	}

	public Session getHibernateSession() {
		return super.getSession();
	}

	public void insertDataBatch(final List<Object> result) {
		this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Transaction tx = session.beginTransaction();

				for (int i = 0; i < result.size(); i++) {
					@SuppressWarnings("unused")
					Object obj = result.get(i);
					// TbSalesNumberLog tbSalesNumberLog = new TbSalesNumberLog(
					// obj.toString());
					// session.save(tbSalesNumberLog);
					if (i % 20 == 0) {
						// 20，与JDBC批量设置相同
						// 将本批插入的对象立即写入数据库并释放内存
						session.flush();
						session.clear();
					}

					obj = null;
					// tbSalesNumberLog = null;
				}
				tx.commit();
				session.close();

				return null;
			}
		});
	}

}
