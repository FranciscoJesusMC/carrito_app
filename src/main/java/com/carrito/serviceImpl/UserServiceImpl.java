package com.carrito.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.UserDTO;
import com.carrito.entity.Order;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.User;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.UserMapper;
import com.carrito.mapper.UserMapperImpl;
import com.carrito.repository.OrderRepository;
import com.carrito.repository.ShoppingCartRepository;
import com.carrito.repository.UserRepository;
import com.carrito.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepositorty;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserMapper mapper = new UserMapperImpl();

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "List of users is empty");
		}
		
		return users.stream().map(user -> mapper.usertoUserDTO(user)).collect(Collectors.toList());
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = mapper.userDTOtoUser(userDTO);
		
		if(userRepository.existsByUsername(userDTO.getUsername())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "username :"+ userDTO.getUsername() +" already in use");
		}
		if(userRepository.existsByEmail(userDTO.getEmail())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "email : "+ userDTO.getEmail() +" already in use");	
		}
		if(userRepository.existsByPhone(userDTO.getPhone())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "phone : "+ userDTO.getPhone() +" already in use");
		}
		
		User newUser = userRepository.save(user);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(newUser);
		shoppingCartRepositorty.save(shoppingCart);
		
		Order order = new Order();
		order.setUser(newUser);
		orderRepository.save(order);
		
		UserDTO createUser = mapper.usertoUserDTO(newUser);
		
		return createUser;
	}

	@Override
	public UserDTO getUserById(long userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Id", "user", userId));
		
		return mapper.usertoUserDTO(user);
	}

	@Override
	public UserDTO updateUser(long userId, UserDTO userDTO) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Id", "user", userId));
		
		User existUsername = userRepository.findByUsername(userDTO.getUsername());
		if(existUsername != null && !existUsername.getId().equals(user.getId()) ) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "username :"+ userDTO.getUsername() +" already in use");
		}
		
		User existsEmail = userRepository.findByEmail(userDTO.getEmail());
		if(existsEmail != null && !existsEmail.getId().equals(user.getId())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "email : "+ userDTO.getEmail() +" already in use");	
		}
		
		User existPhone = userRepository.findByPhone(userDTO.getPhone());
		if(existPhone != null && !existPhone.getId().equals(user.getId())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "phone : "+ userDTO.getPhone() +" already in use");

		}
		
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		user.setAddress(userDTO.getAddress());
	
		User updateUser = userRepository.save(user);
		
		UserDTO saveUser = mapper.usertoUserDTO(updateUser);
		
		return saveUser;
	}

	@Override
	public void deleteUser(long userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Id", "user", userId));
		userRepository.delete(user);
		
	}
	
}
