package com.example.apiorder.controller;

import com.example.commons.dto.OrderDTO;
import com.example.services.service.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<OrderDTO> getAll() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Mono<OrderDTO> create(@RequestBody OrderDTO dto) {
        return orderService.createOrder(dto);
    }
}
