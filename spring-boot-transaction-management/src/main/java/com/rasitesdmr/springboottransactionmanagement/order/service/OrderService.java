package com.rasitesdmr.springboottransactionmanagement.order.service;

import com.rasitesdmr.springboottransactionmanagement.customer.Customer;
import com.rasitesdmr.springboottransactionmanagement.order.Order;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    void saveOrder(Order order);
    String approveOrder(Long authCustomerId);
    Order createOrder(Customer customer);
    Order getById(Long id);
    List<Order> getAllByCreatedDateBetween();
    void logDailyOrderCount();
}
