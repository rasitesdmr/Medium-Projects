package com.rasitesdmr.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stockFeignClient", url = "http://localhost:8083/api/v1/stocks")
public interface StockFeignClient {

    @GetMapping(path = "/sufficient")
    ResponseEntity<Boolean> feignIsStockSufficient(@RequestParam Long productId, @RequestParam int requestedStockQuantity);

}
