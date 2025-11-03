package com.example.services.service;

import com.example.commons.dto.OrderDTO;
import com.example.services.domain.Order;
import com.example.services.kafka.OrderProducer;
import com.example.services.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProducer orderProducer;

    @InjectMocks
    private OrderService orderService;

    private OrderDTO orderDTO;
    private Order order;

    @BeforeEach
    void setUp() {
        orderDTO = new OrderDTO(1L, "Keyboard", 2);
        order = new Order();
        order.setId(1L);
        order.setProduct("Keyboard");
        order.setQuantity(2);
    }

    @Test
    void createOrder_shouldSaveAndPublishEvent() {
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.just(order));

        Mono<OrderDTO> result = orderService.createOrder(orderDTO);

        StepVerifier.create(result)
                .expectNextMatches(saved ->
                        saved.getId() == 1 &&
                                saved.getProduct().equals("Keyboard") &&
                                saved.getQuantity() == 2)
                .verifyComplete();

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProducer, times(1)).sendOrder(any(OrderDTO.class));
    }

    @Test
    void getAllOrders_shouldReturnFluxOfDtos() {
        when(orderRepository.findAll()).thenReturn(Flux.just(order));

        StepVerifier.create(orderService.getAllOrders())
                .expectNextMatches(dto -> dto.getProduct().equals("Keyboard"))
                .verifyComplete();

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void createOrder_shouldHandleRepositorySaveFailure() {
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.error(new RuntimeException("DB failure")));

        Mono<OrderDTO> result = orderService.createOrder(orderDTO);

        StepVerifier.create(result)
                .expectErrorSatisfies(error ->
                        assertThat(error)
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("DB failure"))
                .verify();

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProducer, never()).sendOrder(any(OrderDTO.class));
    }
}
