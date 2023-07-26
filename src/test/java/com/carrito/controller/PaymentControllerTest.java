package com.carrito.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.carrito.entity.Payment;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.User;
import com.carrito.serviceImpl.PaymentServiceImpl;

@WebMvcTest(PaymentController.class)
@Import({PaymentController.class,PaymentServiceImpl.class})
public class PaymentControllerTest {

	@MockBean
	private PaymentServiceImpl service;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void proccessPayment()throws Exception {
		
		
		User user = new User();
		user.setId(1L);
		user.setAddress("Rosa de santa maria 143");
		user.setEmail("franck@gmail.com");
		user.setName("Francisco");
		user.setPassword("12345");
		user.setPhone("987776029");
		user.setUsername("rukero");
		
		ShoppingCart shop = new ShoppingCart();
		shop.setId(UUID.randomUUID());
		shop.setUser(user);
		shop.setStatus("wait for payment...");
		shop.setTotal(new BigDecimal(1000));
		
		this.mockMvc.perform(post("/api/payment/shoppingCart/{shoppingCartId}",shop.getId()))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(""));
		
	}
	
	@Test
	void getPaymentToVerify() throws Exception {
		
		this.mockMvc.perform(get("/api/payment/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("[]"))
		.andReturn();
	}
	
	@Test
	void checkPaymentStatus() throws Exception{
		
		Payment pay = new Payment();
		pay.setId("1");
		
		this.mockMvc.perform(get("/api/payment/{paymentId}",pay.getId()))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(""))
		.andReturn();
	}
}
