package com.ecommerce.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//config-server에 order-service 파일에 있는 url
//order에서 payment 도메인에 요청을 보냄
@FeignClient(
	    name = "product-service",
	    url = "${application.config.payment-url}"
)
public interface PaymentClient {

	@PostMapping
	Integer requestOrderPayment(@RequestBody PaymentRequest request);

}
