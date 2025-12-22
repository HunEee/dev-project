package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
