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

import com.carrito.entity.Brand;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class CartItemRepositoryTest {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	private CartItem cartItem;
	
	private CartItem cartItem2;
		
	private Product product;
	
	private Brand brand;
	
	
	@BeforeEach
	void init() {
		
		brand = new Brand();
		brand.setName("Nike");
		brandRepository.save(brand);
		
		product = new Product();
		product.setBrand(brand);
		product.setName("Phone");
		product.setDescription("Phone of year");
		product.setPrice(new BigDecimal(1000));
		productRepository.save(product);
		
		
		cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(10);
	
		cartItem2 = new CartItem();
		cartItem2.setQuantity(20);
		cartItem2.setProduct(product);

	}
	
	@Test
	void createCartItem() {
		
		CartItem saveCartItem = cartItemRepository.save(cartItem);
		
		assertNotNull(saveCartItem);
		assertThat(cartItem.getId()).isNotEqualTo(0);
		
	}
	
	@Test
	void findCartItemById() {
		
		cartItemRepository.save(cartItem);
		
		CartItem findCartItem = cartItemRepository.findById(cartItem.getId()).get();
		
		assertNotNull(findCartItem);
		assertThat(cartItem.getId()).isNotEqualTo(0);
		assertEquals(10, findCartItem.getQuantity());
		
	}
	
	@Test
	void getAllCartItems() {
		
		cartItemRepository.save(cartItem);
		cartItemRepository.save(cartItem2);
		
		List<CartItem> list = cartItemRepository.findAll();
		
		assertNotNull(list);
		assertEquals(2, list.size());
		
	}
	
	@Test
	void updateCartItem() {
		
		cartItemRepository.save(cartItem);
		
		CartItem findCartItem = cartItemRepository.findById(cartItem.getId()).get();
		findCartItem.setQuantity(200);
		
		CartItem updateCartItem = cartItemRepository.save(findCartItem);
		
		assertNotNull(updateCartItem);
		assertEquals(200, updateCartItem.getQuantity());
	}
	
	@Test
	void deleteCartItem() {
		
		cartItemRepository.save(cartItem);
		cartItemRepository.save(cartItem2);
		
		cartItemRepository.delete(cartItem);
		
		Optional<CartItem> existsCartItem = cartItemRepository.findById(cartItem.getId());
		
		List<CartItem> list = cartItemRepository.findAll();
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertThat(existsCartItem).isEmpty();

	}

}
