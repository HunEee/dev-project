package com.ecommerce.dto;

import java.math.BigDecimal;

import com.ecommerce.domain.PaymentMethod;

public record PaymentRequest(
    Integer id,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    Customer customer
) {
}