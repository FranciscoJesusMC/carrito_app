package com.carrito.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carrito.dto.PaymentDTO;
import com.stripe.exception.StripeException;

public interface PaymentService {

	public String proccessPaymant(UUID shoppingCartId) throws StripeException;
	
	public List<PaymentDTO> getPaymentToVerify();

	public String checkPaymentStatus(String paymentId);
	
	public Page<PaymentDTO> findPaymentBetween(Date startDate,Date endDate,Pageable pageable);
	
	public List<PaymentDTO> listAll();
	
}
