package com.rasitesdmr.order_service.order.service;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;

public interface OrderService {
    void saveOrder(Order order);
    String createOrder(OrderCreateRequest orderCreateRequest);
    Boolean updateOrderConfirmationStatus(Long orderId, String orderStatus);
    Order getById(Long id);
}
