package com.carrito.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {

	List<Size> findByProductId(UUID productId);
	
	Size findByName(String name);
	
	Size findSizeByProductIdAndId(UUID productId, long sizeId);
}
