package com.rasitesdmr.order_service.orderitem.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateRequest {

    private int quantity;

    private BigDecimal totalPrice;

    private Long productId;

}
