package com.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public boolean existsByEmail(String email);
	
	public boolean existsByPhone(String phone);
	
	public boolean existsByUsername(String username);

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public User findByPhone(String phone);
	
}
