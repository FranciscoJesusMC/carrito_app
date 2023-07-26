package com.carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrito.dto.ProductDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.Product;
import com.carrito.repository.BrandRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
	
	private BrandRepository brandRepository = Mockito.mock(BrandRepository.class);
	
	@InjectMocks
	private ProductServiceImpl productService = new ProductServiceImpl();
	
	private Product product;
	
	private Brand brand;
	
	@BeforeEach
	void init() {
		
		brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		brand.setDescription("nice brand");
		
		product = new Product();
		product.setName("Phone");
		product.setDescription("Product of year");
		product.setPrice(new BigDecimal(1000));
	}
	
	@Test
	void createProduct() {
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone");
		productDTO.setDescription("Product of year");
		productDTO.setPrice(new BigDecimal(1000));
		
		when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
		when(productRepository.save(any(Product.class))).thenReturn(product);
		
		ProductDTO saveProduct = productService.createProduct(brand.getId(), productDTO);
		
		assertNotNull(saveProduct);
		verify(productRepository,times(1)).save(any(Product.class));
		assertEquals(product.getName(), saveProduct.getName());
	}
	
	@Test
	void getProductById() {
		
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		
		ProductDTO productDTO = productService.getProductById(product.getId());
		
		assertNotNull(productDTO);
		assertEquals("Phone", productDTO.getName());
		
		
	}
	
	@Test
	void getAllProducts() {
		
		Product product2 = new Product();
		product2.setName("Keyboard");
		product2.setDescription("Keyboard of year");
		product2.setPrice(new BigDecimal(2000));
		
		List<Product> products = new ArrayList<>();
		products.add(product);
		products.add(product2);
	}
	
	@Test
	void updateProduct() {
		
		Product product = new Product();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Product of year");
		product.setPrice(new BigDecimal(1000));
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone update");
		productDTO.setDescription("Product of year update");
		productDTO.setPrice(new BigDecimal(1000));
		
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenReturn(product);
		
		ProductDTO updateProduct = productService.updateProduct(product.getId(), productDTO);
		
		assertNotNull(updateProduct);
		assertEquals("Phone update", updateProduct.getName());
		assertEquals("Product of year update", updateProduct.getDescription());
	}
	
	@Test
	void deleteProduct() {
		
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		
		doNothing().when(productRepository).delete(any(Product.class));
		
		productRepository.delete(product);
		
		verify(productRepository,times(1)).delete(product);
		
	}
}
