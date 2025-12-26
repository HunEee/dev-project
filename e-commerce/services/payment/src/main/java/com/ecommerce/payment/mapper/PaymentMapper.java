package com.ecommerce.payment.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.payment.domain.Payment;
import com.ecommerce.payment.dto.PaymentRequest;

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