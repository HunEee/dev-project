package com.ecommerce.customer.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.customer.domain.entity.Customer;
import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;

@Service
public class CustomerMapper {

	public Customer toCustomer(CustomerRequest request) {
	    if (request == null) {
	      return null;
	    }
	    return Customer.builder()
	        .id(request.id())
	        .firstname(request.firstname())
	        .lastname(request.lastname())
	        .email(request.email())
	        .address(request.address())
	        .build();
	}
	
	
	public CustomerResponse fromCustomer(Customer customer) {
	    if (customer == null) {
	      return null;
	    }
	    return new CustomerResponse(
	        customer.getId(),
	        customer.getFirstname(),
	        customer.getLastname(),
	        customer.getEmail(),
	        customer.getAddress()
	    );
	}

}	
