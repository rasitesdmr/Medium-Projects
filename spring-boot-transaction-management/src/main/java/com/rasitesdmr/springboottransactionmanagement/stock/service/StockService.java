package com.rasitesdmr.springboottransactionmanagement.stock.service;

import com.rasitesdmr.springboottransactionmanagement.stock.Stock;
import com.rasitesdmr.springboottransactionmanagement.stock.model.request.StockUpdateRequest;
import com.rasitesdmr.springboottransactionmanagement.stock.model.response.StockUpdateResponse;

import java.util.List;

public interface StockService {
    List<StockUpdateResponse> updateStockForProducts(List<StockUpdateRequest> stockUpdateRequests);
    StockUpdateResponse updateStockForProduct(StockUpdateRequest stockUpdateRequest);
    void saveStock(Stock stock);
    Stock getByProductId(Long productId);
    void checkStockQuantity(Long productId, int cartItemQuantity);
    void deductStockForOrder(Long productId, int cartItemQuantity);


    Stock getStockByProductIdIsolationLevelDirtyRead(Long productId);
    void updateStockByProductIdIsolationLevelDirtyRead(Long productId, int quantity);
    void readStockByProductIdIsolationLevelDirtyRead(Long productId);


    Stock getStockByProductIdIsolationLevelNonRepeatableRead(Long productId);
    void updateStockByProductIdIsolationLevelNonRepeatableRead(Long productId, int quantity);
    void readStockByProductIdIsolationLevelNonRepeatableRead(Long productId);

    List<Stock> getStocksIsolationLevelPhantomRead();
    void insertStockIsolationLevelPhantomRead();
    void readStocksIsolationLevelPhantomRead();
}
