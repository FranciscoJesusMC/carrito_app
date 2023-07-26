package com.carrito.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.SizeDTO;
import com.carrito.dto.StockDTO;
import com.carrito.service.SizeService;

@RestController
@RequestMapping("/api")
public class SIzeController {

	@Autowired
	private SizeService sizeService;
	
	
	@PostMapping("/product/{productId}/addSize")
	public ResponseEntity<SizeDTO> addSizeToProduct(@PathVariable(name = "productId")UUID productId,@Valid @RequestBody SizeDTO sizeDTO){
		SizeDTO size = sizeService.addSizeToProduct(productId, sizeDTO);
		return new ResponseEntity<>(size, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/product/{productId}/removeSize/{sizeId}")
	public ResponseEntity<String> removeSizeToProduct(@PathVariable(name = "productId") UUID productId,@PathVariable(name = "sizeId")long sizeId){
		sizeService.removeSizeToProduct(productId, sizeId);
		return new ResponseEntity<>("Size removed to product",HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/product/{productId}/updateStock/{sizeId}")
	public ResponseEntity<String> updateStocktoSize(@PathVariable(name = "productId")UUID productId,@PathVariable(name = "sizeId")long sizeId,@Valid @RequestBody StockDTO stockDTO){
		sizeService.updateStockToSize(productId, sizeId, stockDTO);
		return new ResponseEntity<>("Stock updated to product",HttpStatus.OK);
	}
	
}
