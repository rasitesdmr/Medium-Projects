package com.rasitesdmr.springboottransactionmanagement.stock.controller;

import com.rasitesdmr.springboottransactionmanagement.stock.model.request.StockUpdateRequest;
import com.rasitesdmr.springboottransactionmanagement.stock.model.response.StockUpdateResponse;
import com.rasitesdmr.springboottransactionmanagement.stock.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(path = "/api/v1/stocks")
public class StockController {


    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping(path = "/update")
    public ResponseEntity<List<StockUpdateResponse>> updateStockForProducts(@RequestBody List<StockUpdateRequest> stockUpdateRequests) {
        return new ResponseEntity<>(stockService.updateStockForProducts(stockUpdateRequests), HttpStatus.CREATED);
    }


    @GetMapping(path = "/dirty-read")
    public void dirtyReadIsolationLevel() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> stockService.updateStockByProductIdIsolationLevelDirtyRead(1L, 20));
        executorService.execute(() -> stockService.readStockByProductIdIsolationLevelDirtyRead(1L));
        executorService.shutdown();
    }

    @GetMapping(path = "/non-repeatable-read")
    public void nonRepeatableReadIsolationLevel() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> stockService.updateStockByProductIdIsolationLevelNonRepeatableRead(1L, 20));
        executorService.execute(() -> stockService.readStockByProductIdIsolationLevelNonRepeatableRead(1L));
        executorService.shutdown();
    }


    @GetMapping(path = "/phantom-read")
    public void phantomReadIsolationLevel() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(stockService::insertStockIsolationLevelPhantomRead);
        executorService.execute(stockService::readStocksIsolationLevelPhantomRead);
        executorService.shutdown();
    }
}
