package com.carrito.service;

import java.util.List;

import com.carrito.dto.UserDTO;

public interface UserService {


	public List<UserDTO> getAllUsers();
	
	public UserDTO createUser(UserDTO userDTO);

	public UserDTO getUserById(long userId);
	
	public UserDTO updateUser(long userId,UserDTO userDTO);
	
	public void deleteUser(long userId);
}
