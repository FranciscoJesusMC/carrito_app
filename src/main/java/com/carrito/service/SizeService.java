package com.carrito.service;

import java.util.List;
import java.util.UUID;

import com.carrito.dto.SizeDTO;
import com.carrito.dto.StockDTO;

public interface SizeService {

	public SizeDTO addSizeToProduct(UUID productId,SizeDTO sizeDTO);
	
	public void removeSizeToProduct(UUID productId,long sizeId);
	
	public List<SizeDTO> getSizesToProduct(UUID productId);
	
	public void updateStockToSize(UUID productId,long sizeId,StockDTO stockDTO);
		
}
