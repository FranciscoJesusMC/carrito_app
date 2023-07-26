package com.carrito.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.carrito.dto.ProductDTO;
import com.carrito.dto.ProductResponseDTO;
import com.carrito.serviceImpl.ProductServiceImpl;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
		List<ProductResponseDTO> products = productServiceImpl.listAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "productId")UUID productId){
		ProductDTO product = productServiceImpl.getProductById(productId);
		return ResponseEntity.ok(product);
	}
		
	@PostMapping("/brand/{brandId}")
	public ResponseEntity<ProductDTO> createProduct(@PathVariable(name = "brandId")long brandId,@Valid @RequestBody ProductDTO productDTO){
		ProductDTO product = productServiceImpl.createProduct(brandId,productDTO);
		return new ResponseEntity<>(product,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable(name = "productId")UUID productId,@RequestBody ProductDTO productDTO){
		productServiceImpl.updateProduct(productId, productDTO);
		return new ResponseEntity<>("Product updated successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId")UUID productId){
		productServiceImpl.deleteProduct(productId);
		return new ResponseEntity<>("Product deleted successfully",HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<Page<ProductResponseDTO>> newPagination(
			@RequestParam(value = "category",required = false)String category,
			@RequestParam(value = "brand",required = false)String brand,
			@RequestParam(value = "size",required = false)String size,
			@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
			@RequestParam(value = "sort",defaultValue = "id,desc")String sort){
		
	     Sort sortable = Sort.by(sort.split(",")[0]);
	        if (sort.split(",").length > 1) {
	            sortable = (sort.split(",")[1].equalsIgnoreCase("asc")) ? sortable.ascending() : sortable.descending();
	        }
	        
	     Pageable pageable = PageRequest.of(pageNo, pageSize,sortable);
	     Page<ProductResponseDTO> products = productServiceImpl.paginationByCategoryAndBrandAndSize(category, brand, size, pageable);
	     return ResponseEntity.ok(products);
	}
	

		
}
