package com.myproject.beststore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="products")  
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String brand;
	private String name;
	private double price;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	private String image_file_name;
	
	@ManyToOne
	private User purchaser;
	public User getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(User purchaser) {
		this.purchaser = purchaser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getImageFileName() {
		return image_file_name;
	}
	public void setImageFileName(String imageFileName) {
		this.image_file_name = imageFileName;
	}
}
