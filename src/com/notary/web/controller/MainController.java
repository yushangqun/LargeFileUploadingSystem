package com.notary.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.notary.database.mapping.FileUpload;
import com.notary.web.service.IUploadDetailsService;
import com.notary.web.service.IUserService;

@Controller
public class MainController {
	
	@Autowired
	private IUploadDetailsService fileUploadService;
	
	@Autowired 
	private IUserService userService;
	
	@RequestMapping("/list.html")
	public String listFile(Model theModel, HttpServletRequest request){
		//get the session
		HttpSession session = request.getSession();
		//get the account from the session
		
		if (session.getAttribute("account") == null) {
			return "redirect:/showFormForLogin.html";
		}
		
		String account = session.getAttribute("account").toString();
		//get all the files associated with the account.
		
		//get user right
		int userRight=userService.getSysUser(account).getUserRight();
		
		//if userright is equal to one, it means user is admin, who is able to see all files
		if(userRight==1){
			theModel.addAttribute("files", fileUploadService.getFileUploadWithUser());
		}
		else if(userRight ==2){
			String department = userService.getSysUser(account).getDepartmentName();
			theModel.addAttribute("files", fileUploadService.getFileUploadsByDepartment(department));
		}
		else{//else user can only see his own file. 
			theModel.addAttribute("files", fileUploadService.getFileUploadsByAccount(account));
		}
		
		return "pages/list-file";
	}
	
	
	@RequestMapping("/home.html")
	public String home(){
		
		return "pages/home";
	}

}
