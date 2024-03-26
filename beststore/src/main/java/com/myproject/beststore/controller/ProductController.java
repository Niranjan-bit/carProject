	
	package com.myproject.beststore.controller;
	
	import java.io.File;
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.Paths;
	import java.security.Principal;
	import java.util.List;
	import java.util.UUID;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.domain.Sort;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.multipart.MultipartFile;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	
	import com.myproject.beststore.model.Product;
	import com.myproject.beststore.model.ProductDTO;
	import com.myproject.beststore.service.ProductRepo;
	
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.validation.Valid;
	
	@Controller
	@RequestMapping("/products")
	public class ProductController {
		
		@Autowired
		private ProductRepo repo;
		
		@GetMapping({"", "/"})    // it'll get the data of cars
		public String showProductList(Model model) {
			List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
			model.addAttribute("products", products);
			return "products/index";
		}	
		
		@GetMapping("/create")  // displays the form that allows user to add cars
		public String showCreatePage(Model model) {
			ProductDTO productDto = new ProductDTO();
			model.addAttribute("productDto",productDto);
			return "products/CreateProduct";
		}
	
		@PostMapping("/create")
		public String createProduct(
		        @Valid @ModelAttribute ProductDTO productDto,
		        BindingResult result,
		        @RequestParam("imageFile") MultipartFile imageFile,
		        RedirectAttributes redirectAttributes
		) {
		    if (result.hasErrors()) {
		        return "redirect:/products/create";
		    }
	
		    Product product = new Product();
		    product.setName(productDto.getName());
		    product.setBrand(productDto.getBrand());
		    product.setPrice(productDto.getPrice());
		    product.setDescription(productDto.getDescription());
	
		    // Save image file
		    try {
		        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
		        Files.write(Paths.get("/path/to/save/images/" + fileName), imageFile.getBytes());
		        product.setImageFileName(fileName);
		    } catch (IOException e) {
		        e.printStackTrace(); // Handle file upload error
		    }
	
		    repo.save(product);
	
		    return "redirect:/products";
		}
	
		
		
		@GetMapping("/edit")
		public String showEditPage(Model model, @RequestParam int id) {
		    try {
		        Product product = repo.findById(id).orElse(null);
		        if (product == null) {
		            // Handle product not found
		            return "redirect:/products";
		        }
		        
		        // Populate productDto with values from the retrieved Product entity
		        ProductDTO productDto = new ProductDTO();
		        productDto.setName(product.getName());
		        productDto.setBrand(product.getBrand());
		        productDto.setPrice(product.getPrice());
		        productDto.setDescription(product.getDescription());
		        
		        model.addAttribute("product", product);
		        model.addAttribute("productDto", productDto);
		    } catch (Exception ex) {
		        System.out.println("Exception: " + ex.getMessage());
		        return "redirect:/products";
		    }
		    
		    return "products/EditProduct";
		}
	
	
		@PostMapping("/edit")
		public String updateProduct(
		        @RequestParam int id,
		        @Valid @ModelAttribute ProductDTO productDto,
		        BindingResult result,
		        RedirectAttributes redirectAttributes
		) {
		    if (result.hasErrors()) {
		        // If there are validation errors, return to the edit page with the same form
		        return "products/EditProduct";
		    }
	
		    try {
		        // Find the product by its ID
		        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
	
		        // Update the product details with the values from the form
		        product.setName(productDto.getName());
		        product.setBrand(productDto.getBrand());
		        product.setPrice(productDto.getPrice());
		        product.setDescription(productDto.getDescription());
	
		        // Save the updated product
		        repo.save(product);
	
		        // Add a flash attribute for displaying a success message
		        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully");
		    } catch (Exception ex) {
		        // Handle any exceptions, e.g., database errors
		        System.out.println("Exception: " + ex.getMessage());
		        // Add a flash attribute for displaying an error message
		        redirectAttributes.addFlashAttribute("errorMessage", "Failed to update product");
		    }
	
		    // Redirect to the product list page after successful update or in case of error
		    return "redirect:/products";
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
		
		
		@GetMapping("/purchase")
		public String purchasePage(Model model, @RequestParam int id) {
		    try {
		        Product product = repo.findById(id).orElse(null);
		        if (product == null) {
		            return "redirect:/products";
		        }
		        model.addAttribute("product", product);
		    } catch (Exception ex) {
		        System.out.println("Exception: " + ex.getMessage());
		        return "redirect:/products";
		    }
		    return "products/purchase";
		}
		
		
		@GetMapping("/purchasedetails")
		public String purchaseDetails() {
		    return "products/purchasedetails";
		}
	
	
	
		@PostMapping("/confirm-purchase")
	    public String confirmPurchase(@RequestParam int id, RedirectAttributes redirectAttributes) {
	        try {
	            Product product = repo.findById(id).orElse(null);
	            if (product != null) {
	                repo.delete(product);
	                redirectAttributes.addFlashAttribute("successMessage", "Product purchased successfully");
	            } else {
	                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
	            }
	        } catch (Exception ex) {
	            System.out.println("Exception: " + ex.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "Failed to purchase product");
	        }
	        return "redirect:/view";
	    }
	}
