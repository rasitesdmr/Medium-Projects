package com.rasitesdmr.springboottransactionmanagement.stock.repository;

import com.rasitesdmr.springboottransactionmanagement.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long productId);
}
