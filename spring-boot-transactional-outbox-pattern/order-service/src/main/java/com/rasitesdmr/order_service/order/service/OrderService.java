package com.rasitesdmr.order_service.order.service;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;

public interface OrderService {

    Order createOrder(OrderCreateRequest orderCreateRequest);
}
