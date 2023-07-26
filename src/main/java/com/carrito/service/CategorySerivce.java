package com.carrito.service;

import java.util.List;
import java.util.UUID;

import com.carrito.dto.CategoryDTO;

public interface CategorySerivce {
	
	public List<CategoryDTO> listAllCategorys();
	
	public CategoryDTO createCategory (CategoryDTO categoryDTO);
	
	public CategoryDTO updateCategory(long categoryId,CategoryDTO categoryDTO);
	
	public void deleteCategory(long categoryId);
	
	public void addCategoryToProduct(UUID productId,List<CategoryDTO> categoryDTO );
	
	public void removeCategoryToProduct(UUID productId,long categoryId);

}
