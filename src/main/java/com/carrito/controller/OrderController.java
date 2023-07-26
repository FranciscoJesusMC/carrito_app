package com.carrito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.OrderDTO;
import com.carrito.serviceImpl.OrderServiceImpl;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDTO>> getOrders(@PathVariable(name = "userId")long userId){
		List<OrderDTO> orders = orderServiceImpl.getOrderByUserId(userId);
		return ResponseEntity.ok(orders);
	}
}
