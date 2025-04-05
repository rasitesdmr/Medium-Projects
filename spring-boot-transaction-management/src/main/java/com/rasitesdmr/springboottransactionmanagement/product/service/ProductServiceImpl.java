package com.rasitesdmr.springboottransactionmanagement.product.service;

import com.rasitesdmr.springboottransactionmanagement.cart.service.CartService;
import com.rasitesdmr.springboottransactionmanagement.customer.service.CustomerService;
import com.rasitesdmr.springboottransactionmanagement.product.Product;
import com.rasitesdmr.springboottransactionmanagement.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final CustomerService customerService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CustomerService customerService, ProductRepository productRepository) {
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void processProductPaymentWithCard(Long customerId) {
        // Ürünü kart ile ödeme süreçleri...
        String cardData = customerService.getCustomerCardDataById(customerId);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
