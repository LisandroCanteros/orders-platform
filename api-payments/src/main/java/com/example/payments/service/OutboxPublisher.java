package com.example.payments.service;

import com.example.payments.model.OutboxEvent;
import com.example.payments.repository.OutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(OutboxRepository outboxRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        outboxRepository.findByStatus("PENDING")
                .flatMap(event ->
                        Mono.fromFuture(kafkaTemplate.send("payments-topic", String.valueOf(event.getAggregateId()), event.getPayload()))
                                .then(outboxRepository.save(markAsSent(event)))
                )
                .subscribe();
    }

    private OutboxEvent markAsSent(OutboxEvent event) {
        event.setStatus("SENT");
        return event;
    }
}
