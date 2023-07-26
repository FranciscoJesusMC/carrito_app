package com.carrito.entity;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "shoppingCart_id")
	private ShoppingCart shoppingCart;
	
	private BigDecimal amount;
	
	private String currency;
	
	private String methodPayment;

	private String status;
}
