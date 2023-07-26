package com.carrito.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carrito.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

	List<Payment> findByShoppingCartId(UUID shoppingCartId);
	
	Page<Payment> findByCreatedDateAfterAndCreatedDateBefore(Date startDate,Date endDate, Pageable pageable);
	
	Page<Payment> findByCreatedDateBetween(Date startDate,Date endDate,Pageable pageable);
	
}
