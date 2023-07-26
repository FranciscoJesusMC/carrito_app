package com.carrito.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.carrito.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User user;
	
	private User user2;
	
	@BeforeEach
	void init() {
		
		user = new User();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");

		
		user2 = new User();
		user2.setUsername("murdaz");
		user2.setPassword("12345");
		user2.setName("Pablo");
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("Pablo@gmail.com");
		user2.setPhone("999888777");
	
	}
	
	@Test
	void createUser() {
		
		User saveUser = userRepository.save(user);
				
		assertNotNull(saveUser);
		assertThat(saveUser.getId()).isNotEqualTo(0);
	}
	
	@Test
	void findUserById() {
		
		userRepository.save(user);
		
		User findUser = userRepository.findById(user.getId()).get();
		
		assertNotNull(findUser);
		assertThat(user.getId()).isNotEqualTo(0);
		assertEquals("rukero", user.getUsername());
	}
	
	@Test
	void getAllUsers() {
		
		userRepository.save(user);
		
		userRepository.save(user2);
		
		List<User> users = userRepository.findAll();
		
		assertNotNull(users);
		assertEquals(2, users.size());
	}
	
	@Test
	void updateUser() {
		
		User newUser = new User();
		user2.setUsername("jorguito");
		user2.setPassword("12345");
		user2.setName("Jorge");
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("Jorge@gmail.com");
		user2.setPhone("999888771");
		
		userRepository.save(newUser);
		
		User getUser = userRepository.findById(newUser.getId()).get();
		
		
		getUser.setUsername("White");
		getUser.setName("Manolo");
		
		User updateUser = userRepository.save(getUser);
		
		assertEquals("White", updateUser.getUsername());
		assertEquals("Manolo", updateUser.getName());
		
		
	}
	
	@Test
	void deleteUser() {
		
		userRepository.save(user);
		userRepository.save(user2);
		
		userRepository.delete(user);
		
		Optional<User> existsUser = userRepository.findById(user.getId());
		
		List<User> list = userRepository.findAll();
		
		assertEquals(1, list.size());
		assertThat(existsUser).isEmpty();
		
		
	}
}
