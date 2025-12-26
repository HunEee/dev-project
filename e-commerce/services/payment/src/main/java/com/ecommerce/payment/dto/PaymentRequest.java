package com.ecommerce.payment.dto;

import java.math.BigDecimal;

import com.ecommerce.payment.domain.PaymentMethod;

public record PaymentRequest(
    Integer id,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    Customer customer
) {
}