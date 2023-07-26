package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.SizeDTO;
import com.carrito.entity.Size;

@Mapper(componentModel = "spring")
public interface SizeMapper {

	Size sizeDTOtoSize(SizeDTO sizeDTO);
	SizeDTO sizeToSizeDTO(Size size);
}
