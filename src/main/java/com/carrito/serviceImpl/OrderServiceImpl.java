package com.carrito.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.OrderDTO;
import com.carrito.entity.Order;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.OrderMapper;
import com.carrito.mapper.OrderMapperImpl;
import com.carrito.repository.OrderRepository;
import com.carrito.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderMapper mapper = new OrderMapperImpl();

	@Override
	public List<OrderDTO> getOrderByUserId(long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		
		if(orders.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "Order's is empty");
		}
		
		return orders.stream().map(order -> mapper.orderToOrderDTO(order)).collect(Collectors.toList());
	}
}
