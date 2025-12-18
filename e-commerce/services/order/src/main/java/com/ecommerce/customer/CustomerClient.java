package com.ecommerce.customer;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//config-server에 order-service 파일에 있는 url
@FeignClient(
	    name = "customer-service",
	    url = "${application.config.customer-url}"
)
public interface CustomerClient {

	// 고객이 존재할수도 없기 때문에 Optional로 함
	// custmer 프로젝트에 url로 매핑된 것을 호출
	@GetMapping("/{customer-id}")
	Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);

}
