package com.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
