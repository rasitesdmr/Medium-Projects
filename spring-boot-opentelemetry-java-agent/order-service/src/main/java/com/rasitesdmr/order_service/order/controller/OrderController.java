package com.rasitesdmr.order_service.order.controller;

import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import com.rasitesdmr.order_service.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderCreateRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/confirmation/status")
    public ResponseEntity<Boolean> updateOrderConfirmationStatus(@RequestParam Long orderId, @RequestParam String orderStatus) {
        return new ResponseEntity<>(orderService.updateOrderConfirmationStatus(orderId, orderStatus), HttpStatus.OK);
    }
}
