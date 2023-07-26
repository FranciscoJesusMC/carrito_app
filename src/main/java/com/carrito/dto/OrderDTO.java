package com.carrito.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	private List<CartItemDTO> cartItem;
	
}
