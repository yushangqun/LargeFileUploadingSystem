package com.notary.database.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.notary.database.commons.IHibernateDao;
import com.notary.database.dao.IUserDao;
import com.notary.database.mapping.SysUser;


@Repository("UserDaoImpl")
public class UserDaoImpl implements IUserDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public SysUser getSysUser(String account) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		String hql = "FROM SysUser WHERE account = '" + account + "'";
		
		//execute query and get result list
		Query query = currentSession.createQuery(hql);
		// get the list of sysuser which has the same given account. 
		List<SysUser> result = query.list();
		System.out.println("the no of account is equal to");
		System.out.println(result.size());
		
		//return the results
		if(result.size()>0) 
		{
			return result.get(0);
		}
		else {
			return new SysUser();
		}
	}

	@Override
	public List<SysUser> getSysUsers() {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		String hql = "FROM SysUser";
		
		//execute query and get result list
		Query query = currentSession.createQuery(hql);
		
		// get the list of sysuser which has the same given account. 
		List<SysUser> result = query.list();
		
		
		return result;
	}

	@Override
	public SysUser getSysUserById(int theId) {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		SysUser sysUser = (SysUser)currentSession.get(SysUser.class, theId);
		
		return sysUser;
	}

	@Override
	public void saveAccount(SysUser sysUser) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(sysUser);
	}

	@Override
	public void deleteAccount(SysUser theUser) {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.delete(theUser);
	}

}
