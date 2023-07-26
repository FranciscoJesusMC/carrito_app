package com.carrito.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ShoppingCartDTO;
import com.carrito.serviceImpl.ShoppingCartServiceImpl;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartServiceImpl shoppingCartServiceImpl;
	
	@PostMapping("/user/{userId}/product/{productId}/size/{sizeId}")
	public ResponseEntity<String> addCartItemToShoppingCart(@PathVariable(name = "userId")long userId,@PathVariable(name = "productId")UUID productId,@PathVariable(name = "sizeId")long sizeId,@RequestBody CartItemDTO cartItemDTO){
		shoppingCartServiceImpl.addCartItemToShoppinCart(userId, productId,sizeId, cartItemDTO);
		return new ResponseEntity<>("CartItem has been added to shoppingCart",HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}/cartItem/{cartItemId}")
	public ResponseEntity<String> removeCartItemFromShoppingCart(@PathVariable(name = "userId")long userId,@PathVariable(name = "cartItemId")UUID cartItemId){
		shoppingCartServiceImpl.removeCartItem(userId, cartItemId);
		return new ResponseEntity<>("CartItem has been removed to shoppingCart",HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(@PathVariable(name = "userId")long userId){
		ShoppingCartDTO shop = shoppingCartServiceImpl.getShoppingCartByUser(userId);
		return ResponseEntity.ok(shop);
	}
	
}
