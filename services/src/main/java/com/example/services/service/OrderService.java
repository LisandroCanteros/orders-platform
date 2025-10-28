package com.example.services.service;

import com.example.commons.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final List<OrderDTO> orders = new ArrayList<>();

    public List<OrderDTO> getAllOrders() {
        return orders;
    }

    public OrderDTO createOrder(OrderDTO dto) {
        orders.add(dto);
        return dto;
    }
}
