package com.rasitesdmr.springboottransactionmanagement.order.controller;

import com.rasitesdmr.springboottransactionmanagement.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(path = "")
    public ResponseEntity<String> approveOrder(@RequestHeader(name = "auth_customer_id") Long authCustomerId){
        return new ResponseEntity<>(orderService.approveOrder(authCustomerId), HttpStatus.CREATED);
    }
}
