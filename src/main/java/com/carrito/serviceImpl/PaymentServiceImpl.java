package com.carrito.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.PaymentDTO;
import com.carrito.entity.CartItem;
import com.carrito.entity.Order;
import com.carrito.entity.Payment;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.User;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.PaymentMapper;
import com.carrito.mapper.PaymentMapperImpl;
import com.carrito.repository.CartItemRepository;
import com.carrito.repository.PaymentRepository;
import com.carrito.repository.ShoppingCartRepository;
import com.carrito.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private PaymentMapper mapper =  new PaymentMapperImpl();
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Value("${stripe.apiKey}")
	private String stripeApiKey;
	
	
	public String proccessPaymant(UUID shoppingCartId) throws StripeException {
		
		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(()-> new ResourceNotFoundException("ShoppingCart", "id", shoppingCartId));

        Stripe.apiKey = stripeApiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", shoppingCart.getTotal().multiply(BigDecimal.valueOf(100)).intValue());
        params.put("currency", "usd");
        params.put("payment_method_types[]", "card");

        SessionCreateParams.LineItem item = new SessionCreateParams.LineItem.Builder()
                .setName("Payment for Shopping Cart")
                .setAmount(shoppingCart.getTotal().multiply(BigDecimal.valueOf(100)).longValue())
                .setCurrency("usd")
                .setQuantity(1L)
                .build();
        List<SessionCreateParams.LineItem> lineItems = Collections.singletonList(item);

        SessionCreateParams.Builder sessionParams = new SessionCreateParams.Builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://example.com/success")
                .setCancelUrl("https://example.com/cancel");

        Session session = Session.create(sessionParams.build());
        
        String paymentId = session.getPaymentIntent();
        
       Payment payment = new Payment();
       payment.setId(paymentId);
       payment.setShoppingCart(shoppingCart);
       payment.setStatus("generated");
       payment.setAmount(shoppingCart.getTotal());
       paymentRepository.save(payment);
        
        return session.getUrl();
		

	}
	
	public List<PaymentDTO> getPaymentToVerify(){
		List<Payment> payments = paymentRepository.findAll();
		
		List<Payment> unverified = new ArrayList<>();
		
		for(Payment payment : payments) {
			if(payment.getStatus().equals("generated")) {
				unverified.add(payment);
			}
		}
		
		if(unverified.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "List of payments to verfiy is empty");
		}
		
		return unverified.stream().map(payment -> mapper.paymentToPaymentDTO(payment)).collect(Collectors.toList());
	}
	
	
	public String checkPaymentStatus(String paymentId) {
        Stripe.apiKey = stripeApiKey;
        
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new ResourceNotFoundException("Payment", "id", paymentId));

        try {
        	
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
            
            String paymentMethodId = paymentIntent.getPaymentMethod();
            
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
            
            String cardBrand = paymentMethod.getCard().getBrand();
            
            String paymentStatus = paymentIntent.getStatus();
            
            String email = payment.getShoppingCart().getUser().getEmail();
            
            if(paymentStatus.equals("succeeded")) {
            	payment.setStatus(paymentStatus);
            	payment.setCurrency(paymentIntent.getCurrency());
            	payment.setMethodPayment(cardBrand);
            	paymentRepository.save(payment);
            	
            	moveCartItemToOrder(payment.getShoppingCart().getId());
            	emailService.sendPaymentApprovalEmail(email);
            	
            	return "Payment status : " + paymentStatus;
            	
            }else {
            	payment.setStatus(paymentStatus);
            	paymentRepository.save(payment);
            	return "Paymnet status :" + paymentStatus;
            }

                           
        } catch (StripeException e) {
            e.printStackTrace();
        	throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Error checking payment status");
        }
    }
	
	public void moveCartItemToOrder(UUID shoppingCartId) {
		
		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(()-> new ResourceNotFoundException("ShoppingCart", "id", shoppingCartId));

		User user = shoppingCart.getUser();
		
		Order order = user.getOrder();
		
		if(!shoppingCart.getUser().getId().equals(user.getId())) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "ShoppingCart does not belong to user");
		}
	
		Set<CartItem> cartItem = shoppingCart.getCartItem();
		
		for(CartItem item : cartItem) {
			item.setShoppingCart(null);
			item.setOrder(order);
			cartItemRepository.saveAll(cartItem);
		}
		
		shoppingCart.setStatus(null);
		shoppingCart.setTotal(null);
		shoppingCartRepository.save(shoppingCart);
	}

	@Override
	public Page<PaymentDTO> findPaymentBetween(Date startDate, Date endDate,Pageable pageable) {
		
		if(startDate == null && endDate == null) {
			
			Page<Payment> payments = paymentRepository.findAll(pageable);
			return payments.map(payment ->mapper.paymentToPaymentDTO(payment));		
			
		}else if(endDate == null) {
			
			endDate = new Date(Long.MAX_VALUE);
			
			Page<Payment> payments = paymentRepository.findByCreatedDateAfterAndCreatedDateBefore(startDate, endDate, pageable);
			return payments.map(payment ->mapper.paymentToPaymentDTO(payment));
			
		}else {
			
			Page<Payment> payments = paymentRepository.findByCreatedDateBetween(startDate, endDate, pageable);
			return payments.map(payment -> mapper.paymentToPaymentDTO(payment));
		}

	}

	@Override
	public List<PaymentDTO> listAll() {
		List<Payment> payments = paymentRepository.findAll();
		
		if(payments.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "List of payments is empty");
		}
		
		return payments.stream().map(payment ->mapper.paymentToPaymentDTO(payment)).collect(Collectors.toList());
	}

}
	


