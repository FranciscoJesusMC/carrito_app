package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.ProductImageDTO;
import com.carrito.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

	ProductImage imageDTOtoImage(ProductImageDTO imageDTO);
	ProductImageDTO imageToImageDTO(ProductImage image);
	
}
