package com.example.food.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductException(String message) {
		super(message);
	}
	
}
