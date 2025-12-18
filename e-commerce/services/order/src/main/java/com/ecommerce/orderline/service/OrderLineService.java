package com.ecommerce.orderline.service;

import org.springframework.stereotype.Service;

import com.ecommerce.orderline.dto.OrderLineRequest;
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

}
