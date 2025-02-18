package com.rasitesdmr.order_service.mapper;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderMapper {

    public Order orderCreateRequestToOrder(OrderCreateRequest orderCreateRequest){
        return Order.builder()
                .name(orderCreateRequest.getName())
                .customerId(orderCreateRequest.getCustomerId())
                .productType(orderCreateRequest.getProductType())
                .quantity(orderCreateRequest.getQuantity())
                .price(orderCreateRequest.getPrice())
                .orderDate(new Date())
                .build();
    }
}
