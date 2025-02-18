package com.rasitesdmr.order_service.order.model.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateRequest {

    private String name;
    private String customerId;
    private String productType;
    private int quantity;
    private BigDecimal price;
}
