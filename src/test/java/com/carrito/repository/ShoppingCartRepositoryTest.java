package com.carrito.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.carrito.entity.ShoppingCart;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ShoppingCartRepositoryTest {
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	private ShoppingCart shop;
	
	private ShoppingCart shop2;
	
	@BeforeEach
	void init() {
		
		shop = new ShoppingCart();
		
		shop2= new ShoppingCart();
	}
	
	@Test
	void createShoppingCart() {
		
		shoppingCartRepository.save(shop);
		
		assertNotNull(shop);
	}
	
	@Test
	void getShopById() {
		
		shoppingCartRepository.save(shop);
		
		ShoppingCart getShop = shoppingCartRepository.findById(shop.getId()).get();
		
		assertNotNull(getShop);
	}
	
	@Test
	void getAllShop() {
		
		shoppingCartRepository.save(shop);
		shoppingCartRepository.save(shop2);
		
		List<ShoppingCart> list = shoppingCartRepository.findAll();
		
		assertNotNull(list);
		assertEquals(2, list.size());
	}
}
