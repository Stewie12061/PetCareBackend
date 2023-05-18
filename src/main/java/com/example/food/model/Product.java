package com.example.food.model;

import com.example.food.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String image;
	private String name;
	private String color;
	private Double price;
	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
}
