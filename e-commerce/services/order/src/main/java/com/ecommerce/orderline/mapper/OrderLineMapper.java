package com.ecommerce.orderline.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.order.Order;
import com.ecommerce.orderline.OrderLine;
import com.ecommerce.orderline.dto.OrderLineRequest;
import com.ecommerce.orderline.dto.OrderLineResponse;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                //.id(request.orderId())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
