package com.carrito.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carrito.serviceImpl.ImageServiceImpl;

@RequestMapping("/api")
@RestController
public class ImageController {

	@Autowired
	private ImageServiceImpl imageServiceImpl;
	
	@PostMapping("/product/{productId}/image")
	public ResponseEntity<List<String>> addImagenToProduct(@PathVariable(name = "productId")UUID productId,@RequestParam(name = "file")List<MultipartFile> files) {
		try {
			List<String> imageUrls = imageServiceImpl.uploadImage(productId, files);
			return ResponseEntity.ok(imageUrls);
		} catch (IOException e) {
		     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());       			
		}
	}
	
//	@DeleteMapping("/product/{productId}/images/{imageUrl}")
//	public ResponseEntity<String> removeImageToProduct(@PathVariable(name = "productId")UUID productId,@PathVariable(name = "imageUrl")String imageUrl){
//		imageServiceImpl.removeImage(productId, imageUrl);
//		return new ResponseEntity<>("Image has been removed",HttpStatus.NO_CONTENT);
//	}
	
	@PutMapping("/brand/{brandId}/image")
	public ResponseEntity<String> addImageToBrand(@PathVariable(name = "brandId")long brandId,@RequestParam(name = "file")MultipartFile file){
		try {
			String imageUrl = imageServiceImpl.uploadImageToBrand(brandId, file);
			return ResponseEntity.ok(imageUrl);
		} catch (Exception e) {
		     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar la imagen");
		        				
		}	
	}
	
	@DeleteMapping("/brand/{brandId}/deleteImage")
	public ResponseEntity<String> removeImageToBrand(@PathVariable(name = "brandId")long brandId) throws IOException{
		imageServiceImpl.deleteImageToBrand(brandId);
		return new ResponseEntity<>("Image has been removed to brand",HttpStatus.OK);
	}
	
}
