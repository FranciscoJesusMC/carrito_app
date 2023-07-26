package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.CartItemDTO;
import com.carrito.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	CartItem cartItemDTOtoCartItem(CartItemDTO cartItemDTO);
	CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
}
