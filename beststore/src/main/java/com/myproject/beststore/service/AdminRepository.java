package com.myproject.beststore.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.beststore.model.Admin;
import com.myproject.beststore.model.User;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	public Admin findByUsernameAndPassword(String username, String password);
}
