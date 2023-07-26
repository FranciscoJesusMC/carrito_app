package com.carrito.mapper;

import org.mapstruct.Mapper;

import com.carrito.dto.PaymentDTO;
import com.carrito.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	
	Payment paymentDTOtoPayment(PaymentDTO paymentDTO);
	PaymentDTO paymentToPaymentDTO(Payment payment);

}
