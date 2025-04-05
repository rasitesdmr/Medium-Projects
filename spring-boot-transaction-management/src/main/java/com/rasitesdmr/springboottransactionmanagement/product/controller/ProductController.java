package com.rasitesdmr.springboottransactionmanagement.product.controller;

import com.rasitesdmr.springboottransactionmanagement.product.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(path = "/payment/card")
    public void processProductPaymentWithCard(@RequestParam Long customerId) {
        productService.processProductPaymentWithCard(customerId);
    }
}
