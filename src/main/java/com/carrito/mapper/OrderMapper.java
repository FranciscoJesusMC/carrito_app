package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.OrderDTO;
import com.carrito.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	Order orderDTOtoOrder(OrderDTO orderDTO);
	OrderDTO orderToOrderDTO(Order order);
}
