package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.ProductResponseDTO;
import com.carrito.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductReponseMapper {

	Product productReponseDTOtoProduct(ProductResponseDTO productResponseDTO);
	ProductResponseDTO productToProductResponseDTO(Product product);
}
