package com.carrito.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTO {
	
	private Long id;

	@NotNull(message = "EL campo name no puede estar vacio")
	@Size(min = 2 , max = 20,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 20")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String name;
	
	@NotNull(message = "EL campo description no puede estar vacio")
	@Size( max = 60,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 60")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String description;
	
	private BrandImageDTO brandImage;
	
}
