package com.ecommerce.orderline.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.orderline.dto.OrderLineRequest;
import com.ecommerce.orderline.dto.OrderLineResponse;
import com.ecommerce.orderline.mapper.OrderLineMapper;
import com.ecommerce.orderline.repository.OrderLineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    
	private final OrderLineRepository repository;
    private final OrderLineMapper mapper;
    
    public Integer saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
    
}
