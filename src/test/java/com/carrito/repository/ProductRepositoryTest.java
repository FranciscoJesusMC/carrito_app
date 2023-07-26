package com.carrito.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.carrito.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	private Product product;
	
	private Product product2;
	
	@BeforeEach
	void init() {
		
		product = new Product();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		
		product2 = new Product();
		product2.setName("Keyboard");
		product2.setDescription("Keyboard of year");
		product2.setPrice(new BigDecimal(2000));
	}

	@Test
	void createProduct() {
		
		productRepository.save(product);
		
		assertNotNull(product);
		assertThat(product.getId()).isNotEqualTo(0);
	}
	
	@Test
	void getProductById() {
		
		productRepository.save(product);
		
		Product getProduct = productRepository.findById(product.getId()).get();
		
		assertNotNull(getProduct);
		assertThat(getProduct.getId()).isNotEqualTo(0);
		assertEquals("Phone", getProduct.getName());
	}
	
	@Test
	void getAllProducts() {
		
		productRepository.save(product);
		productRepository.save(product2);
		
		List<Product> products = productRepository.findAll();
		
		assertNotNull(products);
		assertEquals(2, products.size());
	}
	
	@Test
	void updateProduct() {
		
		Product newProduct = new Product();
		newProduct.setName("Mouse");
		newProduct.setDescription("Mouse of year");
		newProduct.setPrice(new BigDecimal(500));
		
		productRepository.save(newProduct);
		
		Product findProduct = productRepository.findById(newProduct.getId()).get();
		
		findProduct.setName("TV");
		findProduct.setDescription("TV of year");
		
		Product updateProduct = productRepository.save(findProduct);
		
		assertNotNull(updateProduct);
		assertEquals("TV", updateProduct.getName());
		assertEquals("TV of year", updateProduct.getDescription());
	}
	
	@Test
	void deleteProduct() {
		
		productRepository.save(product);
		productRepository.save(product2);
		
		productRepository.delete(product2);
		
		Optional<Product> existsProduct = productRepository.findById(product2.getId());
		
		List<Product> products = productRepository.findAll();
		
		assertThat(existsProduct).isEmpty();
		assertEquals(1, products.size());
		
	}
}
