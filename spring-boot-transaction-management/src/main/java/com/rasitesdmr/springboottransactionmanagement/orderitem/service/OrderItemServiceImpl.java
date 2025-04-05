package com.rasitesdmr.springboottransactionmanagement.orderitem.service;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.order.Order;
import com.rasitesdmr.springboottransactionmanagement.orderitem.OrderItem;
import com.rasitesdmr.springboottransactionmanagement.orderitem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = OrderItem.builder()
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .order(order)
                .product(cartItem.getProduct())
                .build();

        saveOrderItem(orderItem);
        return orderItem;
    }
}
