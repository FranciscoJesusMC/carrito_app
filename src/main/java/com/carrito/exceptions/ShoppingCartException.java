package com.carrito.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String message;
	
	
	public ShoppingCartException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
}
