package com.example.apifulfillment.repository;

import com.example.apifulfillment.model.FulfilledOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FulfilledOrderRepository extends ReactiveMongoRepository<FulfilledOrder, String> {
}
