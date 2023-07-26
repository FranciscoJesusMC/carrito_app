package com.carrito.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ShoppingCartDTO;


public interface ShoppingCartService {

	public void addCartItemToShoppinCart(long userId,UUID productId,long sizeId,CartItemDTO cartItemDTO);
	
	public void removeCartItem(long userId,UUID cartItemId);

	public ShoppingCartDTO getShoppingCartByUser(long userId);
	
	public BigDecimal updateCartTotal(UUID shoppingCartId);
	
}
