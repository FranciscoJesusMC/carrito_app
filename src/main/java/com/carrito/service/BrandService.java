package com.carrito.service;

import java.util.List;

import com.carrito.dto.BrandDTO;

public interface BrandService {
	
	public List<BrandDTO> getAllBrands();
	
	public BrandDTO getBrandById(long brandId);

	public BrandDTO createBrand(BrandDTO brandDTO);
	
	public BrandDTO updateBrand(long brandId,BrandDTO brandDTO);
	
	public void deleteBrand(long brandId);
}
