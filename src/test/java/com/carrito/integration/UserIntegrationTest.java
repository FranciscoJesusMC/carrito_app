package com.carrito.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.carrito.dto.UserDTO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserIntegrationTest {

	@LocalServerPort
	private int port;
	
	private String url = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	@BeforeAll
	static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	void beforeSetup() {
		url = url + ":" +port+"/api/";
	}
	
	@Test
	void createUser() {
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("frank@gmail.com");
		user.setName("Frank");
		user.setPassword("12345");
		user.setPhone("987776022");
		user.setUsername("Francito");
		
		ResponseEntity<UserDTO> response =restTemplate.postForEntity(url+"user", user, UserDTO.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(user.getAddress(), response.getBody().getAddress());
		assertEquals(user.getEmail(), response.getBody().getEmail());
		assertEquals(user.getName(), response.getBody().getName());
		assertEquals(user.getPassword(), response.getBody().getPassword());
		assertEquals(user.getPhone(), response.getBody().getPhone());
		assertEquals(user.getUsername(), response.getBody().getUsername());
		
		
	}
	
	@Test
	void getAllUsers() {
		
		UserDTO[] users = restTemplate.getForObject(url+"user", UserDTO[].class);
		
		assertEquals(2, users.length);
	}
	
	@Test
	void getUserById() {
		
		ResponseEntity<UserDTO> user = restTemplate.getForEntity(url+"user/"+2, UserDTO.class);
		
		assertNotNull(user);
		assertEquals("Frank", user.getBody().getName());
	}
	
	@Test
	void updateUser() {
		
		UserDTO user2 = new UserDTO();
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("jesus@gmail.com");
		user2.setName("Jesus");
		user2.setPassword("12345");
		user2.setPhone("987776022");
		user2.setUsername("Jesusito");
		
		restTemplate.put(url+"user/"+2, user2);
		
		ResponseEntity<UserDTO> getUser = restTemplate.getForEntity(url+"user/"+2, UserDTO.class);
		
		assertEquals(HttpStatus.OK, getUser.getStatusCode());
		assertNotNull(getUser.getBody());
		assertEquals(user2.getAddress(), getUser.getBody().getAddress());
		assertEquals(user2.getEmail(), getUser.getBody().getEmail());
		assertEquals(user2.getName(), getUser.getBody().getName());
		assertEquals(user2.getPassword(), getUser.getBody().getPassword());
		assertEquals(user2.getPhone(), getUser.getBody().getPhone());
		assertEquals(user2.getUsername(), getUser.getBody().getUsername());
		
	}
	
	@Test
	void deleteUser() {
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("prueba@gmail.com");
		user.setName("Prueba");
		user.setPassword("12345");
		user.setPhone("987776011");
		user.setUsername("Prueba");
		
		ResponseEntity<UserDTO> createUser = restTemplate.postForEntity(url+"/user", user, UserDTO.class);
		
		assertEquals(HttpStatus.CREATED, createUser.getStatusCode());
		assertNotNull(createUser.getBody());
		
		Long userId = createUser.getBody().getId();
		assertNotNull(userId);
		
		restTemplate.delete(url+"user/"+userId);
		
		UserDTO[] users = restTemplate.getForObject(url+"user", UserDTO[].class);
		
		assertEquals(2, users.length);
		
	}
}
