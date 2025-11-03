package com.example.services.service;

import com.example.commons.dto.OrderDTO;
import com.example.services.domain.Order;
import com.example.services.kafka.OrderProducer;
import com.example.services.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Flux<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .map(e -> new OrderDTO(e.getId(), e.getProduct(), e.getQuantity()));
    }

    public Mono<OrderDTO> createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());

        return orderRepository.save(order)
                .map(saved -> {
                    OrderDTO orderDTO = new OrderDTO(
                            saved.getId(), saved.getProduct(), saved.getQuantity()
                    );
                    orderProducer.sendOrder(orderDTO);
                    return orderDTO;
                });
    }
}
