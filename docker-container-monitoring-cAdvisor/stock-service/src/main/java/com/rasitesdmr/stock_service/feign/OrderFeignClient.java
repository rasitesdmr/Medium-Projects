package com.rasitesdmr.stock_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderFeignClient", url = "http://order-service:8081/api/v1/orders")
public interface OrderFeignClient {

    @PostMapping(path = "/confirmation/status")
    ResponseEntity<Boolean> feignUpdateOrderConfirmationStatus(@RequestParam Long orderId, @RequestParam String orderStatus);

}
