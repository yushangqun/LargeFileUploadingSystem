package com.notary.web.service;

import java.util.List;

import com.notary.database.mapping.SysUser;


public interface IUserService {
	public SysUser getSysUser(String account);

	public List<SysUser> getSysUsers();

	public SysUser getSysUserById(int theId);

	public void saveAccount(SysUser sysUser);

	public void deleteAccount(SysUser theUser);
}
