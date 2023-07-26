package com.carrito.exceptions;

import java.util.UUID;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private UUID fieldValue2;
	private String fieldValue3;
	
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s resource not found for %s : '%s'", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, UUID fieldValue2) {
		super(String.format("%s resource not found for %s : '%s'",resourceName,fieldName,fieldValue2));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue2 = fieldValue2;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue3) {
		super(String.format("%s resource not found for %s : '%s'",resourceName,fieldName,fieldValue3));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue3 = fieldValue3;
	}
}
