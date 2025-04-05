package com.rasitesdmr.springboottransactionmanagement.stock.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateRequest {
    private Long productId;
    private int stockQuantity;
}
