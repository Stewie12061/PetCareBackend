package com.example.food.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String image;
	private String name;
	private Double price;
	private String description;
	private String type;
	private int category_id;

}
