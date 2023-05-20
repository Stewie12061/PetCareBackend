package com.example.food.repository;

import java.util.List;
import java.util.Optional;

import com.example.food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.food.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Optional<Product> findProductById(int id);
	
	@Query(value = "SELECT * FROM product WHERE name LIKE %:name%" ,nativeQuery = true)
	Optional<List<Product>> findAllByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM product WHERE res_type LIKE %:resType%" ,nativeQuery = true)
	Optional<List<Product>> findAllByResType(@Param("resType") String resType);

	@Query(value = "SELECT * FROM product WHERE rating > ?1" ,nativeQuery = true)
	Optional<List<Product>> findAllByRating(String rating);
	
	@Query(value = "SELECT * FROM product WHERE price < ?1" ,nativeQuery = true)
	Optional<List<Product>> findAllByPrice(String price);

	@Query(value = "SELECT * FROM product WHERE category_id like %:cateId% and type = 'cat' ",nativeQuery = true)
	Optional<List<Product>> findCatByCategory(Long cateId);

	@Query(value = "SELECT * FROM product WHERE category_id like %:cateId% and type = 'dog' ",nativeQuery = true)
	Optional<List<Product>> findDogByCategory(Long cateId);

	@Query(value = "SELECT * FROM product WHERE category_id like %:cateId%",nativeQuery = true)
	Optional<List<Product>> findByCategory(Long cateId);

	List<Product> findByNameStartingWithIgnoreCase(String name);

}
