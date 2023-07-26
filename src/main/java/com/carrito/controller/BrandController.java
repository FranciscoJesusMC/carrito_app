package com.carrito.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.BrandDTO;
import com.carrito.serviceImpl.BrandServiceImpl;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

	@Autowired
	private BrandServiceImpl brandServiceImpl;
	
	@GetMapping
	public ResponseEntity<List<BrandDTO>> getAllBrands(){
		List<BrandDTO> brands = brandServiceImpl.getAllBrands();
		return ResponseEntity.ok(brands);
	}
	
	@GetMapping("/{brandId}")
	public ResponseEntity<BrandDTO> getBrandById(@PathVariable(name = "brandId")long brandId){
		BrandDTO brand = brandServiceImpl.getBrandById(brandId);
		return ResponseEntity.ok(brand);
	}
	
	@PostMapping
	public ResponseEntity<BrandDTO> createBrand (@Valid @RequestBody BrandDTO brandDTO){
		BrandDTO brand = brandServiceImpl.createBrand(brandDTO);
		return new ResponseEntity<>(brand,HttpStatus.CREATED);
	}
	
	@PutMapping("/{brandId}")
	public ResponseEntity<BrandDTO> updateBrand(@PathVariable(name = "brandId")long brandId,@Valid @RequestBody BrandDTO brandDTO){
		BrandDTO brand = brandServiceImpl.updateBrand(brandId, brandDTO);
		return ResponseEntity.ok(brand);
	}
	
	@DeleteMapping("/{brandId}")
	public ResponseEntity<String> deleteBrand(@PathVariable(name = "brandId")long brandId){
		brandServiceImpl.deleteBrand(brandId);
		return new ResponseEntity<>("Brand deleted successfully",HttpStatus.NO_CONTENT);
	}
	
}
