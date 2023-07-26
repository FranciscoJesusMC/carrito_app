package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.UserDTO;
import com.carrito.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User userDTOtoUser(UserDTO userDTO);
	UserDTO usertoUserDTO(User user);
}
