package com.rasitesdmr.springboottransactionmanagement.cartitem.repository;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByCartId(Long cartId);
}
