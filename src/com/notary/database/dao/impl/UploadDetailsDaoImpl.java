package com.notary.database.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.notary.database.commons.IHibernateDao;
import com.notary.database.dao.IUploadDetailsDao;
import com.notary.database.mapping.FileUpload;

@Repository("UploadDetailsDaoImpl")
public class UploadDetailsDaoImpl implements IUploadDetailsDao {

	@Autowired
	@Qualifier("hibernateDao")
	private IHibernateDao hibernateDao;
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public int addFileUploadDetails(FileUpload fileUpload) {
		hibernateDao.save(fileUpload);
		return fileUpload.toString().length();
	}

	@Override
	public List<FileUpload> getFileUploads() {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		String hql = "FROM FileUpload WHERE userId = '1'";
		
		Query query = currentSession.createQuery(hql);
		
		List<FileUpload> result = query.list();
		
		return result;
	}



	@Override
	public void saveFile(FileUpload fileUpload) {

		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		
		//save or update the customer depending on if the primary key has already existed.
		currentSession.saveOrUpdate(fileUpload);
	}



	@Override
	public FileUpload getFileUpload(int theId) {
		//get current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//retrieve file from database using primary key
		FileUpload fileUpload = (FileUpload)currentSession.get(FileUpload.class, theId);
		
		return fileUpload;
	}



	@Override
	public void deleteFile(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = 
				currentSession.createQuery("delete from FileUpload where id=:fileId");
		theQuery.setParameter("fileId", theId);
		
		theQuery.executeUpdate();
	}



	@Override
	public List<FileUpload> getFileUploadsBySysUserId(int theId) {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		//String hql = "FROM FileUpload WHERE userId = '"+ theId + "'";
		
		String hql="select f from FileUpload f inner join f.sysUser WHERE userId = '" + theId +"'";
		
		Query query = currentSession.createQuery(hql);
		
		List result = query.list();
		
		return result;
	}
	
	@Override
	public List getFileUploadWithUser(){
		Session currentSession = sessionFactory.getCurrentSession();
		
		String hql="select f from FileUpload f inner join f.sysUser";
		
		Query query = currentSession.createQuery(hql);
		
		return query.list();
		
	}

	@Override
	public List getFileUploadsByDepartment(String department) {
		
		Session currentSession = sessionFactory.getCurrentSession();
				
		String hql="select f from FileUpload f inner join f.sysUser WHERE userId in (select id from SysUser WHERE departmentName = '"+ department + "')";
		
		Query query = currentSession.createQuery(hql);
		
		return query.list();
	}
	
	
	
}
