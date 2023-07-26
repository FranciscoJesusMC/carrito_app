package com.carrito.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.carrito.dto.BrandDTO;
import com.carrito.dto.ProductDTO;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ProductIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String url ="http://localhost";
	
	private static RestTemplate restTemplate;
	
	@BeforeAll
	static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	void setup() {
		
		url = url + ":" +port+"/api/";
	}
	
	@Test
	@Order(1)
	void createProduct() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Puma");
		brandDTO.setDescription("Nicebrand");
		
		ResponseEntity<BrandDTO> responseBrand =restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
				
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> response = restTemplate.postForEntity(url+"product/brand/"+brandId, product, ProductDTO.class);
	
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(product.getPrice(), response.getBody().getPrice());
	

	}
	
	@Test
	@Order(2)
	void getProducById() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Puma");
		brandDTO.setDescription("Nicebrand");
		
		ResponseEntity<BrandDTO> responseBrand =restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
				
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> response = restTemplate.postForEntity(url+"product/brand/"+brandId, product, ProductDTO.class);
		UUID productId = response.getBody().getId();
		
		ResponseEntity<ProductDTO> getProduct = restTemplate.getForEntity(url+"product/"+productId, ProductDTO.class);
		
		assertNotNull(getProduct);
		assertEquals(product.getName(), getProduct.getBody().getName());
	}
	
	@Test
	@Order(3)
	void getAllProducts() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Puma");
		brandDTO.setDescription("Nicebrand");
		
		ResponseEntity<BrandDTO> responseBrand =restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		restTemplate.postForEntity(url+"product/brand/"+brandId, product, ProductDTO.class);
	
		
		ProductDTO product2 = new ProductDTO();
		product2.setName("TV");
		product2.setDescription("TV of year");
		product2.setPrice(new BigDecimal(2000));
		
		restTemplate.postForEntity(url+"product/brand/"+brandId, product2, ProductDTO.class);
	
		
//		ProductDTO[] products = restTemplate.getForObject(url+"product", ProductDTO[].class);
		 
		ResponseEntity<ProductDTO[]> products = restTemplate.getForEntity(url+"product", ProductDTO[].class);
		List<ProductDTO> list = List.of(products.getBody());
		
		assertEquals(2, list.size());
	}

	@Test
	@Order(4)
	void updateProduct() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Puma");
		brandDTO.setDescription("Nicebrand");
		
		ResponseEntity<BrandDTO> responseBrand =restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> response = restTemplate.postForEntity(url+"product/brand/"+brandId, product, ProductDTO.class);
		UUID productId = response.getBody().getId();
	
		ProductDTO updateProduct = new ProductDTO();
		updateProduct.setName("Phone update");
		updateProduct.setDescription("Phone of year update");
		updateProduct.setPrice(new BigDecimal(2000));
	
		restTemplate.put(url+"product/"+productId, updateProduct);
		
		 ResponseEntity<ProductDTO> findProduct =restTemplate.getForEntity(url+"product/"+productId, ProductDTO.class);
		
		 assertEquals(HttpStatus.OK, findProduct.getStatusCode());
		 assertNotNull(findProduct.getBody());
		 assertEquals(findProduct.getBody().getName(),updateProduct.getName());
		 assertEquals(findProduct.getBody().getDescription(),updateProduct.getDescription());
		 assertEquals(0, findProduct.getBody().getPrice().compareTo(updateProduct.getPrice()));
	}
	
	@Test
	@Order(5)
	void deleteProduct() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Puma");
		brandDTO.setDescription("Nicebrand");
		
		ResponseEntity<BrandDTO> responseBrand =restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> response = restTemplate.postForEntity(url+"product/brand/"+brandId, product, ProductDTO.class);
		UUID productId = response.getBody().getId();
		assertNotNull(productId);
		
		restTemplate.delete(url+"product/"+productId);		
		
		try {

			ProductDTO[] products = restTemplate.getForObject(url+"product", ProductDTO[].class);
			
			assertEquals(0, products.length);
			fail("Se esperaba mensaje de error de lista vacia");
		} catch (HttpClientErrorException e) {
			
			String message = "list of products is empty";
			String errormessage = e.getResponseBodyAsString();
			
			assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
			assertThat(errormessage).contains(message);
		}
	}
}
