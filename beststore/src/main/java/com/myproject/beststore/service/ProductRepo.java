
package com.myproject.beststore.service;


import org.springframework.data.jpa.repository.JpaRepository;



import com.myproject.beststore.model.Product;
import com.myproject.beststore.model.User;
// repo is responsible for creating,  writing products from db
public interface ProductRepo  extends JpaRepository<Product, Integer>{

	void save(User user);
	
	
}
