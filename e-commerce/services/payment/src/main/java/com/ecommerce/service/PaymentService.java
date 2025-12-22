package com.ecommerce.service;

import org.springframework.stereotype.Service;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.mapper.PaymentMapper;
import com.ecommerce.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository repository;
	private final PaymentMapper mapper;
  
	public Integer createPayment(PaymentRequest request) {
		var payment = this.repository.save(this.mapper.toPayment(request));
    
		return payment.getId();
	}
	
}
