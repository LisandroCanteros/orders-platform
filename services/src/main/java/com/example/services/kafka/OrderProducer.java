package com.example.services.kafka;

import com.example.commons.dto.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    //                                 Key      Value
    public OrderProducer(KafkaTemplate<String, OrderDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDTO order) {
        kafkaTemplate.send("orders-topic", order.getId(), order);
    }
}
