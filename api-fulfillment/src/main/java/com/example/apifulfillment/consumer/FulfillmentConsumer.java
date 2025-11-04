package com.example.apifulfillment.consumer;

import com.example.apifulfillment.model.FulfilledOrder;
import com.example.apifulfillment.repository.FulfilledOrderRepository;
import com.example.commons.dto.PaymentProcessedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class FulfillmentConsumer {
    private final FulfilledOrderRepository fulfilledOrderRepository;

    public FulfillmentConsumer(FulfilledOrderRepository fulfilledOrderRepository) {
        this.fulfilledOrderRepository = fulfilledOrderRepository;
    }

    @KafkaListener(topics = "payments-topic", groupId = "fulfillment-service")
    public void handlePayment(PaymentProcessedEvent event) {
        System.out.println("Received payment event for order"  + event.getOrderId());

        FulfilledOrder order = new FulfilledOrder();
        order.setOrderId(String.valueOf(event.getOrderId()));
        order.setPaymentStatus(event.getProduct());
        order.setAmount(event.getAmount());
        order.setPaymentStatus(event.getStatus());
        order.setFulfilledAt(Instant.now());

        fulfilledOrderRepository.save(order)
                .then(Mono.fromRunnable(() ->
                        System.out.println("Order fulfilled and saved for order ID: " + event.getOrderId())))
                .subscribe();
    }
}
