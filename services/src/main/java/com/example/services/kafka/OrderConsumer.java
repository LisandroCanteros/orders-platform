package com.example.services.kafka;

import com.example.commons.dto.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    @KafkaListener(topics = "orders-topic", groupId = "order-group")
    public void consume(OrderDTO order) {
        System.out.println("Order from Kafka: " + order.getId());
    }
}
