package com.carrito.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeDTO {

	private Long id;
	
	@Pattern(regexp = "^(S|M|L|XL)$", message = "Name of the size must be S, M, L or S")
	@Size(max = 1, message = "The name of the size must have a maximum length of 1")   
	private String name;
	
	@Min(value = 0, message = "El valor debe ser mayor o igual a 0")
	@Max(value = 100, message = "El valor debe ser menor o igual a 100")
	private int stock;
	

}
