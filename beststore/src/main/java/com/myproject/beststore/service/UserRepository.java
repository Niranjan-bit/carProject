package com.myproject.beststore.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.beststore.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
	
	public User findByUsernameAndPassword(String username, String password);

	User findByUsername(String name);
}
