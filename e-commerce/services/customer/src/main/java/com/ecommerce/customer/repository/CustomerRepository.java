package com.ecommerce.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.customer.domain.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String > {

}
