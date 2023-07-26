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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.carrito.dto.UserDTO;
import com.carrito.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@Import({UserController.class,UserServiceImpl.class})
public class UserControllerTest {

	@MockBean
	private UserServiceImpl userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	void createUser() throws Exception{

		
		UserDTO user = new UserDTO();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		when(userService.createUser(any(UserDTO.class))).thenReturn(user);
		
		this.mockMvc.perform(post("/api/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.address", is(user.getAddress())))
		.andExpect(jsonPath("$.email", is(user.getEmail())))
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.password", is(user.getPassword())))
		.andExpect(jsonPath("$.phone", is(user.getPhone())))
		.andExpect(jsonPath("$.username", is(user.getUsername())));
		
		verify(userService,times(1)).createUser(any(UserDTO.class));
		
			
	}
	
	@Test
	void getUserById() throws Exception {
		
		UserDTO user = new UserDTO();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		when(userService.getUserById(anyLong())).thenReturn(user);
		
		this.mockMvc.perform(get("/api/user/{userId}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isOk());
		
		verify(userService,times(1)).getUserById(1L);
	}
	
	@Test
	void getAllUsers() throws Exception{
		
		UserDTO user = new UserDTO();
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		
		UserDTO user2 = new UserDTO();
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("murdaz@gmail.com");
		user2.setName("Francisco");
		user2.setPassword("12345");
		user2.setPhone("987776021");
		user2.setUsername("murdaz");
		
		List<UserDTO> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		
		when(userService.getAllUsers()).thenReturn(list);
		
		this.mockMvc.perform(get("/api/user"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("%.size()", is(list.size())));
		
	}
	
	@Test
	void updateUser() throws Exception {
		
		UserDTO user = new UserDTO();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		
		UserDTO user2 = new UserDTO();
		user2.setId(1L);
		user2.setAddress("Rosa de santa maria 143");
		user2.setEmail("murdaz@gmail.com");
		user2.setName("Francisco");
		user2.setPassword("12345");
		user2.setPhone("987776021");
		user2.setUsername("murdaz");
		
		when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(user2);
		
		MvcResult result = mockMvc.perform(put("/api/user/{userId}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user2)))
		.andExpect(status().isOk())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		assertEquals("User updated successfully", response);
	}
	
	@Test
	void deleteUser() throws Exception {
		
		doNothing().when(userService).deleteUser(anyLong());
		
		MvcResult result = mockMvc.perform(delete("/api/user/{userId}",1L))
		.andExpect(status().isOk())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		assertEquals("User deleted successfully", response);
	}
}
