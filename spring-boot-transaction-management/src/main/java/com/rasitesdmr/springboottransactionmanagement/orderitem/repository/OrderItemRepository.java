package com.rasitesdmr.springboottransactionmanagement.orderitem.repository;

import com.rasitesdmr.springboottransactionmanagement.orderitem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
