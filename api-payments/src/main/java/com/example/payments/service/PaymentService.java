package com.example.payments.service;

import com.example.commons.dto.OrderDTO;
import com.example.payments.model.OutboxEvent;
import com.example.payments.model.Payment;
import com.example.payments.repository.OutboxRepository;
import com.example.payments.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentService(PaymentRepository paymentRepository, OutboxRepository outboxRepository) {
        this.paymentRepository = paymentRepository;
        this.outboxRepository = outboxRepository;
    }

    public Mono<Boolean> processPayment(OrderDTO order) {
        System.out.println("Processing payment for order: " + order.getProduct());

        if ("fail".equalsIgnoreCase(order.getProduct())) {
            return Mono.error(new RuntimeException("Payment declined"));
        }

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setProduct(order.getProduct());
        payment.setAmount(100.0);
        payment.setStatus("APPROVED");

        return paymentRepository.save(payment)
                .flatMap(saved -> {
                    try {
                        OutboxEvent event = new OutboxEvent();
                        event.setAggregateType("PAYMENT");
                        event.setAggregateId(saved.getId());
                        event.setEventType("PAYMENT_CREATED");
                        event.setPayload(objectMapper.writeValueAsString(saved));
                        event.setStatus("PENDING");
                        return outboxRepository.save(event).thenReturn(true);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
