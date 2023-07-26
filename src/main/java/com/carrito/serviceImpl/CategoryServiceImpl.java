package com.carrito.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.CategoryDTO;
import com.carrito.entity.Category;
import com.carrito.entity.Product;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.CategoryMapper;
import com.carrito.mapper.CategoryMapperImpl;
import com.carrito.repository.CategoryRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.service.CategorySerivce;

@Service
public class CategoryServiceImpl implements CategorySerivce {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryMapper mapper = new CategoryMapperImpl();

	@Override
	public List<CategoryDTO> listAllCategorys() {
		List<Category> categories = categoryRepository.findAll();
		if(categories.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "list of categories is empty");
		}
		return categories.stream().map(category -> mapper.categoryToCategoryDTO(category)).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = mapper.categoryDTOtoCategory(categoryDTO);
		
		if(categoryRepository.existsByName(categoryDTO.getName())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "name : "+ categoryDTO.getName() + " already in use");
		}
		
		Category newCategory = categoryRepository.save(category);
		
		
		CategoryDTO saveCategory = mapper.categoryToCategoryDTO(newCategory);
		
		return saveCategory;
	}

	@Override
	public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		Category existName = categoryRepository.findByName(categoryDTO.getName());
		if(existName != null && !existName.getId().equals(category.getId())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "name : "+ categoryDTO.getName() + " already in use");
		}
		
		category.setName(categoryDTO.getName());
		
		Category updateCategory = categoryRepository.save(category);
		
		CategoryDTO saveCategory = mapper.categoryToCategoryDTO(updateCategory);
		
		return saveCategory;
	}
	
	@Override
	public void deleteCategory(long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		categoryRepository.delete(category);
		
	}
	
	@Override
	public void addCategoryToProduct(UUID productId, List<CategoryDTO> categoryDTO) {
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
		
		for(CategoryDTO category : categoryDTO) {
			Category existCategory = categoryRepository.findByName(category.getName());
			product.getCategory().add(existCategory);
		}
		
		
		productRepository.save(product);
		
	}

	@Override
	public void removeCategoryToProduct(UUID productId, long categoryId) {
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		Set<Category> listCategory = categoryRepository.findByProductId(productId);
		
		if(listCategory.contains(category)) {
			product.getCategory().remove(category);
			productRepository.save(product);
		}
	}


}
