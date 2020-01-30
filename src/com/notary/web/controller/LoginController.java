package com.notary.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.notary.database.mapping.SysUser;
import com.notary.web.service.IUserService;


@Controller
public class LoginController {
	
	@Autowired
	private IUserService sysUserService;

	@RequestMapping("/showFormForLogin.html")
	public String showFormForLogin(Model theModel){

		SysUser theUser = new SysUser();
		
		theModel.addAttribute("sysUser", theUser);
		
		return "pages/login-form";
	}
	
	@RequestMapping("/login.html")
	public String login(@ModelAttribute("sysUser") SysUser sysUser, HttpServletRequest request, Model theModel) {
		
		SysUser user = sysUserService.getSysUser(sysUser.getAccount());
		//if user use the proper username and correct password.
		if(sysUser.getAccount().equals(sysUserService.getSysUser(sysUser.getAccount()).getAccount())
				&&sysUser.getPassword().equals(sysUserService.getSysUser(sysUser.getAccount()).getPassword())) 
		{
			HttpSession session = request.getSession();
			session.setAttribute("account", sysUser.getAccount());
		
			session.setAttribute("userRight", user.getUserRight());
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("departmentName", user.getDepartmentName());
			
			return "redirect:/list.html";
		}
		else 
		{
			String error = "Error";
			HttpSession session = request.getSession();
			session.setAttribute("error", error);

			return "redirect:/showFormForLogin.html";
		}
	}
	
	@RequestMapping("/logOut.html")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		session.invalidate();
		return "redirect:/showFormForLogin.html";
	}
	
}
