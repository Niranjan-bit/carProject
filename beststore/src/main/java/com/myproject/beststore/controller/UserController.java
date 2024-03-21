package com.myproject.beststore.controller;


import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.beststore.model.Admin;
import com.myproject.beststore.model.User;
import com.myproject.beststore.service.AdminRepository;
import com.myproject.beststore.service.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository repo;
	
	@GetMapping("/registration")
	public String Register() {
		return "/products/registration";
	}
	 
	@PostMapping("/reg")
	public String userRegister(@ModelAttribute User u, Model model) {
	    // Check if the email or username already exists
	    if(repo.existsByEmail(u.getEmail()) || repo.existsByUsername(u.getUsername())) {
	        model.addAttribute("error", "Email or username already exists.");
	        return "/products/registration"; // Return to the registration page with an error message
	    }
	    repo.save(u);
	    return "/products/login";
	}
    
    @PostMapping("/userlogin")
    public String login(@RequestParam("username") String username, @RequestParam("password")String password) {
    	User u=repo.findByUsernameAndPassword(username, password);
    	if(u!=null) {
    		return "redirect:/view";
    	}
    	else {
    		return "/products/login";
    	}
    	
    	
    }
 
}
