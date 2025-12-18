package com.ecommerce.payment;

import java.math.BigDecimal;

import com.ecommerce.customer.CustomerResponse;
import com.ecommerce.order.PaymentMethod;

public record PaymentRequest(
	    BigDecimal amount,
	    PaymentMethod paymentMethod,
	    Integer orderId,
	    String orderReference,
	    CustomerResponse customer
) {
}
