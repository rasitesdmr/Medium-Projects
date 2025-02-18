package com.rasitesdmr.order_service.order.service;

import com.rasitesdmr.order_service.mapper.OrderMapper;
import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import com.rasitesdmr.order_service.order.repository.OrderRepository;
import com.rasitesdmr.order_service.outbox.service.OutboxService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OutboxService outboxService;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository, OutboxService outboxService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.outboxService = outboxService;
    }

    @Override
    @Transactional
    public Order createOrder(OrderCreateRequest orderCreateRequest) {
        Order order = orderMapper.orderCreateRequestToOrder(orderCreateRequest);
        orderRepository.save(order);
        outboxService.createOutbox(order);
        return order;
    }
}
