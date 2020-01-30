package com.notary.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.notary.database.mapping.SysUser;
import com.notary.web.service.IUserService;

@Controller
public class AccountController {

	@Autowired
	private IUserService sysUserService;
	
	@RequestMapping("/listAccount.html")
	public String listAccount(Model theModel, HttpServletRequest request){
		//get the session
		HttpSession session = request.getSession();
		
		if (session.getAttribute("account") == null) {
			return "redirect:/showFormForLogin.html";
		}
		
		//get the account from the session
		String account = session.getAttribute("account").toString();
		//get all the files associated with the account.
		List<SysUser> theAccounts = sysUserService.getSysUsers();

		theModel.addAttribute("accounts", theAccounts);

		return "pages/list-account";
	}
	
	@RequestMapping("/showFormForAccountCreate.html")
	public String showFormForAccountCreate(Model theModel) {
		
		SysUser sysUser = new SysUser();
		
		theModel.addAttribute("sysUser", sysUser);
		
		return "pages/account-form-create";
	}
	
	@RequestMapping("/showFormForAccountUpdate")
	public String updateUser(@RequestParam("id") int theId, Model theModel) 
	{
		//get the file from service

		SysUser theUser = sysUserService.getSysUserById(theId);
		
		//set file as a model attribute to pre-populate the form
		theModel.addAttribute("sysUser", theUser);
		
		
		//send over to our form
		
		return "pages/account-form-edit";
	}
	
	//when user hit the submit button at account-form-edit
	@RequestMapping("/submitEditedAccounte")
	public String createAccount(@ModelAttribute("sysUser") SysUser sysUser, HttpServletRequest request) {
		
		//get the session
		HttpSession session = request.getSession();
		
		sysUserService.saveAccount(sysUser);
		
		return "redirect:/listAccount.html";
	}
	
	//when user hit the create button at account-form
	@RequestMapping("/createNewAccount.html")
	public String createNewAccount(@ModelAttribute("sysUser") SysUser sysUser, HttpServletRequest request) {
		
		//get the session
		HttpSession session = request.getSession();
		
		String accountName = sysUserService.getSysUser(sysUser.getAccount()).getAccount();
		
		//if the account has already been registered. then don't create new one.
		if(accountName!=null) {
			
			return "redirect:/listAccount.html";
		}
		
		sysUserService.saveAccount(sysUser);
		
		return "redirect:/listAccount.html";
	}
	
	@RequestMapping("/deleteAccount.html")
	public String deleteAccount(@RequestParam("id") int theId, Model theModel) {
		
		
		SysUser theUser = sysUserService.getSysUserById(theId);
		

		
		sysUserService.deleteAccount(theUser);
		
		return "redirect:/listAccount.html";
	}
}
