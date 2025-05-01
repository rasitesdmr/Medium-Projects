package com.rasitesdmr.stock_service.stock.service;

import com.rasitesdmr.stock_service.stock.Stock;
import com.rasitesdmr.stock_service.stock.model.request.StockCreateRequest;
import event.OrderCreatedEvent;

public interface StockService {
    void saveStock(Stock stock);

    Stock createStock(StockCreateRequest stockCreateRequest);

    Boolean isStockSufficient(Long productId, int requestedStockQuantity);

    Stock getStockByProductId(Long productId);

    void decreaseStockQuantity(OrderCreatedEvent orderCreatedEvent);

    void updateOrderConfirmationStatus(Long orderId, String orderStatus);
}
