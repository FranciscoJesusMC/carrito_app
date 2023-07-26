package com.carrito.service;

import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrito.dto.BrandDTO;
import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ProductDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.repository.CartItemRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.repository.SizeRepository;
import com.carrito.serviceImpl.CartItemServiceImpl;


@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {
	
	private CartItemRepository cartItemRepository = Mockito.mock(CartItemRepository.class);
	
	private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		
	private SizeRepository sizeRepository = Mockito.mock(SizeRepository.class);
	
	@InjectMocks
	private CartItemServiceImpl cartItemServiceImpl = new CartItemServiceImpl();

	private CartItem cartItem;
	
	private Product product;
	
	private Brand brand;
	
	private Size size;
	
	
	@BeforeEach
	void init() {
				
		brand = new Brand();
		brand.setName("Nike");

		product = new Product();
		product.setBrand(brand);
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));

		size = new Size();
		size.setId(1L);
		size.setName("M");
		size.setStock(200);
		size.setProduct(product);

		cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(20);
	}
	
	@Test
	void createCartItem() {
				
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setQuantity(10);
		
		when(productRepository.findById(any())).thenReturn(Optional.of(product));
		when(sizeRepository.findById(anyLong())).thenReturn(Optional.of(size));
		
		when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));
		when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
		
		CartItemDTO updateCartItem = cartItemServiceImpl.updateCartItem(cartItem.getId(), size.getId(), cartItemDTO);
		
		assertNotNull(updateCartItem);
		assertEquals(10, updateCartItem.getQuantity());		
	}
}
