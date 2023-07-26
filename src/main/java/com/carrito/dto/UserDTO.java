package com.carrito.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;
	
	@NotNull(message = "EL campo username no puede estar vacio")
	@Size(min = 2 , max = 20,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 20")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String username;
	
	@NotNull(message = "EL campo password no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El password debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Solo esta permitido ingresar letras y numeros")
	private String password;
	
	@NotNull(message = "EL campo nombre no puede estar vacio")
	@Size(min = 2 , max = 30,message = "El nombre debe tener un minimo de 2 caracteres y un maximo de 30")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "Solo esta permitido ingresar letras")
	private String name;
	
	@NotNull(message = "El campo emial no puede estar vacio")
	@Email
	private String email;
	
	@NotNull(message = "EL campo address no puede estar vacio")
	@Size(min = 2 , max = 50,message = "El nombre debe tener un maximo de 50 caracteres")
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Solo esta permitido ingresar letras y numeros")
	private String address;
	
	@NotNull(message = "El campo phone no puede estar vacio")
	@Size(min = 9 , max = 9, message = "El celular debe tener 9 digitos")
	@Pattern(regexp = "^[0-9]+$",message = "Solo esta permitido ingresar numeros")
	private String phone;
}
