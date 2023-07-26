package com.carrito.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDTO {

	private UUID id;
	private Set<CartItemDTO> cartItem;
	private BigDecimal total;
	private String status;
}
