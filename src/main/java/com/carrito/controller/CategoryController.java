package com.carrito.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.CategoryDTO;
import com.carrito.serviceImpl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> listAllCategories(){
		List<CategoryDTO> categories = categoryServiceImpl.listAllCategorys();
		return ResponseEntity.ok(categories);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory (@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO category = categoryServiceImpl.createCategory(categoryDTO);
		return new ResponseEntity<>(category,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<String> updateCategory(@PathVariable(name = "categoryId") long categoryId, @Valid @RequestBody CategoryDTO categoryDTO){
		updateCategory(categoryId, categoryDTO);
		return new ResponseEntity<>("Category has been updated",HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory (@PathVariable(name = "categoryId")long categoryId){
		categoryServiceImpl.deleteCategory(categoryId);
		return new ResponseEntity<>("Category has been deleted",HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/addCategory/product/{productId}")
	public ResponseEntity<String> addCategoriesToProduct(@PathVariable(name = "productId")UUID productId,@RequestBody List<CategoryDTO> categoryDTO){
		categoryServiceImpl.addCategoryToProduct(productId, categoryDTO);
		return new ResponseEntity<>("Categories were added",HttpStatus.OK);
	}
	
	@DeleteMapping("/removeCategory/{categoryId}/product/{productId}")
	public ResponseEntity<String> removeCategoryToProduct(@PathVariable(name = "categoryId")long catgoryId,@PathVariable(name = "productId")UUID productId){
		categoryServiceImpl.removeCategoryToProduct(productId, catgoryId);
		return new ResponseEntity<>("Category has been removed",HttpStatus.OK);
	}
}
