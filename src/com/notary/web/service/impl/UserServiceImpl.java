package com.notary.web.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notary.database.dao.IUserDao;
import com.notary.database.mapping.SysUser;
import com.notary.web.service.IUserService;

@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService{
	
	
	@Autowired
	private IUserDao sysUserDAO;
	
	@Override
	@Transactional
	public SysUser getSysUser(String account) {
		return sysUserDAO.getSysUser(account);
	}

	@Override
	@Transactional
	public List<SysUser> getSysUsers() {
		return sysUserDAO.getSysUsers();
	}

	@Override
	@Transactional
	public SysUser getSysUserById(int theId) {

		return sysUserDAO.getSysUserById(theId);
	}

	@Override
	@Transactional
	public void saveAccount(SysUser sysUser) {
		
		Date date = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		
		String updateDate = format.format(date);
		
		sysUser.setUpdatetime(updateDate);
		
		System.out.println(sysUser.getCreatetime());
		
		if(sysUser.getCreatetime()=="") {
			System.out.println("come here");
			sysUser.setCreatetime(updateDate);
			
		}
		
		sysUserDAO.saveAccount(sysUser);
		
	}

	@Override
	@Transactional
	public void deleteAccount(SysUser theUser) {
		// TODO Auto-generated method stub
		sysUserDAO.deleteAccount(theUser);
	}
}
