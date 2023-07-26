package com.carrito.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrito.entity.Brand;
import com.carrito.entity.Category;
import com.carrito.entity.Order;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.User;
import com.carrito.repository.BrandRepository;
import com.carrito.repository.CategoryRepository;
import com.carrito.repository.OrderRepository;
import com.carrito.repository.ShoppingCartRepository;
import com.carrito.repository.UserRepository;

@Component
public class StartApp {

//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private ShoppingCartRepository shoppingCartRepository;
//	
//	@Autowired
//	private OrderRepository orderRepository;
//	
//	@Autowired
//	private BrandRepository brandRepository;
//	
//	@Autowired
//	private CategoryRepository categoryRepository;
//		
//	@PostConstruct
//	private void init() {
//		
//		User user = userRepository.findByUsername("rukero");
//		
//		if(user == null) {
//		user = new User();
//		user.setUsername("rukero");
//		user.setPassword("12345");
//		user.setName("Francisco");
//		user.setAddress("Rosa de santa maria 143");
//		user.setEmail("franck@gmail.com");
//		user.setPhone("987776029");
//		userRepository.save(user);
//		
//		ShoppingCart shoppingCart = new ShoppingCart();
//		shoppingCart.setUser(user);
//		shoppingCartRepository.save(shoppingCart);
//		
//		Order order = new Order();
//		order.setUser(user);
//		orderRepository.save(order);
//		
//		}
//		
//		Brand brand = brandRepository.findByName("Nike");
//		if(brand == null) {
//			brand = new Brand();
//			brand.setName("Nike");
//			brand.setDescription("Marca de ropa deportiva");
//			brand.setBrandImage(null);
//			brandRepository.save(brand);
//		}
//		
//		Category category = categoryRepository.findByName("Man");
//		if(category == null) {
//			category = new Category();
//			category.setName("Man");
//			categoryRepository.save(category);
//		}
//	}
}
