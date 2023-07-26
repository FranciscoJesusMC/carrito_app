package com.carrito.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.carrito.dto.CartItemDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.entity.User;
import com.carrito.serviceImpl.ShoppingCartServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ShoppingCartController.class)
@Import({ShoppingCartController.class,ShoppingCartServiceImpl.class})
public class ShoppingCartControllerTest {

	@MockBean
	private ShoppingCartServiceImpl service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	void addCartItemToShoppingCart() throws Exception {
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		Brand brand = new Brand();
		brand.setName("Nike");
		brand.setDescription("Nice");
		
		Product product = new Product();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		Size size = new Size();
		size.setId(1L);
		size.setName("M");
		size.setStock(100);
		size.setProduct(product);
		
		CartItem cartItem = new CartItem();
		cartItem.setId(UUID.randomUUID());
		cartItem.setProduct(product);
		cartItem.setSize(size);
		cartItem.setQuantity(10);
				
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setQuantity(20);
		
		this.mockMvc.perform(post("/api/shoppingCart/user/{userId}/product/{productId}/size/{sizeId}",1L,product.getId(),1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cartItemDTO)))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("CartItem has been added to shoppingCart"));
		
	}
	
	
	@Test
	void removeCartItemToShoppingCart() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		Brand brand = new Brand();
		brand.setName("Nike");
		brand.setDescription("Nice");
		
		Product product = new Product();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		Size size = new Size();
		size.setId(1L);
		size.setName("M");
		size.setStock(100);
		size.setProduct(product);
		
		CartItem cartItem = new CartItem();
		cartItem.setId(UUID.randomUUID());
		cartItem.setProduct(product);
		cartItem.setSize(size);
		cartItem.setQuantity(10);
		
		this.mockMvc.perform(delete("/api/shoppingCart/user/{userId}/cartItem/{cartItemId}",1L,cartItem.getId()))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("CartItem has been removed to shoppingCart"));
	}
	
	@Test
	void getShoppingCartByUserId() throws Exception {
	
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		this.mockMvc.perform(get("/api/shoppingCart/user/{userId}",1L))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(""))
		.andReturn();
	}
}

