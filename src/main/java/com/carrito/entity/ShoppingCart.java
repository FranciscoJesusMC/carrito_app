package com.carrito.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type ="uuid-char")
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	  
	@OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<CartItem> cartItem = new HashSet<>();
	
	private BigDecimal total;
	
	private String status;
	
	@OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	private List<Payment> payment =  new ArrayList<>();
}
