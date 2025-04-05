package com.rasitesdmr.springboottransactionmanagement.product.repository;

import com.rasitesdmr.springboottransactionmanagement.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
