package com.myproject.beststore.model;



import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


public class ProductDTO {
	
	@NotEmpty(message="The brand is required")
	private String brand;
	
	@NotEmpty(message = "The name is required")
	private String name;
	
	@Min(1)
	private double price;
	
	@NotEmpty(message="The description is required")
	private String description;
		
	private MultipartFile imageFile;
	
	public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
