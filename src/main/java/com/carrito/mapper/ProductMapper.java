package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.ProductDTO;
import com.carrito.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product productDTOtoProduct(ProductDTO productoDTO);
	ProductDTO producToProductoDTO(Product product);
}
