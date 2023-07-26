package com.carrito.service;

import java.util.UUID;

import com.carrito.dto.CartItemDTO;

public interface CartItemService {
	
	public CartItemDTO updateCartItem(UUID cartItemId,long sizeId,CartItemDTO cartItemDTO);
	
	

	

}
