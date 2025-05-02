package com.rasitesdmr.stock_service.stock.repository;

import com.rasitesdmr.stock_service.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    Stock findStockByProductId(Long productId);
}
