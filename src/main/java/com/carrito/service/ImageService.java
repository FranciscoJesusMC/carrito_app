package com.carrito.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


public interface ImageService {

	public List<String> uploadImage(UUID productId,List<MultipartFile> files) throws IOException;
	
	public void removeImageToProduct(UUID productId,long imageId);
	
	// Brand
	
	public String uploadImageToBrand(long brandId, MultipartFile file) throws IOException;

	public void deleteImageToBrand(long brandId) throws IOException;
}
