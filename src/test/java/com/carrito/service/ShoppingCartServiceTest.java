package com.carrito.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.carrito.dto.BrandDTO;
import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ProductDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.entity.User;
import com.carrito.repository.CartItemRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.repository.SizeRepository;
import com.carrito.repository.UserRepository;
import com.carrito.serviceImpl.ShoppingCartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	
	private CartItemRepository cartItemRepository = Mockito.mock(CartItemRepository.class);
	
	private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		
	private SizeRepository sizeRepository = Mockito.mock(SizeRepository.class);
	
	@InjectMocks
	private ShoppingCartServiceImpl service = new ShoppingCartServiceImpl();

	private CartItem cartItem;
	
	private Product product;
	
	private Brand brand;
	
	private Size size;
	
	private User user;
	
	@BeforeEach
	void init() {
		
		user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
	
		brand = new Brand();
		brand.setName("Nike");

		product = new Product();
		product.setBrand(brand);
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));

		size = new Size();
		size.setId(1L);
		size.setStock(200);
		size.setProduct(product);

		cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(20);
	}
	
	@Test
	void addToCartItem() {
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Nike");
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Phone");
		productDTO.setDescription("Phone of year");
		productDTO.setPrice(new BigDecimal(1000));
		productDTO.setBrand(brandDTO);
		
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setProduct(productDTO);
		cartItemDTO.setQuantity(10);
		

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(sizeRepository.findById(anyLong())).thenReturn(Optional.of(size));
		when(productRepository.findById(any())).thenReturn(Optional.of(product));
		when(sizeRepository.findSizeByProductIdAndId(any(), anyLong())).thenReturn(size);

		
		service.addCartItemToShoppinCart(user.getId(), product.getId(), size.getId(), cartItemDTO);
	}
}

