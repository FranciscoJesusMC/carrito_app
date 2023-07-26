package com.carrito.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.CartItemDTO;
import com.carrito.serviceImpl.CartItemServiceImpl;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {

	@Autowired
	private CartItemServiceImpl cartItemServiceImpl;
		
	@PutMapping("/{cartItemId}/size/{sizeId}")
	public ResponseEntity<String> updateCartItem(@PathVariable(name = "cartItemId")UUID cartItemId,@PathVariable(name = "sizeId")long sizeId,@Valid @RequestBody CartItemDTO cartItemDTO){
		cartItemServiceImpl.updateCartItem(cartItemId,sizeId, cartItemDTO);
		return new ResponseEntity<>("CartItem updated successfully",HttpStatus.OK);
	}
		
}
