package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.CategoryDTO;
import com.carrito.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	Category categoryDTOtoCategory(CategoryDTO categoryDTO);
	CategoryDTO categoryToCategoryDTO(Category category);
}
