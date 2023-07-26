package com.carrito.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.dto.PaymentDTO;
import com.carrito.serviceImpl.PaymentServiceImpl;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	
	@PostMapping("/shoppingCart/{shoppingCartId}")
	public ResponseEntity<String> proccessPayment(@PathVariable(name = "shoppingCartId")UUID shoppingCartId){
		try {
			String paymentLink = paymentServiceImpl.proccessPaymant(shoppingCartId);
			return ResponseEntity.ok(paymentLink);
			
		} catch (Exception e) {
		     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process payment");     
		}
	}
	
	@GetMapping("/toVerfiy")
	public ResponseEntity<List<PaymentDTO>> getPaymentToVerify(){
		List<PaymentDTO> payments = paymentServiceImpl.getPaymentToVerify();
		return ResponseEntity.ok(payments);
	}
	
	
    @GetMapping("/{paymentId}")
    public ResponseEntity<String> checkPaymentStatus(@PathVariable String paymentId) {
    	String status = paymentServiceImpl.checkPaymentStatus(paymentId);
    	return new ResponseEntity<>(status,HttpStatus.OK);

    }
    
   @GetMapping("/pagination")
   public ResponseEntity<Page<PaymentDTO>> findBetween(
		   @RequestParam(value = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
		   @RequestParam(value = "endDate",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
   		   @RequestParam(value = "pageNumber",defaultValue = "0")int pageNumber,
   		   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
   		   @RequestParam(value = "sort",defaultValue = "createdDate,desc")String sort){
   			   
   		Sort sortable = Sort.by(sort.split(",")[0]);
 	     if (sort.split(",").length > 1) {
 	           sortable = (sort.split(",")[1].equalsIgnoreCase("asc")) ? sortable.ascending() : sortable.descending();
 	     }
 	     
 	     Pageable pageable = PageRequest.of(pageNumber, pageSize,sortable);
	   
	   Page<PaymentDTO> payments = paymentServiceImpl.findPaymentBetween(startDate, endDate, pageable);
	   return ResponseEntity.ok(payments);
   }
   
}
