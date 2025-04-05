package com.rasitesdmr.springboottransactionmanagement.product.service;

import com.rasitesdmr.springboottransactionmanagement.product.Product;

public interface ProductService {

    void processProductPaymentWithCard(Long customerId);
    Product getById(Long id);
}
