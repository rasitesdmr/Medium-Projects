package com.rasitesdmr.springboottransactionmanagement.cartitem.service;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getAllByCartId(Long cartId);
    void deleteById(Long id);
}
