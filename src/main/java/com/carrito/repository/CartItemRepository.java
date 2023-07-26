package com.carrito.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

	public List<CartItem> findByProductId(long productId);
	
	public List<CartItem> findByShoppingCartId(UUID shoppingCartId);
}
