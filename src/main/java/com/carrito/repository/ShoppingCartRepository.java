package com.carrito.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
	
	public ShoppingCart findByUserId(long userId);

}
