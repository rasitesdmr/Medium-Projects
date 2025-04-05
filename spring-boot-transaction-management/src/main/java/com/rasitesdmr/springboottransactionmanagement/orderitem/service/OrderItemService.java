package com.rasitesdmr.springboottransactionmanagement.orderitem.service;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.order.Order;
import com.rasitesdmr.springboottransactionmanagement.orderitem.OrderItem;

public interface OrderItemService {

    void saveOrderItem(OrderItem orderItem);
    OrderItem createOrderItem(CartItem cartItem, Order order);
}
