package com.carrito.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	public List<Order> findByUserId(Long userId);
}
