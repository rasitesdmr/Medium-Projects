package com.rasitesdmr.stock_service.stock.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateRequest {

    private int quantity;
    private Long productId;
}
