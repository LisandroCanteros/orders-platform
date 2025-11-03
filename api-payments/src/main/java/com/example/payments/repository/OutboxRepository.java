package com.example.payments.repository;

import com.example.payments.model.OutboxEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OutboxRepository extends ReactiveCrudRepository<OutboxEvent, Long> {
    Flux<OutboxEvent> findByStatus(String status);
}
