package com.carrito.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.BrandDTO;
import com.carrito.entity.Brand;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.BrandMapper;
import com.carrito.mapper.BrandMapperImpl;
import com.carrito.repository.BrandRepository;
import com.carrito.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private BrandMapper mapper = new BrandMapperImpl();

	@Override
	public List<BrandDTO> getAllBrands() {
		List<Brand> brands = brandRepository.findAll();
		if(brands.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "list of brand's is empty");
		}
				
		return brands.stream().map(brand -> mapper.brandtoBrandDTO(brand)).collect(Collectors.toList());
	}

	@Override
	public BrandDTO getBrandById(long brandId) {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
		
		return mapper.brandtoBrandDTO(brand);
	}

	@Override
	public BrandDTO createBrand(BrandDTO brandDTO) {
		Brand brand = mapper.branDTOtoBrand(brandDTO);
		
		if(brandRepository.existsByName(brandDTO.getName())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "name : " + brandDTO.getName() + "already in use");
		}
		
		Brand createBrand = brandRepository.save(brand);
		
		BrandDTO saveBrand = mapper.brandtoBrandDTO(createBrand);
		
		return saveBrand;
	}

	@Override
	public BrandDTO updateBrand(long brandId, BrandDTO brandDTO) {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));

		Brand existName = brandRepository.findByName(brandDTO.getName());
		if(existName != null && !existName.getId().equals(brand.getId())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "name : " + brandDTO.getName() + "already in use");
		}
		
		brand.setName(brandDTO.getName());
		brand.setDescription(brandDTO.getDescription());
		
		Brand updateBrand = brandRepository.save(brand);
		
		BrandDTO saveBrand = mapper.brandtoBrandDTO(updateBrand);
		
		return saveBrand;
	}

	@Override
	public void deleteBrand(long brandId) {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
		brandRepository.delete(brand);
		
	}
}
