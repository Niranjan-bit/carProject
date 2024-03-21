package com.myproject.beststore.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.myproject.beststore.model.Product;
import com.myproject.beststore.service.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

@Controller
public class ViewPageController {

    @Autowired
    private ProductRepo repo;

 @GetMapping("/view")
    public String viewPage(Model model) {
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("products", products);
        return "products/view";
        }
 @GetMapping("/delete")
	public String deleteProduct(
			@RequestParam int id
			) {
		
		try{
			Product product = repo.findById(id).get();
			repo.delete(product);
		}catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());	
		}
		
		return "redirect:/products";
	}
 
}
