package com.example.food.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.model.Product;
import com.example.food.model.ProductRequest;
import com.example.food.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
public class ProductController {
	
	private final ProductService ProductService;
	
	 private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	 // Get All Product
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> products = ProductService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	
	// Get Product By id
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		Product product = ProductService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	// Get Products By Name
	@GetMapping("/name={name}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("name") String name) {
		List<Product> products = ProductService.getProductByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	// Get Products By Type
	@GetMapping("/resType={resType}")
	public ResponseEntity<List<Product>> getProductByResType(@PathVariable("resType") String resType) {
		List<Product> products = ProductService.getProductByResType(resType);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	// Get Products By Rating
	@GetMapping("/rating={rating}")
	public ResponseEntity<List<Product>> getProductByRating(@PathVariable("rating") String rating) {
		List<Product> products = ProductService.getProductByRating(rating);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	// Get Products By Price
	@GetMapping("/price={price}")
	public ResponseEntity<List<Product>> getProductByPrice(@PathVariable("price") String price) {
		List<Product> products = ProductService.getProductByPrice(price);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

}
