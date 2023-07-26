package com.carrito.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.carrito.entity.Brand;
import com.carrito.entity.BrandImage;
import com.carrito.entity.ProductImage;
import com.carrito.entity.Product;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.repository.BrandImageRepository;
import com.carrito.repository.BrandRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.service.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private BrandImageRepository brandImageRepository;

	@Override
	public List<String> uploadImage(UUID productId, List<MultipartFile> files) throws IOException {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
		
		List<String> imagenUrls = new ArrayList<>();
		
		for(MultipartFile file : files) {
			Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			String imageUrl = uploadResult.get("secure_url").toString();
			
			ProductImage image = new ProductImage();
			image.setUrl(imageUrl);
			
			
			product.getImages().add(image);
			productRepository.save(product);
			
			imagenUrls.add(imageUrl);
		}
		
		return imagenUrls;
	}

	@Override
	public void removeImageToProduct(UUID productId, long imageId) {
//		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
		
		
	}

	@Override
	public String uploadImageToBrand(long brandId, MultipartFile file) throws IOException {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
		
		Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		String imageUrl = uploadResult.get("secure_url").toString();
		String publicId = uploadResult.get("public_id").toString();
		
		BrandImage brandImage = new BrandImage();
		brandImage.setUrl(imageUrl);
		brandImage.setPublicId(publicId);
		brandImage.setBrand(brand);
		brandImageRepository.save(brandImage);
		
		brand.setBrandImage(brandImage);
		brandRepository.save(brand);
		
		return imageUrl;
	}

	@Override
	public void deleteImageToBrand(long brandId) throws IOException {
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
		
		BrandImage brandImage = brandImageRepository.findById(brand.getBrandImage().getId()).orElseThrow(()-> new ResourceNotFoundException("BrandImage", "id", brand.getBrandImage().getId()));
		
		try {

			cloudinary.uploader().destroy(brand.getBrandImage().getPublicId(), ObjectUtils.emptyMap());
			
			brand.setBrandImage(null);
			brandRepository.save(brand);
					
			brandImageRepository.delete(brandImage);
			
		} catch (Exception e) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "An error ocurred");
			
		}
		
	}



}
