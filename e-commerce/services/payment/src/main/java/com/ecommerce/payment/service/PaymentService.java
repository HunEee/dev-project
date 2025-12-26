package com.ecommerce.payment.service;

import org.springframework.stereotype.Service;

import com.ecommerce.notification.NotificationProducer;
import com.ecommerce.notification.PaymentNotificationRequest;
import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.mapper.PaymentMapper;
import com.ecommerce.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository repository;
	private final PaymentMapper mapper;
	private final NotificationProducer notificationProducer;

  
	public Integer createPayment(PaymentRequest request) {
		var payment = this.repository.save(this.mapper.toPayment(request));
	    
		this.notificationProducer.sendNotification(
	            new PaymentNotificationRequest(
	                    request.orderReference(),
	                    request.amount(),
	                    request.paymentMethod(),
	                    request.customer().firstname(),
	                    request.customer().lastname(),
	                    request.customer().email()
	            )
	    );
		
		return payment.getId();
	}
	
}
