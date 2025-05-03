package com.rasitesdmr.order_service.order.repository;

import com.rasitesdmr.order_service.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
