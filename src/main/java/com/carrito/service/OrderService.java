package com.carrito.service;

import java.util.List;

import com.carrito.dto.OrderDTO;

public interface OrderService {

	public List<OrderDTO> getOrderByUserId(long userId);
}
