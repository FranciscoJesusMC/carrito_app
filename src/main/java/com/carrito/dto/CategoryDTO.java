package com.carrito.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	@NotNull(message = "EL campo nombre no puede estar vacio")
	@Size(max = 20,message = "El nombre debe tener un maximo de 20 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String name;
}
