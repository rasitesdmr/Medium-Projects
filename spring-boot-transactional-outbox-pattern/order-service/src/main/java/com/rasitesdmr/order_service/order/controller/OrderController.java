package com.rasitesdmr.order_service.order.controller;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import com.rasitesdmr.order_service.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequest orderCreateRequest){
        return new ResponseEntity<>(orderService.createOrder(orderCreateRequest), HttpStatus.CREATED);
    }
}
