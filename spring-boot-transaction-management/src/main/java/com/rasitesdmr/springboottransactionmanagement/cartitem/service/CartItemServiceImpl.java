package com.rasitesdmr.springboottransactionmanagement.cartitem.service;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.cartitem.repository.CartItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CartItem> getAllByCartId(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
