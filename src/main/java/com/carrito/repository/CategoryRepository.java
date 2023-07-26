package com.carrito.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public boolean existsByName(String name);

	public Category findByName(String name);
	
	public Set<Category> findByProductId(UUID productId);
 }
