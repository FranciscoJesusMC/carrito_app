package com.carrito.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

	private UUID id;
	private ProductDTO product;
	private SizeDTO size;
	
	@Min(value = 0, message = "El valor debe ser mayor o igual a 0")
	@Max(value = 100, message = "El valor debe ser menor o igual a 100")
	private int quantity;
	
	private BigDecimal price;
	private BigDecimal subtotal;
	private Date createdDate;
	private Date updatedDate;
	
}
