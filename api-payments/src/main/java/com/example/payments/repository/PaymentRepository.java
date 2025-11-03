package com.example.payments.repository;

import com.example.payments.model.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentRepository extends ReactiveCrudRepository<Payment, Long> {
}
