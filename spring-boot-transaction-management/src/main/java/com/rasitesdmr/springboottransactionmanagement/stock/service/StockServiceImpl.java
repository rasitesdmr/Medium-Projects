package com.rasitesdmr.springboottransactionmanagement.stock.service;

import com.rasitesdmr.springboottransactionmanagement.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springboottransactionmanagement.product.service.ProductService;
import com.rasitesdmr.springboottransactionmanagement.stock.Stock;
import com.rasitesdmr.springboottransactionmanagement.stock.model.request.StockUpdateRequest;
import com.rasitesdmr.springboottransactionmanagement.stock.model.response.StockUpdateResponse;
import com.rasitesdmr.springboottransactionmanagement.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ApplicationContext applicationContext;
    private final ProductService productService;

    public StockServiceImpl(StockRepository stockRepository, ApplicationContext applicationContext, ProductService productService) {
        this.stockRepository = stockRepository;
        this.applicationContext = applicationContext;
        this.productService = productService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<StockUpdateResponse> updateStockForProducts(List<StockUpdateRequest> stockUpdateRequests) {
        List<StockUpdateResponse> responses = new ArrayList<>();
        stockUpdateRequests.forEach(stockUpdateRequest -> responses.add(getProxyStockService().updateStockForProduct(stockUpdateRequest)));
        return responses;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StockUpdateResponse updateStockForProduct(StockUpdateRequest stockUpdateRequest) {
        final Long productId = stockUpdateRequest.getProductId();
        final int stockQuantity = stockUpdateRequest.getStockQuantity();
        Stock stock = getByProductId(productId);
        stock.setQuantity(stockQuantity);
        saveStock(stock);

        try {
            if (productId == 3) {
                throw new InternalServerErrorException("An error occurred during the stock update of the product with ID: " + productId);
            }
        } catch (InternalServerErrorException e) {
            log.error("Stock update error : {}", e.getMessage());
            throw e;
        }

        return new StockUpdateResponse(productId, stockQuantity, true);
    }

    @Override
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock getByProductId(Long productId) {
        return stockRepository.findByProductId(productId);
    }

    @Override
    public void checkStockQuantity(Long productId, int cartItemQuantity) {
        Stock stock = getByProductId(productId);
        int stockQuantity = stock.getQuantity();
        if (cartItemQuantity > stockQuantity) {
            throw new InternalServerErrorException(String.format("The product with id %s is out of stock in the quantity you requested.", productId));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deductStockForOrder(Long productId, int cartItemQuantity) {
        Stock stock = getByProductId(productId);
        int stockQuantity = stock.getQuantity();
        int newStockQuantity = stockQuantity - cartItemQuantity;
        stock.setQuantity(newStockQuantity);
        saveStock(stock);
    }

    @Override
    public Stock getStockByProductIdIsolationLevelDirtyRead(Long productId) {
        return stockRepository.findByProductId(productId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStockByProductIdIsolationLevelDirtyRead(Long productId, int quantity) {
        Stock stock = getStockByProductIdIsolationLevelDirtyRead(productId);
        stock.setQuantity(quantity);
        saveStock(stock);
        log.info("Transaction A T0 anında stok miktarını güncelledi ve stok miktarı : {}", stock.getQuantity());

        try {
            Thread.sleep(Duration.ofMinutes(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Transaction A T3 anında rollback oluyor...");
        throw new InternalServerErrorException("Transaction A rollback");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void readStockByProductIdIsolationLevelDirtyRead(Long productId) {
        try {
            Thread.sleep(Duration.ofSeconds(20));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Stock stockT2 = getStockByProductIdIsolationLevelDirtyRead(productId);
        log.info("Transaction B T1 anında ürünün stok miktarını alıyor ve stok miktarı : {}", stockT2.getQuantity());

        try {
            Thread.sleep(Duration.ofMinutes(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stock getStockByProductIdIsolationLevelNonRepeatableRead(Long productId) {
        return stockRepository.findByProductId(productId);
    }

    @Override
    public void updateStockByProductIdIsolationLevelNonRepeatableRead(Long productId, int quantity) {
        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Stock stock = getStockByProductIdIsolationLevelNonRepeatableRead(productId);
        stock.setQuantity(quantity);
        log.info("Transaction B T1 anında stok miktarını güncelledi ve stok miktarı : {}", stock.getQuantity());

        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        saveStock(stock);
        log.info("Transaction B T2 anında stok miktarı commit edildi ve stok miktarı : {}", stock.getQuantity());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void readStockByProductIdIsolationLevelNonRepeatableRead(Long productId) {
        Stock stockT0 = getStockByProductIdIsolationLevelNonRepeatableRead(productId);
        log.info("Transaction A T0 anında ürünün stok miktarını alıyor ve stok miktarı : {}", stockT0.getQuantity());

        try {
            Thread.sleep(Duration.ofSeconds(30));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Stock stockT2 = getStockByProductIdIsolationLevelNonRepeatableRead(productId);
        log.info("Transaction A T3 anında ürünün stok miktarını alıyor ve stok miktarı : {}", stockT2.getQuantity());
    }

    @Override
    public List<Stock> getStocksIsolationLevelPhantomRead() {
        return stockRepository.findAll();
    }

    @Override
    public void insertStockIsolationLevelPhantomRead() {

        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Stock stock = Stock.builder().quantity(1).product(productService.getById(2L)).build();
        log.info("Transaction B T1 anında yeni stok eklendi");

        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        saveStock(stock);
        log.info("Transaction B T2 anında yeni eklenen stok commit edildi");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void readStocksIsolationLevelPhantomRead() {
        List<Stock> stocksT0 = getStocksIsolationLevelPhantomRead();
        log.info("Transaction A T0 anında stok sayısı: {}", stocksT0.size());

        try {
            Thread.sleep(Duration.ofSeconds(30));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Stock> stocksT3 = getStocksIsolationLevelPhantomRead();
        log.info("Transaction A T3 anında stok sayısı : {}", stocksT3.size());
    }

    private StockService getProxyStockService() {
        return applicationContext.getBean(StockService.class);
    }
}
