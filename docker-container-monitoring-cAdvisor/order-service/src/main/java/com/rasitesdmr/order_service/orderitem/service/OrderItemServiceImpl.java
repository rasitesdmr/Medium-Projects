package com.rasitesdmr.order_service.orderitem.service;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import com.rasitesdmr.order_service.order.service.OrderServiceImpl;
import com.rasitesdmr.order_service.orderitem.OrderItem;
import com.rasitesdmr.order_service.orderitem.model.request.OrderItemCreateRequest;
import com.rasitesdmr.order_service.orderitem.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        try {
            orderItemRepository.save(orderItem);
        } catch (Exception exception) {
            logger.error("Save order item error : {}", exception.getMessage());
        }
    }

    @Override
    public OrderItem createOrderItem(Order order, OrderItemCreateRequest orderItemCreateRequest) {
        OrderItem orderItem = OrderItem.builder()
                .quantity(orderItemCreateRequest.getQuantity())
                .totalPrice(orderItemCreateRequest.getTotalPrice())
                .productId(orderItemCreateRequest.getProductId())
                .order(order)
                .build();
        saveOrderItem(orderItem);
        return orderItem;
    }
}
