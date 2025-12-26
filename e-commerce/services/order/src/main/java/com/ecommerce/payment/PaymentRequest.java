package com.ecommerce.payment;

import java.math.BigDecimal;

import com.ecommerce.customer.CustomerResponse;
import com.ecommerce.order.PaymentMethod;

//order에서 payment 도메인에 요청을 보냄
public record PaymentRequest(
	    BigDecimal amount,
	    PaymentMethod paymentMethod,
	    Integer orderId,
	    String orderReference,
	    CustomerResponse customer
) {
}
