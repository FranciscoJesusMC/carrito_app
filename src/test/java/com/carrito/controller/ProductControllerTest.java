package com.carrito.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.carrito.dto.BrandDTO;
import com.carrito.dto.ProductDTO;
import com.carrito.serviceImpl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@Import({ProductController.class,ProductServiceImpl.class})
public class ProductControllerTest {
	
	@MockBean
	private ProductServiceImpl productService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void createProduct() throws Exception {
		
		BrandDTO brand = new BrandDTO();
		brand.setName("Nike");
		brand.setDescription("Nice brand");
		
		ProductDTO product = new ProductDTO();
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		when(productService.createProduct(anyLong(), any(ProductDTO.class))).thenReturn(product);
		
		BigDecimal expectedPrice = new BigDecimal(1000); // Valor esperado
		
		this.mockMvc.perform(post("/api/product/brand/{brandId}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(product.getName())))
		.andExpect(jsonPath("$.description", is(product.getDescription())))
		.andExpect(jsonPath("$.price").value(expectedPrice))
		.andExpect(jsonPath("$.brand.name", is(brand.getName())));

		
		verify(productService,times(1)).createProduct(anyLong(), any(ProductDTO.class));
	}

	@Test
	void getProductById() throws Exception {
		
		BrandDTO brand = new BrandDTO();
		brand.setName("Nike");
		brand.setDescription("Nice brand");
		
		
		ProductDTO product = new ProductDTO();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		when(productService.getProductById(any())).thenReturn(product);
		
		this.mockMvc.perform(get("/api/product/{productId}",product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)))
		.andExpect(status().isOk());
		
		verify(productService,times(1)).getProductById(product.getId());
	}
	
//	@Test
//	void getAllProducts() throws Exception {
//		
//		
//		BrandDTO brand = new BrandDTO();
//		brand.setName("Nike");
//		brand.setDescription("Nice brand");
//		
//		
//		ProductDTO product = new ProductDTO();
//		product.setId(UUID.randomUUID());
//		product.setName("Phone");
//		product.setDescription("Phone of year");
//		product.setPrice(new BigDecimal(1000));
//		product.setBrand(brand);
//		
//		ProductDTO product2 = new ProductDTO();
//		product2.setId(UUID.randomUUID());
//		product2.setName("Phone");
//		product2.setDescription("Phone of year");
//		product2.setPrice(new BigDecimal(1000));
//		product2.setBrand(brand);
//		
//		List<ProductDTO> list = new ArrayList<>();
//		list.add(product);
//		list.add(product2);
//		
//		when(productService.listAllProducts()).thenReturn(list);
//		
//		this.mockMvc.perform(get("/api/product"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.size()", is(list.size())));
//		
//		
//		
//	}
	
	@Test
	void updateProduct() throws Exception{
	
		BrandDTO brand = new BrandDTO();
		brand.setName("Nike");
		brand.setDescription("Nice brand");
		
		
		ProductDTO product = new ProductDTO();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		ProductDTO product2 = new ProductDTO();
		product2.setId(UUID.randomUUID());
		product2.setName("Phone update");
		product2.setDescription("Phone of year update");
		product2.setPrice(new BigDecimal(1000));
		product2.setBrand(brand);
		
		when(productService.updateProduct(any(), any(ProductDTO.class))).thenReturn(product2);
		
		MvcResult result = mockMvc.perform(put("/api/product/{productId}",product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product2)))
		.andExpect(status().isOk())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		assertEquals("Product updated successfully", response);
	}
	
	@Test
	void deleteProduct() throws Exception {
		
		BrandDTO brand = new BrandDTO();
		brand.setName("Nike");
		brand.setDescription("Nice brand");
		
		
		ProductDTO product = new ProductDTO();
		product.setId(UUID.randomUUID());
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		product.setBrand(brand);
		
		doNothing().when(productService).deleteProduct(any());
		
		MvcResult result = mockMvc.perform(delete("/api/product/{productId}",product.getId()))
		.andExpect(status().isNoContent())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		assertEquals("Product deleted successfully", response);
	}
}
