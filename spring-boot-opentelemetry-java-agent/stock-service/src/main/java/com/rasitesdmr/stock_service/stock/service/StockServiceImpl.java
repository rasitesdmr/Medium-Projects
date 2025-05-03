package com.rasitesdmr.stock_service.stock.service;

import com.rasitesdmr.stock_service.domain.enums.OrderStatus;
import com.rasitesdmr.stock_service.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.stock_service.domain.exception.exceptions.NotAcceptableException;
import com.rasitesdmr.stock_service.feign.OrderFeignClient;
import com.rasitesdmr.stock_service.stock.Stock;
import com.rasitesdmr.stock_service.stock.model.request.StockCreateRequest;
import com.rasitesdmr.stock_service.stock.repository.StockRepository;
import event.OrderCreatedEvent;
import event.OrderItemCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    private final StockRepository stockRepository;
    private final OrderFeignClient orderFeignClient;

    public StockServiceImpl(StockRepository stockRepository, OrderFeignClient orderFeignClient) {
        this.stockRepository = stockRepository;
        this.orderFeignClient = orderFeignClient;
    }

    @Override
    public void saveStock(Stock stock) {
        try {
            stockRepository.save(stock);
        } catch (Exception exception) {
            logger.error("Save stock error : {}", exception.getMessage());
        }
    }

    @Override
    public Stock createStock(StockCreateRequest stockCreateRequest) {
        Stock stock = Stock.builder()
                .productId(stockCreateRequest.getProductId())
                .quantity(stockCreateRequest.getQuantity())
                .build();
        saveStock(stock);
        return stock;
    }

    @Override
    public Boolean isStockSufficient(Long productId, int requestedStockQuantity) {
        Stock stock = getStockByProductId(productId);
        int currentStockQuantity = stock.getQuantity();
        return currentStockQuantity >= requestedStockQuantity;
    }

    @Override
    public Stock getStockByProductId(Long productId) {
        return stockRepository.findStockByProductId(productId);
    }

    @Override
    public void decreaseStockQuantity(OrderCreatedEvent orderCreatedEvent) {
        List<OrderItemCreatedEvent> items = orderCreatedEvent.getItemEvent();
        final Long orderId = orderCreatedEvent.getOrderId();
        items.forEach(item -> {
            final Long productId = item.getProductId();
            final int stockQuantity = item.getQuantity();

            Boolean checkStockSufficient = isStockSufficient(productId, stockQuantity);
            if (!checkStockSufficient) throw new NotAcceptableException("Stock was sufficient at the time of order creation, but became insufficient during order confirmation.");
            Stock stock = getStockByProductId(productId);
            int newStockQuantity = stock.getQuantity() - stockQuantity;
            stock.setQuantity(newStockQuantity);
            saveStock(stock);
            updateOrderConfirmationStatus(orderId,OrderStatus.CONFIRMED.name());
        });


    }

    @Override
    public void updateOrderConfirmationStatus(Long orderId, String orderStatus) {
        Boolean result = orderFeignClient.feignUpdateOrderConfirmationStatus(orderId, orderStatus).getBody();
        if (Boolean.FALSE.equals(result)) throw new InternalServerErrorException("Order Service Feign Error");
    }
}
