package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.ShoppingCartDTO;
import com.carrito.entity.ShoppingCart;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

	ShoppingCart shoppingCartDTOtoShoppingCart(ShoppingCartDTO shoppingCartDTO);
	ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);
}
