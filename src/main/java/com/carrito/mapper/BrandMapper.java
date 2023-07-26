package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.BrandDTO;
import com.carrito.entity.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {

	Brand branDTOtoBrand(BrandDTO brandDTO);
	BrandDTO brandtoBrandDTO(Brand brand);
}
