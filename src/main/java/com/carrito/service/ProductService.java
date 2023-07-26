package com.carrito.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carrito.dto.ProductDTO;
import com.carrito.dto.ProductResponseDTO;

public interface ProductService {

	public List<ProductResponseDTO> listAllProducts();
	
	public ProductDTO createProduct(long brandId,ProductDTO productDTO);
	
	public ProductDTO getProductById(UUID productId);
	
	public ProductDTO updateProduct(UUID productId,ProductDTO productDTO);
	
	public void deleteProduct(UUID productId);
	
	/////Lists

	public Page<ProductResponseDTO> paginationByCategoryAndBrandAndSize(String category,String brand,String size ,Pageable pageable);
	

	
}
