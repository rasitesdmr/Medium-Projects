package com.rasitesdmr.springboottransactionmanagement.cart.repository;

import com.rasitesdmr.springboottransactionmanagement.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCustomerId(Long customerId);
}
