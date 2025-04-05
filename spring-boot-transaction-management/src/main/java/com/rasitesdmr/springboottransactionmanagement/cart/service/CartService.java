package com.rasitesdmr.springboottransactionmanagement.cart.service;

import com.rasitesdmr.springboottransactionmanagement.cart.Cart;

public interface CartService {

    Cart getById(Long id);
    Cart getByCustomerId(Long customerId);
}
