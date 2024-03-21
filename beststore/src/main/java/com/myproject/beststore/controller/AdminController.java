package com.myproject.beststore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.beststore.model.Admin;
import com.myproject.beststore.model.User;
import com.myproject.beststore.service.AdminRepository;


@Controller
public class AdminController {
	@Autowired
	private AdminRepository repo;

    @GetMapping("/")
    public String showLoginForm() {
        return "/products/login"; // Points to login.html
    }
   
    
    @GetMapping("/adminlogin")
    public String loginPage() {
    	return "/products/adminLogin";
    }
   
    
    @PostMapping("/adminlogin")
    public String login(@RequestParam("username") String username, @RequestParam("password")String password,Model m) {
    	Admin a=repo.findByUsernameAndPassword(username, password);
    	if(a!=null) {
    		return "/products/index";    				}
    	else {
    		m.addAttribute("status","Username not found!");
    		return "/products/adminLogin";
    	}	
    }  
}





