package com.rasitesdmr.springboottransactionmanagement.order.repository;

import com.rasitesdmr.springboottransactionmanagement.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByCreatedDateBetween(Date startOfDay , Date endOfDay);
}
