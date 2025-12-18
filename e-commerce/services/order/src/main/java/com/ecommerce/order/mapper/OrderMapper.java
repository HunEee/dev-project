package com.ecommerce.order.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.order.Order;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;

@Service
public class OrderMapper {

  public Order toOrder(OrderRequest request) {
    if (request == null) {
      return null;
    }
    return Order.builder()
        .id(request.id())
        .reference(request.reference())
        .paymentMethod(request.paymentMethod())
        .customerId(request.customerId())
        .build();
  }

  public OrderResponse fromOrder(Order order) {
	  return new OrderResponse(
		order.getId(),
		order.getReference(),
		order.getTotalAmount(),
		order.getPaymentMethod(),
		order.getCustomerId()
      );
  }
  
}
