package com.notary.web.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.notary.constants.Constants;
import com.notary.database.dao.IUploadDetailsDao;
import com.notary.database.dao.IUserDao;
import com.notary.database.mapping.FileUpload;
import com.notary.utils.PropertiesUtil;
import com.notary.web.pojo.OperateResult;
import com.notary.web.service.IUploadDetailsService;

@Repository("UploadDetailsServiceImpl")
public class UploadDetailsServiceImpl implements IUploadDetailsService {

	@Autowired
	@Qualifier("UploadDetailsDaoImpl")
	private IUploadDetailsDao uploadDetailsDaoImpl;
	
	@Autowired
	private IUserDao sysUserDAO;
	

	@Override
	public OperateResult insertFileUploadDetails(FileUpload fileUpload) {
		OperateResult operateResult = new OperateResult();
		int id = uploadDetailsDaoImpl.addFileUploadDetails(fileUpload);
		if (id != 0) {
			operateResult.setOperateCode("insert.success");
			operateResult.setDesc(PropertiesUtil.getValue(
					Constants.MESSAGE_PROPERTY_PATH, "insert.success"));
		} else {
			operateResult.setOperateCode("insert.failed");
			operateResult.setDesc(PropertiesUtil.getValue(
					Constants.MESSAGE_PROPERTY_PATH, "insert.failed"));
		}
		return operateResult;
	}


	@Override
	@Transactional
	public List<FileUpload> getFileUploads() {
		
		return uploadDetailsDaoImpl.getFileUploads();
	}


	@Override
	@Transactional
	public List getFileUploadsByAccount(String account) {
		//get the sysuser's id via account.
		int sysUserId= sysUserDAO.getSysUser(account).getId();
		
		return uploadDetailsDaoImpl.getFileUploadsBySysUserId(sysUserId);
	}


	@Override
	@Transactional
	public void saveFile(FileUpload fileUpload) {
		fileUpload.setFilePath("default");
		
		fileUpload.setSize(1000);
		
		Date date = new Date();
		
		fileUpload.setUploadDate(date.toString());
		
		uploadDetailsDaoImpl.saveFile(fileUpload);
	}


	@Override
	@Transactional
	public FileUpload getFileUpload(int theId) {
		return uploadDetailsDaoImpl.getFileUpload(theId);
	}


	@Override
	@Transactional
	public void deleteFile(int theId) {
		uploadDetailsDaoImpl.deleteFile(theId);
		
	}
	@Override
	@Transactional
	public List getFileUploadWithUser(){
		
		return uploadDetailsDaoImpl.getFileUploadWithUser();
	}


	@Override
	@Transactional
	public List getFileUploadsByDepartment(String department) {
		// TODO Auto-generated method stub
		return uploadDetailsDaoImpl.getFileUploadsByDepartment(department);
	}
	
	
	
}
