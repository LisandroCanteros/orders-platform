package com.example.payments.consumer;

import com.example.commons.dto.OrderDTO;
import com.example.payments.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "orders-topic", groupId = "payments-group")
    public void consumerOrder(OrderDTO order) {
        paymentService.processPayment(order)
                .subscribe(
                        success -> System.out.println("Payment processed for order " + order.getId()),
                        error -> System.out.println("Failed payment: " + error.getMessage())
                );
    }
}
