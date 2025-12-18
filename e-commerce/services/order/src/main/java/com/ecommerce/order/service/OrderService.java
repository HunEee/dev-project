package com.ecommerce.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.customer.CustomerClient;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.orderline.dto.OrderLineRequest;
import com.ecommerce.orderline.service.OrderLineService;
import com.ecommerce.payment.PaymentClient;
import com.ecommerce.payment.PaymentRequest;
import com.ecommerce.product.ProductClient;
import com.ecommerce.product.PurchaseRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderLineService orderLineService;
    

	@Transactional
    public Integer createOrder(OrderRequest request) {
		// customer 존재여부 체크 -> OpenFeim으로 구현
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("주문 생성 X :: 고객ID가 존재하지 않음"));
        
        // product 구매 -> RestTemplate으로 구현 
        var purchasedProducts = productClient.purchaseProducts(request.products());

        // order 도메인 로직 실행 -> Order와 OrderLine에 저장
        var order = this.repository.save(mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        
        //payment 호출 로직 
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        
        //주문 확정 알림 서비스 ->  kafka로 구현

        return order.getId();
    }	
	
}