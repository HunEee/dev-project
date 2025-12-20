package com.ecommerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.customer.CustomerResponse;
import com.ecommerce.order.PaymentMethod;
import com.ecommerce.product.PurchaseResponse;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
	
}
