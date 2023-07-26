package com.carrito.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	
	private String id;
	
	private BigDecimal amount;

	private String status;
	
	private Date createdDate;
}
