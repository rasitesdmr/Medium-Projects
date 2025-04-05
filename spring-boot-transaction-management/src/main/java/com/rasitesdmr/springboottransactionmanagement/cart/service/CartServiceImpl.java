package com.rasitesdmr.springboottransactionmanagement.cart.service;

import com.rasitesdmr.springboottransactionmanagement.cart.Cart;
import com.rasitesdmr.springboottransactionmanagement.cart.repository.CartRepository;
import com.rasitesdmr.springboottransactionmanagement.exception.exceptions.NotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotAvailableException("Cart not found."));
    }

    @Override
    public Cart getByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }
}
