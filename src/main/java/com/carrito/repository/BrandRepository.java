package com.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	public boolean existsByName(String name);
	
	public Brand findByName(String name);
}
