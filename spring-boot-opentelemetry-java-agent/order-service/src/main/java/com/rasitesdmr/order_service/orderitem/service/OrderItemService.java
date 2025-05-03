package com.rasitesdmr.order_service.orderitem.service;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.orderitem.OrderItem;
import com.rasitesdmr.order_service.orderitem.model.request.OrderItemCreateRequest;

public interface OrderItemService {
    void saveOrderItem(OrderItem orderItem);
    OrderItem createOrderItem(Order order, OrderItemCreateRequest orderItemCreateRequest);
}
