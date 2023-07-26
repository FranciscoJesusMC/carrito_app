package com.carrito.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.UserDTO;
import com.carrito.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> users = userServiceImpl.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "userId")long userId){
		UserDTO user = userServiceImpl.getUserById(userId);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO user = userServiceImpl.createUser(userDTO);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable(name = "userId")long userId,@Valid @RequestBody UserDTO userDTO){
		userServiceImpl.updateUser(userId, userDTO);
		return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "userId")long userId){
		userServiceImpl.deleteUser(userId);
		return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
	}
	
}
