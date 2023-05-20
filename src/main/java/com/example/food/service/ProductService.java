package com.example.food.service;

import java.io.IOException;
import java.util.List;

import com.example.food.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.exception.ProductException;
import com.example.food.model.Product;
import com.example.food.model.ProductRequest;
import com.example.food.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	public Product getProductById(int id) {
		return productRepository.findProductById(id)
				.orElseThrow(() -> 
				new ProductException(String.format("Product with id %s couldn't be found", id)));
		
	}

	public List<Product> searchProducts(String query) {
		return productRepository.findByNameStartingWithIgnoreCase(query);
	}

	public List<Product> getProductByName(String name) {
		return  productRepository.findAllByName(name)
				.orElseThrow(() -> new ProductException("No Products found with common name = " + name));
		
	}
	public void addProducts(List<Product> products) {
		productRepository.saveAll(products);
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

	public List<Product> getDogProductsByCategoryId(Long categoryId) {
		return productRepository.findDogByCategory(categoryId)
				.orElseThrow(() -> new ProductException("No Product"));
	}
	public List<Product> getCatProductsByCategoryId(Long categoryId) {
		return productRepository.findCatByCategory(categoryId)
				.orElseThrow(() -> new ProductException("No Product"));
	}
	public List<Product> getProductsByCategoryId(Long categoryId) {
		return productRepository.findByCategory(categoryId)
				.orElseThrow(() -> new ProductException("No Product"));
	}
	
}
