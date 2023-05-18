package com.example.food.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.food.exception.ProductException;
import com.example.food.firesbaseservice.FirebaseService;
import com.example.food.model.Product;
import com.example.food.model.ProductRequest;
import com.example.food.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		return productRepository.findProductById(id)
				.orElseThrow(() -> 
				new ProductException(String.format("Product with id %s couldn't be found", id)));
		
	}

	public List<Product> getProductByName(String name) {
		return  productRepository.findAllByName(name)
				.orElseThrow(() -> new ProductException("No Products found with common name = " + name));
		
	}
	
	public List<Product> getProductByResType(String resType) {
		return  productRepository.findAllByResType(resType)
				.orElseThrow(() -> new ProductException("No Products found with type = " + resType));
		
	}
	
	public List<Product> getProductByRating(String rating) {
		return  productRepository.findAllByRating(rating)
				.orElseThrow(() -> new ProductException("No Products found with rating > " + rating));
		
	}
	
	public List<Product> getProductByPrice(String price) {
		return  productRepository.findAllByPrice(price)
				.orElseThrow(() -> new ProductException("No Products found with price < " + price));
		
	}
	
	
}