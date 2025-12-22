package com.ecommerce.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.domain.Payment;
import com.ecommerce.dto.PaymentRequest;

@Service
public class PaymentMapper {

  public Payment toPayment(PaymentRequest request) {
    if (request == null) {
      return null;
    }
    return Payment.builder()
        .id(request.id())
        .paymentMethod(request.paymentMethod())
        .amount(request.amount())
        .orderId(request.orderId())
        .build();
  }
}