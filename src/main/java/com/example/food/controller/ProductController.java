package com.example.food.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam("query") String query) {
		// Call the ProductService to search for products based on the query
		return ProductService.searchProducts(query);
	}

	@GetMapping("/dog/category/{categoryId}")
	public ResponseEntity<List<Product>> getDogProductsByCategoryId(@PathVariable Long categoryId) {
		List<Product> product = ProductService.getDogProductsByCategoryId(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	@GetMapping("/cat/category/{categoryId}")
	public ResponseEntity<List<Product>> getCatProductsByCategoryId(@PathVariable Long categoryId) {
		List<Product> product = ProductService.getCatProductsByCategoryId(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
		List<Product> product = ProductService.getProductsByCategoryId(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	 // Get All Product
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> products = ProductService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addProducts(@RequestBody List<Product> products) {
		ProductService.addProducts(products);
		return ResponseEntity.status(HttpStatus.OK).body("Products added successfully");
	}
	
	// Get Product By id
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Product product = ProductService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	// Get Products By Name
	@GetMapping("/name={name}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("name") String name) {
		List<Product> products = ProductService.getProductByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	// Get Products By Price
	@GetMapping("/price={price}")
	public ResponseEntity<List<Product>> getProductByPrice(@PathVariable("price") String price) {
		List<Product> products = ProductService.getProductByPrice(price);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}

}
