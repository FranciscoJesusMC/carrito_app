package com.carrito.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.carrito.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;


	@Override
	public void sendPaymentApprovalEmail(String userEmail) {
	       SimpleMailMessage mensaje = new SimpleMailMessage();
	        mensaje.setTo(userEmail);
	        mensaje.setFrom("APP_CARRITO <cuentaparaapi95@outlook.es>");
	        mensaje.setSubject("Gracias por su orden!");
	        mensaje.setText("Confirmacion de pago, ¡El pago ha sido realizado!");

	        javaMailSender.send(mensaje);
	}
	

}