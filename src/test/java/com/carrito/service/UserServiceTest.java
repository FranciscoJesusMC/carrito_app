package com.carrito.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrito.dto.UserDTO;
import com.carrito.entity.Order;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.User;
import com.carrito.repository.OrderRepository;
import com.carrito.repository.ShoppingCartRepository;
import com.carrito.repository.UserRepository;
import com.carrito.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	
	private ShoppingCartRepository shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
	
	private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
	
	@InjectMocks
	private UserServiceImpl userSeviceImpl = new UserServiceImpl();
	
	private ShoppingCart shop;
	
	private Order order;
	
	
	@BeforeEach
	void init() {
		
		shop = new ShoppingCart();
		shop.setId(UUID.randomUUID());
		
		order = new Order();
		order.setId(UUID.randomUUID());
		
	}
	
	@Test
	void createUser() {

		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		UserDTO userDTO = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		user.setShoppingCart(shop);
		user.setOrder(order);
		
		
		when(shoppingCartRepository.findById(shop.getId())).thenReturn(Optional.of(shop));
		when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		UserDTO saveUser = userSeviceImpl.createUser(userDTO);
		
		assertNotNull(saveUser);
		verify(userRepository,times(1)).save(any(User.class));
		assertEquals(user.getName(), saveUser.getName());
		
		
	}
	
	@Test
	void findUserById() {
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
						
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		UserDTO userDTO = userSeviceImpl.getUserById(user.getId());
		
		assertNotNull(userDTO);
		assertEquals("Francisco", user.getName());
	}

	
	@Test
	void getallUsers() {
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		User user2 = new User();
		user2.setId(2L);
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("jesus@gmail.com");
		user2.setName("Jesus");
		user2.setPassword("12345");
		user2.setPhone("987776021");
		user2.setUsername("frio");
		
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		
		when(userRepository.findAll()).thenReturn(list);
		
		List<UserDTO> listDTO = userSeviceImpl.getAllUsers();
		
		assertNotNull(listDTO);
		assertEquals(2, listDTO.size());
		
	}
	
	@Test
	void updateUser() {
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		
		
		UserDTO user2 = new UserDTO();
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("jesus@gmail.com");
		user2.setName("Jesus");
		user2.setPassword("12345");
		user2.setPhone("987776021");
		user2.setUsername("frio");
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		UserDTO updateUser = userSeviceImpl.updateUser(1L, user2);
		
		assertNotNull(updateUser);
		assertEquals("Jesus", updateUser.getName());
		
		
	}
	
	@Test
	void deleteUser() {
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		doNothing().when(userRepository).delete(any(User.class));
		
		userSeviceImpl.deleteUser(1L);
		
		verify(userRepository,times(1)).delete(user);
	}
}
