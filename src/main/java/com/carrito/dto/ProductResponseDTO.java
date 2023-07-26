package com.carrito.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	private Set<ProductImageDTO> images;
	private Set<CategoryDTO> category;
	private BrandDTO brand;
	private List<SizeDTO> size;
}
