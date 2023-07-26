package com.carrito.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.carrito.dto.BrandDTO;
import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ProductDTO;
import com.carrito.dto.ShoppingCartDTO;
import com.carrito.dto.SizeDTO;
import com.carrito.dto.UserDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ShoppingCartIntegrationTest {

	@LocalServerPort
	private int port;
	
	private String url = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	@BeforeAll
	static void init() {
		
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	void setUp() {
		
		url = url+":"+port+"/api/";
	}
	
	@Test
	void addCartItemToShoppingCart() {
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("frank@gmail.com");
		user.setName("Frank");
		user.setPassword("12345");
		user.setPhone("987776022");
		user.setUsername("Francito");
		
		ResponseEntity<UserDTO> responseUser = restTemplate.postForEntity(url+"user", user, UserDTO.class);
		Long userId = responseUser.getBody().getId();
		
		assertNotNull(responseUser.getBody());
		assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());
		

		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Nike");
		brandDTO.setDescription("Nice");
		
		ResponseEntity<BrandDTO> responseBrand = restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		assertNotNull(responseBrand.getBody());
		assertEquals(HttpStatus.CREATED, responseBrand.getStatusCode());
	
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone");
		productDTO.setDescription("Phone of year");
		productDTO.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> responseProduct = restTemplate.postForEntity(url+"product/brand/"+brandId, productDTO, ProductDTO.class);
		UUID productId = responseProduct.getBody().getId();
		
		assertNotNull(responseProduct.getBody());
		assertEquals(HttpStatus.CREATED, responseProduct.getStatusCode());
		
		
		SizeDTO sizeDTO = new SizeDTO();
		sizeDTO.setName("M");
		sizeDTO.setStock(30);

		ResponseEntity<SizeDTO> responseSize = restTemplate.postForEntity(url+"product/"+productId+"/addSize", sizeDTO, SizeDTO.class);
		Long sizeId = responseSize.getBody().getId();
		
		
		CartItemDTO cartItem = new CartItemDTO();
		cartItem.setQuantity(10);
		
		ResponseEntity<String> responseCartItem = restTemplate.postForEntity(url+"shoppingCart/user/"+userId+"/product/"+productId+"/size/"+sizeId, cartItem, String.class);
		String message = responseCartItem.getBody();
		
		
		assertNotNull(responseCartItem.getBody());
		assertEquals(HttpStatus.OK, responseCartItem.getStatusCode());
		assertEquals("CartItem has been added to shoppingCart", message);
		
	}
	
	@Test
	void removeCartItemToShoppingCart() {
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("frank@gmail.com");
		user.setName("Frank");
		user.setPassword("12345");
		user.setPhone("987776022");
		user.setUsername("Francito");
		
		ResponseEntity<UserDTO> responseUser = restTemplate.postForEntity(url+"user", user, UserDTO.class);
		Long userId = responseUser.getBody().getId();
		
		assertNotNull(responseUser.getBody());
		assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());
		

		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Nike");
		brandDTO.setDescription("Nice");
		
		ResponseEntity<BrandDTO> responseBrand = restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		assertNotNull(responseBrand.getBody());
		assertEquals(HttpStatus.CREATED, responseBrand.getStatusCode());
	
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone");
		productDTO.setDescription("Phone of year");
		productDTO.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> responseProduct = restTemplate.postForEntity(url+"product/brand/"+brandId, productDTO, ProductDTO.class);
		UUID productId = responseProduct.getBody().getId();
		
		assertNotNull(responseProduct.getBody());
		assertEquals(HttpStatus.CREATED, responseProduct.getStatusCode());
		
		
		SizeDTO sizeDTO = new SizeDTO();
		sizeDTO.setName("M");
		sizeDTO.setStock(30);

		ResponseEntity<SizeDTO> responseSize = restTemplate.postForEntity(url+"product/"+productId+"/addSize", sizeDTO, SizeDTO.class);
		Long sizeId = responseSize.getBody().getId();
		
		
		CartItemDTO cartItem = new CartItemDTO();
		cartItem.setQuantity(10);
		
		ResponseEntity<String> responseCartItem = restTemplate.postForEntity(url+"shoppingCart/user/"+userId+"/product/"+productId+"/size/"+sizeId, cartItem, String.class);
		String message = responseCartItem.getBody();
		
		
		assertNotNull(responseCartItem.getBody());
		assertEquals(HttpStatus.OK, responseCartItem.getStatusCode());
		assertEquals("CartItem has been added to shoppingCart", message);
		
		ResponseEntity<ShoppingCartDTO> shopResponse = restTemplate.getForEntity(url+"shoppingCart/user/"+userId, ShoppingCartDTO.class);
		ShoppingCartDTO shop = shopResponse.getBody();
		
		
		UUID cartItemId = null ;
		
		if(shop !=null) {
			Set<CartItemDTO> cartItems = shop.getCartItem();
			
			if(cartItems !=null && !cartItems.isEmpty()) {
				CartItemDTO firstCartItem = cartItems.iterator().next();
				cartItemId = firstCartItem.getId();
			}
		}
		
		ResponseEntity<String> responseRemove = restTemplate.exchange(url+"shoppingCart/user/"+userId+"/cartItem/"+cartItemId, HttpMethod.DELETE,null,String.class);
		String deleteMessage = responseRemove.getBody();
		
		assertNotNull(deleteMessage);
		assertEquals(HttpStatus.OK, responseRemove.getStatusCode());
		assertEquals("CartItem has been removed to shoppingCart", deleteMessage);
	
	
	}
	
	@Test
	void getShoppingCartByUserId() {
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("frank@gmail.com");
		user.setName("Frank");
		user.setPassword("12345");
		user.setPhone("987776022");
		user.setUsername("Francito");
		
		ResponseEntity<UserDTO> responseUser = restTemplate.postForEntity(url+"user", user, UserDTO.class);
		Long userId = responseUser.getBody().getId();
		
		assertNotNull(responseUser.getBody());
		assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());
		

		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Nike");
		brandDTO.setDescription("Nice");
		
		ResponseEntity<BrandDTO> responseBrand = restTemplate.postForEntity(url+"brand", brandDTO, BrandDTO.class);
		Long brandId = responseBrand.getBody().getId();
		
		assertNotNull(responseBrand.getBody());
		assertEquals(HttpStatus.CREATED, responseBrand.getStatusCode());
	
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone");
		productDTO.setDescription("Phone of year");
		productDTO.setPrice(new BigDecimal(1000));
		
		ResponseEntity<ProductDTO> responseProduct = restTemplate.postForEntity(url+"product/brand/"+brandId, productDTO, ProductDTO.class);
		UUID productId = responseProduct.getBody().getId();
		
		assertNotNull(responseProduct.getBody());
		assertEquals(HttpStatus.CREATED, responseProduct.getStatusCode());
		
		
		SizeDTO sizeDTO = new SizeDTO();
		sizeDTO.setName("M");
		sizeDTO.setStock(30);

		ResponseEntity<SizeDTO> responseSize = restTemplate.postForEntity(url+"product/"+productId+"/addSize", sizeDTO, SizeDTO.class);
		Long sizeId = responseSize.getBody().getId();
		
		
		CartItemDTO cartItem = new CartItemDTO();
		cartItem.setQuantity(10);
		
		ResponseEntity<String> responseCartItem = restTemplate.postForEntity(url+"shoppingCart/user/"+userId+"/product/"+productId+"/size/"+sizeId, cartItem, String.class);
		String message = responseCartItem.getBody();
		
		
		assertNotNull(responseCartItem.getBody());
		assertEquals(HttpStatus.OK, responseCartItem.getStatusCode());
		assertEquals("CartItem has been added to shoppingCart", message);
		
		ResponseEntity<ShoppingCartDTO> shopResponse = restTemplate.getForEntity(url+"shoppingCart/user/"+userId, ShoppingCartDTO.class);
		ShoppingCartDTO shop = shopResponse.getBody();
		
		assertNotNull(shop);
	}
}
