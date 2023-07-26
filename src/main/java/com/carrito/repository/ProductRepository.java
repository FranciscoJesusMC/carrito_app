package com.carrito.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	
	Page<Product> findByCategoryNameAndBrandNameAndSizeName(String category,String brand,String size,Pageable pageable);

	Page<Product> findByCategoryNameContainingAndBrandNameContainingAndSizeNameContaining(String category,String brand,String size,Pageable pageable);
	
	Page<Product> findByCategoryNameContaining(String category,Pageable pageable);
	
	Page<Product> findByBrandNameContaining(String brand,Pageable pageable);
	
	Page<Product> findBySizeNameContaining(String size,Pageable pageable);
	
	
}
