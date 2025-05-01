package com.rasitesdmr.stock_service.stock.controller;

import com.rasitesdmr.stock_service.stock.Stock;
import com.rasitesdmr.stock_service.stock.model.request.StockCreateRequest;
import com.rasitesdmr.stock_service.stock.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(path = "/sufficient")
    public ResponseEntity<Boolean> isStockSufficient(@RequestParam Long productId, @RequestParam int requestedStockQuantity) {
        return ResponseEntity.ok(stockService.isStockSufficient(productId, requestedStockQuantity));
    }

    @PostMapping(path = "/")
    public ResponseEntity<Stock> createStock(@RequestBody StockCreateRequest stockCreateRequest){
        return new ResponseEntity<>(stockService.createStock(stockCreateRequest),HttpStatus.CREATED);
    }
}
