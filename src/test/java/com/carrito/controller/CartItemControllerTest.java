package com.carrito.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.carrito.dto.CartItemDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.serviceImpl.CartItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CartItemController.class)
@Import({CartItemController.class,CartItemServiceImpl.class})
public class CartItemControllerTest {

	@MockBean
	private CartItemServiceImpl cartItemService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void updateCartItem() throws Exception {
		
		Brand brand = new Brand();
		brand.setName("Nike");
		brand.setDescription("Nice");
		
		Product product = new Product();
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

	
		
		when(cartItemService.updateCartItem(any(), anyLong(), any(CartItemDTO.class))).thenReturn(cartItemDTO);
		
		 MvcResult result = mockMvc.perform(put("/api/cartItem/{cartItemId}/size/{sizeId}",cartItem.getId(),size.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cartItemDTO)))
		.andExpect(status().isOk())
		.andReturn();
		 
		 String response = result.getResponse().getContentAsString();
		 
		 assertEquals("CartItem updated successfully", response);
	
	}
}
